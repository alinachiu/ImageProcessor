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
import javax.swing.Box;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.ILayerModelState;
import model.image.IImage;
import model.image.Image;
import model.layer.ILayer;

// TODO pop up error messages

/**
 * Represents the window that displays the graphical representation of the image processing
 * program.
 */
public class MyWindow extends JFrame implements IGUIView, ActionListener {

  //Saving/Loading Buttons
  private final JButton loadButton;
  //   private final JButton loadAllButton;
  private final JButton saveButton;
  private final JButton saveAllButton;
  private final JButton loadScriptButton;

  // Saving/Loading Menu
  private final JMenuItem loadMenuItem;
  //   private final JMenuItem loadAllMenuItem;
  private final JMenuItem saveMenuItem;
  private final JMenuItem saveAllMenuItem;
  private final JMenuItem loadScriptMenuItem;

  // Image Operations Buttons
  private final JButton blurButton;
  private final JButton sharpenButton;
  private final JButton grayscaleButton;
  private final JButton sepiaButton;
  private final JButton downscaleButton;
  private final JButton mosaicButton;
  private final JTextField numSeedsField;

  // Image Operations Menu
  private final JMenuItem blurMenuItem;
  private final JMenuItem sharpenMenuItem;
  private final JMenuItem grayscaleMenuItem;
  private final JMenuItem sepiaMenuItem;
  private final JMenuItem downscaleMenuItem;
  private final JMenuItem mosaicMenuItem;

  // Layer Functions
  private final JTextField layerNameField;
  private final JButton createLayerButton;
  private final JButton removeLayerButton;
  private final JButton makeInvisibleButton;
  private final JButton makeVisibleButton;

  // Layer Menu
  private final JMenuItem createLayerMenuItem;
  private final JMenuItem removeLayerMenuItem;
  private final JMenuItem makeInvisibleMenuItem;
  private final JMenuItem makeVisibleMenuItem;

  // Checkerboard
  private final JTextField numTilesField;
  private final JButton numTilesSubmitButton;
  private final JTextField sizeTileField;
  private final JButton sizeTileSubmitButton;
  private final JButton color1Button;
  private Color col1;
  private Color col2;
  private final JButton color2Button;
  private final JButton buildCheckerboardButton;
  private final JLabel colorChooserDisplay1;
  private final JLabel colorChooserDisplay2;

  // Checkerboard Menu
  private final JMenuItem buildCheckerboardMenuItem;

  //Map to get the correct field text from a certain key --Keys: "seeds", "numTiles", "sizeTiles", "layerName"
  private final Map<String, JTextField> textFieldData;
  private final List<IViewListener> listeners;
  private int numberOfLayers;

  private final ILayerModelState model;

