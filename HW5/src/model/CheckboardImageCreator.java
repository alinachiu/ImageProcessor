package model;

import java.awt.Color;
import java.util.List;

/**
 * This class creates an image programmatically that is of a checkerboard pattern.
 */
public class CheckboardImageCreator implements IImageCreator {

  // INVARIANT: sizeOfTile is a positive integer
  private final int sizeOfTile;
  // INVARIANT: numTiles is a positive integer
  private final int numTiles;
  //The red channel for the first color
  int color1R;
  //The green channel for the first color
  int color1G;
  //The blue channel for the first color
  int color1B;
  //The red channel for the second color
  int color2R;
  //The green channel for the second color
  int color2G;
  //The blue channel for the second color
  int color2B;


  /**
   * Constructs a checkerboard image that has the given number of square tiles, in which each tile
   * is the given size and is a color that is from the given list of colors.
   *
   * @param sizeOfTile the size of each square tile
   * @param numTiles   the number of tiles in the checkerboard
   * @param color1R    the red channel for the first color
   * @param color1G    the green channel for the first color
   * @param color1B    the blue channel for the first color
   * @param color2R    the red channel for the second color
   * @param color2G    the green channel for the second color
   * @param color2B    the blue channel for the second color
   * @throws IllegalArgumentException if the size of the tiles or the number of tiles is zero or
   *                                  negative or if the given list of colors is null.
   */
  public CheckboardImageCreator(int sizeOfTile, int numTiles, int color1R, int color1G, int color1B,
      int color2R, int color2G, int color2B) {
    if (sizeOfTile <= 0 || numTiles <= 0) {
      throw new IllegalArgumentException("The parameters are invalid to create a checkerboard.");
    }
    this.sizeOfTile = sizeOfTile;
    this.numTiles = numTiles;
  }

  /**
   * Constructs a checkerboard image that has the given number of square tiles, in which each tile
   * is the given size and is a color that is from the given list of colors.
   *
   * @param sizeOfTile the size of each square tile
   * @param numTiles   the number of tiles in the checkerboard
   * @throws IllegalArgumentException if the size of the tiles or the number of tiles is zero or
   *                                  negative or if the given list of colors is null.
   */
  public CheckboardImageCreator(int sizeOfTile, int numTiles) {
    if (sizeOfTile <= 0 || numTiles <= 0) {
      throw new IllegalArgumentException("The parameters are invalid to create a checkerboard.");
    }
    this.sizeOfTile = sizeOfTile;
    this.numTiles = numTiles;
    this.color1R = 255;
    this.color1G = 0;
    this.color1B = 0;
    this.color2R = 0;
    this.color2G = 0;
    this.color2B = 0;
  }

  @Override
  public IImage createImage() {
    String filename = "Checkerboard";
    // create a 2D array of pixels that represents the checkerboard based on the fields of
    // this class
    IPixel[][] checkerboard = new IPixel[this.numTiles * this.sizeOfTile][this.numTiles
        * this.sizeOfTile];

    for (int i = 0; i < this.numTiles * this.sizeOfTile; i++) {
      for (int j = 0; j < this.numTiles * this.sizeOfTile; j++) {
        int counter = 0;

        if (counter < this.sizeOfTile) {
          checkerboard[i][j] = new Pixel(i, j, this.color1R, this.color1G, this.color1B);
          counter++;
          if (counter >= sizeOfTile) {
            counter = sizeOfTile;
          }
        } else {
          checkerboard[i][j] = new Pixel(i, j, this.color2R, this.color2G, this.color2B);
          counter++;
          if (counter >= 2 * sizeOfTile) {
            counter = 0;
          }
        }
      }
    }

    // return a new model.PPMImage that has that array of pixels as a parameter as well as the desired
    // width and height

    return new PPMImage(checkerboard, filename);
  }
}
