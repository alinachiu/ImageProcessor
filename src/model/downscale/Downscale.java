package model.downscale;

import model.image.IImage;
import model.image.IPixel;
import model.image.Image;
import model.image.Pixel;

/**
 * Represents a class that applies a downsizing operation on an image to have a desired width and
 * height.
 */
public class Downscale implements IImageResize {

  @Override
  public IImage apply(IImage image, int width, int height) throws IllegalArgumentException {
    if (image == null || width <= 0 || height <= 0 || width > image.getWidth() || height > image
        .getHeight()) {
      throw new IllegalArgumentException("Invalid arguments to downsize an image.");
    }

    IPixel[][] oldGrid = image.getImage();
    IPixel[][] newGrid = new IPixel[height][width];

    for (int i = 0; i < newGrid.length; i++) {
      for (int j = 0; j < newGrid[i].length; j++) {
        int oldX = (j * image.getWidth()) / width;
        int oldY = (i * image.getHeight()) / height;
        int floorX = (int) Math.floor(oldX);
        int floorY = (int) Math.floor(oldY);
        int ceilX;
        if (Math.ceil(oldX) == Math.floor(oldX)) {
          ceilX = oldX + 1;
        } else {
          ceilX = (int) Math.ceil(oldX);
        }
        ceilX = checkEdgeBoundaries(ceilX, oldGrid[0].length);
        int ceilY;
        if (Math.ceil(oldY) == Math.floor(oldY)) {
          ceilY = oldY + 1;
        } else {
          ceilY = (int) Math.ceil(oldY);
        }
        ceilY = checkEdgeBoundaries(ceilY, oldGrid.length);
        int firstXVal = (oldX - floorX);
        int secondXVal = (ceilX - oldX);

        int[] result = getColor(oldGrid[floorY][ceilX], oldGrid[floorY][floorX],
            oldGrid[ceilY][ceilX],
            oldGrid[ceilY][floorX], firstXVal, secondXVal, oldY, floorY, ceilY);

        newGrid[i][j] = new Pixel(i, j, result[0], result[1], result[2]);
      }
    }
    return new Image(newGrid, image.getFilename());
  }

  /**
   * For the edge cases of when the user chooses a dimension that is the same as the current
   * dimension, then the floor and ceiling case is different. This method prevents the value to be
   * greater than the given maxBoundary, which is the original image's dimensions.
   *
   * @param value       the value to be checked if it's greater than or equal to the boundary.
   * @param maxBoundary the limit the value can be.
   * @return a revised value if necessary.
   */
  private int checkEdgeBoundaries(int value, int maxBoundary) {
    if (value >= maxBoundary) {
      return value - 1;
    }
    return value;
  }

  /**
   * Returns an array containing the red, green, and blue values for a pixel in which it is computed
   * using the proper formula.
   *
   * @param b          the pixel for the B value in the formula
   * @param a          the pixel for the A value in the formula
   * @param d          the pixel for the D value in the formula
   * @param c          the pixel for the C value in the formula
   * @param firstXVal  the value used to multiply by b and d in the formula
   * @param secondXVal the value used to multiply by a and c in the formula
   * @param oldY       the y value for the original image
   * @param floorY     the floor y value for the original image
   * @param ceilY      the ceiling y value for the original image
   * @return an array containing the computed red, green, and blue value for the pixel.
   */
  private int[] getColor(IPixel b, IPixel a, IPixel d, IPixel c,
      int firstXVal, int secondXVal, int oldY, int floorY, int ceilY) {
    int mRed = b.getRed() * firstXVal + a.getRed() * secondXVal;
    int nRed = d.getRed() * firstXVal + c.getRed() * secondXVal;
    int red = nRed * (oldY - floorY) + mRed * (ceilY - oldY);

    int mGreen = b.getGreen() * firstXVal + a.getGreen() * secondXVal;
    int nGreen = d.getGreen() * firstXVal + c.getGreen() * secondXVal;
    int green = nGreen * (oldY - floorY) + mGreen * (ceilY - oldY);

    int mBlue = b.getBlue() * firstXVal + a.getBlue() * secondXVal;
    int nBlue = d.getBlue() * firstXVal + c.getBlue() * secondXVal;
    int blue = nBlue * (oldY - floorY) + mBlue * (ceilY - oldY);

    return new int[]{red, green, blue};
  }

}
