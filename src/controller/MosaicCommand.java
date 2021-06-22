package controller;

import model.layer.ILayerModel;

public class MosaicCommand implements IPhotoCommands {

  private final int numSeeds;

  public MosaicCommand(int numSeeds) {
    this.numSeeds = numSeeds;
  }

  @Override
  public void runCommand(ILayerModel m) throws IllegalArgumentException {
    
  }
}


