package model.downscaling;

import model.filter.IFilter;
import model.image.IImage;

/**
 * Represents an interface that can perform a basic operation on an image to resize it (change its
 * width, height or both) by making the image smaller.
 */
public interface IResize {

  /**
   * Resizes a given image based on .
   *
   * @param image the given image to be transformed by this {@link IFilter}
   * @param desiredImage the desired image to resize the given image's dimensions to
   * @return the transformed image (a new image that is of the original image with the photo
   * operation applied)
   * @throws IllegalArgumentException if any argument is null.
   */
  IImage apply(IImage image, IImage desiredImage) throws IllegalArgumentException;
}
