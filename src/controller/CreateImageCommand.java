package controller;

import model.creator.CheckboardImageCreator;
import model.layer.ILayerModel;

/**
 * A class representing the command to create an image programmatically using
 * the given arguments.
 */
public class CreateImageCommand implements IPhotoCommands {
  private final int sizeOfTile;
  private final int numTiles;
  private final int R1;
  private final int G1;
  private final int B1;
  private final int R2;
  private final int G2;
  private final int B2;

  /**
   * Constructs the command to create an image programmatically with the given colors and dimensions.
   *
   * @param sizeOfTile the size of each square tile
   * @param numTiles the number of tiles in the checkerboard (the size dimension of the
   *                 checkerboard)
   * @param R1 the red channel for the first color
   * @param G1 the green channel for the first color
   * @param B1 the blue channel for the first color
   * @param R2 the red channel for the second color
   * @param G2 the green channel for the second color
   * @param B2 the blue channel for the second color
   */
  public CreateImageCommand(int sizeOfTile, int numTiles, int R1, int G1, int B1,
      int R2, int G2, int B2) {
    this.sizeOfTile = sizeOfTile;
    this.numTiles = numTiles;
    this.R1 = R1;
    this.G1 = G1;
    this.B1 = B1;
    this.R2 = R2;
    this.G2 = G2;
    this.B2 = B2;
  }

  @Override
  public void go(ILayerModel m) throws IllegalArgumentException {
    if (m == null) {
      throw new IllegalArgumentException("Model is null");
    }
    m.createImage(new CheckboardImageCreator(sizeOfTile, numTiles, R1, G1, B1,
        R2, G2, B2));
  }
}
