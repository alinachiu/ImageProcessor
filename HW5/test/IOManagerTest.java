import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.File;
import model.creator.CheckboardImageCreator;
import model.creator.IImageCreator;
import model.image.IImage;
import model.image.PPMImage;
import model.managers.IOManager;
import model.managers.InputFileManager;
import model.managers.InputFilenameManager;
import org.junit.Test;

/**
 * This class tests for methods in the IOManager (InputFileManager and InputFilenameManager
 * classes).
 */
public class IOManagerTest {

  //Tests for InputFileManager:
  @Test(expected = IllegalArgumentException.class)
  public void testNullFile() {
    new InputFileManager(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNonexistentFile() {
    IOManager input = new InputFileManager(new File("InvalidFile"));
    input.apply();
  }

  @Test
  public void testValidFile() {
    IImageCreator board = new CheckboardImageCreator(4, 4);
    IImage checkerboard = board.createImage();
    IOManager input = new InputFileManager(new File("res/Checkerboard.ppm"));
    assertArrayEquals(checkerboard.getImage(), input.apply().getImage());
    assertEquals("res/Checkerboard.ppm", input.apply().getFilename());
  }

  //Tests for InputFilenameManager:

  @Test(expected = IllegalArgumentException.class)
  public void testNullFilename() {
    new InputFilenameManager(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNonexistentFilename() {
    IOManager input = new InputFilenameManager("Invalid");
    input.apply();
  }

  @Test
  public void testValidFilename() {
    IImageCreator board = new CheckboardImageCreator(4, 4);
    IImage checkerboard = new PPMImage(board.createImage().getImage(), "res/Checkerboard.ppm");
    IOManager input = new InputFilenameManager("res/Checkerboard.ppm");
    assertArrayEquals(checkerboard.getImage(), input.apply().getImage());
    assertEquals(checkerboard.getFilename(), input.apply().getFilename());
  }


}
