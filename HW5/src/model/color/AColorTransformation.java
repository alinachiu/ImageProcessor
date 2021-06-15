package model.color;

import model.image.IImage;
import model.image.IPixel;
import model.image.PPMImage;
import model.image.Pixel;

/**
 * The abstract class for image color transformations which contains shared properties between
 * different color transformation.
 */
public abstract class AColorTransformation implements IColorTransformation {

  // The matrix representing the necessary color transformation
  // INVARIANT: The matrix must be a 3x3.
  protected double[][] colorTransformation;

  @Override
  public IImage apply(IImage image) throws IllegalArgumentException {
    if (image == null || colorTransformation == null) {
      throw new IllegalArgumentException("Argument(s) not be null!");
    }
    IPixel[][] imageGrid = image.getImage();

    for (int i = 0; i < imageGrid.length; i++) {
      for (int j = 0; j < imageGrid[i].length; j++) {
        imageGrid[i][j] = applyToEachPixel(new Pixel(imageGrid[i][j].getX(), imageGrid[i][j].getY(),
            imageGrid[i][j].getRed(), imageGrid[i][j].getGreen(), imageGrid[i][j].getBlue()));
      }
    }

    return new PPMImage(imageGrid, image.getFilename());
  }

  /**
   * Apply the color transformation to the given pixel in which the final red, green, and blue
   * values of a pixel are combinations of its initial red, green, and blue values. Clamping is
   * applied if needed.
   *
   * @param pix a given pixel in an image
   * @return the pixel with the color transformation applied to it
   * @throws IllegalArgumentException if the given pixel is null
   */
  protected IPixel applyToEachPixel(IPixel pix) throws IllegalArgumentException {
    if (pix == null) {
      throw new IllegalArgumentException("Cannot have a null pixel.");
    }
    int[] rgb = {pix.getRed(), pix.getGreen(), pix.getBlue()};
    int[] result = new int[3];

    for (int i = 0; i < colorTransformation.length; i++) {
      for (int j = 0; j < rgb.length; j++) {
        result[i] += colorTransformation[i][j] * rgb[j];
      }
      result[i] = this.clampValues(result[i]);
    }

    return new Pixel(pix.getX(), pix.getY(), result[0], result[1], result[2]);
  }

  /**
   * If a given rgb value is greater than the max (255) or less than the minimum (0), clamps the
   * value to be at exactly the max or minimum respectively.
   *
   * @param result the result of the r/g/b value after performing the color transformation.
   */
  protected int clampValues(int result) {
    if (result > 255) {
      return 255;
    } else {
      return Math.max(result, 0);
    }
  }
}
