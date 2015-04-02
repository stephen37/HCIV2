package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import modele.PersistentCanvas;

/**
 * 
 * @author David Bonnet Modifications 	Jeremie Garcia (15-01-2015) 
 * 										Thibault Soret & Stephen Batifol (03-03-2015)
 *
 */
public class Animator extends JPanel implements ActionListener {

	//Caracteristiques
	private static final long serialVersionUID = 5323355458759714979L;
	private Timer timer;
	private int delay;
	private PersistentCanvas canvas;
	public static int x;
	public static int y;
	PathAnimation path;
	ArrayList<PathAnimation> paths;

	//Constructeur 1
	public Animator() {
		this(30);
	}

	//Constructeur 2
	public Animator(PersistentCanvas canvas) {
		this(50);
		this.canvas = canvas;
	}

	public Animator(int delay) {
		super();
		System.out.println("sdqsd ");
		// Timer
		this.delay = delay;
		timer = new Timer(delay, this);
		// Chemins et avions
		paths = new ArrayList<PathAnimation>();
		// Listeners
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event) {
				System.out
						.println("sqdjk qklsdjklsqj kjsd qlksjd qskldjsq dklqjd sqkldj sqlkdj ");
				// ************ à modifier ************
				path = new PathAnimation(event.getX(), event.getY());
				paths.add(path);
				// ************************************
				repaint();
			}

			public void mouseReleased(MouseEvent event) {
				// ************ à modifier ************
				// ************************************
				repaint();
			}
		});
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent event) {
				path.addPoint(event.getX(), event.getY());
				repaint();
			}

			@Override
			public void mouseMoved(MouseEvent event) {
				// Rien si la souris bouge sans bouton enfoncé
			}
		});
	}

	
	public void start() {
		timer.start();
	}

	public void stop() {
		timer.stop();
	}

	public void setDelay(int delay) {
		this.delay = delay;
		timer.setDelay(delay);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		PersistentCanvas.processAnimation();
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
