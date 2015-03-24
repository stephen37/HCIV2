package modele;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class LineItem extends CanvasItem {

	Point firstPoint;

	public LineItem(PersistentCanvas c, Color o, Color f, Point p, int v) {
		super(c, o, f, v);
		shape = new Line2D.Float(p, p);
		firstPoint = p;
	}

	public LineItem(LineItem other) {
		super(other.canvas, other.outline, other.fill, other.vitesse);
		shape = new Line2D.Float(((Line2D.Float) other.shape).getP1(),
				((Line2D.Float) other.shape).getP2());
		isSelected = false;
		firstPoint = other.firstPoint;
	}

	public CanvasItem duplicate() {
		return canvas.addItem(new LineItem(this));
	}

	public void update(Point p) {
		((Line2D.Float) shape).setLine(firstPoint, p);
		canvas.repaint();
	}

	public void modifSelect() {

	}

	@Override
	public Boolean contains(Point p) {
		Line2D.Float l = (Line2D.Float) shape;
		return (l.ptSegDist(p) < 4);
	}

	public void move(int dx, int dy) {
		Line2D.Float l = (Line2D.Float) shape;
		l.x1 += dx;
		l.x2 += dx;
		l.y1 += dy;
		l.y2 += dy;
		canvas.repaint();
	}

	public String getType() {
		return "Line";
	}

	public int getP1X() {
		return (int) ((Line2D.Float) shape).x1;
	}

	public int getP1Y() {
		return (int) ((Line2D.Float) shape).y1;
	}

	public int getP2X() {
		return (int) ((Line2D.Float) shape).x2;
	}

	public int getP2Y() {
		return (int) ((Line2D.Float) shape).y2;
	}

	public int getMinX() {
		return (int) Math.min(((Line2D.Float) shape).x1,
				((Line2D.Float) shape).x2);
	}

	public int getMinY() {
		return (int) Math.min(((Line2D.Float) shape).y1,
				((Line2D.Float) shape).y2);
	}

	public int getWidth() {
		return ((int) ((Line2D.Float) shape).getX2() - (int) ((Line2D.Float) shape)
				.getX1());
	}
	
	public int getHeight() {
		return ((int) ((Line2D.Float) shape).getY2() - (int) ((Line2D.Float) shape)
				.getY1());
	}

	@Override
	public ArrayList<Integer> getPoints() {
		// TODO Auto-generated method stub
		return null;
	}

	public void rotate(int angle){
		
	}
}
