package model.managers;

import model.image.IImage;
import model.image.Image;

/**
 * Represents a class that manages the given input file and returns the file's associated image.
 */
public class InputFilenameManager implements IOManager {

  private final String filename;

  /**
   * Constructs a {@code InputFilenameManager} object.
   *
   * @param filename the path of the file
   * @throws IllegalArgumentException if the filename is null
   */
  public InputFilenameManager(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("Filename is null.");
    }
    this.filename = filename;
  }

  @Override
  public IImage apply() {
    return new Image(filename);
  }
}
