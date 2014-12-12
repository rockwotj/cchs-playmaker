package playmaker.fieldpieces;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Arrowhead extends Line
{
	private static double phi = Math.toRadians(35.0D);
	private static int barb = 8;

	public Arrowhead(int x, int y, double width, double height)
	{
		super(x, y, width, height);
	}

	public Arrowhead(int x1, int y1, int x2, int y2, double width, double height)
	{
		super(x1, y1, width, height);
		moveTail(x2, y2, width, height);
	}

	public void drawOn(Graphics2D g2)
	{
		Line2D.Double line = (Line2D.Double) this.bounds;
		BasicStroke original = (BasicStroke) g2.getStroke();
		if (this.highlighted)
		{
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke(3.5F));
			g2.draw(this.bounds);
		}
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke(2.0F));
		g2.draw(line);
		drawArrowHead(g2, line.getP2(), line.getP1(), Color.black);
		g2.setStroke(original);
	}

	private void drawArrowHead(Graphics2D g2, Point2D tip, Point2D tail, Color color)
	{
		double dy = tip.getY() - tail.getY();
		double dx = tip.getX() - tail.getX();
		double theta = Math.atan2(dy, dx);
		double rho = theta + phi;
		for (int j = 0; j < 2; j++)
		{
			double x = tip.getX() - barb * Math.cos(rho);
			double y = tip.getY() - barb * Math.sin(rho);
			if (this.highlighted)
			{
				g2.setColor(Color.white);
				g2.setStroke(new BasicStroke(3.5F));
				g2.draw(new Line2D.Double(tip.getX(), tip.getY(), x, y));
			}
			g2.setColor(color);
			g2.setStroke(new BasicStroke(2.0F));
			g2.draw(new Line2D.Double(tip.getX(), tip.getY(), x, y));
			rho = theta - phi;
		}
	}

	public String toString()
	{
		Line2D.Double line = (Line2D.Double) this.bounds;
		return "Arrowhead|" + line.x1 + "|" + line.y1 + "|" + line.x2 + "|" + line.y2;
	}
}

/*
 * Location: D:\Software\Mine\CCHS-Playmaker-master\CCHS-Playmaker-master\2.0\
 * FootballPlayMaker.jar
 * 
 * Qualified Name: GUI.Arrowhead
 * 
 * JD-Core Version: 0.7.0.1
 */