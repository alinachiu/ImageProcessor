package view;


public interface IGUIView extends IImageProcessingView {
  
  String getData(String key);

//   void setData(String data);

  void addViewEventListener(IViewListener listener);

//   void requestViewFocus();
}