  public MyWindow(ILayerModelState model) {
    super();
    this.model = model;
    this.listeners = new ArrayList<>();
    textFieldData = new HashMap<String, JTextField>();

    setSize(new Dimension(700, 500));
    setLayout(new BorderLayout());

    //Image Panel -- Shows the topmost visible layer
    JPanel topMostImagePanel = new JPanel();
    placeImagePanel(topMostImagePanel);

    add(topMostImagePanel, BorderLayout.CENTER);

    //Image Options Menu
    blurMenuItem = new JMenuItem("Blur");
    sharpenMenuItem = new JMenuItem("Sharpen");
    sepiaMenuItem = new JMenuItem("Sepia");
    grayscaleMenuItem = new JMenuItem("Grayscale");
    mosaicMenuItem = new JMenuItem("Mosaic (Enter # Seeds)");
    downscaleMenuItem = new JMenuItem("Downscale");
    placeImgOptionsMenu();

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

    //Load and Save Menu
    loadMenuItem = new JMenuItem("Load");
//     loadAllMenuItem = new JMenuItem("Load All");
    saveMenuItem = new JMenuItem("Save");
    saveAllMenuItem = new JMenuItem("Save All");
    loadScriptMenuItem = new JMenuItem("Load Script");
    placeSaveAndLoadMenu();

    //Load and Save Panel -- Load, Load All, Save, Save All, and LoadScript
    JPanel loadAndSavePanel = new JPanel();
    loadButton = new JButton("Load");
//     loadAllButton = new JButton("Load All");
    saveButton = new JButton("Save");
    saveAllButton = new JButton("Save All");
    loadScriptButton = new JButton("Load Script");
    placeSaveAndLoadButtons(loadAndSavePanel);

    // Split Pane for Image Operations Panel and Load/Save Panel
    JSplitPane splitOperAndLoadAndSave = new JSplitPane(SwingConstants.HORIZONTAL,
        imgOptionsPanel, loadAndSavePanel);
    add(splitOperAndLoadAndSave, BorderLayout.WEST);

    //Layer Menu
    createLayerMenuItem = new JMenuItem("Create");
    removeLayerMenuItem = new JMenuItem("Remove");
    makeInvisibleMenuItem = new JMenuItem("Invisible");
    makeVisibleMenuItem = new JMenuItem("Visible");
    placeLayerMenuItem();

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

    //Layer Menu
    buildCheckerboardMenuItem = new JMenuItem("Build");
    placeCheckerboardMenu();

    // Split Pane for Layers Panel and Checkerboard Panel
    JSplitPane splitLayerAndChecker = new JSplitPane(JSplitPane.VERTICAL_SPLIT, layersPanel,
        checkerboardPanel);
    add(splitLayerAndChecker, BorderLayout.EAST);

    // menu
    //Build the File Menu.
    JMenuBar menuBar = new JMenuBar();
    // Menu
    JMenu imageOperMenu = new JMenu("Edit Image");
    imageOperMenu.setMnemonic(KeyEvent.VK_F);
    JMenu layerMenu = new JMenu("Edit Layer");
    layerMenu.setMnemonic(KeyEvent.VK_F);
    JMenu checkerboardMenu = new JMenu("Checkerboard");
    checkerboardMenu.setMnemonic(KeyEvent.VK_F);
    JMenu loadSaveMenu = new JMenu("Load/Save");
    loadSaveMenu.setMnemonic(KeyEvent.VK_F);
    // create menu item and add it to the menu
    loadMenuItem.setMnemonic(KeyEvent.VK_F);
    loadSaveMenu.add(loadMenuItem);
//     loadAllMenuItem.setMnemonic(KeyEvent.VK_F);
//     loadSaveMenu.add(loadAllMenuItem);
    saveMenuItem.setMnemonic(KeyEvent.VK_F);
    loadSaveMenu.add(saveMenuItem);
    saveAllMenuItem.setMnemonic(KeyEvent.VK_F);
    loadSaveMenu.add(saveAllMenuItem);
    loadScriptMenuItem.setMnemonic(KeyEvent.VK_F);
    loadSaveMenu.add(loadScriptMenuItem);

    blurMenuItem.setMnemonic(KeyEvent.VK_F);
    imageOperMenu.add(blurMenuItem);
    sharpenMenuItem.setMnemonic(KeyEvent.VK_F);
    imageOperMenu.add(sharpenMenuItem);
    sepiaMenuItem.setMnemonic(KeyEvent.VK_F);
    imageOperMenu.add(sepiaMenuItem);
    grayscaleMenuItem.setMnemonic(KeyEvent.VK_F);
    imageOperMenu.add(grayscaleMenuItem);
    downscaleMenuItem.setMnemonic(KeyEvent.VK_F);
    imageOperMenu.add(downscaleMenuItem);
    mosaicMenuItem.setMnemonic(KeyEvent.VK_F);
    imageOperMenu.add(mosaicMenuItem);

    createLayerMenuItem.setMnemonic(KeyEvent.VK_F);
    layerMenu.add(createLayerMenuItem);
    removeLayerMenuItem.setMnemonic(KeyEvent.VK_F);
    layerMenu.add(removeLayerMenuItem);
    makeInvisibleMenuItem.setMnemonic(KeyEvent.VK_F);
    layerMenu.add(makeInvisibleMenuItem);
    makeVisibleMenuItem.setMnemonic(KeyEvent.VK_F);
    layerMenu.add(makeVisibleMenuItem);

    buildCheckerboardMenuItem.setMnemonic(KeyEvent.VK_F);
    checkerboardMenu.add(buildCheckerboardMenuItem);

    menuBar.add(imageOperMenu);
    menuBar.add(layerMenu);
    menuBar.add(checkerboardMenu);
    menuBar.add(loadSaveMenu);

    this.setJMenuBar(menuBar);

    //Display the window.
    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

  }

