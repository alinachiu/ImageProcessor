import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import model.filter.Blur;
import model.filter.IFilter;
import model.filter.Sharpening;
import model.filter.UserFilter;
import model.image.IImage;
import model.image.IPixel;
import model.image.PPMImage;
import model.image.Pixel;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests for methods in the IFilter interface (using the classes that implement it --
 * Blue, Sharpening, and UserFilter).
 */
public class IFilterTest {

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

  @Before
  public void setUp() {
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
    evenImg = new PPMImage(evenImage, "ExImage");
    unevenImage = new IPixel[][]{
        {new Pixel(0, 0, 100, 50, 80), new Pixel(0, 1, 50, 200, 10),
            new Pixel(0, 2, 100, 40, 240), new Pixel(0, 3, 90, 88, 120)},
        {new Pixel(1, 0, 100, 50, 80), new Pixel(1, 1, 50, 200, 10),
            new Pixel(1, 2, 100, 40, 240), new Pixel(1, 3, 90, 88, 120)},
        {new Pixel(2, 0, 100, 50, 80), new Pixel(2, 1, 50, 200, 10),
            new Pixel(2, 2, 100, 40, 240), new Pixel(2, 3, 90, 88, 120)}};
    unevenImg = new PPMImage(unevenImage, "ExImage2");
    oddByOddImage = new IPixel[][]{
        {new Pixel(0, 0, 100, 50, 80), new Pixel(0, 1, 50, 200, 10),
            new Pixel(0, 2, 90, 88, 120)},
        {new Pixel(1, 0, 100, 50, 80), new Pixel(1, 1, 50, 200, 10),
            new Pixel(1, 2, 90, 88, 120)},
        {new Pixel(2, 0, 100, 50, 80), new Pixel(2, 1, 50, 200, 10),
            new Pixel(2, 2, 90, 88, 120)}};
    oddByOddImg = new PPMImage(oddByOddImage, "ExOddImage");
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
    greaterHeightImg = new PPMImage(greaterHeight, "ExHeightImage");
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
    greaterWidthImg = new PPMImage(greaterWidth, "ExWidthImage");

  }

  @Test(expected = IllegalArgumentException.class)
  public void applyNull() {
    blur.apply(null);
  }

  //Tests for Blur
  @Test(expected = IllegalArgumentException.class)
  public void nullEmptyImgBlur() {
    blur.apply(new PPMImage(new IPixel[][]{}, "Empty"));
  }

  @Test
  public void blurOneByOneImg() {
    IPixel[][] oneByOne = new IPixel[][]{{new Pixel(0, 0, 10, 100, 200)}};
    IImage img = new PPMImage(oneByOne, "OneByOne");
    assertArrayEquals(new IPixel[][]{{new Pixel(0, 0, 2, 25, 50)}}, blur.apply(img).getImage());
    assertEquals("OneByOneFilter", blur.apply(img).getFilename());
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
    assertArrayEquals(grid, blur.apply(greaterHeightImg).getImage());
    assertEquals("ExHeightImageFilter", blur.apply(greaterHeightImg).getFilename());
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
    assertArrayEquals(grid, blur.apply(greaterWidthImg).getImage());
    assertEquals("ExWidthImageFilter", blur.apply(greaterWidthImg).getFilename());
  }

  @Test
  public void blurSmallerThanKernelDimForImage() {
    IPixel[][] twoByTwo = new IPixel[][]{
        {new Pixel(0, 0, 10, 100, 100), new Pixel(0, 1, 100, 6, 60)},
        {new Pixel(1, 0, 108, 0, 0), new Pixel(1, 1, 80, 100, 40)}};
    IImage img = new PPMImage(twoByTwo, "TwoByTwo");
    assertArrayEquals(new IPixel[][]{{new Pixel(0, 0, 32, 31, 34), new Pixel(0, 1, 42, 25, 32)},
        {new Pixel(1, 0, 44, 24, 20), new Pixel(1, 1, 45, 31, 23)}}, blur.apply(img).getImage());
    assertEquals("TwoByTwoFilter", blur.apply(img).getFilename());
  }

  //Test for Sharpening
  @Test(expected = IllegalArgumentException.class)
  public void nullEmptyImgSharp() {
    sharp.apply(new PPMImage(new IPixel[][]{}, "Empty"));
  }

  @Test
  public void sharpOneByOneImg() {
    IPixel[][] oneByOne = new IPixel[][]{{new Pixel(0, 0, 10, 100, 200)}};
    IImage img = new PPMImage(oneByOne, "OneByOne");
    assertArrayEquals(new IPixel[][]{{new Pixel(0, 0, 10, 100, 200)}}, sharp.apply(img).getImage());
    assertEquals("OneByOneFilter", sharp.apply(img).getFilename());
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
    assertArrayEquals(grid, sharp.apply(greaterHeightImg).getImage());
    assertEquals("ExHeightImageFilter", sharp.apply(greaterHeightImg).getFilename());
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
    assertArrayEquals(grid, sharp.apply(greaterWidthImg).getImage());
    assertEquals("ExWidthImageFilter", sharp.apply(greaterWidthImg).getFilename());
  }

