package controller;

import model.downscale.Downscale;
import model.layer.ILayerModel;

/**
 * A class representing the command to downscale an image to a desired width and height.
 */
public class DownscalingCommand implements IPhotoCommands {

  private final int width;
  private final int height;

  /**
   * Represents the command to downscale the image in the current layer to be the given height and
   * width.
   *
   * @param width  the desired width to be downscaled to
   * @param height the desired height to be downscaled to
   */
  public DownscalingCommand(int width, int height) {
    this.width = width;
    this.height = height;
  }

  @Override
  public void runCommand(ILayerModel m) throws IllegalArgumentException {
    if (m == null) {
      throw new IllegalArgumentException("model is null");
    }
    m.downscaleCurrent(new Downscale(), width, height);
  }
}
