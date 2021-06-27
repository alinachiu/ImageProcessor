import model.image.IImage;
import model.image.IPixel;
import model.image.Image;
import model.image.Pixel;
import model.mosaic.IPhotoEffect;
import model.mosaic.Mosaic;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Represents a class that tests the mosaic extra credit to ensure that the correct exceptions are
 * thrown and that an image is mosaicked properly.
 */
public class MosaicTest {

  IImage image;
  IPhotoEffect mosaic;

  @Before
  public void initData() {
    IPixel[][] grid = new IPixel[][]{
        {new Pixel(0, 0, 255, 0, 3), new Pixel(0, 1, 0, 14, 0),
            new Pixel(0, 2, 255, 0, 30), new Pixel(0, 3, 0, 20, 2)},
        {new Pixel(1, 0, 0, 210, 0), new Pixel(1, 1, 255, 0, 13),
            new Pixel(1, 2, 0, 0, 0), new Pixel(1, 3, 255, 10, 0)},
        {new Pixel(2, 0, 255, 17, 0), new Pixel(2, 1, 0, 0, 0),
            new Pixel(2, 2, 255, 20, 0), new Pixel(2, 3, 0, 32, 0)},
        {new Pixel(3, 0, 0, 0, 0), new Pixel(3, 1, 255, 0, 0),
            new Pixel(3, 2, 0, 0, 0), new Pixel(3, 3, 255, 102, 0)}};
    this.image = new Image(grid, "exImage");
    this.mosaic = new Mosaic();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMosaicApplyNullImage() {
    this.mosaic.apply(null, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMosaicApplyExceptionNegativeSeeds() {
    this.mosaic.apply(image, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMosaicNumSeedsTooMany() {
    this.mosaic.apply(image, 100);
  }

  @Test
  public void testMosaic10Seeds() {
    IImage newImage = this.mosaic.apply(image, 10);
    assertEquals(newImage.getImage().length, this.image.getImage().length);
    assertEquals(newImage.getImage()[0].length, this.image.getImage()[0].length);
    assertNotEquals(newImage.getImage(), this.image.getImage());
  }

  @Test
  public void testMosaic1Seed() {
    IImage newImage = this.mosaic.apply(image, 1);
    assertArrayEquals(newImage.getImage(), new IPixel[][]{
        {new Pixel(0, 0, 127, 26, 3), new Pixel(0, 1, 127, 26, 3),
            new Pixel(0, 2, 127, 26, 3), new Pixel(0, 3, 127, 26, 3)},
        {new Pixel(1, 0, 127, 26, 3), new Pixel(1, 1, 127, 26, 3),
            new Pixel(1, 2, 127, 26, 3), new Pixel(1, 3, 127, 26, 3)},
        {new Pixel(2, 0, 127, 26, 3), new Pixel(2, 1, 127, 26, 3),
            new Pixel(2, 2, 127, 26, 3), new Pixel(2, 3, 127, 26, 3)},
        {new Pixel(3, 0, 127, 26, 3), new Pixel(3, 1, 127, 26, 3),
            new Pixel(3, 2, 127, 26, 3), new Pixel(3, 3, 127, 26, 3)}});
  }

}