  @Test
  public void sharpSmallerThanKernelDimForImage() {
    IPixel[][] twoByTwo = new IPixel[][]{
        {new Pixel(0, 0, 10, 100, 100), new Pixel(0, 1, 100, 6, 60)},
        {new Pixel(1, 0, 108, 0, 0), new Pixel(1, 1, 80, 100, 40)}};
    IImage img = new PPMImage(twoByTwo, "TwoByTwo");
    assertArrayEquals(new IPixel[][]{{
        new Pixel(0, 0, 82, 126, 125), new Pixel(0, 1, 149, 56, 95)}, {
        new Pixel(1, 0, 155, 51, 50), new Pixel(1, 1, 134, 126, 80)}},
        sharp.apply(img).getImage());
    assertEquals("TwoByTwoFilter", sharp.apply(img).getFilename());
  }


  //Tests for UserFilter
  @Test(expected = IllegalArgumentException.class)
  public void nullUserFilter() {
    new UserFilter(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void emptyHeightKernel() {
    double[][] kernel = new double[][]{};
    user = new UserFilter(kernel);
    user.apply(oddByOddImg).getImage();
  }

  @Test(expected = IllegalArgumentException.class)
  public void emptyRowKernel() {
    double[][] kernel = new double[][]{{}};
    user = new UserFilter(kernel);
    user.apply(oddByOddImg).getImage();
  }

  @Test(expected = IllegalArgumentException.class)
  public void userFilterOnEmptyPixels() {
    double[][] kernel = new double[][]{
        {.5, .1, .1},
        {.5, .5, .5}};
    user = new UserFilter(kernel);
    empty = new IPixel[0][0];
    emptyImg = new PPMImage(empty, "Empty");
    user.apply(emptyImg).getImage();
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidEvenByEvenKernel() {
    double[][] kernel = new double[][]{
        {0.0625, 0.125},
        {0.125, 0.25}};
    new UserFilter(kernel);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidGreaterEvenWidthByOddHeightKernel() {
    double[][] kernel = new double[][]{
        {0.0625, 0.125, .125, .232},
        {0.125, 0.25, .352, .324},
        {0.125, 0.25, .352, .324}};
    new UserFilter(kernel);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidGreaterEvenHeightByOddWidthKernel() {
    double[][] kernel = new double[][]{
        {0.0625, 0.125, .125},
        {0.125, 0.25, .352},
        {0.125, 0.25, .352},
        {0.125, 0.25, .352}};
    new UserFilter(kernel);
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
    assertArrayEquals(newImage, user.apply(evenImg).getImage());
    assertEquals("ExImageFilter", user.apply(evenImg).getFilename());
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
    assertArrayEquals(newImage, user.apply(unevenImg).getImage());
    assertEquals("ExImage2Filter", user.apply(unevenImg).getFilename());
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
    assertArrayEquals(newImage, user.apply(unevenImg).getImage());
    assertEquals("ExImage2Filter", user.apply(unevenImg).getFilename());
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
    assertArrayEquals(newImage, user.apply(unevenImg).getImage());
    assertEquals("ExImage2Filter", user.apply(unevenImg).getFilename());
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
    assertArrayEquals(newImage, user.apply(unevenImg).getImage());
    assertEquals("ExImage2Filter", user.apply(unevenImg).getFilename());
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
    assertArrayEquals(newImage, user.apply(unevenImg).getImage());
    assertEquals("ExImage2Filter", user.apply(unevenImg).getFilename());
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
    assertArrayEquals(newImage, user.apply(oddByOddImg).getImage());
    assertEquals("ExOddImageFilter", user.apply(oddByOddImg).getFilename());
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
    IImage img = new PPMImage(threeByThree, "Clamping");
    assertArrayEquals(new IPixel[][]{
        {new Pixel(0, 0, 0, 0, 0), new Pixel(0, 1, 0, 0, 0), new Pixel(0, 2, 0, 0, 0)},
        {new Pixel(1, 0, 255, 255, 255), new Pixel(1, 1, 255, 255, 255),
            new Pixel(1, 2, 255, 255, 255)},
        {new Pixel(2, 0, 255, 255, 255), new Pixel(2, 1, 255, 255, 255),
            new Pixel(2, 2, 255, 255, 255)}}, user.apply(img).getImage());
    assertEquals("ClampingFilter", sharp.apply(img).getFilename());
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
    IImage img = new PPMImage(threeByThree, "Clamping");
    assertArrayEquals(new IPixel[][]{
            {new Pixel(0, 0, 0, 0, 0), new Pixel(0, 1, 0, 0, 0), new Pixel(0, 2, 0, 0, 0)},
            {new Pixel(1, 0, 0, 0, 0), new Pixel(1, 1, 0, 0, 0), new Pixel(1, 2, 0, 0, 0)},
            {new Pixel(2, 0, 0, 0, 0), new Pixel(2, 1, 0, 0, 0), new Pixel(2, 2, 0, 0, 0)}},
        user.apply(img).getImage());
    assertEquals("ClampingFilter", sharp.apply(img).getFilename());
  }
}
