package model.filter;

import model.image.IImage;
import model.image.IPixel;
import model.image.Image;
import model.image.Pixel;

/**
 * The abstract class for image filters which contains shared properties between different filters
 * such as the general application of the filter.
 */
public abstract class AFilter implements IFilter {

  // The matrix representing the filter to be applied onto an image
  // INVARIANT: Must be a square matrix with odd dimensions
  protected double[][] kernel;

  @Override
  public IImage apply(IImage image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null.");
    }

    //Deep copy
    IPixel[][] imageGrid = image.getImage();
    //So that we are not getting the new rgb values of the neighbors
    IPixel[][] copy = image.getImage();

    int numSidePix = kernel.length / 2;
    for (int i = 0; i < imageGrid.length; i++) {
      for (int j = 0; j < imageGrid[i].length; j++) {
        imageGrid[i][j] = applyToEachPixel(i, j, copy, numSidePix);
      }
    }
    return new Image(imageGrid, image.getFilename());
  }

  /**
   * Applies the filter matrix to the current pixel by determining the neighboring cells'
   * coordinates and applying the filter matrix to those neighboring cells to produce the new red,
   * green, and blue values of the current cell. Clamping is applied if needed.
   *
   * @param x          the x coordinate of the current pixel
   * @param y          the y coordinate of the current pixel
   * @param imageGrid  the sequence of pixels for the image
   * @param numSidePix the number of pixels between the center of the filter matrix and the edge of
   *                   the filter matrix
   * @return the current pixel transformed to have a new red, green, and blue based on the filter
   *         matrix values
   * @throws IllegalArgumentException if the image grid is null
   */
  protected IPixel applyToEachPixel(int x, int y, IPixel[][] imageGrid, int numSidePix) {
    if (imageGrid == null) {
      throw new IllegalArgumentException("The image grid is null.");
    }

    int[] result = new int[3];

    int minX = getCoordinate(x - numSidePix, imageGrid.length - 1);
    int maxX = getCoordinate(x + numSidePix, imageGrid.length - 1);
    int minY = getCoordinate(y - numSidePix, imageGrid[0].length - 1);
    int maxY = getCoordinate(y + numSidePix, imageGrid[0].length - 1);
    for (int i = minX; i <= maxX; i++) {
      for (int j = minY; j <= maxY; j++) {
        IPixel currPix = imageGrid[i][j];
        int[] currPixRGB = {currPix.getRed(), currPix.getGreen(), currPix.getBlue()};
        for (int c = 0; c < result.length; c++) {
          result[c] += currPixRGB[c]
              * kernel[numSidePix - (x - currPix.getX())][numSidePix - (y - currPix.getY())];
        }
      }
    }

    // Clamping
    for (int c = 0; c < result.length; c++) {
      result[c] = clampValues(result[c]);
    }

    return new Pixel(x, y, result[0], result[1], result[2]);
  }

  /**
   * Produces the coordinate of which the filter matrix covers. If the possible coordinate is not
   * within the image grid boundary then the coordinate is the edge coordinate.
   *
   * @param possCoor the possible coordinate that the boundary will have
   * @param limit    the limit which the coordinate can be
   * @return the appropriate coordinate. either the possible coordinate given or the edge coordinate
   *         depending on if the possible coordinate is within the image grid boundaries.
   */
  protected int getCoordinate(int possCoor, int limit) {
    if (possCoor < 0) {
      return 0;
    } else {
      return Math.min(possCoor, limit);
    }
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