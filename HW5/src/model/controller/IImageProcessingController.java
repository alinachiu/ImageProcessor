package model.controller;

import java.io.IOException;

/**
 * Interface for the image processing controller. An implementation will work with the IModel
 * interface to process an image or a set of multi-layered images based on a set of commands given
 * by the user. The commands can either be inputted in an interactive way (the user types the script
 * manually) or a file-based way (the user specifies a file that contains the script).
 */
public interface IImageProcessingController {

  /**
   * Processes an image based on a set of commands given in an interactive or file-based way. If the
   * commands are not all given on one line (i.e. create layer \n first), the controller will wait
   * for the user's next input.
   *
   * @throws IllegalStateException    if writing to the Appendable object used by it fails or
   *                                  reading from the provided Readable fails
   * @throws IllegalArgumentException if the given file is malformed
   */
  void processImage() throws IllegalArgumentException;
}
