package model.managers;

import java.io.File;
import java.io.IOException;
import utils.AdditionalImageUtils;
import model.image.IImage;
import model.image.IPixel;
import model.image.Image;

/**
 * Represents a class that manages the given jpeg input filename and returns the filename's
 * associated image.
 */
public class InputJPEGPNGFilenameManager implements IOManager {

  private final String filename;

  /**
   * Constructs a {@code InputJPEGPNGFilenameManager} object.
   *
   * @param filename the path of the file
   * @throws IllegalArgumentException if the filename is null
   */
  public InputJPEGPNGFilenameManager(String filename) throws IllegalArgumentException {
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
      return new Image(imageGrid, file.getAbsolutePath());
    } catch (IOException e) {
      return null;
    }
  }
}
