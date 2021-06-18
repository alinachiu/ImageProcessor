package model.managers;

import java.io.IOException;
import model.AdditionalImageUtils;
import java.io.File;
import model.image.IImage;
import model.image.IPixel;

/**
 * Represents a class that manages the given png input file and returns the file's associated image.
 */
public class InputPNGFileManager implements IOManager {

  private final File file;

  /**
   * Constructs a {@code InputPNGFileManager} object.
   *
   * @param file the given png file to be handled.
   * @throws IllegalArgumentException if the given file is null
   */
  public InputPNGFileManager(File file) throws IllegalArgumentException {
    if (file == null) {
      throw new IllegalArgumentException("Cannot have a null file.");
    }
    this.file = file;
  }

  @Override
  public IImage apply() {
    try {
      IPixel[][] imageGrid = AdditionalImageUtils.readPNGJPEG(file);
      return new Image(imageGrid, file.getName());
    } catch (IOException e) {
      return null;
    }
  }
}
