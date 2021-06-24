package model.mosaic;

import model.image.IImage;

/**
 * Represents an interface for applying photo effects (such as mosaicing) to images and returning
 * their new respective images.
 */
public interface IPhotoEffect {

  /**
   * Applies a photo effect to a given image based on the given number of random seds.
   *
   * @param image the given image to be transformed by this {@link IPhotoEffect}
   * @param numSeeds number of random seeds to create the image with
   * @return the transformed image (a new image that is of the original image with the photo
   * operation applied)
   * @throws IllegalArgumentException if any argument is null or if the number of seeds is negative.
   */
  IImage apply(IImage image, int numSeeds) throws IllegalArgumentException;
}
