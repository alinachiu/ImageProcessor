package model.managers;

import java.util.List;
import model.layer.ILayer;

/**
 * Represents an IOManager which reads and constructs an ILayer based on the given inputs.
 */
public interface IOLayerManager {

  /**
   * Reads and constructs an ILayer based on the fields of this class.
   *
   * @return the ILayers associated with the fields of the class.
   * @throws IllegalArgumentException if any arguments are null/invalid
   */
  List<ILayer> apply();
}

