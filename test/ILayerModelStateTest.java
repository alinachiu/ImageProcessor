import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import model.ILayerModelState;
import model.LayerModelState;
import model.image.Image;
import model.layer.ILayerModel;
import model.layer.Layer;
import model.layer.LayerModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents the test class for {@link ILayerModelState} to ensure that everything functions as
 * expected and that the correct state of the model is returned.
 */
public class ILayerModelStateTest {

  ILayerModelState modelState;
  ILayerModel model;

  @Before
  public void initData() {
    this.model = new LayerModel();
    this.modelState = new LayerModelState(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionNullModel() {
    ILayerModelState state = new LayerModelState(null);
  }

  @Test
  public void testGetNumLayers() {
    assertEquals(0, modelState.getNumLayers());
    this.model.createImageLayer("first");
    this.model.createImageLayer("second");
    this.model.createImageLayer("third");
    assertEquals(3, modelState.getNumLayers());
  }

  @Test
  public void testGetCurrentLayer() {
    assertNull(this.modelState.getCurrentLayer());
    this.model.createImageLayer("first");
    this.model.createImageLayer("second");
    assertEquals(new Layer("first"), this.modelState.getCurrentLayer());
    this.model.setCurrent("second");
    assertEquals(new Layer("second"), this.modelState.getCurrentLayer());
  }

  @Test
  public void testGetTopmostVisibleLayerImageNoLayers() {
    assertNull(modelState.getTopmostVisibleLayerImage());
  }

  @Test
  public void testGetTopmostVisibleLayerImageNoTopmostVisibleLayers() {
    model.createImageLayer("first");
    model.makeLayerInvisible("first");
    assertNull(modelState.getTopmostVisibleLayerImage());
  }

  @Test
  public void testGetTopmostVisibleLayerImageVisibleLayersButNoImage() {
    assertNull(modelState.getTopmostVisibleLayerImage());
    model.createImageLayer("first");
    model.createImageLayer("second");
    model.createImageLayer("third");
    assertNull(modelState.getTopmostVisibleLayerImage());
  }

  @Test
  public void testGetTopmostVisibleLayerImageCorrect() {
    assertNull(modelState.getTopmostVisibleLayerImage());
    model.createImageLayer("first");
    model.createImageLayer("second");
    model.createImageLayer("third");
    model.loadLayer(new Image("res/puppy.ppm"));
    assertArrayEquals(new Image("res/puppy.ppm").getImage(),
        modelState.getTopmostVisibleLayerImage().getImage());
    assertEquals(new Image("res/puppy.ppm").getFilename(),
        modelState.getTopmostVisibleLayerImage().getFilename());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLayerNegative() {
    modelState.getLayer(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetLayerTooLarge() {
    modelState.getLayer(10);
  }

  @Test
  public void testGetLayerCorrect() {
    model.createImageLayer("first");
    model.createImageLayer("second");
    assertEquals(new Layer("first"), modelState.getLayer(0));
    assertEquals(new Layer("second"), modelState.getLayer(1));
  }
}
