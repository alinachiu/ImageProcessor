package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.layer.ILayer;
import model.layer.ILayerModel;

// TODO mouse right click for invisible/visible, controller???, pop up error messages

public class MyWindow extends JFrame implements IGUIView, ActionListener, MouseListener {

  private final JButton createLayerButton;
  private final JButton removeLayerButton;
  private final JButton quitButton;
  private final JButton loadButton;
  private final JButton loadAllButton;
  private final JButton saveButton;
  private final JButton saveAllButton;
  private final JButton colorTransformButton;
  private final JButton createCheckerboardImageButton;
  private final JButton filterButton;
  private final JButton mosaicButton;
  private final JButton downscaleButton;
  private final List<IViewListener> listeners;
  private final List<JLabel> images;
  private final ILayerModel model;

  //Panel for image/layer operations
  private JPanel operPanel;


  public MyWindow(ILayerModel model) {
    super();
    this.model = model;
    this.images = new ArrayList<>();

    setSize(new Dimension(600, 600));
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    listeners = new ArrayList<>();

//     setLayout(new FlowLayout());

    operPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    operPanel.setLayout(new BoxLayout(operPanel, BoxLayout.PAGE_AXIS));

    //dialog boxes
    JPanel dialogBoxesPanel = new JPanel();
    dialogBoxesPanel.setBorder(BorderFactory.createTitledBorder("Dialog boxes"));
    dialogBoxesPanel.setLayout(new BoxLayout(dialogBoxesPanel, BoxLayout.PAGE_AXIS));
    operPanel.add(dialogBoxesPanel);

    createLayerButton = new JButton("Create layer");
    removeLayerButton = new JButton("Remove layer");
    loadButton = new JButton("Load single");
    loadAllButton = new JButton("Load all with script");
    saveButton = new JButton("Save");
    saveAllButton = new JButton("Save all");
    colorTransformButton = new JButton("Color transformation");
    createCheckerboardImageButton = new JButton("Create checkerboard");
    filterButton = new JButton("Filter");
    mosaicButton = new JButton("Mosaic");
    downscaleButton = new JButton("Downscale");
    quitButton = new JButton("quit");

//     inputTextField = new JTextField(30);
//     outputTextLabel = new JLabel("Output text");
//     imageLabel = new JLabel(new ImageIcon("src/Jellyfish.jpg"));
//     imageLabel2 = new JLabel(new ImageIcon("src/Penguins.jpg"));

    createLayerButton.setActionCommand("create");
    removeLayerButton.setActionCommand("remove");
    loadButton.setActionCommand("load");
    loadAllButton.setActionCommand("loadall");
    saveButton.setActionCommand("save");
    saveAllButton.setActionCommand("saveall");
//    invisibleButton.setActionCommand("invisible"); TODO make this right-clickable
//    visibleButton.setActionCommand("visible");
    colorTransformButton.setActionCommand("colortransform");
    createCheckerboardImageButton.setActionCommand("createimage");
    filterButton.setActionCommand("filter");
    mosaicButton.setActionCommand("mosaic");
    downscaleButton.setActionCommand("downscale");
    quitButton.setActionCommand("quit");

    createLayerButton.addActionListener(this);
    removeLayerButton.addActionListener(this);
    loadButton.addActionListener(this);
    loadAllButton.addActionListener(this);
    saveButton.addActionListener(this);
    saveAllButton.addActionListener(this);
//    invisibleButton.addActionListener(this);
//    visibleButton.addActionListener(this);
    colorTransformButton.addActionListener(this);
    createCheckerboardImageButton.addActionListener(this);
    filterButton.addActionListener(this);
    mosaicButton.addActionListener(this);
    downscaleButton.addActionListener(this);

    this.addMouseListener(this); //the mouse clicks are on the frame too.

//    imageLabel.addMouseListener(this);
//    imageLabel2.addMouseListener(this);

    add(createLayerButton);
    add(removeLayerButton);
    add(loadButton);
    add(loadAllButton);
    add(saveButton);
    add(saveAllButton);
    add(colorTransformButton);
    add(createCheckerboardImageButton);
    add(filterButton);
    add(mosaicButton);
    add(downscaleButton);
//    add(imageLabel);
//    add(imageLabel2);

//     //Load image for layer
//     JPanel fileopenPanel = new JPanel();
//     fileopenPanel.setLayout(new FlowLayout());
//     dialogBoxesPanel.add(fileopenPanel);
//     JButton fileOpenButton = new JButton("Load Image");
//     loadButton.setActionCommand("Load Image");
//     loadButton.addActionListener(this);
//     fileopenPanel.add(fileOpenButton);
//     fileOpenDisplay = new JLabel("File path will appear here");
//     fileopenPanel.add(fileOpenDisplay);
//
//     //Save the layer
//     JPanel filesavePanel = new JPanel();
//     filesavePanel.setLayout(new FlowLayout());
//     dialogBoxesPanel.add(filesavePanel);
//     JButton fileSaveButton = new JButton("Save Layer");
//     saveButton.setActionCommand("Save Layer");
//     saveButton.addActionListener(this);
//     filesavePanel.add(fileSaveButton);
//     fileSaveDisplay = new JLabel("File path will appear here");
//     filesavePanel.add(fileSaveDisplay);

    setVisible(true);

    setFocusable(true);
    requestFocus();

    pack();
  }

//  @Override
//  public void setData(String data) {
//    Objects.requireNonNull(data);
//
//    inputTextField.setText(data);
//  }
//
//  @Override
//  public String getData() {
//    return inputTextField.getText();
//  }
//
//  @Override
//  public void addViewEventListener(IViewListener listener) {
//    this.listeners.add(Objects.requireNonNull(listener));
//  }
//
//  @Override
//  public void requestViewFocus() {
//    this.setFocusable(true);
//    this.requestFocus();
//  }

