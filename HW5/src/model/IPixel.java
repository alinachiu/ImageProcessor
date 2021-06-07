package model;

/**
 * This interface represents the different operations that pixels of an Image can perform.
 */
public interface IPixel {

  /**
   * Gets the x value of a pixel.
   * 
   * @return the x value associated with this pixel
   */
  int getX();

  /**
   * Gets the y value of a pixel.
   * 
   * @return the y value associated with this pixel
   */
  int getY();

  /**
   * Gets the red color of a pixel.
   *
   * @return the integer value associated with the pixel's color red
   */
  int getRed();

  /**
   * Gets the blue color of a pixel.
   *
   * @return the integer value associated with the pixel's color blue
   */
  int getBlue();

  /**
   * Gets the green color of a pixel.
   *
   * @return the integer value associated with the pixel's color green
   */
  int getGreen();

  /**
   * Sets the red color of a pixel.
   */
}
