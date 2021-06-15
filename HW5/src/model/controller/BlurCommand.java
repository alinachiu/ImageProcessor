package model.controller;

import model.IModel;
import model.filter.Blur;
import model.image.IImage;

// TODO javadoc
public class BlurCommand implements IPhotoCommands {
  @Override
  public void go(IImage image, IModel m) {
    m.filter(image, new Blur());
  }
}
