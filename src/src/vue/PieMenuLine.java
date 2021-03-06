package vue;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import modele.CanvasItem;
import modele.PersistentCanvas;

public class PieMenuLine extends CanvasItem {

	Point firstpoint;
	String pathImage = "PieMenu/Pie/Pie4LineTrans.png";

	public PieMenuLine(PersistentCanvas c, Color o, Color f, Point p, int v)
			throws IOException {
		super(c, o, f, v);
		Image img = ImageIO.read(new File(pathImage));
		shape = new Rectangle(p.x, p.y, img.getWidth(null), img.getHeight(null));
		firstpoint = p;
		background = img;
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
		return "PieMenuLine";
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
