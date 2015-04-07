package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * @author Thibault Soret & stephen Batifol
 *
 */
public class Tutorial extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton jButton1;
	private JButton jButton2;
	private JLabel jLabel1;
	Color backgroundColor = new Color(61, 120, 180);
	JTextArea jTextArea1;
	JScrollPane jScrollPane1;
	BufferedImage toolbarDrawingImage;
	BufferedImage toolbarAnimationsImage;
	BufferedImage pieMenuDrawings;
	BufferedImage pieMenuAnimations;

	public Tutorial() {
		// this.setUndecorated(true);
		this.setVisible(true);
		// this.setSize(new Dimension(1400, 900));
		this.setLocation(170, 0);
		// this.getContentPane().setBackground(backgroundColor);
		// initComponents();

		try {
			toolbarDrawingImage = ImageIO.read(new File(
					"./ImagesTuto/toolbar.png"));
			toolbarAnimationsImage = ImageIO.read(new File(
					"./ImagesTuto/toolbar_animations.png"));
			pieMenuDrawings = ImageIO.read(new File(
					"./ImagesTuto/PieMenu_animations.png"));
			pieMenuAnimations = ImageIO.read(new File(
					"./ImagesTuto/PieMenu_Dessin.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setVisible(true);
		setSize(1050, 400);
		// initTutoToolbar();
		initTutoToolbar2();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void initTutoToolbar() {
		setVisible(true);
		setSize(800, 800);
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();
		jButton1 = new JButton("Next");
		jButton2 = new JButton("Skip");
		try {
			toolbarDrawingImage = ImageIO.read(new File(
					"./ImagesTuto/toolbar.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// setBackground(new java.awt.Color(61, 120, 180));

		jTextArea1.setColumns(20);
		jTextArea1.setRows(5);
		jTextArea1
				.setText("For the toolbar, there are two parts. \nThe first part is Drawings, the second one is Animations. In drawings, you can draw some basic shapes,\n you can change the color of an object, you can also delete an object, clone it or resize it.\nIn Animations, you can choose to anime an object horizontally or vertically, you can make it blink too.\nIt is also possible to add some decorations, you can add wind and / or snow as decorations.\n");
		jScrollPane1.setViewportView(jTextArea1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(jScrollPane1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										708,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addGap(0, 0, Short.MAX_VALUE)
								.addComponent(jButton1)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jButton2)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(101, 101, 101)
								.addComponent(jScrollPane1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										104,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										304, Short.MAX_VALUE)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jButton1)
												.addComponent(jButton2))
								.addContainerGap()));

		jButton1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				initTutoToolbar2();

			}
		});
		jButton2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		// getContentPane().add(imagePanel);
		pack();
	}

	public void initTutoToolbar2() {
		getContentPane().removeAll();
		JPanel mainPanel = new JPanel();
		GridBagConstraints gb = new GridBagConstraints();
		gb.anchor = GridBagConstraints.CENTER;
		gb.gridheight = 1;
		gb.gridwidth = 1;
		gb.gridx = 0;
		gb.gridy = 0;
		mainPanel.setLayout(new GridBagLayout());
		// mainPanel.setLayout(new GridLayout(3, 1));

		JPanel imagePanel = new JPanel();
		JLabel imageLabel = new JLabel(new ImageIcon(toolbarDrawingImage));
		imagePanel.add(imageLabel);
		mainPanel.add(imagePanel, gb);
		JTextArea textArea = new JTextArea();
		textArea.setBackground(mainPanel.getBackground());
		textArea.setText("For the toolbar, there are two parts. \nThe first part is Drawings, the second one is Animations. In drawings, you can draw some basic shapes,\nyou can change the color of an object, you can also delete an object, clone it or resize it.\n");
		gb.gridy = 1;
		gb.insets = new Insets(10, 0, 10, 0);
		mainPanel.add(textArea, gb);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		JButton backButton = new JButton("<- Back");
		JButton nextButton = new JButton("Next ->");
		JButton skipButton = new JButton("Skip");
		JPanel duoButtonPanel = new JPanel();
		duoButtonPanel.setLayout(new GridLayout(1, 3));
		duoButtonPanel.add(backButton, 0);
		duoButtonPanel.add(nextButton, 1);
		duoButtonPanel.add(skipButton, 2);
		buttonPanel.add(duoButtonPanel, BorderLayout.LINE_END);
		gb.anchor = GridBagConstraints.SOUTHEAST;
		gb.gridy = 2;
		mainPanel.add(buttonPanel, gb);
		getContentPane().add(mainPanel);

		nextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				initTutoToolbar3();
			}
		});
		this.revalidate();
		// getContentPane().setBackground(backgroundColor);

	}

	public void initTutoToolbar3() {
		getContentPane().removeAll();
		JPanel mainPanel = new JPanel();
		GridBagConstraints gb = new GridBagConstraints();
		gb.anchor = GridBagConstraints.CENTER;
		gb.gridheight = 1;
		gb.gridwidth = 1;
		gb.gridx = 0;
		gb.gridy = 0;
		mainPanel.setLayout(new GridBagLayout());
		// mainPanel.setLayout(new GridLayout(3, 1));

		JPanel imagePanel = new JPanel();
		JLabel imageLabel = new JLabel(new ImageIcon(toolbarAnimationsImage));
		imagePanel.add(imageLabel);
		mainPanel.add(imagePanel, gb);
		JTextArea textArea = new JTextArea();
		textArea.setBackground(mainPanel.getBackground());
		textArea.setText("In Animations, you can choose to anime an object horizontally or vertically, you can make it blink too.\nIt is also possible to add some decorations, you can add wind and / or snow as decorations.\n");
		gb.gridy = 1;
		gb.insets = new Insets(10, 0, 10, 0);
		mainPanel.add(textArea, gb);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		JButton backButton = new JButton("<- Back");
		JButton nextButton = new JButton("Next ->");
		JButton skipButton = new JButton("Skip 2");
		JPanel duoButtonPanel = new JPanel();
		duoButtonPanel.setLayout(new GridLayout(1, 3));
		duoButtonPanel.add(backButton, 0);
		duoButtonPanel.add(nextButton, 1);
		duoButtonPanel.add(skipButton, 2);
		buttonPanel.add(duoButtonPanel, BorderLayout.LINE_END);
		gb.anchor = GridBagConstraints.SOUTHEAST;
		gb.gridy = 2;
		mainPanel.add(buttonPanel, gb);
		getContentPane().add(mainPanel);

		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				initTutoToolbar2();
			}
		});
		this.revalidate();
		// getContentPane().setBackground(backgroundColor);

	}

	public void removeAll() {
		this.getContentPane().removeAll();
	}

	public static void main(String[] args) {
//		new Tutorial();
	}

}
