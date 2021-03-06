import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import controller.IImageProcessingController;
import controller.SimpleIImageProcessingController;
import java.io.File;
import java.io.StringReader;
import model.ILayerModelState;
import model.LayerModelState;
import model.layer.ILayerModel;
import model.layer.LayerModel;
import model.mocks.FailingAppendable;
import model.mocks.FailingReadable;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents the test class for {@code IImageProcessingControllerTest} to ensure that the
 * controller's behavior works as expected and properly deals with user input.
 */
public class IImageProcessingControllerTest {

  ILayerModel model;

  @Before
  public void initData() {
    this.model = new LayerModel();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionNullModelForReadableInput() {
    Readable input = new StringReader("");
    Appendable app = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(null, input, app);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionNullReadableForReadableInput() {
    Readable input = null;
    Appendable app = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, app);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionNullAppendableForReadableInput() {
    Readable input = new StringReader("res/script1.txt");
    Appendable app = null;
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, app);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionNullModelForFileInput() {
    File input = new File("res/script1.txt");
    Appendable app = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(null, input, app);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionNullFileForFileInput() {
    File input = null;
    Appendable app = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, app);
    c.processImage();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionFileNotFoundForFileInput() {
    File input = new File("fakeFile");
    Appendable app = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, app);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionNullAppendableForFileInput() {
    File input = new File("fakeFile");
    Appendable app = null;
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, app);
  }

  @Test(expected = IllegalStateException.class)
  public void testProcessImageFailingAppendable() {
    Readable input = new StringReader("");
    Appendable gameLog = new FailingAppendable();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, gameLog);
    c.processImage();
  }

  @Test(expected = IllegalStateException.class)
  public void testProcessImageFailingReadable() {
    Readable fail = new FailingReadable();
    StringBuilder app = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, fail, app);
    c.processImage();
  }

