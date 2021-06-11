package model.exports;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import model.image.IImage;
import model.image.IPixel;

/**
 * Represents a class which manages a given {@code IImage} and exports the Image based on this
 * object's {@link Writer}.
 */
public class PPMExport implements IExport {

  private final IImage image;
  private final Writer wr;

  /**
   * Constructs a {@code PPMOutputFileManager} object with a default Writer for writing a file.
   *
   * @param image the given image to be converted into a file
   * @throws IllegalArgumentException if the given image is null
   * @throws IOException              if an I/O error occurs
   */
  public PPMExport(IImage image) throws IllegalArgumentException, IOException {
    if (image == null) {
      throw new IllegalArgumentException("Cannot have a null image.");
    }
    this.image = image;
    String[] withoutExtension = image.getFilename().toLowerCase().split(".ppm");
    this.wr = new FileWriter(withoutExtension[0] + "New" + ".ppm");
  }

  /**
   * Constructs a {@code PPMOutputFileManager} object with a given Writer.
   *
   * @param image the given image to be converted into a file
   * @throws IllegalArgumentException if any argument is null
   */
  public PPMExport(IImage image, Writer wr) {
    if (image == null || wr == null) {
      throw new IllegalArgumentException("Cannot have any null arguments.");
    }
    this.image = image;
    this.wr = wr;
  }

  @Override
  public void export() throws IOException {
    int height = image.getHeight();
    int width = image.getWidth();

    // write the header
    this.wr.write(String.format("P3 %d %d %d\n", width, height, 255));

    IPixel[][] imageGrid = image.getImage();

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        this.wr.write(imageGrid[i][j].toString() + " ");
      }
    }

    this.wr.close();
  }
}