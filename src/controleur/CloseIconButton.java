/**
 * 
 */
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
@SuppressWarnings("serial")
public class CloseIconButton extends JButton {

	private static ImageIcon icon = new ImageIcon("ImagesMenu/small_close.png");

	public CloseIconButton() {
		this.setSize(30, 30);
		this.setIcon(icon);
		this.setBackground(Color.WHITE);
		this.setMaximumSize(new Dimension(30, 30));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
	}
}
