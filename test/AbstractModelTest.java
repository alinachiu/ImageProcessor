import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import model.IModel;
import model.Model;
import model.filter.Blur;
import model.filter.IFilter;
import model.filter.Sharpening;
import model.filter.UserFilter;
import model.creator.CheckboardImageCreator;
import model.creator.IImageCreator;
import model.image.IImage;
import model.image.IPixel;
import model.image.Image;
import model.image.Pixel;
import java.io.StringWriter;
import java.io.Writer;
import model.color.Grayscale;
import model.color.IColorTransformation;
import model.color.Sepia;
import model.color.UserColorTransformation;
import model.exports.FailingWriter;
import model.exports.IExport;
import model.exports.PPMExport;
import model.layer.LayerModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents the test class for {@code IModel} to ensure that the object's behavior works as
 * expected and properly transforms the given image.
 */
public abstract class AbstractModelTest {

  IModel model;
  IFilter blur;
  IFilter sharp;
  IFilter user;
  IPixel[][] evenImage;
  IImage evenImg;
  IPixel[][] unevenImage;
  IImage unevenImg;
  IPixel[][] oddByOddImage;
  IImage oddByOddImg;
  IPixel[][] empty;
  IImage emptyImg;
  IPixel[][] greaterHeight;
  IImage greaterHeightImg;
  IPixel[][] greaterWidth;
  IImage greaterWidthImg;
  IImageCreator singleSquareBoard;
  IImageCreator twoByTwoCheckerboard;
  IImageCreator threeByThreeCheckerboard;
  IImageCreator defaultFourByFourCheckerboard;
  IPixel[][] sampleImageGrid;
  IImage sampleImage;
  IPixel[][] sampleImageGrid1By2;
  IImage sampleImage1By2;
  IColorTransformation grayscale;
  IColorTransformation sepia;
  double[][] userMatrix;
  IColorTransformation userGenerated;
  double[][] doubleMatrix;
  IColorTransformation doubleUserGenerated;
  double[][] negativeMatrix;
  IColorTransformation negativeUserGenerated;
  IImage defaultCheckerboard;
  IImage checkerboard;
  Writer stringWriterSampleImage;
  Writer stringWriterCheckerboard;
  IExport exportSampleImage;
  IExport exportCheckerboard;
  Writer failingWriter;
  IExport exportWithFailingWriter;

  @Before
  public void setUp() {
    model = this.makeModel();
    this.blur = new Blur();
    this.sharp = new Sharpening();
    evenImage = new IPixel[][]{
        {new Pixel(0, 0, 100, 50, 80), new Pixel(0, 1, 50, 200, 10),
            new Pixel(0, 2, 100, 40, 240), new Pixel(0, 3, 90, 88, 120)},
        {new Pixel(1, 0, 100, 50, 80), new Pixel(1, 1, 50, 200, 10),
            new Pixel(1, 2, 100, 40, 240), new Pixel(1, 3, 90, 88, 120)},
        {new Pixel(2, 0, 100, 50, 80), new Pixel(2, 1, 50, 200, 10),
            new Pixel(2, 2, 100, 40, 240), new Pixel(2, 3, 90, 88, 120)},
        {new Pixel(3, 0, 100, 50, 80), new Pixel(3, 1, 50, 200, 10),
            new Pixel(3, 2, 100, 40, 240), new Pixel(3, 3, 90, 88, 120)}
    };
    evenImg = new Image(evenImage, "ExImage");
    unevenImage = new IPixel[][]{
        {new Pixel(0, 0, 100, 50, 80), new Pixel(0, 1, 50, 200, 10),
            new Pixel(0, 2, 100, 40, 240), new Pixel(0, 3, 90, 88, 120)},
        {new Pixel(1, 0, 100, 50, 80), new Pixel(1, 1, 50, 200, 10),
            new Pixel(1, 2, 100, 40, 240), new Pixel(1, 3, 90, 88, 120)},
        {new Pixel(2, 0, 100, 50, 80), new Pixel(2, 1, 50, 200, 10),
            new Pixel(2, 2, 100, 40, 240), new Pixel(2, 3, 90, 88, 120)}};
    unevenImg = new Image(unevenImage, "ExImage2");
    oddByOddImage = new IPixel[][]{
        {new Pixel(0, 0, 100, 50, 80), new Pixel(0, 1, 50, 200, 10),
            new Pixel(0, 2, 90, 88, 120)},
        {new Pixel(1, 0, 100, 50, 80), new Pixel(1, 1, 50, 200, 10),
            new Pixel(1, 2, 90, 88, 120)},
        {new Pixel(2, 0, 100, 50, 80), new Pixel(2, 1, 50, 200, 10),
            new Pixel(2, 2, 90, 88, 120)}};
    oddByOddImg = new Image(oddByOddImage, "ExOddImage");
    greaterHeight = new IPixel[][]{
        {new Pixel(0, 0, 100, 50, 80), new Pixel(0, 1, 50, 200, 10),
            new Pixel(0, 2, 90, 88, 120), new Pixel(0, 3, 90, 88, 120),
            new Pixel(0, 4, 90, 88, 120)},
        {new Pixel(1, 0, 100, 50, 80), new Pixel(1, 1, 50, 200, 10),
            new Pixel(1, 2, 90, 88, 120), new Pixel(1, 3, 90, 88, 120),
            new Pixel(1, 4, 90, 88, 120)},
        {new Pixel(2, 0, 100, 50, 80), new Pixel(2, 1, 50, 200, 10),
            new Pixel(2, 2, 90, 88, 120), new Pixel(2, 3, 90, 88, 120),
            new Pixel(2, 4, 90, 88, 120)},
        {new Pixel(3, 0, 100, 50, 80), new Pixel(3, 1, 50, 200, 10),
            new Pixel(3, 2, 90, 88, 120), new Pixel(3, 3, 90, 88, 120),
            new Pixel(3, 4, 90, 88, 120)},
        {new Pixel(4, 0, 100, 50, 80), new Pixel(4, 1, 50, 200, 10),
            new Pixel(4, 2, 90, 88, 120), new Pixel(4, 3, 90, 88, 120),
            new Pixel(4, 4, 90, 88, 120)},
        {new Pixel(5, 0, 100, 50, 80), new Pixel(5, 1, 50, 200, 10),
            new Pixel(5, 3, 90, 88, 120), new Pixel(5, 4, 90, 88, 120),
            new Pixel(5, 4, 90, 88, 120)}};
    greaterHeightImg = new Image(greaterHeight, "ExHeightImage");
    greaterWidth = new IPixel[][]{
        {new Pixel(0, 0, 100, 50, 80), new Pixel(0, 1, 50, 200, 10),
            new Pixel(0, 2, 90, 88, 120), new Pixel(0, 3, 90, 88, 120),
            new Pixel(0, 4, 90, 88, 120), new Pixel(0, 5, 100, 50, 80)},
        {new Pixel(1, 0, 100, 50, 80), new Pixel(1, 1, 50, 200, 10),
            new Pixel(1, 2, 90, 88, 120), new Pixel(1, 3, 90, 88, 120),
            new Pixel(1, 4, 90, 88, 120), new Pixel(1, 5, 100, 50, 80)},
        {new Pixel(2, 0, 100, 50, 80), new Pixel(2, 1, 50, 200, 10),
            new Pixel(2, 2, 90, 88, 120), new Pixel(2, 3, 90, 88, 120),
            new Pixel(2, 4, 90, 88, 120), new Pixel(2, 5, 100, 50, 80)},
        {new Pixel(3, 0, 100, 50, 80), new Pixel(3, 1, 50, 200, 10),
            new Pixel(3, 2, 90, 88, 120), new Pixel(3, 3, 90, 88, 120),
            new Pixel(3, 4, 90, 88, 120), new Pixel(3, 5, 100, 50, 80)},
        {new Pixel(4, 0, 100, 50, 80), new Pixel(4, 1, 50, 200, 10),
            new Pixel(4, 2, 90, 88, 120), new Pixel(4, 3, 90, 88, 120),
            new Pixel(4, 4, 90, 88, 120), new Pixel(4, 5, 100, 50, 80)}};
    greaterWidthImg = new Image(greaterWidth, "ExWidthImage");
    singleSquareBoard = new CheckboardImageCreator(1, 1);
    twoByTwoCheckerboard = new CheckboardImageCreator(3, 2, 50, 100, 150, 8, 16, 62);
    threeByThreeCheckerboard = new CheckboardImageCreator(1, 3, 255, 255, 255, 0, 0, 0);
    defaultFourByFourCheckerboard = new CheckboardImageCreator(2, 4);
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
    this.sampleImageGrid1By2 = new IPixel[][]{{new Pixel(0, 0, 100, 50, 80),
        new Pixel(0, 1, 50, 200, 10)}};
    this.sampleImage1By2 = new Image(sampleImageGrid1By2, "OneByTwoGrid");
    this.grayscale = new Grayscale();
    this.sepia = new Sepia();
    this.userMatrix = new double[][]{{0.5, 0.5, 0.5}, {0.1, 0.1, 0.1}, {0.2, 0.2, 0.2}};
    this.doubleMatrix = new double[][]{{2, 2, 2}, {2, 2, 2}, {2, 2, 2}};
    this.doubleUserGenerated = new UserColorTransformation(doubleMatrix);
    this.negativeMatrix = new double[][]{{-1, -1, -1}, {-2, -2, -2}, {-0.3, -0.1, 0.1}};
    this.negativeUserGenerated = new UserColorTransformation(negativeMatrix);
    this.userGenerated = new UserColorTransformation(userMatrix);
    this.defaultCheckerboard = new CheckboardImageCreator(4, 3).createImage();
    this.stringWriterSampleImage = new StringWriter();
    this.stringWriterCheckerboard = new StringWriter();
    this.checkerboard = new CheckboardImageCreator(3, 4).createImage();
    this.exportSampleImage = new PPMExport(sampleImage, stringWriterSampleImage);
    this.exportCheckerboard = new PPMExport(checkerboard, stringWriterCheckerboard);
    this.failingWriter = new FailingWriter();
    this.exportWithFailingWriter = new PPMExport(sampleImage, failingWriter);
  }

