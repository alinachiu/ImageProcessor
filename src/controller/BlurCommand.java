package controller;

import model.filter.Blur;
import model.layer.ILayerModel;

/**
 * A class representing the command to blur an image.
 */
public class BlurCommand implements IPhotoCommands {

  @Override
  public void runCommand(ILayerModel m) {
    if (m == null) {
      throw new IllegalArgumentException("Model is null.");
    }
    m.filterCurrent(new Blur());
  }
}
