package modele;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class GuideItem extends CanvasItem {

	private double length;

	public GuideItem(PersistentCanvas c, Color o, Color f, Point p, int speed) {
		super(c, o, f, speed);
		length = c.getWidth();
		shape = new Line2D.Double(0, p.y, length, p.y);
		tag = new Tag();
	}

	public GuideItem(GuideItem other) {
		super(other.canvas, other.outline, other.fill, 0);
		Line2D.Double l = (Line2D.Double) other.shape;
		shape = new Line2D.Double(l.x1, l.y1, l.x2, l.y2);
		isSelected = false;
	}

	@Override
	public CanvasItem duplicate() {
		return canvas.addItem(new GuideItem(this));
	}

	@Override
	public void update(Point p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void move(int dx, int dy) {
		Line2D.Double l = (Line2D.Double) shape;
		l.y1 += dy;
		l.y2 += dy;
		canvas.repaint();

	}

	public Boolean contains(Point p) {
		Line2D.Double l = (Line2D.Double) shape;
		return (l.ptSegDist(p) < 4);
	}

	protected void drawShape(Graphics2D g) {
		Stroke oldstrk = g.getStroke();
		Stroke dashed;
		dashed = new BasicStroke(1, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER, 10.0f, new float[] { 10.0f }, 0);
		if (isSelected) {
			dashed = new BasicStroke(2, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_MITER, 10.0f, new float[] { 10.0f }, 0);
		}
		g.setStroke(dashed);
		g.setColor(outline);
		g.draw(shape);

		if (oldstrk != null)
			g.setStroke(oldstrk);
	}

	public Boolean isAttracted(Point p) {
		Line2D.Double l = (Line2D.Double) shape;
		return (l.ptSegDist(p) < 20);
	}

	public double distanceToLine(Point p) {
		Line2D.Double l = (Line2D.Double) shape;
		return l.ptSegDist(p);
	}

	public int getY() {
		Line2D.Double l = (Line2D.Double) shape;
		return (int) l.y1;
	}

	public void setY(int y) {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modele.CanvasItem#getType()
	 */
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "Guide";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modele.CanvasItem#getPoints()
	 */
	@Override
	public ArrayList<Integer> getPoints() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modele.CanvasItem#getMinX()
	 */
	@Override
	public int getMinX() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modele.CanvasItem#getMinY()
	 */
	@Override
	public int getMinY() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modele.CanvasItem#getWidth()
	 */
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modele.CanvasItem#getHeight()
	 */
	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modele.CanvasItem#rotate(int)
	 */
	@Override
	public void rotate(int angle) {
		// TODO Auto-generated method stub
	}
}
