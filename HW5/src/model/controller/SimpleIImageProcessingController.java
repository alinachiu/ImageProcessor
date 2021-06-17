package model.controller;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import model.IPhotoOperations;
import model.color.Grayscale;
import model.color.Sepia;
import model.filter.Blur;
import model.filter.Sharpening;
import model.layer.ILayerModel;
import model.view.SimpleIImageProcessingView;

// TODO do the Javadocs
public class SimpleIImageProcessingController implements IImageProcessingController {

  private final ILayerModel model;
  private final SimpleIImageProcessingView view;
  private final Readable rd;

  /**
   * Constructs a {@code SimpleIImageProcessingController} object with the IModel interface for
   * backwards compatibility.
   *
   * @param model the IImage model that the user will use to process images with
   * @param rd    represents the user input
   * @param ap    represents the user output
   * @throws IllegalArgumentException if any of its arguments are null
   */
  /* TODO make backwards compatible
  public SimpleIImageProcessingController(IModel model, Readable rd, Appendable ap)
      throws IllegalArgumentException {
    if (model == null || rd == null || ap == null) {
      throw new IllegalArgumentException("Arguments cannot be null.");
    }

    this.model = model;
    this.rd = rd;
    this.view = new SimpleIImageProcessingView(this.model, ap);
  }*/

  /**
   * Constructs a {@code SimpleIImageProcessingController} object with the new updated model which
   * supports multi-layered image editing.
   *
   * @param model the IImage model that the user will use to process images with
   * @param rd    represents the user input
   * @param ap    represents the user output
   * @throws IllegalArgumentException if any of its arguments are null
   */
  public SimpleIImageProcessingController(ILayerModel model, Readable rd, Appendable ap)
      throws IllegalArgumentException {
    if (model == null || rd == null || ap == null) {
      throw new IllegalArgumentException("Arguments cannot be null.");
    }

    this.model = model;
    this.rd = rd;
    this.view = new SimpleIImageProcessingView(this.model, ap);
  }

  @Override
  public void processImage() throws IllegalStateException, IllegalArgumentException {
    Scanner in = new Scanner(this.rd);
    IPhotoOperations command = null;
    boolean hasQuit = false;

    while (in.hasNext()) {
      String cmd = in.nextLine().toLowerCase();
      String[] tokens = cmd.split(" ");

      try {
        switch (tokens[0]) {
          case "q":
          case "quit":
            hasQuit = true;
            return;
          case "create":
            this.model.createImageLayer(tokens[1]);
            break;
          case "current":
            this.model.setCurrent(tokens[1]);
            break;
          case "load":
            this.model.loadLayer(tokens[1]);
            break;
          case "save":
            this.model.saveLayer(tokens[1]);
            break;
          case "invisible":
            this.model.makeLayerInvisible(tokens[1]);
            break;
          case "blur":
            command = new Blur();
            break;
          case "sharpen":
            command = new Sharpening();
            break;
          case "sepia":
            command = new Sepia();
            break;
          case "grayscale":
            command = new Grayscale();
            break;
          default:
            this.attemptAppend("Invalid command. Try again!\n");
        }
        if (command != null) {
          this.model.applyOperation(command);
          command = null;
        }
      } catch (InputMismatchException e) {
        this.attemptAppend("Invalid input\n");
      }
    }

    if (!hasQuit) {
      throw new IllegalStateException("Readable has failed!");
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
