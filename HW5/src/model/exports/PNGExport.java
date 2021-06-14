package model.exports;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import model.image.IImage;

/**
 * Represents a class which manages a given {@code IImage} and exports the Image based on this
 * object's {@link Writer} to PNG format.
 */
public class PNGExport implements IExport {

  private final IImage image;
  private final Writer wr;

  /**
   * Constructs a {@code JPEGExport} object with a default Writer for writing a file.
   *
   * @param image the given image to be converted into a file
   * @throws IllegalArgumentException if the given image is null
   * @throws IOException              if an I/O error occurs
   */
  public PNGExport(IImage image) throws IllegalArgumentException, IOException {
    if (image == null) {
      throw new IllegalArgumentException("Cannot have a null image.");
    }
    this.image = image;
    String[] withoutExtension = image.getFilename().toLowerCase().split(".png");
    this.wr = new FileWriter(withoutExtension[0] + "New" + ".png");
  }

  /**
   * Constructs a {@code JPEGOutputFileManager} object with a given Writer.
   *
   * @param image the given image to be converted into a file
   * @throws IllegalArgumentException if any argument is null
   */
  public PNGExport(IImage image, Writer wr) {
    if (image == null || wr == null) {
      throw new IllegalArgumentException("Cannot have any null arguments.");
    }
    this.image = image;
    this.wr = wr;
  }

  @Override
  public void export() throws IOException {
    // TODO implement next time
  }
}
