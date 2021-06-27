import model.image.IImage;
import model.image.IPixel;
import model.image.Image;
import model.image.Pixel;
import model.layer.ILayer;
import model.layer.Layer;
import model.managers.InputJPEGPNGFilenameManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Represents the test class for {@code ILayer} to ensure that the layer's behavior works as
 * expected and they are properly created.
 */
public class ILayerTest {

  ILayer layer1;
  ILayer exLayer;
  ILayer exLayer2;
  IPixel[][] grid;
  IImage exImage;

  @Before
  public void setUp() {
    layer1 = new Layer("res/correct/flower.jpeg");
    layer1.setImage(new InputJPEGPNGFilenameManager("res/correct/flower.jpeg").apply());
    exLayer = new Layer("first");
    exLayer2 = new Layer("second");
    grid = new IPixel[][]{
        {new Pixel(0, 0, 255, 0, 0), new Pixel(0, 1, 0, 0, 0),
            new Pixel(0, 2, 255, 0, 0), new Pixel(0, 3, 0, 0, 0)},
        {new Pixel(1, 0, 0, 0, 0), new Pixel(1, 1, 255, 0, 0),
            new Pixel(1, 2, 0, 0, 0), new Pixel(1, 3, 255, 0, 0)},
        {new Pixel(2, 0, 255, 0, 0), new Pixel(2, 1, 0, 0, 0),
            new Pixel(2, 2, 255, 0, 0), new Pixel(2, 3, 0, 0, 0)},
        {new Pixel(3, 0, 0, 0, 0), new Pixel(3, 1, 255, 0, 0),
            new Pixel(3, 2, 0, 0, 0), new Pixel(3, 3, 255, 0, 0)}};
    exImage = new Image(grid, "Grid");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullName() {
    new Layer(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetNullImage() {
    exLayer.setImage(null);
  }

  @Test
  public void testSetImage() {
    assertNull(exLayer.getImage());
    exLayer.setImage(exImage);
    assertArrayEquals(exImage.getImage(), exLayer.getImage().getImage());
  }

  @Test
  public void testSetVisibility() {
    assertTrue(exLayer.isVisible());
    exLayer.setVisibility(false);
    assertFalse(exLayer.isVisible());
  }

  @Test
  public void testSetSameVisibility() {
    assertTrue(exLayer.isVisible());
    exLayer.setVisibility(true);
    assertTrue(exLayer.isVisible());
  }

  @Test
  public void testGetName() {
    assertEquals("first", exLayer.getName());
  }

  @Test
  public void testGetNullImage() {
    assertNull(exLayer.getImage());
  }

  @Test
  public void testGetImage() {
    exLayer.setImage(exImage);
    assertArrayEquals(exImage.getImage(), exLayer.getImage().getImage());
  }

  @Test
  public void isTrueVisible() {
    assertTrue(exLayer.isVisible());
  }

  @Test
  public void isFalseVisible() {
    exLayer.setVisibility(false);
    assertFalse(exLayer.isVisible());
  }

  @Test
  public void testToStringNoImageAssociated() {
    ILayer layer = new Layer("layer");

    assertEquals("Name of Layer: layer, No Image Associated With This Layer, "
            + "Visibility: true",
        layer.toString());
  }

  @Test
  public void testToStringImageAssociated() {
    ILayer layer = new Layer("layer");
    layer.setImage(new Image("res/checkerboard1.ppm"));

    assertEquals("Name of Layer: layer, Image Filename: res/checkerboard1.ppm, "
            + "Visibility: true",
        layer.toString());
  }

  @Test
  public void testEqualsDifferentObject() {
    assertNotEquals("hello", layer1);
  }

  @Test
  public void testEquals() {
    ILayer layer2 = new Layer("res/correct/flower.png");
    layer2.setImage(new InputJPEGPNGFilenameManager("res/correct/flower.png").apply());
    ILayer layer3 = new Layer("res/correct/flower.jpeg");
    layer3.setImage(new InputJPEGPNGFilenameManager("res/correct/flower.jpeg").apply());
    ILayer layer4 = new Layer("res/correct/flower.jpeg");
    layer4.setImage(new InputJPEGPNGFilenameManager("res/correct/flower.jpeg").apply());
    layer4.setVisibility(false);

    assertNotEquals(layer2, layer1);
    assertEquals(layer1, layer1);
    assertEquals(layer1, layer3);
    assertNotEquals(layer1, layer4);
  }

  @Test
  public void testHashcodeIntensional() {
    assertEquals(layer1.hashCode(), layer1.hashCode());
  }

  @Test
  public void testHashcodeExtensional() {
    ILayer layer1Copy = new Layer("res/correct/flower.jpeg");
    layer1Copy.setImage(new InputJPEGPNGFilenameManager("res/correct/flower.jpeg").apply());

    assertEquals(layer1Copy.hashCode(), layer1.hashCode());
  }
}
