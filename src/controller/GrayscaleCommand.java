package controller;

import model.color.Grayscale;
import model.layer.ILayerModel;

/**
 * A class representing the command to make an image with a grayscale filter.
 */
public class GrayscaleCommand implements IPhotoCommands {

  @Override
  public void runCommand(ILayerModel m) {
    if (m == null) {
      throw new IllegalArgumentException("Model is null.");
    }
    m.colorTransformCurrent(new Grayscale());
  }
}
