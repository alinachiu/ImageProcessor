package model;

import model.exports.IExport;
import java.io.IOException;
import model.creator.CheckboardImageCreator;
import model.creator.IImageCreator;
import model.image.IImage;
import model.managers.IOManager;
import model.managers.InputFileManager;
import model.color.IColorTransformation;
import model.filter.IFilter;

/**
 * Represents an image processing model as well as the actions that it can perform such as adding
 * and removing images from a HashMap, applying filters, and transforming the colors of an image.
 */
public interface IModel {

  /**
   * Creates a filter on the given image in which the filter has the given kernel (2D array of
   * numbers, having odd dimensions). Filtering is applied separately to each channel.
   *
   * @param image  the given image to apply a filter to
   * @param filter the type of filter to apply to the image
   * @return a new copy of the given image with a filter applied to it
   * @throws IllegalArgumentException if the image or filter is null
   */
  IImage filter(IImage image, IFilter filter) throws IllegalArgumentException;

  /**
   * Produces a color transformation for the given image in which the color transformation has the
   * given kernal. A color transformation results in a new color of each pixel in the given image.
   *
   * @param image               the image to apply the color transformation on
   * @param colorTransformation the type of color transformation to apply to the image
   * @return a new copy of the given image with a color transformation applied to it
   * @throws IllegalArgumentException if the image or colorTransformation is null
   */
  IImage colorTransformation(IImage image, IColorTransformation colorTransformation)
      throws IllegalArgumentException;

  /**
   * Creates an image programmatically based on a given IImageCreator.
   *
   * @param creator the image creator with the correct values to be implemented (for instance, if a
   *                {@link CheckboardImageCreator} is given, the correct number of tiles, size, and
   *                colors will be available.
   * @return the new image based on the creator
   * @throws IllegalArgumentException if the given creator is null
   */
  IImage createImage(IImageCreator creator) throws IllegalArgumentException;

  /**
   * Exports an image in the correct format based on a given {@link IExport} object.
   *
   * @param export the given export function object which exports the correct file version of an
   *               object
   * @throws IllegalArgumentException if any argument is null
   * @throws IOException              if there is a problem with attempting to write the file
   */
  void exportImage(IExport export) throws IllegalArgumentException, IOException;

  /**
   * Imports a given image in the correct format based on a given {@link InputFileManager} object.
   *
   * @param input the given import manager which manages inputs to create a new Image
   * @return the image based on the given manager
   * @throws IllegalArgumentException if any argument is null
   */
  IImage importImage(IOManager input) throws IllegalArgumentException;
}
