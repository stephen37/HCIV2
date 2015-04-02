package modele;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Vent extends CanvasItem {

	private String path = "Images/vent.png";
	private Point firstPoint;
 
	public Vent(PersistentCanvas c, Point p ,int v) {
		super(c, new Color(1f, 0f, 0f, .0f), new Color(1f, 0f, 0f, .0f), v);
		File file = new File(path);
		try {
			background = ImageIO.read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		shape = new Rectangle(p.x, p.y, background.getWidth(null), background.getHeight(null));
		firstPoint = p;
	}

	public CanvasItem duplicate() {
		return null;
	}

	public void update(Point p) {
	}

	public void move(int dx, int dy) {
		((Rectangle) shape).x += dx;
		((Rectangle) shape).y += dy;
		canvas.repaint();
	}

	public String getType() {
		return "Vent";
	}

	public ArrayList<Integer> getPoints() {
		return null;
	}

	public int getMinX() {
		return (int) shape.getBounds().getMinX();
	}

	public int getMinY() {
		return (int) shape.getBounds().getMinY();
	}

	public int getWidth() {
		return (int) shape.getBounds().getWidth();
	}

	public int getHeight() {
		return (int) shape.getBounds().getHeight();
	}

	public void rotate(int angle) {
	}

	@Override
	public void setY(int y) {
		// TODO Auto-generated method stub
		
	}

}
