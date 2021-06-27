package controller;

import java.util.List;
import model.layer.ILayer;
import model.layer.ILayerModel;
import model.managers.IOLayerManager;
import model.managers.InputTextFilenameManager;

/**
 * A class representing the command to load in a multi-layered image based on a multi-layered image
 * that is represented in the way the program saves a multi-layered image.
 */
public class LoadAllCommand implements IPhotoCommands {

  private final String filename;

  /**
   * Constructs the command to load a multi-layered image from a folder that has the given name.
   *
   * @param filename the name of the folder containing the multi-layered image.
   * @throws IllegalArgumentException if the given filename is null.
   */
  public LoadAllCommand(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("Filename is null");
    }
    this.filename = filename;
  }

  @Override
  public void runCommand(ILayerModel m) {
    if (m == null) {
      throw new IllegalArgumentException("Model is null.");
    }
    IOLayerManager manager = new InputTextFilenameManager(filename);
    List<ILayer> importedLayers = manager.apply();
    m.loadAll(importedLayers);
  }
}
