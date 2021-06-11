import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import model.creator.CheckboardImageCreator;
import model.creator.IImageCreator;
import model.image.IPixel;
import model.image.Pixel;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests for methods in the IImageCreator interface (using the classes that implement it
 * -- CheckboardImageCreator).
 */
public class IImageCreatorTest {

  IImageCreator singleSquareBoard;
  IImageCreator twoByTwoCheckerboard;
  IImageCreator threeByThreeCheckerboard;
  IImageCreator defaultFourByFourCheckerboard;

  @Before
  public void setUp() {
    singleSquareBoard = new CheckboardImageCreator(1, 1);
    twoByTwoCheckerboard = new CheckboardImageCreator(3, 2, 50, 100, 150, 8, 16, 62);
    threeByThreeCheckerboard = new CheckboardImageCreator(1, 3, 255, 255, 255, 0, 0, 0);
    defaultFourByFourCheckerboard = new CheckboardImageCreator(2, 4);
  }


  @Test(expected = IllegalArgumentException.class)
  public void invalidSizeOfTileForPersonalizedConstructor() {
    new CheckboardImageCreator(0, 1, 3, 4, 5, 6, 35, 54);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidNumTilesForPersonalizedConstructor() {
    new CheckboardImageCreator(1, 0, 3, 4, 5, 6, 35, 54);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidLowRedColor1() {
    new CheckboardImageCreator(1, 1, -1, 4, 5, 6, 35, 54);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidHighRedColor1() {
    new CheckboardImageCreator(1, 1, 256, 4, 5, 6, 35, 54);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidLowRedColor2() {
    new CheckboardImageCreator(1, 1, 1, 4, 5, -1, 35, 54);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidHighRedColor2() {
    new CheckboardImageCreator(1, 1, 1, 4, 5, 256, 35, 54);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidLowGreenColor1() {
    new CheckboardImageCreator(1, 1, 1, -1, 5, 23, 35, 54);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidHighGreenColor1() {
    new CheckboardImageCreator(1, 1, 1, 256, 5, 23, 35, 54);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidLowGreenColor2() {
    new CheckboardImageCreator(1, 1, 1, -1, 5, 23, 35, 54);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidHighGreenColor2() {
    new CheckboardImageCreator(1, 1, 1, 256, 5, 23, 35, 54);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidLowBlueColor1() {
    new CheckboardImageCreator(1, 1, 1, 1, -1, 23, 35, 54);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidHighBlueColor1() {
    new CheckboardImageCreator(1, 1, 1, 5, 453, 23, 35, 54);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidLowBlueColor2() {
    new CheckboardImageCreator(1, 1, 1, 1, 1, 23, 35, -54);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidHighBlueColor2() {
    new CheckboardImageCreator(1, 1, 1, 5, 255, 23, 35, 345);
  }

  @Test(expected = IllegalArgumentException.class)
  public void allInvalidInputsForPersonalizedConstructor() {
    new CheckboardImageCreator(-1, -1, -1, 5132, 255, -324, 335, 345);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidSizeOfTile() {
    new CheckboardImageCreator(0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidNumTiles() {
    new CheckboardImageCreator(1, 0);
  }

  @Test
  public void createSingleSquareBoard() {
    IPixel[][] grid = new IPixel[][]{{new Pixel(0, 0, 255, 0, 0)}};
    assertArrayEquals(grid, singleSquareBoard.createImage().getImage());
    assertEquals("Checkerboard", singleSquareBoard.createImage().getFilename());
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
    assertArrayEquals(grid, twoByTwoCheckerboard.createImage().getImage());
    assertEquals("Checkerboard", twoByTwoCheckerboard.createImage().getFilename());
  }

  @Test
  public void createThreeByThreeBoard() {
    IPixel[][] grid = new IPixel[][]{
        {new Pixel(0, 0, 255, 255, 255), new Pixel(0, 1, 0, 0, 0), new Pixel(0, 2, 255, 255, 255)},
        {new Pixel(1, 0, 0, 0, 0), new Pixel(1, 1, 255, 255, 255), new Pixel(1, 2, 0, 0, 0)},
        {new Pixel(2, 0, 255, 255, 255), new Pixel(2, 1, 0, 0, 0), new Pixel(2, 2, 255, 255, 255)}};
    assertArrayEquals(grid, threeByThreeCheckerboard.createImage().getImage());
    assertEquals("Checkerboard", threeByThreeCheckerboard.createImage().getFilename());
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
    assertArrayEquals(grid, defaultFourByFourCheckerboard.createImage().getImage());
    assertEquals("Checkerboard", defaultFourByFourCheckerboard.createImage().getFilename());
  }
}
