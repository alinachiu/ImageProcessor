package view;

import static javax.swing.JOptionPane.showMessageDialog;

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
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.ILayerModelState;
import model.image.IImage;
import model.image.IPixel;
import model.layer.ILayer;

/**
 * Represents the window that displays the graphical representation of the image processing
 * program.
 */
public class MyWindow extends JFrame implements IGUIView, ActionListener {

  //Saving/Loading Buttons
  private final JButton loadButton;
  private final JButton loadAllButton;
  private final JButton saveButton;
  private final JButton saveAllButton;
  private final JButton loadScriptButton;

  // Saving/Loading Menu
  private final JMenuItem loadMenuItem;
  private final JMenuItem loadAllMenuItem;
  private final JMenuItem saveMenuItem;
  private final JMenuItem saveAllMenuItem;
  private final JMenuItem loadScriptMenuItem;

  // Image Operations Buttons
  private final JButton blurButton;
  private final JButton sharpenButton;
  private final JButton grayscaleButton;
  private final JButton sepiaButton;
  private final JButton downscaleButton;
  private final JTextField widthField;
  private final JTextField heightField;
  private final JButton mosaicButton;
  private final JTextField numSeedsField;

  // Image Operations Menu
  private final JMenuItem blurMenuItem;
  private final JMenuItem sharpenMenuItem;
  private final JMenuItem grayscaleMenuItem;
  private final JMenuItem sepiaMenuItem;
  private final JMenuItem downscaleMenuItem;
  private JPanel downscaleMenuP;
  private final JTextField widthFieldC;
  private final JTextField heightFieldC;
  private final JMenuItem mosaicMenuItem;

  // Layer Functions
  private final JTextField layerNameField;
  private final JButton createLayerButton;
  private final JButton removeLayerButton;
  private final JButton makeInvisibleButton;
  private final JButton makeVisibleButton;
  private final JLabel currentLayerText;
  private JScrollPane layerScroller;

  // Layer Menu
  private final JMenuItem createLayerMenuItem;
  private final JMenuItem setCurrentMenuItem;
  private final JMenuItem removeLayerMenuItem;
  private final JMenuItem makeInvisibleMenuItem;
  private final JMenuItem makeVisibleMenuItem;
  private final JPanel layersPanel;
  private JPanel layerButtonP;
  private final JPanel currTextP;

  // Checkerboard Panel for Menu
  private final JPanel checkerboardPanel;
  private JPanel cbFunctionsP;
  private final JTextField numTilesField;
  private final JLabel numTilesLabel;
  private final JTextField sizeTileField;
  private final JLabel sizeTilesLabel;
  private final JTextField numTilesFieldC;
  private final JLabel numTilesLabelC;
  private final JTextField sizeTileFieldC;
  private final JLabel sizeTilesLabelC;
  private final JButton color1Button;
  private Color col1;
  private Color col2;
  private final JButton color2Button;
  private final JButton buildCheckerboardButton;
  private final JLabel colorChooserDisplay1;
  private final JLabel colorChooserDisplay2;
  private JLabel colorChooserDisplay1C;
  private JLabel colorChooserDisplay2C;
  private JButton color1ButtonC;
  private Color col1C;
  private Color col2C;
  private JButton color2ButtonC;

  // Checkerboard Menu
  private final JMenuItem buildCheckerboardMenuItem;
  private JPanel cbFunc;

  //Topmost visible layer Panel
  private JPanel topMostImagePanel;
  private JScrollPane scroller;

  private final List<IViewListener> listeners;
  private final ILayerModelState model;

