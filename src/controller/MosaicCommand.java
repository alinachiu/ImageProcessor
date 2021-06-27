package controller;

import model.layer.ILayerModel;
import model.mosaic.Mosaic;

/**
 * A class representing the command to apply a mosaic effect into the current layer's image.
 */
public class MosaicCommand implements IPhotoCommands {

  private final int numSeeds;

  /**
   * Represents the command to apply a mosaic effect onto the current image.
   *
   * @param numSeeds the number of random seeds to be generated in a mosaic image
   */
  public MosaicCommand(int numSeeds) {
    this.numSeeds = numSeeds;
  }

  @Override
  public void runCommand(ILayerModel m) throws IllegalArgumentException {
    if (m == null) {
      throw new IllegalArgumentException("Model is null");
    }
    m.mosaicCurrent(new Mosaic(), numSeeds);
  }
}


