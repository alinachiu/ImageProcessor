package controller;

import model.filter.Sharpening;
import model.layer.ILayerModel;

/**
 * A class representing the command to sharpen an image.
 */
public class SharpenCommand implements IPhotoCommands {

  @Override
  public void runCommand(ILayerModel m) {
    if (m == null) {
      throw new IllegalArgumentException("Model is null.");
    }
    m.filterCurrent(new Sharpening());
  }
}
