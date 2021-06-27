import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.PrintStream;
import model.LayerModelState;
import model.image.IImage;
import model.image.IPixel;
import model.image.Image;
import model.image.Pixel;
import model.layer.ILayerModel;
import model.layer.LayerModel;
import model.mocks.FailingAppendable;
import org.junit.Before;
import org.junit.Test;
import view.SimpleIImageProcessingView;

/**
 * Represents the test class for {@code IImageProcessingView} to ensure that the views' behavior
 * works as expected and properly deals with displaying text.
 */
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
    new SimpleIImageProcessingView(new LayerModelState(new LayerModel()), null);
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
      new SimpleIImageProcessingView(new LayerModelState(new LayerModel()), s)
          .renderMessage("Hello");
    } catch (IOException e) {
      throw new IllegalStateException();
    }
  }

  @Test(expected = IllegalStateException.class)
  public void failedAppendable() {
    try {
      new SimpleIImageProcessingView(new LayerModelState(new LayerModel()),
          new FailingAppendable()).renderMessage("Hello");
    } catch (IOException e) {
      throw new IllegalStateException();
    }
  }

  @Test
  public void testRenderMessage() {
    StringBuilder out = new StringBuilder();
    try {
      new SimpleIImageProcessingView(new LayerModelState(new LayerModel()), out)
          .renderMessage("Testing render message...");
    } catch (IOException e) {
      throw new IllegalStateException();
    }
    assertEquals("Testing render message...", out.toString());
  }

  @Test
  public void testRenderNullMessage() {
    StringBuilder out = new StringBuilder();
    try {
      new SimpleIImageProcessingView(new LayerModelState(new LayerModel()), out)
          .renderMessage(null);
    } catch (IOException e) {
      throw new IllegalStateException();
    }
    assertEquals("null", out.toString());
  }
}