  private void placeImgOptionsMenu() {
    blurMenuItem.setActionCommand("blur");
    blurMenuItem.addActionListener(this);
    grayscaleMenuItem.setActionCommand("grayscale");
    grayscaleMenuItem.addActionListener(this);
    sepiaMenuItem.setActionCommand("sepia");
    sepiaMenuItem.addActionListener(this);
    sharpenMenuItem.setActionCommand("sharpen");
    sharpenMenuItem.addActionListener(this);
    mosaicMenuItem.setActionCommand("mosaicMenu");
    mosaicMenuItem.addActionListener(this);
    downscaleMenuItem.setActionCommand("downscale");
    downscaleMenuItem.addActionListener(this);
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


  private void placeSaveAndLoadButtons(JPanel panel) {
    panel.setBorder(BorderFactory.createTitledBorder("Load/Save"));
    panel.setBackground(new Color(198, 229, 234));
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    loadButton.setActionCommand("load");
    loadButton.addActionListener(this);
    loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(loadButton);
//     loadAllButton.setActionCommand("loadAll");
//     loadAllButton.addActionListener(this);
//     loadAllButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//     panel.add(loadAllButton);
    saveButton.setActionCommand("save");
    saveButton.addActionListener(this);
    saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(saveButton);
    saveAllButton.setActionCommand("saveAll");
    saveAllButton.addActionListener(this);
    saveAllButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(saveAllButton);
    loadScriptButton.setActionCommand("loadScript");
    loadScriptButton.addActionListener(this);
    loadScriptButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(loadScriptButton);
  }

  //Menu Item --> Save and Loading Components (SaveAll, LoadAll, load script, etc)
  private void placeSaveAndLoadMenu() {
    loadMenuItem.setActionCommand("load");
    loadMenuItem.addActionListener(this);
//     loadAllMenuItem.setActionCommand("loadAll");
//     loadAllMenuItem.addActionListener(this);
    saveMenuItem.setActionCommand("save");
    saveMenuItem.addActionListener(this);
    saveAllMenuItem.setActionCommand("saveAll");
    saveAllMenuItem.addActionListener(this);
    loadScriptMenuItem.setActionCommand("loadScript");
    loadScriptMenuItem.addActionListener(this);
  }

  //Center --> Places the Topmost visible layer at the top center
  private void placeImagePanel(JPanel imagePanel) {
    JScrollPane scroller; // Makes this scrollable
    imagePanel.setBackground(new Color(142, 204, 213));
    imagePanel.setBorder(BorderFactory.createTitledBorder("Topmost Visible Layer"));
    IImage image = model.getTopmostVisibleLayerImage();
    if (image != null) {
      ImageIcon img = new ImageIcon(image.getFilename());
      JLabel imageLabel = new JLabel(img);
      scroller = new JScrollPane(imageLabel);
      imageLabel.setIcon(img);
      scroller.setPreferredSize(new Dimension(600, 600)); //Dimension for panel
      imagePanel.add(scroller);
    }
  }

  private void placeLayerMenuItem() {
    createLayerMenuItem.setActionCommand("createLayerMenu");
    createLayerMenuItem.addActionListener(this);
    removeLayerMenuItem.setActionCommand("removeLayer");
    removeLayerMenuItem.addActionListener(this);
    makeInvisibleMenuItem.setActionCommand("invisible");
    makeInvisibleMenuItem.addActionListener(this);
    makeVisibleMenuItem.setActionCommand("visible");
    makeVisibleMenuItem.addActionListener(this);
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

    //Panel for the actual layers
    JPanel layersP = new JPanel();
    layersP.setBackground(new Color(142, 204, 213));
    layersP.setLayout(new BoxLayout(layersP, BoxLayout.Y_AXIS));

    for (int i = 0; i < model.getNumLayers(); i++) {
      JButton layerButton = new JButton(model.getLayer(i).getName());
      layerButton.setActionCommand("layerButton");
      layerButton.addActionListener(this);
      layerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      layersP.add(layerButton);
    }

    layersPanel.add(layersP);

    JPanel currLayerFunctionsP = new JPanel();
    currLayerFunctionsP.setBackground(new Color(198, 229, 234));
    currLayerFunctionsP.setLayout(new FlowLayout());

    JLabel currentLayerText = new JLabel();
    ILayer currLayer = model.getCurrentLayer();
    if (currLayer != null) {
      currentLayerText.setText("Current Layer: " + model.getCurrentLayer().getName());
      layersPanel.add(Box.createVerticalStrut(10));
      currentLayerText.setAlignmentX(Component.CENTER_ALIGNMENT);
      layersPanel.add(currentLayerText);
      layersPanel.add(Box.createVerticalStrut(10));
    }

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

  private void placeCheckerboardMenu() {
    buildCheckerboardMenuItem.setActionCommand("buildBoard");
    buildCheckerboardMenuItem.addActionListener(this);
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
  public void addViewEventListener(IViewListener listener) {
    this.listeners.add(listener);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "load":   // Allows user to load an image of the format jpeg, ppm, or png
        final JFileChooser fChooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPEG/PNG/PPM Images", "jpeg", "ppm", "png");
        fChooser.setFileFilter(filter);
        int retValue = fChooser.showOpenDialog(this);
        if (retValue == JFileChooser.APPROVE_OPTION) {
          File f = fChooser.getSelectedFile();
          this.emitLoadLayerEvent(f.getAbsolutePath());
        }
        break;
      case "save": // Allows user to save an image (with their given file name)
        final JFileChooser fChooser1 = new JFileChooser(".");
        int retValue1 = fChooser1.showSaveDialog(this);
        if (retValue1 == JFileChooser.APPROVE_OPTION) {
          File f = fChooser1.getSelectedFile();
          this.emitSaveTopmostVisibleLayerEvent();
        }
        break;
//       case "loadAll": // Allows the user to load a folder/directory
//         final JFileChooser fchooser2 = new JFileChooser(".");
//         fchooser2.setFileFilter(new FolderFilter());
//         fchooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//         int retvalue2 = fchooser2.showOpenDialog(this);
//         if (retvalue2 == JFileChooser.APPROVE_OPTION) {
//           File f = fchooser2.getSelectedFile();
//         }
//         this.emitLoadAllEvent(f);
//         break;
      case "saveAll":  // Allows the user to save into a folder or create a new folder to save into
        final JFileChooser fChooser3 = new JFileChooser(".");
        fChooser3.setFileFilter(new FolderFilter());
        fChooser3.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int retValue3 = fChooser3.showSaveDialog(this);
        if (retValue3 == JFileChooser.APPROVE_OPTION) {
          File f = fChooser3.getSelectedFile();
          this.emitSaveAllEvent(f.getAbsolutePath());
        }
        break;
      case "loadScript":  // Allows the user to load in a script that is a txt file
        final JFileChooser fChooser4 = new JFileChooser(".");
        FileNameExtensionFilter filter2 = new FileNameExtensionFilter(
            "Text File", "txt");
        fChooser4.setFileFilter(filter2);
        int retValue4 = fChooser4.showOpenDialog(this);
        if (retValue4 == JFileChooser.APPROVE_OPTION) {
          File f = fChooser4.getSelectedFile();
          this.emitLoadScriptEvent(f.getAbsolutePath());
        }
        break;
      case "color1":
        col1 = JColorChooser
            .showDialog(this, "Choose a color", colorChooserDisplay1.getBackground());
        colorChooserDisplay1.setBackground(col1);
        break;
      case "color2":
        col2 = JColorChooser
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
        this.emitDownscaleEvent(new Image("no image"));    // TODO change later
        break;
      case "mosaic":
        this.emitMosaicEvent(1);     // TODO change this later
        break;
      case "mosaicMenu":
        String seeds = JOptionPane.showInputDialog(this,
            "Number of Seeds:", null);
        if (seeds != null) {
          numSeedsField.setText(seeds); // Set the seeds field to input
          this.emitMosaicEvent(Integer.parseInt(numSeedsField.getText()));
        }
      case "buildBoard":
        if (col1 != null && col2 != null) {
          this.emitCheckerboardEvent(Integer.parseInt(sizeTileField.getText()),
              Integer.parseInt(numTilesField.getText()), col1.getRed(), col1.getGreen(),
              col1.getBlue(), col2.getRed(), col2.getGreen(), col2.getBlue());
        }
        this.emitCheckerboardDefaultColorEvent(Integer.parseInt(sizeTileField.getText()),
            Integer.parseInt(numTilesField.getText()));
        break;
      case "visible":
        this.emitMakeLayerVisibleEvent();
        break;
      case "invisible":
        this.emitMakeLayerInvisibleEvent();
        break;
      case "removeLayer":
        this.emitRemoveLayerEvent();
        break;
      case "createLayer":
        this.emitCreateLayerEvent(layerNameField.getText());
        break;
      case "createLayerMenu":
        String layerName = JOptionPane.showInputDialog(this,
            "Name for Layer:", null);
        if (layerName != null) {
          layerNameField.setText(layerName); // Set the layer name field to input
          this.emitCreateLayerEvent(layerNameField.getText());
        }
        break;
      case "layerButton":
        JButton button = (JButton) e.getSource();
        String buttonName = button.getName();
        this.emitMakeCurrentEvent(buttonName);
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
  public void renderMessage(String message) throws IOException {

  }

  @Override
  public void renderLayerState() throws IOException {

  }

  protected void emitMakeCurrentEvent(String nameOfButton) {
    for (IViewListener listener : listeners) {
      listener.handleMakeCurrentEvent(nameOfButton);
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

  protected void emitCreateLayerEvent(String layerName) {
    for (IViewListener listener : listeners) {
      listener.handleCreateLayerEvent(layerName);
    }
  }

  protected void emitRemoveLayerEvent() {
    for (IViewListener listener : listeners) {
      listener.handleRemoveLayerEvent();
    }
  }

  protected void emitLoadLayerEvent(String filename) {
    for (IViewListener listener : listeners) {
      listener.handleLoadLayerEvent(filename);
    }
  }

  protected void emitLoadScriptEvent(String txtFilename) {
    for (IViewListener listener : listeners) {
      listener.handleLoadScriptEvent(txtFilename);
    }
  }

  protected void emitSaveTopmostVisibleLayerEvent() {
    for (IViewListener listener : listeners) {
      listener.handleSaveTopmostVisibleLayerEvent();
    }
  }

  protected void emitSaveAllEvent(String directory) {
    for (IViewListener listener : listeners) {
      listener.handleSaveAllEvent(directory);
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

  protected void emitDownscaleEvent(IImage image) {
    for (IViewListener listener : listeners) {
      listener.handleDownscaleEvent(image);
    }
  }

  protected void emitMosaicEvent(int numSeeds) {
    for (IViewListener listener : listeners) {
      listener.handleMosaicEvent(numSeeds);
    }
  }

  protected void emitCheckerboardEvent(int sizeOfTiles, int numTiles, int r1, int g1, int b1,
      int r2, int g2, int b2) {
    for (IViewListener listener : listeners) {
      listener.handleCheckerboardEvent(sizeOfTiles, numTiles, r1, g1, b1, r2, g2, b2);
    }
  }

  protected void emitCheckerboardDefaultColorEvent(int sizeOfTiles, int numTiles) {
    for (IViewListener listener : listeners) {
      listener.handleCheckerboardDefaultColorEvent(sizeOfTiles, numTiles);
    }
  }
}