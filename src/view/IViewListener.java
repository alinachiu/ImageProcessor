package view;

// TODO javadocs
public interface IViewListener {
  void handleLoadEvent();
  void handleMakeCurrentEvent();
  void handleSepiaEvent();
  void handleGrayscaleEvent();
  void handleBlurEvent();
  void handleSharpenEvent();
  void handleCreateLayerEvent();
  void handleRemoveLayerEvent();
  void handleLoadLayerEvent();
  void handleLoadAllEvent();
  void handleLoadScriptEvent();
  void handleSaveTopmostVisibleLayerEvent();
  void handleSaveAllEvent();
  void handleMakeLayerInvisibleEvent();
  void handleMakeLayerVisibleEvent();
  void handleDownscaleEvent();
  void handleMosaicEvent();
  void handleCheckerboardEvent();
}
