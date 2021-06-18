package model.creator;

import model.image.IImage;
import model.image.IPixel;
import model.image.Image;
import model.image.Pixel;

/**
 * This class creates an image programmatically that is of a checkerboard pattern.
 */
public class CheckboardImageCreator implements IImageCreator {

  // INVARIANT: sizeOfTile is a positive integer
  private final int sizeOfTile;
  // INVARIANT: numTiles is a positive integer
  private final int numTiles;
  //The red channel for the first color
  // INVARIANT: red is a value between 0 and 255, inclusive
  private final int color1R;
  //The green channel for the first color
  // INVARIANT: green is a value between 0 and 255, inclusive
  private final int color1G;
  //The blue channel for the first color
  // INVARIANT: blue is a value between 0 and 255, inclusive
  private final int color1B;
  //The red channel for the second color
  // INVARIANT: red is a value between 0 and 255, inclusive
  private final int color2R;
  //The green channel for the second color
  // INVARIANT: green is a value between 0 and 255, inclusive
  private final int color2G;
  //The blue channel for the second color
  // INVARIANT: blue is a value between 0 and 255, inclusive
  private final int color2B;


  /**
   * Constructs a checkerboard image that has the given number of square tiles for the side
   * dimension, in which each tile is the given size and is a color that is from the given list of
   * colors.
   *
   * @param sizeOfTile the size of each square tile
   * @param numTiles   the number of tiles in the checkerboard (the size dimension of the
   *                   checkerboard)
   * @param color1R    the red channel for the first color
   * @param color1G    the green channel for the first color
   * @param color1B    the blue channel for the first color
   * @param color2R    the red channel for the second color
   * @param color2G    the green channel for the second color
   * @param color2B    the blue channel for the second color
   * @throws IllegalArgumentException if the size of the tiles or the number of tiles is zero or
   *                                  negative or if the given list of colors is null or if the
   *                                  color values are invalid (not between 0 and 255, inclusive)
   */
  public CheckboardImageCreator(int sizeOfTile, int numTiles, int color1R, int color1G, int color1B,
      int color2R, int color2G, int color2B) {
    if (sizeOfTile <= 0 || numTiles <= 0) {
      throw new IllegalArgumentException("The parameters are invalid to create a checkerboard.");
    }
    if (color1R < 0 || color1R > 255 || color1G < 0 || color1G > 255 || color1B < 0 || color1B > 255
        || color2R < 0 || color2R > 255 || color2G < 0 || color2G > 255 || color2B < 0
        || color2B > 255) {
      throw new IllegalArgumentException("Invalid color arguments.");
    }
    this.sizeOfTile = sizeOfTile;
    this.numTiles = numTiles;
    this.color1R = color1R;
    this.color1G = color1G;
    this.color1B = color1B;
    this.color2R = color2R;
    this.color2G = color2G;
    this.color2B = color2B;
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

    int counter; //Counter for the purpose of switching colors to produce the checkerboard pattern
    for (int i = 0; i < this.numTiles * this.sizeOfTile; i++) {
      if (i / sizeOfTile % 2 != 0) {
        counter = sizeOfTile;
      } else {
        counter = 0;
      }
      for (int j = 0; j < this.numTiles * this.sizeOfTile; j++) {
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

    // return a new model.imageRepresentation.Image that has that array of pixels as a parameter
    // as well as the desired width and height
    return new Image(checkerboard, filename);
  }
}
