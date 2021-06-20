package controller;

import java.io.IOException;
import java.util.List;
import model.exports.IExport;
import model.exports.JPEGExport;
import model.exports.PNGExport;
import model.exports.PPMExportFilename;
import model.image.IImage;
import model.layer.ILayer;
import model.layer.ILayerModel;

/**
 * A class representing the command to save the topmost visible layer in a multi-layered image. It
 * will be exported with the given filename.
 */
public class SaveSingleCommand implements IPhotoCommands {

  private final String filename;

  /**
   * Constructs a command that saves the topmost visible layer within a multi-layered image.
   *
   * @param filename the desired name of the saved file.
   * @throws IllegalArgumentException if the given filename is null.
   */
  public SaveSingleCommand(String filename) {
    if (filename == null) {
      throw new IllegalArgumentException("Null filename");
    }
    this.filename = filename;
  }

  @Override
  public void go(ILayerModel m) {
    if (m == null) {
      throw new IllegalArgumentException("Model is null.");
    }
    IImage image = this.getTopmostVisibleImage(m.getLayers());
    IExport exporter = this.determineCorrectExporter(image);

    try {
      exporter.export();
    } catch (IOException e) {
      throw new IllegalArgumentException("An error has occurred.");
    }
  }

  /**
   * Finds the topmost visible image when given a list of layers in a model.
   *
   * @param layers the given list of layers to sort through
   * @return the topmost visible image in a given list of layers
   * @throws IllegalArgumentException if no topmost visible layer exists
   */
  private IImage getTopmostVisibleImage(List<ILayer> layers) {
    for (int i = layers.size() - 1; i >= 0; i--) {
      if (layers.get(i).isVisible() && layers.get(i).getImage() != null) {
        return layers.get(i).getImage();
      }
    }
    throw new IllegalArgumentException("No topmost visible layer exists!");
  }

  /**
   * Returns the correct {@link IExport} based on a given filename.
   *
   * @param image the topmost visible image in the layers to be saved
   * @return the correct type of exported for the wanted image type
   * @throws IllegalArgumentException if the image is null or unknown file type
   */

  private IExport determineCorrectExporter(IImage image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Image is null.");
    }
    String[] splitFilename = image.getFilename().split("\\.");

    if (splitFilename.length == 1) {
      throw new IllegalArgumentException("Invalid filename");
    }

    String fileType = splitFilename[1];

    try {
      switch (fileType.toLowerCase()) {
        case "ppm":
          return new PPMExportFilename(image, splitFilename[0]);
        case "jpeg":
          return new JPEGExport(image, splitFilename[0]);
        case "png":
          return new PNGExport(image, splitFilename[0]);
        default:
          throw new IllegalArgumentException("Cannot save the layer with that file type.");
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("An error has occurred.");
    }
  }
}
