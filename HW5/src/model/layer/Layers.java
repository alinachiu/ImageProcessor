package model.layer;

import java.util.ArrayList;
import java.util.List;
import model.IPhotoOperations;
import model.image.IImage;

/**
 * Represents a group of layers of {@code IImage} that can add/remove images from the group of
 * layers and apply
 */
public class Layers implements ILayer {

  private List<IImage> layers;

  /**
   * Constructs a {@code Layers} object with an empty ArrayList.
   */
  public Layers() {
    this.layers = new ArrayList<>();
  }

  /**
   * Checks to make sure that a given image has the same dimensions as the first image in a list of
   * layers. If the image is the first one in the list of layers, should always return true.
   *
   * @param image the given image to be compared
   * @return true if the list of layers is empty or if the dimensions (width and height) of the
   * given image match the dimensions of the first image in a list of layers, false if neither are
   * the case
   */
  private boolean validImage(IImage image) {
    if (this.layers.isEmpty()) {
      return true;
    }

    int firstImageHeight = this.layers.get(0).getHeight();
    int firstImageWidth = this.layers.get(0).getWidth();

    return (firstImageWidth == image.getWidth()) && (firstImageHeight == image.getHeight());
  }

  @Override
  public void createImageLayer(int layerNum) throws IllegalArgumentException {
    // TODO find out what "creating a layer" means
    this.layers.add(null);
  }

  @Override
  public void removeImageLayer(int layerNum) throws IllegalArgumentException {
    if (layerNum < 0 || layerNum > this.layers.size() - 1) {
      throw new IllegalArgumentException("Given index is out of bounds!");
    }
    this.layers.remove(layerNum);
  }

  @Override
  public IImage getLayer(int layerNum) throws IllegalArgumentException {
    if (layerNum < 0 || layerNum > this.layers.size() - 1) {
      throw new IllegalArgumentException("Given index is out of bounds!");
    }
    return this.layers.get(layerNum);
  }

  @Override
  public void saveLayer() {
    // TODO find out what "regular image" means
  }

  @Override
  public void loadLayer(List<IImage> layeredImage) {
    for (IImage image : layeredImage) {
      this.layers.add(image);
    }
  }

  @Override
  public void makeLayerInvisible(int layerNum) {
    // TODO find out what "invisible" means
  }

  @Override
  public void applyOperation(IPhotoOperations operation) {
    if (operation == null || this.layers.isEmpty()) {
      throw new IllegalArgumentException("Arguments cannot be null/No layers exist");
    }
    operation.apply(this.layers.get(0));
  }

}