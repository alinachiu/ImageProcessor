import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import model.layer.ILayer;
import model.layer.Layer;
import model.managers.IOLayerManager;
import model.managers.InputFilenameManager;
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
    this.manager = new InputTextFilenameManager("res/a/layerInfo.txt");
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

    ILayer layer1 = new Layer("res/a/flower.jpeg");
    layer1.setImage(new InputJPEGFilenameManager("res/a/flower.jpeg").apply());
    ILayer layer2 = new Layer("res/a/flower.png");
    layer2.setImage(new InputPNGFilenameManager("res/a/flower.png").apply());
    ILayer layer3 = new Layer("res/a/Checkerboard.ppm");
    layer3.setImage(new InputFilenameManager("res/a/Checkerboard.ppm").apply());
    layer3.setVisibility(false);

    assertEquals(layers.size(), 3);
    assertTrue(layers.contains(layer1));
    assertTrue(layers.contains(layer2));
    assertTrue(layers.contains(layer3));
    assertEquals(layers.get(0), layer1);
    assertEquals(layers.get(1), layer2);
    assertEquals(layers.get(2), layer3);
  }

}
