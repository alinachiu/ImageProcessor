package controller;

import model.filter.Blur;
import model.filter.IFilter;
import model.filter.Sharpening;
import model.layer.ILayerModel;
import model.managers.IOManager;
import model.managers.InputFilenameManager;
import model.managers.InputJPEGPNGFilenameManager;

/**
 * A class representing the command to apply a filter on a given image.
 */
public class FilterOnImageCommand implements IPhotoCommands {

  private final String imageFilename;
  private final String filter;

  /**
   * Creates a {@code FilterOnImageCommand} object with a given image and the given filter to be
   * applied on.
   *
   * @param imageFilename the given image filename to be filtered on
   * @param filter        the given filter name to be applied
   * @throws IllegalArgumentException if any argument is null
   */
  public FilterOnImageCommand(String imageFilename, String filter) {
    if (imageFilename == null || filter == null) {
      throw new IllegalArgumentException("Argument(s) cannot be null!");
    }

    this.imageFilename = imageFilename;
    this.filter = filter;
  }

  @Override
  public void runCommand(ILayerModel m) {
    if (m == null) {
      throw new IllegalArgumentException("Model is null.");
    }

    IOManager image = determineImageManager();
    IFilter f = determineFilter();

    if (f != null && image != null) {
      m.filter(image.apply(), f);
    } else {
      throw new IllegalArgumentException("Invalid color transformation and/or image filename.");
    }
  }

  /**
   * Returns the correct {@link IOManager} based on this filename.
   *
   * @return the correct image based on this filename return null if no type associated
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
   * Determines the filter associated with the filter String associated with this object.
   *
   * @return the correct IFilter associated with the String, if no IFilter is associated, return
   *         null
   */
  private IFilter determineFilter() {
    switch (this.filter.toLowerCase()) {
      case "blur":
        return new Blur();
      case "sharpen":
        return new Sharpening();
      default:
        return null;
    }
  }
}
