package model;

import model.colorTransformation.IColorTransformation;
import model.filter.IFilter;

/**
 * Represents an image processing model as well as the actions that it can perform such as adding
 * and removing images from a HashMap, applying filters, and transforming the colors of an image.
 */
public interface IModel {

  /**
   * Adds a given image to the model based on a given id.
   *
   * @param id    the given id for a given image
   * @param image the given image to be added to the model
   */
  void addImage(String id, IImage image);

  /**
   * Removes a given image from the model based on a given id.
   *
   * @param id the given id associated with the given image
   * @param image the given image to be removed from the model
   */
  void removeImage(String id, IImage image);

  /**
   * Creates a filter on the given image in which the filter has the given kernel (2D array of
   * numbers, having odd dimensions). Filtering is applied separately to each channel.
   *
   * @param image the given image to apply a filter to
   * @param filter the type of filter to apply to the image
   * @throws IllegalArugmentException if the image or filter is null
   */
  void filter(IImage image, IFilter filter) throws IllegalArgumentException;

  /**
   * Produces a color transformation for the given image in which the color transformation has the
   * given kernal. A color transformation results in a new color of each pixel in the given image.
   *
   * @param image the image to apply the color transformation on
   * @param colorTransformation the type of color transformation to apply to the image
   * @throws IllegalArugmentException if the image or colorTransformation is null
   */
  void colorTransformation(IImage image, IColorTransformation colorTransformation) throws IllegalArgumentException;

  // TODO figure out a better way to represent this
  /**
  * Creates an image programmatically.
  */
  IImage createImage();
}
