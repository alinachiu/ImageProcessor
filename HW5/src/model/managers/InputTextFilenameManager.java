package model.managers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.layer.ILayer;
import model.layer.Layer;

/**
 * Represents a class that manages the given input filename and returns the file's associated
 * layer.
 */
public class InputTextFilenameManager implements IOLayerManager {

  private final Readable rd;

  /**
   * Constructs a {@code InputTextFilenameManager} object.
   *
   * @param filename the path of the file
   * @throws IllegalArgumentException if the filename is null or if the associated file is not
   *                                  found
   */
  public InputTextFilenameManager(String filename)
      throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("Filename is null.");
    }
    try {
      this.rd = new FileReader(filename);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File does not exist!");
    }
  }

  @Override
  public List<ILayer> apply() {
    Scanner in = new Scanner(this.rd);
    List<ILayer> layers = new ArrayList<>();

    while (in.hasNext()) {
      String cmd = in.nextLine().toLowerCase();
      String[] tokens = cmd.split("\\s*,\\s*");
      if (tokens.length != 3) {
        throw new IllegalArgumentException("Invalid text file!");
      }

      ILayer layer = new Layer(tokens[1]);
      layer.setImage(determineCorrectManager(tokens[1]).apply());
      layer.setVisibility(Boolean.parseBoolean(tokens[2]));
      layers.add(layer);
    }

    return layers;
  }

  /**
   * Returns the correct {@link IOManager} based on a given filename.
   *
   * @param filename the given filename
   * @return the correct {@link IOManager} associated with the filename extension
   * @throws IllegalArgumentException if no filename extension is found
   */
  private IOManager determineCorrectManager(String filename) throws IllegalArgumentException {
    String[] splitFilename = filename.split("\\.");
    String fileType = splitFilename[1];

    switch (fileType) {
      case "ppm":
        return new InputFilenameManager(filename);
      case "jpeg":
        return new InputJPEGFilenameManager(filename);
      case "png":
        return new InputPNGFilenameManager(filename);
      default:
        throw new IllegalArgumentException("Cannot save the layer with that file type.");
    }
  }
}
