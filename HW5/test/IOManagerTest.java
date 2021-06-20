import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import model.creator.CheckboardImageCreator;
import model.creator.IImageCreator;
import model.image.IImage;
import model.image.IPixel;
import model.image.Image;
import model.image.Pixel;
import model.managers.IOManager;
import model.managers.InputFileManager;
import model.managers.InputFilenameManager;
import model.managers.InputJPEGFileManager;
import model.managers.InputJPEGFilenameManager;
import model.managers.InputPNGFileManager;
import model.managers.InputPNGFilenameManager;
import org.junit.Test;
import utils.AdditionalImageUtils;

/**
 * This class tests for methods in the IOManager (InputFileManager and InputFilenameManager
 * classes).
 */
public class IOManagerTest {

  // Tests for InputFileManager:
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

  // Tests for InputFilenameManager:

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
    IImage checkerboard = new Image(board.createImage().getImage(), "res/Checkerboard.ppm");
    IOManager input = new InputFilenameManager("res/Checkerboard.ppm");
    assertArrayEquals(checkerboard.getImage(), input.apply().getImage());
    assertEquals(checkerboard.getFilename(), input.apply().getFilename());
  }

  // Tests for InputJPEGFileManager
  @Test(expected = IllegalArgumentException.class)
  public void testInputJPEGFileManagerNullFileConstructorException() {
    IOManager manager = new InputJPEGFileManager(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInputJPEGFileManagerNonExistentFile() {
    IOManager manager = new InputJPEGFileManager(new File("fake/file"));
    IImage image = manager.apply();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInputJPEGFileManagerFileWithNoExtension() {
    IOManager manager = new InputJPEGFileManager(new File("res/a/flower"));
    IImage image = manager.apply();
  }

  @Test
  public void testInputJPEGFileManagerRealFile() throws IOException {
    IPixel[][] imageGrid = AdditionalImageUtils.readPNGJPEG(new File("res/a/flower.jpeg"));
    IImage imageBasedOnGrid = new Image(imageGrid, "res/a/flower.jpeg");
    IOManager manager = new InputJPEGFileManager(new File("res/a/flower.jpeg"));
    IImage image = manager.apply();

    assertEquals(image.getFilename(), "res/a/flower.jpeg");
    assertEquals(image.getImage().length, 122);
    assertArrayEquals(image.getImage(), imageBasedOnGrid.getImage());
  }

  // Tests for InputJPEGFilenameManager
  @Test(expected = IllegalArgumentException.class)
  public void testInputJPEGFilenameManagerNullFileConstructorException() {
    IOManager manager = new InputJPEGFilenameManager(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInputJPEGFilenameManagerNonExistentFile() {
    IOManager manager = new InputJPEGFilenameManager("fake/file");
    IImage image = manager.apply();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInputJPEGFilenameManagerFileWithNoExtension() {
    IOManager manager = new InputJPEGFilenameManager("res/a/flower");
    IImage image = manager.apply();
  }

  @Test
  public void testInputJPEGFilenameManagerRealFile() throws IOException {
    IPixel[][] imageGrid = AdditionalImageUtils.readPNGJPEG(new File("res/a/flower.jpeg"));
    IImage imageBasedOnGrid = new Image(imageGrid, "res/a/flower.jpeg");
    IOManager manager = new InputJPEGFilenameManager("res/a/flower.jpeg");
    IImage image = manager.apply();

    assertEquals(image.getFilename(), "res/a/flower.jpeg");
    assertEquals(image.getImage().length, 122);
    assertArrayEquals(image.getImage(), imageBasedOnGrid.getImage());
  }

  // Tests for InputPNGFileManager
  @Test(expected = IllegalArgumentException.class)
  public void testInputPNGFileManagerNullFileConstructorException() {
    IOManager manager = new InputPNGFileManager(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInputPNGFileManagerNonExistentFile() {
    IOManager manager = new InputPNGFileManager(new File("fake/file.png"));
    IImage image = manager.apply();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInputPNGFileManagerFileWithNoExtension() {
    IOManager manager = new InputPNGFileManager(new File("res/a/flower"));
    IImage image = manager.apply();
  }

  @Test
  public void testInputPNGFileManagerRealFile() throws IOException {
    IPixel[][] imageGrid = AdditionalImageUtils.readPNGJPEG(new File("res/a/flower.png"));
    IImage imageBasedOnGrid = new Image(imageGrid, "res/a/flower.png");
    IOManager manager = new InputPNGFileManager(new File("res/a/flower.png"));
    IImage image = manager.apply();

    assertEquals(image.getFilename(), "res/a/flower.png");
    assertEquals(image.getImage().length, 122);
    assertArrayEquals(image.getImage(), imageBasedOnGrid.getImage());
  }

  // Tests for InputPNGFilenameManager
  @Test(expected = IllegalArgumentException.class)
  public void testInputPNGFilenameManagerNullFileConstructorException() {
    IOManager manager = new InputPNGFilenameManager(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInputPNGFilenameManagerNonExistentFile() {
    IOManager manager = new InputPNGFilenameManager("fake/file.png");
    IImage image = manager.apply();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInputPNGFilenameManagerFileWithNoExtension() {
    IOManager manager = new InputPNGFilenameManager("res/a/flower");
    IImage image = manager.apply();
  }

  @Test
  public void testInputPNGFilenameManagerRealFile() throws IOException {
    IPixel[][] imageGrid = AdditionalImageUtils.readPNGJPEG(new File("res/a/flower.png"));
    IImage imageBasedOnGrid = new Image(imageGrid, "res/a/flower.png");
    IOManager manager = new InputPNGFilenameManager("res/a/flower.png");
    IImage image = manager.apply();

    assertEquals(image.getFilename(), "res/a/flower.png");
    assertEquals(image.getImage().length, 122);
    assertArrayEquals(image.getImage(), imageBasedOnGrid.getImage());
  }

}
