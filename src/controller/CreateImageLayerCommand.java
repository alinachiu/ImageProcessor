package controller;

import model.layer.ILayerModel;

/**
 * A class representing the command to create an image layer.
 */
public class CreateImageLayerCommand implements IPhotoCommands {

  private final String name;

  /**
   * Constructor representing the command to create a layer with the given name.
   *
   * @param name the name of the layer to be created.
   * @throws IllegalArgumentException if the given name is null.
   */
  public CreateImageLayerCommand(String name) {
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
    m.createImageLayer(this.name);
  }
}
