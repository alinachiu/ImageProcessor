package model.controller;

import model.IModel;
import model.color.Grayscale;
import model.filter.Blur;
import model.image.IImage;

public class GrayscaleCommand implements IPhotoCommands {

  @Override
  public void go(IImage image, IModel m) {
    m.colorTransformation(image, new Grayscale());
  }
}
