# ImageProcessing Program

ImageProcessing is a Java program which deals with image manipulation and enhancement. The model can
perform actions such as filtering images (blurring and sharpening), creating images with color
transformations (monochrome and sepia), creating images programmatically (checkerboards), and
importing/exporting images in PPM format.

## Chosen Image Representation

For the purposes of this program, images are represented as a 2D array of pixels as well as its
associated file name. The image will throw an Illegal argument exception if any fields are null or
if any elements in the 2D array are invalid pixels (i.e. null). Additionally, Pixels are represented
as having a non-negative x and y position and red, green, and blue integer values that are between 0
and 255, inclusive. To create an image, the user can either pass in a filename associated with an
existing file or pass in a 2D array of pixels that makes an image.

Below is an example of how to create IImages and IPixels:
```Java
// create an IImage with an existing filename
IImage existingImage = new PPMImage("res/ocean.ppm");

// create an IImage with a 2D array of IPixels
IPixel[][] sampleImageGrid = new IPixel[][]{{new Pixel(0, 0, 100, 50, 80),
                                              new Pixel(0, 1, 50, 200, 10),
                                              new Pixel(0, 2, 100, 40, 240),
                                              new Pixel(0, 3, 90, 88, 120)},
                                            {new Pixel(1, 0, 100, 50, 80),
                                              new Pixel(1, 1, 50, 200, 10),
                                              new Pixel(1, 2, 100, 40, 240),
                                              new Pixel(1, 3, 90, 88, 120)},
                                            {new Pixel(2, 0, 100, 50, 80),
                                              new Pixel(2, 1, 50, 200, 10),
                                              new Pixel(2, 2, 100, 40, 240),
                                              new Pixel(2, 3, 90, 88, 120)}};
this.sampleImage = new PPMImage(sampleImageGrid, "SampleImage");
```

## How to use the Model

The Model class implements the IModel interface which can perform actions such as filtering a given
image, performing color transformations on a given image, creating an image based on a user's input,
exporting a given image, and importing an image based on an IOManager which manages files based on
their names or the files themselves. (Note: The Model class itself is field-less). Each method works
by passing in the respective function object as well as the given image that will be manipulated or
exported. In the case of importing and creating a given image, only the function object is
necessary (more on how to use each function object below). If any arguments of any methods are null,
an IllegalArgumentException will be thrown. Additionally, if there are any unforeseen issues with
exporting the image as a file, an IOException will be thrown.

Here are some examples of how to call each method properly in the model:

```Java
IModel model = new Model();
IImage dog = new PPMImage("res/puppy.ppm");

// call filter
IImage sharpenedDog = model.filter(dog, new Sharpening());

// call colorTransformation
IImage grayScaleDog = model.color(dog, new Grayscale());

// create an image of a checkerboard with tiles of size 4 and 6 tiles total with default colors
IImage newCheckerboard = model.createImage(new CheckerboardImageCreator(4,6));

// export the new checkerboard image
model.exportImage(new PPMExport(newCheckerboard));

// import a koala image
model.importImage(new InputFilenameManager("res/koala.ppm")));
```

## Filtering

To filter an image using the model, the user can utilize function objects that implement the IFilter
interface. These function objects represent a filter for an image, and the operations it can perform
using its kernel. The method apply applies the filter using its kernel to a given image provided by
the user. For instance, the following code would create a new IPhoto copy of the dog photo with the
blur filter applied to it.

```Java
IFilter blur = new Blur();
IImage dog = new PPMImage("res/puppy.ppm");
IImage blurredDog = blur.apply(dog);
```

Note that, while the Blur and Sharpening classes have built-in kernels, the UserFilter class allows
the user to input their own kernel/filter in as parameter to be applied to a given image. Given that
the kernel is not null, and it is a square with odd dimensions. If the provided kernel is not a
square matrix with odd dimensions, the kernel will be reformulated. More specifically, if the greater 
side dimension of the kernal is odd, then the rectangular matrix will be padded with zeros to create 
a square matrix with the dimension of the greater side of the orignal matrix. On the other hand, 
if the greater side dimension of the kernal is even then the kernal cannot be reformulated and 
an exception will be thrown.

