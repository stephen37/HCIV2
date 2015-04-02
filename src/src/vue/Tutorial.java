/**
 * 
 */
package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Thibault Soret & stephen Batifol
 *
 */
public class Tutorial extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel mainPanel;
	JPanel buttonPanel;
	JPanel okPanel;

	public Tutorial() {
		this.setUndecorated(true);
		this.setVisible(true);
		this.setSize(new Dimension(1400, 900));
		this.setLocation(170, 0);

		initPanels();
	}

	public void initPanels() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(new Color(61, 133, 255));
		this.add(mainPanel);
		buttonPanel = new JPanel();
		okPanel = new JPanel();
		JButton okButton = new JButton(" OK ");
		JButton skipButton = new JButton("Skip !");
		okPanel.setBackground(new Color(61, 133, 255));
		okPanel.add(okButton);
		okButton.setPreferredSize(new Dimension(80, 30));

		buttonPanel.setLayout(new BorderLayout());
		skipButton.setPreferredSize(new Dimension(80, 30));
		JPanel testPanel = new JPanel();
		testPanel.setBackground(new Color(61, 133, 255));

		testPanel.add(okPanel);
		testPanel.add(skipButton);
		buttonPanel.add(testPanel, BorderLayout.EAST);
		// buttonPanel.add(okPanel, BorderLayout.LINE_END);
		buttonPanel.setBackground(new Color(61, 133, 255));

		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		skipButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
	}
	
	public void initText() {
		
	}
}
