package controller;

import model.image.IImage;
import model.layer.ILayerModel;
import view.IGUIView;
import view.IViewListener;

/**
 * The controller for the LayerModel class which allows the reading of a text file to produce
 * different outcomes to layers and the content inside of them (such as their image) and processes
 * the batch-script input from the user, as well as the graphical user interface.
 */
public class GraphicalImageProcessingController implements IImageProcessingController,
    IViewListener {

  private final ILayerModel model;
  private final IGUIView view;

  public GraphicalImageProcessingController(ILayerModel model, IGUIView view)
      throws IllegalArgumentException {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Arguments cannot be null.");
    }

    this.view = view;
    this.model = model;
    this.view.addViewEventListener(this);
  }

  @Override
  public void processImage() {
// TODO figure out
  }

  @Override
  public void handleMakeCurrentEvent(String nameOfButton) {
    new SetCurrentCommand(nameOfButton).runCommand(model);
  }

  @Override
  public void handleSepiaEvent() {
    new SepiaCommand().runCommand(model);
  }

  @Override
  public void handleGrayscaleEvent() {
    new GrayscaleCommand().runCommand(model);
  }

  @Override
  public void handleBlurEvent() {
    new BlurCommand().runCommand(model);
  }

  @Override
  public void handleSharpenEvent() {
    new SharpenCommand().runCommand(model);
  }

  @Override
  public void handleCreateLayerEvent(String layerName) {
    new CreateImageLayerCommand(layerName).runCommand(model);
  }

  @Override
  public void handleRemoveLayerEvent() {
    new RemoveImageLayerCommand(model.getCurrentLayer().getName()).runCommand(model);
  }

  @Override
  public void handleLoadLayerEvent(String filename) {
    new LoadSingleCommand(filename).runCommand(model);
  }

  @Override
  public void handleLoadScriptEvent(String txtFilename) {
    new LoadAllCommand(txtFilename).runCommand(model);
  }

  @Override
  public void handleSaveTopmostVisibleLayerEvent() {
    new SaveSingleCommand().runCommand(model);
  }

  @Override
  public void handleSaveAllEvent(String desiredDir) {
    new SaveAllCommand(desiredDir).runCommand(model);
  }

  @Override
  public void handleMakeLayerInvisibleEvent() {
    new MakeInvisibleCommand(model.getCurrentLayer().getName())
        .runCommand(model); // TODO change this to not only work for current layer
  }

  @Override
  public void handleMakeLayerVisibleEvent() {
    new MakeVisibleCommand(model.getCurrentLayer().getName()).runCommand(model);
  }

  @Override
  public void handleDownscaleEvent(IImage image) {
    new DownscalingCommand(image).runCommand(model);
  }

  @Override
  public void handleMosaicEvent(int numSeeds) {
    new MosaicCommand(numSeeds).runCommand(model);
  }

  @Override
  public void handleCheckerboardEvent(int sizeOfTiles, int numTiles, int r1, int g1, int b1, int r2,
      int g2, int b2) {
    new CreateImageCommand(sizeOfTiles, numTiles, r1, g1, b1, r2, g2, b2).runCommand(model);
  }

  @Override
  public void handleCheckerboardDefaultColorEvent(int sizeOfTiles, int numTiles) {
    new CreateImageDefaultCommand(sizeOfTiles, numTiles).runCommand(model);
  }
}
