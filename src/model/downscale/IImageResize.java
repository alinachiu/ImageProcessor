package model.downscale;

import model.image.IImage;

/**
 * Represents an interface for resizing images (such as downscaling) and returning their new
 * respective images.
 */
public interface IImageResize {

  /**
   * Applies a resize to the given image so that the image has new dimensions of the given width and
   * the given height.
   *
   * @param image  the image to be resized
   * @param width  the desired width
   * @param height the desired height
   * @return the image with the given width and given height
   * @throws IllegalArgumentException if the image is null or if the width or height is non-positive
   *                                  or if the width/height is not appropriate for the operation
   *                                  (ex: downscaling should have a given width/height that is
   *                                  smaller than the given image's dimensions)
   */
  IImage apply(IImage image, int width, int height) throws IllegalArgumentException;
}
