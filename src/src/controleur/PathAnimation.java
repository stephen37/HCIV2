package controleur;

import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * 
 * @author John Doe Modification Thibault Soret & Stephen Batifol (03-03-2015)
 *
 */
public class PathAnimation {
	
	//Caracteristiques
	Point2D location;
	double angle;
	int index;
	ArrayList<Point2D> points;

	//Constructeur
	public PathAnimation(int x, int y) {
		points = new ArrayList<Point2D>();
		location = new Point2D.Double(x, y);
		addPoint(location);
	}

	public void addPoint(int x, int y) {
		points.add(new Point2D.Double(x, y));
	}

	public void addPoint(Point2D point) {
		points.add(point);
	}

	public double getAngle() {
		return angle;
	}

	public Point2D getLocation() {
		return location;
	}

	// Avance de "amount" pixels le long du chemin "path" ou, s'il n'est pas
	// spécifié, dans la direction spécifiée par "angle".
	// Retourne "vrai" si le bout du chemin est atteint. Faux sinon.
	public boolean progressBy(double amount) {
		int size = points.size();
		if (size == 0) {
			return false;
		}
		double distance = 0d, rest, progress, totalDistance = 0d;
		Point2D point, lastPoint;
		point = lastPoint = points.get(0);
		int i;
		for (i = index; i < size; i++) {
			point = points.get(i);
			distance = location.distance(point);
			totalDistance += distance;
			if (totalDistance > amount) {
				rest = totalDistance - amount;
				progress = (distance - rest) / distance;
				location.setLocation(lastPoint.getX()
						+ (point.getX() - lastPoint.getX()) * progress,
						lastPoint.getY() + (point.getY() - lastPoint.getY())
								* progress);
				index = i;
				angle = Math.atan2(point.getY() - lastPoint.getY(),
						point.getX() - lastPoint.getX());
				return false;
			} else {
				lastPoint = point;
			}
		}
		if (size > 1) {
			lastPoint = points.get(size - 2);
		}
		location.setLocation(point);
		angle = Math.atan2(point.getY() - lastPoint.getY(), point.getX()
				- lastPoint.getX());
		index = size - 1;
		return true;
	}

	public void draw(Graphics2D g2d) {
		long size = points.size();
		if (index < size) {
			GeneralPath path = new GeneralPath();
			path.moveTo(location.getX(), location.getY());
			for (int i = index; i < size; i++) {
				Point2D point = points.get(i);
				path.lineTo(point.getX(), point.getY());
			}
			g2d.draw(path);
		}
	}
}
