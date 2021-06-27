package model.exports;

import java.io.IOException;

/**
 * Represents a class that exports the given image to the export constructor into its correct file
 * type.
 */
public interface IExport {

  /**
   * Exports an image into the correct format.
   *
   * @throws IOException if there is a problem with attempting to write the file
   */
  void export() throws IOException;


}
