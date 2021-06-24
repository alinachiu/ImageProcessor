package model;

import model.layer.ILayer;
import model.layer.ILayerModel;

/**
 * Represents an object that gets the current state of a given layer model.
 */
public class LayerModelState implements ILayerModelState {

  private final ILayerModel model;

  /**
   * Constructs an {@code LayerModelState} object which gets the data from this object's model
   * field.
   *
   * @param model the model to retrieve data from
   */
  public LayerModelState(ILayerModel model) {
    this.model = model;
  }

  @Override
  public int getNumLayers() {
    return this.model.getLayers().size();
  }

  @Override
  public ILayer getCurrentLayer() throws IllegalArgumentException {
    return this.model.getCurrentLayer();
  }

  @Override
  public ILayer getLayer(int index) throws IllegalArgumentException {
    return this.model.getLayers().get(index);
  }
}
