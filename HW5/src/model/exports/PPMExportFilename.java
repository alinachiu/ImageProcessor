package model.exports;

import java.io.FileWriter;
import java.io.IOException;
import model.image.IImage;

/**
 * Represents an object that can export images to the PPM format and name them based on a given file
 * name.
 */
public class PPMExportFilename extends PPMExport implements IExport {

  /**
   * Constructs a {@code PPMExportFilename} object with a default Writer for writing a file.
   *
   * @param image    the given image to be converted into a file
   * @param filename the desired filename for exporting file
   * @throws IllegalArgumentException if the given image is null
   * @throws IOException              if an I/O error occurs
   */
  public PPMExportFilename(IImage image, String filename)
      throws IllegalArgumentException, IOException {
    super(checkNullImage(image), new FileWriter(checkNullDesiredName(filename) + ".ppm"));

  }

  /**
   * Checks if the given image from the constructor is null before calling super on it.
   *
   * @param image the image to be checked.
   * @return the image if it was not null.
   * @throws IllegalArgumentException if the image is null.
   */
  private static IImage checkNullImage(IImage image) {
    if (image == null) {
      throw new IllegalArgumentException("Image is null.");
    }
    return image;
  }

  /**
   * Checks if the given name from the constructor is null before calling super on it.
   *
   * @param desiredName the name to be checked.
   * @return the name if it was not null.
   * @throws IllegalArgumentException if the name is null.
   */
  private static String checkNullDesiredName(String desiredName) {
    if (desiredName == null) {
      throw new IllegalArgumentException("Name is null.");
    }
    return desiredName;
  }


  @Override
  public void export() throws IOException {
    super.export();
  }
}
