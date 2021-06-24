package model;

import model.layer.ILayer;
import model.image.IImage;
/**
 * This interface represents different operations that a layer model must support to return various
 * aspects of its state. This interface does not provide any operations to mutate the state of a
 * layer model.
 */
public interface ILayerModelState {

  /**
   * Gets the number of layers in the model associated with this object.
   *
   * @return the number of layers in the model associated with this {@link ILayerModelState}
   */
  int getNumLayers();

  /**
   * Gets the current layer in the model, if it exists
   *
   * @return the current layer
   * @throws IllegalArgumentException if there is no current layer
   */
  ILayer getCurrentLayer() throws IllegalArgumentException;

  /**
   * Gets the topmost visible layer in the model, if it exists
   *
   * @return the current layer
   * @throws IllegalArgumentException if there is no current layer
   */
  IImage getTopmostVisibleLayerImage() throws IllegalArgumentException;

  /**
   * Gets the layer at a given index.
   *
   * @param index the index associated with the desired layer
   * @return the layer at a given index
   * @throws IllegalArgumentException if the given index is invalid
   */
  ILayer getLayer(int index) throws IllegalArgumentException;
}
