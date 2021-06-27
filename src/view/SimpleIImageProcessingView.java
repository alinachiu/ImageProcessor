package view;

import java.io.IOException;
import model.ILayerModelState;

/**
 * Represents the text view for an image processing model which displays the interface to the user
 * based on a given {@code LayerModel} and appends it to an {@link Appendable} if one is given. It
 * communicates to the user what the model is doing as they enter in commands.
 */
public class SimpleIImageProcessingView implements IImageProcessingView {

  private final ILayerModelState model;

  private final Appendable out;

  /**
   * Constructs a view of the script that will contain commands from the user to create
   * multi-layered images.
   *
   * @param model The model for image processing
   * @throws IllegalArgumentException if the given model is null
   */
  public SimpleIImageProcessingView(ILayerModelState model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
    out = System.out;
  }

  /**
   * Constructs a view of the script that will contain commands from the user to create
   * multi-layered images and creates an appendable that communicates with the controller/provided
   * data destination to append messages or to append the textual view of the script.
   *
   * @param model The model for image processing
   * @param ap    The appendable that outputs/communicates to the controller of the game.
   * @throws IllegalArgumentException if the given model or appendable is null
   */
  public SimpleIImageProcessingView(ILayerModelState model, Appendable ap) {
    if (model == null || ap == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
    this.out = ap;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    out.append(message);
  }
}
