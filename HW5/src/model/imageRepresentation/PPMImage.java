package model.imageRepresentation;

import model.ImageUtil;

/**
 * Represents a PPM image that is of the PPM format (a simple, text-based file format to store
 * images) that has a list of pixels. A PPM Image is made up of pixels that have red, green, and
 * blue values of each pixel, row-wise.
 */
public class PPMImage implements IImage {

  private IPixel[][] image;
  private final String filename;

  /**
   * Constructs a {@code Image} object based on a given file for the purpose of loading an image.
   *
   * @param filename the name of a given file
   * @throws IllegalArgumentException if any class invariants are violated or if any argument is
   *                                  null
   */
  public PPMImage(String filename) {
    if (filename == null) {
      throw new IllegalArgumentException("The filename cannot be null.");
    }
    this.filename = filename;
    this.image = ImageUtil.readPPM(this.filename);
  }

  /**
   * Constructs a {@code Image} object with the given name based on a given set of pixels for the
   * purpose of creating an imae.ge
   *
   * @param image    the image to be loaded
   * @param filename the name of the image
   * @throws IllegalArgumentException if any class invariants are violated or if any argument is
   *                                  null
   */
  public PPMImage(IPixel[][] image, String filename) {
    if (image == null || filename == null) {
      throw new IllegalArgumentException("Cannot have a null image or filename.");
    }

    this.image = image;
    this.filename = filename;
  }

  @Override
  public IPixel[][] getImage() {
    IPixel[][] imageGrid = new IPixel[this.getHeight()][this.getWidth()];

    for (int i = 0; i < this.getHeight(); i++) {
      for (int j = 0; j < this.getWidth(); j++) {
        IPixel currPixel = this.image[i][j];
        imageGrid[i][j] = new Pixel(i, j, currPixel.getRed(), currPixel.getGreen(), currPixel.getBlue());
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
}
