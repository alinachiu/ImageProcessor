import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import model.creator.CheckboardImageCreator;
import model.creator.IImageCreator;
import model.downscale.Downscale;
import model.downscale.IImageResize;
import model.image.IImage;
import model.image.IPixel;
import model.image.Pixel;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests for methods within the IImageResize interface (testing the downscale effect
 * specifically).
 */
public class DownscaleTest {

  IImageResize downscale;
  IImage exImg;

  @Before
  public void setUp() {
    downscale = new Downscale();
    IImageCreator cbCreator = new CheckboardImageCreator(4, 2);
    exImg = cbCreator.createImage();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullImage() {
    new Downscale().apply(null, 100, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroWidthImage() {
    new Downscale().apply(exImg, 0, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidWidthImage() {
    new Downscale().apply(exImg, 100, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroHeightImage() {
    new Downscale().apply(exImg, 3, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidHeightImage() {
    new Downscale().apply(exImg, 3, 50);
  }

  @Test
  public void testApplyDownscaleForSmallerWidthAndSmallerHeight() {
    IPixel[][] newGrid = new IPixel[][]{
        {new Pixel(0, 0, 255, 0, 0), new Pixel(0, 1, 255, 0, 0), new Pixel(0, 2, 0, 0, 0)},
        {new Pixel(1, 0, 0, 0, 0), new Pixel(1, 1, 0, 0, 0), new Pixel(1, 2, 255, 0, 0)}};
    assertArrayEquals(newGrid, downscale.apply(exImg, 3, 2).getImage());
    assertEquals("Checkerboard", downscale.apply(exImg, 3, 2).getFilename());

  }

  @Test
  public void testApplyDownscaleForSmallerWidthAndSameHeight() {
    IPixel[][] newGrid = new IPixel[][]{
        {new Pixel(0, 0, 255, 0, 0), new Pixel(0, 1, 0, 0, 0)},
        {new Pixel(1, 0, 255, 0, 0), new Pixel(1, 1, 0, 0, 0)},
        {new Pixel(2, 0, 255, 0, 0), new Pixel(2, 1, 0, 0, 0)},
        {new Pixel(3, 0, 255, 0, 0), new Pixel(3, 1, 0, 0, 0)},
        {new Pixel(4, 0, 0, 0, 0), new Pixel(4, 1, 255, 0, 0)},
        {new Pixel(5, 0, 0, 0, 0), new Pixel(5, 1, 255, 0, 0)},
        {new Pixel(6, 0, 0, 0, 0), new Pixel(6, 1, 255, 0, 0)},
        {new Pixel(7, 0, 0, 0, 0), new Pixel(7, 1, 0, 0, 0)}};
    assertArrayEquals(newGrid, downscale.apply(exImg, 2, 8).getImage());
    assertEquals("Checkerboard", downscale.apply(exImg, 2, 8).getFilename());
  }

  @Test
  public void testApplyDownscaleForSameWidthAndSmallerHeight() {
    IPixel[][] newGrid = new IPixel[][]{
        {new Pixel(0, 0, 255, 0, 0), new Pixel(0, 1, 255, 0, 0), new Pixel(0, 2, 255, 0, 0),
            new Pixel(0, 3, 255, 0, 0), new Pixel(0, 4, 0, 0, 0), new Pixel(0, 5, 0, 0, 0),
            new Pixel(0, 6, 0, 0, 0), new Pixel(0, 7, 0, 0, 0)},
        {new Pixel(1, 0, 255, 0, 0), new Pixel(1, 1, 255, 0, 0), new Pixel(1, 2, 255, 0, 0),
            new Pixel(1, 3, 255, 0, 0), new Pixel(1, 4, 0, 0, 0), new Pixel(1, 5, 0, 0, 0),
            new Pixel(1, 6, 0, 0, 0), new Pixel(1, 7, 0, 0, 0)},
        {new Pixel(2, 0, 0, 0, 0), new Pixel(2, 1, 0, 0, 0), new Pixel(2, 2, 0, 0, 0),
            new Pixel(2, 3, 0, 0, 0), new Pixel(2, 4, 255, 0, 0), new Pixel(2, 5, 255, 0, 0),
            new Pixel(2, 6, 255, 0, 0), new Pixel(2, 7, 0, 0, 0)}};
    assertArrayEquals(newGrid, downscale.apply(exImg, 8, 3).getImage());
    assertEquals("Checkerboard", downscale.apply(exImg, 8, 3).getFilename());
  }

  @Test
  public void testApplyDownscaleForSameWidthAndSameHeight() {
    IPixel[][] sameGrid = exImg.getImage();
    sameGrid[4][7] = new Pixel(4, 7, 0, 0, 0);
    sameGrid[5][7] = new Pixel(5, 7, 0, 0, 0);
    sameGrid[6][7] = new Pixel(6, 7, 0, 0, 0);
    sameGrid[7][7] = new Pixel(7, 7, 0, 0, 0);
    sameGrid[7][4] = new Pixel(7, 4, 0, 0, 0);
    sameGrid[7][5] = new Pixel(7, 5, 0, 0, 0);
    sameGrid[7][6] = new Pixel(7, 6, 0, 0, 0);
    assertArrayEquals(sameGrid, downscale.apply(exImg, 8, 8).getImage());
    assertEquals("Checkerboard", downscale.apply(exImg, 8, 8).getFilename());
  }
}
