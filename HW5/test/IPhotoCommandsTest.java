import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import controller.BlurCommand;
import controller.CreateImageLayerCommand;
import controller.GrayscaleCommand;
import controller.IImageProcessingController;
import controller.IPhotoCommands;
import controller.LoadAllCommand;
import controller.LoadSingleCommand;
import controller.MakeInvisibleCommand;
import controller.MakeVisibleCommand;
import controller.RemoveImageLayerCommand;
import controller.SaveAllCommand;
import controller.SaveSingleCommand;
import controller.SepiaCommand;
import controller.SetCurrentCommand;
import controller.SharpenCommand;
import controller.SimpleIImageProcessingController;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import model.color.Grayscale;
import model.color.Sepia;
import model.exports.IExport;
import model.exports.PNGExport;
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
 * This class tests for the command classes ones that implement {@link IPhotoCommands} that are
 * called in the controller (the controller uses the command design pattern) to make sure that
 * they work properly when given an {@link ILayerModel}.
 */
public class IPhotoCommandsTest {

  IImageProcessingController controller;
  Readable in;
  Appendable out;
  ILayerModel model;
  ILayer exLayer;
  ILayer exLayer2;
  ILayer exLayer3;
  IPixel[][] grid1;
  IPixel[][] grid2;
  IPixel[][] grid3;
  IPixel[][] grid4;
  IImage exImage;
  IImage exImage2;
  IImage exImage3;
  IImage exImage4;
  Appendable mockAppendable;
  ILayerModel mockModel;

