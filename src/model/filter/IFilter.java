package model.filter;


import model.image.IImage;

/**
 * Represents a filter for an image as well as the operations it can perform (such as applying the
 * filter on an image).
 */
public interface IFilter {

  /**
   * Applies a filter to a given image based on the matrix of this {@link IFilter}.
   *
   * @param image the given image to be transformed by this {@link IFilter}
   * @return the transformed image (a new image that is of the original image with the photo
   *         operation applied)
   * @throws IllegalArgumentException if any argument is null.
   */
  IImage apply(IImage image) throws IllegalArgumentException;
}
