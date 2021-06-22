import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import model.layer.ILayer;
import model.layer.Layer;
import model.managers.IOLayerManager;
import model.managers.InputJPEGFilenameManager;
import model.managers.InputPNGFilenameManager;
import model.managers.InputTextFilenameManager;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents the test class for {@code IOLayerManager} to ensure that the text file
 * is imported correctly and interpreted as the correct list of layers.
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

  @Test
  public void testApply() {
    List<ILayer> layers = this.manager.apply();

    ILayer layer1 = new Layer("res/correct/flower.jpeg");
    layer1.setImage(new InputJPEGFilenameManager("res/correct/flower.jpeg").apply());
    ILayer layer2 = new Layer("res/correct/flower.png");
    layer2.setImage(new InputPNGFilenameManager("res/correct/flower.png").apply());
    layer2.setVisibility(false);

    assertEquals(layers.size(), 3);
    assertTrue(layers.contains(layer1));
    assertTrue(layers.contains(layer2));
    assertEquals(layers.get(0), layer1);
    assertEquals(layers.get(1), layer2);
  }

}
