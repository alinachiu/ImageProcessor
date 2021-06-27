package controller;

import model.layer.ILayerModel;

/**
 * A class representing the command to make a layer with a layername invisible.
 */
public class MakeInvisibleCommand implements IPhotoCommands {

  private final String layerName;

  /**
   * Constructs the command that makes the layer with the given name as invisible.
   *
   * @param layerName the name of the layer to make invisible
   * @throws IllegalArgumentException if the given layername is null
   */
  public MakeInvisibleCommand(String layerName) throws IllegalArgumentException {
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
    m.makeLayerInvisible(layerName);
  }
}
