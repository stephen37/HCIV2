package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.Timer;

import modele.CanvasItem;
import modele.ItemAnimation;
import vue.GraphicalEditor;

/**
 * 
 * @author John Doe Modifications Thibault Soret & Stephen Batifol (03-03-2015)
 *
 */
public class PositionAnimation extends ItemAnimation {

	// Caracteristiques
	Timer timer;
	Timer timer2;
	PathAnimation path;
	Point2D location;
	double angle;
	double speed = 3;

	// Constructeur
	public PositionAnimation(CanvasItem item) {
		super(item);
	}

	// Animation horizontale cyclique
	@Override
	public boolean processHorizontal() {
		if (item.getMinX() < GraphicalEditor.canvas.getWidth()) {
			item.move(2 * item.vitesse, 0);
		} else {
			item.move(-(GraphicalEditor.canvas.getWidth() + item.getWidth()), 0);
		}

		return true;
	}

	// Animation verticale cyclique
	@Override
	public boolean processVertical() {
		if (item.getMinY() < GraphicalEditor.canvas.getHeight()) {
			item.move(0, 2 * item.vitesse);

		} else {
			item.move(0,
					-(GraphicalEditor.canvas.getHeight() + item.getHeight()));
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
				item.getCanvas().addItem(GraphicalEditor.selection);
			}
		});
		timer.start();
		timer2.start();
	}

	// Animation de clignotement
	public boolean processBlink() {
		blink();
		return true;
	}

	// Redimensionne un item
	public boolean processResize() {
		return true;
	}

	@Override
	public void resume(int x, int y) {
		item.move(x, y);
	}

}
