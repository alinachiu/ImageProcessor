package view;

import model.image.IImage;

// TODO javadocs
public interface IViewListener {

  void handleMakeCurrentEvent(String nameOfButton);

  void handleSepiaEvent();

  void handleGrayscaleEvent();

  void handleBlurEvent();

  void handleSharpenEvent();

  void handleCreateLayerEvent(String layerName);

  void handleRemoveLayerEvent();

  void handleLoadLayerEvent(String filename);

  void handleLoadScriptEvent(String txtFilename);

  void handleSaveTopmostVisibleLayerEvent();

  void handleSaveAllEvent(String desiredDir);

  void handleMakeLayerInvisibleEvent();

  void handleMakeLayerVisibleEvent();

  void handleDownscaleEvent(IImage image);

  void handleMosaicEvent(int numSeeds);

  void handleCheckerboardEvent(int sizeOfTiles, int numTiles, int r1, int g1, int b1, int r2, int g2, int b2);

  void handleCheckerboardDefaultColorEvent(int sizeOfTiles, int numTiles);
}
