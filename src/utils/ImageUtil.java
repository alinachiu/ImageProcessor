package utils;

import controller.GraphicalImageProcessingController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Scanner;
import controller.IImageProcessingController;
import controller.SimpleIImageProcessingController;
import model.image.IPixel;
import model.image.Pixel;
import model.layer.ILayerModel;
import model.layer.LayerModel;
import view.GraphicalImageProcessingView;
import view.IImageProcessingView;

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
//     Appendable out = System.out;
//     if (args.length == 1) {
//       File in = new File(args[0]);
//       IImageProcessingController controller = new SimpleIImageProcessingController(new LayerModel(),
//           in, out);
//
//       controller.processImage();
//     } else {
//       Readable in = new InputStreamReader(System.in);
//       IImageProcessingController controller = new SimpleIImageProcessingController(new LayerModel(),
//           in, out);
//
//       controller.processImage();
//     }

    ILayerModel model = new LayerModel();
    IImageProcessingView view = new GraphicalImageProcessingView();


    IImageProcessingController controller = new GraphicalImageProcessingController(model, view);
    controller.processImage();
  }
}