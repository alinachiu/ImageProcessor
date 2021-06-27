
package utils;

import controller.BlurCommand;
import controller.ColorTransformOnImageCommand;
import controller.CreateImageCommand;
import controller.CreateImageDefaultCommand;
import controller.CreateImageLayerCommand;
import controller.DownscalingCommand;
import controller.FilterOnImageCommand;
import controller.GrayscaleCommand;
import controller.IPhotoCommands;
import controller.LoadAllCommand;
import controller.LoadSingleCommand;
import controller.MakeInvisibleCommand;
import controller.MakeVisibleCommand;
import controller.MosaicCommand;
import controller.RemoveImageLayerCommand;
import controller.SaveAllCommand;
import controller.SaveSingleCommand;
import controller.SepiaCommand;
import controller.SetCurrentCommand;
import controller.SharpenCommand;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

/**
 * This class contains utility methods to help get the known controller commands of an
 * IImageProcessingController.
 */
public class AdditionalControllerUtils {

  /**
   * Represents a static method that gets the known commands that can be used in an {@code
   * IImageProcessingController} and returns them as a Map.
   *
   * @return the map of known commands associated with the controller
   */
  public static Map<String, Function<Scanner, IPhotoCommands>> getKnownCommands() {
    Map<String, Function<Scanner, IPhotoCommands>> knownCommands =
        new HashMap<>();

    knownCommands.putIfAbsent("blur", scanner -> new BlurCommand());
    knownCommands.putIfAbsent("sharpen", scanner -> new SharpenCommand());
    knownCommands.putIfAbsent("sepia", scanner -> new SepiaCommand());
    knownCommands.putIfAbsent("grayscale", scanner -> new GrayscaleCommand());
    knownCommands.putIfAbsent("create", scanner -> new CreateImageLayerCommand(scanner.next()));
    knownCommands.putIfAbsent("remove", scanner -> new RemoveImageLayerCommand(scanner.next()));
    knownCommands.putIfAbsent("current", scanner -> new SetCurrentCommand(scanner.next()));
    knownCommands.putIfAbsent("load", scanner -> new LoadSingleCommand(scanner.next()));
    knownCommands.putIfAbsent("loadall", scanner -> new LoadAllCommand(scanner.next()));
    knownCommands.putIfAbsent("save", scanner -> new SaveSingleCommand(scanner.next()));
    knownCommands.putIfAbsent("saveall", scanner -> new SaveAllCommand(scanner.next()));
    knownCommands.putIfAbsent("invisible", scanner -> new MakeInvisibleCommand(scanner.next()));
    knownCommands.putIfAbsent("visible", scanner -> new MakeVisibleCommand(scanner.next()));
    knownCommands.putIfAbsent("colortransform",
        scanner -> new ColorTransformOnImageCommand(scanner.next(), scanner.next()));
    knownCommands.putIfAbsent("createdefaultimage",
        scanner -> new CreateImageDefaultCommand(Integer.parseInt(scanner.next()),
            Integer.parseInt(scanner.next())));
    knownCommands.putIfAbsent("createimage",
        scanner -> new CreateImageCommand(Integer.parseInt(scanner.next()),
            Integer.parseInt(scanner.next()), Integer.parseInt(scanner.next()),
            Integer.parseInt(scanner.next()), Integer.parseInt(scanner.next()),
            Integer.parseInt(scanner.next()), Integer.parseInt(scanner.next()),
            Integer.parseInt(scanner.next())));
    knownCommands
        .putIfAbsent("filter", scanner -> new FilterOnImageCommand(scanner.next(), scanner.next()));
    knownCommands
        .putIfAbsent("mosaic", scanner -> new MosaicCommand(Integer.parseInt(scanner.next())));
    knownCommands
        .putIfAbsent("downscale",
            scanner -> new DownscalingCommand(Integer.parseInt(scanner.next()),
                Integer.parseInt(scanner.next())));

    return knownCommands;
  }
}