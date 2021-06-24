package view;

import java.io.IOException;
import model.ILayerModelState;
import model.IModel;

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

  @Override
  public void renderLayerState() throws IOException {
    out.append(this.createLayerState());
  }

  /**
   * Creates the layer state based on the model state field.
   *
   * @return the string associated with the current layer state.
   */
  private String createLayerState() {
    StringBuilder newString = new StringBuilder();

    for (int i = 0; i < this.model.getNumLayers(); i++) {
      newString.append("Layer #").append(i + 1).append(", ")
          .append(this.model.getLayer(i).toString())
          .append("\n");
    }

    try {
      return newString + "Number of valid layers created: " + this.model.getNumLayers()
          + "\nCurrent Layer: " + this.model.getCurrentLayer().toString() + "\n";
    } catch (IllegalArgumentException e) {
      return newString + "Number of valid layers created: " + this.model.getNumLayers()
          + "\nCurrent not yet set.\n";
    }
  }

}
