package model;

/**
 * This interface represents the different operations that images can perform.
 */
public interface IImage {

  /**
   * Creates a 2D array of pixels that represent an image.
   * 
   * @return a 2D array of pixels that represent an image
   */
    IPixel[][] createPixels();
}
