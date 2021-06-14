package model.layer;

import java.util.List;
import model.image.IImage;

/**
 * Represents a set of layers of images that are able to be added or removed.
 */
public interface ILayer {

  /**
   * Adds a given image to the {@link ILayer}.
   *
   * @param image the given image to be added to this {@link ILayer}
   * @throws IllegalArgumentException if the given image is null or if the given image to be added
   * is not the same dimenson as the previous layers.
   */
  void addImageLayer(IImage image) throws IllegalArgumentException;

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
   *  Loads in a given set of layered images that is saved in the same format.
   *
   * @throws IllegalArgumentException if the given multi-layered image to be loaded is null or
   * if the list is empty.
   */
  void loadLayer(List<IImage> layeredImage);

}


