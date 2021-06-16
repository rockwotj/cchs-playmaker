package playmaker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import playmaker.fieldpieces.DrawingField;
import playmaker.fieldpieces.Player;
import playmaker.fieldpieces.TextBox;
import playmaker.fieldpieces.Zone;

public class PropertiesFrame {
  private static JFrame edit;
  private static DrawingField field;
  private static JFrame frame;

  public PropertiesFrame(DrawingField field, JFrame frame) {
    this.field = field;
    this.frame = frame;
    edit = new JFrame();
  }

  public static void playerPropertyFrame(final Player selected) {
    JButton okay = createEditFrame("Player Properties");
    JPanel content = new JPanel();
    GridLayout layout = new GridLayout(0, 2, 10, 0);
    content.setLayout(layout);
    content.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
    JLabel shapeLabel = new JLabel(" Shape");
    content.add(shapeLabel);
    JLabel colorLabel = new JLabel(" Color");
    content.add(colorLabel);
    String[] shapes = {"Circle", "Square"};
    final JComboBox<String> isSquare = new JComboBox(shapes);
    if (selected.isCircle()) {
      isSquare.setSelectedIndex(0);
    } else {
      isSquare.setSelectedIndex(1);
    }
    String[] colors = {"White", "Black"};
    final JComboBox<String> isWhite = new JComboBox(colors);
    if (selected.isWhite()) {
      isWhite.setSelectedIndex(0);
    } else {
      isWhite.setSelectedIndex(1);
    }
    content.add(isSquare);
    content.add(isWhite);
    JPanel text = new JPanel();
    text.setLayout(new GridBagLayout());
    final JCheckBox textCheck = new JCheckBox("Text");
    textCheck.setSelected(selected.isTextEnabled());
    text.add(textCheck);
    final JTextField textContent = new JTextField();
    textContent.setColumns(3);
    textContent.setText(selected.getLetter());
    text.add(textContent);
    content.add(text);
    edit.add(content, "North");
    edit.pack();
    edit.setSize(280, 150);
    edit.setVisible(true);
    okay.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            selected.SetSquare(isSquare.getSelectedIndex() == 1);
            selected.setWhite(isWhite.getSelectedIndex() == 0);
            selected.setTextEnabled(textCheck.isSelected());
            selected.setLetter(textContent.getText());
            PropertiesFrame.field.repaint();
            PropertiesFrame.frame.setEnabled(true);
            PropertiesFrame.edit.dispose();
          }
        });
  }

  public static JButton createEditFrame(String title) {
    edit = new JFrame(title);
    edit.setAlwaysOnTop(true);
    frame.setEnabled(false);
    edit.setLocationRelativeTo(field);
    edit.setLayout(new BorderLayout());
    edit.addWindowListener(
        new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
            PropertiesFrame.frame.setEnabled(true);
            PropertiesFrame.edit.dispose();
          }
        });
    JPanel okNo = new JPanel();
    JButton ok = new JButton("Okay");
    JButton no = new JButton("Cancel");
    okNo.add(ok);
    okNo.add(no);
    edit.add(okNo, "South");
    no.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            PropertiesFrame.frame.setEnabled(true);
            PropertiesFrame.edit.dispose();
          }
        });
    return ok;
  }

  public static void textBoxPropertyFrame(final TextBox selected) {
    JButton okay = createEditFrame("TextBox Properties");
    JPanel rightContent = new JPanel();
    rightContent.setLayout(new GridLayout(0, 1));
    JPanel leftContent = new JPanel();
    leftContent.setLayout(new GridLayout(0, 1));
    JLabel textLabel = new JLabel(" Text");
    leftContent.add(textLabel);
    JLabel colorLabel = new JLabel(" Text Color");
    rightContent.add(colorLabel);

    final JTextField text = new JTextField();
    text.setColumns(10);
    text.setText(selected.getText());
    leftContent.add(text);

    final String[] colors = {"Black", "White", "Blue", "Red"};
    final JComboBox<String> fontColor = new JComboBox(colors);
    rightContent.add(fontColor);
    String currentFontColor = selected.getFontColor();
    for (int i = 0; i < 4; i++) {
      if (currentFontColor.equals(colors[i])) {
        fontColor.setSelectedIndex(i);
      }
    }
    JLabel fontLabel = new JLabel(" Font Type");
    leftContent.add(fontLabel);
    JLabel sizeLabel = new JLabel(" Font Size");
    rightContent.add(sizeLabel);

    final String[] fonts = {"Times New Roman", "Lucida Console", "Arial Black", "Georgia"};
    final JComboBox<String> font = new JComboBox(fonts);
    String currentFont = selected.getFont();
    for (int i = 0; i < 4; i++) {
      if (currentFont.equals(fonts[i])) {
        font.setSelectedIndex(i);
      }
    }
    leftContent.add(font);

    JTextField size = new JTextField();
    size.setColumns(3);
    size.setText(selected.getSize());
    rightContent.add(size);

    okay.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            Graphics2D g2 = (Graphics2D) PropertiesFrame.field.getGraphics();
            int inputedSize;
            try {
              inputedSize = Integer.parseInt(selected.getText());
            } catch (NumberFormatException exception) {
              inputedSize = Integer.parseInt(selected.getSize());
            }
            selected.setFontAndSizeAndText(
                fonts[font.getSelectedIndex()], text.getText(), inputedSize, g2);
            selected.setFontColor(colors[fontColor.getSelectedIndex()]);
            PropertiesFrame.field.repaint();
            PropertiesFrame.frame.setEnabled(true);
            PropertiesFrame.edit.dispose();
          }
        });
    rightContent.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 10));
    leftContent.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 5));
    edit.add(rightContent, "East");
    edit.add(leftContent, "West");
    edit.pack();
    edit.setVisible(true);
  }

  public static void zonePropertyFrame(final Zone selected) {
    JButton okay = createEditFrame("Zone Properties");
    JLabel label = new JLabel(" Zone Color     ");
    JPanel content = new JPanel();
    final String[] colors = {"Blue", "Yellow", "Turquoise", "Gold", "Red", "Black"};
    final JComboBox<String> color = new JComboBox(colors);
    String currentColor = selected.getColorString();
    for (int i = 0; i < 6; i++) {
      if (currentColor.equals(colors[i])) {
        color.setSelectedIndex(i);
      }
    }
    content.add(label);
    content.add(color);
    edit.add(content);
    edit.pack();
    edit.setSize(240, 120);
    edit.setVisible(true);
    okay.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            selected.setColorFromString(colors[color.getSelectedIndex()]);
            PropertiesFrame.field.repaint();
            PropertiesFrame.frame.setEnabled(true);
            PropertiesFrame.edit.dispose();
          }
        });
  }

  public static void setDefaultsFrame() {
    JButton okay = createEditFrame("Default Properties");
    JPanel basicProperties = new JPanel();
    JPanel textBoxProperties = new JPanel();
    JPanel playerProperties = new JPanel();
    JPanel zoneProperties = new JPanel();

    JPanel basicContent = new JPanel();
    basicContent.setLayout(new BorderLayout());
    basicContent.add(basicProperties);
    basicProperties.add(new JLabel(" Play name"));
    final JTextField title = new JTextField();
    title.setColumns(10);
    title.setText(Main.fileName);
    basicProperties.add(title);

    basicProperties.add(new JLabel(" Number of Yards"));
    JPanel numYards = new JPanel();
    numYards.setLayout(new GridLayout(0, 1));
    basicProperties.add(numYards);
    final JRadioButton twofive = new JRadioButton("25");
    JRadioButton threefive = new JRadioButton("35");
    if (Main.numYards == 25) {
      twofive.setSelected(true);
    } else {
      threefive.setSelected(true);
    }
    ButtonGroup group = new ButtonGroup();
    group.add(twofive);
    group.add(threefive);
    numYards.add(twofive);
    numYards.add(threefive);

    JPanel textBoxContent = new JPanel();
    textBoxContent.setLayout(new BorderLayout());
    textBoxContent.add(
        new JLabel(
            "     Font Size                   Font Style                          Font Color"));
    textBoxContent.add(textBoxProperties, "South");
    final JTextField fontsize = new JTextField();
    fontsize.setColumns(3);
    // fontsize.setFont(Main.defaultFontSize);
    textBoxProperties.add(fontsize);
    textBoxProperties.add(new JLabel("       "));
    final String[] fonts = {"Times New Roman", "Lucida Console", "Arial Black", "Georgia"};
    final JComboBox<String> font = new JComboBox(fonts);
    String currentFont = Main.defaultFontStyle;
    for (int i = 0; i < 4; i++) {
      if (currentFont.equals(fonts[i])) {
        font.setSelectedIndex(i);
      }
    }
    textBoxProperties.add(font);
    textBoxProperties.add(new JLabel("       "));
    final String[] colors = {"Black", "White", "Blue", "Red"};
    final JComboBox<String> fontColor = new JComboBox(colors);
    textBoxProperties.add(fontColor);
    String currentFontColor = getColorString(Main.defaultTextColor);
    for (int i = 0; i < 4; i++) {
      if (currentFontColor.equals(colors[i])) {
        fontColor.setSelectedIndex(i);
      }
    }
    JPanel playerContent = new JPanel();
    playerContent.setLayout(new BorderLayout());
    playerContent.add(playerProperties);
    playerProperties.add(new JLabel(" Shape"));
    String[] shapes = {"Circle", "Square"};
    final JComboBox<String> isSquare = new JComboBox(shapes);
    if (Main.defaultIsCircle) {
      isSquare.setSelectedIndex(0);
    } else {
      isSquare.setSelectedIndex(1);
    }
    String[] shapeColors = {"White", "Black"};
    final JComboBox<String> isWhite = new JComboBox(shapeColors);
    if (Main.defaultIsWhite) {
      isWhite.setSelectedIndex(0);
    } else {
      isWhite.setSelectedIndex(1);
    }
    playerProperties.add(isSquare);
    playerProperties.add(new JLabel(" Color"));
    playerProperties.add(isWhite);

    JPanel zoneContent = new JPanel();
    zoneContent.setLayout(new BorderLayout());
    zoneContent.add(zoneProperties);
    zoneProperties.add(new JLabel(" Color"));
    final String[] zoneColors = {"Blue", "Yellow", "Turquoise", "Gold", "Red", "Black"};
    final JComboBox<String> zoneColor = new JComboBox(zoneColors);
    String currentColor = getColorString(Main.defaultZoneColor);
    for (int i = 0; i < 6; i++) {
      if (currentColor.equals(zoneColors[i])) {
        zoneColor.setSelectedIndex(i);
      }
    }
    zoneProperties.add(zoneColor);
    JPanel content = new JPanel();
    content.setLayout(new GridLayout(2, 2));
    basicContent.setBorder(BorderFactory.createTitledBorder("Basic Properties"));
    playerContent.setBorder(BorderFactory.createTitledBorder("Player Properties"));
    textBoxContent.setBorder(BorderFactory.createTitledBorder("Text Box Properties"));
    zoneContent.setBorder(BorderFactory.createTitledBorder("Zone Properties"));
    content.add(basicContent);
    content.add(playerContent);
    content.add(textBoxContent);
    content.add(zoneContent);
    edit.add(content);
    edit.pack();
    edit.setVisible(true);
    okay.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Main.fileName = title.getText();
            if (twofive.isSelected()) {
              Main.numYards = 25;
            } else {
              Main.numYards = 35;
            }
            Main.defaultFontStyle = fonts[font.getSelectedIndex()];
            try {
              Main.defaultFontSize = Integer.parseInt(fontsize.getText());
            } catch (NumberFormatException localNumberFormatException) {
            }
            Main.defaultTextColor =
                PropertiesFrame.getColorFromString(colors[fontColor.getSelectedIndex()]);
            Main.defaultIsCircle = isSquare.getSelectedIndex() == 0;
            Main.defaultIsWhite = isWhite.getSelectedIndex() == 0;
            Main.defaultZoneColor =
                PropertiesFrame.getColorFromString(zoneColors[zoneColor.getSelectedIndex()]);
            Main.setFrameTitle();
            LoadSaveHandler.saveProperties();
            PropertiesFrame.frame.setEnabled(true);
            PropertiesFrame.edit.dispose();
          }
        });
  }

  private static Color getColorFromString(String line) {
    if (line.equals("Black")) {
      return Color.BLACK;
    }
    if (line.equals("Blue")) {
      return Color.BLUE;
    }
    if (line.equals("White")) {
      return Color.WHITE;
    }
    if (line.equals("Yellow")) {
      return Color.YELLOW;
    }
    if (line.equals("Turquoise")) {
      return Color.CYAN;
    }
    if (line.equals("Gold")) {
      return Color.ORANGE;
    }
    if (line.equals("Red")) {
      return Color.RED;
    }
    return null;
  }

  private static String getColorString(Color color) {
    if (color.equals(Color.BLACK)) {
      return "Black";
    }
    if (color.equals(Color.BLUE)) {
      return "Blue";
    }
    if (color.equals(Color.WHITE)) {
      return "White";
    }
    if (color.equals(Color.YELLOW)) {
      return "Yellow";
    }
    if (color.equals(Color.CYAN)) {
      return "Turquoise";
    }
    if (color.equals(Color.ORANGE)) {
      return "Gold";
    }
    if (color.equals(Color.RED)) {
      return "Red";
    }
    return null;
  }
}

/*
 * Location: D:\Software\Mine\CCHS-Playmaker-master\CCHS-Playmaker-master\2.0\
 * FootballPlayMaker.jar
 *
 * Qualified Name: GUI.PropertiesFrame
 *
 * JD-Core Version: 0.7.0.1
 */
