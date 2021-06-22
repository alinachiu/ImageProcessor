package controller;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import model.layer.ILayerModel;
import utils.AdditionalControllerUtils;
import view.IImageProcessingView;

/**
 * The controller for the LayerModel class which allows the reading of a text file to produce
 * different outcomes to layers and the content inside of them (such as their image) and processes
 * the batch-script input from the user, as well as the graphical user interface.
 */
public class GraphicalImageProcessingController implements IImageProcessingController {

  private final ILayerModel model;
  private final IImageProcessingView view;

  /**
   * Constructs a {@code SimpleIImageProcessingController} object with the new updated model which
   * supports multi-layered image editing.
   *
   * @param model the IImage model that the user will use to process images with
   * @param rd    represents the user input
   * @param ap    represents the user output
   * @throws IllegalArgumentException if any of its arguments are null
   */
  public GraphicalImageProcessingController(ILayerModel model, IImageProcessingView view)
      throws IllegalArgumentException {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Arguments cannot be null.");
    }

    this.model = model;
    this.view = view;
  }

  @Override
  public void processImage() throws IllegalStateException, IllegalArgumentException {
    Scanner in = new Scanner(this.rd);
    Map<String, Function<Scanner, IPhotoCommands>> knownCommands = AdditionalControllerUtils
        .getKnownCommands();
    Function<Scanner, IPhotoCommands> functionCommand;
    boolean hasQuit = false;

    while (in.hasNext()) {
      String input = in.next();
      if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) {
        hasQuit = true;
        this.attemptAppend("The process has been quit.\n");
        return;
      }
      functionCommand = knownCommands.getOrDefault(input, null);

      if (functionCommand != null) {
        try {
          this.runCommandBasedOnFunction(functionCommand, in);
        } catch (IllegalArgumentException e) {
          this.attemptAppend("Invalid command! Try again! " + e.getMessage() + "\n");
        }
      } else {
        this.attemptAppend("Invalid input!\n");
      }
    }

    // checks if readable has failed
    if (!hasQuit) {
      throw new IllegalStateException("Readable has failed!");
    }
  }

  /**
   * Runs the correct command based on a given function object and a scanner.
   *
   * @param functionCommand the given function command associated with
   * @param in              the scanner associated with the controller
   * @throws IllegalStateException if writing to the Appendable fails
   */
  private void runCommandBasedOnFunction(Function<Scanner, IPhotoCommands> functionCommand,
      Scanner in) throws IllegalStateException {
    IPhotoCommands command = functionCommand.apply(in);
    command.runCommand(this.model);

    try {
      this.view.renderLayerState();
    } catch (IOException e) {
      throw new IllegalStateException("Writing to the Appendable object used by it fails");
    }
  }

  /**
   * Tries to append a given string to this appendable, if possible.
   *
   * @param str the given string to be appended
   * @throws IllegalStateException    if writing to the Appendable throws an IOException
   * @throws IllegalArgumentException if the given string is null.
   */
  private void attemptAppend(String str) throws IllegalStateException {
    if (str == null) {
      throw new IllegalArgumentException("String is null");
    }
    try {
      this.view.renderMessage(str);
    } catch (IOException e) {
      e.printStackTrace();
      throw new IllegalStateException("Writing to the Appendable object used by it fails");
    }
  }
}
