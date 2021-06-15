package model.controller;

import model.IModel;
import model.filter.Sharpening;
import model.image.IImage;

public class SharpenCommand implements IPhotoCommands {

  @Override
  public void go(IImage image, IModel m) {
    m.filter(image, new Sharpening());
  }
}
