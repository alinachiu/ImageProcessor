package model.managers;

import model.image.IImage;

/**
 * Represents an IOManager which reads and constructs an IImage based on the given inputs.
 */
public interface IOManager {

  /**
   * Reads and constructs an IImage based on the fields of this class.
   *
   * @return the IImage associated with the fields of the class.
   * @throws IllegalArgumentException if the 2D array of pixels of this image is null (the file does
   *                                  not exist/not found).
   */
  IImage apply();
}
