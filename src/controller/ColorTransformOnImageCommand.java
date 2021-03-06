package controller;

import model.color.Grayscale;
import model.color.IColorTransformation;
import model.color.Sepia;
import model.layer.ILayerModel;
import model.managers.IOManager;
import model.managers.InputFilenameManager;
import model.managers.InputJPEGPNGFilenameManager;

/**
 * A class representing the command to apply a filter on a given image.
 */
public class ColorTransformOnImageCommand implements IPhotoCommands {

  private final String imageFilename;
  private final String colorTransformation;

  /**
   * Creates a {@code ColorTransformOnImageCommand} object with a given image and the given color
   * transformation to be applied on.
   *
   * @param imageFilename       the given image filename to be filtered on
   * @param colorTransformation the given color transformation name to be applied
   * @throws IllegalArgumentException if any argument is null
   */
  public ColorTransformOnImageCommand(String imageFilename, String colorTransformation) {
    if (imageFilename == null || colorTransformation == null) {
      throw new IllegalArgumentException("Argument(s) cannot be null!");
    }

    this.imageFilename = imageFilename;
    this.colorTransformation = colorTransformation;
  }

  @Override
  public void runCommand(ILayerModel m) {
    if (m == null) {
      throw new IllegalArgumentException("Model is null.");
    }

    IOManager image = determineImageManager();
    IColorTransformation c = determineColorTransformation();

    if (c != null && image != null) {
      m.colorTransformation(image.apply(), c);
    } else {
      throw new IllegalArgumentException("Invalid color transformation and/or image filename.");
    }
  }

  /**
   * Returns the correct {@link IOManager} based on this filename.
   *
   * @return the correct image based on this filename, returns null if no image type associated
   */
  private IOManager determineImageManager() throws IllegalArgumentException {
    int endFilename = imageFilename.indexOf(".");

    switch (imageFilename.substring(endFilename + 1)) {
      case "ppm":
        return new InputFilenameManager(imageFilename);
      case "jpeg":
      case "png":
        return new InputJPEGPNGFilenameManager(imageFilename);
      default:
        return null;
    }
  }

  /**
   * Determines the color transformation associated with the color transformation String associated
   * with this object.
   *
   * @return the correct IColorTransformation associated with the String, if no IColorTransformation
   *         is associated, return null
   */
  private IColorTransformation determineColorTransformation() {
    switch (this.colorTransformation.toLowerCase()) {
      case "sepia":
        return new Sepia();
      case "grayscale":
        return new Grayscale();
      default:
        return null;
    }
  }
}
