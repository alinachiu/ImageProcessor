package model.layer;

import java.util.List;
import model.IModel;
import model.color.IColorTransformation;
import model.filter.IFilter;
import model.image.IImage;

/**
 * Represents a set of layers of images that are able to be added or removed.
 */
public interface ILayerModel extends IModel {

  /**
   * Creates a layer in a set of layers. If a layer with the given name already exists it will do
   * nothing. If the layer created is the first layer, it becomes the current
   *
   * @param name the name of the layer being created
   * @throws IllegalArgumentException if the given name is null.
   */
  void createImageLayer(String name) throws IllegalArgumentException;

  /**
   * Represents a function to remove the layer with the given name from the {@link ILayer}.
   *
   * @param name the name of the layer being removed
   * @throws IllegalArgumentException if the given name is null or if no such layer exists
   */
  void removeImageLayer(String name) throws IllegalArgumentException;

  /**
   * Loads the given image into the current layer by setting the layer's image to the given image,
   * as long as the image's dimensions are the same as the previous images or if it is the first
   * image to be placed in a layer.
   *
   * @param image the image to be loaded into the current layer
   * @throws IllegalArgumentException if the given image is null
   */
  void loadLayer(IImage image);

  /**
   * Loads/create a multi-layered image using the given imported layers.
   *
   * @param importedLayers the multi-layers to be instantiated
   * @throws IllegalArgumentException if the given list of layers is null.
   */
  void loadAll(List<ILayer> importedLayers);

  /**
   * Makes the layer that has the given name invisible.
   *
   * @param layerName the name of the layer to make invisible
   * @throws IllegalArgumentException if the given name is null or if the name does not exist within
   *                                  the layers.
   */
  void makeLayerInvisible(String layerName);

  /**
   * Makes the layer that has the given name visible.
   *
   * @param layerName the name of the layer to make visible
   * @throws IllegalArgumentException if the given name is null or if the name does not exist within
   *                                  the layers.
   */
  void makeLayerVisible(String layerName);

  /**
   * Produces the topmost visible image within the layers.
   *
   * @throws IllegalArgumentException if all the layers are invisible/none are visible.
   */
  IImage getTopmostVisibleImage();

  /**
   * Produces the number of layers in the multi-layered image.
   */
  int getNumberOfLayers();

  /**
   * Produces the image within the multi-layer image that is at the given index.
   *
   * @param index the index at which we want the image from the layers
   * @return the image at the given index.
   * @throws IllegalArgumentException if the index is out of bounds (negative or more than the
   *                                  number of layers available)
   */
  IImage getImageAtLayer(int index) throws IllegalArgumentException;

  /**
   * Produces the visibility of the layer at the given index.
   *
   * @param index the index at which we want the visibility from the layers
   * @return boolean that is true if the layer is visible.
   * @throws IllegalArgumentException if the index is out of bounds (negative or more than the
   *                                  number of layers available)
   */
  boolean getVisibilityAtLayer(int index) throws IllegalArgumentException;

  /**
   * Sets the given layer based on its name to be the current layer to perform operations on. If the
   * layer does not exist, the current will not be set to it.
   *
   * @param layerName the name of the layer to be operated on
   * @throws IllegalArgumentException if the layer name is null
   */
  void setCurrent(String layerName) throws IllegalArgumentException;

  /**
   * Applies the given filter onto the image in the current layer, as long as the current image
   * isn't null and invisible.
   *
   * @param filter the type of filter to apply.
   * @throws IllegalArgumentException if the given filter is null
   */
  void filterCurrent(IFilter filter) throws IllegalArgumentException;

  /**
   * Applies the given color transformation onto the image in the current layer, as long as the
   * current image isn't null and invisible.
   *
   * @param colorTransformation the type of colorTransformation to apply.
   * @throws IllegalArgumentException if the given colorTransformation is null
   */
  void colorTransformCurrent(IColorTransformation colorTransformation);

}


