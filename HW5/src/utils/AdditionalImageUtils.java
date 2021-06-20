package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.image.IImage;
import model.image.IPixel;
import model.image.Pixel;

/**
 * This class contains utility methods to read a PNG/JPEG image from file and simply print its
 * contents.
 */
public class AdditionalImageUtils {

  /**
   * Read an image file in the PNG/JPEG format and print the colors.
   *
   * @param file the path of the file.
   * @return a 2D array of pixels that represents a PPM image
   * @throws IllegalArgumentException if the given file is null or the file is not found
   * @throws IOException              if an I/O error occurs
   */
  public static IPixel[][] readPNGJPEG(File file) throws IllegalArgumentException, IOException {
    if (file == null) {
      throw new IllegalArgumentException("File cannot be null!");
    }

    BufferedImage bufferedImage = ImageIO.read(file);
    IPixel[][] imageGrid = new Pixel[bufferedImage.getHeight()][bufferedImage.getWidth()];

    for (int i = 0; i < bufferedImage.getHeight(); i++) {
      for (int j = 0; j < bufferedImage.getWidth(); j++) {
        // Getting pixel color by position x and y
        Color myColor = new Color(bufferedImage.getRGB(j, i));
        int red = myColor.getRed();
        int green = myColor.getGreen();
        int blue = myColor.getBlue();

        IPixel newPixel = new Pixel(j, i, red, green, blue);
        imageGrid[i][j] = newPixel;
      }
    }

    return imageGrid;
  }

  /**
   * Exports the given image to be the type with the given extension and to have the given filename.
   * @param image the image to be exported.
   * @param filename the name of the file to be exported.
   * @param extension tbe extension of the file to be exported.
   * @throws IllegalArgumentException if any arguments are null
   * @throws IOException if could not write to writer.
   */
  public static void exportWithType(IImage image, String filename, String extension) throws IllegalArgumentException {
    if (image == null || filename == null || extension == null) {
      throw new IllegalArgumentException("One or more of the arguments are null");
    }
    BufferedImage img = new BufferedImage(image.getWidth(), image.getHeight(),
        BufferedImage.TYPE_INT_RGB);
    File file = new File(filename + "." + extension);

    IPixel[][] imageGrid = image.getImage();

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        IPixel currPix = imageGrid[i][j];
        int red = currPix.getRed();
        int green = currPix.getGreen();
        int blue = currPix.getBlue();
        int rgb = (red << 16 | green << 8 | blue);
        img.setRGB(j, i, rgb);
      }
    }

    try {
      ImageIO.write(img, extension, file);
    } catch (IOException e) {
      throw new IllegalArgumentException("cannot export image.");
    }
  }

}
