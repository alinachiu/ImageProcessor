package model.layer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.IModel;
import model.IPhotoOperations;
import model.Model;
import model.color.IColorTransformation;
import model.creator.IImageCreator;
import model.exports.IExport;
import model.exports.JPEGExport;
import model.exports.PNGExport;
import model.exports.PPMExportFilename;
import model.exports.TextFileExport;
import model.filter.IFilter;
import model.image.IImage;
import model.managers.IOLayerManager;
import model.managers.IOManager;
import model.managers.InputFilenameManager;
import model.managers.InputJPEGFilenameManager;
import model.managers.InputPNGFilenameManager;
import model.managers.InputTextFilenameManager;

// TODO ask about backwards compatibility, write USEME/tests, two examples of script file
//  JAR file, updated class diagram, updated README

// TODO split tests:
//  - Alina: Controller, Imports (all), LayerModel (createImageLayer, removeImageLayer, loadLayer,
//    loadAll, setCurrent, applyOperation, make abstract test class for shared model features), UML,
//    update README, USEME, test to see if JAR file works, AdditionalImageUtils, two examples of
//    script files
//  - Jessica: IPhotoOperations (abstract), Exports (all), Layer class, LayerModel (saveLayer,
//    saveAll, makeLayerInvisible/visible), mocks, view, change PNG and JPEG export to
//    AdditionalImageUtils, abstract out save + saveAll

/**
 * Represents a group of layers of {@code IImage} that can add/remove images from the group of
 * layers and apply
 */
public class LayerModel implements ILayerModel {

  private final IModel delegate;
  private List<ILayer> layers;
  private int currentLayerNum;

  /**
   * Constructs a {@code Layers} object with an empty ArrayList.
   */
  public LayerModel() {
    this.delegate = new Model();
    this.layers = new ArrayList<>();
    currentLayerNum = -1;
  }

  @Override
  public void createImageLayer(String name) throws IllegalArgumentException {
    if (this.getIndexBasedOnName(name) == -1) {
      if (layers.isEmpty()) {
        currentLayerNum = 0;
      }
      this.layers.add(new Layer(name));
    }
  }

  @Override
  public void removeImageLayer(String layerName) throws IllegalArgumentException {
    int indexLayerToBeRemoved = this.getIndexBasedOnName(layerName);

    if (indexLayerToBeRemoved == -1) {
      throw new IllegalArgumentException("No such layer exists!");
    }

    this.layers.remove(indexLayerToBeRemoved);
  }

  @Override
  public void saveLayer(String fileName) {
    if (fileName == null) {
      throw new IllegalArgumentException("Desired filename is null");
    }

    IImage image = getTopmostVisibleImage();

    String[] splitFilename = fileName.split("\\.");
    String fileType = splitFilename[1];
    IExport exporter;

    try {
      switch (fileType) {
        case "ppm":
          exporter = new PPMExportFilename(image, splitFilename[0]);
          break;
        case "jpeg":
          exporter = new JPEGExport(image, splitFilename[0]);
          break;
        case "png":
          exporter = new PNGExport(image, splitFilename[0]);
          break;
        default:
          throw new IllegalArgumentException("Cannot save the layer with that file type.");
      }
      exporter.export();
    } catch (IOException e) {
      throw new IllegalArgumentException("An error has occurred.");
    }
  }

  /**
   * Produces the topmost visible image within the layers.
   *
   * @throws IllegalArgumentException if all the layers are invisible/none are visible.
   */
  private IImage getTopmostVisibleImage() {
    for (int i = this.layers.size() - 1; i >= 0; i++) {
      if (this.layers.get(i).isVisible()) {
        return this.layers.get(i).getImage();
      }
    }
    throw new IllegalArgumentException("None of the layers are visible");
  }


  @Override
  public void saveAll(String desiredDir) {
    IExport imgExporter;
    IExport textExporter;
    String imageInfo = "";
    File f = new File("res/" + desiredDir);
    if (f.exists()) {
      f = new File(desiredDir + "1");
    }
    f.mkdir();

    for (int i = 0; i < this.layers.size(); i++) {
      IImage currImg = this.layers.get(i).getImage();
      if (currImg != null) {
        String fileName = currImg.getFilename();
        String[] splitFilename = fileName.split("\\.");
        String fileType = splitFilename[1];

        try {
          switch (fileType) {
            case "ppm":
              imgExporter = new PPMExportFilename(currImg,
                  "res/" + desiredDir + "/" + splitFilename[0]);
              break;
            case "jpeg":
              imgExporter = new JPEGExport(currImg, "res/" + desiredDir + "/" + splitFilename[0]);
              break;
            case "png":
              imgExporter = new PNGExport(currImg, "res/" + desiredDir + "/" + splitFilename[0]);
              break;
            default:
              throw new IllegalArgumentException("Cannot save the layer with that file type.");
          }
          imgExporter.export();
          imageInfo += i + ", " + "res/" + desiredDir + "/" + fileName + ", " + this.layers.get(i)
              .isVisible() + "\n";
        } catch (IOException e) {
          throw new IllegalArgumentException("An error has occurred.");
        }
      }
    }
    textExporter = new TextFileExport(desiredDir, imageInfo);
    try {
      textExporter.export();
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not write to text file.");
    }
  }

