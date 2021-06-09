package model.managers;

import model.imageRepresentation.IImage;

/**
 * Represents an IOManager which reads and constructs an IImage based on the given inputs.
 */
public interface IOManager {

  /**
   * Reads and constructs an IImage based on the fields of this class.
   *
   * @return the IImage associated with the fields of the class.
   */
  IImage apply();
}