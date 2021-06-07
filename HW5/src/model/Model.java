package model;

import java.util.HashMap;
import java.util.Map;
import model.colorTransformation.IColorTransformation;
import model.filter.IFilter;

public class Model implements IModel {

  protected final Map<String, IImage> images;

  public Model() {
    images = new HashMap<>();
  }

  @Override
  public void addImage(String id, IImage image) {
    
  }

  @Override
  public void removeImage(String id, IImage image) {

  }

  @Override
  public void filter(IImage image, IFilter filter) throws IllegalArgumentException {
    filter.apply(image);
  }

  @Override
  public void colorTransformation(IImage image, IColorTransformation colorTransformation)
      throws IllegalArgumentException {
    colorTransformation.apply(image);
  }

  @Override
  public IImage createImage() {
    return null;
  }
}
