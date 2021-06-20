import static org.junit.Assert.assertEquals;

import controller.IImageProcessingController;
import controller.SimpleIImageProcessingController;
import java.io.File;
import java.io.StringReader;
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
    Readable input = new StringReader("create first load res/Checkerboard.ppm blur q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals(
        "Layer #1, Name of Layer: first, No Image Associated With This Layer, Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, No Image Associated With This Layer, Visibility: true\n"
            + "Layer #1, Name of Layer: first, Image Filename: res/Checkerboard.ppm, Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, Image Filename: res/Checkerboard.ppm, Visibility: true\n"
            + "Layer #1, Name of Layer: first, Image Filename: res/Checkerboard.ppm, Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, Image Filename: res/Checkerboard.ppm, Visibility: true\n"
            + "The process has been quit.\n", ap.toString());
  }

  @Test
  public void testSharpenOneLayerCreated() {
    Readable input = new StringReader("create first load res/Checkerboard.ppm sharpen q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals(
        "Layer #1, Name of Layer: first, No Image Associated With This Layer, Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, No Image Associated With This Layer, Visibility: true\n"
            + "Layer #1, Name of Layer: first, Image Filename: res/Checkerboard.ppm, Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, Image Filename: res/Checkerboard.ppm, Visibility: true\n"
            + "Layer #1, Name of Layer: first, Image Filename: res/Checkerboard.ppm, Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, Image Filename: res/Checkerboard.ppm, Visibility: true\n"
            + "The process has been quit.\n", ap.toString());
  }

  @Test
  public void testSepiaOneLayerCreated() {
    Readable input = new StringReader("create first load res/Checkerboard.ppm sepia q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals(
        "Layer #1, Name of Layer: first, No Image Associated With This Layer, Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, No Image Associated With This Layer, Visibility: true\n"
            + "Layer #1, Name of Layer: first, Image Filename: res/Checkerboard.ppm, Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, Image Filename: res/Checkerboard.ppm, Visibility: true\n"
            + "Layer #1, Name of Layer: first, Image Filename: res/Checkerboard.ppm, Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, Image Filename: res/Checkerboard.ppm, Visibility: true\n"
            + "The process has been quit.\n", ap.toString());
  }

  @Test
  public void testGrayscaleOneLayerCreated() {
    Readable input = new StringReader("create first load res/Checkerboard.ppm grayscale q");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals(
        "Layer #1, Name of Layer: first, No Image Associated With This Layer, Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, No Image Associated With This Layer, Visibility: true\n"
            + "Layer #1, Name of Layer: first, Image Filename: res/Checkerboard.ppm, Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, Image Filename: res/Checkerboard.ppm, Visibility: true\n"
            + "Layer #1, Name of Layer: first, Image Filename: res/Checkerboard.ppm, Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, Image Filename: res/Checkerboard.ppm, Visibility: true\n"
            + "The process has been quit.\n", ap.toString());
  }

  @Test
  public void testScriptAsReadable() {
    Readable input = new StringReader("hello\n"
        + "create first\n"
        + "remove first\n"
        + "current first\n"
        + "sepia\n"
        + "create first\n"
        + "current first\n"
        + "create second\n"
        + "create third\n"
        + "createimage 4 3 10 10 10 20 20 20\n"
        + "create fourth\n"
        + "load res/check.ppm\n"
        + "colortransform res/check.ppm sepie\n"
        + "colortransform res/check.ppm sepia\n"
        + "colortransform res/check.pp grayscale\n"
        + "remove second\n"
        + "current second\n"
        + "overload res/puppy.ppm\n"
        + "load res/puppy.ppm\n"
        + "loadall res/correct/layerInfo.txt\n"
        + "blur\n"
        + "save name\n"
        + "saveall multiLayeredImage\n"
        + "current third\n"
        + "grayscale\n"
        + "invisible res/correct/flower.jpeg\n"
        + "current res/correct/flower.jpeg\n"
        + "visible res/correct/flower.jpeg\n"
        + "current res/correct/flower.jpeg\n"
        + "filter res/correct/flower.png sepia\n"
        + "filter res/correct/flower.png blur\n"
        + "filter res/correct/Checkerboard.ppm sharpen\n"
        + "sharpen\n"
        + "sepia\n"
        + "bokeh\n"
        + "invisible third\n"
        + "current fakeLayer\n"
        + "createdefaultimage 4 3\n"
        + "saveall multiLayeredImage\n"
        + "saveall multiLayeredImageUpdated\n"
        + "quite\n"
        + "quit");
    Appendable ap = new StringBuilder();
    IImageProcessingController c = new SimpleIImageProcessingController(model, input, ap);
    c.processImage();

    assertEquals("Invalid input!\n"
        + "Layer #1, Name of Layer: first, No Image Associated With This Layer, Visibility: true\n"
        + "Number of valid layers created: 1\n"
        + "Current Layer: Name of Layer: first, No Image Associated With This Layer, "
        + "Visibility: true\n"
        + "Number of valid layers created: 0\n"
        + "Current not yet set.\n"
        + "Invalid command! Try again! Current cannot be set!\n"
        + "Invalid command! Try again! Operation cannot be performed!\n"
        + "Layer #1, Name of Layer: first, No Image Associated With This Layer, Visibility: true\n"
        + "Number of valid layers created: 1\n"
        + "Current Layer: Name of Layer: first, No Image Associated With This Layer, "
        + "Visibility: true\n"
        + "Layer #1, Name of Layer: first, No Image Associated With This Layer, Visibility: true\n"
        + "Number of valid layers created: 1\n"
        + "Current Layer: Name of Layer: first, No Image Associated With This Layer, "
        + "Visibility: true\n"
        + "Layer #1, Name of Layer: first, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #2, Name of Layer: second, No Image Associated With This Layer, Visibility: true\n"
        + "Number of valid layers created: 2\n"
        + "Current Layer: Name of Layer: first, No Image Associated With This Layer, "
        + "Visibility: true\n"
        + "Layer #1, Name of Layer: first, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #2, Name of Layer: second, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #3, Name of Layer: third, No Image Associated With This Layer, Visibility: true\n"
        + "Number of valid layers created: 3\n"
        + "Current Layer: Name of Layer: first, No Image Associated With This Layer, "
        + "Visibility: true\n"
        + "Layer #1, Name of Layer: first, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #2, Name of Layer: second, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #3, Name of Layer: third, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #4, Name of Layer: Checkerboard, Image Filename: Checkerboard, Visibility: true\n"
        + "Number of valid layers created: 4\n"
        + "Current Layer: Name of Layer: first, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #1, Name of Layer: first, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #2, Name of Layer: second, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #3, Name of Layer: third, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #4, Name of Layer: Checkerboard, Image Filename: Checkerboard, Visibility: true\n"
        + "Layer #5, Name of Layer: fourth, No Image Associated With This Layer, Visibility: true\n"
        + "Number of valid layers created: 5\n"
        + "Current Layer: Name of Layer: first, No Image Associated With This Layer, "
        + "Visibility: true\n"
        + "Layer #1, Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Layer #2, Name of Layer: second, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #3, Name of Layer: third, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #4, Name of Layer: Checkerboard, Image Filename: Checkerboard, Visibility: true\n"
        + "Layer #5, Name of Layer: fourth, No Image Associated With This Layer, "
        + "Visibility: true\n"
        + "Number of valid layers created: 5\n"
        + "Current Layer: Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Layer #1, Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Layer #2, Name of Layer: second, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #3, Name of Layer: third, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #4, Name of Layer: Checkerboard, Image Filename: Checkerboard, Visibility: true\n"
        + "Layer #5, Name of Layer: fourth, No Image Associated With This Layer, "
        + "Visibility: true\n"
        + "Number of valid layers created: 5\n"
        + "Current Layer: Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Layer #1, Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Layer #2, Name of Layer: second, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #3, Name of Layer: third, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #4, Name of Layer: Checkerboard, Image Filename: Checkerboard, Visibility: true\n"
        + "Layer #5, Name of Layer: fourth, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #6, Name of Layer: res/check.ppm, Image Filename: res/check.ppm,"
        + " Visibility: true\n"
        + "Number of valid layers created: 6\n"
        + "Current Layer: Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Layer #1, Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Layer #2, Name of Layer: second, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #3, Name of Layer: third, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #4, Name of Layer: Checkerboard, Image Filename: Checkerboard, Visibility: true\n"
        + "Layer #5, Name of Layer: fourth, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #6, Name of Layer: res/check.ppm, Image Filename: res/check.ppm,"
        + " Visibility: true\n"
        + "Number of valid layers created: 6\n"
        + "Current Layer: Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Layer #1, Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Layer #2, Name of Layer: third, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #3, Name of Layer: Checkerboard, Image Filename: Checkerboard, Visibility: true\n"
        + "Layer #4, Name of Layer: fourth, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #5, Name of Layer: res/check.ppm, Image Filename: res/check.ppm, "
        + "Visibility: true\n"
        + "Number of valid layers created: 5\n"
        + "Current Layer: Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Invalid command! Try again! Current cannot be set!\n"
        + "Invalid input!\n"
        + "Invalid input!\n"
        + "Invalid command! Try again! Image(s) are not the same dimension!\n"
        + "Invalid command! Try again! Image(s) are not the same dimension!\n"
        + "Layer #1, Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Layer #2, Name of Layer: third, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #3, Name of Layer: Checkerboard, Image Filename: Checkerboard, Visibility: true\n"
        + "Layer #4, Name of Layer: fourth, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #5, Name of Layer: res/check.ppm, Image Filename: res/check.ppm,"
        + " Visibility: true\n"
        + "Number of valid layers created: 5\n"
        + "Current Layer: Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Layer #1, Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Layer #2, Name of Layer: third, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #3, Name of Layer: Checkerboard, Image Filename: Checkerboard, Visibility: true\n"
        + "Layer #4, Name of Layer: fourth, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #5, Name of Layer: res/check.ppm, Image Filename: res/check.ppm, "
        + "Visibility: true\n"
        + "Number of valid layers created: 5\n"
        + "Current Layer: Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Layer #1, Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Layer #2, Name of Layer: third, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #3, Name of Layer: Checkerboard, Image Filename: Checkerboard, Visibility: true\n"
        + "Layer #4, Name of Layer: fourth, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #5, Name of Layer: res/check.ppm, Image Filename: res/check.ppm, "
        + "Visibility: true\n"
        + "Number of valid layers created: 5\n"
        + "Current Layer: Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Layer #1, Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Layer #2, Name of Layer: third, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #3, Name of Layer: Checkerboard, Image Filename: Checkerboard, Visibility: true\n"
        + "Layer #4, Name of Layer: fourth, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #5, Name of Layer: res/check.ppm, Image Filename: res/check.ppm, "
        + "Visibility: true\n"
        + "Number of valid layers created: 5\n"
        + "Current Layer: Name of Layer: third, No Image Associated With This Layer,"
        + " Visibility: true\n"
        + "Invalid command! Try again! Operation cannot be performed!\n"
        + "Invalid command! Try again! Layer does not exist!\n"
        + "Invalid command! Try again! Current cannot be set!\n"
        + "Invalid command! Try again! Layer does not exist!\n"
        + "Invalid command! Try again! Current cannot be set!\n"
        + "Layer #1, Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Layer #2, Name of Layer: third, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #3, Name of Layer: Checkerboard, Image Filename: Checkerboard, Visibility: true\n"
        + "Layer #4, Name of Layer: fourth, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #5, Name of Layer: res/check.ppm, Image Filename: res/check.ppm, "
        + "Visibility: true\n"
        + "Number of valid layers created: 5\n"
        + "Current Layer: Name of Layer: third, No Image Associated With This Layer, "
        + "Visibility: true\n"
        + "Layer #1, Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Layer #2, Name of Layer: third, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #3, Name of Layer: Checkerboard, Image Filename: Checkerboard, Visibility: true\n"
        + "Layer #4, Name of Layer: fourth, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #5, Name of Layer: res/check.ppm, Image Filename: res/check.ppm,"
        + " Visibility: true\n"
        + "Layer #6, Name of Layer: res/correct/flower.png, Image Filename: res/correct/flower.png,"
        + " Visibility: true\n"
        + "Number of valid layers created: 6\n"
        + "Current Layer: Name of Layer: third, No Image Associated With This Layer, "
        + "Visibility: true\n"
        + "Invalid command! Try again! File res/correct/Checkerboard.ppm not found!\n"
        + "Invalid command! Try again! Operation cannot be performed!\n"
        + "Invalid command! Try again! Operation cannot be performed!\n"
        + "Invalid input!\n"
        + "Layer #1, Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Layer #2, Name of Layer: third, No Image Associated With This Layer, Visibility: false\n"
        + "Layer #3, Name of Layer: Checkerboard, Image Filename: Checkerboard, Visibility: true\n"
        + "Layer #4, Name of Layer: fourth, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #5, Name of Layer: res/check.ppm, Image Filename: res/check.ppm, "
        + "Visibility: true\n"
        + "Layer #6, Name of Layer: res/correct/flower.png, Image Filename: res/correct/flower.png,"
        + " Visibility: true\n"
        + "Number of valid layers created: 6\n"
        + "Current Layer: Name of Layer: third, No Image Associated With This Layer, "
        + "Visibility: false\n"
        + "Invalid command! Try again! Current cannot be set!\n"
        + "Layer #1, Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Layer #2, Name of Layer: third, No Image Associated With This Layer, Visibility: false\n"
        + "Layer #3, Name of Layer: Checkerboard, Image Filename: Checkerboard, Visibility: true\n"
        + "Layer #4, Name of Layer: fourth, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #5, Name of Layer: res/check.ppm, Image Filename: res/check.ppm, "
        + "Visibility: true\n"
        + "Layer #6, Name of Layer: res/correct/flower.png, Image Filename: "
        + "res/correct/flower.png, Visibility: true\n"
        + "Layer #7, Name of Layer: Checkerboard, Image Filename: Checkerboard, Visibility: true\n"
        + "Number of valid layers created: 7\n"
        + "Current Layer: Name of Layer: third, No Image Associated With This Layer, "
        + "Visibility: false\n"
        + "Layer #1, Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Layer #2, Name of Layer: third, No Image Associated With This Layer, Visibility: false\n"
        + "Layer #3, Name of Layer: Checkerboard, Image Filename: Checkerboard, Visibilit"
        + "y: true\n"
        + "Layer #4, Name of Layer: fourth, No Image Associated With This Layer, Visibilit"
        + "y: true\n"
        + "Layer #5, Name of Layer: res/check.ppm, Image Filename: res/check.ppm, Visibili"
        + "ty: true\n"
        + "Layer #6, Name of Layer: res/correct/flower.png, Image Filename: res/correct/flowe"
        + "r.png, Visibility: true\n"
        + "Layer #7, Name of Layer: Checkerboard, Image Filename: Checkerboard, Visibility: true\n"
        + "Number of valid layers created: 7\n"
        + "Current Layer: Name of Layer: third, No Image Associated With This Layer, Visibil"
        + "ity: false\n"
        + "Layer #1, Name of Layer: first, Image Filename: res/check.ppm, Visibility: true\n"
        + "Layer #2, Name of Layer: third, No Image Associated With This Layer, Visibility: false\n"
        + "Layer #3, Name of Layer: Checkerboard, Image Filename: Checkerboard, Visibility: true\n"
        + "Layer #4, Name of Layer: fourth, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #5, Name of Layer: res/check.ppm, Image Filename: res/check.ppm, Visibilit"
        + "y: true\n"
        + "Layer #6, Name of Layer: res/correct/flower.png, Image Filename: "
        + "res/correct/flower.png, Visibility: true\n"
        + "Layer #7, Name of Layer: Checkerboard, Image Filename: Checkerboard, Visibility: true\n"
        + "Number of valid layers created: 7\n"
        + "Current Layer: Name of Layer: third, No Image Associated With This Layer, "
        + "Visibility: false\n"
        + "Invalid input!\n"
        + "The process has been quit.\n", ap.toString());
  }

  /*
  TODO test all possible combinations of commands including bad inputs to the controller
   DEAL WITH FILE INPUT CONSTRUCTOR LATER
   */
}