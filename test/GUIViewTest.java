import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Represents the test class for {@code ControllerUtils} to ensure that its static method returns a
 * Map with the correct commands.
 */
public class GUIViewTest {

  MockView view;
  MockController controller;
  Appendable ap;

  @Before
  public void initData() {
    this.ap = new StringBuilder();
    this.view = new MockView(ap);
    this.controller = new MockController(view, ap);
  }

  @Test
  public void testMakeCurrentWiring() {
    assertEquals("", this.ap.toString());
    this.controller.processImage();
    this.view.fireMakeCurrentEvent("button");
    assertEquals("handleMakeCurrentEvent", this.ap.toString());
  }

  @Test
  public void testSepiaWiring() {
    assertEquals("", this.ap.toString());
    this.controller.processImage();
    this.view.fireSepiaEvent();
    assertEquals("handleSepiaEvent", this.ap.toString());
  }

  @Test
  public void testGrayscaleWiring() {
    assertEquals("", this.ap.toString());
    this.controller.processImage();
    this.view.fireGrayscaleEvent();
    assertEquals("handleGrayscaleEvent", this.ap.toString());
  }

  @Test
  public void testBlurWiring() {
    assertEquals("", this.ap.toString());
    this.controller.processImage();
    this.view.fireBlurEvent();
    assertEquals("handleBlurEvent", this.ap.toString());
  }

  @Test
  public void testSharpenWiring() {
    assertEquals("", this.ap.toString());
    this.controller.processImage();
    this.view.fireSharpenEvent();
    assertEquals("handleSharpenEvent", this.ap.toString());
  }

  @Test
  public void testCreateLayerWiring() {
    assertEquals("", this.ap.toString());
    this.controller.processImage();
    this.view.fireCreateLayerEvent("");
    assertEquals("handleCreateLayerEvent", this.ap.toString());
  }

  @Test
  public void testRemoveLayerWiring() {
    assertEquals("", this.ap.toString());
    this.controller.processImage();
    this.view.fireRemoveLayerEvent();
    assertEquals("handleRemoveLayerEvent", this.ap.toString());
  }

  @Test
  public void testLoadWiring() {
    assertEquals("", this.ap.toString());
    this.controller.processImage();
    this.view.fireLoadLayerEvent("");
    assertEquals("handleLoadLayerEvent", this.ap.toString());
  }

  @Test
  public void testLoadAllWiring() {
    assertEquals("", this.ap.toString());
    this.controller.processImage();
    this.view.fireLoadAllEvent("");
    assertEquals("handleLoadAllEvent", this.ap.toString());
  }

  @Test
  public void testLoadScriptWiring() {
    assertEquals("", this.ap.toString());
    this.controller.processImage();
    this.view.fireLoadScriptEvent("");
    assertEquals("handleLoadScriptEvent", this.ap.toString());
  }

  @Test
  public void testSaveTopmostVisibleLayerWiring() {
    assertEquals("", this.ap.toString());
    this.controller.processImage();
    this.view.fireSaveTopmostVisibleLayerEvent("");
    assertEquals("handleSaveTopmostVisibleLayerEvent", this.ap.toString());
  }

  @Test
  public void testSaveAllWiring() {
    assertEquals("", this.ap.toString());
    this.controller.processImage();
    this.view.fireSaveAllEvent("");
    assertEquals("handleSaveAllEvent", this.ap.toString());
  }

  @Test
  public void testMakeLayerInvisibleWiring() {
    assertEquals("", this.ap.toString());
    this.controller.processImage();
    this.view.fireMakeLayerInvisibleEvent();
    assertEquals("handleMakeLayerInvisibleEvent", this.ap.toString());
  }

  @Test
  public void testMakeLayerVisibleWiring() {
    assertEquals("", this.ap.toString());
    this.controller.processImage();
    this.view.fireMakeLayerVisibleEvent();
    assertEquals("handleMakeLayerVisibleEvent", this.ap.toString());
  }

  @Test
  public void testDownscaleWiring() {
    assertEquals("", this.ap.toString());
    this.controller.processImage();
    this.view.fireDownscaleEvent(1, 2);
    assertEquals("handleDownscaleEvent", this.ap.toString());
  }

  @Test
  public void testMosaicWiring() {
    assertEquals("", this.ap.toString());
    this.controller.processImage();
    this.view.fireMosaicEvent(1);
    assertEquals("handleMosaicEvent", this.ap.toString());
  }

  @Test
  public void testCheckerboardEventWiring() {
    assertEquals("", this.ap.toString());
    this.controller.processImage();
    this.view.fireCheckerboardEvent(1, 1, 1, 1, 1, 1, 1, 1);
    assertEquals("handleCheckerboardEvent", this.ap.toString());
  }

  @Test
  public void testCheckerboardDefaultColorWiring() {
    assertEquals("", this.ap.toString());
    this.controller.processImage();
    this.view.fireCheckerboardDefaultColorEvent(1, 1);
    assertEquals("handleCheckerboardDefaultColorEvent", this.ap.toString());
  }
}
