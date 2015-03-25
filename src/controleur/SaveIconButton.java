package controleur;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * @author stephen BATIFOL
 *
 */
public class SaveIconButton extends JButton {

	
	private static ImageIcon icon = new ImageIcon("ImagesMenu/save.png");
	
	public SaveIconButton() {
		this.setSize(30, 30);
		this.setIcon(icon);
		this.setBackground(Color.WHITE);
		this.setMaximumSize(new Dimension(30,30));
		this.setBackground(new Color(238,238,238));
		this.setBorderPainted(false);
		this.setFocusPainted(false);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
	}
}
