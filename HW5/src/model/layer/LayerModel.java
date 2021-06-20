package model.layer;

import java.util.ArrayList;
import java.util.List;
import model.IModel;
import model.Model;
import model.color.IColorTransformation;
import model.creator.IImageCreator;
import model.filter.IFilter;
import model.image.IImage;

// TODO ask about backwards compatibility, write USEME/tests, two examples of script file
//  JAR file, updated class diagram, updated README

// TODO split tests:
//  - Alina: Controller, Imports (all), LayerModel createImageLayer, removeImageLayer, setCurrent,),
//    UML, update README, USEME, test to see if JAR file works, one example of script files add where to put JAR file in README
//  - Jessica: IPhotoOperations (abstract), Exports (all), Layer class, LayerModel (saveLayer,
//    saveAll, makeLayerInvisible/visible), mocks, view, change PNG and JPEG export to
//    AdditionalImageUtils, abstract out save + saveAll, ask TA about backwards compatibility
//    in controller

/**
 * Represents a group of layers of {@code IImage} that can add/remove images from the group of
 * layers and apply
 */
public class LayerModel implements ILayerModel {

  private final IModel delegate;
  private final List<ILayer> layers;
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

    // reset current to be -1 if removing this layer means the layers will be empty
    if (this.layers.isEmpty()) {
      this.currentLayerNum = -1;
    }
  }

  @Override
  public IImage getTopmostVisibleImage() {
    for (int i = this.layers.size() - 1; i >= 0; i--) {
      if (this.layers.get(i).isVisible() && this.layers.get(i).getImage() != null) {
        return this.layers.get(i).getImage();
      }
    }
    throw new IllegalArgumentException("None of the layers are visible");
  }

  @Override
  public int getNumberOfLayers() {
    return this.layers.size();
  }

  @Override
  public IImage getImageAtLayer(int index) {
    if (index < 0 || index >= getNumberOfLayers()) {
      throw new IllegalArgumentException("Impossible layer num");
    }
    IImage image = this.layers.get(index).getImage();
    if (image != null) {
      return image;
    }
    return null;
  }

  @Override
  public boolean getVisibilityAtLayer(int index) {
    if (index < 0 || index >= getNumberOfLayers()) {
      throw new IllegalArgumentException("Impossible layer num");
    }
    return this.layers.get(index).isVisible();
  }

  @Override
  public void loadLayer(IImage image) {
    if (image == null) {
      throw new IllegalArgumentException("Image is null");
    }
    if (sameDimensions(image)) {
      this.layers.get(currentLayerNum).setImage(image);
    }
  }

  @Override
  public void loadAll(List<ILayer> importedLayers) {
    if (importedLayers == null) {
      throw new IllegalArgumentException("Null layers.");
    }
    this.layers.addAll(importedLayers);
  }

  /**
   * Determines whether or not the given image has the same dimensions as the first image in the
   * list of layers. If there are no layers in the list of layers, return true.
   *
   * @param image the image to be checked if the dimensions are appropriate
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
    if (index == -1) {
      throw new IllegalArgumentException("No such layer exists!");
    }
    this.layers.get(index).setVisibility(false);
  }

  @Override
  public void makeLayerVisible(String layerName) {
    int index = getIndexBasedOnName(layerName);
    if (index == -1) {
      throw new IllegalArgumentException("No such layer exists!");
    }
    this.layers.get(index).setVisibility(true);
  }

  @Override
  public void setCurrent(String layerName) {
    if (layerName == null) {
      throw new IllegalArgumentException("Layer name cannot be null!");
    }

    if (this.getIndexBasedOnName(layerName) != -1 && layers.get(this.getIndexBasedOnName(layerName))
        .isVisible()) {
      this.currentLayerNum = this.getIndexBasedOnName(layerName);
    }
  }

  /**
   * Determines if the blur, gray, sepia, or sharpen can be applied to an image by checking if the
   * current layer has an image and that the layer is visible.
   *
   * @return true if the current layer has an image and that it is visible.
   */
  private boolean canApplyOperation() {
    return (this.currentLayerNum != -1 && this.layers.get(this.currentLayerNum).getImage() != null
        && this.layers.get(this.currentLayerNum).isVisible());
  }

  @Override
  public void filterCurrent(IFilter filter) {
    if (filter == null) {
      throw new IllegalArgumentException("Filter cannot be null!");
    }

    if (this.currentLayerNum != -1 && canApplyOperation()) {
      IImage image = this.delegate.filter(this.layers.get(currentLayerNum).getImage(), filter);
      this.layers.get(this.currentLayerNum).setImage(image);
    }
  }

  @Override
  public void colorTransformCurrent(IColorTransformation colorTransformation) {
    if (colorTransformation == null) {
      throw new IllegalArgumentException("Filter cannot be null!");
    }

    if (canApplyOperation()) {
      IImage image = this.delegate
          .colorTransformation(this.layers.get(currentLayerNum).getImage(),
              colorTransformation);
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
    if (image == null || filter == null) {
      throw new IllegalArgumentException("Arguments are null");
    }
    // adds image to the list of layers
    IImage i = this.delegate.filter(image, filter);
    Layer layer = new Layer(i.getFilename());
    layer.setImage(i);
    this.layers.add(layer);

    return i;
  }

  @Override
  public IImage colorTransformation(IImage image, IColorTransformation colorTransformation)
      throws IllegalArgumentException {
    if (image == null || colorTransformation == null) {
      throw new IllegalArgumentException("Arguments are null");
    }
    // adds image to the list of layers
    IImage i = this.delegate.colorTransformation(image, colorTransformation);
    Layer layer = new Layer(i.getFilename());
    layer.setImage(i);
    this.layers.add(layer);

    return i;
  }

  @Override
  public IImage createImage(IImageCreator creator) throws IllegalArgumentException {
    if (creator == null) {
      throw new IllegalArgumentException("Arguments are null");
    }
    // adds image to the list of layers
    IImage image = this.delegate.createImage(creator);
    Layer layer = new Layer(image.getFilename());
    layer.setImage(image);
    this.layers.add(layer);

    return image;
  }

  @Override
  public String toString() {
    StringBuilder newString = new StringBuilder();

    for (int i = 0; i < this.layers.size(); i++) {
      newString.append("Layer #").append(i + 1).append(", ").append(this.layers.get(i).toString())
          .append("\n");
    }

    if (currentLayerNum == -1) {
      return newString + "Number of valid layers created: " + this.getNumberOfLayers()
          + "\nCurrent not yet set.\n";
    } else {
      return newString + "Number of valid layers created: " + this.getNumberOfLayers()
          + "\nCurrent Layer: " + this.layers.get(currentLayerNum).toString() + "\n";
    }
  }

}
