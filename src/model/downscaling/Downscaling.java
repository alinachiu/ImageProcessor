package model.downscaling;

import model.image.IImage;

/**
 * Represents a resizing function object that can downsize a given image to be the same dimensions
 * as another desired image.
 */
public class Downscaling implements IResize {

  @Override
  public IImage apply(IImage image, IImage desiredImage) throws IllegalArgumentException {
    if (image == null || desiredImage == null) {
      throw new IllegalArgumentException("Argument(s) cannot be null!");
    }

    return null;
  }
}
