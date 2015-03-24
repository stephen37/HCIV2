package modele;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import vue.GraphicalEditor;
import controleur.Animator;

public class PathItem extends CanvasItem {
	int x, y;
	protected GeneralPath path;
	protected ArrayList<Point> listPoint;

	public PathItem(PersistentCanvas c, Color o, Color f, Point p, int v) {
		super(c, o, f, v);
		path = new GeneralPath();
		path.moveTo(p.x, p.y);
		shape = path;
		x = p.x;
		y = p.y;
		listPoint = new ArrayList<Point>();
		listPoint.add(new Point(x, y));
		// System.out.println(x + " ; " + y);
	}

	public PathItem(PathItem other) {
		super(other.canvas, other.outline, other.fill, other.vitesse);
		shape = new GeneralPath(other.shape);
		isSelected = false;
	}

	public CanvasItem duplicate() {
		return canvas.addItem(new PathItem(this));
	}

	public void update(Point p) {
		GeneralPath path = (GeneralPath) shape;
		path.lineTo(p.x, p.y);
		listPoint.add(new Point(p.x, p.y));
		// System.out.println(p.x + " ; " + p.y);
		canvas.repaint();
	}

	public void move(int dx, int dy) {
		shape = AffineTransform.getTranslateInstance(dx, dy)
				.createTransformedShape(shape);
		for (Point point : listPoint) {
			point.x += dx;
			point.y += dy;
			// System.out.println(point.x + " ; " + point.y);
		}
		canvas.repaint();
	}


	public void paint(Graphics2D g) {
		drawShape(g);
	}

	public String getType() {
		return "Path";
	}

	public ArrayList<Point> getListPoint() {
		return listPoint;
	}

	@Override
	public ArrayList<Integer> getPoints() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(x);
		list.add(y);
		return list;
	}

	public int getMinX() {
		int minX;
		Iterator<Point> iter = getListPoint().iterator();
		minX = iter.next().x;
		while (iter.hasNext()) {
			if (iter.next().x < minX)
				minX = iter.next().x;
		}
		return minX;
	}

	public int getMinY() {
		int minY;
		Iterator<Point> iter = getListPoint().iterator();
		minY = iter.next().y;
		if (iter.next().y < minY)
			minY = iter.next().y;
		return minY;
	}

	public int getWidth() {
		int maxX;
		Iterator<Point> iter = getListPoint().iterator();
		maxX = iter.next().x;
		while (iter.hasNext()) {
			if (iter.next().x > maxX)
				maxX = iter.next().x;
		}
		return maxX - getMinX();
	}
	
	public int getHeight() {
		int maxY;
		Iterator<Point> iter = getListPoint().iterator();
		maxY = iter.next().y;
		while (iter.hasNext()) {
			if (iter.next().y > maxY)
				maxY = iter.next().y;
		}
		return maxY - getMinX();
	}
	
	public void rotate(int angle){
		
	}
}
