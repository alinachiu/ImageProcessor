package view;

/**
 * Represents the view listener which handles the interactions that the user has with the view.
 */
public interface IViewListener {

  /**
   * Handles the user interaction by making the layer associated with the layer button the current
   * layer.
   *
   * @param nameOfButton the name of the layer button that the user has clicked
   */
  void handleMakeCurrentEvent(String nameOfButton);

  /**
   * Handles the user interaction (clicking the sepia button) by applying the sepia color
   * transformation to the current layer.
   */
  void handleSepiaEvent();

  /**
   * Handles the user interaction (clicking the grayscale button) by applying the grayscale color
   * transformation to the current layer.
   */
  void handleGrayscaleEvent();

  /**
   * Handles the user interaction (clicking the blur button) by applying the blur filter to the
   * current layer.
   */
  void handleBlurEvent();

  /**
   * Handles the user interaction (clicking the sharpen button) by applying the sharpen filter to
   * the current layer.
   */
  void handleSharpenEvent();

  /**
   * Handles the user interaction (inputting the name of a layer and pressing the create layer
   * button) by creating a new layer based on the given layer name.
   *
   * @param layerName the given layer name
   */
  void handleCreateLayerEvent(String layerName);

  /**
   * Handles the user interaction (pressing the remove layer button) by removing the current layer
   * that the user is looking at.
   */
  void handleRemoveLayerEvent();

  /**
   * Handles the user interaction (pressing the load button) by loading in the image associated with
   * the given filename to the layer that the user is currently looking at (aka the current).
   *
   * @param filename the filename associated with the image the user would like to load in
   */
  void handleLoadLayerEvent(String filename);

  /**
   * Handles the user interaction (pressing the load all button) by loading in the images associated
   * with the given filename for the text file with the layer information, if possible.
   *
   * @param txtFilename the filename associated with the image layer info the user would like to
   *                    load in
   */
  void handleLoadAllEvent(String txtFilename);

  /**
   * Handles the user interaction (pressing the load script button) by loading in a script with text
   * commands associated with the given filename for the text file with the layer information, if
   * possible.
   *
   * @param txtFilename the filename associated with the script commands that the user would like to
   *                    load in
   */
  void handleLoadScriptEvent(String txtFilename);

  /**
   * Handles the user interaction (pressing the save button) by saving the topmost visible layer.
   */
  void handleSaveTopmostVisibleLayerEvent(String desiredFilename);

  /**
   * Handles the user interaction (pressing the save all button) by saving all the layers that the
   * user has created as well as a layer info text file that contains the information about each
   * layer to a directory of a desired name.
   *
   * @param desiredDir the desired directory name
   */
  void handleSaveAllEvent(String desiredDir);

  /**
   * Handles the user interaction (pressing the invisible button) by making the image that the user
   * is currently looking at (aka the current) invisible.
   */
  void handleMakeLayerInvisibleEvent();

  /**
   * Handles the user interaction (pressing the visible button) by making the image that the user is
   * currently looking at (aka the current) visible.
   */
  void handleMakeLayerVisibleEvent();

  /**
   * Handles the user interaction (pressing the downscale button) by downscaling the image to be the
   * size of the given width and height.
   *
   * @param width  the desired width to downscale the image to
   * @param height the desired height to downscale the image to
   */
  void handleDownscaleEvent(int width, int height);

  /**
   * Handles the user interaction (pressing the mosaic button) by applying the mosaic photo effect
   * to the image that the user is currently looking at based on a given number of random seeds to
   * be created.
   *
   * @param numSeeds the given number of random seeds to be created
   */
  void handleMosaicEvent(int numSeeds);

  /**
   * Handles the user interaction (working with and pressing the create checkerboard button) by
   * creating a new checkerboard image, if possible (the image is the same dimension as the other
   * layers), based on the given size of tiles, number of tiles per row/column, and two RGB color
   * values.
   *
   * @param sizeOfTiles the size of the checkerboard tiles
   * @param numTiles    the number of tiles per row/column
   * @param r1          the red value of the first color
   * @param g1          the green value of the first color
   * @param b1          the blue value of the first color
   * @param r2          the red value of the second color
   * @param g2          the green value of the second color
   * @param b2          the blue value of the second color
   */
  void handleCheckerboardEvent(int sizeOfTiles, int numTiles, int r1, int g1, int b1, int r2,
      int g2, int b2);

  /**
   * Handles the user interaction (working with and pressing the create checkerboard button) by
   * creating a new checkerboard image, if possible (the image is the same dimension as the other
   * layers), based on the given size of tiles and number of tiles per row/column, with default
   * colors red and black.
   *
   * @param sizeOfTiles the size of the checkerboard tiles
   * @param numTiles    the number of tiles per row/column
   */
  void handleCheckerboardDefaultColorEvent(int sizeOfTiles, int numTiles);
}
