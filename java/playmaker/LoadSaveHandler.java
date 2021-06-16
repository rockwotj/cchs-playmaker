package playmaker;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import playmaker.fieldpieces.Arrowhead;
import playmaker.fieldpieces.Blockhead;
import playmaker.fieldpieces.CurvedLine;
import playmaker.fieldpieces.DashedLine;
import playmaker.fieldpieces.DrawingField;
import playmaker.fieldpieces.FieldPieces;
import playmaker.fieldpieces.MotionLine;
import playmaker.fieldpieces.Player;
import playmaker.fieldpieces.SolidLine;
import playmaker.fieldpieces.TextBox;
import playmaker.fieldpieces.Zone;

public class LoadSaveHandler {

  public static ArrayList<FieldPieces> load(Graphics2D g2, DrawingField drawingField)
      throws FileNotFoundException {

    JFileChooser inFileChooser = new JFileChooser("SavedData\\");
    FileNameExtensionFilter filter =
        new FileNameExtensionFilter("Playmaker Files", new String[] {"roc"});
    inFileChooser.setFileFilter(filter);
    inFileChooser.setAcceptAllFileFilterUsed(false);
    File inFile = null;
    if (inFileChooser.showOpenDialog(null) == 0) {
      inFile = inFileChooser.getSelectedFile();
    } else {
      return null;
    }
    return loadFile(inFile, g2, drawingField, true);
  }

  public static ArrayList<FieldPieces> loadDefault(Graphics2D g2) throws FileNotFoundException {

    String path = "SavedData\\template.roc";
    File inFile = new File(path);
    return loadFile(inFile, g2, null, false);
  }

  public static void save(ArrayList<FieldPieces> pieces, int width, int height) {

    String path = "SavedData\\" + Main.fileName + ".roc";
    File outFile = new File(path);
    PrintWriter out = null;
    try {
      out = new PrintWriter(outFile);
    } catch (FileNotFoundException e) {

      return;
    }
    save(pieces, out, outFile, width, height);
    out.close();
  }

  public static void saveAs(ArrayList<FieldPieces> pieces, int width, int height) {

    String path = "SavedData\\";
    JFileChooser outFileChooser = new JFileChooser(path);
    FileNameExtensionFilter filter =
        new FileNameExtensionFilter("Playmaker Files", new String[] {"roc"});
    outFileChooser.setFileFilter(filter);
    outFileChooser.setAcceptAllFileFilterUsed(false);
    PrintWriter out = null;
    File outFile;
    if (outFileChooser.showSaveDialog(null) == 0) {

      if (!outFileChooser.getSelectedFile().getName().endsWith(".roc")) {
        outFile = new File(outFileChooser.getSelectedFile().getPath() + ".roc");
      } else {
        outFile = outFileChooser.getSelectedFile();
      }
      try {
        out = new PrintWriter(outFile);
      } catch (FileNotFoundException exception) {

        return;
      }
    } else {
      return;
    }
    save(pieces, out, outFile, width, height);
    out.close();
  }

  private static void save(
      ArrayList<FieldPieces> pieces, PrintWriter out, File outFile, int width, int height) {

    out.println(outFile.getName().substring(0, outFile.getName().indexOf('.')));
    out.println(Main.numYards);
    out.println(width);
    out.println(height);
    for (int i = 0; i < pieces.size(); i++) {
      out.println(((FieldPieces) pieces.get(i)).toString());
    }
    JOptionPane.showMessageDialog(null, Main.fileName + " saved!", "Cowboy Play Maker", 1);
  }

