package model.filter;

/**
 * A filter that uses the given kernel as the filter to apply to every channel of every pixel to
 * produce the output image.
 */
public class UserFilter extends AFilter {

  /**
   * Produces the filter to be used on an image using the given kernel matrix. The constructor also
   * produces a revised kernel so that the kernel is always a odd dimensioned square matrix.
   *
   * @param kernel the matrix to be applied onto an image
   * @throws IllegalArgumentException if the given kernel is null
   */
  public UserFilter(double[][] kernel) throws IllegalArgumentException {
    if (kernel == null) {
      throw new IllegalArgumentException("Kernel is null.");
    }
    this.kernel = revisedKernel(kernel);
  }

  /**
   * Constructs a new kernel in which the new kernel is square with odd dimensions by padding zeros
   * to the lesser dimension if the greater dimension is an odd integer. Otherwise a new matrix
   * cannot be constructed.
   *
   * @param originalKernel the kernel given to the constructor and to be altered if needed
   * @return the revised odd dimensioned square kernel
   * @throws IllegalArgumentException if the given kernel is null or if the greater dimension of the
   *                                  kernel is even or if the kernel is empty
   */
  private double[][] revisedKernel(double[][] originalKernel) throws IllegalArgumentException {
    if (originalKernel == null || originalKernel.length == 0 || originalKernel[0].length == 0) {
      throw new IllegalArgumentException("Kernel is null or empty");
    }
    //If width is greater than or equal to the height and the width is an odd dimension
    if (originalKernel[0].length >= originalKernel.length && originalKernel[0].length % 2 != 0) {
      return createNewKernel(originalKernel[0].length, originalKernel);
    }
    //If height is greater than the width and the height is an odd dimension
    else if (originalKernel.length > originalKernel[0].length && originalKernel.length % 2 != 0) {
      return createNewKernel(originalKernel.length, originalKernel);
    } else {
      throw new IllegalArgumentException(
          "Invalid kernel. The greater dimension is even and cannot make an odd"
              + "dimensioned square with it.");
    }
  }

  /**
   * Alters the given kernel so that the new kernel is a square matrix with odd dimensions by
   * padding the rectangular matrix with zeros to create a square matrix.
   *
   * @param dim            the desired dimension for the new matrix
   * @param originalKernel the kernel given to the constructor of this class
   * @return a square matrix with odd dimensions
   * @throws IllegalArgumentException if the given kernel is null
   */
  private double[][] createNewKernel(int dim, double[][] originalKernel)
      throws IllegalArgumentException {
    if (originalKernel == null) {
      throw new IllegalArgumentException("Kernel is null");
    }

    double[][] revisedKernel = new double[dim][dim];
    for (int i = 0; i < dim; i++) {
      for (int j = 0; j < dim; j++) {
        if (i >= originalKernel.length || j >= originalKernel[0].length) {
          revisedKernel[i][j] = 0; //pad with zeros
        } else {
          revisedKernel[i][j] = originalKernel[i][j];
        }
      }
    }
    return revisedKernel;
  }
}

