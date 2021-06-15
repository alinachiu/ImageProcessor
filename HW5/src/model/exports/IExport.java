package model.exports;

import java.io.IOException;

/**
 * Represents a class that model.exports a given image into its correct file type.
 */
public interface IExport {

  /**
   * Exports an image into the correct format.
   *
   * @throws IOException if there is a problem with attempting to write the file
   */
  void export() throws IOException;
}
