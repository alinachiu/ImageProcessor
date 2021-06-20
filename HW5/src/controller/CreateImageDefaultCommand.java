package controller;

import model.creator.CheckboardImageCreator;
import model.layer.ILayerModel;

public class CreateImageDefaultCommand implements IPhotoCommands {
  private final int sizeOfTile;
  private final int numTiles;
  private final int R1;
  private final int G1;
  private final int B1;
  private final int R2;
  private final int G2;
  private final int B2;

  /**
   * Constructs the command to create an image programmatically with the given dimensions.
   * The image has default colors.
   *
   * @param sizeOfTile the size of each square tile
   * @param numTiles the number of tiles in the checkerboard (the size dimension of the
   *                   checkerboard)
   */
  public CreateImageDefaultCommand(int sizeOfTile, int numTiles) {
    this.sizeOfTile = sizeOfTile;
    this.numTiles = numTiles;
    this.R1 = 255;
    this.G1 = 0;
    this.B1 = 0;
    this.R2 = 0;
    this.G2 = 0;
    this.B2 = 0;
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
