package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.ILayerModelState;
import model.layer.ILayerModel;

// TODO mouse right click for invisible/visible, controller???, pop up error messages

/**
 * Represents the window that displays the graphical representation of the image processing
 * program.
 */
public class MyWindow extends JFrame implements IGUIView, ActionListener, MouseListener {

  // Saving/Loading
  private final JMenuItem loadMenuItem;
  private final JMenuItem loadAllMenuItem;
  private final JMenuItem saveMenuItem;
  private final JMenuItem saveAllMenuItem;
  private final JMenuItem loadScriptMenuItem;

  // Layer Functions
  private final JTextField layerNameField;
  private final JButton createLayerButton;
  private final JButton removeLayerButton;
  private final JButton makeInvisibleButton;
  private final JButton makeVisibleButton;

  // Image Operations
  private final JButton blurButton;
  private final JButton sharpenButton;
  private final JButton grayscaleButton;
  private final JButton sepiaButton;
  private final JButton downscaleButton;
  private final JButton mosaicButton;
  private final JTextField numSeedsField;

  // Checkerboard
  private final JTextField numTilesField;
  private final JButton numTilesSubmitButton;
  private final JTextField sizeTileField;
  private final JButton sizeTileSubmitButton;
  private final JButton color1Button;
  private final JButton color2Button;
  private final JButton buildCheckerboardButton;
  private final JLabel colorChooserDisplay1;
  private final JLabel colorChooserDisplay2;

  private final List<JLabel> images;
  private final ILayerModelState model;
  //Map to get the correct field text from a certain key --Keys: "seeds", "numTiles", "sizeTiles", "layerName"
  private final Map<String, JTextField> textFieldData;
  private final List<IViewListener> listeners;


  public MyWindow(ILayerModelState model) {
    super();
    this.images = new ArrayList<>();
    this.model = model;
    this.listeners = new ArrayList<>();
    textFieldData = new HashMap<String, JTextField>();

    setSize(new Dimension(700, 500));
    setLayout(new BorderLayout());

    //Image Panel -- Shows the topmost visible layer
    JPanel topMostImagePanel = new JPanel();
    placeImagePanel(topMostImagePanel);

    //Image Panel -- Shows the current layer
    JPanel currentLayerPanel = new JPanel();
    placeCurrentImgPanel(currentLayerPanel);

    // Split Pane for Topmost visible layer Panel and Current Layer Panel
    JSplitPane shownLayers = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topMostImagePanel,
        currentLayerPanel);
    add(shownLayers, BorderLayout.CENTER);

    //Image Options Panel -- Blur, Sharpen, Gray, Sepia, Mosaic, and Downscale
    JPanel imgOptionsPanel = new JPanel();
    blurButton = new JButton("Blur");
    sharpenButton = new JButton("Sharpen");
    sepiaButton = new JButton("Sepia");
    grayscaleButton = new JButton("Grayscale");
    numSeedsField = new JTextField(3);
    textFieldData.put("seeds", numSeedsField);
    mosaicButton = new JButton("Mosaic (Enter # Seeds)");
    downscaleButton = new JButton("Downscale");
    placeImgOptionsPanel(imgOptionsPanel);

    //Load and Save Panel -- Load, Load All, Save, Save All, and LoadScript
    loadMenuItem = new JMenuItem("Load");
    loadAllMenuItem = new JMenuItem("Load All");
    saveMenuItem = new JMenuItem("Save");
    saveAllMenuItem = new JMenuItem("Save All");
    loadScriptMenuItem = new JMenuItem("Load Script");
    placeSaveAndLoadComponents();