  @Override
  public void loadLayer(String... filename) {
    int iterateBy = filename.length;

    if (filename.length > this.layers.size()) {
      iterateBy = this.layers.size();
    }

    for (int i = iterateBy - 1; i >= 0; i--) {
      IOManager manager = this.determineCorrectManager(filename[i]);
      IImage image = manager.apply();

      if (this.sameDimensions(image)) {
        if (this.currentLayerNum - i >= 0) {
          this.layers.get(currentLayerNum - i).setImage(image);
        } else {
          this.layers.get(currentLayerNum + i).setImage(image);
        }
      }
    }
  }

  /**
   * Returns the correct {@link IOManager} based on a given filename.
   *
   * @param filename the given filename
   * @return the correct {@link IOManager} associated with the filename extension
   * @throws IllegalArgumentException if no filename extension is found
   */
  private IOManager determineCorrectManager(String filename) throws IllegalArgumentException {
    String[] splitFilename = filename.split("\\.");
    String fileType = splitFilename[1];

    switch (fileType) {
      case "ppm":
        return new InputFilenameManager(filename);
      case "jpeg":
        return new InputJPEGFilenameManager(filename);
      case "png":
        return new InputPNGFilenameManager(filename);
      default:
        throw new IllegalArgumentException("Cannot save the layer with that file type.");
    }
  }

  @Override
  public void loadAll(String filename) {
    IOLayerManager manager = new InputTextFilenameManager(filename);

    List<ILayer> importedLayers = manager.apply();

    for (ILayer layer : importedLayers) {
      this.layers.add(layer);
    }
  }

  /**
   * Determines whether or not the given image has the same dimensions as the first image in the
   * list of layers. If there are no layers in the list of layers, return true.
   *
   * @param image
   * @return true if the given image has the same dimensions as the first or if the image is the
   * first layer in the list of layers
   */
  private boolean sameDimensions(IImage image) {
    if (!layers.isEmpty() && layers.get(0).getImage() != null) {
      int firstImageHeight = this.layers.get(0).getImage().getHeight();
      int firstImageWidth = this.layers.get(0).getImage().getWidth();

      return (firstImageWidth == image.getWidth()) && (firstImageHeight == image.getHeight());
    } else {
      return true;
    }
  }

  @Override
  public void makeLayerInvisible(String layerName) {
    int index = getIndexBasedOnName(layerName);
    this.layers.get(index).setVisibility(false);
  }

  @Override
  public void makeLayerVisible(String layerName) {
    int index = getIndexBasedOnName(layerName);
    this.layers.get(index).setVisibility(true);
  }

  @Override
  public void setCurrent(String layerName) {
    if (layerName == null) {
      throw new IllegalArgumentException("Layer name cannot be null!");
    }

    this.currentLayerNum = this.getIndexBasedOnName(layerName);

    if (this.currentLayerNum == -1) {
      throw new IllegalArgumentException("No such layer exists!");
    }
  }

  @Override
  public void applyOperation(IPhotoOperations operation) {
    if (this.layers.get(this.currentLayerNum).getImage() != null && this.layers
        .get(this.currentLayerNum).isVisible()) {
      IImage image = this.layers.get(this.currentLayerNum).applyOperation(operation);
      this.layers.get(this.currentLayerNum).setImage(image);
    }
  }

  /**
   * Gets the index of the layer based on the name. Returns -1 if no such layer exists.
   *
   * @param layerName the name of the desired layer
   * @return The index of the desired layer based on its name. Returns -1 if no such layer exists.
   */
  private int getIndexBasedOnName(String layerName) {
    for (int i = 0; i < layers.size(); i++) {
      if (layers.get(i).getName().equals(layerName)) {
        return i;
      }
    }

    return -1;
  }

  @Override
  public IImage filter(IImage image, IFilter filter) throws IllegalArgumentException {
    return this.delegate.filter(this.layers.get(currentLayerNum).getImage(), filter);
  }

  @Override
  public IImage colorTransformation(IImage image, IColorTransformation colorTransformation)
      throws IllegalArgumentException {
    return this.delegate.colorTransformation(this.layers.get(this.layers.size() - 1).getImage(),
        colorTransformation);
  }

  @Override
  public IImage createImage(IImageCreator creator) throws IllegalArgumentException {
    return this.delegate.createImage(creator);
  }

  @Override
  public void exportImage(IExport export) throws IllegalArgumentException, IOException {
    this.delegate.exportImage(export);
  }

  @Override
  public IImage importImage(IOManager input) throws IllegalArgumentException {
    return this.delegate.importImage(input);
  }
}