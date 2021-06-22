package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This is an implementation of the IImageProcessingView interface that uses Java Swing to draw the
 * results of the turtle. It shows any error messages using a pop-up dialog box, and shows the
 * topmost, visible layer of the image. The image can be bigger than * the area allocated to it. In
 * this case, the user should be able to scroll the image.
 */
public class GraphicalImageProcessingView extends JFrame implements IImageProcessingView {

  private JButton addLayerButton, removeLayerButton, quitButton;
  private JPanel buttonPanel;
  private ImageProcessingPanel imageProcessingPanel;
  private JTextField input;
  private JLabel display;

  /**
   * Constructs an {@code GraphicalImageProcessingView} object which represents the graphical view
   * of an image processing model.
   */
  public GraphicalImageProcessingView() {
    this.setTitle("Image Processor");
    this.setSize(500, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //use a borderlayout with drawing panel in center and button panel in south
    this.setLayout(new BorderLayout());
    imageProcessingPanel = new ImageProcessingPanel();
    imageProcessingPanel.setPreferredSize(new Dimension(500, 500));
    this.add(imageProcessingPanel, BorderLayout.CENTER);

    //button panel
    buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    this.add(buttonPanel, BorderLayout.SOUTH);

    //input textfield TODO figure out if this is necessary
    input = new JTextField(15);
    buttonPanel.add(input);

    //buttons
    addLayerButton = new JButton("Add Layer");
    buttonPanel.add(addLayerButton);

    removeLayerButton = new JButton("Remove Layer");
    buttonPanel.add(removeLayerButton);

    // TODO add extra buttons elsewhere

    //quit button
    quitButton = new JButton("Quit");
    quitButton.addActionListener((ActionEvent e) -> {
      System.exit(0);
    });
    buttonPanel.add(quitButton);

    this.pack();
  }

  @Override
  public void renderMessage(String message) throws IOException {
    // TODO returns pop up of message
  }

  @Override
  public void renderLayerState() throws IOException {
    // TODO display the current layer state of the model graphically (work with the
    //  ImageProcessingPanel

    /*
    BufferedImage myPicture = ImageIO.read(new File("path-to-file"));
    JLabel picLabel = new JLabel(new ImageIcon(myPicture));
    add(picLabel);
     */

  }
}
