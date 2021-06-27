package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import model.LayerModelState;
import model.layer.ILayerModel;
import utils.ControllerUtils;
import view.SimpleIImageProcessingView;

/**
 * The controller for the LayerModel class which allows user interaction/commands or the reading of
 * a text file to produce different outcomes to layers and the content inside of them (such as their
 * image).
 */
public class SimpleIImageProcessingController implements IImageProcessingController {

  private final ILayerModel model;
  private final SimpleIImageProcessingView view;
  private final Readable rd;

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
    this.view = new SimpleIImageProcessingView(new LayerModelState(this.model), ap);
  }

  /**
   * Constructs a {@code SimpleIImageProcessingController} object with the new updated model which
   * supports multi-layered image editing as well as a file with a given script.
   *
   * @param model the IImage model that the user will use to process images with
   * @param file  represents the file to be read from
   * @param ap    represents the user output
   * @throws IllegalArgumentException if any of its arguments are null or if the file is not found
   */
  public SimpleIImageProcessingController(ILayerModel model, File file, Appendable ap)
      throws IllegalArgumentException {
    if (model == null || file == null || ap == null) {
      throw new IllegalArgumentException("Arguments cannot be null.");
    }

    this.model = model;
    this.view = new SimpleIImageProcessingView(new LayerModelState(this.model), ap);
    try {
      this.rd = new FileReader(file);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found!");
    }
  }

  @Override
  public void processImage() throws IllegalStateException, IllegalArgumentException {
    Scanner in = new Scanner(this.rd);
    Map<String, Function<Scanner, IPhotoCommands>> knownCommands = ControllerUtils
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

    this.attemptAppend(createLayerState());
  }

  /**
   * Creates the layer state based on the model state field.
   *
   * @return the string associated with the current layer state.
   */
  private String createLayerState() {
    StringBuilder newString = new StringBuilder();

    for (int i = 0; i < this.model.getLayers().size(); i++) {
      newString.append("Layer #").append(i + 1).append(", ")
          .append(this.model.getLayers().get(i).toString())
          .append("\n");
    }

    try {
      return newString + "Number of valid layers created: " + this.model.getLayers().size()
          + "\nCurrent Layer: " + this.model.getCurrentLayer().toString() + "\n";
    } catch (IllegalArgumentException e) {
      return newString + "Number of valid layers created: " + this.model.getLayers().size()
          + "\nCurrent not yet set.\n";
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
      throw new IllegalStateException("Writing to the Appendable object used by it fails");
    }
  }
}