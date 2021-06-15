package model.managers;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.AdditionalImageUtils;
import model.image.IImage;
import model.image.IPixel;
import model.image.JPEGImage;
import model.image.PNGImage;

/**
 * Represents a class that manages the given png input filename and returns the image associated
 * with it.
 */
public class InputPNGFilenameManager implements IOManager {

  private final String filename;

  /**
   * Constructs a {@code InputPNGFilenameManager} object.
   *
   * @param filename the path of the file
   * @throws IllegalArgumentException if the filename is null
   */
  public InputPNGFilenameManager(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("Filename is null.");
    }
    this.filename = filename;
  }

  @Override
  public IImage apply() {
    try {
      File file = new File(filename);
      IPixel[][] imageGrid = AdditionalImageUtils.readPNGJPEG(file);
      return new PNGImage(imageGrid, file.getName());
    } catch (IOException e) {
      return null;
    }
  }
}
