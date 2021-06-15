package model.controller;

import model.IModel;
import model.color.Sepia;
import model.image.IImage;

public class SepiaCommand implements IPhotoCommands {

  @Override
  public void go(IImage image, IModel m) {
    m.colorTransformation(image, new Sepia());
  }
}