  private static ArrayList<FieldPieces> loadFile(
      File inFile, Graphics2D g2, DrawingField drawingField, boolean showMessage)
      throws FileNotFoundException {

    ArrayList<FieldPieces> piecesInFile = new ArrayList();
    Scanner in = new Scanner(inFile);
    StringBuilder builder = new StringBuilder();
    Main.fileName = in.nextLine();
    Main.setFrameTitle();
    Main.numYards = Integer.parseInt(in.nextLine());
    int width = Integer.parseInt(in.nextLine());
    int height = Integer.parseInt(in.nextLine());
    while (in.hasNext()) {

      char[] line = in.nextLine().toCharArray();

      int i = 0;
      while (line[i] != '|') {

        builder.append(line[i]);
        i++;
      }
      i++;
      String type = builder.toString();
      clearStringBuilder(builder);
      if (type.equals("Player")) {
        piecesInFile.add(playerFromString(line, i, builder, width, height));
      } else if (type.equals("Arrowhead")) {
        piecesInFile.add(arrowheadFromString(line, i, builder, width, height));
      } else if (type.equals("Blockhead")) {
        piecesInFile.add(blockheadFromString(line, i, builder, width, height));
      } else if (type.equals("CurvedLine")) {
        piecesInFile.add(curvedLineFromString(line, i, builder, width, height));
      } else if (type.equals("DashedLine")) {
        piecesInFile.add(dashedLineFromString(line, i, builder, width, height));
      } else if (type.equals("MotionLine")) {
        piecesInFile.add(motionLineFromString(line, i, builder, width, height));
      } else if (type.equals("SolidLine")) {
        piecesInFile.add(solidLineFromString(line, i, builder, width, height));
      } else if (type.equals("TextBox")) {
        piecesInFile.add(textBoxFromString(line, i, builder, g2, width, height));
      } else if (type.equals("Zone")) {
        piecesInFile.add(zoneFromString(line, i, builder, width, height));
      } else {
        System.out.println("ERROR!");
      }
    }
    if (showMessage) {
      JOptionPane.showMessageDialog(
          drawingField, Main.fileName + " loaded!", "Cowboy Play Maker", 1);
    }
    in.close();
    return piecesInFile;
  }

  private static Color getColorFromString(String line) {

    if (line.equals(Color.BLACK.toString())) {
      return Color.BLACK;
    }
    if (line.equals(Color.BLUE.toString())) {
      return Color.BLUE;
    }
    if (line.equals(Color.WHITE.toString())) {
      return Color.WHITE;
    }
    if (line.equals(Color.YELLOW.toString())) {
      return Color.YELLOW;
    }
    if (line.equals(Color.CYAN.toString())) {
      return Color.CYAN;
    }
    if (line.equals(Color.ORANGE.toString())) {
      return Color.ORANGE;
    }
    if (line.equals(Color.RED.toString())) {
      return Color.RED;
    }
    return null;
  }