## Color Transformations

To perform a color transformation on an image using the model, the user can utilize function objects
that implement the IColorTransformation interface. Similarly to the IFilter objects, these function
objects represent objects that have a method apply which applies a color transformation to a given
image based on the 3x3 matrix associated with the object. The following code snippet creates a new
IPhoto copy of a koala photo with Sepia applied to it.

```Java
IColorTransformation sepia = new Sepia();
IImage koala = new PPMImage("res/koala.ppm");
IImage sepiaKoala = sepia.apply(koala);
```

Like the IFilter function objects, the Sepia and Grayscale classes have default matrices associated
with their color transformation while the UserColorTransformation class allows a user to input their
own transformation matrix. It must be non-null and 3x3 or an IllegalArgumentException will be
thrown.

## Programmatic Image Creation

To create an image based on user input, use any object that implements the IImageCreator interface.
The method, createImage() creates a new image based on user parameters. For instance, with the
CheckerboardImageCreator, a user can give the size of the checkerboard tiles as well as the number
of desired tiles for the board dimension. A user can also specify the RGB values for 
both colors of tiles if they want.

Here is an example of how to create a function object for programmatic image creation and use it:

```Java
// represents an image creator that makes a 3x3 checkerboard with tiles of size 4 and default colors
IImageCreator creator = new CheckerboardImageCreator(3, 3);
IImage newCheckerboard = creator.createImage();

// represents an image creator that makes a 2x2, black and white checkerboard
IImageCreator blackAndWhiteCreator = new CheckerboardImageCreator(2, 2, 0, 0, 0, 255, 255, 255);
IImage newBlackAndWhiteCheckerboard = blackAndWhiteCreator.createImage();
```

## Importing/Exporting PPM Images

To import an image, use objects which implement the IOManager class, which manages different input
types such as files and filenames. It uses a method apply to create the IImage associated with the
field(s) of the class. An IllegalArgumentException will be thrown if the file/filename is not found.

Here is an example of how to correctly import an image using a filename/file:

```Java
// imports an image of the ocean using a filename
IOManager filenameManager = new InputFilenameManager("res/ocean.ppm");
IImage oceanFilename = filenameManager.apply();

// imports an image of the ocean using a file
IOManager fileManager = new InputFileManager(new File("res/ocean.ppm"));
IImage oceanFile = fileManager.apply();
```

To export an image, use objects which implement the IExport interface. In the case of the PPMExport
object, the field of the given image is used to export the image to the user's computer based on the
image's given height, width, and image grid. The export method exports the given image to the
correct format such as the .ppm format. An IOException is thrown if there are any issues with
writing. Can either use the default constructor which writes a file or pass in a type of Writer
(i.e. StringWriter or PrintWriter). Throws an IOException if any I/O error occurs in the constructor
or in the export method.

Here is an example of how to correctly export an image using a filename:

```Java
// exports an image of the ocean using default constructor, which will create a new image 
// called oceanNew.ppm
IImage ocean = new PPMImage("res/ocean.ppm");
IExport export = new PPMExport(ocean);
export.export();

// exports an image using a StringWriter instead
IImage dog = new PPMImage("res/puppy.ppm", new StringWriter());
IExport export = new PPMExport(dog);
export.export();
```

## Note about the ImageUtil class

The ImageUtil class was used to help import images based on their filename.

## Sources for Images:

- Dog Image: DL, JackieLou. “Puppy Lying Down.” Pixabay, 13 Nov. 2018,
  <pixabay.com/photos/puppy-pup-puppy-lying-down-3813378/>.
- Ocean Image: Highsmith, Carol "Photographs of buildings in Los Angeles, California and the
  surrounding area." Picryl, 01 Jan. 2005, <https://picryl.com/media/
  photographs-of-buildings-in-los-angeles-california-and-the-surrounding-area-51>.