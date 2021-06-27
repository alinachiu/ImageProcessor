package model.color;

import model.image.IImage;

/**
 * This interface constructs methods to be used on applying a color transformation on an image.
 */
public interface IColorTransformation {

  /**
   * Applies a color transformation to a given image based on the matrix of this {@link
   * IColorTransformation}.
   *
   * @param image the given image to be transformed by this {@link IColorTransformation}
   * @return the transformed image (a new image that is of the original image with the photo
   *         operation applied)
   * @throws IllegalArgumentException if any argument is null.
   */
  IImage apply(IImage image) throws IllegalArgumentException;
}
