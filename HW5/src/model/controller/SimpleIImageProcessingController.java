package model.controller;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import model.IModel;
import model.color.Grayscale;
import model.color.Sepia;
import model.filter.Blur;
import model.filter.Sharpening;
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
    Scanner in = new Scanner(this.rd);
    IPhotoCommands command = null;
    while (in.hasNext()) {
      String cmd = in.next().toLowerCase();

      try {
        switch (cmd) {
          case "q":
          case "quit":
            return;
          case "create":

          case "current":

          case "load":

          case "save":

          case "invisible":

          case "bl":
            command = new BlurCommand();
            break;
          case "sharpen":
            command = new SharpenCommand();
            break;
          case "sepia":
            command = new SepiaCommand();
            break;
          case "grayscale":
            command = new GrayscaleCommand();
            break;
        }
        if (command != null) {
          command.go(null, this.model);  // TODO null to be replaced with image
        }
      } catch (InputMismatchException e) {
        this.attemptAppend("Invalid input");
      }

      // TODO use the command design pattern
    }
  }

  /**
   * Tries to append a given string to this appendable, if possible.
   *
   * @param str the given string to be appended
   * @throws IllegalStateException if writing to the Appendable throws an IOException
   */
  private void attemptAppend(String str) throws IllegalStateException {
    try {
      this.view.renderMessage(str);
    } catch (IOException e) {
      e.printStackTrace();
      throw new IllegalStateException("Writing to the Appendable object used by it fails");
    }
  }
}
