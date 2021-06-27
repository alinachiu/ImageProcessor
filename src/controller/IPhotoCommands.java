package controller;

import model.layer.ILayerModel;

/**
 * Represents an interface that will allow the model to perform a command. It is a way for the
 * controller to tell the model what to do.
 */
public interface IPhotoCommands {

  /**
   * Uses the given model to perform the class' command as a way of communicating to the model.
   *
   * @param m the model of the image-processing multi-layered model
   * @throws IllegalArgumentException if the given model is null or if any parameters produce
   *                                  invalid results (i.e. invalid filename)
   */
  void runCommand(ILayerModel m) throws IllegalArgumentException;

}
