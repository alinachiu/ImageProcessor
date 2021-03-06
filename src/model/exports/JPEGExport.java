package model.exports;

import java.io.IOException;
import java.io.Writer;
import model.image.IImage;
import utils.AdditionalImageUtils;

/**
 * Represents a class which manages a given {@code IImage} and exports the Image based on this
 * object's {@link Writer} to JPEG format.
 */
public class JPEGExport extends AbstractExport {

  /**
   * Constructs a {@code JPEGExport} object with a default name for the new file.
   *
   * @param image the given image to be converted into a file
   * @throws IllegalArgumentException if the given image is null
   */
  public JPEGExport(IImage image) throws IllegalArgumentException, IOException {
    super(checkNullImage(image), image.getFilename().toLowerCase().split(".jpeg")[0] + "New");
  }


  /**
   * Constructs a {@code JPEGExport} object with an image and a desired name for the new file.
   *
   * @param image       the given image to be converted into a file
   * @param desiredName the desired filename for the exported file
   * @throws IllegalArgumentException if the given image is null
   * @throws IOException              if an I/O error occurs
   */
  public JPEGExport(IImage image, String desiredName) throws IllegalArgumentException, IOException {
    super(checkNullImage(image), checkNullDesiredName(desiredName));
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
    AdditionalImageUtils.exportWithType(this.image, this.filename, "jpeg");
  }
}