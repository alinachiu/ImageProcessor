package model.managers;

import java.io.File;
import java.io.IOException;
import model.AdditionalImageUtils;
import model.image.IImage;
import model.image.IPixel;
import model.image.Image;

/**
 * Represents a class that manages the given jpeg input file and returns the file's associated
 * image.
 */
public class InputJPEGFileManager implements IOManager {

  private final File file;

  /**
   * Constructs a {@code InputJPEGFileManager} object.
   *
   * @param file the given file to be handled.
   * @throws IllegalArgumentException if the given file is null
   */
  public InputJPEGFileManager(File file) throws IllegalArgumentException {
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
