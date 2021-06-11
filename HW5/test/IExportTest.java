import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import model.color.IColorTransformation;
import model.color.Sepia;
import model.exports.FailingWriter;
import model.exports.IExport;
import model.exports.PPMExport;
import model.creator.CheckboardImageCreator;
import model.image.IImage;
import model.image.IPixel;
import model.image.PPMImage;
import model.image.Pixel;
import org.junit.Before;
import org.junit.Test;

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
    this.sampleImage = new PPMImage(sampleImageGrid, "SampleImage");
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
    IImage image = new PPMImage("res/Checkerboard.ppm");
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
  }

}
