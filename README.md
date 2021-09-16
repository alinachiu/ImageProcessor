# ImageProcessing Program

ImageProcessing is a Java program which deals with image manipulation and enhancement. The model can
perform actions such as filtering images (blurring and sharpening), creating images with color
transformations (monochrome and sepia), creating images programmatically (checkerboards), and
importing/exporting images in PPM format. Note: only tests relevant to this assignment were
included to stay under the file limit.

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

## The GUI
When the user runs the interactive model via the terminal, an Image Processor GUI that can be operated
using the buttons on the left/right side or the menu items. The GUI MVC works with the same rules as
the textual view. To clarify, the user must create layers before attempting to
load, save, saveall, or apply color transformations/filters. If the user violates any rules, the
program will display a pop-up error message detailing to the user what they have done wrong.
The user can also use the create a checkerboard section to create a checkerboard image and layer
with custom or default (red/black) colors. If the user would like to add image layers,
they can name the layer in using the text field and then press create layer. A button
will then appear representing that layer and allowing them to navigate between layers. Applying photo
operations, loading, saving, removing, and making visible/invisible will be done to the current layer
that the user is on which is displayed in the bottom right hand corner. When loading in
images into the program, they must be the same dimensions and within the project folder. A text
script can also be loaded in to perform a series of commands all at once. The image displayed will be
the topmost visible image, even if the layer being manipulated is the current. More information can be found
in the USEME file.

