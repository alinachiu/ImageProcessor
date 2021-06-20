import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.image.IPixel;
import org.junit.Test;
import utils.AdditionalImageUtils;
import model.image.IImage;
import model.image.Image;
import model.image.Pixel;
import model.managers.IOManager;
import model.managers.InputPNGFilenameManager;
import org.junit.Before;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Represents the test class for {@code AdditionalImageUtils} to ensure that the object's behavior
 * works as expected and returns the correct values.
 */
public class AdditionalImageUtilsTest {

  IPixel[][] grid1;
  IImage exImage;


  @Before
  public void setUp() {
    grid1 = new IPixel[][]{
        {new Pixel(0, 0, 255, 0, 0), new Pixel(0, 1, 0, 0, 0),
            new Pixel(0, 2, 255, 0, 0), new Pixel(0, 3, 0, 0, 0)},
        {new Pixel(1, 0, 0, 0, 0), new Pixel(1, 1, 255, 0, 0),
            new Pixel(1, 2, 0, 0, 0), new Pixel(1, 3, 255, 0, 0)},
        {new Pixel(2, 0, 255, 0, 0), new Pixel(2, 1, 0, 0, 0),
            new Pixel(2, 2, 255, 0, 0), new Pixel(2, 3, 0, 0, 0)},
        {new Pixel(3, 0, 0, 0, 0), new Pixel(3, 1, 255, 0, 0),
            new Pixel(3, 2, 0, 0, 0), new Pixel(3, 3, 255, 0, 0)}};
    exImage = new Image(grid1, "Grid1");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadPNGJPEGNullFile() throws IOException {
    IPixel[][] grid = AdditionalImageUtils.readPNGJPEG(null);
  }

  @Test
  public void testConvertFlowerJPEGImageFile() throws IOException {
    BufferedImage image = ImageIO.read(new File("res/correct/flower.jpeg"));
    IPixel[][] grid = AdditionalImageUtils.readPNGJPEG(new File("res/correct/flower.jpeg"));

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        assertEquals(new Color(image.getRGB(j, i)).getRed(), grid[i][j].getRed());
        assertEquals(new Color(image.getRGB(j, i)).getGreen(), grid[i][j].getGreen());
        assertEquals(new Color(image.getRGB(j, i)).getBlue(), grid[i][j].getBlue());
      }
    }
  }

  @Test
  public void testConvertFlowerPNGImageFile() throws IOException {
    BufferedImage image = ImageIO.read(new File("res/correct/flower.png"));
    IPixel[][] grid = AdditionalImageUtils.readPNGJPEG(new File("res/correct/flower.png"));

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        assertEquals(new Color(image.getRGB(j, i)).getRed(), grid[i][j].getRed());
        assertEquals(new Color(image.getRGB(j, i)).getGreen(), grid[i][j].getGreen());
        assertEquals(new Color(image.getRGB(j, i)).getBlue(), grid[i][j].getBlue());
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportWithTypeNullImage() {
    AdditionalImageUtils.exportWithType(null, "test", "jpeg");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportWithTypeNullFilename() {
    AdditionalImageUtils.exportWithType(exImage, null, "jpeg");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExportWithTypeNullExtension() {
    AdditionalImageUtils.exportWithType(exImage, "test", null);
  }

  @Test
  public void testExportWithType() {
    AdditionalImageUtils.exportWithType(exImage, "Example", "png");
    IOManager importer = new InputPNGFilenameManager("Example.png");
    IImage img = importer.apply();
    assertArrayEquals(grid1, img.getImage());
    assertEquals("Example.png", img.getFilename());
    File file = new File("Example.png");
    file.delete();
  }
}
