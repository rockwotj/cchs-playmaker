package playmaker.fieldpieces;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import playmaker.LoadSaveHandler;
import playmaker.Main;

public class DrawingField extends JComponent {
  private int width;
  private int height;
  private ArrayList<FieldPieces> pieces;
  public FieldPieces selected;

  public DrawingField(int numYardsToBeShown) {
    this.pieces = new ArrayList<FieldPieces>();
    this.selected = null;
    setFocusable(true);
  }

  public void addPlayer(int x, int y) {
    Player playerToAdd =
        new Player(x, y, Main.numYards, this.width, this.height, (Graphics2D) getGraphics());
    this.pieces.add(playerToAdd);
    repaint();
    playerToAdd.setComponentDimenisons(this.height, this.width);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    this.width = getWidth();
    this.height = getHeight();
    g2.setColor(new Color(34, 139, 34));
    g2.fillRect(0, 0, this.width, this.height);

    g2.setColor(Color.BLACK);
    for (int i = 0; i <= Main.numYards; i++) {
      g2.drawLine(
          0, this.height / Main.numYards * i, this.width / 100, this.height / Main.numYards * i);
      g2.drawLine(
          this.width,
          this.height / Main.numYards * i,
          this.width - this.width / 100,
          this.height / Main.numYards * i);
      g2.drawLine(
          this.width / 3,
          this.height / Main.numYards * i,
          this.width / 3 + this.width / 100,
          this.height / Main.numYards * i);
      g2.drawLine(
          2 * this.width / 3,
          this.height / Main.numYards * i,
          2 * this.width / 3 + this.width / 100,
          this.height / Main.numYards * i);
      if (i % 5 == 0) {
        if ((Main.numYards == 25) && (i == 15)) {
          drawArrow(g2, 5 * this.width / 6 + 4, this.height / Main.numYards * i - 28);
          g2.drawRect(5 * this.width / 6, this.height / Main.numYards * i - 25, 30, 20);
          g2.drawRect(5 * this.width / 6 + 3, this.height / Main.numYards * i - 22, 24, 14);
          drawRightOne(g2, 5 * this.width / 6, this.height / Main.numYards * i + 5, 30, 20);

          drawArrow(g2, this.width / 6 - 11, this.height / Main.numYards * i - 28);
          g2.drawRect(this.width / 6 - 30, this.height / Main.numYards * i + 5, 30, 20);
          g2.drawRect(this.width / 6 - 27, this.height / Main.numYards * i + 8, 24, 14);
          drawLeftOne(g2, this.width / 6 - 30, this.height / Main.numYards * i - 25, 30, 20);
        }
        g2.drawLine(
            0, this.height / Main.numYards * i, this.width, this.height / Main.numYards * i);
        g2.drawLine(
            this.width / 3 + this.width / 100,
            this.height / Main.numYards * i - this.height / (3 * Main.numYards),
            this.width / 3 + this.width / 100,
            this.height / Main.numYards * i + this.height / (3 * Main.numYards));
        g2.drawLine(
            2 * this.width / 3,
            this.height / Main.numYards * i - this.height / (3 * Main.numYards),
            2 * this.width / 3,
            this.height / Main.numYards * i + this.height / (3 * Main.numYards));
      }
    }
    for (int i = 0; i < this.pieces.size(); i++) {
      this.pieces.get(i).drawOn(g2);
    }
  }

  private void drawArrow(Graphics2D g2, int x, int y) {
    g2.drawLine(x, y, x + 8, y);
    g2.drawLine(x + 4, y - 14, x, y);
    g2.drawLine(x + 4, y - 14, x + 8, y);
  }