    // Split Pane for Image Operations Panel and Load/Save Panel
    JSplitPane splitOperAndLoadAndSave = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
        imgOptionsPanel,
        shownLayers);
    add(splitOperAndLoadAndSave, BorderLayout.WEST);

    //Layers Panel -- Shows Layers, Visibility Options, Create/Remove Layer
    JPanel layersPanel = new JPanel();
    makeVisibleButton = new JButton("Visible");
    makeInvisibleButton = new JButton("Invisible");
    createLayerButton = new JButton("Create Layer");
    layerNameField = new JTextField(10);
    textFieldData.put("layerName", layerNameField);
    removeLayerButton = new JButton("Remove Layer");
    placeLayersPanel(layersPanel);

    //Checkerboard Panel --Can enter num tiles, size of tile, and two colors (optional)
    JPanel checkerboardPanel = new JPanel();
    numTilesField = new JTextField(3);
    textFieldData.put("numTiles", numTilesField);
    numTilesSubmitButton = new JButton("Submit Side Dimension");
    sizeTileField = new JTextField(3);
    textFieldData.put("sizeTile", sizeTileField);
    sizeTileSubmitButton = new JButton("Submit Tile Size");
    color1Button = new JButton("Choose color 1 (Optional)");
    color2Button = new JButton("Choose color 2 (Optional)");
    buildCheckerboardButton = new JButton("Build board");
    colorChooserDisplay1 = new JLabel("      ");
    colorChooserDisplay2 = new JLabel("      ");
    placeCheckerboardPanel(checkerboardPanel);

    // Split Pane for Layers Panel and Checkerboard Panel
    JSplitPane splitLayerAndChecker = new JSplitPane(JSplitPane.VERTICAL_SPLIT, layersPanel,
        checkerboardPanel);
    add(splitLayerAndChecker, BorderLayout.EAST);

    // menu
    //Build the File Menu.
    JMenuBar menuBar = new JMenuBar();
    // Menu
    JMenu menu = new JMenu("Load/Save");
    menu.setMnemonic(KeyEvent.VK_F);
    // create menu item and add it to the menu
    loadMenuItem.setMnemonic(KeyEvent.VK_F);
    menu.add(loadMenuItem);
    loadAllMenuItem.setMnemonic(KeyEvent.VK_F);
    menu.add(loadAllMenuItem);
    saveMenuItem.setMnemonic(KeyEvent.VK_F);
    menu.add(saveMenuItem);
    saveAllMenuItem.setMnemonic(KeyEvent.VK_F);
    menu.add(saveAllMenuItem);
    loadScriptMenuItem.setMnemonic(KeyEvent.VK_F);
    menu.add(loadScriptMenuItem);
    menuBar.add(menu);

    this.setJMenuBar(menuBar);

    //Display the window.
    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

  }

  //Upper-Left --> Operations that can be done on a current layer image (blur, sepia, etc)
  private void placeImgOptionsPanel(JPanel panel) {
    panel.setBorder(BorderFactory.createTitledBorder("Operations"));
    panel.setBackground(new Color(198, 229, 234));
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    //Blur
    blurButton.setActionCommand("blur");
    blurButton.addActionListener(this);
    blurButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(blurButton);
    //Shar
    sharpenButton.setActionCommand("sharpen");
    sharpenButton.addActionListener(this);
    sharpenButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(sharpenButton);
    //Sepia
    sepiaButton.setActionCommand("sepia");
    sepiaButton.addActionListener(this);
    sepiaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(sepiaButton);
    //Grayscale
    grayscaleButton.setActionCommand("grayscale");
    grayscaleButton.addActionListener(this);
    grayscaleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(grayscaleButton);
    //Downscale
    downscaleButton.setActionCommand("downscale");
    downscaleButton.addActionListener(this);
    downscaleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(downscaleButton);
    //Mosaic
    JPanel mosaicPanel = new JPanel();
    mosaicPanel.setLayout(new FlowLayout());
    mosaicPanel.setBackground(new Color(198, 229, 234));
    mosaicPanel.add(numSeedsField);
    mosaicButton.setActionCommand("mosaic");
    mosaicButton.addActionListener(this);
    mosaicButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    mosaicPanel.add(mosaicButton);
    panel.add(mosaicPanel);
  }

  //Menu Item --> Save and Loading Componenets (SaveAll, LoadAll, load script, etc)
  private void placeSaveAndLoadComponents() {
    loadMenuItem.setActionCommand("load");
    loadMenuItem.addActionListener(this);
    loadAllMenuItem.setActionCommand("loadAll");
    loadAllMenuItem.addActionListener(this);
    saveMenuItem.setActionCommand("save");
    saveMenuItem.addActionListener(this);
    saveAllMenuItem.setActionCommand("saveAll");
    saveAllMenuItem.addActionListener(this);
    loadScriptMenuItem.setActionCommand("loadScript");
    loadScriptMenuItem.addActionListener(this);
  }

  //Top Center --> Places the Topmost visible layer at the top center
  private void placeImagePanel(JPanel imagePanel) {
    JScrollPane scroller; // Makes this scrollable
    imagePanel.setBackground(new Color(142, 204, 213));
    imagePanel.setBorder(BorderFactory.createTitledBorder("Topmost Visible Layer"));
    ImageIcon img = new ImageIcon("Jellyfish.jpg");
    JLabel imageLabel = new JLabel(img);
    scroller = new JScrollPane(imageLabel);
    imageLabel.setIcon(img);
    scroller.setPreferredSize(new Dimension(600, 300)); //Dimension for panel
    imagePanel.add(scroller);
  }

  //Bottom Center --> Places the current layer at the bottom center
  private void placeCurrentImgPanel(JPanel currentPanel) {
    JScrollPane scroller; // Makes this scrollable
    currentPanel.setBackground(new Color(198, 229, 234));
    currentPanel.setBorder(BorderFactory.createTitledBorder("Current Layer"));
    //The image to be placed
    ImageIcon img = new ImageIcon("Jellyfish.jpg");
    JLabel imageLabel = new JLabel(img);
    scroller = new JScrollPane(imageLabel);
    imageLabel.setIcon(img);
    scroller.setPreferredSize(new Dimension(600, 300)); //Dimension for panel
    currentPanel.add(scroller);
  }

  //Top-Right --> Layer functions (create, remove, visibility, and all layer buttons)
  private void placeLayersPanel(JPanel layersPanel) {
    layersPanel.setBorder(BorderFactory.createTitledBorder("Layers"));
    layersPanel.setBackground(new Color(198, 229, 234));
    layersPanel.setLayout(new BoxLayout(layersPanel, BoxLayout.Y_AXIS));
    JPanel createLayerP = new JPanel();
    createLayerP.setLayout(new FlowLayout());
    createLayerP.setBackground(new Color(198, 229, 234));
    //Create layer
    createLayerP.add(layerNameField);
    createLayerButton.setActionCommand("createLayer");
    createLayerButton.addActionListener(this);
    createLayerP.add(createLayerButton);
    layersPanel.add(createLayerP);

    //    for (int i=0; i < model.getLayers().size(); i++) { //pseudo --> change this to get from the controller
    //      JButton layerButton = new JButton(model.getLayers().get(i).getName());
    //      layerButton.setActionCommand(model.getLayers().get(i).getName());
    //      layerButton.addActionListener(this);
    //      layersP.add(layerButton);

    //Panel for the actual layers
    JPanel layersP = new JPanel();
    layersP.setBackground(new Color(142, 204, 213));
    layersP.setLayout(new BoxLayout(layersP, BoxLayout.Y_AXIS));

    for (int i = 0; i < 6; i++) { //psuedo --> change this to get from the controller
      JButton layerButton = new JButton("Layer " + (i + 1));
      layerButton.setActionCommand("Layer " + i);
      layerButton.addActionListener(this);
      layerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      layersP.add(layerButton);
    }
    layersPanel.add(layersP);

    JPanel currLayerFunctionsP = new JPanel();
    currLayerFunctionsP.setBackground(new Color(198, 229, 234));
    currLayerFunctionsP.setLayout(new FlowLayout());

    //Remove Layer
    removeLayerButton.setActionCommand("removeLayer");
    removeLayerButton.addActionListener(this);
    currLayerFunctionsP.add(removeLayerButton);
    //Make invisible
    makeInvisibleButton.setActionCommand("invisible");
    makeInvisibleButton.addActionListener(this);
    currLayerFunctionsP.add(makeInvisibleButton);
    //Make visible
    makeVisibleButton.setActionCommand("visible");
    makeVisibleButton.addActionListener(this);
    currLayerFunctionsP.add(makeVisibleButton);

    layersPanel.add(currLayerFunctionsP);
  }

  //Bottom-Right --> Checkerboard Functions (numTitles, size of tiles, colors, build the board)
  private void placeCheckerboardPanel(JPanel cbPanel) {
    cbPanel.setBorder(BorderFactory.createTitledBorder("Create a Checkerboard"));
    cbPanel.setBackground(new Color(198, 229, 234));
    cbPanel.setLayout(new BoxLayout(cbPanel, BoxLayout.Y_AXIS));

    JPanel cbFunctionsP = new JPanel();
    cbFunctionsP.setLayout(new GridBagLayout());
    cbFunctionsP.setBackground(new Color(142, 204, 213));
    GridBagConstraints c = new GridBagConstraints();
    c.gridwidth = GridBagConstraints.REMAINDER;

    //For Submitting Side Dimension
    JPanel sideDimPanel = new JPanel();
    sideDimPanel.setLayout(new FlowLayout());
    sideDimPanel.setBackground(new Color(142, 204, 213));
    sideDimPanel.add(numTilesField);
    numTilesSubmitButton.setActionCommand("submitNumTiles");
    numTilesSubmitButton.addActionListener(this);
    sideDimPanel.add(numTilesSubmitButton);
    cbFunctionsP.add(sideDimPanel, c);

    //For Submitting Tile Size
    JPanel numTilesPanel = new JPanel();
    numTilesPanel.setLayout(new FlowLayout());
    numTilesPanel.setBackground(new Color(142, 204, 213));
    numTilesPanel.add(sizeTileField);
    sizeTileSubmitButton.setActionCommand("submitSizeTiles");
    sizeTileSubmitButton.addActionListener(this);
    numTilesPanel.add(sizeTileSubmitButton);
    cbFunctionsP.add(numTilesPanel, c);

    //For Choosing Color 1 (Optional)
    JPanel color1Chooser = new JPanel();
    color1Chooser.setLayout(new FlowLayout());
    color1Chooser.setBackground(new Color(142, 204, 213));
    color1Button.setActionCommand("color1");
    color1Button.addActionListener(this);
    color1Chooser.add(color1Button);
    colorChooserDisplay1.setOpaque(true); //so that background color shows up
    colorChooserDisplay1.setBackground(Color.WHITE);
    color1Chooser.add(colorChooserDisplay1);
    cbFunctionsP.add(color1Chooser, c);

    //For Choosing Color 2 (Optional)
    JPanel color2Chooser = new JPanel();
    color2Chooser.setLayout(new FlowLayout());
    color2Chooser.setBackground(new Color(142, 204, 213));
    color2Button.setActionCommand("color2");
    color2Button.addActionListener(this);
    color2Chooser.add(color2Button);
    colorChooserDisplay2.setOpaque(true); //so that background color shows up
    colorChooserDisplay2.setBackground(Color.WHITE);
    color2Chooser.add(colorChooserDisplay2);
    cbFunctionsP.add(color2Chooser, c);

    cbPanel.add(cbFunctionsP);

    //For Build board button
    buildCheckerboardButton.setActionCommand("buildBoard");
    buildCheckerboardButton.addActionListener(this);
    buildCheckerboardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    cbPanel.add(buildCheckerboardButton, c);

  }


  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "load":   // Allows user to load an image of the format jpeg, ppm, or png
        final JFileChooser fchooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPEG/PNG/PPM Images", "jpeg", "ppm", "png");
        fchooser.setFileFilter(filter);
        int retvalue = fchooser.showOpenDialog(this);
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
        }
        this.emitLoadEvent();
        break;
      case "save": // Allows user to save an image (with their given file name)
        final JFileChooser fchooser1 = new JFileChooser(".");
        int retvalue1 = fchooser1.showSaveDialog(this);
        if (retvalue1 == JFileChooser.APPROVE_OPTION) {
          File f = fchooser1.getSelectedFile();
        }
        this.emitSaveTopmostVisibleLayerEvent();
        break;
      case "loadAll": // Allows the user to load a folder/directory
        final JFileChooser fchooser2 = new JFileChooser(".");
        fchooser2.setFileFilter(new FolderFilter());
        fchooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int retvalue2 = fchooser2.showOpenDialog(this);
        if (retvalue2 == JFileChooser.APPROVE_OPTION) {
          File f = fchooser2.getSelectedFile();
        }
        this.emitLoadAllEvent();
        break;
      case "saveAll":  // Allows the user to save into a folder or create a new folder to save into
        final JFileChooser fchooser3 = new JFileChooser(".");
        fchooser3.setFileFilter(new FolderFilter());
        fchooser3.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int retvalue3 = fchooser3.showSaveDialog(this);
        if (retvalue3 == JFileChooser.APPROVE_OPTION) {
          File f = fchooser3.getSelectedFile();
        }
        this.emitSaveAllEvent();
        break;
      case "loadScript":  // Allows the user to load in a script that is a txt file
        final JFileChooser fchooser4 = new JFileChooser(".");
        FileNameExtensionFilter filter2 = new FileNameExtensionFilter(
            "Text File", "txt");
        fchooser4.setFileFilter(filter2);
        int retvalue4 = fchooser4.showOpenDialog(this);
        if (retvalue4 == JFileChooser.APPROVE_OPTION) {
          File f = fchooser4.getSelectedFile();
        }
        this.emitLoadScriptEvent();
        break;
      case "color1":
        Color col1 = JColorChooser
            .showDialog(this, "Choose a color", colorChooserDisplay1.getBackground());
        colorChooserDisplay1.setBackground(col1);
        break;
      case "color2":
        Color col2 = JColorChooser
            .showDialog(this, "Choose a color", colorChooserDisplay2.getBackground());
        colorChooserDisplay2.setBackground(col2);
        break;
      case "blur":
        this.emitBlurEvent();
        break;
      case "sharpen":
        this.emitSharpenEvent();
        break;
      case "grayscale":
        this.emitGrayscaleEvent();
        break;
      case "sepia":
        this.emitSepiaEvent();
        break;
      case "downscale":
        this.emitDownscaleEvent();
        break;
      case "mosaic":
        this.emitMosaicEvent();
        break;
      case "buildBoard":
        this.emitCheckerboardEvent();
        break;
      case "visible":
        this.emitMakeLayerVisibleEvent();
        break;
      case "invisible":
        this.emitMakeLayerInvisibleEvent();
        break;
      case "remove":
        this.emitRemoveLayerEvent();
        break;
      case "create":
        this.emitCreateLayerEvent();
        break;
      case "current":
        this.emitMakeCurrentEvent();
        break;
    }
  }


  private static class FolderFilter extends javax.swing.filechooser.FileFilter {

    @Override
    public boolean accept(File file) {
      return file.isDirectory();
    }

    @Override
    public String getDescription() {
      return "Folder/Directory";
    }
  }

