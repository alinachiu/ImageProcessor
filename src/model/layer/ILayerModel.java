package model.layer;

import java.util.List;
import model.IModel;
import model.color.IColorTransformation;
import model.downscale.IImageResize;
import model.filter.IFilter;
import model.image.IImage;
import model.mosaic.IPhotoEffect;

/**
 * Represents a set of layers of images that are able to be added or removed.
 */
public interface ILayerModel extends IModel {

  /**
   * Creates a layer in a set of layers. If a layer with the given name already exists it will do
   * nothing. If the layer created is the first layer, it becomes the current
   *
   * @param name the name of the layer being created
   * @throws IllegalArgumentException if the given name is null or if a layer already exists with
   *                                  that name
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
  void loadLayer(IImage image) throws IllegalArgumentException;

  /**
   * Loads/create a multi-layered image using the given imported layers.
   *
   * @param importedLayers the multi-layers to be instantiated
   * @throws IllegalArgumentException if the given list of layers is null.
   */
  void loadAll(List<ILayer> importedLayers) throws IllegalArgumentException;

  /**
   * Makes the layer that has the given name invisible.
   *
   * @param layerName the name of the layer to make invisible
   * @throws IllegalArgumentException if the given name is null or if no such layer exists
   */
  void makeLayerInvisible(String layerName) throws IllegalArgumentException;

  /**
   * Makes the layer that has the given name visible.
   *
   * @param layerName the name of the layer to make visible
   * @throws IllegalArgumentException if the given name is null or if no such layer exists
   */
  void makeLayerVisible(String layerName) throws IllegalArgumentException;

  /**
   * Sets the given layer based on its name to be the current layer to perform operations on.
   *
   * @param layerName the name of the layer to be operated on
   * @throws IllegalArgumentException if the layer name is null or if no such layer exists
   */
  void setCurrent(String layerName) throws IllegalArgumentException;

  /**
   * Applies the given filter onto the image in the current layer, as long as the current image
   * isn't null and invisible.
   *
   * @param filter the type of filter to apply.
   * @throws IllegalArgumentException if the given filter is null or if the current is
   *                                  null/invisible/does not exist, nothing will be done.
   */
  void filterCurrent(IFilter filter) throws IllegalArgumentException;

  /**
   * Applies the given color transformation onto the image in the current layer, as long as the
   * current image isn't null and invisible.
   *
   * @param colorTransformation the type of colorTransformation to apply.
   * @throws IllegalArgumentException if the given colorTransformation is null or If the current is
   *                                  null/invisible/does not exist, nothing will be done.
   */
  void colorTransformCurrent(IColorTransformation colorTransformation)
      throws IllegalArgumentException;

  /**
   * Applies the given mosaic effect onto the image in the current layer, as long as the current
   * image isn't null and invisible.
   *
   * @param mosaicOper the type of mosaic operation to apply.
   * @param numSeeds   the number of seeds to be used for the mosaic operation.
   * @throws IllegalArgumentException if the given mosaicOper is null or If the current is
   *                                  null/invisible/does not exist, nothing will be done.
   */
  void mosaicCurrent(IPhotoEffect mosaicOper, int numSeeds) throws IllegalArgumentException;


  /**
   * Applies the given image resizing (downscaling) onto the image into the current layer, as long
   * as the current image isn't null and invisible.
   *
   * @param downscaleOper the type of image resizing operation to apply.
   * @param width         the desired width for the resizing of the image.
   * @param height        the desired height for the resizing of the image.
   * @throws IllegalArgumentException if the given downscaleOper is null or If the current is
   *                                  null/invisible/does not exist, nothing will be done.
   */
  void downscaleCurrent(IImageResize downscaleOper, int width, int height)
      throws IllegalArgumentException;

  /**
   * Creates a deep copy of the list of layers in this {@code ILayerModel}.
   *
   * @return a deep copy of the list of layers in the model.
   */
  List<ILayer> getLayers();

  /**
   * Returns the current layer, if one exists.
   *
   * @return the current layer
   * @throws IllegalArgumentException if the current layer does not exist
   */
  ILayer getCurrentLayer() throws IllegalArgumentException;

}


