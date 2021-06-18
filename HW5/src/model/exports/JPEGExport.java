package model.exports;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import javax.imageio.ImageIO;
import model.image.IPixel;
import model.image.IImage;
import model.layer.ILayer;

/**
 * Represents a class which manages a given {@code IImage} and exports the Image based on this
 * object's {@link Writer} to JPEG format.
 */
public class JPEGExport implements IExport {

  private final IImage image;
  private final String filename;

  /**
   * Constructs a {@code JPEGExport} object with a default Writer for writing a file.
   *
   * @param image the given image to be converted into a file
   * @throws IllegalArgumentException if the given image is null
   * @throws IOException              if an I/O error occurs
   */
  public JPEGExport(IImage image) throws IllegalArgumentException, IOException {
    if (image == null) {
      throw new IllegalArgumentException("Cannot have a null image.");
    }
    this.image = image;
    String[] withoutExtension = image.getFilename().toLowerCase().split(".jpeg");
    this.filename = withoutExtension[0] + " New";
  }

  /**
   * Constructs a {@code JPEGExport} object with a default Writer for writing a file.
   *
   * @param image the given image to be converted into a file
   * @param desiredName the desired filename for the exported file
   * @throws IllegalArgumentException if the given image is null
   * @throws IOException              if an I/O error occurs
   */
  public JPEGExport(IImage image, String desiredName) throws IllegalArgumentException, IOException {
    if (image == null || desiredName == null) {
      throw new IllegalArgumentException("Cannot have a null image or name.");
    }
    this.image = image;
    this.filename = desiredName;
  }

  @Override
  public void export() throws IOException {
    BufferedImage img = new BufferedImage(image.getWidth(), image.getHeight(),
        BufferedImage.TYPE_INT_RGB);
    File file = new File(this.filename + ".jpeg");

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

    ImageIO.write(img, "jpeg", file);
  }
}