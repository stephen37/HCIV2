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
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
	Color backgroundColor = new Color(61, 120, 180);
	JTextArea jTextArea1;
	JScrollPane jScrollPane1;
	BufferedImage toolbarDrawingImage;
	BufferedImage toolbarAnimationsImage;
	BufferedImage pieMenuDrawings;
	BufferedImage pieMenuAnimations;
	BufferedImage pieMenu;
	public static Preferences prefs;
	boolean selected = false;

	// Permet de lancer un tutoriel lors du lancement du logiciel.
	// On utilise les préférences afin de savoir si l'utilisateur ne souhaite
	// plus voir le tuto lors du lancement de l'application
	public Tutorial() {

		prefs = Preferences.userNodeForPackage(this.getClass());
		if (prefs.getBoolean("checkbox", true)) {
			System.out.println("checkbox dans pref = true");
			new GraphicalEditor("Editor 2.0", 1400, 900, new ToolBar());
		} else {
			this.setVisible(true);
			this.setLocation(170, 0);

			try {
				toolbarDrawingImage = ImageIO.read(new File(
						"./ImagesTuto/toolbar.png"));
				toolbarAnimationsImage = ImageIO.read(new File(
						"./ImagesTuto/toolbar_animations.png"));
				pieMenuDrawings = ImageIO.read(new File(
						"./ImagesTuto/PieMenu_animations.png"));
				pieMenuAnimations = ImageIO.read(new File(
						"./ImagesTuto/PieMenu_Dessin.png"));
				pieMenu = ImageIO.read(new File(
						"./ImagesTuto/PieMenu_short.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			setVisible(true);
			setSize(1050, 600);
			initTuto();
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
	}

	// Initialise la premiere partie du tutoriel
	public void initTuto() {

		getContentPane().removeAll();
		JPanel mainPanel = new JPanel();
		GridBagConstraints gb = new GridBagConstraints();
		gb.anchor = GridBagConstraints.CENTER;
		gb.gridheight = 1;
		gb.gridwidth = 1;
		gb.gridx = 0;
		gb.gridy = 0;
		mainPanel.setLayout(new GridBagLayout());

		JPanel imagePanel = new JPanel();
		JLabel imageLabel = new JLabel(new ImageIcon(toolbarDrawingImage));
		imagePanel.add(imageLabel);
		mainPanel.add(imagePanel, gb);
		JTextArea textArea = new JTextArea();
		textArea.setBackground(mainPanel.getBackground());
		textArea.setText("Welcome to the tutorial of this software.\n"
				+ "If you want to skip it, press Skip, otherwise press Next");
		textArea.setEditable(false);
		gb.gridy = 1;
		gb.insets = new Insets(10, 0, 10, 0);
		mainPanel.add(textArea, gb);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		JButton nextButton = new JButton("Next ->");
		JButton skipButton = new JButton("Skip");
		JCheckBox checkBox = new JCheckBox("Do not show this screen next time.");
		JPanel checkBoxPanel = new JPanel();
		checkBoxPanel.setLayout(new BorderLayout());
		checkBoxPanel.add(checkBox, BorderLayout.EAST);
		JPanel duoButtonPanel = new JPanel();
		duoButtonPanel.setLayout(new GridLayout(1, 2));
		duoButtonPanel.add(nextButton, 0);
		duoButtonPanel.add(skipButton, 1);
		buttonPanel.add(duoButtonPanel, BorderLayout.LINE_END);
		gb.anchor = GridBagConstraints.SOUTHEAST;
		gb.gridy = 2;
		mainPanel.add(buttonPanel, gb);
		gb.anchor = GridBagConstraints.SOUTHWEST;
		mainPanel.add(checkBoxPanel, gb);
		getContentPane().add(mainPanel);

		checkBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractButton abstractButton = (AbstractButton) e.getSource();
				selected = abstractButton.getModel().isSelected();
				if (selected) {
					prefs.putBoolean("checkbox", true);
					System.out.println(prefs.getBoolean("checkbox", true));
				} else {
					prefs.putBoolean("checkbox", false);
					System.out.println("non selectionné");
				}
			}
		});
		nextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				initTutoToolbar2();
			}
		});
		skipButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				ToolBar tool = new ToolBar();
				new GraphicalEditor("Editor 2.0", 1400, 900, tool);
				tool.setVisible(true);
			}
		});
		this.revalidate();

	}

	// Initialise la seconde partie du tuto
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

		JPanel imagePanel = new JPanel();
		JLabel imageLabel = new JLabel(new ImageIcon(toolbarDrawingImage));
		imagePanel.add(imageLabel);
		mainPanel.add(imagePanel, gb);
		JTextArea textArea = new JTextArea();
		textArea.setBackground(mainPanel.getBackground());
		textArea.setText("For the toolbar, there are two parts. \nThe first part is Drawings, the second one is Animations. In drawings, you can draw some basic shapes,\nyou can change the color of an object, you can also delete an object, clone it or resize it.\n");
		textArea.setEditable(false);
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
				initTutoToolbar3();
			}
		});
		skipButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				ToolBar tool = new ToolBar();
				new GraphicalEditor("Editor 2.0", 1400, 900, tool);
				tool.setVisible(true);
			}
		});
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				initTuto();
			}
		});
		this.revalidate();

	}

	// Initialise la 3eme partie du tuto
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

		JPanel imagePanel = new JPanel();
		JLabel imageLabel = new JLabel(new ImageIcon(toolbarAnimationsImage));
		imagePanel.add(imageLabel);
		mainPanel.add(imagePanel, gb);
		JTextArea textArea = new JTextArea();
		textArea.setBackground(mainPanel.getBackground());
		textArea.setText("In Animations, you can choose to anime an object horizontally or vertically, you can make it blink too.\nIt is also possible to add some decorations, you can add wind and / or snow as decorations.\n");
		textArea.setEditable(false);
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

		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				initTutoToolbar2();
			}
		});

		nextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				initTutoPieMenu();
			}
		});
		skipButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				ToolBar tool = new ToolBar();
				new GraphicalEditor("Editor 2.0", 1400, 900, tool);
				tool.setVisible(true);
			}
		});
		this.revalidate();

	}

	// Initialise la 4ème partie du tuto
	public void initTutoPieMenu() {

		getContentPane().removeAll();
		JPanel mainPanel = new JPanel();
		GridBagConstraints gb = new GridBagConstraints();
		gb.anchor = GridBagConstraints.CENTER;
		gb.gridheight = 1;
		gb.gridwidth = 1;
		gb.gridx = 0;
		gb.gridy = 0;
		mainPanel.setLayout(new GridBagLayout());

		JPanel imagePanel = new JPanel();
		JLabel imageLabel = new JLabel(new ImageIcon(pieMenu));
		imagePanel.add(imageLabel);
		mainPanel.add(imagePanel, gb);
		JTextArea textArea = new JTextArea();
		textArea.setBackground(mainPanel.getBackground());
		textArea.setText("You can get the same tools that you have in the toolbar by using a Pie-Menu, in order to show it, you simply have to do a right click and it will appear.");
		textArea.setEditable(false);
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

		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				initTutoToolbar3();
			}
		});
		nextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				initTutoPieMenu2();
			}
		});
		skipButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				ToolBar tool = new ToolBar();
				new GraphicalEditor("Editor 2.0", 1400, 900, tool);
				tool.setVisible(true);
			}
		});
		this.revalidate();

	}

	// Initialise la 5eme partie du tuto
	public void initTutoPieMenu2() {

		getContentPane().removeAll();
		JPanel mainPanel = new JPanel();
		GridBagConstraints gb = new GridBagConstraints();
		gb.anchor = GridBagConstraints.CENTER;
		gb.gridheight = 1;
		gb.gridwidth = 1;
		gb.gridx = 0;
		gb.gridy = 0;
		mainPanel.setLayout(new GridBagLayout());

		JPanel imagePanel = new JPanel();
		JLabel imageLabel = new JLabel(new ImageIcon(pieMenuAnimations));
		imagePanel.add(imageLabel);
		mainPanel.add(imagePanel, gb);
		JTextArea textArea = new JTextArea();
		textArea.setBackground(mainPanel.getBackground());
		textArea.setText("You can draw some objects the same way as in the toolbar when you go on the Drawing part.");
		textArea.setEditable(false);
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

		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				initTutoPieMenu();
			}
		});

		nextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				initTutoPieMenu3();
			}
		});
		skipButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				ToolBar tool = new ToolBar();
				new GraphicalEditor("Editor 2.0", 1400, 900, tool);
				tool.setVisible(true);
			}
		});
		this.revalidate();
	}

	// Initialise la 6eme partie du tuto
	public void initTutoPieMenu3() {

		getContentPane().removeAll();
		JPanel mainPanel = new JPanel();
		GridBagConstraints gb = new GridBagConstraints();
		gb.anchor = GridBagConstraints.CENTER;
		gb.gridheight = 1;
		gb.gridwidth = 1;
		gb.gridx = 0;
		gb.gridy = 0;
		mainPanel.setLayout(new GridBagLayout());

		JPanel imagePanel = new JPanel();
		JLabel imageLabel = new JLabel(new ImageIcon(pieMenuDrawings));
		imagePanel.add(imageLabel);
		mainPanel.add(imagePanel, gb);
		JTextArea textArea = new JTextArea();
		textArea.setBackground(mainPanel.getBackground());
		textArea.setText("You can animate these objects when you go to the Animation part.");
		textArea.setEditable(false);
		gb.gridy = 1;
		gb.insets = new Insets(10, 0, 10, 0);
		mainPanel.add(textArea, gb);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		JButton backButton = new JButton("<- Back");
		JButton okButton = new JButton("Ok");
		JPanel duoButtonPanel = new JPanel();
		duoButtonPanel.setLayout(new GridLayout(1, 3));
		duoButtonPanel.add(backButton, 0);
		duoButtonPanel.add(okButton, 1);
		buttonPanel.add(duoButtonPanel, BorderLayout.LINE_END);
		gb.anchor = GridBagConstraints.SOUTHEAST;
		gb.gridy = 2;
		mainPanel.add(buttonPanel, gb);
		getContentPane().add(mainPanel);

		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				initTutoPieMenu2();
			}
		});

		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				ToolBar tool = new ToolBar();
				new GraphicalEditor("Editor 2.0", 1400, 900, tool);
				tool.setVisible(true);
			}
		});

		this.revalidate();

	}

	public void removeAll() {
		this.getContentPane().removeAll();
	}

}
