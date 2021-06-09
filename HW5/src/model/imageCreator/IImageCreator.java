package model.imageCreator;

import model.imageRepresentation.IImage;

/**
 * This interface represents the different operations that an ImageCreator can perform.
 */
public interface IImageCreator {

  /**
   * Creates an image based on the given fields in this IImageCreator.
   *
   * @return the new image based on the given fields
   */

  IImage createImage();
}
