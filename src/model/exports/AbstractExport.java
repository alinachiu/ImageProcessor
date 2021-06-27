package model.exports;

import model.image.IImage;

/**
 * An abstract class that exports images as a type determined by the subclasses.
 */
public abstract class AbstractExport implements IExport {

  protected final IImage image;
  protected final String filename;

  /**
   * Constructs a {@code AbstractExport} object with an image and desired name for the file.
   *
   * @param image       the given image to be converted into a file
   * @param desiredName the desired filename for the exported file
   * @throws IllegalArgumentException if the given image is null
   */
  public AbstractExport(IImage image, String desiredName) throws IllegalArgumentException {
    if (image == null || desiredName == null) {
      throw new IllegalArgumentException("Cannot have a null image or name.");
    }
    this.image = image;
    this.filename = desiredName;
  }

}
