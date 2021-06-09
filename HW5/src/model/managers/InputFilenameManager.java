package model.managers;

import model.imageRepresentation.IImage;
import model.imageRepresentation.PPMImage;

/**
 * Represents a class that manages the given input file and returns the file's
 * associated image.
 */
public class InputFilenameManager implements IOManager {
  private final String filename;

  /**
   * Constructs a {@code InputFilenameManager} object.
   *
   * @param filename the path of the file
   */
  public InputFilenameManager(String filename) {
    this.filename = filename;
  }

  @Override
  public IImage apply() {
    return new PPMImage(filename);
  }
}
