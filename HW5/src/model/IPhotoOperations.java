package model;

import model.image.IImage;

/**
 * Represents an operation that can be done on an {@code IImage} such as filtering an image or
 * applying a color transformation to it.
 */
public interface IPhotoOperations {

  /**
   * Applies a color transformation to a given image based on the matrix of this {@link
   * IPhotoOperations}.
   *
   * @param image the given image to be transformed by this {@link IPhotoOperations}
   * @return the transformed image (a new image that is of the original image with the photo
   * operation applied)
   * @throws IllegalArgumentException if any argument is null.
   */
  IImage apply(IImage image) throws IllegalArgumentException;
}
