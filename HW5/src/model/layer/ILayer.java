package model.layer;

import java.util.List;
import model.IPhotoOperations;
import model.image.IImage;

/**
 * Represents a set of layers of images that are able to be added or removed.
 */
public interface ILayer {

  /**
   * Creates a layer in a set of layers.
   *
   * @param layerNum the number at which to insert the new layer in the set of layers
   * @throws IllegalArgumentException if the layerNum is not the next layer number in the set
   */
  void createImageLayer(int layerNum) throws IllegalArgumentException;

  /**
   * Represents a function to remove the layer that is at the given number from the {@link ILayer}.
   *
   * @throws IllegalArgumentException if the given layerNum is out of bounds (negative or greater
   *                                  than the number of images available).
   */
  void removeImageLayer(int layerNum) throws IllegalArgumentException;

  /**
   * Retrieves the layer that is at the give number from the {@link ILayer}.
   *
   * @throws IllegalArgumentException if the given layerNum is out of bounds (negative or greater
   *                                  than the number of images available).
   */
  IImage getLayer(int layerNum) throws IllegalArgumentException;


  /**
  * Saves the multi-layered image as a collections of files (one for each layer as a regular image and
  * one text files that stores the locations of all the layer files).
  *
  * @throws IllegalArgumentException if the multi-layered image is empty.
  */
  void saveLayer();

  /**
   * Loads in a given set of layered images that is saved in the same format.
   *
   * @throws IllegalArgumentException if the given multi-layered image to be loaded is null or
   * if the list is empty.
   */
  void loadLayer(List<IImage> layeredImage);

  /**
   * Makes a layer invisible based on a given index.
   *
   * @param layerNum a given index of a layer in a list of layers of images
   */
  void makeLayerInvisible(int layerNum);

  /**
   * Applies the given IPhotoOperation to the topmost layer of a list of layers.
   *
   * @param operation the given IPhotoOperation to be applied
   * @throws IllegalArgumentException if any arguments are null
   */
  void applyOperation(IPhotoOperations operation);

}


