package controller;

import java.io.File;
import java.io.FileNotFoundException;
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

  /**
   * Constructs an {@code GraphicalImageProcessingController} object which has an associated layer
   * model and GUI view.
   *
   * @param model the model associated with this controller
   * @param view  the view associated with this controller
   * @throws IllegalArgumentException if any arguments are null
   */
  public GraphicalImageProcessingController(ILayerModel model, IGUIView view)
      throws IllegalArgumentException {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Arguments cannot be null.");
    }

    this.view = view;
    this.model = model;
  }

  @Override
  public void processImage() {
    this.view.addViewEventListener(this);
  }

  @Override
  public void handleMakeCurrentEvent(String nameOfButton) {
    try {
      new SetCurrentCommand(nameOfButton).runCommand(model);
    } catch (IllegalArgumentException e) {
      this.view.renderMessage("Invalid Command: " + e.getMessage());
    }
  }

  @Override
  public void handleSepiaEvent() {
    try {
      new SepiaCommand().runCommand(model);
    } catch (IllegalArgumentException e) {
      this.view.renderMessage("Invalid Command: " + e.getMessage());
    }
  }

  @Override
  public void handleGrayscaleEvent() {
    try {
      new GrayscaleCommand().runCommand(model);
    } catch (IllegalArgumentException e) {
      this.view.renderMessage("Invalid Command: " + e.getMessage());
    }
  }

  @Override
  public void handleBlurEvent() {
    try {
      new BlurCommand().runCommand(model);
    } catch (IllegalArgumentException e) {
      this.view.renderMessage("Invalid Command: " + e.getMessage());
    }
  }

  @Override
  public void handleSharpenEvent() {
    try {
      new SharpenCommand().runCommand(model);
    } catch (IllegalArgumentException e) {
      this.view.renderMessage("Invalid Command: " + e.getMessage());
    }
  }

  @Override
  public void handleCreateLayerEvent(String layerName) {
    try {
      new CreateImageLayerCommand(layerName).runCommand(model);
    } catch (IllegalArgumentException e) {
      this.view.renderMessage("Invalid Command: " + e.getMessage());
    }
  }

  @Override
  public void handleRemoveLayerEvent() {
    try {
      new RemoveImageLayerCommand(model.getCurrentLayer().getName()).runCommand(model);
    } catch (IllegalArgumentException e) {
      this.view.renderMessage("Invalid Command: " + e.getMessage());
    }
  }

  @Override
  public void handleLoadLayerEvent(String filename) {
    try {
      new LoadSingleCommand(filename).runCommand(model);
    } catch (IllegalArgumentException e) {
      this.view.renderMessage("Invalid Command: " + e.getMessage());
    }
  }

  @Override
  public void handleLoadAllEvent(String txtFilename) {
    try {
      new LoadAllCommand(txtFilename).runCommand(model);
    } catch (IllegalArgumentException e) {
      this.view.renderMessage("Invalid Command: " + e.getMessage());
    }
  }

  @Override
  public void handleLoadScriptEvent(String txtFilename) {
    if (!determineFiletype(txtFilename).equalsIgnoreCase("txt")) {
      this.view.renderMessage("Incorrect file type!");
      return;
    }

    try {
      Scanner in = new Scanner(new File(txtFilename));
      Map<String, Function<Scanner, IPhotoCommands>> knownCommands = AdditionalControllerUtils
          .getKnownCommands();
      Function<Scanner, IPhotoCommands> functionCommand;
      boolean hasQuit = false;

      while (in.hasNext()) {
        String input = in.next();
        if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) {
          hasQuit = true;
          this.view.renderMessage("The process has been quit.\n");
          return;
        }
        functionCommand = knownCommands.getOrDefault(input, null);

        if (functionCommand != null) {
          try {
            this.runCommandBasedOnFunction(functionCommand, in);
          } catch (IllegalArgumentException e) {
            this.view.renderMessage("Invalid command! Try again! " + e.getMessage() + "\n");
          }
        } else {
          this.view.renderMessage("Invalid input!\n");
        }
      }

      // checks if readable has failed
      if (!hasQuit) {
        throw new IllegalStateException("Readable has failed!");
      }
    } catch (FileNotFoundException e) {
      this.view.renderMessage("File not found!");
    }
  }

  /**
   * Determines and returns the filetype associated with this file.
   *
   * @param filename the filename to be processed
   * @return the filetype associated with a given filename
   */
  private String determineFiletype(String filename) {
    int endFilename = filename.lastIndexOf(".");
    return filename.substring(endFilename + 1);
  }

  @Override
  public void handleSaveTopmostVisibleLayerEvent(String desiredFilename) {
    try {
      new SaveSingleCommand(desiredFilename).runCommand(model);
    } catch (IllegalArgumentException e) {
      this.view.renderMessage("Invalid Command: " + e.getMessage());
    }
  }

  @Override
  public void handleSaveAllEvent(String desiredDir) {
    try {
      new SaveAllCommand(desiredDir).runCommand(model);
    } catch (IllegalArgumentException e) {
      this.view.renderMessage("Invalid Command: " + e.getMessage());
    }
  }

  @Override
  public void handleMakeLayerInvisibleEvent() {
    try {
      new MakeInvisibleCommand(model.getCurrentLayer().getName())
          .runCommand(model);
    } catch (IllegalArgumentException e) {
      this.view.renderMessage("Invalid Command: " + e.getMessage());
    }
  }

  @Override
  public void handleMakeLayerVisibleEvent() {
    try {
      new MakeVisibleCommand(model.getCurrentLayer().getName()).runCommand(model);
    } catch (IllegalArgumentException e) {
      this.view.renderMessage("Invalid Command: " + e.getMessage());
    }
  }

  @Override
  public void handleDownscaleEvent(int width, int height) {
    try {
      new DownscalingCommand(width, height).runCommand(model);
    } catch (IllegalArgumentException e) {
      this.view.renderMessage("Invalid Command: " + e.getMessage());
    }
  }

  @Override
  public void handleMosaicEvent(int numSeeds) {
    try {
      new MosaicCommand(numSeeds).runCommand(model);
    } catch (IllegalArgumentException e) {
      this.view.renderMessage("Invalid Command: " + e.getMessage());
    }
  }

  @Override
  public void handleCheckerboardEvent(int sizeOfTiles, int numTiles, int r1, int g1, int b1,
      int r2, int g2, int b2) {
    try {
      new CreateImageCommand(sizeOfTiles, numTiles, r1, g1, b1, r2, g2, b2).runCommand(model);
    } catch (IllegalArgumentException e) {
      this.view.renderMessage("Invalid Command: " + e.getMessage());
    }
  }

  @Override
  public void handleCheckerboardDefaultColorEvent(int sizeOfTiles, int numTiles) {
    try {
      new CreateImageDefaultCommand(sizeOfTiles, numTiles).runCommand(model);
    } catch (IllegalArgumentException e) {
      this.view.renderMessage("Invalid Command: " + e.getMessage());
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
  }
}
