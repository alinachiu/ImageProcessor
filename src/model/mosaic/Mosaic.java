package model.mosaic;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import model.image.IImage;
import model.image.IPixel;
import model.image.Image;

/**
 * A photo effect that applies a mosaic effect, which gives an image a 'stained glass window' effect
 * (stained glass windows create pictures by joining smaller irregularly-shaped pieces of stained
 * glass), onto a given image based on a given number of random seeds.
 */
public class Mosaic implements IPhotoEffect {

  @Override
  public IImage apply(IImage image, int numSeeds) throws IllegalArgumentException {
    if (image == null || numSeeds < 0 || numSeeds > (image.getImage().length * image
        .getImage()[0].length)) {
      throw new IllegalArgumentException("One or more arguments is invalid!");
    }

    int height = image.getHeight();
    int width = image.getWidth();

    // find the locations of the random seeds and taking note of them, avoiding duplicates
    // by using HashSet
    Set<RandomSeed> randomSeedLocations = new HashSet<>();
    Random rand = new Random();

    // adds random seed locations to the hashset until the locations are finalized
    while (randomSeedLocations.size() < numSeeds) {
      randomSeedLocations.add(new RandomSeed(rand.nextInt(height), rand.nextInt(width)));
    }

    List<RandomSeed> listOfRandomSeedLocations = new ArrayList<>(randomSeedLocations);
    IPixel[][] grid = image.getImage();

    // add pixels to their corresponding images
    for (int j = 0; j < height; j++) {
      for (int k = 0; k < width; k++) {
        for (int i = 0; i < listOfRandomSeedLocations.size(); i++) {
          if (nearestPoint(listOfRandomSeedLocations, j, k) == listOfRandomSeedLocations.get(i)) {
            listOfRandomSeedLocations.get(i).addPixel(grid[j][k]);
            i = listOfRandomSeedLocations.size();
          }
        }
      }
    }

    List<Color> avgColors = new ArrayList<>();

    for (RandomSeed listOfRandomSeedLocation : listOfRandomSeedLocations) {
      avgColors.add(listOfRandomSeedLocation.blendColors());
    }

    for (int i = 0; i < listOfRandomSeedLocations.size(); i++) {
      for (int p = 0; p < listOfRandomSeedLocations.get(i).closestPixels.size(); p++) {
        IPixel currPix = listOfRandomSeedLocations.get(i).closestPixels.get(p);
        currPix.setRed(avgColors.get(i).getRed());
        currPix.setGreen(avgColors.get(i).getGreen());
        currPix.setBlue(avgColors.get(i).getBlue());
      }
    }

    return new Image(grid, image.getFilename());
  }

  /**
   * Determines the distance between two given points.
   *
   * @param x1 the x coordinate associated with the first point
   * @param y1 the y coordinate associated with the first point
   * @param x2 the x coordinate associated with the second point
   * @param y2 the y coordinate associated with the second point
   * @return the distance between two points
   */
  private double distance(int x1, int y1, int x2, int y2) {
    double x = Math.pow(x2 - x1, 2);
    double y = Math.pow(y2 - y1, 2);
    return Math.sqrt(x + y);
  }

  /**
   * Finds out which random seed a given point is closest to.
   *
   * @param listOfCoordinates the list of random seeds that were generated
   * @param x                 the given x coordinate associated with a point
   * @param y                 the given y coordinate associated with a point
   * @return the random seed closest to the given point
   * @throws IllegalArgumentException if the given list of coordinates is empty or the given x/y
   *                                  coordinates are out of bounds or if any of the coordinates in
   *                                  the list of coordinates are out of bounds
   */
  private RandomSeed nearestPoint(List<RandomSeed> listOfCoordinates, int x, int y)
      throws IllegalArgumentException {
    if (listOfCoordinates.isEmpty() || x < 0 || y < 0) {
      throw new IllegalArgumentException("Invalid argument(s)");
    }

    RandomSeed closestPoint = listOfCoordinates.get(0);
    double closestDist = distance(x, y, closestPoint.getX(), closestPoint.getY());

    for (RandomSeed listOfCoordinate : listOfCoordinates) {
      double dist = distance(x, y, listOfCoordinate.getX(),
          listOfCoordinate.getY());

      if (dist < closestDist) {
        closestDist = dist;
        closestPoint = listOfCoordinate;
      }
    }

    return closestPoint;
  }

  /**
   * Represents a private helper class that has a position where the random seed is located as well
   * as all the pixels that are closest to that seed.
   */
  private static class RandomSeed {

    private final int x;
    private final int y;
    private final List<IPixel> closestPixels;


    /**
     * Constructs an {@code RandomSeed} object which represents a random seed to be used in a mosaic
     * object.
     *
     * @param x the x location of the random seed
     * @param y the y location of the random seed
     * @throws IllegalArgumentException if the given x and/or y values are negative
     */
    private RandomSeed(int x, int y) throws IllegalArgumentException {
      if (x < 0 || y < 0) {
        throw new IllegalArgumentException("Random seed is out of bounds!");
      }

      this.x = x;
      this.y = y;
      this.closestPixels = new ArrayList<>();
    }

    /**
     * Adds a given pixel to the list of closest pixels in this random seed object.
     *
     * @param pixel the given pixel
     */
    private void addPixel(IPixel pixel) {
      this.closestPixels.add(pixel);
    }

    /**
     * Gets the x value of this random seed.
     *
     * @return the x value of this random seed
     */
    private int getX() {
      return this.x;
    }

    /**
     * Gets the y value of this random seed.
     *
     * @return the y value of this random seed
     */
    private int getY() {
      return this.y;
    }

    /**
     * Blends the colors of the closest pixels together.
     *
     * @return the average color after blending all the closest pixels together
     * @throws IllegalArgumentException if no pixels are available to be blended
     */
    private Color blendColors() {
      if (closestPixels.isEmpty()) {
        return new Color(0, 0, 0);
      }

      int red = 0;
      int green = 0;
      int blue = 0;

      for (IPixel pixel : closestPixels) {
        red += pixel.getRed();
        green += pixel.getGreen();
        blue += pixel.getBlue();
      }

      // divide by number of pixels to get average color
      red /= closestPixels.size();
      green /= closestPixels.size();
      blue /= closestPixels.size();

      // clamp values
      red = clamp(red);
      green = clamp(green);
      blue = clamp(blue);

      return new Color(red, green, blue);
    }

    /**
     * Clamps the given value to be 255 if necessary.
     *
     * @param colorValue the color value to be clamped
     * @return the clamped integer value
     */
    private int clamp(int colorValue) {
      if (colorValue > 255) {
        return 255;
      } else {
        return Math.max(colorValue, 0);
      }
    }

    public String toString() {
      StringBuilder result = new StringBuilder();
      for (IPixel closestPixel : closestPixels) {
        result.append(closestPixel.getX()).append(" ").append(closestPixel.getY()).append(" ");
      }
      return result.toString();

    }
  }
}
