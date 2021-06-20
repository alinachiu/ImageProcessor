package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import model.exports.IExport;
import model.exports.JPEGExport;
import model.exports.PNGExport;
import model.exports.PPMExportFilename;
import model.exports.TextFileExport;
import model.image.IImage;
import model.layer.ILayer;
import model.layer.ILayerModel;

/**
 * A class representing the command to save a multi-layered image. It will be exported with the
 * given name. A text file is constructed along with the files in the directory in which it contains
 * information needed to reload it (name of file with its path, order, and visibility)
 */
public class SaveAllCommand implements IPhotoCommands {

  private final String desiredDir;

  /**
   * Constructs a command that saves a multi-layered image along with a text file containing the
   * necessary info to reloa it.
   *
   * @param desiredDirName the desired name of the directory to save the layers.
   * @throws IllegalArgumentException if the given filename is null.
   */
  public SaveAllCommand(String desiredDirName) {
    if (desiredDirName == null) {
      throw new IllegalArgumentException("Null filename");
    }
    File f = new File("res/" + desiredDirName);
    if (f.exists()) {
      f = new File("res/" + desiredDirName + "1");
    }
    f.mkdir();
    this.desiredDir = f.getPath() + "/";
  }

  @Override
  public void go(ILayerModel m) throws IllegalArgumentException {
    if (m == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    IExport imgExporter;
    String imageInfo = "";

    List<ILayer> layers = m.getLayers();

    for (int i = 0; i < layers.size(); i++) {
      IImage currImg = layers.get(i).getImage();
      if (currImg != null) {
        try {
          imgExporter = determineCorrectExporter(currImg);
          if (imgExporter != null) {
            imgExporter.export();
            imageInfo +=
                i + ", " + desiredDir + getOnlyNameForFile(currImg.getFilename()) + ", " + layers.get(i).isVisible()
                    + "\n";
          }
        } catch (IOException e) {
          throw new IllegalArgumentException("An error has occurred.");
        }
      }
    }
    exportTextFile(desiredDir, imageInfo);
  }

  /**
   * Exports a text file containing information about the layers including each layers
   * location/name, order, and visibility.
   *
   * @param desiredDir
   * @param imageInfo
   */
  private void exportTextFile(String desiredDir, String imageInfo) {
    IExport textExporter = new TextFileExport(desiredDir, imageInfo);
    try {
      textExporter.export();
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not write to text file.");
    }
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
    String filename = getOnlyNameForFile(image.getFilename());
    String fileType = getFileType(image.getFilename());

    if (filename != null) {
      try {
        switch (fileType) {
          case "ppm":
            return new PPMExportFilename(image, desiredDir + filename);
          case "jpeg":
            return new JPEGExport(image, desiredDir + filename);
          case "png":
            return new PNGExport(image, desiredDir + filename);
          default:
            throw new IllegalArgumentException("Cannot save the layer with that file type.");
        }
      } catch (IOException e) {
        throw new IllegalArgumentException("An error has occurred.");
      }
    }
    return null;
  }

  /**
   * Returns the filename from a full file name (aka the filename does not include the
   * path or the extension).
   * @param fullName the full name of a file (includes extension and path)
   * @return filename (no extension or path)
   */
  private String getOnlyNameForFile(String fullName) {
    int startFilename = fullName.lastIndexOf("/");
    int endFilename = fullName.indexOf(".");
    if (endFilename == -1) {
      return null;
    }
    return fullName.substring(startFilename + 1, endFilename);
  }

  /**
   * Returns the extension of a file.
   * @param fullName the full name of a file (including extension)
   * @return the extension of the file.
   */
  private String getFileType(String fullName) {
    int endFilename = fullName.indexOf(".");
    return fullName.substring(endFilename + 1);
  }
}
