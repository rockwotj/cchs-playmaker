package playmaker.fieldpieces;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;

public class CurvedLine extends Line
{
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private int xCrtl;
	private int yCrtl;
	private double xCrtlPercent;
	private double yCrtlPercent;
	private QuadCurve2D temp;

	public CurvedLine(int x, int y, double width, double height)
	{
		super(x, y, width, height);
		this.x1 = x;
		this.y1 = y;
		this.x2 = x;
		this.y2 = y;
		this.temp = new QuadCurve2D.Double();
		this.x1Percent = (x / width);
		this.x2Percent = (x / width);
		this.y1Percent = (y / height);
		this.y2Percent = (y / height);
	}

	public CurvedLine(int x1, int y1, int xCrtl, int yCrtl, int x2, int y2, double width,
			double height)
	{
		super(x1, y1, width, height);
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.bounds = new QuadCurve2D.Double(x1, y1, xCrtl, yCrtl, x2, y2);
		this.x1Percent = (x1 / width);
		this.x2Percent = (x2 / width);
		this.y1Percent = (y1 / height);
		this.y2Percent = (y2 / height);
		this.xCrtlPercent = (xCrtl / width);
		this.yCrtlPercent = (yCrtl / height);
	}

	public void drawOn(Graphics2D g2)
	{
		Stroke original = g2.getStroke();
		if (this.highlighted)
		{
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke(3.5F));
			g2.draw(this.bounds);
		}
		g2.setStroke(new BasicStroke(2.0F));
		g2.setColor(Color.black);
		g2.draw(this.bounds);
		g2.setStroke(original);
	}

	public void moveTo(int x, int y, double width, double height)
	{
		QuadCurve2D.Double line = (QuadCurve2D.Double) this.bounds;
		if (Point2D.Double.distance(x, y, this.anchorX, this.anchorY) > 1.0D)
		{
			int xDiff = x - this.anchorX;
			int yDiff = y - this.anchorY;
			line.setCurve(line.x1 + xDiff, line.y1 + yDiff, line.ctrlx + xDiff, line.ctrly + yDiff,
					line.x2 + xDiff, line.y2 + yDiff);
			this.anchorX = x;
			this.anchorY = y;
			this.x1 = ((int) line.x1);
			this.y1 = ((int) line.y1);
			this.y2 = ((int) line.y2);
			this.x2 = ((int) line.x2);
			this.xCrtl = ((int) line.ctrlx);
			this.yCrtl = ((int) line.ctrly);
			this.x1Percent = (line.x1 / width);
			this.x2Percent = (line.x2 / width);
			this.y1Percent = (line.y1 / height);
			this.y2Percent = (line.y2 / height);
			this.xCrtlPercent = (line.ctrlx / width);
			this.yCrtlPercent = (line.ctrly / height);
		}
	}

	public void moveTail(int x, int y, double width, double height)
	{
		this.x2 = x;
		this.y2 = y;
		Line2D.Double line = (Line2D.Double) this.bounds;
		line.setLine(line.x1, line.y1, x, y);
		this.x2Percent = (line.x2 / width);
		this.y2Percent = (line.y2 / height);
	}

	public void setControl(int x, int y, double width, double height)
	{
		this.xCrtl = x;
		this.yCrtl = y;
		this.bounds = this.temp;
		QuadCurve2D.Double line = (QuadCurve2D.Double) this.bounds;
		line.setCurve(this.x1, this.y1, this.xCrtl, this.yCrtl, this.x2, this.y2);
		this.xCrtlPercent = (line.ctrlx / width);
		this.yCrtlPercent = (line.ctrly / height);
	}

	public boolean contains(int x, int y)
	{
		if ((this.bounds instanceof QuadCurve2D.Double))
		{
			QuadCurve2D.Double line = (QuadCurve2D.Double) this.bounds;
			if (line.getFlatness() < 25.0D)
			{
				return Line2D.Double.ptSegDist(line.x1, line.y1, line.x2, line.y2, x, y) < 25.0D;
			}
			return line.contains(x, y);
		}
		Line2D.Double line = (Line2D.Double) this.bounds;
		return line.ptSegDist(x, y) < 5.0D;
	}

	public String toString()
	{
		QuadCurve2D.Double line = (QuadCurve2D.Double) this.bounds;
		return "CurvedLine|" + line.x1 + "|" + line.y1 + "|" + line.ctrlx + "|" + line.ctrly + "|"
				+ line.x2 + "|" + line.y2;
	}

	public void resize(int height, int width)
	{
		this.x1 = ((int) (this.x1Percent * width));
		this.y1 = ((int) (this.y1Percent * height));
		this.x2 = ((int) (this.x2Percent * width));
		this.y2 = ((int) (this.y2Percent * height));
		this.xCrtl = ((int) (this.xCrtlPercent * width));
		this.yCrtl = ((int) (this.yCrtlPercent * height));
		QuadCurve2D.Double line = (QuadCurve2D.Double) this.bounds;
		line.setCurve(this.x1, this.y1, this.xCrtl, this.yCrtl, this.x2, this.y2);
	}
}

/*
 * Location: D:\Software\Mine\CCHS-Playmaker-master\CCHS-Playmaker-master\2.0\
 * FootballPlayMaker.jar
 * 
 * Qualified Name: GUI.CurvedLine
 * 
 * JD-Core Version: 0.7.0.1
 */