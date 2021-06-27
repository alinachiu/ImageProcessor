package view;

/**
 * The interface representing the interactive view with a graphical user interface. This will allow
 * a user to interactively load, process and save images. The result of this iteration will be a
 * program that a user can interact with, as well as use batch scripting from the previous
 * iteration.
 */
public interface IGUIView {

  /**
   * Provide the view with an action listener for the button that should cause the program to
   * process a command. This is so that when the button is pressed, control goes to the action
   * listener.
   *
   * @param listener the IViewListener object to be added
   * @throws IllegalArgumentException if the listener that the user is trying to add is already in
   *                                  this view
   */
  void addViewEventListener(IViewListener listener) throws IllegalArgumentException;

  /**
   * Renders a given message as a pop-up alert.
   *
   * @param message the message to be rendered
   * @throws IllegalArgumentException if the message is null
   */
  void renderMessage(String message) throws IllegalArgumentException;

}
