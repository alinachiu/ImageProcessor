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
}
