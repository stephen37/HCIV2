package modele;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class CercleItem extends CanvasItem {

	Point firstPoint;

	public CercleItem(PersistentCanvas c, Color o, Color f, Point p, int v) {
		super(c, o, f, v);
		shape = new Ellipse2D.Float(p.x, p.y, 0, 0);
		firstPoint = p;
	}

	public CercleItem(CercleItem other) {
		super(other.canvas, other.outline, other.fill, other.vitesse);
		shape = new Ellipse2D.Float(
				(float) ((Ellipse2D.Float) other.shape).getX(),
				(float) ((Ellipse2D.Float) other.shape).getY(),
				(float) ((Ellipse2D.Float) other.shape).getWidth(),
				(float) ((Ellipse2D.Float) other.shape).getHeight());
		isSelected = false;
		firstPoint = other.firstPoint;
	}

	public CanvasItem duplicate() {
		return canvas.addItem(new CercleItem(this));
	}

	public void update(Point p) {
		((Ellipse2D.Float) shape).setFrameFromDiagonal(firstPoint, p);
		canvas.repaint();
	}

	public void modifSelect() {

	}

	public void move(int dx, int dy) {
		((Ellipse2D.Float) shape).x += dx;
		((Ellipse2D.Float) shape).y += dy;
		canvas.repaint();
	}

	public String getType() {
		return "Ellipse";
	}

	public int getPetitRayon() {
		return (int) ((Ellipse2D.Float) shape).getMaxX();
	}

	public int getGrandRayon() {
		return (int) ((Ellipse2D.Float) shape).getMaxY();
	}

	public int getX() {
		return (int) ((Ellipse2D.Float) shape).getX();
	}

	public int getY() {
		return (int) ((Ellipse2D.Float) shape).getY();
	}

	public int getMinX() {
		return (int) ((Ellipse2D.Float) shape).getMinX();
	}

	public int getMinY() {
		return (int) ((Ellipse2D.Float) shape).getMinY();
	}

	public int getWidth() {
		return (int) ((Ellipse2D.Float) shape).getWidth();
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return (int) ((Ellipse2D.Float) shape).getHeight();
	}

	@Override
	public ArrayList<Integer> getPoints() {
		// TODO Auto-generated method stub
		return null;
	}

	public void rotate(int angle) {
		((Ellipse2D) shape).getPathIterator(AffineTransform
				.getRotateInstance(Math.toRadians(angle)));

		// System.out.println("Ch'ui la");
		canvas.repaint();
	}
}
