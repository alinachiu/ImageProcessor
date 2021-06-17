package model.layer;

import model.IPhotoOperations;
import model.image.IImage;

/**
 * Represents a named layer of a multi-layered image with an image and a visibility setting.
 */
public class Layer implements ILayer {

  private IImage image;
  private final String name;
  private boolean visibility; // true if visible, false if invisible

  /**
   * Constructs a {@code Layer} object without a set image, a name, and visibility status as true.
   *
   * @param name the name of the layer
   * @throws IllegalArgumentException if the given name of the layer is null
   */
  public Layer(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null");
    }
    image = null;
    this.name = name;
    visibility = true;
  }

  @Override
  public void setImage(IImage image) {
    if (image == null) {
      throw new IllegalArgumentException("Cannot have a null image");
    }
    this.image = image;
  }

  @Override
  public void setVisibility(boolean isVisible) {
    this.visibility = isVisible;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public IImage getImage() {
    return this.image;
  }

  @Override
    public boolean isVisible() {
      return this.visibility;
    }

  @Override
  public IImage applyOperation(IPhotoOperations operation) {
    return operation.apply(image);
  }
}
