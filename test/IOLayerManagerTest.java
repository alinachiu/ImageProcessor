import model.managers.IOLayerManager;
import model.managers.InputTextFilenameManager;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents the test class for {@code IOLayerManager} to ensure that the text file is imported
 * correctly and interpreted as the correct list of layers.
 */
public class IOLayerManagerTest {

  IOLayerManager manager;

  @Before
  public void initData() {
    this.manager = new InputTextFilenameManager("res/correct/layerInfo.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionNullFilename() {
    IOLayerManager layerManager = new InputTextFilenameManager(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorExceptionNonExistentFile() {
    IOLayerManager layerManager = new InputTextFilenameManager("fake/path");
  }

}
