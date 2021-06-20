package controller;

import model.color.Sepia;
import model.layer.ILayerModel;

/**
 * A class representing the command to apply sepia on an image.
 */
public class SepiaCommand implements IPhotoCommands {

  @Override
  public void go(ILayerModel m) {
    if (m == null) {
      throw new IllegalArgumentException("Model is null.");
    }
    m.colorTransformCurrent(new Sepia());
  }
}