  protected void makeCurrentEvent() {
    for (IViewListener listener : listeners) {
      listener.makeCurrentEvent();
    }
  }

  protected void emitSaveEvent() {
    for (IViewListener listener : listeners) {
      listener.handleSaveEvent();
    }
  }

  protected void emitLoadEvent() {
    for (IViewListener listener : listeners) {
      listener.handleLoadEvent();
    }
  }

  @Override //JavaFX, FunWorld, WorldImpl
  public void actionPerformed(ActionEvent e) {
    String actionCommand = e.getActionCommand();
    switch (actionCommand) {
      case "toUpperCase":
        emitToUpperCaseEvent();
        break;
      case "SaveButton":
        emitSaveEvent();
        break;
      case "LoadButton":
        emitLoadEvent();
        break;
    }
    // requestViewFocus();//After each click the button will
    //transfer the focus back to the frame.
  }


  @Override
  public void renderMessage(String message) throws IOException {

  }

  @Override
  public void renderLayerState() throws IOException {
    List<ILayer> layers = new ArrayList<>();

    for (ILayer layer : layers) {
      if (!images.contains(layer)) {
        images.add(new JLabel(new ImageIcon(layer.getName())));
      }
    }

    for (JLabel image : this.images) {
      image.addMouseListener(this);
      add(image);
    }
    this.repaint();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (images.contains(e.getSource())) {
      makeCurrentEvent();
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }

  @Override
  public void addViewEventListener(IViewListener listener) {

  }

  //   @Override
  //   public void keyTyped(KeyEvent e) {
  //     switch (e.getKeyChar()) {
  //       case 'u':
  //         emitToUpperCaseEvent();
  //         break;
  //       case 'l':
  //         emitLoadEvent();
  //         break;
  //       case 's':
  //         emitSaveEvent();
  //         break;
  //     }
  //   }
  //
  //   @Override
  //   public void keyPressed(KeyEvent e) {
  //
  //   }
  //
  //   @Override
  //   public void keyReleased(KeyEvent e) {
  //
  //   }

}