package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import model.color.Grayscale;
import model.color.IColorTransformation;
import model.color.Sepia;
import model.controller.IImageProcessingController;
import model.controller.SimpleIImageProcessingController;
import model.exports.IExport;
import model.exports.JPEGExport;
import model.exports.PNGExport;
import model.exports.PPMExport;
import model.filter.Blur;
import model.filter.IFilter;
import model.filter.Sharpening;
import model.creator.CheckboardImageCreator;
import model.creator.IImageCreator;
import model.image.IImage;
import model.image.IPixel;
import model.image.PPMImage;
import model.image.Pixel;
import model.layer.ILayer;
import model.layer.ILayerModel;
import model.layer.LayerModel;
import model.managers.IOManager;
import model.managers.InputJPEGFilenameManager;
import model.managers.InputPNGFilenameManager;

/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file.
   * @return a 2D array of pixels that represents a PPM image
   * @throws IllegalArgumentException if the given filename is null or the file is not found
   */
  public static IPixel[][] readPPM(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("No valid filename given.");
    }
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + filename + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    // now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    // System.out.println("Width of image: " + width);
    int height = sc.nextInt();
    // System.out.println("Height of image: " + height);
    int maxValue = sc.nextInt();
    // System.out.println("Maximum value of a color in this file (usually 256): " + maxValue);

    Pixel[][] pixels = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        // System.out.println("Color of pixel (" + j + "," + i + "): " + r + "," + g + "," + b);
        pixels[i][j] = new Pixel(i, j, r, g, b);
      }
    }

    return pixels;
  }

  /**
   * Runs the program to test output for methods within the project.
   *
   * @param args the string argument
   */
  public static void main(String[] args) {
    String filename;

    if (args.length > 0) {
      filename = args[0];
    } else {
      filename = "res/flower.jpeg";
    }

    Appendable out = System.out;
    Readable in = new InputStreamReader(System.in);
    IImageProcessingController controller = new SimpleIImageProcessingController(new LayerModel(),
        in, out);

    controller.processImage();
  }
}