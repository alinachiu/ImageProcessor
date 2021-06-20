package controller;

import model.layer.ILayerModel;
import model.managers.IOManager;
import model.managers.InputFilenameManager;
import model.managers.InputJPEGFilenameManager;
import model.managers.InputPNGFilenameManager;

/**
 * A class representing the command to load in a file with a filename for the current layer.
 */
public class LoadSingleCommand implements IPhotoCommands {

  private final String filename;

  /**
   * Constructs the command that will load a file with the given filename into the current layer.
   *
   * @param filename the name of the file containing the image to be loaded.
   * @throws IllegalArgumentException if the given filename is null.
   */
  public LoadSingleCommand(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("Filename is null");
    }
    this.filename = filename;
  }

  @Override
  public void go(ILayerModel m) {
    if (m == null) {
      throw new IllegalArgumentException("Model is null.");
    }
    IOManager manager = this.determineCorrectManager(filename);
    m.loadLayer(manager.apply());
  }

  /**
   * Returns the correct {@link IOManager} based on a given filename.
   *
   * @param filename the given filename
   * @return the correct {@link IOManager} associated with the filename extension
   * @throws IllegalArgumentException if no filename extension is found or filename is null
   */
  private IOManager determineCorrectManager(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("Filename is null");
    }
    String[] splitFilename = filename.split("\\.");
    String fileType = splitFilename[1];

    switch (fileType) {
      case "ppm":
        return new InputFilenameManager(filename);
      case "jpeg":
        return new InputJPEGFilenameManager(filename);
      case "png":
        return new InputPNGFilenameManager(filename);
      default:
        throw new IllegalArgumentException("Cannot save the layer with that file type.");
    }
  }
}
