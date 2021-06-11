import model.color.Grayscale;
import model.color.IColorTransformation;
import model.color.Sepia;
import model.color.UserColorTransformation;
import model.creator.CheckboardImageCreator;
import model.image.IImage;
import model.image.IPixel;
import model.image.PPMImage;
import model.image.Pixel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Represents the test class for {@code IColorTransformation} to ensure that the object's behavior
 * works as expected and properly transforms the given image.
 */
public class IColorTransformationTest {

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


  @Before
  public void initData() {
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
    this.sampleImageGrid1By2 = new IPixel[][]{{new Pixel(0, 0, 100, 50, 80),
        new Pixel(0, 1, 50, 200, 10)}};
    this.sampleImage1By2 = new PPMImage(sampleImageGrid1By2, "OneByTwoGrid");
    this.grayscale = new Grayscale();
    this.sepia = new Sepia();
    this.userMatrix = new double[][]{{0.5, 0.5, 0.5}, {0.1, 0.1, 0.1}, {0.2, 0.2, 0.2}};
    this.doubleMatrix = new double[][]{{2, 2, 2}, {2, 2, 2}, {2, 2, 2}};
    this.doubleUserGenerated = new UserColorTransformation(doubleMatrix);
    this.negativeMatrix = new double[][]{{-1, -1, -1}, {-2, -2, -2}, {-0.3, -0.1, 0.1}};
    this.negativeUserGenerated = new UserColorTransformation(negativeMatrix);
    this.userGenerated = new UserColorTransformation(userMatrix);
    this.defaultCheckerboard = new CheckboardImageCreator(4, 3).createImage();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyGrayscaleWhenGivenImageIsNull() {
    this.grayscale.apply(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplySepiaWhenGivenImageIsNull() {
    this.sepia.apply(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyUserColorTransformationWhenGivenImageIsNull() {
    this.userGenerated.apply(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionUserMatrixIsNot3x3() {
    double[][] malformedMatrix = {{1, 2, 3}, {3, 4, 5}};
    IColorTransformation user = new UserColorTransformation(malformedMatrix);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionUserMatrixIsNull() {
    IColorTransformation user = new UserColorTransformation(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyNull() {
    IImage image = this.sepia.apply(null);
  }

  @Test
  public void testApplyGrayscale1By2Image() {
    assertArrayEquals(new IPixel[][]{{new Pixel(0, 0, 100, 50, 80),
        new Pixel(0, 1, 50, 200, 10)}}, this.sampleImageGrid1By2);
    IImage grayscale1By2Image = this.grayscale.apply(this.sampleImage1By2);
    assertArrayEquals(new IPixel[][]{{new Pixel(0, 0, 63, 63, 63),
        new Pixel(0, 1, 160, 160, 160)}}, grayscale1By2Image.getImage());
  }

  @Test
  public void testApplySepia1By2Image() {
    assertArrayEquals(new IPixel[][]{{new Pixel(0, 0, 100, 50, 80),
        new Pixel(0, 1, 50, 200, 10)}}, this.sampleImageGrid1By2);
    IImage sepia1By2Image = this.sepia.apply(this.sampleImage1By2);
    assertArrayEquals(new IPixel[][]{{new Pixel(0, 0, 92, 81, 63),
        new Pixel(0, 1, 173, 155, 120)}}, sepia1By2Image.getImage());
  }

  @Test
  public void testApplyUserGenerated1By2Image() {
    assertArrayEquals(new IPixel[][]{{new Pixel(0, 0, 100, 50, 80),
        new Pixel(0, 1, 50, 200, 10)}}, this.sampleImageGrid1By2);
    IImage userGenerated1By2Image = this.userGenerated.apply(this.sampleImage1By2);
    assertArrayEquals(new IPixel[][]{{new Pixel(0, 0, 115, 23, 46),
        new Pixel(0, 1, 130, 26, 52)}}, userGenerated1By2Image.getImage());
  }

  @Test
  public void testApplyGrayscaleCheckerboardMakeSureDoesNotAlterOriginalImage() {
    IImage preTransformBoard = this.defaultCheckerboard;
    IImage grayScaleBoard = this.grayscale.apply(this.defaultCheckerboard);
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
    IImage sepiaBoard = this.sepia.apply(this.defaultCheckerboard);
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
    IImage userBoard = this.userGenerated.apply(this.defaultCheckerboard);
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
    IImage grayscaleSampleImage = this.grayscale.apply(this.sampleImage);

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
    IImage sepiaSampleImage = this.sepia.apply(this.sampleImage);

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
    IImage userGeneratedSampleImage = this.userGenerated.apply(this.sampleImage);

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
    IImage doubledSampleImage = this.doubleUserGenerated.apply(this.sampleImage);

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
    IImage negativeSampleImage = this.negativeUserGenerated.apply(this.sampleImage);

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

}
