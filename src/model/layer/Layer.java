package model.layer;

import java.util.Objects;
import model.image.IImage;
import model.image.Image;

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
    this.image = null;
    this.name = name;
    this.visibility = true;
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
    if (this.image == null) {
      return null;
    } else {
      return new Image(this.image.getImage(), this.image.getFilename());
    }
  }

  @Override
  public boolean isVisible() {
    return this.visibility;
  }

  @Override
  public String toString() {
    if (this.image == null) {
      return "Name of Layer: " + this.name
          + ", No Image Associated With This Layer"
          + ", Visibility: " + this.visibility;
    } else {
      return "Name of Layer: " + this.name + ", Image Filename: " + this.image.getFilename()
          + ", Visibility: " + this.visibility;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Layer layer = (Layer) o;

    if (this.image != null) {
      return visibility == layer.visibility && image.getFilename()
          .equalsIgnoreCase(layer.image.getFilename())
          && Objects.deepEquals(image.getImage(), image.getImage())
          && name.equalsIgnoreCase(layer.name);
    } else {
      return visibility == layer.visibility && image == layer.image
          && name.equalsIgnoreCase(layer.name);
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, visibility);
  }

}
