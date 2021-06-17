package model.layer;

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
import model.filter.IFilter;
import model.image.IImage;
import model.managers.IOManager;
import model.managers.InputFilenameManager;
import model.managers.InputJPEGFilenameManager;
import model.managers.InputPNGFilenameManager;

// TODO fix save to make a new folder, saveAll, loadAll, tests?, USEME, add file-based
//  script functionality to controller

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
  public void saveAll() {
    // TODO ignore but fix later
  }

  @Override
  public void loadLayer(String filename) {
    IOManager manager = this.determineCorrectManager(filename);
    boolean sameDimensions = true;
    IImage image = manager.apply();

    if (!layers.isEmpty() && layers.get(0).getImage() != null) {
      int firstImageHeight = this.layers.get(0).getImage().getHeight();
      int firstImageWidth = this.layers.get(0).getImage().getWidth();
      sameDimensions =
          (firstImageWidth == image.getWidth()) && (firstImageHeight == image.getHeight());
    }

    if (sameDimensions) {
      this.layers.get(currentLayerNum).setImage(image);
    }
  }

  /**
   * Returns the correct {@link IOManager} based on a given filename.
   *
   * @param filename the given filename
   * @return the correct {@link IOManager} associated with the filename extension
   * @throws IllegalArgumentException if no filename extension is found
   */
  public IOManager determineCorrectManager(String filename) throws IllegalArgumentException {
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
  public void loadAll(List<ILayer> layeredImage) {
    // TODO ignore but fix later
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
    IImage image = this.layers.get(this.layers.size() - 1).applyOperation(operation);

    this.layers.get(this.layers.size() - 1).setImage(image);
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
    // TODO change this
    this.delegate.exportImage(export);
  }

  @Override
  public IImage importImage(IOManager input) throws IllegalArgumentException {
    return this.delegate.importImage(input);
  }
}