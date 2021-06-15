package model.image;

import model.ImageUtil;

// TODO javadoc consider something else (this is the same)
public class JPEGImage implements IImage  {

  private IPixel[][] image;
  private final String filename;

  /**
   * Constructs a {@code JPEGImage} object based on a given file for the purpose of loading an
   * image.
   *
   * @param filename the name of a given file
   * @throws IllegalArgumentException if any class invariants are violated or if any argument is
   *                                  null or if the image is null because of a nonexistent file
   */
  public JPEGImage(String filename) {
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
   * Constructs a {@code JPEGImage} object with the given name based on a given set of pixels for
   * the purpose of creating an image
   *
   * @param image    the image to be loaded
   * @param filename the name of the image
   * @throws IllegalArgumentException if any class invariants are violated or if any argument is
   *                                  null
   */
  public JPEGImage(IPixel[][] image, String filename) {
    if (image == null || filename == null || image.length == 0 || image[0].length == 0) {
      throw new IllegalArgumentException("Cannot have a null/empty image or filename.");
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
}