## Image Downscaling
To implement image downscaling, we created a new interface called IImageResize which has a method called
apply which helps resize an image to a given dimension (change its width, height or both) based on the given width and height. The given width and height must be appropriate numbers (positive integers less than or equal to
the image's respective side dimension).
We also created a class that implements this interface called Downscale which applies downscaling
to the class's image field. There are also respective commands for downscaling in the new graphical 
controller and GUIView. When one image is downscaled in a multi-layered image, the others are also
downscaled to maintain the invariant that all images must be the same dimensions. To make downscaling
and mosaicking work properly, we also added a new class called AdditionalControllerUtils and two
new classes which implement the IPhotoCommands interface to allow the controller to execute these
new commands.

Here are some examples on how downscaling is used:
```Java
IImage image = new Image("puppy.ppm");
IImageResize downscale = new Downscale();
downscale.apply(image, 100, 80);

```

## Mosaic Effect
To implement the mosaic effect, we created a new interface called IPhotoEffect which has a method called
apply which applies a photo effect onto an image field of a class that implements this interface based
on a given number of random seeds. We also created a class that implements this interface called 
Mosaic which applies the mosaic effect (an effect that gives a photo the "stained glass effect") 
to the class's image field. There are also respective commands for mosaicking in the new graphical 
controller and GUIView.

Here are some examples on how downscaling is used:
```Java
IImage image = new Image("puppy.ppm");
IPhotoEffect mosaic = new Mosaic(image);

// applying the mosaic effect onto the puppy image with 8 random seed locations
mosaic.apply(8);
```

## How to use the Model Class

The Model class implements the IModel interface which can perform actions such as filtering a given
image, performing color transformations on a given image, and creating an image based on a user's
input. (Note: The Model class itself is field-less). Each method works by passing in the respective
function object as well as the given image that will be manipulated or created. In the case of
creating a given image, only the function object is necessary (more on how to use each function
object below). If any arguments of any methods are null, an IllegalArgumentException
will be thrown.

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
```

## How to use the LayerModel Class

The LayerModel class implements the ILayerModel interface, which can perform all the actions an IModel can as well as supporting multi-layered image processing functions. The additional methods can perform actions such as creating a new image layer based on a given name tht has no image associated with it and has a default transparency setting of true, removing an image layer based on a given name, loading a pre-existing image into the current layer, loading a group of layers at once based on a text file, making a layer visible/invisible, setting the current layer (the one that the program will perform actions on), filtering the current, performing color transformations on the current, and getting a deep copy of the layers in the model (Note: The LayerModel class itself is field-less). Each delegated method works by passing in the respective function object as well as the given image that will be manipulated/created/used as a multi-layered class. For the new methods, most rely on getting the name of the layer in String format. If any arguments of any methods are null, an IllegalArgumentException will be thrown.

Here are some examples of how to call each method properly in the LayerModel:

```Java
ILayerModel model = new LayerModel():

// create new image layers
model.createImageLayer("first");
model.createImageLayer("second");
model.createImageLayer("another");

// remove non-existent layer, should do nothing and remove "another" layer
model.removeImageLayer("fake");
model.removeImageLayer("another");

// load checkerboard into the second layer after setting it as current
model.setCurrent("second");
model.loadLayer("res/Checkerboard.ppm");

// load based on a list of layers
List<ILayer> list = new ArrayList<>();
list.add(new Layer("another"));
list.add(new Layer("dog"));

model.loadAll(list);

// make layer invisible and then visible again
model.makeLayerInvisible("another");
model.makeLayerVisible("another");

// filter and color transform on the current
model.filterCurrent(new Blur());
model.colorTransformCurrent(new Sepia());

// get deep copy of layers
List<ILayer> layers = model.getLayers();

```
## Notes about Each Method in ILayerModel
- createImageLayer: creates a new layer in a list of existing layers. If a layer with the given naem already exists, it will throw an IllegalArgumentException. If the layer is the first to be created, it becomes the current by default
- removeImageLayer: removes an existing layer based on a given name, will do nothing if no such layer exists
- loadLayer: loads in a given image to the current layer. If the image is not the same dimensions as the previous images in the model, the image will not be loaded into the layer
- loadAll: loads a multi-layered image based on a text file with information about them. if one or more of the layers does not exist, then an exception will be thrown. If any of the layers are different dimensions from the others, they will not be added either.
- makeLayerInvisible: makes a layer visible based on a given layer name, will do nothing if the layer does not exist
- makeLayerVisible: makes a layer invisible based on a given layer name, will do nothing if the layer does not exist
- setCurrent: sets the given layer based on its name as the current layer to perform operations on. If the layer does not exist it will do nothing.
- filterCurrent: applies the given filter onto the image associated with the current layer. if the current layer does not exist, has a null image, or is invisible, nothing will be done.
- colorTransformCurrent: applies the given color transformation onto the image associated with the current layer. if the current layer does not exist, has a null image, or is invisible, nothing will be done.

## What is a Layer?
A Layer implements the interface ILayer and has a name, image associated with it, and a visibility value. It can perform actions such as setting the image, setting the visibility, getting its name, image, and visibility levels.

Here is how the layer methods are used:
```Java
ILayer layer = new Layer("first");

// set layer as checkerboard image
layer.setImage(new Image("res/Checkerboard.ppm"));

// set visibility to be false
layer.setVisibility(false);

// get name of layer, should be "first"
String name = layer.getName();

// get Image
IImage image = layer.getImage();

// get visibility
boolean visibility = layer.isVisible();
```

## How to use the Controller
The controller follows the command design pattern and can either take in user input or a script file to perform image layer manipulation. 
More about the specifics of each known command can be found in the USEME file.

## The View
The view can do two things: append new messages or get the current state of the model using the methods renderMessage and renderLayerState.

They are used as follows:
```Java
IImageProcessingView view = new SimpleIImageProcessingView(new LayerModel());

// render a given message
view.renderMessage("hello!");

// render the current model state
view.renderLayerState();
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
field(s) of the class. An IllegalArgumentException will be thrown if the file/filename is not found. To import an image based on a text file, can use any object that implements the IOLayerManager class which converts a text file in a specific format into a List of ILayers.

Here is an example of how to correctly import an image using a filename/file:

```Java
// imports an image of the ocean using a filename
IOManager filenameManager = new InputFilenameManager("res/ocean.ppm");
IImage oceanFilename = filenameManager.apply();

// imports an image of the ocean using a file
IOManager fileManager = new InputFileManager(new File("res/ocean.ppm"));
IImage oceanFile = fileManager.apply();

// imports a jpeg image
IOManager fileJPEGManager = new InputJPEGFileManager(new File("res/flower.jpeg"));
IImage flowerFile = fileJPEGManager.apply();

IOManager filenameJPEGManager = new InputJPEGFilenameManager("res/flower.jpeg");
IImage flowerFilename = filenameJPEGManager.apply();

// imports a png image
IOManager filePNGManager = new InputPNGFileManager(new File("res/flower.png"));
IImage flowerFile = filePNGManager.apply();

IOManager filenamePNGManager = new InputPNGFilenameManager("res/flower.png");
IImage flowerFilename = filenamePNGManager.apply();

// imports a list of layers based on a text file
IOLayerManager layerManager = new InputTextFilenameManager("res/a/layerInfo.txt");
List<ILayer> listOfLayers = layerManager.apply();

```

To export an image, use objects which implement the IExport interface. In the case of the PPMExport
object, the field of the given image is used to export the image to the user's computer based on the
image's given height, width, and image grid. The export method exports the given image to the
correct format such as the .ppm format. An IOException is thrown if there are any issues with
writing. Can either use the default constructor which writes a file or pass in a type of Writer
(i.e. StringWriter or PrintWriter). Throws an IOException if any I/O error occurs in the constructor
or in the export method. JPEG and PNG images can also be exported via filename

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

## The JAR File
If not already placed there, please run the JAR file from the res folder. The JAR file can be run using user 
input by following the commands in the USEME or using a given script. 
Three sample scripts are given in the res folder that can be used to see all possible cases for this controller.
NOTE: script1 and script2 have errors in them to demonstrate the possible errors present in the program, 
to have a fully functioning program script run with only a few pop up error messages in the interactive view, 
run the guiScript.txt from the given res directory and click ok to exit out of the error messages that will
appear.

The JAR file can be run in three ways using the command line/terminal as seen below:
```Java
// using a script
java -jar HW7.jar -script path-of-script-file

// using user input
java -jar HW7.jar -text // then input commands as stated in USEME

// using the GUI
java -jar HW7.jar -interactive
```

## Note about the ImageUtil and AdditionalImageUtil classes

The ImageUtil class was used to help import images based on their filename. The AdditionalImageUtil
class was also used to help import and export images.

## Sources for Images:

- Dog Image: DL, JackieLou. “Puppy Lying Down.” Pixabay, 13 Nov. 2018,
<http://pixabay.com/photos/puppy-pup-puppy-lying-down-3813378/>.
- Ocean Image: Highsmith, Carol "Photographs of buildings in Los Angeles, California and the
surrounding area." Picryl, 01 Jan. 2005, <https://picryl.com/media/
photographs-of-buildings-in-los-angeles-california-and-the-surrounding-area-51>.
- Flower Image: Koshy, Koshy. "Pretty Flower." Flickr, 31 Aug. 2008,
<https://www.flickr.com/photos/kkoshy/2825871499>
