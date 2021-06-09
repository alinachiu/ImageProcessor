package model.managers;

import java.io.File;
import model.imageRepresentation.IImage;
import model.ImageUtil;
import model.imageRepresentation.PPMImage;

/**
 * Represents a class that manages the given input file and returns the file's
 * associated image.
 */
public class InputFileManager implements IOManager {
  private final File file;

  /**
   * Constructs a {@code InputFileManager} object.
   * 
   * @param file the given file to be handled.
   * @throws IllegalArgumentException if the given file is null
   */
  public InputFileManager(File file) throws IllegalArgumentException {
    if (file == null) {
      throw new IllegalArgumentException("Cannot have a null file.");
    }
    this.file = file;
  }

  @Override
  public IImage apply() {
    String filename = file.getName();
    IImage image = new PPMImage(ImageUtil.readPPM(filename), filename);
    return image;
  }
}