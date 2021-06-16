package com.github.rockwotj.playmaker.fieldpieces;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;
import com.github.rockwotj.playmaker.Main;

public class TextBox extends FieldPieces {
  private Font font;
  private TextLayout textBox;
  private int x;
  private int y;
  private String text;
  private int size;
  private String fontStyle;
  private Color textColor;
  private double xPercent;
  private double yPercent;

  public TextBox(int x, int y, Graphics2D g2, String string, double width, double height) {
    this.size = Main.defaultFontSize;
    this.fontStyle = Main.defaultFontStyle;
    this.font = new Font(this.fontStyle, 1, this.size);
    this.text = string;
    this.textBox = new TextLayout(this.text, this.font, g2.getFontRenderContext());
    this.x = x;
    this.y = y;
    this.textColor = Main.defaultTextColor;
    this.xPercent = (x / width);
    this.yPercent = (y / height);
  }

  public TextBox(
      int x,
      int y,
      Graphics2D g2,
      String text,
      String font,
      int size,
      String textColor,
      double width,
      double height) {
    setFontAndSizeAndText(font, text, size, g2);
    this.x = x;
    this.y = y;
    setFontColor(textColor);
    this.xPercent = (x / width);
    this.yPercent = (y / height);
  }

  public void drawOn(Graphics2D g2) {
    if (this.highlighted) {
      Stroke original = g2.getStroke();
      g2.setColor(Color.white);
      Rectangle outline = this.textBox.getPixelBounds(null, this.x, this.y);
      outline.setBounds(this.x - 2, outline.y - 2, outline.width + 5, outline.height + 3);
      g2.draw(outline);
      g2.setStroke(original);
    }
    g2.setColor(this.textColor);
    this.textBox.draw(g2, this.x, this.y);
  }

  public void moveTo(int x, int y, double width, double height) {
    if (Point2D.Double.distance(x, y, this.anchorX, this.anchorY) > 1.0D) {
      int xDiff = x - this.anchorX;
      int yDiff = y - this.anchorY;
      this.x += xDiff;
      this.y += yDiff;
      this.anchorX = x;
      this.anchorY = y;
      this.xPercent = (this.x / width);
      this.yPercent = (this.y / height);
    }
  }

  public boolean contains(int x, int y) {
    return this.textBox.getPixelBounds(null, this.x, this.y).contains(x, y);
  }

  public void setFontAndSizeAndText(String font, String text, int size, Graphics2D g2) {
    this.text = text;
    this.size = size;
    this.fontStyle = font;
    this.font = new Font(this.fontStyle, 1, this.size);
    this.textBox = new TextLayout(this.text, this.font, g2.getFontRenderContext());
  }

  public String getText() {
    return this.text;
  }

  public String getSize() {
    return String.valueOf(this.size);
  }

  public String getFont() {
    return this.fontStyle;
  }

  public void setFontStyle(String fontStyle, Graphics2D g2) {
    this.fontStyle = fontStyle;
    this.font = new Font(this.fontStyle, 1, this.size);
    this.textBox = new TextLayout(this.text, this.font, g2.getFontRenderContext());
  }

  public String getFontColor() {
    if (this.textColor.equals(Color.BLACK)) {
      return "Black";
    }
    if (this.textColor.equals(Color.WHITE)) {
      return "White";
    }
    if (this.textColor.equals(Color.BLUE)) {
      return "Blue";
    }
    return "Red";
  }

  public void setFontColor(String color) {
    if (color.equals("Black")) {
      this.textColor = Color.BLACK;
    } else if (color.equals("White")) {
      this.textColor = Color.WHITE;
    } else if (color.equals("Blue")) {
      this.textColor = Color.BLUE;
    } else {
      this.textColor = Color.RED;
    }
  }

  public String toString() {
    return "TextBox|"
        + this.x
        + "|"
        + this.y
        + "|"
        + this.text
        + "|"
        + this.fontStyle
        + "|"
        + this.size
        + "|"
        + getFontColor();
  }

  public Rectangle getBounds() {
    return this.textBox.getPixelBounds(null, this.x, this.y);
  }

  public void resize(int height, int width) {
    this.x = ((int) (this.xPercent * width));
    this.y = ((int) (this.yPercent * height));
  }
}

/*
 * Location: D:\Software\Mine\CCHS-Playmaker-master\CCHS-Playmaker-master\2.0\
 * FootballPlayMaker.jar
 *
 * Qualified Name: GUI.TextBox
 *
 * JD-Core Version: 0.7.0.1
 */
