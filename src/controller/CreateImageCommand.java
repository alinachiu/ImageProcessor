package controller;

import model.creator.CheckboardImageCreator;
import model.layer.ILayerModel;

/**
 * A class representing the command to create an image programmatically using the given arguments.
 */
public class CreateImageCommand implements IPhotoCommands {

  private final int sizeOfTile;
  private final int numTiles;
  private final int r1;
  private final int g1;
  private final int b1;
  private final int r2;
  private final int g2;
  private final int b2;

  /**
   * Constructs the command to create an image programmatically with the given colors and
   * dimensions.
   *
   * @param sizeOfTile the size of each square tile
   * @param numTiles   the number of tiles in the checkerboard (the size dimension of the
   *                   checkerboard)
   * @param r1         the red channel for the first color
   * @param g1         the green channel for the first color
   * @param b1         the blue channel for the first color
   * @param r2         the red channel for the second color
   * @param g2         the green channel for the second color
   * @param b2         the blue channel for the second color
   */
  public CreateImageCommand(int sizeOfTile, int numTiles, int r1, int g1, int b1,
      int r2, int g2, int b2) {
    this.sizeOfTile = sizeOfTile;
    this.numTiles = numTiles;
    this.r1 = r1;
    this.g1 = g1;
    this.b1 = b1;
    this.r2 = r2;
    this.g2 = g2;
    this.b2 = b2;
  }

  @Override
  public void runCommand(ILayerModel m) throws IllegalArgumentException {
    if (m == null) {
      throw new IllegalArgumentException("Model is null");
    }
    m.createImage(new CheckboardImageCreator(sizeOfTile, numTiles, r1, g1, b1,
        r2, g2, b2));
  }
}
