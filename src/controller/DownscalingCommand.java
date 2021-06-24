package controller;

import model.image.IImage;
import model.layer.ILayerModel;

public class DownscalingCommand implements IPhotoCommands {

  private final IImage desiredImage;

  public DownscalingCommand(IImage desiredImage) {
    this.desiredImage = desiredImage;
  }

  @Override
  public void runCommand(ILayerModel m) throws IllegalArgumentException {
    // TODO do this
  }
}
