package utils;

import controller.GraphicalImageProcessingController;
import controller.SimpleIImageProcessingController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Scanner;
import controller.IImageProcessingController;
import model.LayerModelState;
import model.image.IPixel;
import model.image.Pixel;
import model.layer.ILayerModel;
import model.layer.LayerModel;
import view.MyWindow;

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
    int height = sc.nextInt();

    Pixel[][] pixels = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
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
  public static void main(String[] args) throws IOException {
    Appendable out = System.out;
    switch (args.length) {
      case 2:
        if (args[0].equalsIgnoreCase("-script")) {
          File in = new File(args[1]);
          IImageProcessingController controller = new SimpleIImageProcessingController(
              new LayerModel(),
              in, out);

          controller.processImage();
        } else {
          // for invalid commands, display error message and quit
          System.out.println("Invalid command!");
          try {
            Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
        break;
      case 1:
        if (args[0].equalsIgnoreCase("-text")) {
          Readable in = new InputStreamReader(System.in);
          IImageProcessingController controller = new SimpleIImageProcessingController(
              new LayerModel(), in, out);

          controller.processImage();
        } else if (args[0].equalsIgnoreCase("-interactive")) {
          ILayerModel model = new LayerModel();
          IImageProcessingController controller = new GraphicalImageProcessingController(
              model, new MyWindow(new LayerModelState(model)));

          controller.processImage();
        } else {
          // for invalid commands, display error message and quit
          System.out.println("Invalid command!");
          new SimpleIImageProcessingController(new LayerModel(), new StringReader("q"), out);
        }
        break;
      default:
        // for invalid commands, display error message and quit
        System.out.println("Invalid command!");
        try {
          Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
        } catch (Exception e) {
          e.printStackTrace();
        }
        break;
    }
  }
}