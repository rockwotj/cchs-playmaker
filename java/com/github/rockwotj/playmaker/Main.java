package com.github.rockwotj.playmaker;

import com.github.rockwotj.playmaker.fieldpieces.DrawingField;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {
  public static int numYards;
  private static JFrame frame;
  private static DrawingField field;
  public static String defaultFontStyle = "Times New Roman";
  public static int defaultFontSize = 15;
  public static Color defaultTextColor = Color.BLACK;
  public static boolean defaultIsCircle = true;
  public static boolean defaultIsWhite = true;
  public static Color defaultZoneColor = Color.BLUE;
  public static String fileName = "template";
  private static Image frameIcon;
  private static ToolBar optionToolBar;

  public static void main(String[] args) {
    frameIcon = null;
    try {
      frameIcon = ImageIO.read(Main.class.getResourceAsStream("/images/logo.jpg"));
    } catch (IOException localIOException) {
      System.out.println("Load Logo Fail");
    }
    ProgramProperties properties = ProgramProperties.load();
    // TODO: Stop using global state.
    Main.defaultFontStyle = properties.fontStyle();
    Main.defaultFontSize = properties.setFontSize();
    Main.defaultTextColor = properties.textColor();
    Main.defaultIsCircle = properties.playerShape() != PlayerShape.SQUARE;
    Main.defaultIsWhite = !properties.playerColor().equals(Color.BLACK);
    Main.defaultZoneColor = properties.zoneColor();
    frame = new JFrame();
    frame.setIconImage(frameIcon);
    field = new DrawingField(numYards);
    optionToolBar = new ToolBar(field);
    frame.add(optionToolBar, "East");
    frame.add(field);
    HeaderMenu header = new HeaderMenu(field);
    frame.add(header, "North");
    ToolClickHandler clickHandler = new ToolClickHandler(field, optionToolBar);
    MouseMotionListener motionListener =
        new SelectionMotionHandler(field, optionToolBar, clickHandler);
    FieldKeyHandler keyHandler = new FieldKeyHandler(field);
    field.addComponentListener(
        new ComponentAdapter() {
          public void componentResized(ComponentEvent evt) {
            Main.field.resizePieces();
          }
        });
    field.addMouseListener(clickHandler);
    field.addMouseMotionListener(motionListener);
    field.addKeyListener(keyHandler);
    new PropertiesFrame(field, frame);
    new PopupMenu(field);
    frame.setDefaultCloseOperation(0);
    WindowListener exitListener =
        new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
            int confirm =
                JOptionPane.showOptionDialog(
                    null,
                    "Are You Sure to Close the Playmaker?",
                    "Exit Confirmation",
                    0,
                    3,
                    null,
                    null,
                    null);
            if (confirm == 0) {
              System.exit(0);
            }
          }
        };
    frame.addWindowListener(exitListener);
    numYards = 25;
    field.loadDefault();
    setFrameTitle();
    frame.setSize(1000, 20 * numYards + 1);
    frame.setTitle("Cowboy Playmaker 2.0");
    frame.setVisible(true);
  }

  public static void setFrameTitle() {
    optionToolBar.setName();
  }
}
