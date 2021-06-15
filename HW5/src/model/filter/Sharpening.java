package model.filter;

/**
 * A filter that sharpens an image by accentuating the edges (the boundaries between regions of high
 * contrast).
 */
public class Sharpening extends AFilter {

  /**
   * Constructs an {@code Sharpening} object.
   */
  public Sharpening() {
    this.kernel = new double[][]{
        {-0.125, -0.125, -0.125, -0.125, -0.125},
        {-0.125, 0.25, 0.25, 0.25, -0.125},
        {-0.125, 0.25, 1, 0.25, -0.125},
        {-0.125, 0.25, 0.25, 0.25, -0.125},
        {-0.125, -0.125, -0.125, -0.125, -0.125}};
  }
}
