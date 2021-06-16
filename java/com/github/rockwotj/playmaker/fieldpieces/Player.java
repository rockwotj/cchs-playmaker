package com.github.rockwotj.playmaker.fieldpieces;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import com.github.rockwotj.playmaker.Main;

public class Player extends FieldPieces {
  private int xCenter;
  private int yCenter;
  private double diameter;
  private boolean isCircle;
  private Color fillColor;
  private double xPercent;
  private double yPercent;
  private String letter;
  private boolean textEnabled;
  private Font font;

  public Player(int x, int y, int NumYardsShown, double width, double height, Graphics2D g2) {
    this.xCenter = x;
    this.yCenter = y;
    this.diameter = (height / NumYardsShown);
    SetSquare(!Main.defaultIsCircle);
    setWhite(Main.defaultIsWhite);
    this.xPercent = (x / width);
    this.yPercent = (y / height);
    this.letter = "";
    setTextEnabled(false);
    this.font = new Font("Arial", 1, (int) this.diameter);
  }

  public Player(
      int x,
      int y,
      double diameter,
      boolean isCircle,
      boolean isWhite,
      String letter,
      double width,
      double height) {
    this.xCenter = x;
    this.yCenter = y;
    this.diameter = diameter;
    SetSquare(!isCircle);
    setWhite(isWhite);
    this.xPercent = (x / width);
    this.yPercent = (y / height);
    this.letter = letter;
    if (letter.equals("")) {
      setTextEnabled(false);
    } else {
      setTextEnabled(true);
    }
    this.font = new Font("Arial", 1, (int) this.diameter);
  }

  public void drawOn(Graphics2D g2) {
    if (this.isCircle) {
      Ellipse2D.Double shape = (Ellipse2D.Double) this.bounds;
      if (this.highlighted) {
        Stroke original = g2.getStroke();
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(1.5F));
        shape.setFrame(
            this.xCenter - this.diameter / 2.0D - 1.0D,
            this.yCenter - this.diameter / 2.0D - 1.0D,
            this.diameter + 3.0D,
            this.diameter + 3.0D);
        g2.draw(shape);
        g2.setStroke(original);
      }
      shape.setFrame(
          this.xCenter - this.diameter / 2.0D,
          this.yCenter - this.diameter / 2.0D,
          this.diameter,
          this.diameter);
      g2.setColor(this.fillColor);
      g2.fill(this.bounds);
      g2.setColor(Color.black);
      g2.draw(this.bounds);
      if (this.textEnabled) {
        if (!isWhite()) {
          g2.setColor(Color.white);
        }
        FontMetrics m = g2.getFontMetrics(this.font);
        g2.setFont(this.font);
        g2.drawString(
            this.letter,
            this.xCenter - m.stringWidth(this.letter) / 2,
            (int) (this.yCenter + this.diameter / 2.75D));
      }
    } else {
      Rectangle2D.Double shape = (Rectangle2D.Double) this.bounds;
      if (this.highlighted) {
        Stroke original = g2.getStroke();
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(2.0F));
        shape.setFrame(
            this.xCenter - this.diameter / 2.0D - 1.0D,
            this.yCenter - this.diameter / 2.0D - 1.0D,
            this.diameter + 3.0D,
            this.diameter + 3.0D);
        g2.draw(shape);

        g2.setStroke(original);
      }
      shape.setFrame(
          this.xCenter - this.diameter / 2.0D,
          this.yCenter - this.diameter / 2.0D,
          this.diameter,
          this.diameter);
      g2.setColor(this.fillColor);
      g2.fill(this.bounds);
      g2.setColor(Color.black);
      g2.draw(this.bounds);
      if (this.textEnabled) {
        if (!isWhite()) {
          g2.setColor(Color.white);
        }
        FontMetrics m = g2.getFontMetrics(this.font);
        g2.setFont(this.font);
        g2.drawString(
            this.letter,
            this.xCenter - m.stringWidth(this.letter) / 2,
            (int) (this.yCenter + this.diameter / 2.75D));
      }
    }
  }

  public void moveTo(int x, int y, double width, double height) {
    this.xCenter = x;
    this.yCenter = y;
    this.xPercent = (x / width);
    this.yPercent = (y / height);
  }

  public boolean isCircle() {
    return this.isCircle;
  }

  public boolean isWhite() {
    return this.fillColor.equals(Color.white);
  }

  public void setWhite(boolean isWhite) {
    if (isWhite) {
      this.fillColor = Color.white;
    } else {
      this.fillColor = Color.black;
    }
  }

  public void SetSquare(boolean isSquare) {
    this.isCircle = (!isSquare);
    if (this.isCircle) {
      this.bounds = new Ellipse2D.Double();
    } else {
      this.bounds = new Rectangle2D.Double();
    }
  }

  public String toString() {
    if (this.textEnabled) {
      return "Player|"
          + this.xCenter
          + "|"
          + this.yCenter
          + "|"
          + this.diameter
          + "|"
          + this.isCircle
          + "|"
          + isWhite()
          + "|"
          + this.letter;
    }
    return "Player|"
        + this.xCenter
        + "|"
        + this.yCenter
        + "|"
        + this.diameter
        + "|"
        + this.isCircle
        + "|"
        + isWhite()
        + "|";
  }

  public void resize(int height, int width) {
    this.xCenter = ((int) (this.xPercent * width));
    this.yCenter = ((int) (this.yPercent * height));
    this.diameter = (height / Main.numYards);
    this.font = new Font("Arial", 1, (int) this.diameter);
  }

  public String getLetter() {
    return this.letter;
  }

  public void setLetter(String letter) {
    this.letter = letter.trim();
  }

  public boolean isTextEnabled() {
    return this.textEnabled;
  }

  public void setTextEnabled(boolean textEnabled) {
    this.textEnabled = textEnabled;
  }
}

/*
 * Location: D:\Software\Mine\CCHS-Playmaker-master\CCHS-Playmaker-master\2.0\
 * FootballPlayMaker.jar
 *
 * Qualified Name: GUI.Player
 *
 * JD-Core Version: 0.7.0.1
 */
