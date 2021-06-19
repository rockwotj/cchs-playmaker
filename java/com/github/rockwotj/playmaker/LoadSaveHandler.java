package com.github.rockwotj.playmaker;

import com.github.rockwotj.playmaker.fieldpieces.Arrowhead;
import com.github.rockwotj.playmaker.fieldpieces.Blockhead;
import com.github.rockwotj.playmaker.fieldpieces.CurvedLine;
import com.github.rockwotj.playmaker.fieldpieces.DashedLine;
import com.github.rockwotj.playmaker.fieldpieces.DrawingField;
import com.github.rockwotj.playmaker.fieldpieces.FieldPieces;
import com.github.rockwotj.playmaker.fieldpieces.MotionLine;
import com.github.rockwotj.playmaker.fieldpieces.Player;
import com.github.rockwotj.playmaker.fieldpieces.SolidLine;
import com.github.rockwotj.playmaker.fieldpieces.TextBox;
import com.github.rockwotj.playmaker.fieldpieces.Zone;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LoadSaveHandler {

  private static Path baseSaveDirectory() {
    String installDir = System.getProperty("install.dir");
    if (installDir == null) {
      return Paths.get(System.getProperty("user.home"), "playmaker");
    }
    return Paths.get(installDir, "SavedData");
  }

  public static ArrayList<FieldPieces> load(Graphics2D g2, DrawingField drawingField)
      throws FileNotFoundException {
    JFileChooser inFileChooser = new JFileChooser(baseSaveDirectory().toFile());
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Playmaker Files", "roc");
    inFileChooser.setFileFilter(filter);
    inFileChooser.setAcceptAllFileFilterUsed(false);
    File inFile;
    if (inFileChooser.showOpenDialog(null) == 0) {
      inFile = inFileChooser.getSelectedFile();
    } else {
      return null;
    }
    return loadFile(inFile, g2, drawingField, true);
  }

  public static ArrayList<FieldPieces> loadDefault(Graphics2D g2) throws FileNotFoundException {
    Path path = baseSaveDirectory().resolve("template.roc");
    return loadFile(path.toFile(), g2, null, false);
  }

  public static void save(ArrayList<FieldPieces> pieces, int width, int height) {

    Path path = baseSaveDirectory().resolve(Main.fileName + ".roc");
    PrintWriter out;
    try {
      out = new PrintWriter(path.toFile());
    } catch (FileNotFoundException e) {
      return;
    }
    save(pieces, out, path.toFile(), width, height);
    out.close();
  }

  public static void saveAs(ArrayList<FieldPieces> pieces, int width, int height) {

    String path = "SavedData\\";
    JFileChooser outFileChooser = new JFileChooser(path);
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Playmaker Files", "roc");
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
    for (FieldPieces piece : pieces) {
      out.println(piece.toString());
    }
    JOptionPane.showMessageDialog(null, Main.fileName + " saved!", "Cowboy Play Maker", 1);
  }

  private static ArrayList<FieldPieces> loadFile(
      File inFile, Graphics2D g2, DrawingField drawingField, boolean showMessage)
      throws FileNotFoundException {

    ArrayList<FieldPieces> piecesInFile = new ArrayList<>();
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
      switch (type) {
        case "Player":
          piecesInFile.add(playerFromString(line, i, builder, width, height));
          break;
        case "Arrowhead":
          piecesInFile.add(arrowheadFromString(line, i, builder, width, height));
          break;
        case "Blockhead":
          piecesInFile.add(blockheadFromString(line, i, builder, width, height));
          break;
        case "CurvedLine":
          piecesInFile.add(curvedLineFromString(line, i, builder, width, height));
          break;
        case "DashedLine":
          piecesInFile.add(dashedLineFromString(line, i, builder, width, height));
          break;
        case "MotionLine":
          piecesInFile.add(motionLineFromString(line, i, builder, width, height));
          break;
        case "SolidLine":
          piecesInFile.add(solidLineFromString(line, i, builder, width, height));
          break;
        case "TextBox":
          piecesInFile.add(textBoxFromString(line, i, builder, g2, width, height));
          break;
        case "Zone":
          piecesInFile.add(zoneFromString(line, i, builder, width, height));
          break;
        default:
          System.out.println("ERROR!");
          break;
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
        new FileNameExtensionFilter(fileType.toUpperCase() + " Image Files", fileType);
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
}
