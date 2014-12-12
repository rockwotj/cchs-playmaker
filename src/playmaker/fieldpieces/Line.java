package playmaker.fieldpieces;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public abstract class Line extends FieldPieces
{
	protected double x1Percent;
	protected double y1Percent;
	protected double x2Percent;
	protected double y2Percent;

	public Line(int x, int y, double width, double height)
	{
		this.bounds = new Line2D.Double(x, y, x, y);
		this.x1Percent = (x / width);
		this.y1Percent = (y / height);
		this.x2Percent = (x / width);
		this.y2Percent = (y / height);
	}

	public void moveTo(int x, int y, double width, double height)
	{
		Line2D.Double line = (Line2D.Double) this.bounds;
		if (Point2D.Double.distance(x, y, this.anchorX, this.anchorY) > 1.0D)
		{
			int xDiff = x - this.anchorX;
			int yDiff = y - this.anchorY;
			line.setLine(line.x1 + xDiff, line.y1 + yDiff, line.x2 + xDiff, line.y2 + yDiff);
			this.anchorX = x;
			this.anchorY = y;
			this.x1Percent = (line.x1 / width);
			this.y1Percent = (line.y1 / height);
			this.y2Percent = (line.y2 / height);
			this.x2Percent = (line.x2 / width);
		}
	}

	public void moveTail(int x, int y, double width, double height)
	{
		Line2D.Double line = (Line2D.Double) this.bounds;
		line.setLine(line.x1, line.y1, x, y);
		this.x2Percent = (x / width);
		this.y2Percent = (y / height);
	}

	public boolean contains(int x, int y)
	{
		Line2D.Double line = (Line2D.Double) this.bounds;
		return line.ptSegDist(x, y) < 5.0D;
	}

	public void resize(int height, int width)
	{
		Line2D.Double line = (Line2D.Double) this.bounds;
		int x1 = (int) (this.x1Percent * width);
		int y1 = (int) (this.y1Percent * height);
		int x2 = (int) (this.x2Percent * width);
		int y2 = (int) (this.y2Percent * height);
		line.setLine(x1, y1, x2, y2);
	}
}

/*
 * Location: D:\Software\Mine\CCHS-Playmaker-master\CCHS-Playmaker-master\2.0\
 * FootballPlayMaker.jar
 * 
 * Qualified Name: GUI.Line
 * 
 * JD-Core Version: 0.7.0.1
 */