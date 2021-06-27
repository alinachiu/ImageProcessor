import controller.IImageProcessingController;
import java.io.IOException;
import view.IGUIView;
import view.IViewListener;

/**
 * Represents a mock {@link IImageProcessingController} which checks to make sure that the wiring
 * between the view and the controller are correct.
 */
public class MockController implements IImageProcessingController, IViewListener {

  private final IGUIView mockView;
  private final Appendable out;

  /**
   * Constructs an {@code MockController} object meant for testing with a mock view and an
   * appendable.
   *
   * @param mockView the given mock view associated with this mock controller
   * @param out      the appendable to be written to
   * @throws IllegalArgumentException if any arguments are null
   */
  public MockController(IGUIView mockView, Appendable out) throws IllegalArgumentException {
    if (mockView == null || out == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    this.mockView = mockView;
    this.out = out;
  }

  @Override
  public void processImage() throws IllegalArgumentException {
    this.mockView.addViewEventListener(this);
  }

  @Override
  public void handleMakeCurrentEvent(String nameOfButton) {
    write("handleMakeCurrentEvent");
  }

  @Override
  public void handleSepiaEvent() {
    write("handleSepiaEvent");
  }

  @Override
  public void handleGrayscaleEvent() {
    write("handleGrayscaleEvent");
  }

  @Override
  public void handleBlurEvent() {
    write("handleBlurEvent");
  }

  @Override
  public void handleSharpenEvent() {
    write("handleSharpenEvent");
  }

  @Override
  public void handleCreateLayerEvent(String layerName) {
    write("handleCreateLayerEvent");
  }

  @Override
  public void handleRemoveLayerEvent() {
    write("handleRemoveLayerEvent");
  }

  @Override
  public void handleLoadLayerEvent(String filename) {
    write("handleLoadLayerEvent");
  }

  @Override
  public void handleLoadAllEvent(String txtFilename) {
    write("handleLoadAllEvent");
  }

  @Override
  public void handleLoadScriptEvent(String txtFilename) {
    write("handleLoadScriptEvent");
  }

  @Override
  public void handleSaveTopmostVisibleLayerEvent(String desiredFilename) {
    write("handleSaveTopmostVisibleLayerEvent");
  }

  @Override
  public void handleSaveAllEvent(String desiredDir) {
    write("handleSaveAllEvent");
  }

  @Override
  public void handleMakeLayerInvisibleEvent() {
    write("handleMakeLayerInvisibleEvent");
  }

  @Override
  public void handleMakeLayerVisibleEvent() {
    write("handleMakeLayerVisibleEvent");
  }

  @Override
  public void handleDownscaleEvent(int width, int height) {
    write("handleDownscaleEvent");
  }

  @Override
  public void handleMosaicEvent(int numSeeds) {
    write("handleMosaicEvent");
  }

  @Override
  public void handleCheckerboardEvent(int sizeOfTiles, int numTiles, int r1, int g1, int b1, int r2,
      int g2, int b2) {
    write("handleCheckerboardEvent");
  }

  @Override
  public void handleCheckerboardDefaultColorEvent(int sizeOfTiles, int numTiles) {
    write("handleCheckerboardDefaultColorEvent");
  }

  /**
   * Tries to append a given string to this appendable, if possible.
   *
   * @param str the given string to be appended
   * @throws IllegalStateException    if writing to the Appendable throws an IOException
   * @throws IllegalArgumentException if the given string is null.
   */
  private void write(String str) throws IllegalStateException {
    if (str == null) {
      throw new IllegalArgumentException("String is null");
    }
    try {
      this.out.append(str);
    } catch (IOException e) {
      throw new IllegalStateException(
          "Writing to the Appendable object used by it fails. Test should fail.");
    }
  }
}
