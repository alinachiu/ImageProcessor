package model.image;

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
   * Sets the color red to be the given value.
   *
   * @param newRed the given integer value of red to set this pixel's red value to
   * @throws IllegalArgumentException if the given new integer value is less than 0 or greater than
   *                                  255
   */
  void setRed(int newRed) throws IllegalArgumentException;

  /**
   * Sets the color green to be the given value.
   *
   * @param newGreen the given integer value of green to set this pixel's green value to
   * @throws IllegalArgumentException if the given new integer value is less than 0 or greater than
   *                                  255
   */
  void setGreen(int newGreen) throws IllegalArgumentException;

  /**
   * Sets the color blue to be the given value.
   *
   * @param newBlue the given integer value of blue to set this pixel's blue value to
   * @throws IllegalArgumentException if the given new integer value is less than 0 or greater than
   *                                  255
   */
  void setBlue(int newBlue) throws IllegalArgumentException;


}
