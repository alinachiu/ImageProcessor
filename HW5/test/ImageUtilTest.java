import static org.junit.Assert.assertArrayEquals;

import model.ImageUtil;
import model.creator.CheckboardImageCreator;
import model.creator.IImageCreator;
import model.image.IPixel;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests for methods in the ImageUtil class.
 */
public class ImageUtilTest {

  ImageUtil u;

  @Before
  public void setUp() {
    u = new ImageUtil();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNull() {
    u.readPPM(null);
  }

  @Test
  public void testNonexistentFile() {
    assertArrayEquals(null, u.readPPM("res/Checker.ppm"));
  }

  @Test
  public void testReadPPM() {
    IImageCreator cb = new CheckboardImageCreator(4, 4);
    IPixel[][] board = cb.createImage().getImage();
    assertArrayEquals(board, u.readPPM("res/Checkerboard.ppm"));
  }
}