  //Tests for Filter
  @Test(expected = IllegalArgumentException.class)
  public void nullImageForFilter() {
    model.filter(null, blur);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullFilter() {
    model.filter(greaterWidthImg, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullFilterAndImage() {
    model.filter(null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullEmptyImgBlur() {
    model.filter(emptyImg, blur);
  }

  @Test
  public void blurOneByOneImg() {
    IPixel[][] oneByOne = new IPixel[][]{{new Pixel(0, 0, 10, 100, 200)}};
    IImage img = new Image(oneByOne, "OneByOne");
    assertArrayEquals(new IPixel[][]{{new Pixel(0, 0, 2, 25, 50)}},
        model.filter(img, blur).getImage());
    assertEquals("OneByOne", model.filter(img, blur).getFilename());
  }

  @Test
  public void blurImageWithHeightGreaterThanWidth() {
    IPixel[][] grid = new IPixel[][]{
        {new Pixel(0, 0, 46, 55, 31), new Pixel(0, 1, 52, 100, 40),
            new Pixel(0, 2, 58, 86, 68), new Pixel(0, 3, 65, 65, 89),
            new Pixel(0, 4, 49, 49, 67)},
        {new Pixel(1, 0, 61, 73, 41), new Pixel(1, 1, 69, 133, 53),
            new Pixel(1, 2, 77, 114, 90), new Pixel(1, 3, 86, 86, 118),
            new Pixel(1, 4, 65, 65, 89)},
        {new Pixel(2, 0, 61, 73, 41), new Pixel(2, 1, 69, 133, 53),
            new Pixel(2, 2, 77, 114, 90), new Pixel(2, 3, 86, 86, 118),
            new Pixel(2, 4, 65, 65, 89)},
        {new Pixel(3, 0, 61, 73, 41), new Pixel(3, 1, 69, 133, 53),
            new Pixel(3, 2, 77, 114, 90), new Pixel(3, 3, 86, 86, 118),
            new Pixel(3, 4, 65, 65, 89)},
        {new Pixel(4, 0, 61, 73, 41), new Pixel(4, 1, 69, 133, 53),
            new Pixel(4, 2, 77, 114, 90), new Pixel(4, 3, 86, 86, 118),
            new Pixel(4, 4, 65, 65, 89)},
        {new Pixel(5, 0, 46, 55, 31), new Pixel(5, 1, 52, 100, 40),
            new Pixel(5, 2, 58, 86, 68), new Pixel(5, 3, 65, 65, 89),
            new Pixel(5, 4, 49, 49, 67)}};
    assertArrayEquals(grid, model.filter(greaterHeightImg, blur).getImage());
    assertEquals("ExHeightImage", model.filter(greaterHeightImg, blur).getFilename());
  }

  @Test
  public void blurImageWithWidthGreaterThanHeight() {
    IPixel[][] grid = new IPixel[][]{
        {new Pixel(0, 0, 46, 55, 31), new Pixel(0, 1, 52, 100, 40),
            new Pixel(0, 2, 58, 86, 68), new Pixel(0, 3, 65, 65, 89),
            new Pixel(0, 4, 67, 58, 82), new Pixel(0, 5, 53, 34, 52)},
        {new Pixel(1, 0, 61, 73, 41), new Pixel(1, 1, 69, 133, 53),
            new Pixel(1, 2, 77, 114, 90), new Pixel(1, 3, 86, 86, 118),
            new Pixel(1, 4, 89, 77, 109), new Pixel(1, 5, 70, 45, 69)},
        {new Pixel(2, 0, 61, 73, 41), new Pixel(2, 1, 69, 133, 53),
            new Pixel(2, 2, 77, 114, 90), new Pixel(2, 3, 86, 86, 118),
            new Pixel(2, 4, 89, 77, 109), new Pixel(2, 5, 70, 45, 69)},
        {new Pixel(3, 0, 61, 73, 41), new Pixel(3, 1, 69, 133, 53),
            new Pixel(3, 2, 77, 114, 90), new Pixel(3, 3, 86, 86, 118),
            new Pixel(3, 4, 89, 77, 109), new Pixel(3, 5, 70, 45, 69)},
        {new Pixel(4, 0, 46, 55, 31), new Pixel(4, 1, 52, 100, 40),
            new Pixel(4, 2, 58, 86, 68), new Pixel(4, 3, 65, 65, 89),
            new Pixel(4, 4, 67, 58, 82), new Pixel(4, 5, 53, 34, 52)}};
    assertArrayEquals(grid, model.filter(greaterWidthImg, blur).getImage());
    assertEquals("ExWidthImage", model.filter(greaterWidthImg, blur).getFilename());
  }

  @Test
  public void blurSmallerThanKernelDimForImage() {
    IPixel[][] twoByTwo = new IPixel[][]{
        {new Pixel(0, 0, 10, 100, 100), new Pixel(0, 1, 100, 6, 60)},
        {new Pixel(1, 0, 108, 0, 0), new Pixel(1, 1, 80, 100, 40)}};
    IImage img = new Image(twoByTwo, "TwoByTwo");
    assertArrayEquals(new IPixel[][]{{
            new Pixel(0, 0, 32, 31, 34), new Pixel(0, 1, 42, 25, 32)}, {
            new Pixel(1, 0, 44, 24, 20), new Pixel(1, 1, 45, 31, 23)}},
        model.filter(img, blur).getImage());
    assertEquals("TwoByTwo", model.filter(img, blur).getFilename());
  }

  //Test for Sharpening
  @Test(expected = IllegalArgumentException.class)
  public void nullEmptyImgSharp() {
    model.filter(new Image(new IPixel[][]{}, "Empty"), sharp);
  }

  @Test
  public void sharpOneByOneImg() {
    IPixel[][] oneByOne = new IPixel[][]{{new Pixel(0, 0, 10, 100, 200)}};
    IImage img = new Image(oneByOne, "OneByOne");
    assertArrayEquals(new IPixel[][]{{new Pixel(0, 0, 10, 100, 200)}},
        model.filter(img, sharp).getImage());
    assertEquals("OneByOne", model.filter(img, sharp).getFilename());
  }

  @Test
  public void sharpImageWithHeightGreaterThanWidth() {
    IPixel[][] grid = new IPixel[][]{
        {new Pixel(0, 0, 93, 97, 47), new Pixel(0, 1, 88, 242, 40),
            new Pixel(0, 2, 75, 154, 108), new Pixel(0, 3, 144, 90, 220),
            new Pixel(0, 4, 97, 99, 135)},
        {new Pixel(1, 0, 118, 148, 54), new Pixel(1, 1, 135, 255, 77),
            new Pixel(1, 2, 106, 230, 145), new Pixel(1, 3, 203, 131, 255),
            new Pixel(1, 4, 129, 132, 180)},
        {new Pixel(2, 0, 90, 107, 29), new Pixel(2, 1, 96, 255, 37),
            new Pixel(2, 2, 61, 166, 90), new Pixel(2, 3, 165, 73, 255),
            new Pixel(2, 4, 98, 99, 135)},
        {new Pixel(3, 0, 90, 107, 29), new Pixel(3, 1, 96, 255, 37),
            new Pixel(3, 2, 61, 166, 90), new Pixel(3, 3, 165, 73, 255),
            new Pixel(3, 4, 98, 99, 135)},
        {new Pixel(4, 0, 122, 150, 56), new Pixel(4, 1, 140, 255, 79),
            new Pixel(4, 2, 117, 231, 147), new Pixel(4, 3, 208, 131, 255),
            new Pixel(4, 4, 134, 132, 180)},
        {new Pixel(5, 0, 97, 99, 49), new Pixel(5, 1, 93, 244, 42),
            new Pixel(5, 2, 86, 155, 110), new Pixel(5, 3, 149, 90, 221),
            new Pixel(5, 4, 102, 99, 135)}};
    assertArrayEquals(grid, model.filter(greaterHeightImg, sharp).getImage());
    assertEquals("ExHeightImage", model.filter(greaterHeightImg, sharp).getFilename());
  }

  @Test
  public void sharpImageWithWidthGreaterThanHeight() {
    IPixel[][] grid = new IPixel[][]{
        {new Pixel(0, 0, 93, 97, 47), new Pixel(0, 1, 88, 242, 40),
            new Pixel(0, 2, 75, 154, 108), new Pixel(0, 3, 105, 69, 190),
            new Pixel(0, 4, 134, 116, 165), new Pixel(0, 5, 109, 55, 90)},
        {new Pixel(1, 0, 118, 148, 54), new Pixel(1, 1, 135, 255, 77),
            new Pixel(1, 2, 106, 230, 145), new Pixel(1, 3, 151, 103, 255),
            new Pixel(1, 4, 191, 161, 230), new Pixel(1, 5, 144, 78, 125)},
        {new Pixel(2, 0, 90, 107, 29), new Pixel(2, 1, 96, 255, 37),
            new Pixel(2, 2, 61, 166, 90), new Pixel(2, 3, 104, 40, 212),
            new Pixel(2, 4, 148, 122, 175), new Pixel(2, 5, 112, 51, 85)},
        {new Pixel(3, 0, 122, 150, 56), new Pixel(3, 1, 140, 255, 79),
            new Pixel(3, 2, 117, 231, 147), new Pixel(3, 3, 160, 105, 255),
            new Pixel(3, 4, 197, 162, 230), new Pixel(3, 5, 149, 80, 125)},
        {new Pixel(4, 0, 97, 99, 49), new Pixel(4, 1, 93, 244, 42),
            new Pixel(4, 2, 86, 155, 110), new Pixel(4, 3, 114, 71, 191),
            new Pixel(4, 4, 140, 117, 165), new Pixel(4, 5, 114, 57, 90)}};
    assertArrayEquals(grid, model.filter(greaterWidthImg, sharp).getImage());
    assertEquals("ExWidthImage", model.filter(greaterWidthImg, sharp).getFilename());
  }

  @Test
  public void sharpSmallerThanKernelDimForImage() {
    IPixel[][] twoByTwo = new IPixel[][]{
        {new Pixel(0, 0, 10, 100, 100), new Pixel(0, 1, 100, 6, 60)},
        {new Pixel(1, 0, 108, 0, 0), new Pixel(1, 1, 80, 100, 40)}};
    IImage img = new Image(twoByTwo, "TwoByTwo");
    assertArrayEquals(new IPixel[][]{{
            new Pixel(0, 0, 82, 126, 125), new Pixel(0, 1, 149, 56, 95)}, {
            new Pixel(1, 0, 155, 51, 50), new Pixel(1, 1, 134, 126, 80)}},
        model.filter(img, sharp).getImage());
    assertEquals("TwoByTwo", model.filter(img, sharp).getFilename());
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullUserFilter() {
    model.filter(greaterHeightImg, new UserFilter(null));
  }

  @Test(expected = IllegalArgumentException.class)
  public void emptyHeightKernel() {
    double[][] kernel = new double[][]{};
    user = new UserFilter(kernel);
    model.filter(greaterHeightImg, user);
  }

  @Test(expected = IllegalArgumentException.class)
  public void emptyRowKernel() {
    double[][] kernel = new double[][]{{}};
    user = new UserFilter(kernel);
    model.filter(greaterHeightImg, user);
  }

  @Test(expected = IllegalArgumentException.class)
  public void userFilterOnEmptyPixels() {
    double[][] kernel = new double[][]{
        {.5, .1, .1},
        {.5, .5, .5}};
    user = new UserFilter(kernel);
    empty = new IPixel[0][0];
    emptyImg = new Image(empty, "Empty");
    model.filter(emptyImg, user);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidEvenByEvenKernel() {
    double[][] kernel = new double[][]{
        {0.0625, 0.125},
        {0.125, 0.25}};
    model.filter(greaterWidthImg, new UserFilter(kernel));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidGreaterEvenWidthByOddHeightKernel() {
    double[][] kernel = new double[][]{
        {0.0625, 0.125, .125, .232},
        {0.125, 0.25, .352, .324},
        {0.125, 0.25, .352, .324}};
    model.filter(greaterWidthImg, new UserFilter(kernel));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidGreaterEvenHeightByOddWidthKernel() {
    double[][] kernel = new double[][]{
        {0.0625, 0.125, .125},
        {0.125, 0.25, .352},
        {0.125, 0.25, .352},
        {0.125, 0.25, .352}};
    model.filter(greaterWidthImg, new UserFilter(kernel));
  }

  @Test
  public void validGreaterOddHeightByEvenWidthKernel() {
    double[][] kernel = new double[][]{
        {.1, .5},
        {.5, 0.25},
        {.25, 0.25}};
    user = new UserFilter(kernel);
    IPixel[][] newImage = new IPixel[][]{
        {new Pixel(0, 0, 50, 24, 40), new Pixel(0, 1, 99, 137, 64), new Pixel(0, 2, 87, 170, 127),
            new Pixel(0, 3, 119, 74, 240)},
        {new Pixel(1, 0, 100, 49, 80), new Pixel(1, 1, 134, 242, 77),
            new Pixel(1, 2, 142, 210, 248), new Pixel(1, 3, 174, 122, 255)},
        {new Pixel(2, 0, 100, 49, 80), new Pixel(2, 1, 134, 242, 77),
            new Pixel(2, 2, 142, 210, 248), new Pixel(2, 3, 174, 122, 255)},
        {new Pixel(3, 0, 75, 37, 60), new Pixel(3, 1, 97, 180, 55), new Pixel(3, 2, 105, 150, 186),
            new Pixel(3, 3, 127, 90, 234)}
    };
    assertArrayEquals(newImage, model.filter(evenImg, user).getImage());
    assertEquals("ExImage", model.filter(evenImg, user).getFilename());
  }

  @Test
  public void validGreaterOddWidthByEvenHeightKernel() {
    double[][] kernel = new double[][]{
        {.5, .1, .1},
        {.5, .5, .5}};
    user = new UserFilter(kernel);

    IPixel[][] newImage = new IPixel[][]{
        {new Pixel(0, 0, 75, 125, 45), new Pixel(0, 1, 125, 145, 165),
            new Pixel(0, 2, 120, 164, 185), new Pixel(0, 3, 95, 64, 180)},
        {new Pixel(1, 0, 90, 150, 54), new Pixel(1, 1, 190, 194, 230),
            new Pixel(1, 2, 164, 255, 226), new Pixel(1, 3, 154, 92, 255)},
        {new Pixel(2, 0, 90, 150, 54), new Pixel(2, 1, 190, 194, 230),
            new Pixel(2, 2, 164, 255, 226), new Pixel(2, 3, 154, 92, 255)}};
    assertArrayEquals(newImage, model.filter(unevenImg, user).getImage());
    assertEquals("ExImage2", model.filter(unevenImg, user).getFilename());
  }

  @Test
  public void validGreaterOddWidthByOddHeightKernel() {
    double[][] kernel = new double[][]{{0.5, .90, .4}};
    user = new UserFilter(kernel);

    IPixel[][] newImage = new IPixel[][]{
        {new Pixel(0, 0, 0, 0, 0), new Pixel(0, 1, 0, 0, 0), new Pixel(0, 2, 0, 0, 0),
            new Pixel(0, 3, 0, 0, 0)},
        {new Pixel(1, 0, 110, 125, 76), new Pixel(1, 1, 135, 221, 145),
            new Pixel(1, 2, 151, 171, 255), new Pixel(1, 3, 131, 99, 228)},
        {new Pixel(2, 0, 110, 125, 76), new Pixel(2, 1, 135, 221, 145),
            new Pixel(2, 2, 151, 171, 255), new Pixel(2, 3, 131, 99, 228)}};
    assertArrayEquals(newImage, model.filter(unevenImg, user).getImage());
    assertEquals("ExImage2", model.filter(unevenImg, user).getFilename());
  }

  @Test
  public void validGreaterOddHeightByOddWidthKernel() {
    double[][] kernel = new double[][]{
        {0.5},
        {.25},
        {0.75}};
    user = new UserFilter(kernel);

    IPixel[][] newImage = new IPixel[][]{
        {new Pixel(0, 0, 0, 0, 0), new Pixel(0, 1, 100, 49, 80), new Pixel(0, 2, 49, 200, 9),
            new Pixel(0, 3, 100, 40, 240)},
        {new Pixel(1, 0, 0, 0, 0), new Pixel(1, 1, 150, 74, 120),
            new Pixel(1, 2, 74, 255, 14), new Pixel(1, 3, 150, 60, 255)},
        {new Pixel(2, 0, 0, 0, 0), new Pixel(2, 1, 75, 37, 60),
            new Pixel(2, 2, 37, 150, 7), new Pixel(2, 3, 75, 30, 180)}};
    assertArrayEquals(newImage, model.filter(unevenImg, user).getImage());
    assertEquals("ExImage2", model.filter(unevenImg, user).getFilename());
  }

  @Test
  public void validSquareKernel() {
    double[][] kernel = new double[][]{
        {.5, .1, .5},
        {.1, .5, .1},
        {.5, .1, .5}};
    user = new UserFilter(kernel);

    IPixel[][] newImage = new IPixel[][]{
        {new Pixel(0, 0, 90, 150, 54), new Pixel(0, 1, 150, 174, 198),
            new Pixel(0, 2, 144, 196, 222),
            new Pixel(0, 3, 114, 76, 216)},
        {new Pixel(1, 0, 125, 255, 67), new Pixel(1, 1, 255, 239, 255),
            new Pixel(1, 2, 224, 255, 255), new Pixel(1, 3, 173, 104, 255)},
        {new Pixel(2, 0, 90, 150, 54), new Pixel(2, 1, 150, 174, 198),
            new Pixel(2, 2, 144, 196, 222), new Pixel(2, 3, 114, 76, 216)}};
    assertArrayEquals(newImage, model.filter(unevenImg, user).getImage());
    assertEquals("ExImage2", model.filter(unevenImg, user).getFilename());
  }

  @Test
  public void oneByOneKernel() {
    double[][] kernel = new double[][]{
        {.5}};
    user = new UserFilter(kernel);
    IPixel[][] newImage = new IPixel[][]{
        {new Pixel(0, 0, 50, 25, 40), new Pixel(0, 1, 25, 100, 5),
            new Pixel(0, 2, 50, 20, 120), new Pixel(0, 3, 45, 44, 60)},
        {new Pixel(1, 0, 50, 25, 40), new Pixel(1, 1, 25, 100, 5),
            new Pixel(1, 2, 50, 20, 120), new Pixel(1, 3, 45, 44, 60)},
        {new Pixel(2, 0, 50, 25, 40), new Pixel(2, 1, 25, 100, 5),
            new Pixel(2, 2, 50, 20, 120), new Pixel(2, 3, 45, 44, 60)}};
    assertArrayEquals(newImage, model.filter(unevenImg, user).getImage());
    assertEquals("ExImage2", model.filter(unevenImg, user).getFilename());
  }

  @Test
  public void testKernelLargerThanImage() {
    double[][] kernel = new double[][]{
        {.05, .5, .05, .5, .05},
        {.5, .05, .5, .05, .5},
        {.5, .5, .05, .5, .5},
        {.5, .05, .5, .05, .5},
        {.05, .5, .05, .5, .05}};
    user = new UserFilter(kernel);
    IPixel[][] newImage = new IPixel[][]{
        {new Pixel(0, 0, 206, 255, 184), new Pixel(0, 1, 228, 255, 215),
            new Pixel(0, 2, 210, 255, 166)},
        {new Pixel(1, 0, 255, 255, 255), new Pixel(1, 1, 165, 255, 130),
            new Pixel(1, 2, 255, 255, 251)},
        {new Pixel(2, 0, 206, 255, 184), new Pixel(2, 1, 228, 255, 215),
            new Pixel(2, 2, 210, 255, 166)}};
    assertArrayEquals(newImage, model.filter(oddByOddImg, user).getImage());
    assertEquals("ExOddImage", model.filter(oddByOddImg, user).getFilename());
  }

  @Test
  public void test255Clamping() {
    double[][] kernel = new double[][]{{.80, .80, .9}};
    user = new UserFilter(kernel);
    IPixel[][] threeByThree = new IPixel[][]{
        {new Pixel(0, 0, 255, 255, 255), new Pixel(0, 1, 255, 255, 255),
            new Pixel(0, 2, 255, 255, 255)},
        {new Pixel(1, 0, 200, 200, 255), new Pixel(1, 1, 200, 200, 200),
            new Pixel(1, 2, 255, 255, 255)},
        {new Pixel(2, 0, 255, 255, 255), new Pixel(2, 1, 255, 255, 255),
            new Pixel(2, 2, 255, 255, 255)}};
    IImage img = new Image(threeByThree, "Clamping");
    assertArrayEquals(new IPixel[][]{
        {new Pixel(0, 0, 0, 0, 0), new Pixel(0, 1, 0, 0, 0), new Pixel(0, 2, 0, 0, 0)},
        {new Pixel(1, 0, 255, 255, 255), new Pixel(1, 1, 255, 255, 255),
            new Pixel(1, 2, 255, 255, 255)},
        {new Pixel(2, 0, 255, 255, 255), new Pixel(2, 1, 255, 255, 255),
            new Pixel(2, 2, 255, 255, 255)}}, model.filter(img, user).getImage());
    assertEquals("Clamping", model.filter(img, user).getFilename());
  }

  @Test
  public void test0Clamping() {
    double[][] kernel = new double[][]{{-.50, -.80, -.9}};
    user = new UserFilter(kernel);
    IPixel[][] threeByThree = new IPixel[][]{
        {new Pixel(0, 0, 255, 255, 255), new Pixel(0, 1, 255, 255, 255),
            new Pixel(0, 2, 255, 255, 255)},
        {new Pixel(1, 0, 200, 200, 255), new Pixel(1, 1, 200, 200, 200),
            new Pixel(1, 2, 255, 255, 255)},
        {new Pixel(2, 0, 255, 255, 255), new Pixel(2, 1, 255, 255, 255),
            new Pixel(2, 2, 255, 255, 255)}};
    IImage img = new Image(threeByThree, "Clamping");
    assertArrayEquals(new IPixel[][]{
            {new Pixel(0, 0, 0, 0, 0), new Pixel(0, 1, 0, 0, 0), new Pixel(0, 2, 0, 0, 0)},
            {new Pixel(1, 0, 0, 0, 0), new Pixel(1, 1, 0, 0, 0), new Pixel(1, 2, 0, 0, 0)},
            {new Pixel(2, 0, 0, 0, 0), new Pixel(2, 1, 0, 0, 0), new Pixel(2, 2, 0, 0, 0)}},
        model.filter(img, user).getImage());
    assertEquals("Clamping", model.filter(img, user).getFilename());
  }

  //Create Image
  @Test(expected = IllegalArgumentException.class)
  public void nullCreator() {
    model.createImage(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidSizeOfTileForPersonalizedConstructor() {
    model.createImage(new CheckboardImageCreator(0, 1, 3, 4, 5, 6, 35, 54));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidNumTilesForPersonalizedConstructor() {
    model.createImage(new CheckboardImageCreator(1, 0, 3, 4, 5, 6, 35, 54));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidLowRedColor1() {
    model.createImage(new CheckboardImageCreator(1, 1, -1, 4, 5, 6, 35, 54));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidHighRedColor1() {
    model.createImage(new CheckboardImageCreator(1, 1, 256, 4, 5, 6, 35, 54));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidLowRedColor2() {
    model.createImage(new CheckboardImageCreator(1, 1, 1, 4, 5, -1, 35, 54));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidHighRedColor2() {
    model.createImage(new CheckboardImageCreator(1, 1, 1, 4, 5, 256, 35, 54));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidLowGreenColor1() {
    model.createImage(new CheckboardImageCreator(1, 1, 1, -1, 5, 23, 35, 54));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidHighGreenColor1() {
    model.createImage(new CheckboardImageCreator(1, 1, 1, 256, 5, 23, 35, 54));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidLowGreenColor2() {
    model.createImage(new CheckboardImageCreator(1, 1, 1, -1, 5, 23, 35, 54));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidHighGreenColor2() {
    model.createImage(new CheckboardImageCreator(1, 1, 1, 256, 5, 23, 35, 54));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidLowBlueColor1() {
    model.createImage(new CheckboardImageCreator(1, 1, 1, 1, -1, 23, 35, 54));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidHighBlueColor1() {
    model.createImage(new CheckboardImageCreator(1, 1, 1, 5, 453, 23, 35, 54));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidLowBlueColor2() {
    model.createImage(new CheckboardImageCreator(1, 1, 1, 1, 1, 23, 35, -54));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidHighBlueColor2() {
    model.createImage(new CheckboardImageCreator(1, 1, 1, 5, 255, 23, 35, 345));
  }

  @Test(expected = IllegalArgumentException.class)
  public void allInvalidInputsForPersonalizedConstructor() {
    model.createImage(new CheckboardImageCreator(-1, -1, -1, 5132, 255, -324, 335, 345));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidSizeOfTile() {
    model.createImage(new CheckboardImageCreator(0, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidNumTiles() {
    model.createImage(new CheckboardImageCreator(1, 0));
  }

  @Test
  public void createSingleSquareBoard() {
    IPixel[][] grid = new IPixel[][]{{new Pixel(0, 0, 255, 0, 0)}};
    assertArrayEquals(grid, model.createImage(singleSquareBoard).getImage());
    assertEquals("Checkerboard", model.createImage(singleSquareBoard).getFilename());
  }

  @Test
  public void createTwoByTwoBoard() {
    IPixel[][] grid = new IPixel[][]{
        {new Pixel(0, 0, 50, 100, 150), new Pixel(0, 1, 50, 100, 150),
            new Pixel(0, 2, 50, 100, 150),
            new Pixel(0, 3, 8, 16, 62), new Pixel(0, 4, 8, 16, 62), new Pixel(0, 5, 8, 16, 62)},
        {new Pixel(1, 0, 50, 100, 150), new Pixel(1, 1, 50, 100, 150),
            new Pixel(1, 2, 50, 100, 150),
            new Pixel(1, 3, 8, 16, 62), new Pixel(1, 4, 8, 16, 62), new Pixel(1, 5, 8, 16, 62)},
        {new Pixel(2, 0, 50, 100, 150), new Pixel(2, 1, 50, 100, 150),
            new Pixel(2, 2, 50, 100, 150),
            new Pixel(2, 3, 8, 16, 62), new Pixel(2, 4, 8, 16, 62), new Pixel(2, 5, 8, 16, 62)},
        {new Pixel(3, 0, 8, 16, 62), new Pixel(3, 1, 8, 16, 62), new Pixel(3, 2, 8, 16, 62),
            new Pixel(3, 3, 50, 100, 150), new Pixel(3, 4, 50, 100, 150),
            new Pixel(3, 5, 50, 100, 150)},
        {new Pixel(4, 0, 8, 16, 62), new Pixel(4, 1, 8, 16, 62), new Pixel(4, 2, 8, 16, 62),
            new Pixel(4, 3, 50, 100, 150), new Pixel(4, 4, 50, 100, 150),
            new Pixel(4, 5, 50, 100, 150)},
        {new Pixel(5, 0, 8, 16, 62), new Pixel(5, 1, 8, 16, 62), new Pixel(5, 2, 8, 16, 62),
            new Pixel(5, 3, 50, 100, 150), new Pixel(5, 4, 50, 100, 150),
            new Pixel(5, 5, 50, 100, 150)}};
    assertArrayEquals(grid, model.createImage(twoByTwoCheckerboard).getImage());
    assertEquals("Checkerboard", model.createImage(twoByTwoCheckerboard).getFilename());
  }

  @Test
  public void createThreeByThreeBoard() {
    IPixel[][] grid = new IPixel[][]{
        {new Pixel(0, 0, 255, 255, 255), new Pixel(0, 1, 0, 0, 0), new Pixel(0, 2, 255, 255, 255)},
        {new Pixel(1, 0, 0, 0, 0), new Pixel(1, 1, 255, 255, 255), new Pixel(1, 2, 0, 0, 0)},
        {new Pixel(2, 0, 255, 255, 255), new Pixel(2, 1, 0, 0, 0), new Pixel(2, 2, 255, 255, 255)}};
    assertArrayEquals(grid, model.createImage(threeByThreeCheckerboard).getImage());
    assertEquals("Checkerboard", model.createImage(threeByThreeCheckerboard).getFilename());
  }

  @Test
  public void createFourByFour() {
    IPixel[][] grid = new IPixel[][]{
        {new Pixel(0, 0, 255, 0, 0), new Pixel(0, 1, 255, 0, 0),
            new Pixel(0, 2, 0, 0, 0), new Pixel(0, 3, 0, 0, 0),
            new Pixel(0, 4, 255, 0, 0), new Pixel(0, 5, 255, 0, 0),
            new Pixel(0, 6, 0, 0, 0), new Pixel(0, 7, 0, 0, 0)},
        {new Pixel(1, 0, 255, 0, 0), new Pixel(1, 1, 255, 0, 0),
            new Pixel(1, 2, 0, 0, 0), new Pixel(1, 3, 0, 0, 0),
            new Pixel(1, 4, 255, 0, 0), new Pixel(1, 5, 255, 0, 0),
            new Pixel(1, 6, 0, 0, 0), new Pixel(1, 7, 0, 0, 0)},
        {new Pixel(2, 0, 0, 0, 0), new Pixel(2, 1, 0, 0, 0),
            new Pixel(2, 2, 255, 0, 0), new Pixel(2, 3, 255, 0, 0),
            new Pixel(2, 4, 0, 0, 0), new Pixel(2, 5, 0, 0, 0),
            new Pixel(2, 6, 255, 0, 0), new Pixel(2, 7, 255, 0, 0)},
        {new Pixel(3, 0, 0, 0, 0), new Pixel(3, 1, 0, 0, 0),
            new Pixel(3, 2, 255, 0, 0), new Pixel(3, 3, 255, 0, 0),
            new Pixel(3, 4, 0, 0, 0), new Pixel(3, 5, 0, 0, 0),
            new Pixel(3, 6, 255, 0, 0), new Pixel(3, 7, 255, 0, 0)},
        {new Pixel(4, 0, 255, 0, 0), new Pixel(4, 1, 255, 0, 0),
            new Pixel(4, 2, 0, 0, 0), new Pixel(4, 3, 0, 0, 0),
            new Pixel(4, 4, 255, 0, 0), new Pixel(4, 5, 255, 0, 0),
            new Pixel(4, 6, 0, 0, 0), new Pixel(4, 7, 0, 0, 0)},
        {new Pixel(5, 0, 255, 0, 0), new Pixel(5, 1, 255, 0, 0),
            new Pixel(5, 2, 0, 0, 0), new Pixel(5, 3, 0, 0, 0),
            new Pixel(5, 4, 255, 0, 0), new Pixel(5, 5, 255, 0, 0),
            new Pixel(5, 6, 0, 0, 0), new Pixel(5, 7, 0, 0, 0)},
        {new Pixel(6, 0, 0, 0, 0), new Pixel(6, 1, 0, 0, 0),
            new Pixel(6, 2, 255, 0, 0), new Pixel(6, 3, 255, 0, 0),
            new Pixel(6, 4, 0, 0, 0), new Pixel(6, 5, 0, 0, 0),
            new Pixel(6, 6, 255, 0, 0), new Pixel(6, 7, 255, 0, 0)},
        {new Pixel(7, 0, 0, 0, 0), new Pixel(7, 1, 0, 0, 0),
            new Pixel(7, 2, 255, 0, 0), new Pixel(7, 3, 255, 0, 0),
            new Pixel(7, 4, 0, 0, 0), new Pixel(7, 5, 0, 0, 0),
            new Pixel(7, 6, 255, 0, 0), new Pixel(7, 7, 255, 0, 0)}};
    assertArrayEquals(grid, model.createImage(defaultFourByFourCheckerboard).getImage());
    assertEquals("Checkerboard", model.createImage(defaultFourByFourCheckerboard).getFilename());
  }

  // color transformation model tests

  @Test(expected = IllegalArgumentException.class)
  public void testApplyGrayscaleWhenGivenImageIsNull() {
    this.model.colorTransformation(null, this.sepia);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyGrayscaleWhenColorTransformationIsNull() {
    this.model.colorTransformation(this.sampleImage, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelMalformedMatrixColorTransformationMethod() {
    double[][] malformedMatrix = {{1, 2, 3}, {3, 4, 5}};
    this.model.colorTransformation(this.sampleImage, new UserColorTransformation(malformedMatrix));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelNullMatrixColorTransformationMethod() {
    this.model.colorTransformation(this.sampleImage, new UserColorTransformation(null));
  }

  @Test
  public void testApplyGrayscale1By2Image() {
    assertArrayEquals(new IPixel[][]{{new Pixel(0, 0, 100, 50, 80),
        new Pixel(0, 1, 50, 200, 10)}}, this.sampleImageGrid1By2);
    IImage grayscale1By2Image = this.model
        .colorTransformation(this.sampleImage1By2, new Grayscale());
    assertArrayEquals(new IPixel[][]{{new Pixel(0, 0, 63, 63, 63),
        new Pixel(0, 1, 160, 160, 160)}}, grayscale1By2Image.getImage());
  }

  @Test
  public void testApplySepia1By2Image() {
    assertArrayEquals(new IPixel[][]{{new Pixel(0, 0, 100, 50, 80),
        new Pixel(0, 1, 50, 200, 10)}}, this.sampleImageGrid1By2);
    IImage sepia1By2Image = this.model.colorTransformation(this.sampleImage1By2, new Sepia());
    assertArrayEquals(new IPixel[][]{{new Pixel(0, 0, 92, 81, 63),
        new Pixel(0, 1, 173, 155, 120)}}, sepia1By2Image.getImage());
  }

  @Test
  public void testApplyUserGenerated1By2Image() {
    assertArrayEquals(new IPixel[][]{{new Pixel(0, 0, 100, 50, 80),
        new Pixel(0, 1, 50, 200, 10)}}, this.sampleImageGrid1By2);
    IImage userGenerated1By2Image = this.model
        .colorTransformation(this.sampleImage1By2, this.userGenerated);
    assertArrayEquals(new IPixel[][]{{new Pixel(0, 0, 115, 23, 46),
        new Pixel(0, 1, 130, 26, 52)}}, userGenerated1By2Image.getImage());
  }

  @Test
  public void testApplyGrayscaleCheckerboardMakeSureDoesNotAlterOriginalImage() {
    IImage preTransformBoard = this.defaultCheckerboard;
    IImage grayScaleBoard = this.model
        .colorTransformation(this.defaultCheckerboard, new Grayscale());
    IImage postTransformBoard = this.defaultCheckerboard;

    // should be the same even after applying grayscale, grayscale should create a new copy of
    // the image instead of altering the original
    assertEquals(preTransformBoard, postTransformBoard);
    // should be different
    assertNotEquals(preTransformBoard, grayScaleBoard);
    assertNotEquals(postTransformBoard, grayScaleBoard);
  }

  @Test
  public void testApplySepiaCheckerboardMakeSureDoesNotAlterOriginalImage() {
    IImage preTransformBoard = this.defaultCheckerboard;
    IImage sepiaBoard = this.model
        .colorTransformation(this.defaultCheckerboard, new Sepia());
    IImage postTransformBoard = this.defaultCheckerboard;

    // should be the same even after applying sepia, sepia should create a new copy of
    // the image instead of altering the original
    assertEquals(preTransformBoard, postTransformBoard);
    // should be different
    assertNotEquals(preTransformBoard, sepiaBoard);
    assertNotEquals(postTransformBoard, sepiaBoard);
  }

  @Test
  public void testApplyUserGeneratedCheckerboardMakeSureDoesNotAlterOriginalImage() {
    IImage preTransformBoard = this.defaultCheckerboard;
    IImage userBoard = this.model
        .colorTransformation(this.defaultCheckerboard, this.userGenerated);
    IImage postTransformBoard = this.defaultCheckerboard;

    // should be the same even after applying user filter, user filter should create a new copy of
    // the image instead of altering the original
    assertEquals(preTransformBoard, postTransformBoard);
    // should be different
    assertNotEquals(preTransformBoard, userBoard);
    assertNotEquals(postTransformBoard, userBoard);
  }

  @Test
  public void testApplyGrayscaleToSampleImage() {
    IImage grayscaleSampleImage = this.model
        .colorTransformation(this.sampleImage, new Grayscale());

    assertArrayEquals(new IPixel[][]{
        {new Pixel(0, 0, 63, 63, 63),
            new Pixel(0, 1, 160, 160, 160),
            new Pixel(0, 2, 68, 68, 68),
            new Pixel(0, 3, 93, 93, 93)},
        {new Pixel(1, 0, 63, 63, 63),
            new Pixel(1, 1, 160, 160, 160),
            new Pixel(1, 2, 68, 68, 68),
            new Pixel(1, 3, 93, 93, 93)},
        {new Pixel(2, 0, 63, 63, 63),
            new Pixel(2, 1, 160, 160, 160),
            new Pixel(2, 2, 68, 68, 68),
            new Pixel(2, 3, 93, 93, 93)}}, grayscaleSampleImage.getImage());
  }

  @Test
  public void testApplySepiaToSampleImage() {
    IImage sepiaSampleImage = this.model
        .colorTransformation(this.sampleImage, new Sepia());

    assertArrayEquals(new IPixel[][]{
        {new Pixel(0, 0, 92, 81, 63),
            new Pixel(0, 1, 173, 155, 120),
            new Pixel(0, 2, 114, 101, 79),
            new Pixel(0, 3, 124, 111, 85)},
        {new Pixel(1, 0, 92, 81, 63),
            new Pixel(1, 1, 173, 155, 120),
            new Pixel(1, 2, 114, 101, 79),
            new Pixel(1, 3, 124, 111, 85)},
        {new Pixel(2, 0, 92, 81, 63),
            new Pixel(2, 1, 173, 155, 120),
            new Pixel(2, 2, 114, 101, 79),
            new Pixel(2, 3, 124, 111, 85)}}, sepiaSampleImage.getImage());
  }

  @Test
  public void testApplyUserGeneratedColorTransformationToSampleImage() {
    IImage userGeneratedSampleImage = this.model
        .colorTransformation(this.sampleImage, this.userGenerated);

    assertArrayEquals(new IPixel[][]{
        {new Pixel(0, 0, 115, 23, 46),
            new Pixel(0, 1, 130, 26, 52),
            new Pixel(0, 2, 190, 38, 76),
            new Pixel(0, 3, 149, 29, 59)},
        {new Pixel(1, 0, 115, 23, 46),
            new Pixel(1, 1, 130, 26, 52),
            new Pixel(1, 2, 190, 38, 76),
            new Pixel(1, 3, 149, 29, 59)},
        {new Pixel(2, 0, 115, 23, 46),
            new Pixel(2, 1, 130, 26, 52),
            new Pixel(2, 2, 190, 38, 76),
            new Pixel(2, 3, 149, 29, 59)}}, userGeneratedSampleImage.getImage());
  }

  @Test
  public void testClampingUserGeneratedColorTransformationForMaxShouldBeAll255() {
    IImage doubledSampleImage = this.model
        .colorTransformation(this.sampleImage, doubleUserGenerated);

    assertArrayEquals(new IPixel[][]{
        {new Pixel(0, 0, 255, 255, 255),
            new Pixel(0, 1, 255, 255, 255),
            new Pixel(0, 2, 255, 255, 255),
            new Pixel(0, 3, 255, 255, 255)},
        {new Pixel(1, 0, 255, 255, 255),
            new Pixel(1, 1, 255, 255, 255),
            new Pixel(1, 2, 255, 255, 255),
            new Pixel(1, 3, 255, 255, 255)},
        {new Pixel(2, 0, 255, 255, 255),
            new Pixel(2, 1, 255, 255, 255),
            new Pixel(2, 2, 255, 255, 255),
            new Pixel(2, 3, 255, 255, 255)}}, doubledSampleImage.getImage());
  }

  @Test
  public void testClampingUserGeneratedColorTransformationForMinShouldBeAll0() {
    IImage negativeSampleImage = this.model
        .colorTransformation(this.sampleImage, negativeUserGenerated);

    assertArrayEquals(new IPixel[][]{
        {new Pixel(0, 0, 0, 0, 0),
            new Pixel(0, 1, 0, 0, 0),
            new Pixel(0, 2, 0, 0, 0),
            new Pixel(0, 3, 0, 0, 0)},
        {new Pixel(1, 0, 0, 0, 0),
            new Pixel(1, 1, 0, 0, 0),
            new Pixel(1, 2, 0, 0, 0),
            new Pixel(1, 3, 0, 0, 0)},
        {new Pixel(2, 0, 0, 0, 0),
            new Pixel(2, 1, 0, 0, 0),
            new Pixel(2, 2, 0, 0, 0),
            new Pixel(2, 3, 0, 0, 0)}}, negativeSampleImage.getImage());
  }

  /**
   * Constructs an instance of the class under test representing the Image Processing Model.
   *
   * @return an instance of the class under test
   */
  protected abstract IModel makeModel();

  /**
   * Concrete class for testing the {@code Model} implementation of {@code IModel} interface.
   */
  public static final class SimpleModelTest extends AbstractModelTest {

    @Override
    protected IModel makeModel() {
      return new Model();
    }
  }

  /**
   * Concrete class for testing the {@code LayerModel} implementation of {@code IModel} interface.
   */
  public static final class LayerModelTest extends AbstractModelTest {

    @Override
    protected IModel makeModel() {
      return new LayerModel();
    }
  }
}
