package model.image;

import java.util.Arrays;
import java.util.Objects;
import utils.ImageUtil;

/**
 * Represents an image that is of the PPM/JPEG/PNG format (a simple, text-based file format to store
 * images) that has a list of pixels. A PPM Image is made up of pixels that have red, green, and
 * blue values of each pixel, row-wise.
 */
public class Image implements IImage {

  private final IPixel[][] image;
  private final String filename;

  /**
   * Constructs a {@code Image} object based on a given file for the purpose of loading an image.
   *
   * @param filename the name of a given file
   * @throws IllegalArgumentException if any class invariants are violated or if any argument is
   *                                  null or if the image is null because of a nonexistent file
   */
  public Image(String filename) {
    if (filename == null) {
      throw new IllegalArgumentException("The filename cannot be null.");
    }
    this.filename = filename;
    this.image = ImageUtil.readPPM(this.filename);

    if (this.image == null) {
      throw new IllegalArgumentException("The file doesn't exist.");
    }
  }

  /**
   * Constructs a {@code Image} object with the given name based on a given set of pixels for the
   * purpose of creating an image.
   *
   * @param image    the image to be loaded
   * @param filename the name of the image
   * @throws IllegalArgumentException if any class invariants are violated or if any argument is
   *                                  null
   */
  public Image(IPixel[][] image, String filename) {
    if (image == null || filename == null || image.length == 0 || image[0].length == 0
        || this.checkImageGrid(image)) {
      throw new IllegalArgumentException("Cannot have a null/empty image or filename.");
    }

    this.image = image;
    this.filename = filename;
  }

  /**
   * Returns true if any of the pixels in a given 2D IPixel array are invalid.
   *
   * @param imageGrid the given 2D IPixel array
   * @return true if any of the pixels in the given 2D IPixel array are invalid, false if they are
   *         all valid
   */
  private boolean checkImageGrid(IPixel[][] imageGrid) throws IllegalArgumentException {
    for (IPixel[] iPixels : imageGrid) {
      for (IPixel iPixel : iPixels) {
        if (iPixel == null) {
          return true;
        }
      }
    }

    return false;
  }

  @Override
  public IPixel[][] getImage() {
    IPixel[][] imageGrid = new IPixel[this.getHeight()][this.getWidth()];

    for (int i = 0; i < this.getHeight(); i++) {
      for (int j = 0; j < this.getWidth(); j++) {
        IPixel currPixel = this.image[i][j];
        imageGrid[i][j] = new Pixel(i, j, currPixel.getRed(), currPixel.getGreen(),
            currPixel.getBlue());
      }
    }

    return imageGrid;
  }

  @Override
  public String getFilename() {
    return this.filename;
  }

  @Override
  public int getHeight() {
    return this.image.length;
  }

  @Override
  public int getWidth() {
    return this.image[0].length;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Image ppmImage = (Image) o;
    return Arrays.deepEquals(image, ppmImage.image) && Objects
        .equals(filename, ppmImage.filename);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(filename);
    result = 31 * result + Arrays.deepHashCode(image);
    return result;
  }
}
