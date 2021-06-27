package model.image;

import java.util.Objects;

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
   * Constructs a {@code model.imageRepresentation.Pixel} object.
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

  @Override
  public int getX() {
    return this.x;
  }

  @Override
  public int getY() {
    return this.y;
  }

  @Override
  public int getRed() {
    return this.red;
  }

  @Override
  public int getBlue() {
    return this.blue;
  }

  @Override
  public int getGreen() {
    return this.green;
  }

  @Override
  public void setRed(int newRed) throws IllegalArgumentException {
    if (newRed < 0 || newRed > 255) {
      throw new IllegalArgumentException("Given integer is too small or large!");
    }

    this.red = newRed;
  }

  @Override
  public void setGreen(int newGreen) throws IllegalArgumentException {
    if (newGreen < 0 || newGreen > 255) {
      throw new IllegalArgumentException("Given integer is too small or large!");
    }

    this.green = newGreen;
  }

  @Override
  public void setBlue(int newBlue) throws IllegalArgumentException {
    if (newBlue < 0 || newBlue > 255) {
      throw new IllegalArgumentException("Given integer is too small or large!");
    }

    this.blue = newBlue;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Pixel pixel = (Pixel) o;
    return x == pixel.x && y == pixel.y && red == pixel.red && green == pixel.green
        && blue == pixel.blue;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, red, green, blue);
  }

  @Override
  public String toString() {
    // returns the red green and blue values in sequential order
    return this.red + " " + this.green + " " + this.blue;
  }
}
