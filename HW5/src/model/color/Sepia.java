package model.color;

/**
 * Converts an image into a sepia tone which is done using a linear color transformation.
 */
public class Sepia extends AColorTransformation {

  /**
   * Constructs a sepia color transformation to be applied on an image.
   */
  public Sepia() {
    this.colorTransformation = new double[][]{
        {.393, .769, .189},
        {.349, .686, .168},
        {.272, .534, .131}};
  }
}
