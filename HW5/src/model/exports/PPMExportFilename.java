package model.exports;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import model.image.IImage;

/**
 * Represents an object that can export images to the PPM format and name them based on a given file
 * name.
 */
public class PPMExportFilename implements IExport {

  private final IImage image;
  private final Writer wr;
  private final IExport delegate;

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
    if (image == null) {
      throw new IllegalArgumentException("Cannot have a null image.");
    }
    this.image = image;
    this.delegate = new PPMExport(image);
    this.wr = new FileWriter(filename);
  }

  /**
   * Constructs a {@code PPMOutputFileManager} object with a given Writer.
   *
   * @param image the given image to be converted into a file
   * @throws IllegalArgumentException if any argument is null
   * @throws IOException if any I/O error(s) occur when creating the delegate
   */
  public PPMExportFilename(IImage image, Writer wr) throws IOException {
    if (image == null || wr == null) {
      throw new IllegalArgumentException("Cannot have any null arguments.");
    }
    this.image = image;
    this.delegate = new PPMExport(image);
    this.wr = wr;
  }

  @Override
  public void export() throws IOException {
    this.delegate.export();
  }
}
