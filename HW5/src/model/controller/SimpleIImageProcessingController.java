package model.controller;

import model.IModel;
import model.view.SimpleIImageProcessingView;

// TODO do the Javadocs
public class SimpleIImageProcessingController implements IImageProcessingController {

  private final IModel model;
  private final SimpleIImageProcessingView view;
  private final Readable rd;

  /**
   * Constructs a {@code SimpleIImageProcessingController} object.
   *
   * @param model the IImage model that the user will use to process images with
   * @param rd    represents the user input
   * @param ap    represents the user output
   * @throws IllegalArgumentException if any of its arguments are null
   */
  public SimpleIImageProcessingController(IModel model, Readable rd, Appendable ap)
      throws IllegalArgumentException {
    if (model == null || rd == null || ap == null) {
      throw new IllegalArgumentException("Arguments cannot be null.");
    }

    this.model = model;
    this.rd = rd;
    this.view = new SimpleIImageProcessingView(this.model, ap);
  }

  @Override
  public void processImage() {
    // TODO use the command design pattern
  }
}
