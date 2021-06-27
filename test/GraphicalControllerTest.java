import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import controller.GraphicalImageProcessingController;
import controller.IImageProcessingController;
import java.io.File;
import model.ILayerModelState;
import model.LayerModelState;
import model.image.Image;
import model.layer.ILayerModel;
import model.layer.Layer;
import model.layer.LayerModel;
import model.managers.InputJPEGPNGFileManager;
import org.junit.Before;
import org.junit.Test;
import view.IGUIView;
import view.IViewListener;
import view.MyWindow;

/**
 * Represents the test class for {@code GraphicalImageProcessingController} to ensure that the
 * object's behavior works as expected and properly performs actions to the given image.
 */
public class GraphicalControllerTest {

  ILayerModel model;
  ILayerModelState modelState;
  IGUIView view;
  Appendable ap;
  IGUIView viewMock;
  IImageProcessingController controller;
  IViewListener controllerListener;

  @Before
  public void initData() {
    this.model = new LayerModel();
    this.modelState = new LayerModelState(this.model);
    this.view = new MyWindow(this.modelState);
    this.ap = new StringBuilder();
    this.viewMock = new MockView(this.ap);
    this.controller = new GraphicalImageProcessingController(this.model, this.view);
    this.controllerListener = new GraphicalImageProcessingController(this.model, this.viewMock);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionNullModel() {
    IImageProcessingController controller = new GraphicalImageProcessingController(null, this.view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionNullView() {
    IImageProcessingController controller = new GraphicalImageProcessingController(this.model,
        null);
  }

  @Test
  public void testProcessImage() {
    // process image should add a new event listener to the view
    assertEquals("Number of listeners in this window: 0", this.view.toString());
    this.controller.processImage();
    assertEquals("Number of listeners in this window: 1", this.view.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testProcessImageAddSameListenerTwice() {
    // process image should add a new event listener to the view
    this.controller.processImage();
    this.controller.processImage();
  }

  @Test
  public void testHandleMakeCurrentEventButtonDoesNotExistErrorMessage() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleMakeCurrentEvent("bajfhasjd");
    assertEquals("Invalid Command: Current cannot be set!", this.ap.toString());
  }

  @Test
  public void testHandleMakeCurrentEventShouldWork() {
    assertEquals("", this.ap.toString());
    assertNull(this.modelState.getCurrentLayer());
    assertEquals(this.modelState.getNumLayers(), 0);
    this.controllerListener.handleCreateLayerEvent("first");
    assertEquals(this.modelState.getCurrentLayer(), new Layer("first"));
    this.controllerListener.handleCreateLayerEvent("second");
    this.controllerListener.handleMakeCurrentEvent("second");
    // should contain nothing since no error message will be displayed
    assertEquals("", this.ap.toString());
    assertEquals(this.modelState.getCurrentLayer(), new Layer("second"));
    assertEquals(this.modelState.getNumLayers(), 2);
  }

  @Test
  public void testHandleSepiaEventNoLayer() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleSepiaEvent();
    assertEquals("Invalid Command: Operation cannot be performed! Image must be "
        + "visible and present.", this.ap.toString());
  }

  @Test
  public void testHandleSepiaEventNoImageAssociatedWithLayer() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleCreateLayerEvent("first");
    this.controllerListener.handleSepiaEvent();
    assertEquals("Invalid Command: Operation cannot be performed!"
        + " Image must be visible and present.", this.ap.toString());
  }

  @Test
  public void testHandleSepiaEventCorrect() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleCreateLayerEvent("first");
    this.controllerListener.handleLoadLayerEvent("res/puppy.ppm");
    assertArrayEquals(this.modelState.getCurrentLayer().getImage().getImage(),
        new Image("res/puppy.ppm").getImage());
    this.controllerListener.handleSepiaEvent();
    assertNotEquals(this.modelState.getCurrentLayer().getImage().getImage(),
        new Image("res/puppy.ppm").getImage());
    assertEquals("", this.ap.toString());
  }

  @Test
  public void testHandleGrayscaleEventNoLayer() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleCreateLayerEvent("first");
    this.controllerListener.handleGrayscaleEvent();
    assertEquals(
        "Invalid Command: Operation cannot be performed!"
            + " Image must be visible and present.",
        this.ap.toString());
  }

