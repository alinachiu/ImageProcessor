package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
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
   * @throws IllegalArgumentException if the given filename is null or the file is not found
   * @throws IOException if an I/O error occurs
   */
  public static IPixel[][] readPNGJPEG(File file) throws IllegalArgumentException, IOException {

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

}
