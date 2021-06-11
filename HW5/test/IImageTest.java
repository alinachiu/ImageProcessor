import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import model.ImageUtil;
import model.image.IImage;
import model.image.IPixel;
import model.image.PPMImage;
import model.image.Pixel;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests for methods in the IImage interface (and the PPM image class, which implements
 * the IImage class).
 */
public class IImageTest {

  IPixel[][] exImg;
  IImage exPPM;
  IImage checkerboardPMM;

  @Before
  public void setUp() {
    exImg = new IPixel[][]{
        {new Pixel(0, 0, 255, 0, 0), new Pixel(0, 1, 0, 0, 0),
            new Pixel(0, 2, 255, 0, 0), new Pixel(0, 3, 0, 0, 0)},
        {new Pixel(1, 0, 0, 0, 0), new Pixel(1, 1, 255, 0, 0),
            new Pixel(1, 2, 0, 0, 0), new Pixel(1, 3, 255, 0, 0)},
        {new Pixel(2, 0, 255, 0, 0), new Pixel(2, 1, 0, 0, 0),
            new Pixel(2, 2, 255, 0, 0), new Pixel(2, 3, 0, 0, 0)},
        {new Pixel(3, 0, 0, 0, 0), new Pixel(3, 1, 255, 0, 0),
            new Pixel(3, 2, 0, 0, 0), new Pixel(3, 3, 255, 0, 0)}};
    exPPM = new PPMImage(exImg, "Checkerboard");
    checkerboardPMM = new PPMImage("res/Checkerboard.ppm");
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullFilenameSingleConstructor() {
    new PPMImage(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nonExistentFilenameSingleConstructor() {
    new PPMImage("NonexistentFilename");
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidImageConstructor() {
    new PPMImage(null, "Checkerboard");
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidFilenameConstructor() {
    new PPMImage(exImg, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidImageAndFilenameConstructor() {
    new PPMImage(null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void emptyHeightImageForConstructor() {
    new PPMImage(new IPixel[][]{}, "Empty");
  }

  @Test(expected = IllegalArgumentException.class)
  public void emptyRowImageForConstructor() {
    new PPMImage(new IPixel[][]{{}}, "Empty");
  }

  @Test(expected = IllegalArgumentException.class)
  public void createPPMImageWithImageGridWithNullValue() {
    IPixel[][] invalidGrid = {
        {new Pixel(1, 1, 2, 3, 4),
            new Pixel(1, 1, 2, 3, 4),
            new Pixel(1, 1, 2, 3, 4)},
        {new Pixel(1, 1, 2, 3, 4),
            null,
            new Pixel(1, 1, 2, 3, 4)}};
    IImage invalidImage = new PPMImage(invalidGrid, "InvalidPPMImage");
  }

  @Test
  public void getImageWithSingleConstructor() {
    assertArrayEquals(new ImageUtil().readPPM("res/Checkerboard.ppm"), checkerboardPMM.getImage());
  }

  @Test
  public void getImageWithGivenImageConstructor() {
    IPixel[][] imageGrid = exImg;
    assertArrayEquals(imageGrid, exPPM.getImage());
  }

  @Test
  public void getFilenameWithSingleConstructor() {
    assertEquals("res/Checkerboard.ppm", checkerboardPMM.getFilename());
  }

  @Test
  public void getFilenameWithGivenImageConstructor() {
    assertEquals("Checkerboard", exPPM.getFilename());
  }

  @Test
  public void getHeightWithSingleConstructor() {
    assertEquals(16, checkerboardPMM.getHeight());
  }

  @Test
  public void getHeightWithGivenImageConstructor() {
    assertEquals(4, exPPM.getHeight());
  }

  @Test
  public void getWidthWithSingleConstructor() {
    assertEquals(16, checkerboardPMM.getWidth());
  }

  @Test
  public void getWidthWithGivenImageConstructor() {
    assertEquals(4, exPPM.getWidth());
  }

  @Test
  public void testEqualsIntensional() {
    assertEquals(exPPM, exPPM);
  }

  @Test
  public void testEqualsExtensional() {
    IImage copyPPM = new PPMImage(exImg, exPPM.getFilename());
    assertEquals(exPPM, copyPPM);
  }

  @Test
  public void testNotEquals() {
    assertNotEquals(new Pixel(4, 4, 200, 3, 3), exPPM);
  }

  @Test
  public void testHashcodeIntensional() {
    assertEquals(exPPM.hashCode(), exPPM.hashCode());
  }

  @Test
  public void testHashcodeExtensional() {
    IImage copyPPM = new PPMImage(exImg, exPPM.getFilename());
    assertEquals(copyPPM.hashCode(), exPPM.hashCode());
  }
}

