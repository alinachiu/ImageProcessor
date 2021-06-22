import model.image.IPixel;
import model.image.Pixel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Represents the test class for {@code IPixel} to ensure that the object's behavior works as
 * expected and properly enforces constructor exceptions.
 */
public class IPixelTest {

  IPixel redPixel;
  IPixel greenPixel;
  IPixel bluePixel;
  IPixel mixedPixel;

  @Before
  public void initData() {
    this.redPixel = new Pixel(1, 1, 255, 0, 0);
    this.greenPixel = new Pixel(2, 3, 0, 255, 0);
    this.bluePixel = new Pixel(5, 4, 0, 0, 255);
    this.mixedPixel = new Pixel(2, 2, 102, 189, 13);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreatePixelWithNegativeX() {
    IPixel negativeXPixel = new Pixel(-1, 0, 0, 2, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreatePixelWithNegativeY() {
    IPixel negativeYPixel = new Pixel(0, -1, 0, 2, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreatePixelNegativeRedValue() {
    IPixel negativeRedPixel = new Pixel(0, 1, -1, 2, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreatePixelNegativeGreenValue() {
    IPixel negativeGreenPixel = new Pixel(0, 20, 0, -12, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreatePixelNegativeBlueValue() {
    IPixel negativeBluePixel = new Pixel(0, 10, 0, 2, -3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreatePixelRedGreaterThan255() {
    IPixel redPixelGreaterThan255 = new Pixel(1, 3, 256, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreatePixelGreenGreaterThan255() {
    IPixel greenPixelGreaterThan255 = new Pixel(1, 3, 0, 260, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreatePixelBlueGreaterThan255() {
    IPixel bluePixelGreaterThan255 = new Pixel(1, 3, 255, 255, 300);
  }

  @Test
  public void testGetX() {
    assertEquals(1, this.redPixel.getX());
    assertEquals(2, this.greenPixel.getX());
  }

  @Test
  public void testGetY() {
    assertEquals(4, this.bluePixel.getY());
    assertEquals(2, this.mixedPixel.getY());
  }

  @Test
  public void testGetRed() {
    assertEquals(255, this.redPixel.getRed());
    assertEquals(0, this.bluePixel.getRed());
    assertEquals(0, this.greenPixel.getRed());
    assertEquals(102, this.mixedPixel.getRed());
  }

  @Test
  public void testGetBlue() {
    assertEquals(0, this.redPixel.getBlue());
    assertEquals(255, this.bluePixel.getBlue());
    assertEquals(0, this.greenPixel.getBlue());
    assertEquals(13, this.mixedPixel.getBlue());
  }

  @Test
  public void testGetGreen() {
    assertEquals(0, this.redPixel.getGreen());
    assertEquals(0, this.bluePixel.getGreen());
    assertEquals(255, this.greenPixel.getGreen());
    assertEquals(189, this.mixedPixel.getGreen());
  }

  @Test
  public void testToString() {
    assertEquals("255 0 0", this.redPixel.toString());
    assertEquals("0 255 0", this.greenPixel.toString());
    assertEquals("0 0 255", this.bluePixel.toString());
    assertEquals("102 189 13", this.mixedPixel.toString());
  }

  @Test
  public void testEquals() {
    assertEquals(new Pixel(1, 1, 255, 0, 0), this.redPixel);
    assertEquals(new Pixel(2, 3, 0, 255, 0), this.greenPixel);
    assertEquals(new Pixel(5, 4, 0, 0, 255), this.bluePixel);
    assertEquals(new Pixel(2, 2, 102, 189, 13), this.mixedPixel);
  }

  @Test
  public void testHashcodeSamePixelSameObject() {
    assertTrue((this.redPixel.equals(this.redPixel)) && (this.redPixel.hashCode()
        == this.redPixel.hashCode()));
  }

  @Test
  public void testHashcodeSamePixelDifferentObject() {
    IPixel sameRedPixel = new Pixel(1, 1, 255, 0, 0);
    assertTrue((this.redPixel.equals(sameRedPixel)) && (this.redPixel.hashCode()
        == sameRedPixel.hashCode()));
  }
}
