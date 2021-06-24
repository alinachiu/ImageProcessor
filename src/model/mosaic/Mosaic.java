package model.mosaic;

import model.image.IImage;

/**
 * A photo effect that applies a mosaic effect, which gives an image a 'stained glass window' effect
 * (stained glass windows create pictures by joining smaller irregularly-shaped pieces of stained
 * glass), onto a given image based on a given number of random seeds.
 */
public class Mosaic implements IPhotoEffect {

  @Override
  public IImage apply(IImage image, int numSeeds) throws IllegalArgumentException {
    if (image == null || numSeeds < 0) {
      throw new IllegalArgumentException("One or more arguments is invalid!");
    }

    

    return null;
  }
}
