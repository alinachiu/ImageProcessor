package model;

/**
 * Represents a pixel in an image that has a position of non-negative x and y values as well as a
 * red, green, and blue value which are represented with values 0 to 255 inclusive.
 */
public class Pixel implements IPixel {

  // INVARIANT: the x position of a pixel is non-negative
  private final int x;
  // INVARIANT: the y position of a pixel is non-negative
  private final int y;
  // INVARIANT: red is a value between 0 and 255, inclusive
  private int red;
  // INVARIANT: green is a value between 0 and 255, inclusive
  private int green;
  // INVARIANT: blue is a value between 0 and 255, inclusive
  private int blue;

  /**
   * Constructs a {@code model.Pixel} object.
   *
   * @param x     the x coordinate of a given pixel
   * @param y     the y coordinate of a given pixel
   * @param red   the red value of a given pixel
   * @param green the green value of a given pixel
   * @param blue  the blue value of a given pixel
   * @throws IllegalArgumentException if any class invariants are violated
   */
  public Pixel(int x, int y, int red, int green, int blue) throws IllegalArgumentException {
    if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255 || x < 0
        || y < 0) {
      throw new IllegalArgumentException("One or more parameters violates a class invariant!");
    }

    this.x = x;
    this.y = y;
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public int getRed() {
    return this.red;
  }

  public int getBlue() {
    return this.blue;
  }

  public int getGreen() {
    return this.green;
  }
}
