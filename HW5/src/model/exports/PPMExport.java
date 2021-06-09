package exports;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import model.IImage;
import model.IPixel;

/**
 * Represents a class which manages a given {@code IImage} and creates a file associated with it.
 */
public class PPMExport implements IExport {

  private final IImage image;

  /**
   * Constructs a {@code PPMOutputFileManager} object.
   *
   * @param image the given image to be converted into a file
   * @throws IllegalArgumentException if the given image is null
   */
  public PPMExport(IImage image) {
    if (image == null) {
      throw new IllegalArgumentException("Cannot have a null image.");
    }
    this.image = image;
  }

  @Override
  public void export() throws IOException {
    //TODO DO NOT OVERRIDE
    Writer newFile = new FileWriter(image.getFilename() + ".ppm");
    int height = image.getHeight();
    int width = image.getWidth();
    int maxCCV = 255;

    // write the header
    newFile.write(String.format("P3 %d %d %d\n", width, height, maxCCV));

    IPixel[][] imageGrid = image.getImage();

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        newFile.write(imageGrid[i][j].getRed() + " ");
        newFile.write(imageGrid[i][j].getGreen() + " ");
        newFile.write(imageGrid[i][j].getBlue() + " ");
      }
    }

    newFile.close();
  }
}