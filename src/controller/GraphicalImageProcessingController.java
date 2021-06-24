package controller;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import model.layer.ILayerModel;
import utils.AdditionalControllerUtils;
import view.IGUIView;
import view.IViewListener;

/**
 * The controller for the LayerModel class which allows the reading of a text file to produce
 * different outcomes to layers and the content inside of them (such as their image) and processes
 * the batch-script input from the user, as well as the graphical user interface.
 */
public class GraphicalImageProcessingController implements IImageProcessingController,
    IViewListener {

  private final ILayerModel model;
  private final IGUIView view;


  public GraphicalImageProcessingController(ILayerModel model, IGUIView view)
      throws IllegalArgumentException {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Arguments cannot be null.");
    }

    this.view = view;
    this.model = model;
    this.view.addViewEventListener(this);
  }

  @Override
  public void processImage() {
//     actionPerformed();
  }

  public void actionPerformed(ActionEvent event) {
    String actionCommand = event.getActionCommand();
    Scanner in = new Scanner(actionCommand);
    Map<String, Function<Scanner, IPhotoCommands>> knownCommands = AdditionalControllerUtils
        .getKnownCommands();
    Function<Scanner, IPhotoCommands> functionCommand;

    if (actionCommand.equalsIgnoreCase("q") || actionCommand.equalsIgnoreCase("quit")) {
      return;
    }
    functionCommand = knownCommands.getOrDefault(actionCommand, null);

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

  @Override
  public void handleLoadEvent() {
    //new LoadSingleCommand();
  }

  @Override
  public void handleMakeCurrentEvent() {
    //new SetCurrentCommand();
  }

  @Override
  public void handleSepiaEvent() {
    new SepiaCommand();
  }

  @Override
  public void handleGrayscaleEvent() {
    new GrayscaleCommand();
  }

  @Override
  public void handleBlurEvent() {
    new BlurCommand();
  }

  @Override
  public void handleSharpenEvent() {
    new SharpenCommand();
  }

  @Override
  public void handleCreateLayerEvent() {
    new CreateImageLayerCommand(view.getData("layerName"));
  }

  @Override
  public void handleRemoveLayerEvent() {
    new RemoveImageLayerCommand(model.getCurrentLayer().getName());
  }

  @Override
  public void handleLoadLayerEvent() {
    //new LoadSingleCommand();
  }

  @Override
  public void handleLoadAllEvent() {
    //new LoadAllCommand();
  }

  @Override
  public void handleLoadScriptEvent() {

  }

  @Override
  public void handleSaveTopmostVisibleLayerEvent() {
    new SaveSingleCommand();
  }

  @Override
  public void handleSaveAllEvent() {
    //new SaveAllCommand();
  }

  @Override
  public void handleMakeLayerInvisibleEvent() {
    new MakeInvisibleCommand(model.getCurrentLayer().getName());
  }

  @Override
  public void handleMakeLayerVisibleEvent() {
     new MakeVisibleCommand(model.getCurrentLayer().getName());
  }

  @Override
  public void handleDownscaleEvent() {

  }

  @Override
  public void handleMosaicEvent() {

  }

  @Override
  public void handleCheckerboardEvent() {

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
