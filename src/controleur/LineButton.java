package controleur;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class LineButton extends JButton{

	
	private static final long serialVersionUID = 1L;
	private static ImageIcon icon = new ImageIcon("ImagesMain/Line.png");

	public LineButton() {
		this.setSize(30, 30);
		this.setIcon(icon);
		this.setBackground(Color.WHITE);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
	}
}
