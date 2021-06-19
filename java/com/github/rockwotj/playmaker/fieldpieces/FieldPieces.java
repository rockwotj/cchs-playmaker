package com.github.rockwotj.playmaker.fieldpieces;

import java.awt.Graphics2D;
import java.awt.Shape;

public abstract class FieldPieces {
  Shape bounds;
  boolean highlighted;
  int anchorX;
  int anchorY;
  int fieldWidth;
  int fieldHeight;

  public abstract void drawOn(Graphics2D paramGraphics2D);

  public boolean contains(int x, int y) {
    return this.bounds.contains(x, y);
  }

  public void setAnchor(int x, int y) {
    this.anchorX = x;
    this.anchorY = y;
  }

  public void isHighlighted(boolean isHighlighted) {
    this.highlighted = isHighlighted;
  }

  public abstract void resize(int paramInt1, int paramInt2);

  public void setComponentDimenisons(int height, int width) {
    this.fieldHeight = height;
    this.fieldWidth = width;
  }

  public abstract void moveTo(
      int paramInt1, int paramInt2, double paramDouble1, double paramDouble2);
}
