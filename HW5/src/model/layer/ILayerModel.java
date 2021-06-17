package model.layer;

import java.io.IOException;
import java.util.List;
import model.IModel;
import model.IPhotoOperations;
import model.image.IImage;

/**
 * Represents a set of layers of images that are able to be added or removed.
 */
public interface ILayerModel extends IModel {

  /**
   * Creates a layer in a set of layers. If a layer with the given name already exists it will do
   * nothing.
   *
   * @param name the name of the layer being created
   * @throws IllegalArgumentException if the given name is null.
   */
  void createImageLayer(String name) throws IllegalArgumentException;

  /**
   * Represents a function to remove the layer with the given name from the {@link ILayers}.
   *
   * @param name the name of the layer being created
   * @throws IllegalArgumentException if the given name is null or if no such layer exists
   */
  void removeImageLayer(String name) throws IllegalArgumentException;


  /**
   * Saves/exports the current layer as the file type that is was loaded as and it will be saved
   * with the given filename
   *
   * @throws IllegalArgumentException if the given filename is null or if the desired file type is
   *                                  invalid/not available.
   */
  void saveLayer(String fileName) throws IllegalArgumentException;

  /**
   * Saves the multi-layered image as a collections of files (one for each layer as a regular image
   * and one text files that stores the locations of all the layer files).
   *
   * @throws IllegalArgumentException if the multi-layered image is empty.
   */
  void saveAll();

  /**
   * Loads in an image that has the given filename to be assigned to the current layer.
   *
   * @throws IllegalArgumentException if the given filename is null or cannot find a file with the
   *                                  given filename
   */
  void loadLayer(String filename);

  /**
   * Loads in a given set of layered images that is saved in the same format.
   *
   * @throws IllegalArgumentException if the given multi-layered image to be loaded is null or if
   *                                  the list is empty. Or if cannot find the layeredImage
   */
  void loadAll(List<ILayer> layeredImage);

  /**
   * Makes the layer that has the given name invisible.
   *
   * @param layerName if the given name is null or if the name does not exist within the layers.
   */
  void makeLayerInvisible(String layerName);

  /**
   * Makes the layer that has the given name visible.
   *
   * @param layerName if the given name is null or if the name does not exist within the layers.
   */
  void makeLayerVisible(String layerName);

  /**
   * Sets the given layer based on its name to be the current layer to perform operations on.
   *
   * @param layerName the name of the layer to be operated on
   * @throws IllegalArgumentException if the layer name is null or if the layer does not exist
   */
  void setCurrent(String layerName);

  // TODO write javadoc
  void applyOperation(IPhotoOperations operation);


}


