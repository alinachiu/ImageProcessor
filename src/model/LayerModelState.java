package model;

import model.layer.ILayer;
import model.layer.ILayerModel;
import model.image.IImage;


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
    try {
      return this.model.getCurrentLayer();
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  @Override
  public IImage getTopmostVisibleLayerImage() throws IllegalArgumentException {
    for (int i = getNumLayers() - 1; i >= 0; i--) {
      if (getLayer(i).isVisible() && getLayer(i).getImage() != null) {
        return getLayer(i).getImage();
      }
    }
    return null;
  }

  @Override
  public ILayer getLayer(int index) throws IllegalArgumentException {
    return this.model.getLayers().get(index);
  }
}
