package view;

// TODO javadocs
public interface IViewListener {
  void handleSaveEvent();
  void handleLoadEvent();
  void makeCurrentEvent();
  void colorTransformCurrentEvent();
  void filterCurrentEvent();
  void createLayerEvent();
  void removeLayerEvent();
  void loadLayerEvent();
  void loadScriptEvent();
  void saveTopmostVisibleLayerEvent();
  void saveAllEvent();
  void makeLayerInvisibleEvent();
}