  @Test
  public void testHandleGrayscaleEventNoImageAssociatedWithLayer() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleCreateLayerEvent("first");
    this.controllerListener.handleGrayscaleEvent();
    assertEquals(
        "Invalid Command: Operation cannot be performed! "
            + "Image must be visible and present.",
        this.ap.toString());
  }

  @Test
  public void testHandleGrayscaleEventCorrect() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleCreateLayerEvent("first");
    this.controllerListener.handleLoadLayerEvent("res/puppy.ppm");
    assertArrayEquals(this.modelState.getCurrentLayer().getImage().getImage(),
        new Image("res/puppy.ppm").getImage());
    this.controllerListener.handleGrayscaleEvent();
    assertNotEquals(this.modelState.getCurrentLayer().getImage().getImage(),
        new Image("res/puppy.ppm").getImage());
    assertEquals("", this.ap.toString());
  }

  @Test
  public void testHandleBlurEventNoLayer() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleCreateLayerEvent("first");
    this.controllerListener.handleBlurEvent();
    assertEquals(
        "Invalid Command: Operation cannot be performed!"
            + " Image must be visible and present.",
        this.ap.toString());
  }

  @Test
  public void testHandleBlurEventNoImageAssociatedWithLayer() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleCreateLayerEvent("first");
    this.controllerListener.handleBlurEvent();
    assertEquals(
        "Invalid Command: Operation cannot be performed! "
            + "Image must be visible and present.",
        this.ap.toString());
  }

  @Test
  public void testHandleBlurEventCorrect() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleCreateLayerEvent("first");
    this.controllerListener.handleLoadLayerEvent("res/puppy.ppm");
    assertArrayEquals(this.modelState.getCurrentLayer().getImage().getImage(),
        new Image("res/puppy.ppm").getImage());
    this.controllerListener.handleBlurEvent();
    assertNotEquals(this.modelState.getCurrentLayer().getImage().getImage(),
        new Image("res/puppy.ppm").getImage());
    assertEquals("", this.ap.toString());
  }

  @Test
  public void testHandleSharpenEventNoLayer() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleCreateLayerEvent("first");
    this.controllerListener.handleSharpenEvent();
    assertEquals(
        "Invalid Command: Operation cannot be performed! "
            + "Image must be visible and present.",
        this.ap.toString());
  }

  @Test
  public void testHandleSharpenEventNoImageAssociatedWithLayer() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleCreateLayerEvent("first");
    this.controllerListener.handleSharpenEvent();
    assertEquals(
        "Invalid Command: Operation cannot be performed!"
            + " Image must be visible and present.",
        this.ap.toString());
  }

  @Test
  public void testHandleSharpenEventCorrect() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleCreateLayerEvent("first");
    this.controllerListener.handleLoadLayerEvent("res/puppy.ppm");
    assertArrayEquals(this.modelState.getCurrentLayer().getImage().getImage(),
        new Image("res/puppy.ppm").getImage());
    this.controllerListener.handleSharpenEvent();
    assertNotEquals(this.modelState.getCurrentLayer().getImage().getImage(),
        new Image("res/puppy.ppm").getImage());
    assertEquals("", this.ap.toString());
  }

  @Test
  public void handleCreateLayerEventCorrect() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleCreateLayerEvent("first");
    assertEquals(this.modelState.getNumLayers(), 1);
    this.controllerListener.handleCreateLayerEvent("second");
    this.controllerListener.handleCreateLayerEvent("third");
    assertEquals(this.modelState.getNumLayers(), 3);
    assertEquals("", this.ap.toString());
  }

  @Test
  public void handleCreateLayeEventDuplicate() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleCreateLayerEvent("first");
    assertEquals(this.modelState.getNumLayers(), 1);
    this.controllerListener.handleCreateLayerEvent("first");
    assertEquals(this.modelState.getNumLayers(), 1);
    assertEquals("Invalid Command: Layer already exists!", this.ap.toString());
  }

  @Test
  public void handRemoveLayerEventNoLayersExist() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleRemoveLayerEvent();
    assertEquals("Invalid Command: There is no current layer!", this.ap.toString());
  }

  @Test
  public void handleRemoveLayerEventCorrect() {
    assertEquals("", this.ap.toString());
    assertEquals(this.modelState.getNumLayers(), 0);
    this.controllerListener.handleCreateLayerEvent("first");
    assertEquals(this.modelState.getNumLayers(), 1);
    this.controllerListener.handleRemoveLayerEvent();
    assertEquals(this.modelState.getNumLayers(), 0);
    assertEquals("", this.ap.toString());
  }

  @Test
  public void handleLoadLayerEventFileDoesNotExist() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleLoadLayerEvent("fakeFile.jpeg");
    assertEquals("Invalid Command: File does not exist!", this.ap.toString());
  }

  @Test
  public void handleLoadLayerEventFileInvalidFileType() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleLoadLayerEvent("fakeFile");
    assertEquals("Invalid Command: Cannot load the layer with that file type.",
        this.ap.toString());
  }

  @Test
  public void handleLoadLayerEventCorrectFileButNoLayerCreatedYet() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleLoadLayerEvent("res/puppy.ppm");
    assertEquals("Invalid Command: No current layer exists!", this.ap.toString());
  }

  @Test
  public void handleLoadLayerEventCorrect() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleCreateLayerEvent("first");
    assertEquals("Name of Layer: first, No Image Associated With This Layer,"
            + " Visibility: true",
        this.modelState.getCurrentLayer().toString());
    this.controllerListener.handleLoadLayerEvent("res/puppy.ppm");
    assertEquals("Name of Layer: first, Image Filename: res/puppy.ppm,"
            + " Visibility: true",
        this.modelState.getCurrentLayer().toString());
    assertEquals("", this.ap.toString());
  }

  @Test
  public void handleLoadAllEventInvalidTextFile() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleLoadLayerEvent("file.txt");
    assertEquals("Invalid Command: Cannot load the layer with that file type.",
        this.ap.toString());
  }

  @Test
  public void handleLoadAllEventIncorrectFileType() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleLoadLayerEvent("file");
    assertEquals("Invalid Command: Cannot load the layer with that file type.",
        this.ap.toString());
  }

  @Test
  public void handleLoadAllEventBadlyFormattedTextFile() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleLoadLayerEvent("res/badLayerInfo.txt");
    assertEquals("Invalid Command: Cannot load the layer with that file type.",
        this.ap.toString());
  }

  @Test
  public void handleLoadAllEventTextFileWithDifferentSizedImages() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleLoadLayerEvent("file");
    assertEquals("Invalid Command: Cannot load the layer with that file type.", this.ap.toString());
  }

  @Test
  public void handleLoadAllEventCorrect() {
    assertEquals("", this.ap.toString());
    assertEquals(modelState.getNumLayers(), 0);
    this.controllerListener.handleLoadAllEvent("res/correct/layerInfo.txt");
    assertEquals(3, modelState.getNumLayers());
    assertArrayEquals(
        new InputJPEGPNGFileManager(new File("res/correct/flower.jpeg")).apply().getImage(),
        modelState.getTopmostVisibleLayerImage().getImage());
    assertEquals("", this.ap.toString());
  }

  @Test
  public void testHandleLoadScriptMalformedScript() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleLoadScriptEvent("res/badScript.txt");
    assertEquals("Invalid input!\n"
        + "Invalid input!\n"
        + "The process has been quit.\n", this.ap.toString());
  }

  @Test
  public void testHandleLoadScriptWrongFormat() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleLoadScriptEvent("res/puppy.ppm");
    assertEquals("Incorrect file type!", this.ap.toString());
  }

  @Test
  public void testHandleLoadScriptCorrect() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleLoadScriptEvent("res/script1.txt");
    assertEquals("Invalid input!\n"
        + "Invalid command! Try again! Current cannot be set!\n"
        + "Invalid command! Try again! Operation cannot be performed! "
        + "Image must be visible and present.\n"
        + "Invalid command! Try again! Image(s) are not the same dimension!\n"
        + "Invalid command! Try again! Invalid color transformation and/or "
        + "image filename.\n"
        + "Invalid command! Try again! Invalid color transformation and/or image filename.\n"
        + "Invalid command! Try again! Current cannot be set!\n"
        + "Invalid command! Try again! Layer does not exist!\n"
        + "Invalid input!\n"
        + "Invalid input!\n"
        + "Invalid command! Try again! Image(s) are not the same dimension!\n"
        + "Invalid command! Try again! Image(s) are not the same dimension!\n"
        + "Invalid command! Try again! Cannot save the layer with that file type.\n"
        + "Invalid input!\n"
        + "Invalid command! Try again! Operation cannot be performed! "
        + "Image must be visible and present.\n"
        + "Invalid command! Try again! Layer does not exist!\n"
        + "Invalid command! Try again! Current cannot be set!\n"
        + "Invalid command! Try again! Layer does not exist!\n"
        + "Invalid command! Try again! Current cannot be set!\n"
        + "Invalid command! Try again! Invalid color transformation and/or image filename.\n"
        + "Invalid command! Try again! File does not exist!\n"
        + "Invalid command! Try again! Operation cannot be performed! "
        + "Image must be visible and present.\n"
        + "Invalid command! Try again! Operation cannot be performed! "
        + "Image must be visible and present.\n"
        + "Invalid input!\n"
        + "Invalid command! Try again! File does not exist!\n"
        + "Invalid command! Try again! File puppy.ppm not found!\n"
        + "Invalid command! Try again! Current cannot be set!\n"
        + "Invalid command! Try again! Image(s) are not the same dimension!\n"
        + "Invalid command! Try again! An error has occurred.\n"
        + "Invalid command! Try again! An error has occurred.\n"
        + "Invalid command! Try again! File does not exist!\n"
        + "Invalid input!\n"
        + "The process has been quit.\n", ap.toString());
    deleteDirectory(new File("multiLayeredImage"));
    deleteDirectory(new File("multiLayeredImageUpdated"));
  }

  @Test
  public void testHandleSaveTopmostVisibleLayerEventNoLayerExists() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleSaveTopmostVisibleLayerEvent("something");
    assertEquals("Invalid Command: No topmost visible layer exists!", this.ap.toString());
  }

  @Test
  public void testHandleSaveAllEventNothingToSave() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleSaveAllEvent("res/something");
    assertEquals("", this.ap.toString());
    assertTrue(new File("res/something").exists());
    deleteDirectory(new File("res/something"));
  }

  @Test
  public void testMakeInvisibleNoLayersExist() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleMakeLayerInvisibleEvent();
    assertEquals("Invalid Command: There is no current layer!", this.ap.toString());
  }

  @Test
  public void testMakeInvisible() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleCreateLayerEvent("first");
    assertEquals("Name of Layer: first, No Image Associated With This Layer,"
            + " Visibility: true",
        this.modelState.getCurrentLayer().toString());
    this.controllerListener.handleMakeLayerInvisibleEvent();
    assertEquals("Name of Layer: first, No Image Associated With This Layer,"
            + " Visibility: false",
        this.modelState.getCurrentLayer().toString());
    assertEquals("", this.ap.toString());
  }

  @Test
  public void testMakeVisibleNoLayersExist() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleMakeLayerVisibleEvent();
    assertEquals("Invalid Command: There is no current layer!", this.ap.toString());
  }

  @Test
  public void testMakeVisible() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleCreateLayerEvent("first");
    assertEquals("Name of Layer: first, No Image Associated With This Layer,"
            + " Visibility: true",
        this.modelState.getCurrentLayer().toString());
    this.controllerListener.handleMakeLayerInvisibleEvent();
    assertEquals("Name of Layer: first, No Image Associated With This Layer,"
            + " Visibility: false",
        this.modelState.getCurrentLayer().toString());
    this.controllerListener.handleMakeLayerVisibleEvent();
    assertEquals("Name of Layer: first, No Image Associated With This Layer,"
            + " Visibility: true",
        this.modelState.getCurrentLayer().toString());
    assertEquals("", this.ap.toString());
  }

  @Test
  public void testHandleCheckerboardInvalidSizeOfTiles() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleCheckerboardEvent(-1, 2, 3, 2, 2, 2, 2, 2);
    assertEquals("Invalid Command: The parameters are invalid to create a checkerboard.",
        this.ap.toString());
  }

  @Test
  public void testHandleCheckerboardInvalidNumfTiles() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleCheckerboardEvent(1, -1, 3, 2, 2, 2, 2, 2);
    assertEquals("Invalid Command: The parameters are invalid to create a checkerboard.",
        this.ap.toString());
  }

  @Test
  public void testHandleCheckerboardInvalidRGBTooBig() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleCheckerboardEvent(1, 1, 3, 257, 2, 2, 2, 2);
    assertEquals("Invalid Command: Invalid color arguments.", this.ap.toString());
  }

  @Test
  public void testHandleCheckerboardInvalidRGBNegative() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleCheckerboardEvent(1, 1, -2, 257, 256, 232, -2, 2);
    assertEquals("Invalid Command: Invalid color arguments.", this.ap.toString());
  }

  @Test
  public void testHandleCheckerboardEventCorrect() {
    assertEquals("", this.ap.toString());
    assertEquals(this.modelState.getNumLayers(), 0);
    this.controllerListener.handleCheckerboardEvent(1, 1, 2, 2, 2, 2, 2, 2);
    assertEquals(this.modelState.getNumLayers(), 1);
    assertEquals(this.modelState.getLayer(0).toString(),
        "Name of Layer: Checkerboard1, Image Filename: Checkerboard, Visibility: true");
    assertEquals("", this.ap.toString());
  }

  @Test
  public void testHandleCheckerboardDefaultInvalidSizeOfTiles() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleCheckerboardDefaultColorEvent(-1, 2);
    assertEquals("Invalid Command: The parameters are invalid to create a checkerboard.",
        this.ap.toString());
  }

  @Test
  public void testHandleCheckerboardDefaultInvalidNumfTiles() {
    assertEquals("", this.ap.toString());
    this.controllerListener.handleCheckerboardDefaultColorEvent(1, -1);
    assertEquals("Invalid Command: The parameters are invalid to create a checkerboard.",
        this.ap.toString());
  }

  @Test
  public void testHandleCheckerboardDefaultEventCorrect() {
    assertEquals("", this.ap.toString());
    assertEquals(this.modelState.getNumLayers(), 0);
    this.controllerListener.handleCheckerboardDefaultColorEvent(1, 1);
    assertEquals(this.modelState.getNumLayers(), 1);
    assertEquals(this.modelState.getLayer(0).toString(),
        "Name of Layer: Checkerboard1, Image Filename: Checkerboard, Visibility: true");
    assertEquals("", this.ap.toString());
  }

  /**
   * Deletes the directory based on a given file path, returns true if the deletion is successful.
   *
   * @param path path to the directory to be deleted
   * @return true if the deletion is successful, false if it isn't
   */
  public static boolean deleteDirectory(File path) {
    if (path.exists()) {
      File[] files = path.listFiles();
      for (File file : files) {
        if (file.isDirectory()) {
          deleteDirectory(file);
        } else {
          file.delete();
        }
      }
    }
    return (path.delete());
  }
}
