import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import controller.IPhotoCommands;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import org.junit.Test;
import utils.ControllerUtils;

/**
 * Represents the test class for {@code ControllerUtils} to ensure that its static method returns a
 * Map with the correct commands
 */
public class ControllerUtilsTest {

  @Test
  public void testMapCorrectCommands() {
    Map<String, Function<Scanner, IPhotoCommands>> knownCommands = ControllerUtils
        .getKnownCommands();

    // check for commands that should be there
    assertTrue(knownCommands.containsKey("blur"));
    assertTrue(knownCommands.containsKey("sharpen"));
    assertTrue(knownCommands.containsKey("sepia"));
    assertTrue(knownCommands.containsKey("grayscale"));
    assertTrue(knownCommands.containsKey("create"));
    assertTrue(knownCommands.containsKey("remove"));
    assertTrue(knownCommands.containsKey("current"));
    assertTrue(knownCommands.containsKey("load"));
    assertTrue(knownCommands.containsKey("loadall"));
    assertTrue(knownCommands.containsKey("save"));
    assertTrue(knownCommands.containsKey("saveall"));
    assertTrue(knownCommands.containsKey("visible"));
    assertTrue(knownCommands.containsKey("invisible"));
    assertTrue(knownCommands.containsKey("colortransform"));
    assertTrue(knownCommands.containsKey("filter"));
    assertTrue(knownCommands.containsKey("createdefaultimage"));
    assertTrue(knownCommands.containsKey("createimage"));

    // check to make sure no other commands are in the map
    assertFalse(knownCommands.containsKey("fakeCommand"));
    assertFalse(knownCommands.containsKey("colorTransform"));
  }

}