  private void drawRightOne(Graphics2D g2, int x, int y, int width, int height) {
    g2.drawLine(x + width, y, x + width, y + height);
    g2.drawLine(x + width - 3, y, x + width - 3, y + 8);
    g2.drawLine(x + width - 3, y + height, x + width - 3, y + 12);
    g2.drawLine(x + width, y, x + width - 3, y);
    g2.drawLine(x + width, y + height, x + width - 3, y + height);
    g2.drawLine(x, y + 8, x + width - 3, y + 8);
    g2.drawLine(x + 8, y + 12, x + width - 3, y + 12);
    g2.drawLine(x + 8, y + 12, x + 8, y + 15);
    g2.drawLine(x, y + 12, x + 8, y + 15);
    g2.drawLine(x, y + 12, x, y + 8);
  }

  private void drawLeftOne(Graphics2D g2, int x, int y, int width, int height) {
    g2.drawLine(x, y, x, y + height);
    g2.drawLine(x + 3, y, x + 3, y + 8);
    g2.drawLine(x + 3, y + height, x + 3, y + 12);
    g2.drawLine(x, y, x + 3, y);
    g2.drawLine(x, y + height, x + 3, y + height);
    g2.drawLine(x + 3, y + 12, x + width, y + 12);
    g2.drawLine(x + width - 8, y + 8, x + 3, y + 8);
    g2.drawLine(x + width - 8, y + 8, x + width - 8, y + 4);
    g2.drawLine(x + width - 8, y + 4, x + width, y + 8);
    g2.drawLine(x + width, y + 12, x + width, y + 8);
  }

  public void findPieceAt(int x, int y) {
    if (this.selected != null) {
      this.selected.isHighlighted(false);
    }
    this.selected = null;
    for (int i = this.pieces.size() - 1; i >= 0; i--) {
      if (this.pieces.get(i).contains(x, y)) {
        this.selected = (this.pieces.get(i));
        this.selected.isHighlighted(true);
        this.selected.setAnchor(x, y);
        break;
      }
    }
    repaint();
  }

  public void moveSelectedTo(int x, int y) {
    this.selected.moveTo(x, y, this.width, this.height);
    repaint();
  }

  public void moveTail(int x, int y) {
    Line line = (Line) this.selected;
    line.moveTail(x, y, this.width, this.height);
    repaint();
  }

  public SolidLine addSolid(int x, int y) {
    SolidLine lineToAdd = new SolidLine(x, y, this.width, this.height);
    this.pieces.add(lineToAdd);
    repaint();
    lineToAdd.setComponentDimenisons(this.height, this.width);
    return lineToAdd;
  }

  public DashedLine addDashed(int x, int y) {
    DashedLine lineToAdd = new DashedLine(x, y, this.width, this.height);
    this.pieces.add(lineToAdd);
    repaint();
    lineToAdd.setComponentDimenisons(this.height, this.width);
    return lineToAdd;
  }

  public Arrowhead addArrow(int x, int y) {
    Arrowhead lineToAdd = new Arrowhead(x, y, this.width, this.height);
    this.pieces.add(lineToAdd);
    repaint();
    lineToAdd.setComponentDimenisons(this.height, this.width);
    return lineToAdd;
  }

  public Blockhead addBlock(int x, int y) {
    Blockhead lineToAdd = new Blockhead(x, y, this.width, this.height);
    this.pieces.add(lineToAdd);
    repaint();
    lineToAdd.setComponentDimenisons(this.height, this.width);
    return lineToAdd;
  }

  public MotionLine addMotion(int x, int y) {
    MotionLine lineToAdd = new MotionLine(x, y, this.width, this.height);
    this.pieces.add(lineToAdd);
    repaint();
    lineToAdd.setComponentDimenisons(this.height, this.width);
    return lineToAdd;
  }

  public void addTextBox(int x, int y, String string) {
    if (string.length() > 0) {
      TextBox text = new TextBox(x, y, (Graphics2D) getGraphics(), string, this.width, this.height);
      this.pieces.add(text);
      text.setComponentDimenisons(this.height, this.width);
      repaint();
    }
  }

