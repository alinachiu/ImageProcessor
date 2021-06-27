import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import model.creator.CheckboardImageCreator;
import model.creator.IImageCreator;
import model.image.IImage;
import model.image.IPixel;
import model.image.Image;
import model.managers.IOManager;
import model.managers.InputFileManager;
import model.managers.InputFilenameManager;
import model.managers.InputJPEGPNGFileManager;
import model.managers.InputJPEGPNGFilenameManager;
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
    IOManager input = new InputFileManager(new File("res/Checkerboard1.ppm"));
    assertEquals("res/Checkerboard1.ppm", input.apply().getFilename());
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
    IImage checkerboard = new Image(board.createImage().getImage(), "res/Checkerboard1.ppm");
    IOManager input = new InputFilenameManager("res/Checkerboard1.ppm");
    assertEquals(checkerboard.getFilename(), input.apply().getFilename());
  }

  // Tests for InputJPEGPNGFileManager
  @Test(expected = IllegalArgumentException.class)
  public void testInputJPEGFileManagerNullFileConstructorException() {
    IOManager manager = new InputJPEGPNGFileManager(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInputJPEGFileManagerNonExistentFile() {
    IOManager manager = new InputJPEGPNGFileManager(new File("fake/file"));
    IImage image = manager.apply();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInputJPEGFileManagerFileWithNoExtension() {
    IOManager manager = new InputJPEGPNGFileManager(new File("res/correct/flower"));
    IImage image = manager.apply();
  }

  @Test
  public void testInputJPEGFileManagerRealFile() throws IOException {
    IPixel[][] imageGrid = AdditionalImageUtils.readPNGJPEG(new File("res/correct/flower.jpeg"));
    IImage imageBasedOnGrid = new Image(imageGrid, "res/correct/flower.jpeg");
    IOManager manager = new InputJPEGPNGFileManager(new File("res/correct/flower.jpeg"));
    IImage image = manager.apply();

    assertEquals(image.getImage().length, 122);
    assertArrayEquals(image.getImage(), imageBasedOnGrid.getImage());
  }

  // Tests for InputJPEGPNGFilenameManager
  @Test(expected = IllegalArgumentException.class)
  public void testInputJPEGFilenameManagerNullFileConstructorException() {
    IOManager manager = new InputJPEGPNGFilenameManager(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInputJPEGFilenameManagerNonExistentFile() {
    IOManager manager = new InputJPEGPNGFilenameManager("fake/file");
    IImage image = manager.apply();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInputJPEGFilenameManagerFileWithNoExtension() {
    IOManager manager = new InputJPEGPNGFilenameManager("res/correct/flower");
    IImage image = manager.apply();
  }

  @Test
  public void testInputJPEGFilenameManagerRealFile() throws IOException {
    IPixel[][] imageGrid = AdditionalImageUtils.readPNGJPEG(new File("res/correct/flower.jpeg"));
    IImage imageBasedOnGrid = new Image(imageGrid, "res/correct/flower.jpeg");
    IOManager manager = new InputJPEGPNGFilenameManager("res/correct/flower.jpeg");
    IImage image = manager.apply();

    assertEquals(image.getImage().length, 122);
    assertArrayEquals(image.getImage(), imageBasedOnGrid.getImage());
  }

  // Tests for InputPNGFileManager
  @Test(expected = IllegalArgumentException.class)
  public void testInputPNGFileManagerNullFileConstructorException() {
    IOManager manager = new InputJPEGPNGFileManager(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInputPNGFileManagerNonExistentFile() {
    IOManager manager = new InputJPEGPNGFileManager(new File("fake/file.png"));
    IImage image = manager.apply();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInputPNGFileManagerFileWithNoExtension() {
    IOManager manager = new InputJPEGPNGFileManager(new File("res/correct/flower"));
    IImage image = manager.apply();
  }

  @Test
  public void testInputPNGFileManagerRealFile() throws IOException {
    IPixel[][] imageGrid = AdditionalImageUtils.readPNGJPEG(new File("res/correct/flower.png"));
    IImage imageBasedOnGrid = new Image(imageGrid, "res/correct/flower.png");
    IOManager manager = new InputJPEGPNGFileManager(new File("res/correct/flower.png"));
    IImage image = manager.apply();

    assertEquals(image.getImage().length, 122);
    assertArrayEquals(image.getImage(), imageBasedOnGrid.getImage());
  }

  // Tests for InputPNGFilenameManager
  @Test(expected = IllegalArgumentException.class)
  public void testInputPNGFilenameManagerNullFileConstructorException() {
    IOManager manager = new InputJPEGPNGFilenameManager(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInputPNGFilenameManagerNonExistentFile() {
    IOManager manager = new InputJPEGPNGFilenameManager("fake/file.png");
    IImage image = manager.apply();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInputPNGFilenameManagerFileWithNoExtension() {
    IOManager manager = new InputJPEGPNGFilenameManager("res/correct/flower");
    IImage image = manager.apply();
  }

  @Test
  public void testInputPNGFilenameManagerRealFile() throws IOException {
    IPixel[][] imageGrid = AdditionalImageUtils.readPNGJPEG(new File("res/correct/flower.png"));
    IImage imageBasedOnGrid = new Image(imageGrid, "res/correct/flower.png");
    IOManager manager = new InputJPEGPNGFilenameManager("res/correct/flower.png");
    IImage image = manager.apply();

    assertEquals(image.getImage().length, 122);
    assertArrayEquals(image.getImage(), imageBasedOnGrid.getImage());
  }

}
