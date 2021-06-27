import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import view.IGUIView;

/**
 * Represents a method that tests the mocks for the graphical view and controller to make sure that
 * the mocks are set up correctly.
 */
public class MockControllerAndViewTests {

  IGUIView view;
  MockController controller;
  Appendable ap;

  @Before
  public void initData() {
    this.ap = new StringBuilder();
    this.view = new MockView(ap);
    this.controller = new MockController(view, ap);
  }

  @Test
  public void testToStringAddViewEventListenerMockView() {
    assertEquals("Number of listeners in this window: 0", this.view.toString());
    this.view.addViewEventListener(controller);
    assertEquals("Number of listeners in this window: 1", this.view.toString());
  }

  @Test
  public void testRenderMessageMockView() {
    assertEquals("", this.ap.toString());
    this.view.renderMessage("hello");
    assertEquals("hello", this.ap.toString());
  }

  @Test
  public void testProcessImageMockController() {
    assertEquals("Number of listeners in this window: 0", this.view.toString());
    this.controller.processImage();
    assertEquals("Number of listeners in this window: 1", this.view.toString());
  }

  @Test
  public void testHandleMakeCurrentEventMockController() {
    assertEquals("", this.ap.toString());
    this.controller.handleMakeCurrentEvent("hello");
    assertEquals("handleMakeCurrentEvent", this.ap.toString());
  }

  @Test
  public void testHandleSepiaEventMockController() {
    assertEquals("", this.ap.toString());
    this.controller.handleSepiaEvent();
    assertEquals("handleSepiaEvent", this.ap.toString());
  }

  @Test
  public void testHandleBlurEventMockController() {
    assertEquals("", this.ap.toString());
    this.controller.handleBlurEvent();
    assertEquals("handleBlurEvent", this.ap.toString());
  }

  @Test
  public void testHandleGrayscaleMockController() {
    assertEquals("", this.ap.toString());
    this.controller.handleGrayscaleEvent();
    assertEquals("handleGrayscaleEvent", this.ap.toString());
  }

  @Test
  public void testHandleSharpenMockController() {
    assertEquals("", this.ap.toString());
    this.controller.handleSharpenEvent();
    assertEquals("handleSharpenEvent", this.ap.toString());
  }

  @Test
  public void testHandleCreateLayerEventMockController() {
    assertEquals("", this.ap.toString());
    this.controller.handleCreateLayerEvent("");
    assertEquals("handleCreateLayerEvent", this.ap.toString());
  }

  @Test
  public void testHandleRemoveLayerEventMockController() {
    assertEquals("", this.ap.toString());
    this.controller.handleRemoveLayerEvent();
    assertEquals("handleRemoveLayerEvent", this.ap.toString());
  }

  @Test
  public void testHandleLoadLayerEventMockController() {
    assertEquals("", this.ap.toString());
    this.controller.handleLoadLayerEvent("");
    assertEquals("handleLoadLayerEvent", this.ap.toString());
  }

  @Test
  public void testHandleLoadAllMockController() {
    assertEquals("", this.ap.toString());
    this.controller.handleLoadAllEvent("");
    assertEquals("handleLoadAllEvent", this.ap.toString());
  }

  @Test
  public void testHandleLoadScriptMockController() {
    assertEquals("", this.ap.toString());
    this.controller.handleLoadScriptEvent("");
    assertEquals("handleLoadScriptEvent", this.ap.toString());
  }

  @Test
  public void testHandleSaveTopmostVisibleLayerMockController() {
    assertEquals("", this.ap.toString());
    this.controller.handleSaveTopmostVisibleLayerEvent("");
    assertEquals("handleSaveTopmostVisibleLayerEvent", this.ap.toString());
  }

  @Test
  public void testHandleSaveAllMockController() {
    assertEquals("", this.ap.toString());
    this.controller.handleSaveAllEvent("");
    assertEquals("handleSaveAllEvent", this.ap.toString());
  }

  @Test
  public void testHandleMakeLayerInvisibleMockController() {
    assertEquals("", this.ap.toString());
    this.controller.handleMakeLayerInvisibleEvent();
    assertEquals("handleMakeLayerInvisibleEvent", this.ap.toString());
  }

  @Test
  public void testHandleMakeLayerVisibleMockController() {
    assertEquals("", this.ap.toString());
    this.controller.handleMakeLayerVisibleEvent();
    assertEquals("handleMakeLayerVisibleEvent", this.ap.toString());
  }

  @Test
  public void testHandleDownscaleEventMockController() {
    assertEquals("", this.ap.toString());
    this.controller.handleDownscaleEvent(1, 2);
    assertEquals("handleDownscaleEvent", this.ap.toString());
  }

  @Test
  public void testHandleMosaicEventMockController() {
    assertEquals("", this.ap.toString());
    this.controller.handleMosaicEvent(1);
    assertEquals("handleMosaicEvent", this.ap.toString());
  }

  @Test
  public void testHandleCheckerboardEventMockController() {
    assertEquals("", this.ap.toString());
    this.controller.handleCheckerboardEvent(1, 1, 1, 1, 1, 1, 1, 1);
    assertEquals("handleCheckerboardEvent", this.ap.toString());
  }

  @Test
  public void testHandleCheckerboardDefaultColorEventMockController() {
    assertEquals("", this.ap.toString());
    this.controller.handleCheckerboardDefaultColorEvent(1, 1);
    assertEquals("handleCheckerboardDefaultColorEvent", this.ap.toString());
  }
}
