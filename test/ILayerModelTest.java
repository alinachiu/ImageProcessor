import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import model.color.Grayscale;
import model.color.Sepia;
import model.creator.CheckboardImageCreator;
import model.filter.Blur;
import model.filter.Sharpening;
import model.image.IImage;
import model.image.IPixel;
import model.image.Image;
import model.image.Pixel;
import model.layer.ILayer;
import model.layer.ILayerModel;
import model.layer.Layer;
import model.layer.LayerModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents the test class for {@code ILayerModel} to ensure that the model's behavior works as
 * expected and properly deals with its methods.
 */
public class ILayerModelTest {

  ILayerModel model;
  ILayer exLayer;
  ILayer exLayer2;
  ILayer exLayer3;
  IPixel[][] grid1;
  IPixel[][] grid2;
  IPixel[][] grid3;
  IImage exImage;
  IImage exImage2;
  IImage exImage3;

  @Before
  public void setUp() {
    model = new LayerModel();
    exLayer = new Layer("first");
    exLayer2 = new Layer("second");
    exLayer3 = new Layer("third");
    grid1 = new IPixel[][]{
        {new Pixel(0, 0, 255, 0, 0), new Pixel(0, 1, 0, 0, 0),
            new Pixel(0, 2, 255, 0, 0), new Pixel(0, 3, 0, 0, 0)},
        {new Pixel(1, 0, 0, 0, 0), new Pixel(1, 1, 255, 0, 0),
            new Pixel(1, 2, 0, 0, 0), new Pixel(1, 3, 255, 0, 0)},
        {new Pixel(2, 0, 255, 0, 0), new Pixel(2, 1, 0, 0, 0),
            new Pixel(2, 2, 255, 0, 0), new Pixel(2, 3, 0, 0, 0)},
        {new Pixel(3, 0, 0, 0, 0), new Pixel(3, 1, 255, 0, 0),
            new Pixel(3, 2, 0, 0, 0), new Pixel(3, 3, 255, 0, 0)}};
    grid2 = new IPixel[][]{
        {new Pixel(0, 0, 255, 0, 0), new Pixel(0, 1, 0, 0, 0),
            new Pixel(0, 2, 255, 0, 0), new Pixel(0, 3, 0, 0, 0)},
        {new Pixel(1, 0, 0, 0, 0), new Pixel(1, 1, 255, 0, 0),
            new Pixel(1, 2, 0, 0, 0), new Pixel(1, 3, 255, 0, 0)},
        {new Pixel(2, 0, 255, 0, 0), new Pixel(2, 1, 0, 0, 0),
            new Pixel(2, 2, 255, 0, 0), new Pixel(2, 3, 0, 0, 0)},
        {new Pixel(3, 0, 0, 0, 0), new Pixel(3, 1, 255, 0, 0),
            new Pixel(3, 2, 0, 0, 0), new Pixel(3, 3, 255, 0, 0)}};
    exImage = new Image(grid1, "Grid1");
    exImage2 = new Image(grid2, "Grid2");
    grid3 = new IPixel[][]{
        {new Pixel(0, 0, 255, 0, 0), new Pixel(0, 1, 0, 0, 0),
            new Pixel(0, 2, 255, 0, 0), new Pixel(0, 3, 0, 0, 0)},
        {new Pixel(1, 0, 0, 0, 0), new Pixel(1, 1, 255, 0, 0),
            new Pixel(1, 2, 0, 0, 0), new Pixel(1, 3, 255, 0, 0)},
        {new Pixel(2, 0, 255, 0, 0), new Pixel(2, 1, 0, 0, 0),
            new Pixel(2, 2, 255, 0, 0), new Pixel(2, 3, 0, 0, 0)},
        {new Pixel(3, 0, 0, 0, 0), new Pixel(3, 1, 255, 0, 0),
            new Pixel(3, 2, 0, 0, 0), new Pixel(3, 3, 255, 0, 0)}};
    exImage3 = new Image(grid3, "Grid3");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullLayerNameForInvisible() {
    model.makeLayerInvisible(null);
  }

  @Test
  public void testMakeLayerInvisibleFirstLayer() {
    model.createImageLayer("first");
    model.createImageLayer("second");
    model.createImageLayer("third");
    assertTrue(this.model.getLayers().get(0).isVisible());
    model.makeLayerInvisible("first");
    assertFalse(this.model.getLayers().get(0).isVisible());
  }

  @Test
  public void testMakeLayerInvisibleLastLayer() {
    model.createImageLayer("first");
    model.createImageLayer("second");
    model.createImageLayer("third");
    assertTrue(this.model.getLayers().get(2).isVisible());
    model.makeLayerInvisible("third");
    assertFalse(this.model.getLayers().get(2).isVisible());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMakeLayerInvisibleNonExistentNameShouldDoNothing() {
    assertEquals("Number of valid layers created: 0\n"
        + "Current not yet set.\n", model.toString());
    model.createImageLayer("first");
    model.createImageLayer("second");
    model.createImageLayer("third");
    String toStringBeforeAttemptingToMakeInvisible = model.toString();
    model.makeLayerInvisible("fourth");

    assertEquals(toStringBeforeAttemptingToMakeInvisible, model.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullLayerNameForVisible() {
    model.makeLayerVisible(null);
  }

  @Test
  public void testMakeLayerVisibleFirstLayer() {
    model.createImageLayer("first");
    model.createImageLayer("second");
    model.createImageLayer("third");
    assertTrue(this.model.getLayers().get(0).isVisible());
    model.makeLayerInvisible("first");
    assertFalse(this.model.getLayers().get(0).isVisible());
    model.makeLayerVisible("first");
    assertTrue(this.model.getLayers().get(0).isVisible());
  }

  @Test
  public void testMakeLayerVisibleLastLayer() {
    model.createImageLayer("first");
    model.createImageLayer("second");
    model.createImageLayer("third");
    assertTrue(this.model.getLayers().get(2).isVisible());
    model.makeLayerInvisible("third");
    assertFalse(this.model.getLayers().get(2).isVisible());
    model.makeLayerVisible("third");
    assertTrue(this.model.getLayers().get(2).isVisible());
  }

  @Test
  public void testMakeAlreadyVisibleLayerVisible() {
    model.createImageLayer("first");
    model.createImageLayer("second");
    model.createImageLayer("third");
    assertTrue(this.model.getLayers().get(1).isVisible());
    model.makeLayerVisible("second");
    assertTrue(this.model.getLayers().get(1).isVisible());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testMakeLayerVisibleShouldDoNothing() {
    assertEquals("Number of valid layers created: 0\n"
        + "Current not yet set.\n", model.toString());

    model.createImageLayer("first");
    model.createImageLayer("second");
    model.createImageLayer("third");
    String toStringBeforeAttemptingToMakeInvisible = model.toString();
    model.makeLayerInvisible("fourth");

    assertEquals(toStringBeforeAttemptingToMakeInvisible, model.toString());
  }

  @Test
  public void testGetTrueVisibilityAtLayerIndexTwo() {
    model.createImageLayer("first");
    model.setCurrent("first");
    model.loadLayer(exImage);
    model.createImageLayer("second");
    model.setCurrent("second");
    model.loadLayer(exImage2);
    model.createImageLayer("third");
    model.setCurrent("third");
    model.loadLayer(exImage3);
    assertTrue(this.model.getLayers().get(2).isVisible());
  }

  @Test
  public void testGetFalseVisibilityAtLayerIndexTwo() {
    model.createImageLayer("first");
    model.setCurrent("first");
    model.loadLayer(exImage);
    model.createImageLayer("second");
    model.setCurrent("second");
    model.loadLayer(exImage2);
    model.makeLayerInvisible("second");
    model.createImageLayer("third");
    model.setCurrent("third");
    model.loadLayer(exImage3);
    assertFalse(this.model.getLayers().get(1).isVisible());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullFilterCurrentLayer() {
    model.filterCurrent(null);
  }

  @Test
  public void testBlurCurrentSecondLayer() {
    model.createImageLayer("first");
    model.setCurrent("first");
    model.loadLayer(exImage);
    model.createImageLayer("second");
    model.setCurrent("second");
    model.loadLayer(exImage2);
    model.filterCurrent(new Blur());
    IImage blurImg = new Blur().apply(new Image(exImage2.getImage(), exImage2.getFilename()));
    assertArrayEquals(grid1, this.model.getLayers().get(0).getImage().getImage());
    assertEquals("Grid1", this.model.getLayers().get(0).getImage().getFilename());
    assertArrayEquals(blurImg.getImage(), this.model.getLayers().get(1).getImage().getImage());
    assertEquals("Grid2", this.model.getLayers().get(1).getImage().getFilename());
  }

  @Test
  public void testSharpCurrentFirstLayer() {
    model.createImageLayer("first");
    model.setCurrent("first");
    model.loadLayer(exImage);
    model.createImageLayer("second");
    model.setCurrent("second");
    model.loadLayer(exImage2);
    model.setCurrent("first");
    model.filterCurrent(new Sharpening());
    IImage sharpImg = new Sharpening().apply(new Image(exImage.getImage(), exImage.getFilename()));
    assertArrayEquals(grid2, this.model.getLayers().get(1).getImage().getImage());
    assertEquals("Grid2", this.model.getLayers().get(1).getImage().getFilename());
    assertArrayEquals(sharpImg.getImage(), this.model.getLayers().get(0).getImage().getImage());
    assertEquals("Grid1", this.model.getLayers().get(0).getImage().getFilename());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSharpCurrentInvisibleLayer() {
    model.createImageLayer("first");
    model.setCurrent("first");
    model.loadLayer(exImage);
    model.createImageLayer("second");
    model.setCurrent("second");
    model.loadLayer(exImage2);
    model.setCurrent("first");
    model.makeLayerInvisible("first");
    model.filterCurrent(new Sharpening());
    assertArrayEquals(grid1, this.model.getLayers().get(0).getImage().getImage());
    assertEquals("Grid1", this.model.getLayers().get(0).getImage().getFilename());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSharpCurrentNullLayer() {
    model.createImageLayer("first");
    model.setCurrent("first");
    model.createImageLayer("second");
    model.setCurrent("second");
    model.loadLayer(exImage2);
    model.setCurrent("first");
    model.makeLayerInvisible("first");
    model.filterCurrent(new Sharpening());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullColorTransCurrentLayer() {
    model.colorTransformCurrent(null);
  }

  @Test
  public void testGrayCurrentSecondLayer() {
    model.createImageLayer("first");
    model.setCurrent("first");
    model.loadLayer(exImage);
    model.createImageLayer("second");
    model.setCurrent("second");
    model.loadLayer(exImage2);
    model.colorTransformCurrent(new Grayscale());
    IImage grayImg = new Grayscale().apply(new Image(exImage2.getImage(), exImage2.getFilename()));
    assertArrayEquals(grid1, this.model.getLayers().get(0).getImage().getImage());
    assertEquals("Grid1", this.model.getLayers().get(0).getImage().getFilename());
    assertArrayEquals(grayImg.getImage(), this.model.getLayers().get(1).getImage().getImage());
    assertEquals("Grid2", this.model.getLayers().get(1).getImage().getFilename());
  }

  @Test
  public void testSepiaCurrentFirstLayer() {
    model.createImageLayer("first");
    model.setCurrent("first");
    model.loadLayer(exImage);
    model.createImageLayer("second");
    model.setCurrent("second");
    model.loadLayer(exImage2);
    model.setCurrent("first");
    model.colorTransformCurrent(new Sepia());
    IImage sepiaImg = new Sepia().apply(new Image(exImage.getImage(), exImage.getFilename()));
    assertArrayEquals(grid2, this.model.getLayers().get(1).getImage().getImage());
    assertEquals("Grid2", this.model.getLayers().get(1).getImage().getFilename());
    assertArrayEquals(sepiaImg.getImage(), this.model.getLayers().get(0).getImage().getImage());
    assertEquals("Grid1", this.model.getLayers().get(0).getImage().getFilename());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSepiaCurrentInvisibleLayer() {
    model.createImageLayer("first");
    model.setCurrent("first");
    model.loadLayer(exImage);
    model.createImageLayer("second");
    model.setCurrent("second");
    model.loadLayer(exImage2);
    model.setCurrent("first");
    model.makeLayerInvisible("first");
    model.colorTransformCurrent(new Sepia());
    assertArrayEquals(grid1, this.model.getLayers().get(0).getImage().getImage());
    assertEquals("Grid1", this.model.getLayers().get(0).getImage().getFilename());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSepiaCurrentNullLayer() {
    model.createImageLayer("first");
    model.setCurrent("first");
    model.createImageLayer("second");
    model.setCurrent("second");
    model.loadLayer(exImage2);
    model.setCurrent("first");
    model.makeLayerInvisible("first");
    model.colorTransformCurrent(new Sepia());
  }

  @Test
  public void testToStringNoLayers() {
    assertEquals("Number of valid layers created: 0\n"
        + "Current not yet set.\n", model.toString());
  }

  @Test
  public void testToStringStateOnlyOneLayerShouldSetCurrentToThisLayer() {
    model.createImageLayer("first");

    assertEquals("Layer #1, Name of Layer: first, No Image Associated With This Layer, "
        + "Visibility: true\n"
        + "Number of valid layers created: 1\n"
        + "Current Layer: Name of Layer: first, No Image Associated With This Layer, "
        + "Visibility: true\n", model.toString());
  }

  @Test
  public void testToStringWithMultipleLayersOneWithImageOneWithout() {
    model.createImageLayer("first");
    model.setCurrent("first");
    model.createImageLayer("second");
    model.setCurrent("second");
    model.loadLayer(exImage2);
    model.setCurrent("first");
    model.makeLayerInvisible("first");
    assertEquals(
        "Layer #1, Name of Layer: first, No Image Associated With This Layer, "
            + "Visibility: false\n"
            + "Layer #2, Name of Layer: second, Image Filename: Grid2, Visibility: true\n"
            + "Number of valid layers created: 2\n"
            + "Current Layer: Name of Layer: first, No Image Associated With This Layer, "
            + "Visibility: false\n",
        model.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateImageLayerNullString() {
    model.createImageLayer(null);
  }

  @Test
  public void testCreateImageLayer() {
    assertEquals("Number of valid layers created: 0\n"
        + "Current not yet set.\n", model.toString());
    model.createImageLayer("first");

    ILayer firstLayer = model.getLayers().get(0);
    assertEquals(firstLayer.getName(), "first");
    assertNull(firstLayer.getImage());
    assertTrue(firstLayer.isVisible());
    assertEquals(1, model.getLayers().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAttemptToCreateImageLayerWithSameName() {
    assertEquals("Number of valid layers created: 0\n"
        + "Current not yet set.\n", model.toString());
    model.createImageLayer("first");
    model.createImageLayer("first");

    ILayer firstLayer = model.getLayers().get(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveImageLayerNullLayerName() {
    model.removeImageLayer(null);
  }

  @Test
  public void testRemoveImageLayer() {
    model.createImageLayer("first");
    model.createImageLayer("second");
    model.createImageLayer("third");
    assertEquals("Layer #1, Name of Layer: first, No Image Associated With This Layer, "
        + "Visibility: true\n"
        + "Layer #2, Name of Layer: second, No Image Associated With This Layer, "
        + "Visibility: true\n"
        + "Layer #3, Name of Layer: third, No Image Associated With This Layer, "
        + "Visibility: true\n"
        + "Number of valid layers created: 3\n"
        + "Current Layer: Name of Layer: first, No Image Associated With This Layer, "
        + "Visibility: true\n", model.toString());

    assertEquals(3, model.getLayers().size());

    model.removeImageLayer("second");
    assertEquals(2, model.getLayers().size());
    model.removeImageLayer("first");
    assertEquals(1, model.getLayers().size());
    // checks to make sure current is set to third after removing the current first
    assertEquals("Layer #1, Name of Layer: third, No Image Associated With This Layer,"
        + " Visibility: true\n"
        + "Number of valid layers created: 1\n"
        + "Current Layer: Name of Layer: third, No Image Associated With This Layer,"
        + " Visibility: true\n", model.toString());
    model.removeImageLayer("third");
    assertEquals(0, model.getLayers().size());
    // checks to make sure current gets set to nothing after everything is removed
    assertEquals("Number of valid layers created: 0\n"
        + "Current not yet set.\n", model.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveImageLayerNonexistentLayerShouldDoNothing() {
    model.createImageLayer("first");
    model.createImageLayer("second");
    model.createImageLayer("third");

    model.removeImageLayer("fakeLayer");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetCurrentNullLayerName() {
    model.setCurrent(null);
  }

  @Test
  public void testSetCurrentThatIsAlreadyCurrent() {
    model.createImageLayer("first");
    model.createImageLayer("second");
    model.createImageLayer("third");
    String toStringCurrentFirst = model.toString();

    assertEquals(3, model.getLayers().size());

    model.setCurrent("first");
    // makes sure current is still first
    assertEquals(toStringCurrentFirst, model.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetCurrentNonExistentLayerName() {
    model.createImageLayer("first");
    model.createImageLayer("second");
    model.createImageLayer("third");

    model.setCurrent("fakeLayer");
  }

  @Test
  public void testSetCurrentInvisibleLayer() {
    model.createImageLayer("first");
    model.createImageLayer("second");
    model.createImageLayer("third");
    model.makeLayerInvisible("third");
    String toStringCurrentFirst = model.toString();

    assertEquals(3, model.getLayers().size());

    model.setCurrent("third");
    // makes sure current is still first
    assertEquals("Layer #1, Name of Layer: first, No Image Associated With"
        + " This Layer, Visibility: true\n"
        + "Layer #2, Name of Layer: second, No Image Associated With This Layer,"
        + " Visibility: true\n"
        + "Layer #3, Name of Layer: third, No Image Associated With This Layer,"
        + " Visibility: false\n"
        + "Number of valid layers created: 3\n"
        + "Current Layer: Name of Layer: third, No Image Associated With"
        + " This Layer, Visibility: false\n", model.toString());
  }

  @Test
  public void testSetCurrent() {
    model.createImageLayer("first");
    model.createImageLayer("second");
    model.createImageLayer("third");

    assertEquals(3, model.getLayers().size());

    model.setCurrent("third");
    // makes sure current is still first
    assertEquals("Layer #1, Name of Layer: first, No Image Associated With This Layer, "
        + "Visibility: true\n"
        + "Layer #2, Name of Layer: second, No Image Associated With This Layer, "
        + "Visibility: true\n"
        + "Layer #3, Name of Layer: third, No Image Associated With This Layer, "
        + "Visibility: true\n"
        + "Number of valid layers created: 3\n"
        + "Current Layer: Name of Layer: third, No Image Associated With This Layer, "
        + "Visibility: true\n", model.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadAllNullLayers() {
    model.loadAll(null);
  }

  @Test
  public void testLoadAll() {
    assertEquals(0, model.getLayers().size());
    model.loadAll(new ArrayList<>(Arrays.asList(new Layer("first"), new Layer("second"))));
    assertEquals(2, model.getLayers().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadAllImagesWithDifferentDimensions() {
    assertEquals(0, model.getLayers().size());

    ILayer first = new Layer("first");
    ILayer second = new Layer("second");
    first.setImage(new Image("res/puppy.ppm"));
    second.setImage(new Image("res/check.ppm"));

    model.loadAll(new ArrayList<>(Arrays.asList(first, second)));
    assertEquals(2, model.getLayers().size());
  }

  @Test
  public void testLoadAllImagesWithSomeNullImages() {
    assertEquals(0, model.getLayers().size());

    ILayer first = new Layer("first");
    ILayer second = new Layer("second");
    first.setImage(new Image("res/puppy.ppm"));

    model.loadAll(new ArrayList<>(Arrays.asList(first, second)));
    assertEquals(2, model.getLayers().size());
  }

  @Test
  public void testGetLayersNoLayers() {
    assertEquals(0, model.getLayers().size());
    assertEquals(new ArrayList<>(), model.getLayers());
  }

  @Test
  public void testGetLayersSomeLayersWithAndWithoutImages() {
    model.createImageLayer("first");
    model.createImageLayer("second");
    model.createImageLayer("third");
    model.loadLayer(new Image("res/Checkerboard1.ppm"));

    ILayer first = new Layer("first");
    first.setImage(new Image("res/Checkerboard1.ppm"));

    assertEquals(3, model.getLayers().size());
    assertEquals(
        new ArrayList<>(Arrays.asList(first, new Layer("second"), new Layer("third"))),
        model.getLayers());
  }

  @Test
  public void testCreateImageCheckLayers() {
    assertEquals(0, model.getLayers().size());
    model.createImage(new CheckboardImageCreator(1, 2));
    assertEquals(1, model.getLayers().size());
  }

  @Test
  public void testColorTransformationCheckLayers() {
    assertEquals(0, model.getLayers().size());
    model.colorTransformation(new Image("res/check.ppm"), new Sepia());
    assertEquals(1, model.getLayers().size());
  }

  @Test
  public void testFilterCheckLayers() {
    assertEquals(0, model.getLayers().size());
    model.filter(new Image("res/check.ppm"), new Blur());
    assertEquals(1, model.getLayers().size());
  }
}
