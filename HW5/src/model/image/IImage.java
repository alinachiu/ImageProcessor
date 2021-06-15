package model.image;

/**
 * This interface represents the different operations that images can perform.
 */
public interface IImage {

  /**
   * Gets a deep copy of a 2D array of pixels that represent an image.
   *
   * @return a 2D array of pixels that represent an image
   */
  IPixel[][] getImage();

  /**
   * Gets the filename of this image.
   *
   * @return the filename of this image
   */
  String getFilename();

  /**
   * Gets the height of this image.
   *
   * @return the height of this image.
   */
  int getHeight();

  /**
   * Gets the width of this image.
   *
   * @return the width of this image.
   */
  int getWidth();


}