//   @Override
//   public void setData(String data) {
//     Objects.requireNonNull(data);
//
//     inputTextField.setText(data);
//   }

  @Override
  public String getData(String key) {
    if (key == null) {
      throw new IllegalArgumentException("String cannot be null");
    }
    if (textFieldData.containsKey(key)) {
      return textFieldData.get(key).getText();
    }
    throw new IllegalArgumentException("Key doesn't exist");
  }


  @Override
  public void mouseClicked(MouseEvent e) {

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
    this.listeners.add(listener);
  }

  @Override
  public void renderMessage(String message) throws IOException {

  }

  @Override
  public void renderLayerState() throws IOException {

  }

  protected void emitLoadEvent() {
    for (IViewListener listener : listeners) {
      listener.handleLoadEvent();
    }
  }

  protected void emitMakeCurrentEvent() {
    for (IViewListener listener : listeners) {
      listener.handleMakeCurrentEvent();
    }
  }

  protected void emitSepiaEvent() {
    for (IViewListener listener : listeners) {
      listener.handleSepiaEvent();
    }
  }

  protected void emitGrayscaleEvent() {
    for (IViewListener listener : listeners) {
      listener.handleGrayscaleEvent();
    }
  }

  protected void emitBlurEvent() {
    for (IViewListener listener : listeners) {
      listener.handleBlurEvent();
    }
  }

  protected void emitSharpenEvent() {
    for (IViewListener listener : listeners) {
      listener.handleSharpenEvent();
    }
  }

  protected void emitCreateLayerEvent() {
    for (IViewListener listener : listeners) {
      listener.handleCreateLayerEvent();
    }
  }

  protected void emitRemoveLayerEvent() {
    for (IViewListener listener : listeners) {
      listener.handleRemoveLayerEvent();
    }
  }

  protected void emitLoadLayerEvent() {
    for (IViewListener listener : listeners) {
      listener.handleLoadLayerEvent();
    }
  }

  protected void emitLoadScriptEvent() {
    for (IViewListener listener : listeners) {
      listener.handleLoadScriptEvent();
    }
  }

  protected void emitSaveTopmostVisibleLayerEvent() {
    for (IViewListener listener : listeners) {
      listener.handleSaveTopmostVisibleLayerEvent();
    }
  }

  protected void emitSaveAllEvent() {
    for (IViewListener listener : listeners) {
      listener.handleSaveAllEvent();
    }
  }

  protected void emitMakeLayerInvisibleEvent() {
    for (IViewListener listener : listeners) {
      listener.handleMakeLayerInvisibleEvent();
    }
  }

  protected void emitMakeLayerVisibleEvent() {
    for (IViewListener listener : listeners) {
      listener.handleMakeLayerVisibleEvent();
    }
  }

  protected void emitLoadAllEvent() {
    for (IViewListener listener : listeners) {
      listener.handleLoadAllEvent();
    }
  }

  protected void emitDownscaleEvent() {
    for (IViewListener listener : listeners) {
      listener.handleDownscaleEvent();
    }
  }

  protected void emitMosaicEvent() {
    for (IViewListener listener : listeners) {
      listener.handleMosaicEvent();
    }
  }

  protected void emitCheckerboardEvent() {
    for (IViewListener listener : listeners) {
      listener.handleCheckerboardEvent();
    }
  }
}