  /**
   * Constructs a {@code MyWindow} object with all the necessary components to create the GUI for
   * the Image Processor.
   *
   * @param model an immutable model state used to assist the view but prevent communication between
   *              the view and the model
   */
  public MyWindow(ILayerModelState model) {
    super();
    this.model = model;
    this.listeners = new ArrayList<>();
    this.setTitle("Image Processor");
    setSize(new Dimension(1200, 500));
    setLayout(new BorderLayout());

    //Image Panel -- Shows the topmost visible layer
    topMostImagePanel = new JPanel();
    topMostImagePanel.setPreferredSize(new Dimension(600, 600));
    scroller = new JScrollPane();
    this.placeImagePanel();

    add(topMostImagePanel, BorderLayout.CENTER);

    //Image Options Menu
    blurMenuItem = new JMenuItem("Blur");
    sharpenMenuItem = new JMenuItem("Sharpen");
    sepiaMenuItem = new JMenuItem("Sepia");
    grayscaleMenuItem = new JMenuItem("Grayscale");
    mosaicMenuItem = new JMenuItem("Mosaic (Enter # Seeds)");
    downscaleMenuItem = new JMenuItem("Downscale");
    widthFieldC = new JTextField(5);
    heightFieldC = new JTextField(5);
    downscaleMenuP = new JPanel();
    placeImgOptionsMenu();

    //Image Options Panel -- Blur, Sharpen, Gray, Sepia, Mosaic, and Downscale
    JPanel imgOptionsPanel = new JPanel();
    blurButton = new JButton("Blur");
    sharpenButton = new JButton("Sharpen");
    sepiaButton = new JButton("Sepia");
    grayscaleButton = new JButton("Grayscale");
    numSeedsField = new JTextField(3);
    mosaicButton = new JButton("Mosaic (Enter # Seeds)");
    widthField = new JTextField(3);
    heightField = new JTextField(3);
    downscaleButton = new JButton("Downscale (Enter width & height, respectively)");
    placeImgOptionsPanel(imgOptionsPanel);

    //Load and Save Menu
    loadMenuItem = new JMenuItem("Load");
    loadAllMenuItem = new JMenuItem("Load All");
    saveMenuItem = new JMenuItem("Save");
    saveAllMenuItem = new JMenuItem("Save All");
    loadScriptMenuItem = new JMenuItem("Load Script");
    placeSaveAndLoadMenu();

    //Load and Save Panel -- Load, Load All, Save, Save All, and LoadScript
    JPanel loadAndSavePanel = new JPanel();
    loadButton = new JButton("Load");
    loadAllButton = new JButton("Load All");
    saveButton = new JButton("Save");
    saveAllButton = new JButton("Save All");
    loadScriptButton = new JButton("Load Script");
    placeSaveAndLoadButtons(loadAndSavePanel);

    // Split Pane for Image Operations Panel and Load/Save Panel
    JSplitPane splitOperAndLoadAndSave = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
        imgOptionsPanel, loadAndSavePanel);
    add(splitOperAndLoadAndSave, BorderLayout.WEST);

    //Layer Menu
    createLayerMenuItem = new JMenuItem("Create");
    setCurrentMenuItem = new JMenuItem("Set Current");
    removeLayerMenuItem = new JMenuItem("Remove");
    makeInvisibleMenuItem = new JMenuItem("Invisible");
    makeVisibleMenuItem = new JMenuItem("Visible");
    placeLayerMenuItem();

    //Layers Panel -- Shows Layers, Visibility Options, Create/Remove Layer
    layersPanel = new JPanel();
    layerButtonP = new JPanel();
    createLayerButton = new JButton("Create Layer");
    currentLayerText = new JLabel();
    currTextP = new JPanel();
    layerScroller = new JScrollPane();
    layerNameField = new JTextField(10);
    removeLayerButton = new JButton("Remove");
    makeInvisibleButton = new JButton("Invisible");
    makeVisibleButton = new JButton("Visible");
    placeLayersPanel();

    //Checkerboard Panel --Can enter num tiles, size of tile, and two colors (optional)
    checkerboardPanel = new JPanel();
    cbFunctionsP = new JPanel();
    numTilesField = new JTextField(3);
    numTilesLabel = new JLabel("Side Dimension:");
    sizeTileField = new JTextField(3);
    sizeTilesLabel = new JLabel("Tile Size:");
    color1Button = new JButton("Choose color 1 (Optional)");
    color2Button = new JButton("Choose color 2 (Optional)");
    buildCheckerboardButton = new JButton("Build board");
    colorChooserDisplay1 = new JLabel("      ");
    colorChooserDisplay2 = new JLabel("      ");
    numTilesFieldC = new JTextField(3);
    numTilesLabelC = new JLabel("Side Dimension:");
    sizeTileFieldC = new JTextField(3);
    sizeTilesLabelC = new JLabel("Tile Size:");
    color1ButtonC = new JButton("Choose color 1 (Optional)");
    color2ButtonC = new JButton("Choose color 2 (Optional)");
    colorChooserDisplay1C = new JLabel("      ");
    colorChooserDisplay2C = new JLabel("      ");
    placeCheckerboardPanel();

    //Layer Menu
    buildCheckerboardMenuItem = new JMenuItem("Build");
    placeCheckerboardMenu();

    // Split Pane for Layers Panel and Checkerboard Panel
    JSplitPane splitLayerAndChecker = new JSplitPane(JSplitPane.VERTICAL_SPLIT, checkerboardPanel,
        layersPanel);
    add(splitLayerAndChecker, BorderLayout.EAST);

