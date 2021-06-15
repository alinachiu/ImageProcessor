package model.color;

/**
 * Converts a color image into a grayscale image. A grayscale is composed only of shades of grey (if
 * the red, green, and blue values are the same).
 */
public class Grayscale extends AColorTransformation {

  /**
   * Constructs a grayscale color transformation to be applied on an image.
   */
  public Grayscale() {
    this.colorTransformation = new double[][]{
        {.2126, .7512, .0722},
        {.2126, .7512, .0722},
        {.2126, .7512, .0722}};
  }


}
