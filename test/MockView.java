import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import view.IGUIView;
import view.IViewListener;

/**
 * Represents a flexible mock class for the IGUIView that works with an appendable instead of a
 * pop-up message.
 */
public class MockView implements IGUIView {

  List<IViewListener> listeners;
  Appendable ap;

  /**
   * Constructs an {@code MockView} object which takes in an appendable for increased flexibility.
   *
   * @param ap the given appendable to be used
   */
  public MockView(Appendable ap) {
    this.listeners = new ArrayList<>();
    this.ap = ap;
  }

  @Override
  public void addViewEventListener(IViewListener listener) throws IllegalArgumentException {
    if (listeners.contains(listener)) {
      throw new IllegalArgumentException("Listener has already been added to the view!");
    }
    this.listeners.add(listener);
  }

  @Override
  public void renderMessage(String message) throws IllegalArgumentException {
    try {
      this.ap.append(message);
    } catch (IOException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  @Override
  public String toString() {
    return "Number of listeners in this window: " + this.listeners.size();
  }

  // Note: adding additional public methods for testing as allowed by Prof. Vido's
  // lecture on 6/24/21

  /**
   * Fires the make current event from the listeners in the listeners field.
   *
   * @param nameOfButton the name of the button being clicked
   */
  public void fireMakeCurrentEvent(String nameOfButton) {
    for (IViewListener listener : listeners) {
      listener.handleMakeCurrentEvent(nameOfButton);
    }
  }

  /**
   * Fires the make sepia event from the listeners in the listeners field.
   */
  public void fireSepiaEvent() {
    for (IViewListener listener : listeners) {
      listener.handleSepiaEvent();
    }
  }

  /**
   * Fires the make grayscale event from the listeners in the listeners field.
   */
  public void fireGrayscaleEvent() {
    for (IViewListener listener : listeners) {
      listener.handleGrayscaleEvent();
    }
  }

  /**
   * Fires the make blur event from the listeners in the listeners field.
   */
  public void fireBlurEvent() {
    for (IViewListener listener : listeners) {
      listener.handleBlurEvent();
    }
  }

  /**
   * Fires the make sharpen event from the listeners in the listeners field.
   */
  public void fireSharpenEvent() {
    for (IViewListener listener : listeners) {
      listener.handleSharpenEvent();
    }
  }

  /**
   * Fires the make create layer event from the listeners in the listeners field.
   *
   * @param layerName the layer name to be created
   */
  public void fireCreateLayerEvent(String layerName) {
    for (IViewListener listener : listeners) {
      listener.handleCreateLayerEvent(layerName);
    }
  }

  /**
   * Fires the make remove layer event from the listeners in the listeners field.
   */
  public void fireRemoveLayerEvent() {
    for (IViewListener listener : listeners) {
      listener.handleRemoveLayerEvent();
    }
  }

  /**
   * Fires the make load layer event from the listeners in the listeners field.
   *
   * @param filename the filename to be loaded in
   */
  public void fireLoadLayerEvent(String filename) {
    for (IViewListener listener : listeners) {
      listener.handleLoadLayerEvent(filename);
    }
  }

  /**
   * Fires the make load all event from the listeners in the listeners field.
   *
   * @param txtFilename the filename to be loaded in
   */
  public void fireLoadAllEvent(String txtFilename) {
    for (IViewListener listener : listeners) {
      listener.handleLoadAllEvent(txtFilename);
    }
  }

  /**
   * Fires the make load script from the listeners in the listeners field.
   *
   * @param txtFilename the filename to be loaded in
   */
  public void fireLoadScriptEvent(String txtFilename) {
    for (IViewListener listener : listeners) {
      listener.handleLoadScriptEvent(txtFilename);
    }
  }

  /**
   * Fires the make save topmost visible layer event from the listeners in the listeners field.
   *
   * @param desiredFilename the filename to save the layer as
   */
  public void fireSaveTopmostVisibleLayerEvent(String desiredFilename) {
    for (IViewListener listener : listeners) {
      listener.handleSaveTopmostVisibleLayerEvent(desiredFilename);
    }
  }

  /**
   * Fires the make save all items in the multi-layered image event from the listeners in the
   * listeners field.
   *
   * @param desiredDir the name of the directory to save
   */
  public void fireSaveAllEvent(String desiredDir) {
    for (IViewListener listener : listeners) {
      listener.handleSaveAllEvent(desiredDir);
    }
  }

  /**
   * Fires the make layer invisible event from the listeners in the listeners field.
   */
  public void fireMakeLayerInvisibleEvent() {
    for (IViewListener listener : listeners) {
      listener.handleMakeLayerInvisibleEvent();
    }
  }

  /**
   * Fires the make layer visible event from the listeners in the listeners field.
   */
  public void fireMakeLayerVisibleEvent() {
    for (IViewListener listener : listeners) {
      listener.handleMakeLayerVisibleEvent();
    }
  }

  /**
   * Fires the downscale event from the listeners in the listeners field.
   *
   * @param width  the width to downscale to
   * @param height the height to downscale to
   */
  public void fireDownscaleEvent(int width, int height) {
    for (IViewListener listener : listeners) {
      listener.handleDownscaleEvent(width, height);
    }
  }

  /**
   * Fires the mosaic event from the listeners in the listeners field.
   *
   * @param numSeeds the number of random seeds to add to the mosaic
   */
  public void fireMosaicEvent(int numSeeds) {
    for (IViewListener listener : listeners) {
      listener.handleMosaicEvent(numSeeds);
    }
  }

  /**
   * Fires the checkerboard event with custom colors from the listeners in the listeners field.
   *
   * @param sizeOfTiles size of the checkerboard tiles
   * @param numTiles    number of tiles per row/column
   * @param r1          red value associated with the first custom color
   * @param g1          green value associated with the first custom color
   * @param b1          blue value associated with the first custom color
   * @param r2          red value associated with the second custom color
   * @param g2          green value associated with the second custom color
   * @param b2          blue value associated with the second custom color
   */
  public void fireCheckerboardEvent(int sizeOfTiles, int numTiles, int r1, int g1, int b1, int r2,
      int g2, int b2) {
    for (IViewListener listener : listeners) {
      listener.handleCheckerboardEvent(sizeOfTiles, numTiles, r1, g1, b1, r2, g2, b2);
    }
  }

  /**
   * Fires the checkerboard event with default colors from the listeners in the listeners field.
   *
   * @param sizeOfTiles size of the checkerboard tiles
   * @param numTiles    number of tiles per row/column
   */
  public void fireCheckerboardDefaultColorEvent(int sizeOfTiles, int numTiles) {
    for (IViewListener listener : listeners) {
      listener.handleCheckerboardDefaultColorEvent(sizeOfTiles, numTiles);
    }
  }
}
