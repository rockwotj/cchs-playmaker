package com.github.rockwotj.playmaker.fieldpieces;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class MotionLine extends Line {
  private Line2D.Double tracer;

  public MotionLine(int x, int y, double width, double height) {
    super(x, y, width, height);
    this.tracer = new Line2D.Double();
  }

  public MotionLine(int x1, int y1, int x2, int y2, double width, double height) {
    super(x1, y1, width, height);
    moveTail(x2, y2, width, height);
    this.tracer = new Line2D.Double();
  }

  public void drawOn(Graphics2D g2) {
    Line2D.Double line = (Line2D.Double) this.bounds;
    BasicStroke original = (BasicStroke) g2.getStroke();

    double dy = line.y2 - line.y1;
    double dx = line.x2 - line.x1;
    double dist = Math.sqrt(Math.pow(dx, 2.0D) + Math.pow(dy, 2.0D));
    double theta = Math.atan2(dy, dx);
    double x = line.x1;
    double y = line.y1;
    double tracedDist = 0.0D;
    int count = 5;
    double swiggleyness = 150.0D;
    double rho = theta + swiggleyness;
    while (tracedDist <= dist) {
      if (count >= 12) {
        swiggleyness *= -1.0D;
        rho = theta + swiggleyness;
        count = 0;
      }
      this.tracer.setLine(x, y, x + Math.cos(rho), y + Math.sin(rho));
      if (this.highlighted) {
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(2.75F));
        g2.draw(this.tracer);
      }
      g2.setStroke(new BasicStroke(2.0F));
      g2.setColor(Color.black);
      g2.draw(this.tracer);
      x += Math.cos(rho);
      y += Math.sin(rho);
      tracedDist = Math.sqrt(Math.pow(x - line.x1, 2.0D) + Math.pow(y - line.y1, 2.0D));
      count++;
    }
    g2.setStroke(original);
  }

  public boolean contains(int x, int y) {
    Line2D.Double line = (Line2D.Double) this.bounds;
    return line.ptSegDist(x, y) < 8.0D;
  }

  public String toString() {
    Line2D.Double line = (Line2D.Double) this.bounds;
    return "MotionLine|" + line.x1 + "|" + line.y1 + "|" + line.x2 + "|" + line.y2;
  }
}

/*
 * Location: D:\Software\Mine\CCHS-Playmaker-master\CCHS-Playmaker-master\2.0\
 * FootballPlayMaker.jar
 *
 * Qualified Name: GUI.MotionLine
 *
 * JD-Core Version: 0.7.0.1
 */
