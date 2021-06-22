import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import model.color.IColorTransformation;
import model.color.Sepia;
import model.creator.IImageCreator;
import model.exports.FailingWriter;
import model.exports.IExport;
import model.exports.JPEGExport;
import model.exports.PNGExport;
import model.exports.PPMExport;
import model.creator.CheckboardImageCreator;
import model.exports.PPMExportFilename;
import model.exports.TextFileExport;
import model.image.IImage;
import model.image.IPixel;
import model.image.Image;
import model.image.Pixel;
import model.managers.IOManager;
import model.managers.InputFilenameManager;
import model.managers.InputJPEGFilenameManager;
import model.managers.InputPNGFilenameManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Represents the test class for {@code IExport} to ensure that the object's behavior works as
 * expected and properly transforms the given image.
 */
public class IExportTest {

  IPixel[][] sampleImageGrid;
  IImage sampleImage;
  IImage checkerboard;
  Writer stringWriterSampleImage;
  Writer stringWriterCheckerboard;
  IExport exportSampleImage;
  IExport exportCheckerboard;
  Writer failingWriter;
  IExport exportWithFailingWriter;

  @Before
  public void initData() {
    this.stringWriterSampleImage = new StringWriter();
    this.stringWriterCheckerboard = new StringWriter();
    this.sampleImageGrid = new IPixel[][]{
        {new Pixel(0, 0, 100, 50, 80),
            new Pixel(0, 1, 50, 200, 10),
            new Pixel(0, 2, 100, 40, 240),
            new Pixel(0, 3, 90, 88, 120)},
        {new Pixel(1, 0, 100, 50, 80),
            new Pixel(1, 1, 50, 200, 10),
            new Pixel(1, 2, 100, 40, 240),
            new Pixel(1, 3, 90, 88, 120)},
        {new Pixel(2, 0, 100, 50, 80),
            new Pixel(2, 1, 50, 200, 10),
            new Pixel(2, 2, 100, 40, 240),
            new Pixel(2, 3, 90, 88, 120)}};
    this.sampleImage = new Image(sampleImageGrid, "SampleImage");
    this.checkerboard = new CheckboardImageCreator(3, 4).createImage();
    this.exportSampleImage = new PPMExport(sampleImage, stringWriterSampleImage);
    this.exportCheckerboard = new PPMExport(checkerboard, stringWriterCheckerboard);
    this.failingWriter = new FailingWriter();
    this.exportWithFailingWriter = new PPMExport(sampleImage, failingWriter);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPPMExportConstructorExceptionDefaultNullImage() throws IOException {
    IExport nullImageDefaultExport = new PPMExport(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPPMExportConstructorExceptionGivenReaderNullImage() throws IOException {
    IExport nullImageDefaultExport = new PPMExport(null, new StringWriter());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPPMExportConstructorExceptionGivenNullReader() throws IOException {
    IExport nullImageDefaultExport = new PPMExport(sampleImage, null);
  }

  @Test(expected = IOException.class)
  public void testPPMExportExportMethodWithFailingWriter() throws IOException {
    this.exportWithFailingWriter.export();
  }

  @Test
  public void testExportSampleImage() throws IOException {
    // tests to make sure the writer starts as empty
    assertEquals("", stringWriterSampleImage.toString());
    exportSampleImage.export();
    // tests to make sure that the writer's output is correct
    assertEquals("P3 4 3 255\n"
            + "100 50 80 50 200 10 100 40 240 90 88 120 100 50 80 50 200 10 100 40 240 90 88 "
            + "120 100 50 80 50 200 10 100 40 240 90 88 120 ",
        stringWriterSampleImage.toString());
  }

  @Test
  public void testExportSepiaSampleImage() throws IOException {
    Writer writer = new StringWriter();
    assertEquals("", writer.toString());
    IColorTransformation sepia = new Sepia();
    IImage sepiaSampleImage = sepia.apply(sampleImage);
    IExport export = new PPMExport(sepiaSampleImage, writer);

    export.export();

    assertEquals("P3 4 3 255\n"
        + "92 81 63 173 155 120 114 101 79 124 111 85 92 "
        + "81 63 173 155 120 114 101 79 124 111 85 92 81 63 "
        + "173 155 120 114 101 79 124 111 85 ", writer.toString());
  }

  @Test
  public void testExportCheckerboardImage() throws IOException {
    // tests to make sure the writer starts as empty
    assertEquals("", stringWriterCheckerboard.toString());
    exportCheckerboard.export();
    // tests to make sure that the writer's output is correct
    assertEquals("P3 12 12 255\n"
            + "255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 "
            + "0 0 255 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 "
            + "0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 "
            + "0 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 255 0 "
            + "0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 "
            + "255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 "
            + "0 0 255 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 "
            + "0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 "
            + "0 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 255 0 "
            + "0 255 0 0 255 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 "
            + "0 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 255 0 "
            + "0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 "
            + "0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 "
            + "0 0 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 0 0 0 "
            + "0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 255 "
            + "0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 "
            + "0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 "
            + "0 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 "
            + "0 0 255 0 0 ",
        stringWriterCheckerboard.toString());
  }

  @Test
  public void testImportingAndExportingAnImage() throws IOException {
    IImageCreator checkerboard = new CheckboardImageCreator(4, 4);
    IImage board = checkerboard.createImage();
    IExport exportBoard = new PPMExportFilename(board, "Checkerboard");
    exportBoard.export();
    IImage image = new Image("Checkerboard.ppm");
    Writer writer = new StringWriter();
    IExport export = new PPMExport(image, writer);

    assertEquals("", writer.toString());

    export.export();

    assertEquals("P3 16 16 255\n"
        + "255 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 "
        + "255 0 0 0 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 0 "
        + "0 0 255 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 "
        + "255 0 0 0 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 0 "
        + "0 0 255 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 "
        + "255 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 255 "
        + "0 0 0 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 0 0 0 "
        + "255 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 255 "
        + "0 0 0 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 0 0 0 "
        + "255 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 255 "
        + "0 0 0 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 255 0 0 255 0 0 255 0 0 255 0 0 "
        + "255 0 0 0 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 0 "
        + "0 0 255 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 "
        + "255 0 0 0 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 0 "
        + "0 0 255 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 "
        + "255 0 0 0 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 0 "
        + "0 0 0 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 0 0 0 "
        + "255 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 255 "
        + "0 0 0 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 0 0 0 "
        + "255 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 255 "
        + "0 0 0 0 0 0 0 0 0 0 0 0 0 0 255 0 0 255 0 0 255 0 0 255 0 0 0 0 0 0 0 0 0 0 0 0 0 0 "
        + "255 0 0 255 0 0 255 0 0 255 0 0 ", writer.toString());
    File file = new File("Checkerboard.ppm");
    file.delete();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullImageForJPEGExport() {
    try {
      new JPEGExport(null);
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullImageForJPEGExportConstructorTwo() {
    try {
      new JPEGExport(null, "Sample");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullDesiredNameForJPEGExport() {
    try {
      new JPEGExport(sampleImage, null);
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  @Test
  public void testExportSepiaSampleJPEGImageDefaultName() throws IOException {
    IColorTransformation sepia = new Sepia();
    IImage sepiaSampleImage = sepia.apply(sampleImage);
    IExport export = new JPEGExport(sepiaSampleImage);
    export.export();
    IOManager reimport = new InputJPEGFilenameManager("SampleImageNew.jpeg");
    IImage transformedImg = reimport.apply();
    IPixel[][] grid = new IPixel[][]{
        {new Pixel(0, 0, 96, 82, 56), new Pixel(0, 1, 166, 152, 126),
            new Pixel(0, 2, 114, 100, 74), new Pixel(0, 3, 127, 113, 87)},
        {new Pixel(1, 0, 96, 82, 56), new Pixel(1, 1, 166, 152, 126),
            new Pixel(1, 2, 114, 100, 74), new Pixel(1, 3, 127, 113, 87)},
        {new Pixel(2, 0, 96, 82, 56), new Pixel(2, 1, 166, 152, 126),
            new Pixel(2, 2, 114, 100, 74), new Pixel(2, 3, 127, 113, 87)}};
    assertArrayEquals(grid, transformedImg.getImage());
    assertEquals("SampleImageNew.jpeg", transformedImg.getFilename());
    File file = new File("SampleImageNew.jpeg");
    file.delete();
  }

  @Test
  public void testExportSepiaSampleJPEGImageGivenName() throws IOException {
    IColorTransformation sepia = new Sepia();
    IImage sepiaSampleImage = sepia.apply(sampleImage);
    IExport export = new JPEGExport(sepiaSampleImage, "SepiaImage");
    export.export();
    IOManager reimport = new InputJPEGFilenameManager("SepiaImage.jpeg");
    IImage transformedImg = reimport.apply();
    IPixel[][] grid = new IPixel[][]{
        {new Pixel(0, 0, 96, 82, 56), new Pixel(0, 1, 166, 152, 126),
            new Pixel(0, 2, 114, 100, 74), new Pixel(0, 3, 127, 113, 87)},
        {new Pixel(1, 0, 96, 82, 56), new Pixel(1, 1, 166, 152, 126),
            new Pixel(1, 2, 114, 100, 74), new Pixel(1, 3, 127, 113, 87)},
        {new Pixel(2, 0, 96, 82, 56), new Pixel(2, 1, 166, 152, 126),
            new Pixel(2, 2, 114, 100, 74), new Pixel(2, 3, 127, 113, 87)}};
    assertArrayEquals(grid, transformedImg.getImage());
    assertEquals("SepiaImage.jpeg", transformedImg.getFilename());
    File file = new File("SepiaImage.jpeg");
    file.delete();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullImageForPNGExport() {
    new PNGExport(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullImageForPNGExportConstructorTwo() {
    try {
      new PNGExport(null, "Sample");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullDesiredNameForPNGExport() {
    try {
      new PNGExport(sampleImage, null);
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  @Test
  public void testExportSepiaSamplePNGImageDefaultName() throws IOException {
    IColorTransformation sepia = new Sepia();
    IImage sepiaSampleImage = sepia.apply(sampleImage);
    IExport export = new PNGExport(sepiaSampleImage);
    export.export();
    IOManager reimport = new InputPNGFilenameManager("SampleImageNew.png");
    IImage transformedImg = reimport.apply();
    IPixel[][] grid = new IPixel[][]{
        {new Pixel(0, 0, 92, 81, 63), new Pixel(0, 1, 173, 155, 120),
            new Pixel(0, 2, 114, 101, 79), new Pixel(0, 3, 124, 111, 85)},
        {new Pixel(1, 0, 92, 81, 63), new Pixel(1, 1, 173, 155, 120),
            new Pixel(1, 2, 114, 101, 79), new Pixel(1, 3, 124, 111, 85)},
        {new Pixel(2, 0, 92, 81, 63), new Pixel(2, 1, 173, 155, 120),
            new Pixel(2, 2, 114, 101, 79), new Pixel(2, 3, 124, 111, 85)}};
    assertArrayEquals(grid, transformedImg.getImage());
    assertEquals("SampleImageNew.png", transformedImg.getFilename());
    File file = new File("SampleImageNew.png");
    file.delete();
  }

  @Test
  public void testExportSepiaSamplePNGImageGivenName() throws IOException {
    IColorTransformation sepia = new Sepia();
    IImage sepiaSampleImage = sepia.apply(sampleImage);
    IExport export = new PNGExport(sepiaSampleImage, "SepiaImage");
    export.export();
    IOManager reimport = new InputPNGFilenameManager("SepiaImage.png");
    IImage transformedImg = reimport.apply();
    IPixel[][] grid = new IPixel[][]{
        {new Pixel(0, 0, 92, 81, 63), new Pixel(0, 1, 173, 155, 120),
            new Pixel(0, 2, 114, 101, 79), new Pixel(0, 3, 124, 111, 85)},
        {new Pixel(1, 0, 92, 81, 63), new Pixel(1, 1, 173, 155, 120),
            new Pixel(1, 2, 114, 101, 79), new Pixel(1, 3, 124, 111, 85)},
        {new Pixel(2, 0, 92, 81, 63), new Pixel(2, 1, 173, 155, 120),
            new Pixel(2, 2, 114, 101, 79), new Pixel(2, 3, 124, 111, 85)}};
    assertArrayEquals(grid, transformedImg.getImage());
    assertEquals("SepiaImage.png", transformedImg.getFilename());
    File file = new File("SepiaImage.png");
    file.delete();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullImageForPPMExportFilename() throws IOException {
    new PPMExportFilename(null, "SampleImg");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullFilenameForPPMExportFilename() throws IOException {
    new PPMExportFilename(sampleImage, null);
  }

  @Test
  public void testExportSepiaSamplePPMImageGivenName() throws IOException {
    IColorTransformation sepia = new Sepia();
    IImage sepiaSampleImage = sepia.apply(sampleImage);
    IExport export = new PPMExportFilename(sepiaSampleImage, "SepiaPPMImage");
    export.export();
    IOManager reimport = new InputFilenameManager("SepiaPPMImage.ppm");
    IImage transformedImg = reimport.apply();
    IPixel[][] grid = new IPixel[][]{
        {new Pixel(0, 0, 92, 81, 63), new Pixel(0, 1, 173, 155, 120),
            new Pixel(0, 2, 114, 101, 79), new Pixel(0, 3, 124, 111, 85)},
        {new Pixel(1, 0, 92, 81, 63), new Pixel(1, 1, 173, 155, 120),
            new Pixel(1, 2, 114, 101, 79), new Pixel(1, 3, 124, 111, 85)},
        {new Pixel(2, 0, 92, 81, 63), new Pixel(2, 1, 173, 155, 120),
            new Pixel(2, 2, 114, 101, 79), new Pixel(2, 3, 124, 111, 85)}};
    assertArrayEquals(grid, transformedImg.getImage());
    assertEquals("SepiaPPMImage.ppm", transformedImg.getFilename());
    File file = new File("SepiaPPMImage.ppm");
    file.delete();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullDirForTextFileExport() {
    new TextFileExport(null, "Testing adding text...");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullTextForTextFileExport() {
    new TextFileExport("res/", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIOExceptionTextFileExport() {
    new TextFileExport("unknown/", "Testing adding text...");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullWriterConstructorException() {
    new TextFileExport("res/", "Testing adding text...", null);
  }

  @Test
  public void testTextFileExport() throws IOException {
    Writer writer = new StringWriter();
    IExport export = new TextFileExport("tester", "Testing adding text...", writer);
    export.export();

    assertEquals("Testing adding text...", writer.toString());
  }
}