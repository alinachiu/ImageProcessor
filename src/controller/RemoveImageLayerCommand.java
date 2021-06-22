package controller;

import model.layer.ILayerModel;

/**
 * A class representing the command to remove an image layer.
 */
public class RemoveImageLayerCommand implements IPhotoCommands {

  private final String name;

  /**
   * Constructs the command to remove a layer with the given name.
   *
   * @param name the name of the layer to remove.
   * @throws IllegalArgumentException if the given name is null.
   */
  public RemoveImageLayerCommand(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Null name");
    }
    this.name = name;
  }

  @Override
  public void runCommand(ILayerModel m) {
    if (m == null) {
      throw new IllegalArgumentException("Model is null.");
    }
    m.removeImageLayer(this.name);
  }
}
