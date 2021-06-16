package playmaker.fieldpieces;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;

public class DashedLine extends Line {
  public DashedLine(int x, int y, double width, double height) {
    super(x, y, width, height);
  }

  public DashedLine(int x1, int y1, int x2, int y2, double width, double height) {
    super(x1, y1, width, height);
    moveTail(x2, y2, width, height);
  }

  public void drawOn(Graphics2D g2) {
    Stroke original = g2.getStroke();
    if (this.highlighted) {
      g2.setColor(Color.white);
      g2.setStroke(new BasicStroke(3.5F, 0, 2, 0.0F, new float[] {10.0F}, 0.0F));
      g2.draw(this.bounds);
    }
    Stroke dottedStroke = new BasicStroke(2.0F, 0, 2, 0.0F, new float[] {10.0F}, 0.0F);
    g2.setStroke(dottedStroke);
    g2.setColor(Color.black);
    g2.draw(this.bounds);
    g2.setStroke(original);
  }

  public String toString() {
    Line2D.Double line = (Line2D.Double) this.bounds;
    return "DashedLine|" + line.x1 + "|" + line.y1 + "|" + line.x2 + "|" + line.y2;
  }
}

/*
 * Location: D:\Software\Mine\CCHS-Playmaker-master\CCHS-Playmaker-master\2.0\
 * FootballPlayMaker.jar
 *
 * Qualified Name: GUI.DashedLine
 *
 * JD-Core Version: 0.7.0.1
 */
