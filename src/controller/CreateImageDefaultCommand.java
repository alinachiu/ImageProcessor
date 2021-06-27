package controller;

import model.creator.CheckboardImageCreator;
import model.layer.ILayerModel;

/**
 * A class representing the command to create an image programmatically using the given arguments in
 * a default way where the colors are chosen for the user as red and black.
 */
public class CreateImageDefaultCommand implements IPhotoCommands {

  private final int sizeOfTile;
  private final int numTiles;
  private final int r1;
  private final int g1;
  private final int b1;
  private final int r2;
  private final int g2;
  private final int b2;

  /**
   * Constructs the command to create an image programmatically with the given dimensions. The image
   * has default colors.
   *
   * @param sizeOfTile the size of each square tile
   * @param numTiles   the number of tiles in the checkerboard (the size dimension of the
   *                   checkerboard)
   */
  public CreateImageDefaultCommand(int sizeOfTile, int numTiles) {
    this.sizeOfTile = sizeOfTile;
    this.numTiles = numTiles;
    this.r1 = 255;
    this.g1 = 0;
    this.b1 = 0;
    this.r2 = 0;
    this.g2 = 0;
    this.b2 = 0;
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