  private static FieldPieces playerFromString(
      char[] line, int i, StringBuilder builder, int width, int height) {

    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    int x = Integer.parseInt(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    int y = Integer.parseInt(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    double diameter = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    boolean isCircle = Boolean.parseBoolean(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    boolean isWhite = Boolean.parseBoolean(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (i < line.length) {

      builder.append(line[i]);
      i++;
    }
    String letter = builder.toString();
    clearStringBuilder(builder);
    return new Player(x, y, diameter, isCircle, isWhite, letter, width, height);
  }

  private static void clearStringBuilder(StringBuilder builder) {

    builder.delete(0, builder.length());
  }

  private static FieldPieces zoneFromString(
      char[] line, int i, StringBuilder builder, int compWidth, int compHeight) {

    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    double x = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    double y = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    double width = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    double height = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (i < line.length) {

      builder.append(line[i]);
      i++;
    }
    String color = builder.toString();
    clearStringBuilder(builder);
    return new Zone(x, y, width, height, color, compWidth, compHeight);
  }

  private static FieldPieces textBoxFromString(
      char[] line, int i, StringBuilder builder, Graphics2D g2, int width, int height) {

    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    int x = Integer.parseInt(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    int y = Integer.parseInt(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    String text = builder.toString();
    clearStringBuilder(builder);
    i++;
    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    String fontStyle = builder.toString();
    clearStringBuilder(builder);
    i++;
    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    int size = Integer.parseInt(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (i < line.length) {

      builder.append(line[i]);
      i++;
    }
    String fontColor = builder.toString();
    clearStringBuilder(builder);
    return new TextBox(x, y, g2, text, fontStyle, size, fontColor, width, height);
  }

  private static FieldPieces solidLineFromString(
      char[] line, int i, StringBuilder builder, int width, int height) {

    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    double x1 = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    double y1 = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    double x2 = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (i < line.length) {

      builder.append(line[i]);
      i++;
    }
    double y2 = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    return new SolidLine((int) x1, (int) y1, (int) x2, (int) y2, width, height);
  }

  private static FieldPieces motionLineFromString(
      char[] line, int i, StringBuilder builder, int width, int height) {

    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    double x1 = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    double y1 = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    double x2 = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (i < line.length) {

      builder.append(line[i]);
      i++;
    }
    double y2 = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    return new MotionLine((int) x1, (int) y1, (int) x2, (int) y2, width, height);
  }

  private static FieldPieces dashedLineFromString(
      char[] line, int i, StringBuilder builder, int width, int height) {

    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    double x1 = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    double y1 = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    double x2 = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (i < line.length) {

      builder.append(line[i]);
      i++;
    }
    double y2 = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    return new DashedLine((int) x1, (int) y1, (int) x2, (int) y2, width, height);
  }

  private static FieldPieces curvedLineFromString(
      char[] line, int i, StringBuilder builder, int width, int height) {

    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    double x1 = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    double y1 = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    double xCtrl = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    double yCtrl = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    double x2 = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (i < line.length) {

      builder.append(line[i]);
      i++;
    }
    double y2 = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    return new CurvedLine(
        (int) x1, (int) y1, (int) xCtrl, (int) yCtrl, (int) x2, (int) y2, width, height);
  }

  private static FieldPieces blockheadFromString(
      char[] line, int i, StringBuilder builder, int width, int height) {

    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    double x1 = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    double y1 = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    double x2 = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (i < line.length) {

      builder.append(line[i]);
      i++;
    }
    double y2 = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    return new Blockhead((int) x1, (int) y1, (int) x2, (int) y2, width, height);
  }

  private static FieldPieces arrowheadFromString(
      char[] line, int i, StringBuilder builder, int width, int height) {

    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    double x1 = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    double y1 = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (line[i] != '|') {

      builder.append(line[i]);
      i++;
    }
    double x2 = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    i++;
    while (i < line.length) {

      builder.append(line[i]);
      i++;
    }
    double y2 = Double.parseDouble(builder.toString());
    clearStringBuilder(builder);
    return new Arrowhead((int) x1, (int) y1, (int) x2, (int) y2, width, height);
  }

  public static boolean exportAs(JComponent comp, String fileType) {

    String path = "exported\\";
    JFileChooser outFileChooser = new JFileChooser(path);
    FileNameExtensionFilter filter =
        new FileNameExtensionFilter(
            fileType.toUpperCase() + " Image Files", new String[] {fileType});
    outFileChooser.setFileFilter(filter);
    outFileChooser.setAcceptAllFileFilterUsed(false);
    File outFile;
    if (outFileChooser.showSaveDialog(null) == 0) {

      if (!outFileChooser.getSelectedFile().getName().endsWith("." + fileType)) {
        outFile = new File(outFileChooser.getSelectedFile().getPath() + "." + fileType);
      } else {
        outFile = outFileChooser.getSelectedFile();
      }
    } else {
      return false;
    }
    try {
      BufferedImage image = new BufferedImage(comp.getWidth(), comp.getHeight(), 1);
      Graphics2D graphics2D = image.createGraphics();
      comp.paint(graphics2D);
      ImageIO.write(image, fileType, outFile);
      return true;
    } catch (Exception exception) {
    }
    return false;
  }

  public static void saveProperties() {

    String path = "properties.config";
    File outFile = new File(path);
    PrintWriter out = null;
    try {
      out = new PrintWriter(outFile);
    } catch (FileNotFoundException e) {

      return;
    }
    out.println(Main.defaultFontStyle);
    out.println(Main.defaultFontSize);
    out.println(Main.defaultTextColor.toString());
    out.println(Main.defaultIsCircle);
    out.println(Main.defaultIsWhite);
    out.println(Main.defaultZoneColor);
    out.close();
  }

  public static void loadProperties() throws FileNotFoundException {

    String path = "properties.config";
    File inFile = new File(path);
    Scanner in = new Scanner(inFile);
    Main.defaultFontStyle = in.nextLine();
    Main.defaultFontSize = Integer.parseInt(in.nextLine());
    Main.defaultTextColor = getColorFromString(in.nextLine());
    Main.defaultIsCircle = Boolean.parseBoolean(in.nextLine());
    Main.defaultIsWhite = Boolean.parseBoolean(in.nextLine());
    Main.defaultZoneColor = getColorFromString(in.nextLine());
    in.close();
  }
}

/*
 * Location: D:\Software\Mine\CCHS-Playmaker-master\CCHS-Playmaker-master\2.0\
 * FootballPlayMaker.jar
 *
 * Qualified Name: GUI.LoadSaveHandler
 *
 * JD-Core Version: 0.7.0.1
 */
