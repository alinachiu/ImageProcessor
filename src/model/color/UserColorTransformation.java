package model.color;

/**
 * A color transformation that uses the given matrix as the linear color transformation to apply to
 * an image.
 */
public class UserColorTransformation extends AColorTransformation {

  /**
   * Constructs a color transformation using the given matrix to be applied on an image.
   *
   * @param colorTransformation the matrix to be applied on an image's pixels
   * @throws IllegalArgumentException if the matrix is null or if the dimensions of the matrix is
   *                                  not 3x3.
   */
  public UserColorTransformation(double[][] colorTransformation) throws IllegalArgumentException {
    if (colorTransformation == null) {
      throw new IllegalArgumentException("Matrix is null.");
    }
    if (colorTransformation.length != 3 || colorTransformation[0].length != 3) {
      throw new IllegalArgumentException("The matrix must be a 3x3.");
    }
    this.colorTransformation = colorTransformation;
  }
}
