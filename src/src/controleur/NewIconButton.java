/**
 * 
 */
package controleur;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * @author stephen BATIFOL
 *
 */
@SuppressWarnings("serial")
public class NewIconButton extends JButton {

	private static ImageIcon icon = new ImageIcon("ImagesMenu/window_new.png");

	public NewIconButton() {
		this.setSize(30, 30);
		this.setIcon(icon);
		this.setBackground(Color.WHITE);
		this.setMaximumSize(new Dimension(30, 30));
		this.setBackground(new Color(200, 233, 255));
		this.setBorderPainted(false);
		this.setFocusPainted(false);

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseExited(MouseEvent e) {
				setBorderPainted(false);
				setFocusPainted(false);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setBorderPainted(true);
				setFocusPainted(true);
				setToolTipText("Launch a new Editor.");
			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
	}

}
