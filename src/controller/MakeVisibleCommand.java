package controller;

import model.layer.ILayerModel;

/**
 * A class representing the command to make a layer with a layername visible.
 */
public class MakeVisibleCommand implements IPhotoCommands {

  private final String layerName;

  /**
   * Constructs the command that makes the layer with the given name as visible.
   *
   * @param layerName the name of the layer to make visible
   * @throws IllegalArgumentException if the given layername is null
   */
  public MakeVisibleCommand(String layerName) {
    if (layerName == null) {
      throw new IllegalArgumentException("Layer name is null");
    }
    this.layerName = layerName;
  }

  @Override
  public void go(ILayerModel m) {
    if (m == null) {
      throw new IllegalArgumentException("Model is null.");
    }
    m.makeLayerVisible(layerName);
  }
}
