package model.filter;

/**
 * A filter that blurs an image by applying the filter to every channel of every pixel to produce
 * the output image.
 */
public class Blur extends AFilter {

  /**
   * Constructs an {@code Blur} object.
   */
  public Blur() {
    this.kernel = new double[][]{{0.0625, 0.125, 0.0625}, {0.125, 0.25, 0.125},
        {0.0625, 0.125, 0.0625}};
  }
}