  @Before
  public void setUp() {
    out = new StringBuilder();
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
    grid4 = new IPixel[][]{
        {new Pixel(0, 0, 255, 0, 0), new Pixel(0, 1, 0, 0, 0),
            new Pixel(0, 2, 255, 0, 0), new Pixel(0, 3, 0, 0, 0)},
        {new Pixel(1, 0, 0, 0, 0), new Pixel(1, 1, 255, 0, 0),
            new Pixel(1, 2, 0, 0, 0), new Pixel(1, 3, 255, 0, 0)},
        {new Pixel(2, 0, 255, 0, 0), new Pixel(2, 1, 0, 0, 0),
            new Pixel(2, 2, 255, 0, 0), new Pixel(2, 3, 0, 0, 0)},
        {new Pixel(3, 0, 0, 0, 0), new Pixel(3, 1, 255, 0, 0),
            new Pixel(3, 2, 0, 0, 0), new Pixel(3, 3, 255, 0, 0)}};
    exImage4 = new Image(grid4, "Grid4");
    model.createImageLayer("first");
    model.createImageLayer("second");
    model.createImageLayer("third");
    model.setCurrent("first");
    model.loadLayer(exImage);
    model.setCurrent("second");
    model.loadLayer(exImage2);
    model.setCurrent("third");
    model.loadLayer(exImage3);
    mockAppendable = new StringBuilder();
    mockModel = new MockILayerModel(mockAppendable);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullBlur() {
    new BlurCommand().go(null);
  }

  @Test
  public void testBlurCommand() {
    model.setCurrent("first");
    new BlurCommand().go(model);
    IImage blurImg = new Blur().apply(new Image(exImage.getImage(), exImage.getFilename()));
    assertArrayEquals(blurImg.getImage(), this.model.getLayers().get(0).getImage().getImage());
    assertEquals("Grid1", this.model.getLayers().get(0).getImage().getFilename());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateImageLayerWithNullName() {
    new CreateImageLayerCommand(null).go(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullCreateImageLayer() {
    new CreateImageLayerCommand("Layer 2").go(null);
  }

  @Test
  public void testCreateImageLayerCommand() {
    new CreateImageLayerCommand("fourth").go(model);
    assertEquals(4, model.getLayers().size());
    assertTrue(model.getLayers().get(3).isVisible());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullGrayscale() {
    new GrayscaleCommand().go(null);
  }

  @Test
  public void testGrayscaleCommand() {
    model.setCurrent("first");
    new GrayscaleCommand().go(model);
    IImage grayImg = new Grayscale().apply(new Image(exImage.getImage(), exImage.getFilename()));
    assertArrayEquals(grayImg.getImage(), model.getLayers().get(0).getImage().getImage());
    assertEquals("Grid1", model.getLayers().get(0).getImage().getFilename());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullLoadAllCommand() {
    new LoadAllCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelLoadAllCommand() {
    new LoadAllCommand("Tester").go(null);
    new File("res/Tester").delete();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullLoadSingleCommand() {
    new LoadSingleCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelLoadSingleCommand() {
    new LoadAllCommand("Tester").go(null);
    new File("res/Tester").delete();
  }

  @Test
  public void testLoadSingleCommand() throws IOException {
    model.createImageLayer("fourth");
    model.setCurrent("fourth");
    IExport exporter = new PNGExport(exImage4, "fourthImg");
    exporter.export();
    IPhotoCommands load = new LoadSingleCommand("fourthImg.png");
    load.go(model);
    assertArrayEquals(grid4,this.model.getLayers().get(3).getImage().getImage());
    assertEquals("fourthImg.png", this.model.getLayers().get(3).getImage().getFilename());
    File file = new File("fourthImg.png");
    file.delete();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullMakeInvisibleCommand() {
    new MakeInvisibleCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelMakeInvisibleCommand() {
    new MakeInvisibleCommand("first").go(null);
  }

  @Test
  public void testMakeInvisibleCommand() {
    assertTrue(this.model.getLayers().get(0).isVisible());
    new MakeInvisibleCommand("second").go(model);
    assertFalse(this.model.getLayers().get(1).isVisible());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullMakeVisibleCommand() {
    new MakeVisibleCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelMakeVisibleCommand() {
    new MakeVisibleCommand("first").go(null);
  }

  @Test
  public void testMakeVisibleCommand() {
    assertTrue(this.model.getLayers().get(1).isVisible());
    new MakeInvisibleCommand("second").go(model);
    assertFalse(this.model.getLayers().get(1).isVisible());
    new MakeVisibleCommand("second").go(model);
    assertTrue(this.model.getLayers().get(1).isVisible());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullRemoveImageLayerCommand() {
    new RemoveImageLayerCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelRemoveImageLayerCommand() {
    new RemoveImageLayerCommand("first").go(null);
  }

  @Test
  public void testRemoveImageLayerCommand() {
    assertEquals(3, model.getLayers().size());
    assertArrayEquals(grid2, this.model.getLayers().get(0).getImage().getImage());
    new RemoveImageLayerCommand("second").go(model);
    assertEquals(2, model.getLayers().size());
    assertEquals(grid3, model.getLayers().get(1).getImage().getImage());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullSaveAllCommand() {
    new SaveAllCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelSaveAllCommand() {
    new SaveAllCommand("Tester").go(null);
    new File("res/Tester").delete();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullSaveSingleCommand() {
    new SaveSingleCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelSaveSingleCommand() {
    new SaveSingleCommand("Tester").go(null);
    new File("res/Tester").delete();
  }

  @Test
  public void testModelSaveSingleCommand() throws IOException {
    model.createImageLayer("fourth");
    model.setCurrent("fourth");
    IExport exporter = new PNGExport(exImage4, "fourthImg");
    exporter.export();
    IPhotoCommands load = new LoadSingleCommand("fourthImg.png");
    load.go(model);
    assertArrayEquals(grid4, model.getLayers().get(3).getImage().getImage());
    assertEquals("fourthImg.png",  model.getLayers().get(3).getImage().getFilename());
    IPhotoCommands save = new SaveSingleCommand("fourthImg.png");
    save.go(model);
    ILayerModel m = new LayerModel();
    m.createImageLayer("test");
    m.setCurrent("test");
    IPhotoCommands load2 = new LoadSingleCommand("fourthImg.png");
    load2.go(m);
    File file = new File("fourthImg.png");
    assertArrayEquals(grid4,  model.getLayers().get(3).getImage().getImage());
    assertEquals("fourthImg.png",  model.getLayers().get(3).getImage().getFilename());
    file.delete();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullSepia() {
    new SepiaCommand().go(null);
  }

  @Test
  public void testSepiaCommand() {
    model.setCurrent("second");
    new SepiaCommand().go(model);
    IImage sepiaImg = new Sepia().apply(new Image(exImage2.getImage(), exImage2.getFilename()));
    assertArrayEquals(sepiaImg.getImage(),  model.getLayers().get(1).getImage().getImage());
    assertEquals("Grid2",  model.getLayers().get(1).getImage().getFilename());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullSetCurrentCommand() {
    new SetCurrentCommand(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelSetCurrentCommand() {
    new SetCurrentCommand("third").go(null);
  }

  @Test
  public void testSetCurrentCommand() {
    assertEquals("Layer #1, Name of Layer: first, Image Filename: Grid1, Visibility: true\n"
            + "Layer #2, Name of Layer: second, Image Filename: Grid2, Visibility: true\n"
            + "Layer #3, Name of Layer: third, Image Filename: Grid3, Visibility: true\n"
            + "Number of valid layers created: 3\n"
            + "Current Layer: Name of Layer: third, Image Filename: Grid3, Visibility: true\n",
        model.toString());
    new SetCurrentCommand("second").go(model);
    assertEquals("Layer #1, Name of Layer: first, Image Filename: Grid1, Visibility: true\n"
            + "Layer #2, Name of Layer: second, Image Filename: Grid2, Visibility: true\n"
            + "Layer #3, Name of Layer: third, Image Filename: Grid3, Visibility: true\n"
            + "Number of valid layers created: 3\n"
            + "Current Layer: Name of Layer: second, Image Filename: Grid2, Visibility: true\n",
        model.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullSharpen() {
    new SharpenCommand().go(null);
  }

  @Test
  public void testSharpenCommand() {
    model.setCurrent("third");
    new SharpenCommand().go(model);
    IImage sharpImg = new Sharpening()
        .apply(new Image(exImage3.getImage(), exImage3.getFilename()));
    assertArrayEquals(sharpImg.getImage(),  model.getLayers().get(2).getImage().getImage());
    assertEquals("Grid3",  model.getLayers().get(2).getImage().getFilename());
  }

  //Testing mocks
  @Test(expected = IllegalArgumentException.class)
  public void testNullMock() {
    new MockILayerModel(null);
  }

  @Test(expected = IllegalStateException.class)
  public void testMockNoQuitting() {
    in = new StringReader("");
    controller = new SimpleIImageProcessingController(mockModel, in, out);
    controller.processImage();
  }

  @Test(expected = IllegalStateException.class)
  public void testMockCreateAndNoQuitting() {
    in = new StringReader("create first ");
    controller = new SimpleIImageProcessingController(mockModel, in, out);
    controller.processImage();
  }

  @Test
  public void testMockCreateAndCurrent() {
    in = new StringReader("create first\ncurrent first\nq");
    controller = new SimpleIImageProcessingController(mockModel, in, out);
    controller.processImage();
    assertEquals("first\nfirst\n", mockAppendable.toString());
  }

  @Test
  public void testMockCreateAndCurrentAndCreateAndRemove() {
    in = new StringReader("create first\ncurrent first   create second  remove first q");
    controller = new SimpleIImageProcessingController(mockModel, in, out);
    controller.processImage();
    assertEquals("first\nfirst\nsecond\nfirst\n", mockAppendable.toString());
  }

  @Test
  public void testMockCreateAndCreateAndMakeInvisible() {
    in = new StringReader("create first  create second  invisible first q");
    controller = new SimpleIImageProcessingController(mockModel, in, out);
    controller.processImage();
    assertEquals("first\nsecond\nfirst\n", mockAppendable.toString());
  }

  @Test
  public void testMockCreateAndCreateAndMakeInvisibleAndMakeVisible() {
    in = new StringReader(
        "create first  create second  invisible first visible first visible second q");
    controller = new SimpleIImageProcessingController(mockModel, in, out);
    controller.processImage();
    assertEquals("first\nsecond\nfirst\nfirst\nsecond\n", mockAppendable.toString());
  }

  @Test
  public void testMockCreateAndCurrentAndBlur() {
    in = new StringReader("create first  current first blur  q");
    controller = new SimpleIImageProcessingController(mockModel, in, out);
    controller.processImage();
    assertEquals("first\nfirst\n" + new Blur().getClass().getSimpleName() + "\n",
        mockAppendable.toString());
  }

  @Test
  public void testMockCreateAndCurrentAndSharpen() {
    in = new StringReader("create first  current first   sharpen  q");
    controller = new SimpleIImageProcessingController(mockModel, in, out);
    controller.processImage();
    assertEquals("first\nfirst\n" + new Sharpening().getClass().getSimpleName() + "\n",
        mockAppendable.toString());
  }

  @Test
  public void testMockCreateAndCurrentAndGray() {
    in = new StringReader("create first  current first   grayscale  q");
    controller = new SimpleIImageProcessingController(mockModel, in, out);
    controller.processImage();
    assertEquals("first\nfirst\n" + new Grayscale().getClass().getSimpleName() + "\n",
        mockAppendable.toString());
  }

  @Test
  public void testMockCreateAndCurrentAndSepia() {
    in = new StringReader("create first  current first   sepia  q");
    controller = new SimpleIImageProcessingController(mockModel, in, out);
    controller.processImage();
    assertEquals("first\nfirst\n" + new Sepia().getClass().getSimpleName() + "\n",
        mockAppendable.toString());
  }

  @Test
  public void testMockCreateAndCurrentAndLoadAndInvisible() throws IOException {
    IExport export = new PNGExport(exImage, "tester");
    export.export();
    in = new StringReader("create first  current first   load tester.png  invisible first q");
    controller = new SimpleIImageProcessingController(mockModel, in, out);
    controller.processImage();
    assertEquals("first\nfirst\ntester.png\nfirst\n",
        mockAppendable.toString());
    File file = new File("tester.png");
    file.delete();
  }
}
