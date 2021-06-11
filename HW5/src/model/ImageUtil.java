package model;

import java.io.IOException;
import model.color.Grayscale;
import model.color.IColorTransformation;
import model.color.Sepia;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import model.exports.IExport;
import model.exports.PPMExport;
import model.filter.Blur;
import model.filter.IFilter;
import model.filter.Sharpening;
import model.creator.CheckboardImageCreator;
import model.creator.IImageCreator;
import model.image.IImage;
import model.image.PPMImage;
import model.image.Pixel;

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
   * @throws IllegalArgumentException if the given filename is null
   */
  public static Pixel[][] readPPM(String filename) {
    if (filename == null) {
      throw new IllegalArgumentException("No valid filename given.");
    }
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
      return null;
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
   * @param args the string argument
   */
  public static void main(String[] args) {
    String filename;

    if (args.length > 0) {
      filename = args[0];
    } else {
      filename = "res/ocean.ppm";
    }

    IColorTransformation sepia = new Sepia();
    IColorTransformation gray = new Grayscale();
    IFilter blur = new Blur();
    IFilter sharp = new Sharpening();

    IImageCreator checkerboard = new CheckboardImageCreator(10, 10);
    IImage checkerboardImg = checkerboard.createImage();
    IImage givenFile = new PPMImage(filename);
  }
}