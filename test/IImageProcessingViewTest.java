import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.PrintStream;
import model.image.IImage;
import model.image.IPixel;
import model.image.Image;
import model.image.Pixel;
import model.layer.ILayerModel;
import model.layer.LayerModel;
import model.mocks.FailingAppendable;
import org.junit.Before;
import org.junit.Test;
import view.IImageProcessingView;
import view.SimpleIImageProcessingView;

public class IImageProcessingViewTest {

  ILayerModel model;
  IImage exImage;

  @Before
  public void initData() {
    this.model = new LayerModel();
    IPixel[][] grid = new IPixel[][]{
        {new Pixel(0, 0, 255, 0, 0), new Pixel(0, 1, 0, 0, 0),
            new Pixel(0, 2, 255, 0, 0), new Pixel(0, 3, 0, 0, 0)},
        {new Pixel(1, 0, 0, 0, 0), new Pixel(1, 1, 255, 0, 0),
            new Pixel(1, 2, 0, 0, 0), new Pixel(1, 3, 255, 0, 0)},
        {new Pixel(2, 0, 255, 0, 0), new Pixel(2, 1, 0, 0, 0),
            new Pixel(2, 2, 255, 0, 0), new Pixel(2, 3, 0, 0, 0)},
        {new Pixel(3, 0, 0, 0, 0), new Pixel(3, 1, 255, 0, 0),
            new Pixel(3, 2, 0, 0, 0), new Pixel(3, 3, 255, 0, 0)}};
    exImage = new Image(grid, "Image");
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullModel() {
    new SimpleIImageProcessingView(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullModelAndNonNullAppendable() {
    new SimpleIImageProcessingView(null, new StringBuilder());
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullAppendable() {
    new SimpleIImageProcessingView(new LayerModel(), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullModelAndAppendable() {
    new SimpleIImageProcessingView(null, null);
  }


  @Test(expected = IllegalStateException.class)
  public void failedRenderMessage() {
    PrintStream s;
    try {
      s = new PrintStream("");
      new SimpleIImageProcessingView(new LayerModel(), s).renderMessage("Hello");
    } catch (IOException e) {
      throw new IllegalStateException();
    }
  }

  @Test(expected = IllegalStateException.class)
  public void failedAppendable() {
    try {
      new SimpleIImageProcessingView(new LayerModel(), new FailingAppendable()).renderMessage("Hello");
    } catch (IOException e) {
      throw new IllegalStateException();
    }
  }

  @Test
  public void testRenderMessage() {
    StringBuilder out = new StringBuilder();
    try {
      new SimpleIImageProcessingView(new LayerModel(), out).renderMessage("Testing render message...");
    } catch (IOException e) {
      throw new IllegalStateException();
    }
    assertEquals("Testing render message...", out.toString());
  }

  @Test
  public void testRenderNullMessage() {
    StringBuilder out = new StringBuilder();
    try {
      new SimpleIImageProcessingView(new LayerModel(), out).renderMessage(null);
    } catch (IOException e) {
      throw new IllegalStateException();
    }
    assertEquals("null", out.toString());
  }

  @Test
  public void testRenderLayerStateWithNoInputYet() {
    StringBuilder out = new StringBuilder();
    try {
      new SimpleIImageProcessingView(new LayerModel(), out).renderLayerState();
    } catch (IOException e) {
      throw new IllegalStateException();
    }
    assertEquals("Number of valid layers created: 0\n"
        + "Current not yet set.\n", out.toString());
  }

  @Test
  public void testRenderLayerStateWithInput() {
    ILayerModel model = new LayerModel();
    model.createImageLayer("first");
    model.createImageLayer("second");
    model.createImageLayer("third");
    model.setCurrent("second");
    StringBuilder out = new StringBuilder();
    try {
      new SimpleIImageProcessingView(model, out).renderLayerState();
    } catch (IOException e) {
      throw new IllegalStateException();
    }
    assertEquals("Layer #1, Name of Layer: first, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #2, Name of Layer: second, No Image Associated With This Layer, Visibility: true\n"
        + "Layer #3, Name of Layer: third, No Image Associated With This Layer, Visibility: true\n"
        + "Number of valid layers created: 3\n"
        + "Current Layer: Name of Layer: second, No Image Associated With This Layer, Visibility: true\n", out.toString());
  }

  @Test
  public void testRenderLayerStateNoLayers() throws IOException {
    Appendable ap = new StringBuilder();
    IImageProcessingView view = new SimpleIImageProcessingView(model, ap);
    view.renderLayerState();

    assertEquals("Number of valid layers created: 0\n"
        + "Current not yet set.\n", ap.toString());
  }

  @Test
  public void testRenderLayerStateOnlyOneLayerShouldSetCurrentToThisLayer() throws IOException {
    model.createImageLayer("first");
    Appendable ap = new StringBuilder();
    IImageProcessingView view = new SimpleIImageProcessingView(model, ap);
    view.renderLayerState();

    assertEquals(
        "Layer #1, Name of Layer: first, No Image Associated With This Layer, "
            + "Visibility: true\n"
            + "Number of valid layers created: 1\n"
            + "Current Layer: Name of Layer: first, No Image Associated With This Layer, "
            + "Visibility: true\n",
        ap.toString());
  }

  @Test
  public void testToStringWithMultipleLayersOneWithImageOneWithout() throws IOException {
    model.createImageLayer("first");
    model.setCurrent("first");
    model.createImageLayer("second");
    model.setCurrent("second");
    model.loadLayer(exImage);
    model.setCurrent("first");
    model.makeLayerInvisible("first");
    model.createImageLayer("another");

    Appendable ap = new StringBuilder();
    IImageProcessingView view = new SimpleIImageProcessingView(model, ap);
    view.renderLayerState();

    assertEquals(
        "Layer #1, Name of Layer: first, No Image Associated With This Layer, "
            + "Visibility: false\n"
            + "Layer #2, Name of Layer: second, Image Filename: Image, Visibility: true\n"
            + "Layer #3, Name of Layer: another, No Image Associated With This Layer, "
            + "Visibility: true\n"
            + "Number of valid layers created: 3\n"
            + "Current Layer: Name of Layer: first, No Image Associated With This Layer, "
            + "Visibility: false\n",
        ap.toString());
  }
}
