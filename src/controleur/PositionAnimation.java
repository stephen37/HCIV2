package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.Timer;

import modele.CanvasItem;
import modele.ItemAnimation;
import modele.Vent;
import vue.GraphicalEditor;

public class PositionAnimation extends ItemAnimation {

	Timer timer;
	Timer timer2;
	PathAnimation path;
	Point2D location;
	double angle;
	double speed = 3;

	public PositionAnimation(CanvasItem item) {
		super(item);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean processHorizontal() {
		// TODO Auto-generated method stub
		
		if (item.getMinX() < GraphicalEditor.widthWindow) {
			item.move(2 * item.vitesse, 0);

		} else {
			// GraphicalEditor.canvas.addItem(item);
			System.out.println("On revient au départ !");
			item.move(-(GraphicalEditor.widthWindow + item.getWidth()), 0);
		}

		return true;
		/*
		 * Tracé d'un chemin à la main if (path != null) { // L'avion avance sur
		 * le chemin boolean done = path.progressBy(speed);
		 * location.setLocation(path.getLocation()); angle = path.getAngle();
		 * return done; } else { // L'avion avance dans la direction actuelle
		 * double x = Math.cos(angle) * speed + location.getX(); double y =
		 * Math.sin(angle) * speed + location.getY(); location.setLocation(x,
		 * y); return false; }
		 */
	}

	@Override
	public boolean processVertical() {
		// TODO Auto-generated method stub
		System.out.println("item.getMinY()" + item.getMinY());
		if (item.getMinY() < GraphicalEditor.heightWindow) {
			item.move(0, 2 * item.vitesse);

		} else {
			item.move(0, -(GraphicalEditor.heightWindow + item.getHeight()));
		}
		return false;
	}

	public void blinkAnimated() {
		item.getCanvas().repaint();
		this.blink();
	}

	public void blickUnanimated() {
		item.getCanvas().repaint();
		timer.stop();
		timer2.stop();
		item.getCanvas().addItem(GraphicalEditor.selection);
	}

	public void blink() {
		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				item.getCanvas().removeItem(GraphicalEditor.selection);

			}
		});
		timer2 = new Timer(1100, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				item.getCanvas().addItem(GraphicalEditor.selection);

			}
		});
		timer.start();
		timer2.start();
	}

	public boolean processBlink() {
		blink();
		return true;
	}

	public boolean processResize() {
		return true;
	}

	@Override
	public void resume(int x, int y) {
		// TODO Auto-generated method stub
		System.out.println("On revient à la maison les copains !");
		item.move(x, y);
	}

}
