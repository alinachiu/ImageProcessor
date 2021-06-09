package model.filter;

import model.imageRepresentation.IImage;

/**
 * Represents a filter for an image as well as the operations it can perform (such as applying the
 * filter on an image).
 */
public interface IFilter {

  /**
   * Applies this filter to a given image's color channels using this filter's kernel.
   *
   * @param image the given image to be filtered
   * @return a filtered copy of this image
   * @throws IllegalArgumentException if the image argument is null
   */
  IImage apply(IImage image) throws IllegalArgumentException;
}