  public CurvedLine addCurve(int x, int y) {
    CurvedLine lineToAdd = new CurvedLine(x, y, this.width, this.height);
    this.pieces.add(lineToAdd);
    repaint();
    lineToAdd.setComponentDimenisons(this.height, this.width);
    return lineToAdd;
  }

  public void setCurvedControl(int x, int y) {
    CurvedLine line = (CurvedLine) this.selected;
    line.setControl(x, y, this.width, this.height);
    repaint();
  }

  public void unhighlightAll() {
    this.selected = null;
    for (int i = 0; i < this.pieces.size(); i++) {
      this.pieces.get(i).isHighlighted(false);
    }
    repaint();
  }

  public Zone addZone(int x, int y) {
    Zone zone = new Zone(x, y, this.width, this.height);
    this.pieces.add(zone);
    repaint();
    zone.setComponentDimenisons(this.height, this.width);
    return zone;
  }

  public void setEdge(int x, int y) {
    Zone zone = (Zone) this.selected;
    zone.setEdge(x, y, this.width, this.height);
    repaint();
  }

  public void sendToBack() {
    if (this.selected != null) {
      this.pieces.remove(this.selected);
      this.pieces.add(0, this.selected);
      repaint();
    }
  }

  public void bringToFront() {
    if (this.selected != null) {
      this.pieces.remove(this.selected);
      this.pieces.add(this.selected);
      repaint();
    }
  }

  public void removeSelected() {
    if (this.selected != null) {
      this.pieces.remove(this.selected);
      this.selected = null;
      repaint();
    }
  }

  public void saveAs() {
    LoadSaveHandler.saveAs(this.pieces, this.width, this.height);
  }

  public void save() {
    LoadSaveHandler.save(this.pieces, this.width, this.height);
  }

  public void load() {
    ArrayList<FieldPieces> temp = null;
    try {
      temp = LoadSaveHandler.load((Graphics2D) getGraphics(), this);
    } catch (FileNotFoundException e) {
      JOptionPane.showMessageDialog(this, "Error: Load failed!", "Error", 0);
      return;
    }
    if (temp != null) {
      this.pieces = temp;
    } else if (this.pieces == null) {
      try {
        this.pieces = LoadSaveHandler.loadDefault((Graphics2D) getGraphics());
      } catch (FileNotFoundException e) {
        JOptionPane.showMessageDialog(this, "Error: No template found!", "Error", 0);
      }
    }
    resizePieces();
  }

  public void exportAsGIF() {
    boolean show = LoadSaveHandler.exportAs(this, "gif");
    if (show) {
      JOptionPane.showMessageDialog(
          this, Main.fileName + " exported as picture!", "Cowboy Play Maker", 1);
    }
  }

  public void exportAsJPEG() {
    boolean show = LoadSaveHandler.exportAs(this, "jpeg");
    if (show) {
      JOptionPane.showMessageDialog(
          this, Main.fileName + " exported as picture!", "Cowboy Play Maker", 1);
    }
  }

  public void exportAsPNG() {
    boolean show = LoadSaveHandler.exportAs(this, "png");
    if (show) {
      JOptionPane.showMessageDialog(
          this, Main.fileName + " exported as picture!", "Cowboy Play Maker", 1);
    }
  }

  public void loadDefault() {
    try {
      this.pieces = LoadSaveHandler.loadDefault((Graphics2D) getGraphics());
    } catch (FileNotFoundException e) {
      JOptionPane.showMessageDialog(this, "Error: No template found!", "Error", 0);
    }
    resizePieces();
  }

  public void resizePieces() {
    this.height = getHeight();
    this.width = getWidth();
    for (FieldPieces p : this.pieces) {
      p.resize(this.height, this.width);
      p.setComponentDimenisons(this.height, this.width);
    }
    repaint();
  }
}

/*
 * Location: D:\Software\Mine\CCHS-Playmaker-master\CCHS-Playmaker-master\2.0\
 * FootballPlayMaker.jar
 *
 * Qualified Name: GUI.DrawingField
 *
 * JD-Core Version: 0.7.0.1
 */