  @Test
  public void testProcessImageQuitWithLowercaseQForReadableInput() {
    Readable input = new StringReader("q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals("The process has been quit.\n", ap.toString());
  }

  @Test
  public void testProcessImageQuitWithQForReadableInput() {
    Readable input = new StringReader("Q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals("The process has been quit.\n", ap.toString());
  }

  @Test
  public void testProcessImageQuitWithQuitLowercaseForReadableInput() {
    Readable input = new StringReader("quit");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals("The process has been quit.\n", ap.toString());
  }

  @Test
  public void testProcessImageQuitWithQuitForReadableInput() {
    Readable input = new StringReader("QuIt");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals("The process has been quit.\n", ap.toString());
  }

  @Test
  public void testBlurOneLayerCreated() {
    Readable input = new StringReader("create first load res/check.ppm blur q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals(
        "Layer #1, Name of Layer: first, No Image Associated With This Layer, "
            + "Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, No Image Associated With This Layer,"
            + " Visibility: true\n"
            + "Layer #1, Name of Layer: first, Image Filename: res/check.ppm, "
            + "Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, Image Filename: res/check.ppm, "
            + "Visibility: true\n"
            + "Layer #1, Name of Layer: first, Image Filename: res/check.ppm, "
            + "Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, Image Filename: res/check.ppm, "
            + "Visibility: true\n"
            + "The process has been quit.\n", ap.toString());
  }

  @Test
  public void testBlurNonExistentLayer() {
    Readable input = new StringReader("blur q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals("Invalid command! Try again! Operation cannot be performed! "
        + "Image must be visible and present.\n"
        + "The process has been quit.\n", ap.toString());
  }

  @Test
  public void testSharpenOneLayerCreated() {
    Readable input = new StringReader("create first load res/check.ppm sharpen q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals(
        "Layer #1, Name of Layer: first, No Image Associated With This Layer,"
            + " Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, No Image Associated With This Layer,"
            + " Visibility: true\n"
            + "Layer #1, Name of Layer: first, Image Filename: res/check.ppm,"
            + " Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, Image Filename: res/check.ppm,"
            + " Visibility: true\n"
            + "Layer #1, Name of Layer: first, Image Filename: res/check.ppm,"
            + " Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, Image Filename: res/check.ppm,"
            + " Visibility: true\n"
            + "The process has been quit.\n", ap.toString());
  }

  @Test
  public void testSharpenNonExistentLayer() {
    Readable input = new StringReader("sharpen q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals("Invalid command! Try again! Operation cannot be performed! "
        + "Image must be visible and present.\n"
        + "The process has been quit.\n", ap.toString());
  }

  @Test
  public void testSepiaOneLayerCreated() {
    Readable input = new StringReader("create first load res/check.ppm sepia q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals(
        "Layer #1, Name of Layer: first, No Image Associated With This Layer,"
            + " Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, No Image Associated With This Layer,"
            + " Visibility: true\n"
            + "Layer #1, Name of Layer: first, Image Filename: res/check.ppm,"
            + " Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, Image Filename: res/check.ppm,"
            + " Visibility: true\n"
            + "Layer #1, Name of Layer: first, Image Filename: res/check.ppm,"
            + " Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, Image Filename: res/check.ppm,"
            + " Visibility: true\n"
            + "The process has been quit.\n", ap.toString());
  }

  @Test
  public void testSepianNonExistentLayer() {
    Readable input = new StringReader("sepia q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals("Invalid command! Try again! Operation cannot be performed! "
        + "Image must be visible and present.\n"
        + "The process has been quit.\n", ap.toString());
  }

  @Test
  public void testGrayscaleOneLayerCreated() {
    Readable input = new StringReader("create first load res/check.ppm grayscale q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals(
        "Layer #1, Name of Layer: first, No Image Associated With This Layer,"
            + " Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, No Image Associated With This Layer, "
            + "Visibility: true\n"
            + "Layer #1, Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, Image Filename: res/check.ppm,"
            + " Visibility: true\n"
            + "Layer #1, Name of Layer: first, Image Filename: res/check.ppm, "
            + "Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, Image Filename: res/check.ppm, "
            + "Visibility: true\n"
            + "The process has been quit.\n", ap.toString());
  }

  @Test
  public void testGrayscaleNonExistentLayer() {
    Readable input = new StringReader("grayscale q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals("Invalid command! Try again! Operation cannot be performed!"
        + " Image must be visible and present.\n"
        + "The process has been quit.\n", ap.toString());
  }

  @Test
  public void testCreateMultipleLayers() {
    Readable input = new StringReader("create first create second create third q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals(
        "Layer #1, Name of Layer: first, No Image Associated With This Layer, "
            + "Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, No Image Associa"
            + "ted With This Layer, Visibility: true\n"
            + "Layer #1, Name of Layer: first, No Image Associated"
            + " With This Layer, Visibility: true\n"
            + "Layer #2, Name of Layer: second, No Image Associated "
            + "With This Layer, Visibility: true\n"
            + "Number of valid layers created: 2\n"
            + "Current Layer: Name of Layer: first, No Image Associated "
            + "With This Layer, Visibility: true\n"
            + "Layer #1, Name of Layer: first, No Image Associated With "
            + "This Layer, Visibility: true\n"
            + "Layer #2, Name of Layer: second, No Image Associated With "
            + "This Layer, Visibility: true\n"
            + "Layer #3, Name of Layer: third, No Image Associated With "
            + "This Layer, Visibility: true\n"
            + "Number of valid layers created: 3\n"
            + "Current Layer: Name of Layer: first, No Image Associated "
            + "With This Layer, Visibility: true\n"
            + "The process has been quit.\n", ap.toString());
  }

  @Test
  public void testAttemptToCreateDuplicateLayers() {
    Readable input = new StringReader(
        "create first create second create third \ncreate third create first q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    String[] split = ap.toString().split("\n");

    assertEquals("Invalid command! Try again! Layer already exists!",
        split[split.length - 3]);
    assertEquals("Invalid command! Try again! Layer already exists!",
        split[split.length - 2]);
  }

  @Test
  public void testRemoveLayer() {
    Readable input = new StringReader("create first create second remove second q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals(
        "Layer #1, Name of Layer: first, No Image Associated With This Layer, "
            + "Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, No Image Associated With This Layer,"
            + " Visibility: true\n"
            + "Layer #1, Name of Layer: first, No Image Associated With This Layer, "
            + "Visibility: true\n"
            + "Layer #2, Name of Layer: second, No Image Associated With This Layer,"
            + " Visibility: true\n"
            + "Number of valid layers created: 2\n"
            + "Current Layer: Name of Layer: first, No Image Associated With This"
            + " Layer, Visibility: true\n"
            + "Layer #1, Name of Layer: first, No Image Associated With This Layer,"
            + " Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, No Image Associated With This "
            + "Layer, Visibility: true\n"
            + "The process has been quit.\n", ap.toString());
  }

  @Test
  public void testRemoveNonExistentLayerAndAllRemoved() {
    Readable input = new StringReader(
        "create first remove second remove first q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    String[] split = ap.toString().split("\n");

    assertEquals("Current not yet set.", split[split.length - 2]);
    assertEquals("Invalid command! Try again! Layer does not exist!",
        split[split.length - 4]);
  }

  @Test
  public void testSetCurrent() {
    Readable input = new StringReader(
        "create first create second current second q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals(
        "Layer #1, Name of Layer: first, No Image Associated With This Layer, "
            + "Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, No Image Associated With This Layer, "
            + "Visibility: true\n"
            + "Layer #1, Name of Layer: first, No Image Associated With This Layer,"
            + " Visibility: true\n"
            + "Layer #2, Name of Layer: second, No Image Associated With This Layer,"
            + " Visibility: true\n"
            + "Number of valid layers created: 2\n"
            + "Current Layer: Name of Layer: first, No Image Associated With This Layer,"
            + " Visibility: true\n"
            + "Layer #1, Name of Layer: first, No Image Associated With This Layer,"
            + " Visibility: true\n"
            + "Layer #2, Name of Layer: second, No Image Associated With This Layer,"
            + " Visibility: true\n"
            + "Number of valid layers created: 2\n"
            + "Current Layer: Name of Layer: second, No Image Associated With This Layer,"
            + " Visibility: true\n"
            + "The process has been quit.\n", ap.toString());
  }

  @Test
  public void testSetCurrentInvisibleLayer() {
    Readable input = new StringReader("create first invisible first current first q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    String[] split = ap.toString().split("\n");

    assertEquals("Current Layer: Name of Layer: first, No Image Associated With This"
            + " Layer, Visibility: false",
        split[split.length - 2]);
  }

  @Test
  public void testSetCurrentNonexistentLayer() {
    Readable input = new StringReader("create first current layer q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    String[] split = ap.toString().split("\n");

    assertEquals("Invalid command! Try again! Current cannot be set!",
        split[split.length - 2]);
  }

  @Test
  public void testLoadTwoImagesOfDifferentDimension() {
    Readable input = new StringReader("create first current first load res/check.ppm create "
        + "second current second load res/puppy.ppm q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    String[] split = ap.toString().split("\n");

    assertEquals("Invalid command! Try again! Image(s) are not the same dimension!",
        split[split.length - 2]);
  }

  @Test
  public void testLoadTwoImagesOfSameDimension() {
    Readable input = new StringReader("create first current first load res/check.ppm create "
        + "second current second load res/Checkerboard1.ppm q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals("Layer #1, Name of Layer: first, No Image Associated With This Layer, "
        + "Visibility: true\n"
        + "Number of valid layers created: 1\n"
        + "Current Layer: Name of Layer: first, No Image Associated With"
        + " This Layer, Visibility: true\n"
        + "Layer #1, Name of Layer: first, No Image Associated With "
        + "This Layer, Visibility: true\n"
        + "Number of valid layers created: 1\n"
        + "Current Layer: Name of Layer: first, No Image Associated "
        + "With This Layer, Visibility: true\n"
        + "Layer #1, Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Number of valid layers created: 1\n"
        + "Current Layer: Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Layer #1, Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Layer #2, Name of Layer: second, No Image Associated With This Layer, Visibility: true\n"
        + "Number of valid layers created: 2\n"
        + "Current Layer: Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Layer #1, Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Layer #2, Name of Layer: second, No Image Associated With This Layer, Visibility: true\n"
        + "Number of valid layers created: 2\n"
        + "Current Layer: Name of Layer: second, No Image Associated With This Layer,"
        + " Visibility: true\n"
        + "Layer #1, Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Layer #2, Name of Layer: second, Image Filename: res/Checkerboard1.ppm, "
        + "Visibility: true\n"
        + "Number of valid layers created: 2\n"
        + "Current Layer: Name of Layer: second, Image Filename: res/Checkerboard1.ppm, "
        + "Visibility: true\n"
        + "The process has been quit.\n", ap.toString());
  }

  @Test
  public void testLoadAllImagesOfDifferentDimensions() {
    Readable input = new StringReader("loadall res/bad/layerInfo.txt q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    String[] split = ap.toString().split("\n");

    assertEquals("Invalid command! Try again! Image(s) are not the same dimension!",
        split[split.length - 2]);
  }

  @Test
  public void testLoadAllFileDoesntExist() {
    Readable input = new StringReader("loadall fakeFile.txt q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    String[] split = ap.toString().split("\n");

    assertEquals("Invalid command! Try again! File does not exist!",
        split[split.length - 2]);
  }


  @Test
  public void testLoadAllBadScript() {
    Readable input = new StringReader("loadall res/badLayerInfo.txt q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    String[] split = ap.toString().split("\n");

    assertEquals("Invalid command! Try again! Invalid text file!",
        split[split.length - 2]);
  }

  @Test
  public void testLoadAll() {
    Readable input = new StringReader("loadall res/correct/layerInfo.txt q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    ILayerModelState modelState = new LayerModelState(model);
    c.processImage();

    assertEquals(modelState.getNumLayers(), 3);
    assertEquals(modelState.getCurrentLayer().getName(), "flower");
    assertTrue(modelState.getCurrentLayer().isVisible());
  }

  @Test
  public void testMakeInvisible() {
    Readable input = new StringReader("create first invisible first q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    String[] split = ap.toString().split("\n");

    assertEquals(
        "Layer #1, Name of Layer: first, No Image Associated With This Layer,"
            + " Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, No Image Associated With This Layer,"
            + " Visibility: true\n"
            + "Layer #1, Name of Layer: first, No Image Associated With This Layer"
            + ", Visibility: false\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, No Image Associated With This Layer"
            + ", Visibility: false\n"
            + "The process has been quit.\n", ap.toString());
  }

  @Test
  public void testMakeInvisibleNonexistentLayer() {
    Readable input = new StringReader("create first invisible layer q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    String[] split = ap.toString().split("\n");

    assertEquals("Invalid command! Try again! Layer does not exist!",
        split[split.length - 2]);
  }

  @Test
  public void testMakeVisible() {
    Readable input = new StringReader("create first invisible first q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    String[] split = ap.toString().split("\n");

    assertEquals(
        "Layer #1, Name of Layer: first, No Image Associated With This Layer"
            + ", Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, No Image Associated With This Layer,"
            + " Visibility: true\n"
            + "Layer #1, Name of Layer: first, No Image Associated With This Layer,"
            + " Visibility: false\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, No Image Associated With This Layer,"
            + " Visibility: false\n"
            + "The process has been quit.\n", ap.toString());
  }

  @Test
  public void testMakeVisibleNonexistentLayer() {
    Readable input = new StringReader("create first visible layer q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    String[] split = ap.toString().split("\n");

    assertEquals("Invalid command! Try again! Layer does not exist!",
        split[split.length - 2]);
  }

  @Test
  public void testColorTransformGrayscaleOnGivenImage() {
    Readable input = new StringReader("colortransform res/check.ppm grayscale q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    String[] split = ap.toString().split("\n");

    assertEquals(
        "Layer #1, Name of Layer: res/check.ppm, Image Filename: res/check.ppm,"
            + " Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current not yet set.\n"
            + "The process has been quit.\n",
        ap.toString());
  }

  @Test
  public void testColorTransformSepiaOnGivenImage() {
    Readable input = new StringReader("colortransform res/check.ppm sepia q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    String[] split = ap.toString().split("\n");

    assertEquals(
        "Layer #1, Name of Layer: res/check.ppm, Image Filename: res/check.ppm,"
            + " Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current not yet set.\n"
            + "The process has been quit.\n",
        ap.toString());
  }

  @Test
  public void testColorTransformNonExistentTransformationOnGivenImage() {
    Readable input = new StringReader("create first colortransform res/check.ppm grayscalee q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals(
        "Layer #1, Name of Layer: first, No Image Associated With This Layer,"
            + " Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, No Image Associated With This Layer, "
            + "Visibility: true\n"
            + "Invalid command! Try again! Invalid color transformation and/or image filename.\n"
            + "The process has been quit.\n", ap.toString());
  }

  @Test
  public void testCreateDefaultCheckerboardImage() {
    Readable input = new StringReader("createdefaultimage 1 1 q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals(
        "Layer #1, Name of Layer: Checkerboard1, Image Filename: "
            + "Checkerboard, Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: Checkerboard1, Image Filename: "
            + "Checkerboard, Visibility: true\n"
            + "The process has been quit.\n", ap.toString());
  }

  @Test
  public void testCreateDefaultCheckerboardImageNegativeSize() {
    Readable input = new StringReader("createdefaultimage -1 1 q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals(
        "Invalid command! Try again! The parameters are invalid to create "
            + "a checkerboard.\n"
            + "The process has been quit.\n", ap.toString());
  }

  @Test
  public void testCreateDefaultCheckerboardImageNegativeNumTiles() {
    Readable input = new StringReader("createdefaultimage 1 -1 q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals(
        "Invalid command! Try again! The parameters are invalid to create "
            + "a checkerboard.\n"
            + "The process has been quit.\n", ap.toString());
  }


  @Test
  public void testCreateCheckerboardImageSpecialColors() {
    Readable input = new StringReader("createdefaultimage 2 1 q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals(
        "Layer #1, Name of Layer: Checkerboard1, Image Filename: Checkerboard, "
            + "Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: Checkerboard1, Image Filename: Checkerboard,"
            + " Visibility: true\n"
            + "The process has been quit.\n", ap.toString());
  }

  @Test
  public void testCreateCheckerboardImageSpecialColorsNegativeNumTilesSize() {
    Readable input = new StringReader("createimage -21 -1 1 2 3 4 3 2 q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals(
        "Invalid command! Try again! The parameters are invalid to "
            + "create a checkerboard.\n"
            + "The process has been quit.\n", ap.toString());
  }

  @Test
  public void testCreateCheckerboardImageSpecialColorsRGBTooHighOrLow() {
    Readable input = new StringReader("createimage -21 -1 -100 100 256 -1 1 2 q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals(
        "Invalid command! Try again! The parameters are invalid "
            + "to create a checkerboard.\n"
            + "The process has been quit.\n", ap.toString());
  }

  @Test
  public void testFilterSharpenOnGivenImage() {
    Readable input = new StringReader("filter res/check.ppm sharpen q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    String[] split = ap.toString().split("\n");

    assertEquals(
        "Layer #1, Name of Layer: res/check.ppm, Image Filename: res/check.ppm,"
            + " Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current not yet set.\n"
            + "The process has been quit.\n",
        ap.toString());
  }

  @Test
  public void testFilterBlurOnGivenImage() {
    Readable input = new StringReader("filter res/check.ppm blur q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    String[] split = ap.toString().split("\n");

    assertEquals(
        "Layer #1, Name of Layer: res/check.ppm, Image Filename: res/check.ppm,"
            + " Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current not yet set.\n"
            + "The process has been quit.\n",
        ap.toString());
  }

  @Test
  public void testFilterNonExistentTransformationOnGivenImage() {
    Readable input = new StringReader("create first filter res/check.ppm bler q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals(
        "Layer #1, Name of Layer: first, No Image Associated With"
            + " This Layer, Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, No Image Associated With"
            + " This Layer, Visibility: true\n"
            + "Invalid command! Try again! Invalid color transformation and/or image filename.\n"
            + "The process has been quit.\n", ap.toString());
  }
}