    // menu
    this.initMenu();

    //Display the window.
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

  }

  /**
   * Initializes the menu bar for the view, including sections for editing an image, editing layers,
   * creating a checkerboard, and loading/saving.
   */
  private void initMenu() {
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
    loadAllMenuItem.setMnemonic(KeyEvent.VK_F);
    loadSaveMenu.add(loadAllMenuItem);
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
    setCurrentMenuItem.setMnemonic(KeyEvent.VK_F);
    layerMenu.add(setCurrentMenuItem);
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
  }

  /**
   * Places the image operations to their respective menu items and adds action listeners and
   * commands to them.
   */
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
    downscaleMenuItem.setActionCommand("downscaleMenu");
    downscaleMenuItem.addActionListener(this);
  }

  /**
   * Places the downscale menu with the components necessary to downscale an image such as
   * submitting the height and width to downscale the image to.
   *
   * @return the JPanel with added downscale components
   */
  private JPanel placeDownscaleMenu() {
    downscaleMenuP = new JPanel();
    downscaleMenuP.setLayout(new GridBagLayout());
    downscaleMenuP.setBackground(new Color(142, 204, 213));
    GridBagConstraints c = new GridBagConstraints();
    c.gridwidth = GridBagConstraints.REMAINDER;

    //For Submitting Width
    JPanel widthPanel = new JPanel();
    widthPanel.setLayout(new FlowLayout());
    widthPanel.setBackground(new Color(142, 204, 213));
    JLabel widthLabelC = new JLabel("Enter Width:");
    widthPanel.add(widthLabelC);
    widthPanel.add(widthFieldC);
    downscaleMenuP.add(widthPanel, c);

    //For Submitting Height
    JPanel heightPanel = new JPanel();
    heightPanel.setLayout(new FlowLayout());
    heightPanel.setBackground(new Color(142, 204, 213));
    JLabel heightLabelC = new JLabel("Enter Height:");
    heightPanel.add(heightLabelC);
    heightPanel.add(heightFieldC);
    downscaleMenuP.add(heightPanel, c);

    return downscaleMenuP;
  }

  /**
   * Places the operations that can be done on a current layer image in the upper-left hand corner.
   * These operations include blurring an image, sharpening an image, applying sepia to an image,
   * and applying grayscale to an image.
   *
   * @param panel the given panel to place buttons on
   */
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
    JPanel downscalePanel = new JPanel();
    downscalePanel.setLayout(new FlowLayout());
    downscalePanel.setBackground(new Color(198, 229, 234));
    downscalePanel.add(widthField);
    downscalePanel.add(heightField);
    downscaleButton.setActionCommand("downscale");
    downscaleButton.addActionListener(this);
    downscalePanel.add(downscaleButton);
    panel.add(downscalePanel);
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

  /**
   * Places the buttons associated with saving and loading actions in the bottom left corner onto a
   * given panel.
   *
   * @param panel the given panel to place the buttons on
   */
  private void placeSaveAndLoadButtons(JPanel panel) {
    panel.setBorder(BorderFactory.createTitledBorder("Load/Save"));
    panel.setBackground(new Color(198, 229, 234));
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    loadButton.setActionCommand("load");
    loadButton.addActionListener(this);
    loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(loadButton);
    loadAllButton.setActionCommand("loadAll");
    loadAllButton.addActionListener(this);
    loadAllButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(loadAllButton);
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

  /**
   * Places the save and load menu in the top left corner which has all the save and loading
   * components including save all, save, load all, and load script.
   */
  private void placeSaveAndLoadMenu() {
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

  /**
   * Places the image panel in the center with the topmost visible layer, if no layers exist or if
   * all layers are invisible, will show nothing.
   */
  private void placeImagePanel() {
    topMostImagePanel.setBackground(new Color(142, 204, 213));
    topMostImagePanel.setBorder(BorderFactory.createTitledBorder("Topmost Visible Layer"));
    IImage image = model.getTopmostVisibleLayerImage();
    if (image != null) {
      BufferedImage layerImg = getImageToBufferedImg(image);
      JLabel imageLabel = new JLabel();
      imageLabel.setIcon(new ImageIcon(layerImg));
      scroller = new JScrollPane(imageLabel);

    } else {
      scroller = new JScrollPane();
    }
    scroller.setPreferredSize(new Dimension(400, 420)); //Dimension for panel
    topMostImagePanel.add(scroller);
  }

  /**
   * Converts the given image to a buffered image.
   *
   * @param image the image to be converted
   * @return a buffered image.
   */
  private BufferedImage getImageToBufferedImg(IImage image) {
    BufferedImage bi = new BufferedImage(image.getWidth(), image.getHeight(),
        BufferedImage.TYPE_INT_RGB);
    IPixel[][] pixels = image.getImage();
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        IPixel pix = pixels[i][j];
        int red = pix.getRed();
        int green = pix.getGreen();
        int blue = pix.getBlue();

        int rgb = (red << 16) | (green << 8) | blue;
        bi.setRGB(j, i, rgb);
      }
    }
    return bi;
  }

  /**
   * Places the layer menu item and sets its action commands accordingly.
   */
  private void placeLayerMenuItem() {
    createLayerMenuItem.setActionCommand("createLayerMenu");
    createLayerMenuItem.addActionListener(this);
    setCurrentMenuItem.setActionCommand("setCurrent");
    setCurrentMenuItem.addActionListener(this);
    removeLayerMenuItem.setActionCommand("removeLayer");
    removeLayerMenuItem.addActionListener(this);
    makeInvisibleMenuItem.setActionCommand("invisible");
    makeInvisibleMenuItem.addActionListener(this);
    makeVisibleMenuItem.setActionCommand("visible");
    makeVisibleMenuItem.addActionListener(this);
  }

  /**
   * Places the layers panel accordingly in the bottom right corner with layer functions such as
   * create, remove, visibility, and all layer buttons.
   */
  private void placeLayersPanel() {
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

  /**
   * Places the layers buttons associated with the layers the user has created/loaded in accordingly
   * in the bottom right corner underneath the layer functions.
   */
  private void placeLayerButtonP() {
    layerButtonP.setLayout(new BoxLayout(layerButtonP, BoxLayout.Y_AXIS));
    layerButtonP.setBackground(new Color(142, 204, 213));
    for (int i = 0; i < model.getNumLayers(); i++) {
      JPanel buttonAndVisibilityAndImage = new JPanel();
      buttonAndVisibilityAndImage.setLayout(new FlowLayout());
      buttonAndVisibilityAndImage.setBackground(new Color(142, 204, 213));
      JButton layerButton = new JButton(model.getLayer(i).getName());
      layerButton.setActionCommand("layerButton");
      layerButton.addActionListener(this);
      buttonAndVisibilityAndImage.add(layerButton);
      String visibilityStatus;
      if (model.getLayer(i).isVisible()) {
        visibilityStatus = "Visible, ";
      } else {
        visibilityStatus = "Invisible, ";
      }
      JLabel visibility = new JLabel(visibilityStatus);
      buttonAndVisibilityAndImage.add(visibility);
      String imageStatus;
      if (model.getLayer(i).getImage() != null) {
        imageStatus = "Has image";
      } else {
        imageStatus = "No image";
      }
      JLabel imageStatusLabel = new JLabel(imageStatus);
      buttonAndVisibilityAndImage.add(imageStatusLabel);
      layerButtonP.add(buttonAndVisibilityAndImage);
    }

    layerScroller = new JScrollPane(layerButtonP);
    JScrollBar bar = layerScroller.getVerticalScrollBar();
    bar.setPreferredSize(new Dimension(10, 200));
    layersPanel.add(layerScroller);
  }

  /**
   * Places the text in the bottom right corner that describes to the user which layer is the
   * current layer.
   */
  private void placeCurrentLayerText() {
    currTextP.setLayout(new BoxLayout(layerButtonP, BoxLayout.Y_AXIS));
    currTextP.setBackground(new Color(142, 204, 213));
    ILayer currLayer = model.getCurrentLayer();
    if (currLayer != null) {
      currentLayerText.setText("Current Layer: " + model.getCurrentLayer().getName());
      currentLayerText.setAlignmentX(Component.CENTER_ALIGNMENT);
      layersPanel.add(currentLayerText);
    }
  }

  /**
   * Places the checkerboard menu in the menu bar.
   */
  private void placeCheckerboardMenu() {
    buildCheckerboardMenuItem.setActionCommand("buildBoardMenu");
    buildCheckerboardMenuItem.addActionListener(this);
  }

  /**
   * Places the checkerboard functions in the top right box so that it can perform actions such as
   * getting the number of tiles, size of the tiles, colors (optional), and building the board.
   */
  private void placeCheckerboardPanel() {
    checkerboardPanel.setBorder(BorderFactory.createTitledBorder("Create a Checkerboard"));
    checkerboardPanel.setLayout(new BoxLayout(checkerboardPanel, BoxLayout.Y_AXIS));

    cbFunctionsP = new JPanel();
    cbFunctionsP.setLayout(new GridBagLayout());
    cbFunctionsP.setBackground(new Color(142, 204, 213));
    GridBagConstraints c = new GridBagConstraints();
    c.gridwidth = GridBagConstraints.REMAINDER;

    //For Submitting Side Dimension
    JPanel sideDimPanel = new JPanel();
    sideDimPanel.setLayout(new FlowLayout());
    sideDimPanel.setBackground(new Color(142, 204, 213));
    sideDimPanel.add(numTilesLabel);
    sideDimPanel.add(numTilesField);
    cbFunctionsP.add(sideDimPanel, c);

    //For Submitting Tile Size
    JPanel numTilesPanel = new JPanel();
    numTilesPanel.setLayout(new FlowLayout());
    numTilesPanel.setBackground(new Color(142, 204, 213));
    numTilesPanel.add(sizeTilesLabel);
    numTilesPanel.add(sizeTileField);
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

    checkerboardPanel.add(cbFunctionsP);

    //For Build board button
    buildCheckerboardButton.setActionCommand("buildBoard");
    buildCheckerboardButton.addActionListener(this);
    buildCheckerboardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    checkerboardPanel.setBackground(new Color(198, 229, 234));
    checkerboardPanel.add(buildCheckerboardButton, c);

  }

  /**
   * Builds the checkerboard menu panel which can be found in the top left corner.
   *
   * @return the checkerboard panel menu
   */
  private JPanel placeCheckerboardPanelMenu() {
    cbFunc = new JPanel();
    cbFunc.setLayout(new GridBagLayout());
    cbFunc.setBackground(new Color(142, 204, 213));
    GridBagConstraints c = new GridBagConstraints();
    c.gridwidth = GridBagConstraints.REMAINDER;

    //For Submitting Side Dimension
    JPanel sideDimPanelC = new JPanel();
    sideDimPanelC.setLayout(new FlowLayout());
    sideDimPanelC.setBackground(new Color(142, 204, 213));
    sideDimPanelC.add(numTilesLabelC);
    sideDimPanelC.add(numTilesFieldC);
    cbFunc.add(sideDimPanelC, c);
    col1 = null;
    col2 = null;

    //For Submitting Tile Size
    JPanel numTilesPanelC = new JPanel();
    numTilesPanelC.setLayout(new FlowLayout());
    numTilesPanelC.setBackground(new Color(142, 204, 213));
    numTilesPanelC.add(sizeTilesLabelC);
    numTilesPanelC.add(sizeTileFieldC);
    cbFunc.add(numTilesPanelC, c);

    //For Choosing Color 1 (Optional)
    JPanel color1ChooserC = new JPanel();
    color1ChooserC.setLayout(new FlowLayout());
    color1ChooserC.setBackground(new Color(142, 204, 213));
    color1ButtonC.setActionCommand("color1C");
    color1ButtonC.addActionListener(this);
    color1ChooserC.add(color1ButtonC);
    colorChooserDisplay1C.setOpaque(true); //so that background color shows up
    colorChooserDisplay1C.setBackground(Color.WHITE);
    color1ChooserC.add(colorChooserDisplay1C);
    cbFunc.add(color1ChooserC, c);

    //For Choosing Color 2 (Optional)
    JPanel color2ChooserC = new JPanel();
    color2ChooserC.setLayout(new FlowLayout());
    color2ChooserC.setBackground(new Color(142, 204, 213));
    color2ButtonC.setActionCommand("color2C");
    color2ButtonC.addActionListener(this);
    color2ChooserC.add(color2ButtonC);
    colorChooserDisplay2C.setOpaque(true); //so that background color shows up
    colorChooserDisplay2C.setBackground(Color.WHITE);
    color2ChooserC.add(colorChooserDisplay2C);
    cbFunc.add(color2ChooserC, c);

    return cbFunc;
  }

  @Override
  public void addViewEventListener(IViewListener listener) throws IllegalArgumentException {
    if (!this.listeners.contains(listener)) {
      this.listeners.add(listener);
    } else {
      throw new IllegalArgumentException("Listener has already been added!");
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "load":   // Allows user to load an image of the format jpeg, ppm, or png
        final JFileChooser fChooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPEG/JPG/PNG/PPM Images", "jpeg", "ppm", "png", "jpg");
        fChooser.setFileFilter(filter);
        int retValue = fChooser.showOpenDialog(this);
        if (retValue == JFileChooser.APPROVE_OPTION) {
          File f = fChooser.getSelectedFile();
          this.emitLoadLayerEvent(f.getAbsolutePath());
          resetScroller();
          reset();
        }
        break;
      case "save": // Allows user to save an image (with their given file name)
        final JFileChooser fChooser1 = new JFileChooser(".");
        int retValue1 = fChooser1.showSaveDialog(this);
        if (retValue1 == JFileChooser.APPROVE_OPTION) {
          File f = fChooser1.getSelectedFile();
          this.emitSaveTopmostVisibleLayerEvent(f.getAbsolutePath());
        }
        break;
      case "loadAll": // Allows the user to load a folder/directory
        JFileChooser fChooser2 = new JFileChooser(".");
        FileNameExtensionFilter filter2 = new FileNameExtensionFilter(
            "Text File", "txt");
        fChooser2.setFileFilter(filter2);
        int retvalue2 = fChooser2.showOpenDialog(this);
        if (retvalue2 == JFileChooser.APPROVE_OPTION) {
          File f = fChooser2.getSelectedFile();
          this.emitLoadAllEvent(f.getAbsolutePath());
          resetScroller();
          reset();
        }
        break;
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
        FileNameExtensionFilter filter4 = new FileNameExtensionFilter(
            "Text File", "txt");
        fChooser4.setFileFilter(filter4);
        int retValue4 = fChooser4.showOpenDialog(this);
        if (retValue4 == JFileChooser.APPROVE_OPTION) {
          File f = fChooser4.getSelectedFile();
          this.emitLoadScriptEvent(f.getAbsolutePath());
          resetScroller();
          reset();
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
        reset();
        break;
      case "sharpen":
        this.emitSharpenEvent();
        reset();
        break;
      case "grayscale":
        this.emitGrayscaleEvent();
        reset();
        break;
      case "sepia":
        this.emitSepiaEvent();
        reset();
        break;
      case "downscale":
        this.emitDownscaleEvent(
            Integer.parseInt(widthField.getText()), Integer.parseInt(heightField.getText()));
        reset();
        break;
      case "mosaic":
        this.emitMosaicEvent(Integer.parseInt(numSeedsField.getText()));
        reset();
        break;
      case "mosaicMenu":
        String seeds = JOptionPane.showInputDialog(this,
            "Number of Seeds:", null);
        if (seeds != null) {
          numSeedsField.setText(seeds); // Set the seeds field to input
          this.emitMosaicEvent(Integer.parseInt(numSeedsField.getText()));
        }
        reset();
        break;
      case "buildBoard":
        if (col1 != null && col2 != null) {
          this.emitCheckerboardEvent(Integer.parseInt(sizeTileField.getText()),
              Integer.parseInt(numTilesField.getText()), col1.getRed(), col1.getGreen(),
              col1.getBlue(), col2.getRed(), col2.getGreen(), col2.getBlue());
        } else {
          this.emitCheckerboardDefaultColorEvent(Integer.parseInt(sizeTileField.getText()),
              Integer.parseInt(numTilesField.getText()));
        }
        resetScroller();
        reset();
        break;
      case "visible":
        this.emitMakeLayerVisibleEvent();
        resetScroller();
        reset();
        break;
      case "invisible":
        this.emitMakeLayerInvisibleEvent();
        resetScroller();
        reset();
        break;
      case "removeLayer":
        this.emitRemoveLayerEvent();
        resetScroller();
        reset();
        break;
      case "createLayer":
        this.emitCreateLayerEvent(layerNameField.getText());
        resetScroller();
        reset();
        break;
      case "createLayerMenu":
        String layerName = JOptionPane.showInputDialog(this,
            "Name for Layer:", null);
        if (layerName != null) {
          this.emitCreateLayerEvent(layerName);
        }
        resetScroller();
        reset();
        break;
      case "layerButton":
        String button = ((JButton) e.getSource()).getText();
        this.emitMakeCurrentEvent(button);
        reset();
        break;
      case "setCurrent":
        String currentLayer = JOptionPane.showInputDialog(this,
            "Name of Layer to set current:", null);
        if (currentLayer != null) {
          this.emitMakeCurrentEvent(currentLayer);
        }
        resetScroller();
        reset();
        break;
      case "buildBoardMenu":
        int result = JOptionPane
            .showConfirmDialog(null, placeCheckerboardPanelMenu(), "Create a Checkerboard",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
          if (col1C != null && col2C != null) {
            this.emitCheckerboardEvent(Integer.parseInt(sizeTileFieldC.getText()),
                Integer.parseInt(numTilesFieldC.getText()), col1C.getRed(), col1C.getGreen(),
                col1C.getBlue(), col2C.getRed(), col2C.getGreen(), col2C.getBlue());
          } else {
            this.emitCheckerboardDefaultColorEvent(Integer.parseInt(sizeTileFieldC.getText()),
                Integer.parseInt(numTilesFieldC.getText()));
          }
          resetCheckerboardMenu();
        }
        resetScroller();
        reset();
        this.validate();
        break;
      case "color1C":
        col1C = JColorChooser
            .showDialog(this, "Choose a color", colorChooserDisplay1C.getBackground());
        colorChooserDisplay1C.setBackground(col1C);
        break;
      case "color2C":
        col2C = JColorChooser
            .showDialog(this, "Choose a color", colorChooserDisplay2C.getBackground());
        colorChooserDisplay2C.setBackground(col2C);
        break;
      case "downscaleMenu":
        int downscaleResult = JOptionPane
            .showConfirmDialog(null, placeDownscaleMenu(), "Downscale Current Layer Image:",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (downscaleResult == JOptionPane.OK_OPTION) {
          this.emitDownscaleEvent(Integer.parseInt(widthFieldC.getText()),
              Integer.parseInt(heightFieldC.getText()));
        }
        resetScroller();
        reset();
        this.validate();
        break;
      default:
        this.renderMessage("Invalid action!");
    }
  }

  /**
   * Resets the scroller panel so that it updates which layer buttons are shown/adds the scroller if
   * necessary.
   */
  private void resetScroller() {
    layersPanel.remove(layerScroller);
    layerScroller = new JScrollPane();
    layerButtonP = new JPanel();
    this.placeLayerButtonP();
    this.validate();
  }

  /**
   * Resets the menu dialog panel for the checkerboard so that the color buttons are reset.
   */
  private void resetCheckerboardMenu() {
    cbFunc = new JPanel();
    color1ButtonC = new JButton("Choose color 1 (Optional)");
    color2ButtonC = new JButton("Choose color 2 (Optional)");
    colorChooserDisplay1C = new JLabel("      ");
    colorChooserDisplay2C = new JLabel("      ");
  }

  /**
   * Resets the board to show the topmost visible layer (with any of it operations if necessary).
   */
  private void reset() {
    layersPanel.remove(currentLayerText);
    this.placeCurrentLayerText();
    this.remove(topMostImagePanel);
    topMostImagePanel = new JPanel();
    this.placeImagePanel();
    add(topMostImagePanel, BorderLayout.CENTER);
    this.validate();
    this.repaint();
  }

  /**
   * A class to determine if the file is a directory/folder so that the save functions only allow
   * the user to choose a folder.
   */
  private static class FolderFilter extends javax.swing.filechooser.FileFilter {

    /**
     * Determines if the given file is a directory.
     *
     * @param file the file to check if it is a directory.
     * @return boolean that is true if the given file is a directory.
     * @throws IllegalArgumentException if the given file is null or cannot be found.
     */
    @Override
    public boolean accept(File file) {
      if (file == null) {
        throw new IllegalArgumentException("File is null");
      }
      return file.isDirectory();
    }

    /**
     * Returns the description of a folder filter which will appear in the dialog box for choosing a
     * directory.
     *
     * @return the description of the folder.
     */
    @Override
    public String getDescription() {
      return "Folder/Directory";
    }
  }


  @Override
  public void renderMessage(String message) throws IllegalArgumentException {
    if (message == null) {
      throw new IllegalArgumentException("Message cannot be null!");
    }
    showMessageDialog(null, message);
  }

  /**
   * Emits the make current event to the listeners in the listeners field.
   *
   * @param nameOfButton the name of the button being clicked
   */
  protected void emitMakeCurrentEvent(String nameOfButton) {
    for (IViewListener listener : listeners) {
      listener.handleMakeCurrentEvent(nameOfButton);
    }
  }

  /**
   * Emits the make sepia event to the listeners in the listeners field.
   */
  protected void emitSepiaEvent() {
    for (IViewListener listener : listeners) {
      listener.handleSepiaEvent();
    }
  }

  /**
   * Emits the make grayscale event to the listeners in the listeners field.
   */
  protected void emitGrayscaleEvent() {
    for (IViewListener listener : listeners) {
      listener.handleGrayscaleEvent();
    }
  }

  /**
   * Emits the make blur event to the listeners in the listeners field.
   */
  protected void emitBlurEvent() {
    for (IViewListener listener : listeners) {
      listener.handleBlurEvent();
    }
  }

  /**
   * Emits the make sharpen event to the listeners in the listeners field.
   */
  protected void emitSharpenEvent() {
    for (IViewListener listener : listeners) {
      listener.handleSharpenEvent();
    }
  }

  /**
   * Emits the make create layer event to the listeners in the listeners field.
   *
   * @param layerName the layer name to be created
   */
  protected void emitCreateLayerEvent(String layerName) {
    for (IViewListener listener : listeners) {
      listener.handleCreateLayerEvent(layerName);
    }
  }

  /**
   * Emits the make remove layer event to the listeners in the listeners field.
   */
  protected void emitRemoveLayerEvent() {
    for (IViewListener listener : listeners) {
      listener.handleRemoveLayerEvent();
    }
  }

  /**
   * Emits the make load layer event to the listeners in the listeners field.
   *
   * @param filename the filename to be loaded in
   */
  protected void emitLoadLayerEvent(String filename) {
    for (IViewListener listener : listeners) {
      listener.handleLoadLayerEvent(filename);
    }
  }

  /**
   * Emits the make load all event to the listeners in the listeners field.
   *
   * @param filename the filename to be loaded in
   */
  protected void emitLoadAllEvent(String filename) {
    for (IViewListener listener : listeners) {
      listener.handleLoadAllEvent(filename);
    }
  }

  /**
   * Emits the make load script to the listeners in the listeners field.
   *
   * @param txtFilename the filename to be loaded in
   */
  protected void emitLoadScriptEvent(String txtFilename) {
    for (IViewListener listener : listeners) {
      listener.handleLoadScriptEvent(txtFilename);
    }
  }

  /**
   * Emits the make save topmost visible layer event to the listeners in the listeners field.
   *
   * @param desiredName the filename to save the layer as
   */
  protected void emitSaveTopmostVisibleLayerEvent(String desiredName) {
    for (IViewListener listener : listeners) {
      listener.handleSaveTopmostVisibleLayerEvent(desiredName);
    }
  }

  /**
   * Emits the make save all items in the multi-layered image event to the listeners in the
   * listeners field.
   *
   * @param directory the name of the directory to save
   */
  protected void emitSaveAllEvent(String directory) {
    for (IViewListener listener : listeners) {
      listener.handleSaveAllEvent(directory);
    }
  }

  /**
   * Emits the make layer invisible event to the listeners in the listeners field.
   */
  protected void emitMakeLayerInvisibleEvent() {
    for (IViewListener listener : listeners) {
      listener.handleMakeLayerInvisibleEvent();
    }
  }

  /**
   * Emits the make layer visible event to the listeners in the listeners field.
   */
  protected void emitMakeLayerVisibleEvent() {
    for (IViewListener listener : listeners) {
      listener.handleMakeLayerVisibleEvent();
    }
  }

  /**
   * Emits the downscale event to the listeners in the listeners field.
   *
   * @param width  the width to downscale to
   * @param height the height to downscale to
   */
  protected void emitDownscaleEvent(int width, int height) {
    for (IViewListener listener : listeners) {
      listener.handleDownscaleEvent(width, height);
    }
  }

  /**
   * Emits the mosaic event to the listeners in the listeners field.
   *
   * @param numSeeds the number of random seeds to use for the mosaic
   */
  protected void emitMosaicEvent(int numSeeds) {
    for (IViewListener listener : listeners) {
      listener.handleMosaicEvent(numSeeds);
    }
  }

  /**
   * Emits the custom color checkerboard action to any listeners that the view has.
   *
   * @param sizeOfTiles size of the checkerboard tiles
   * @param numTiles    number of tiles per row/column
   * @param r1          red value associated with the first custom color
   * @param g1          green value associated with the first custom color
   * @param b1          blue value associated with the first custom color
   * @param r2          red value associated with the second custom color
   * @param g2          green value associated with the second custom color
   * @param b2          blue value associated with the second custom color
   */
  protected void emitCheckerboardEvent(int sizeOfTiles, int numTiles, int r1, int g1, int b1,
      int r2, int g2, int b2) {
    for (IViewListener listener : listeners) {
      listener.handleCheckerboardEvent(sizeOfTiles, numTiles, r1, g1, b1, r2, g2, b2);
    }
  }

  /**
   * Emits the default color checkerboard action to any listeners that the view has.
   *
   * @param sizeOfTiles the size the checkerboard tiles will be
   * @param numTiles    the number of tiles per row/column
   */
  protected void emitCheckerboardDefaultColorEvent(int sizeOfTiles, int numTiles) {
    for (IViewListener listener : listeners) {
      listener.handleCheckerboardDefaultColorEvent(sizeOfTiles, numTiles);
    }
  }

  @Override
  public String toString() {
    return "Number of listeners in this window: " + this.listeners.size();
  }
}