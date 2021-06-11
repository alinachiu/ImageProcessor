package model;

import model.exports.IExport;
import java.io.IOException;
import model.creator.IImageCreator;
import model.image.IImage;
import model.managers.IOManager;
import model.color.IColorTransformation;
import model.filter.IFilter;

/**
 * Represents the model class of an image processor which can filter, perform color transformations,
 * export, and import images.
 */
public final class Model implements IModel {

  @Override
  public IImage filter(IImage image, IFilter filter) throws IllegalArgumentException {
    if (image == null || filter == null) {
      throw new IllegalArgumentException("One or more arguments is null!");
    }

    return filter.apply(image);
  }

  @Override
  public IImage colorTransformation(IImage image, IColorTransformation colorTransformation)
      throws IllegalArgumentException {
    if (image == null || colorTransformation == null) {
      throw new IllegalArgumentException("One or more arguments is null!");
    }

    return colorTransformation.apply(image);
  }

  @Override
  public IImage createImage(IImageCreator creator) throws IllegalArgumentException {
    if (creator == null) {
      throw new IllegalArgumentException("One or more arguments is null!");
    }

    return creator.createImage();
  }

  @Override
  public void exportImage(IExport export)
      throws IllegalArgumentException, IOException {
    if (export == null) {
      throw new IllegalArgumentException("One or more arguments is null!");
    }

    export.export();
  }

  @Override
  public IImage importImage(IOManager input) throws IllegalArgumentException {
    if (input == null) {
      throw new IllegalArgumentException("Argument(s) can't be null!");
    }

    return input.apply();
  }
}
