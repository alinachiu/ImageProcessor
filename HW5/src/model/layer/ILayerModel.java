package model.layer;

import java.io.File;
import java.util.List;
import model.IModel;
import model.IPhotoOperations;

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
   * @param desiredDir the name the user wants to name the directory as
   * @throws IllegalArgumentException if the multi-layered image is empty.
   */
  void saveAll(String desiredDir);

  /**
   * Loads in one or more image(s) that have the given filename(s) to be assigned to the current
   * layer. If the user has not created enough layers before trying to load in images, the method
   * will ignore the extra images (i.e. if there are only two layers but the user attempts to load
   * in four, the first two will load in and the other two will be ignored). If the given image is
   * not the same dimension as the previous layers, the image will not be loaded in.
   *
   * @throws IllegalArgumentException if the given filename is null or cannot find a file with the
   *                                  given filename
   */
  void loadLayer(String... filename);

  /**
   * Loads/Creates a given set of layered images based on the file name of the text file associated
   * with the LIME.
   *
   * @throws IllegalArgumentException if the given multi-layered image to be loaded is null or if
   *                                  cannot find the image associated with the string(s)
   */
  void loadAll(String filename);

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

  /**
   * Applies the given {@code IPhotoOperations} to the current layer. If there is no image
   * associated with the layer or if the layer is invisible, then the operation will not be
   * applied.
   *
   * @param operation the operation to be applied to the current layer
   */
  void applyOperation(IPhotoOperations operation);


}


