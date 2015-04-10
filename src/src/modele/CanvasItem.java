package modele;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.util.ArrayList;

/**
 * @author Nicolas Roussel (roussel@lri.fr)
 * 
 */

public abstract class CanvasItem {

	protected PersistentCanvas canvas;
	protected Color outline, fill;
	protected Shape shape;
	protected Boolean isSelected;
	protected Boolean isInGuide = false;

	public Image background;

	public boolean horizAnimate;
	public boolean verticAnimate;
	public boolean blinkAnimate;
	public boolean resize;
	public boolean neige;
	public boolean vent;
	public int vitesse;

	public static int value = 2;

	public Point firstPoint;

	protected Tag tag;

	Integer stroke;

	public CanvasItem(PersistentCanvas c, Color o, Color f, int vit) {
		canvas = c;
		fill = f;
		outline = o;
		shape = null;
		isSelected = false;
		background = null;
		horizAnimate = false;
		verticAnimate = false;
		blinkAnimate = false;
		resize = false;
		stroke = null;
		vitesse = vit;
	}

	public void setOutlineColor(Color c) {
		outline = c;
		canvas.repaint();
	}

	public void setFillColor(Color c) {
		fill = c;
		System.out.println("setFillColor !!! ");
		canvas.repaint();
	}

	public void select() {
		isSelected = true;
		canvas.repaint();
	}

	public void deselect() {
		isSelected = false;
		canvas.repaint();
	}

	public PersistentCanvas getCanvas() {
		return canvas;
	}

	protected void fillShape(Graphics2D g) {
		// g.setStroke(new BasicStroke(value));
		g.setColor(fill);
		g.fill(shape);
	}

	protected void drawShape(Graphics2D g) {
		Stroke oldstrk = null;
		if (isSelected) {
			oldstrk = g.getStroke();
			g.setStroke(new BasicStroke(2));
			// g.setStroke(new BasicStroke(value));
			// setStroke(value, g);
		}
		g.setColor(outline);
		g.draw(shape);
		if (oldstrk != null)
			g.setStroke(oldstrk);
	}

	public void paint(Graphics2D g) {
		if (background == null) {
			fillShape(g);
			drawShape(g);
		} else {
			g.drawImage(background, (int) shape.getBounds().getMinX(),
					(int) shape.getBounds().getMinY(), null);
			drawShape(g);
		}

	}

	public Boolean contains(Point p) {
		return shape.contains(p);
	}

	public String getColorInterieur() {
		return outline.getRed() + " " + outline.getGreen() + " "
				+ outline.getBlue();
	}

	public String getColorExterieur() {
		return fill.getRed() + " " + fill.getGreen() + " " + fill.getBlue();
	}

	public Boolean getIsInGuide() {
		return isInGuide;
	}

	public void setIsInGuide(Boolean isInGuide) {
		this.isInGuide = isInGuide;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public Tag getTag() {
		return tag;
	}

	public abstract CanvasItem duplicate();

	public abstract void update(Point p);

	public abstract void move(int dx, int dy);

	public abstract String getType();

	public abstract ArrayList<Integer> getPoints();

	public abstract int getMinX();

	public abstract int getMinY();

	public abstract int getWidth();

	public abstract int getHeight();

	public abstract void rotate(int angle);

	public abstract void setY(int y);
}
