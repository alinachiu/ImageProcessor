package controller;

import model.layer.ILayerModel;

/**
 * The class representing the command to set a layer with a name as the current layer.
 */
public class SetCurrentCommand implements IPhotoCommands {

  private final String layerName;

  /**
   * Constructs the command that sets a layer with the given name as the current layer.
   *
   * @param layerName the name of the layer to make current.
   * @throws IllegalArgumentException if the given layer name is null.
   */
  public SetCurrentCommand(String layerName) throws IllegalArgumentException {
    if (layerName == null) {
      throw new IllegalArgumentException("Layer name is null");
    }
    this.layerName = layerName;
  }

  @Override
  public void runCommand(ILayerModel m) {
    if (m == null) {
      throw new IllegalArgumentException("Model is null.");
    }
    m.setCurrent(layerName);
  }
}
