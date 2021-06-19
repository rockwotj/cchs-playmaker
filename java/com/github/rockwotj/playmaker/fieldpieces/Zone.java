package com.github.rockwotj.playmaker.fieldpieces;

import com.github.rockwotj.playmaker.Main;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Zone extends FieldPieces {
  private Color fillColor;
  private Color outlineColor;
  private int x;
  private int y;
  private int anchorX;
  private int anchorY;
  private BasicStroke stroke;
  private double x1Percent;
  private double y1Percent;
  private double x2Percent;
  private double y2Percent;

  public Zone(int x, int y, double width, double height) {
    this.bounds = new Rectangle2D.Double(x, y, 0.0D, 0.0D);
    setColor(Main.defaultZoneColor);
    this.x = x;
    this.y = y;
    this.stroke = new BasicStroke(3.0F, 1, 1);
    this.x1Percent = (x / width);
    this.y1Percent = (y / height);
  }

  public Zone(
      double x,
      double y,
      double width,
      double height,
      String color,
      double componentWidth,
      double componentHeight) {
    this.x = ((int) x);
    this.y = ((int) y);
    setColorFromString(color);
    this.bounds = new Rectangle2D.Double(x, y, width, -height);
    this.stroke = new BasicStroke(3.0F, 1, 1);
    this.x1Percent = (x / componentWidth);
    this.y1Percent = (y / componentHeight);
    this.x2Percent = ((x + width) / componentWidth);
    this.y2Percent = ((y + height) / componentHeight);
  }

  public boolean contains(int x, int y) {
    Rectangle2D.Double zone = (Rectangle2D.Double) this.bounds;
    Rectangle2D bound = zone.getBounds2D();
    bound.setFrame(zone.x - 3.0D, zone.y - 3.0D, zone.width + 6.0D, zone.height + 6.0D);
    return bound.contains(x, y);
  }

  public void setAnchor(int x, int y) {
    this.anchorX = x;
    this.anchorY = y;
  }

  public void drawOn(Graphics2D g2) {
    g2.setColor(this.fillColor);
    g2.fill(this.bounds);
    BasicStroke original = (BasicStroke) g2.getStroke();
    g2.setStroke(this.stroke);
    if (this.highlighted) {
      g2.setColor(Color.white);
    } else {
      g2.setColor(this.outlineColor);
    }
    g2.draw(this.bounds);
    g2.setStroke(original);
  }

  public void moveTo(int x, int y, double width, double height) {
    Rectangle2D.Double zone = (Rectangle2D.Double) this.bounds;
    if (Point2D.Double.distance(x, y, this.anchorX, this.anchorY) > 1.0D) {
      int xDiff = x - this.anchorX;
      int yDiff = y - this.anchorY;
      zone.setFrameFromDiagonal(
          zone.x + xDiff,
          zone.y + yDiff,
          zone.x + zone.width + xDiff,
          zone.y + zone.height + yDiff);
      this.anchorX = x;
      this.anchorY = y;
      this.x1Percent = (zone.x / width);
      this.y1Percent = (zone.y / height);
      this.x2Percent = ((zone.x + zone.width) / width);
      this.y2Percent = ((zone.y + zone.height) / height);
    }
  }

  public void setEdge(int x, int y, double width, double height) {
    Rectangle2D.Double zone = (Rectangle2D.Double) this.bounds;
    zone.setFrameFromDiagonal(this.x, this.y, x, y);
    this.x1Percent = (zone.x / width);
    this.y1Percent = (zone.y / height);
    this.x2Percent = ((zone.x + zone.width) / width);
    this.y2Percent = ((zone.y + zone.height) / height);
  }

  public String getColorString() {
    if (this.outlineColor.equals(Color.BLUE.darker())) {
      return "Blue";
    }
    if (this.outlineColor.equals(Color.YELLOW.darker())) {
      return "Yellow";
    }
    if (this.outlineColor.equals(Color.CYAN.darker())) {
      return "Turquoise";
    }
    if (this.outlineColor.equals(Color.ORANGE.darker())) {
      return "Gold";
    }
    if (this.outlineColor.equals(Color.RED.darker())) {
      return "Red";
    }
    return "Black";
  }

  public void setColorFromString(String color) {
    if (color.equals("Blue")) {
      setColor(Color.BLUE);
    } else if (color.equals("Yellow")) {
      setColor(Color.YELLOW);
    } else if (color.equals("Turquoise")) {
      setColor(Color.CYAN);
    } else if (color.equals("Gold")) {
      setColor(Color.ORANGE);
    } else if (color.equals("Red")) {
      setColor(Color.RED);
    } else {
      setColor(Color.BLACK);
    }
  }

  private void setColor(Color color) {
    this.fillColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 150);
    this.outlineColor = color.darker();
  }

  public String toString() {
    Rectangle2D.Double zone = (Rectangle2D.Double) this.bounds;
    return "Zone|"
        + zone.x
        + "|"
        + zone.y
        + "|"
        + zone.width
        + "|"
        + zone.height
        + "|"
        + getColorString();
  }

  public void resize(int height, int width) {
    Rectangle2D.Double zone = (Rectangle2D.Double) this.bounds;
    double x1 = this.x1Percent * width;
    double x2 = this.x2Percent * width;
    double y1 = this.y1Percent * height;
    double y2 = this.y2Percent * height;
    zone.setFrameFromDiagonal(x1, y1, x2, y2);
  }
}
