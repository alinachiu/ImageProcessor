package view;

import java.io.IOException;

/**
 * Represents the IImageProcessingView interface which displays the model interface of the image
 * processing program to the user.
 */
public interface IImageProcessingView {

  /**
   * Render a specific message to the provided data destination.
   *
   * @param message the message to be transmitted
   * @throws IOException if transmission to the provided data destination fails
   */
  void renderMessage(String message) throws IOException;

  /**
   * Renders the current state of the LIME by showing the number of layers currently created as well
   * as their names, order, and visibility.
   *
   * @throws IOException if transmission of the layer state to the provided data destination fails
   */
  void renderLayerState() throws IOException;
}
