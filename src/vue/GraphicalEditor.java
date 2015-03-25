package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import modele.CanvasItem;
import modele.CercleItem;
import modele.ImageItem;
import modele.LineItem;
import modele.Neige;
import modele.PathItem;
import modele.PersistentCanvas;
import modele.PieMenuAnimation;
import modele.PieMenuC;
import modele.PieMenuDessin;
import modele.PieMenuEllipse;
import modele.PieMenuH;
import modele.PieMenuLine;
import modele.PieMenuP;
import modele.PieMenuPath;
import modele.PieMenuRectangle;
import modele.PieMenuV;
import modele.RectangleItem;
import modele.Vent;
import controleur.Animator;
import controleur.CloseIconButton;
import controleur.NewIconButton;
import controleur.OpenIconButton;
import controleur.RedoIconButton;
import controleur.SaveIconButton;
import controleur.ToolBoxIconButton;
import controleur.UndoIconButton;

/**
 * @author Nicolas Roussel (roussel@lri.fr) Modified by Cedric Fleury
 *         (cfleury@lri.fr) - 18.10.2013 Nouvelles modifications par Stephen
 *         Batifol
 */

@SuppressWarnings({ "unused", "resource", "static-access", "serial",
		"unchecked" })
public class GraphicalEditor extends JFrame implements DropTargetListener,
		Serializable, KeyListener {

	// Graphical Interface
	// Représente la liste des boutons permettant de faire des opérations.
	public static ArrayList<JButton> operations;

	private Point mousepos; // Stores the previous mouse position
	private Point mousePosition;

	protected static String title; // Changes according to the mode

	public static PersistentCanvas canvas; // Stores the created items
	public static CanvasItem selection; // Stores the selected item
	ToolBar toolbar; // Lance notre toolbar
	public static String mode;
	Image image; // Permet de récuperer l'image lors du drag & drop
	JMenuBar menu;
	JMenu fileMenu, editMenu;
	public static JMenuItem undoItem;
	JPanel menuPanel;
	JPanel menuIconPanel;
	File file;
	BufferedWriter writer;
	BufferedReader reader;
	File fileChoosen;
	static JFrame frame;
	public static Animator anim; // Permet de lancer l'animation
	public static int widthWindow;
	public static int heightWindow;
	public static int speed;
	public static boolean vent;
	public static boolean neige;
	private Point pointRotate;
	Point menuPoint;

	NewIconButton newIcon = new NewIconButton();
	OpenIconButton openIcon = new OpenIconButton();
	SaveIconButton saveIcon = new SaveIconButton();
	ToolBoxIconButton toolboxIcon = new ToolBoxIconButton();
	UndoIconButton undoIcon = new UndoIconButton();
	RedoIconButton redoIcon = new RedoIconButton();
	CloseIconButton closeIcon = new CloseIconButton();

	JLabel infosMousePositionLabel = new JLabel("" + "," + "0" + " ||");
	JLabel infosFormeLabel = new JLabel(" Forme : / " + "||");
	JLabel infosTailleLabel = new JLabel(" Taille : 0 " + "||");
	JLabel infosAnimationHorLabel = new JLabel(" Animation Horizontale : Non "
			+ "||");
	JLabel infosAnimationVerLabel = new JLabel(" Animation Verticale : Non "
			+ "||");
	JLabel infosAnimationBlinkLabel = new JLabel(
			" Animation de clignotement : Non " + "||");
	JLabel infosVitesse = new JLabel(" Vitesse : 0 ");

	boolean pieMenuDessin;
	boolean pieMenuAnimation;
	Point dessin = new Point(0, 0);

	// Constructor of the Graphical Editor

	public GraphicalEditor(String theTitle, int width, int height, ToolBar tool) {
		frame = this;
		widthWindow = width;
		heightWindow = height;
		title = theTitle;
		selection = null;
		toolbar = tool;
		pieMenuDessin = true;
		pieMenuAnimation = true;

		mode = toolbar.getMode(); // Permet d'obtenir le mode de dessin de la
									// toolbar.
		operations = toolbar.getOperations();
		speed = toolbar.slidVitesse.getValue();

		vent = toolbar.getVent();
		neige = toolbar.getNeige();
		anim = new Animator(canvas);
		JPanel menusPanel = new JPanel();
		menusPanel.setLayout(new BoxLayout(menusPanel, BoxLayout.PAGE_AXIS));
		initMenu();
		initIconsMenuPanel();
		initListenersIconsMenu();
		this.setLayout(new BorderLayout());
		menusPanel.add(menuPanel);
		menusPanel.add(menuIconPanel);
		this.add(menusPanel, BorderLayout.NORTH);
		JPanel infosPanel = new JPanel();
		infosPanel.setBackground(Color.yellow);
		this.add(infosPanel, BorderLayout.SOUTH);

		infosPanel.add(infosMousePositionLabel);
		infosPanel.add(infosFormeLabel);
		infosPanel.add(infosTailleLabel);
		infosPanel.add(infosAnimationHorLabel);
		infosPanel.add(infosAnimationVerLabel);
		infosPanel.add(infosAnimationBlinkLabel);
		infosPanel.add(infosVitesse);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		// Create the canvas for drawing
		canvas = new PersistentCanvas();
		canvas.setBackground(Color.WHITE);
		canvas.setPreferredSize(new Dimension(width, height));
		this.add(canvas);
		new DropTarget(canvas, this);

		// AJOUT DE L'ANIMATION !!!
		panel.add(anim);

		initListenersCanvas();
		/*
		 * Ajoute le KeyListener permettant de faire les raccourcis clavier Ctrl
		 * + s pour sauvegarder, Ctrl + O pour ouvrir un fichier, Ctrl + z pour
		 * annuler l'action précédente Ctrl + V pour copier, coller un fichier
		 */
		this.addKeyListener(this);
		pack();
		updateTitle();
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Donne une position à notre Jframe afin d'éviter qu'elle soit par
		// dessus notre toolbox.
		setLocation(170, 0);
	}

	// Permet de redessiner le bouton Undo afin de le rendre non selectionnable
	// quand on ne peut plus faire de Undo()
	public static void repaintUndo() {
		if (!canvas.items.isEmpty() && selection != null) {
			undoItem.setEnabled(true);
		} else {
			undoItem.setEnabled(false);
		}
		frame.repaint();
	}

	// Initialise les listeners de souris pour notre Canvas
	public void initListenersCanvas() {

		canvas.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (selection != null) {
					// infosFormeLabel.setText("Forme : " +
					// canvas.getItemAt(e.getPoint()).getType());
					// TODO : Finir le label avec toutes les infos
				}
				try {
					saveUndo();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Point p = e.getPoint();
				Color o = toolbar.getOutlineColor();
				Color f = toolbar.getFillColor();
				CanvasItem item = null;
				int but = e.getButton();
				if (but == MouseEvent.BUTTON3) {
					try {
						mode = "Menu";

						CanvasItem pieDessin = null;
						CanvasItem pieAnimation = null;

						pieDessin = new PieMenuDessin(canvas, new Color(1f, 0f,
								0f, .0f), new Color(1f, 0f, 0f, .0f), p, 0);
						pieAnimation = new PieMenuAnimation(canvas, new Color(
								1f, 0f, 0f, .0f), new Color(1f, 0f, 0f, .0f),
								p, 0);

						pieDessin.move(-pieDessin.getWidth() / 2,
								-pieDessin.getHeight());
						pieAnimation.move(-pieAnimation.getWidth() / 2, 0);

						canvas.addItem(pieDessin);
						canvas.addItem(pieAnimation);

						menuPoint = e.getPoint();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else if (but == MouseEvent.BUTTON1) {
					if (mode.equals("Select/Move")) {
						// TODO you can use the function select(CanvasItem
						// item);
						select(canvas.getItemAt(p));

					} else if (mode.equals("Blink")) {
						select(canvas.getItemAt(p));

						if (selection != null) {
							if (selection.blinkAnimate == true) {
								selection.blinkAnimate = false;
							} else {
								selection.blinkAnimate = true;
							}
						}
					} else if (mode.equals("Horizontal")) {
						select(canvas.getItemAt(p));
						if (selection != null) {
							if (selection.horizAnimate == true) {
								selection.horizAnimate = false;
							} else {
								selection.horizAnimate = true;
							}
						}
					} else if (mode.equals("Vertical")) {
						select(canvas.getItemAt(p));
						if (selection != null) {
							if (selection.verticAnimate == true) {
								selection.verticAnimate = false;
							} else {
								selection.verticAnimate = true;
							}
						}
					} else if (mode.equals("Resize")) {
						select(canvas.getItemAt(p));
						if (selection != null) {
							if (selection.resize == true) {
								selection.resize = false;
							} else {
								selection.resize = true;
							}
						}
					} else if (mode.equals("Rotation")) {
						select(canvas.getItemAt(p));
						if (selection != null) {
							pointRotate = p;
						}
					} else if (mode.equals("Vent")) {

					} else if (mode.equals("Neige")) {
						selection.neige = true;
						System.out.println("neige = true");
					} else {
						if (mode.equals("Rectangle")) {
							item = new RectangleItem(canvas, o, f, p, speed);
							infosFormeLabel.setText("Forme : Rectangle");
						} else if (mode.equals("Ellipse")) {
							// TODO create a new ellipse
							item = new CercleItem(canvas, o, f, p, speed);
						} else if (mode.equals("Line")) {
							// TODO create a new line
							item = new LineItem(canvas, o, f, p, speed);
						} else if (mode.equals("Path")) {
							// TODO create a new path
							item = new PathItem(canvas, o, f, p, speed);
						}
						canvas.addItem(item);
						select(item);
					}
				}
				updateTitle();
				repaintUndo();
				mousepos = p;
				repaint();
			}

			public void mouseReleased(MouseEvent e) {
				if (mode.equals("Menu")) {
					Point p = e.getPoint();

					// Recupere le mode
					if (!pieMenuDessin) {
						if (p.x < dessin.x - 20 && p.y < dessin.y - 20) {
							mode = "Ellipse";
						} else if (p.x > dessin.x + 20 && p.y < dessin.y - 20) {
							mode = "Rectangle";
						} else if (p.x < dessin.x - 20 && p.y > dessin.y + 20) {
							mode = "Path";
						} else if (p.x > dessin.x + 20 && p.y > dessin.y + 20) {
							mode = "Line";
						} else {
							mode = "Select/Move";
						}
					} else if (!pieMenuAnimation) {
						if (p.x < dessin.x - 20 && p.y < dessin.y - 20) {
							mode = "Vertical";
						} else if (p.x > dessin.x + 20 && p.y < dessin.y - 20) {
							mode = "Horizontal";
						} else if (p.x < dessin.x - 20 && p.y > dessin.y + 20) {
							mode = "P";
						} else if (p.x > dessin.x + 20 && p.y > dessin.y + 20) {
							mode = "Blink";
						} else {
							mode = "Select/Move";
						}
					}

					// Efface le menu
					ArrayList<CanvasItem> list = new ArrayList<CanvasItem>();
					for (int i = 0; i < canvas.getItems().size(); i++) {
						if (canvas.getItems().get(i).getType()
								.equals("PieMenuRectangle")
								|| canvas.getItems().get(i).getType()
										.equals("PieMenuEllipse")
								|| canvas.getItems().get(i).getType()
										.equals("PieMenuPath")
								|| canvas.getItems().get(i).getType()
										.equals("PieMenuLine")
								|| canvas.getItems().get(i).getType()
										.equals("PieMenuDessin")
								|| canvas.getItems().get(i).getType()
										.equals("PieMenuAnimation")
								|| canvas.getItems().get(i).getType()
										.equals("PieMenuH")
								|| canvas.getItems().get(i).getType()
										.equals("PieMenuV")
								|| canvas.getItems().get(i).getType()
										.equals("PieMenuP")
								|| canvas.getItems().get(i).getType()
										.equals("PieMenuC")) {
							list.add(canvas.getItems().get(i));
						}
					}
					for (int j = 0; j < list.size(); j++) {
						canvas.removeItem(list.get(j));
					}
					pieMenuDessin = true;
					pieMenuAnimation = true;
					updateTitle();
					frame.repaint();
				}
			}
		});

		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				ArrayList<CanvasItem> list = new ArrayList<CanvasItem>();
				for (int i = 0; i < canvas.getItems().size(); i++) {
					if (canvas.getItems().get(i).getType()
							.equals("PieMenuRectangle")
							|| canvas.getItems().get(i).getType()
									.equals("PieMenuEllipse")
							|| canvas.getItems().get(i).getType()
									.equals("PieMenuPath")
							|| canvas.getItems().get(i).getType()
									.equals("PieMenuLine")
							|| canvas.getItems().get(i).getType()
									.equals("PieMenuDessin")
							|| canvas.getItems().get(i).getType()
									.equals("PieMenuAnimation")
							|| canvas.getItems().get(i).getType()
									.equals("PieMenuH")
							|| canvas.getItems().get(i).getType()
									.equals("PieMenuV")
							|| canvas.getItems().get(i).getType()
									.equals("PieMenuP")
							|| canvas.getItems().get(i).getType()
									.equals("PieMenuC")) {
						list.add(canvas.getItems().get(i));
					}
				}

				if (mode.equals("Menu")) {
					Point p = e.getPoint();
					if (pieMenuDessin && p.y < menuPoint.y - 20) {
						dessin.setLocation(new Point(menuPoint.x,
								menuPoint.y - 160));
						removePieAnimation();
						setPieDessin(dessin, list);
						for (CanvasItem itm : list) {
							if (itm.getType().equals("PieMenuAnimation")) {
								try {
									Image img = ImageIO
											.read(new File(
													"PieMenu/Pie/Pie2AnimationTrans.png"));
									itm.background = img;
								} catch (IOException y) {
								}
							}
							if (itm.getType().equals("PieMenuDessin")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie2Dessin.png"));
									itm.background = img;
								} catch (IOException y) {
								}
							}
						}
					}
					if (pieMenuAnimation && p.y > menuPoint.y + 20) {
						dessin.setLocation(new Point(menuPoint.x,
								menuPoint.y + 160));
						removePieDessin();
						setPieAnimation(dessin, list);
						for (CanvasItem itm : list) {
							if (itm.getType().equals("PieMenuAnimation")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie2Animation.png"));
									itm.background = img;
								} catch (IOException y) {
								}
							}
							if (itm.getType().equals("PieMenuDessin")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie2DessinTrans.png"));
									itm.background = img;
								} catch (IOException y) {
								}
							}
						}
					}

					if (p.x < dessin.x - 20 && p.y < dessin.y - 20) {
						// Menu Ellipse | V
						for (CanvasItem itm : list) {
							if (itm.getType().equals("PieMenuRectangle")) {
								try {
									Image img = ImageIO
											.read(new File(
													"PieMenu/Pie/Pie4RectangleTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuPath")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4PathTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuLine")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4LineTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuEllipse")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4Ellipse.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuH")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4HTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuP")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4PTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuC")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4CTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuV")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4V.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							canvas.repaint();
						}
					} else if (p.x > dessin.x + 20 && p.y < dessin.y - 20) {
						// Menu Rectangle | H
						for (CanvasItem itm : list) {
							if (itm.getType().equals("PieMenuRectangle")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4Rectangle.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuPath")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4PathTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuLine")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4LineTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuEllipse")) {
								try {
									Image img = ImageIO
											.read(new File(
													"PieMenu/Pie/Pie4EllipseTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuH")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4H.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuP")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4PTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuC")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4CTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuV")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4VTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							canvas.repaint();
						}
					} else if (p.x < dessin.x - 20 && p.y > dessin.y + 20) {
						// Menu Path | P
						for (CanvasItem itm : list) {
							if (itm.getType().equals("PieMenuRectangle")) {
								try {
									Image img = ImageIO
											.read(new File(
													"PieMenu/Pie/Pie4RectangleTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuPath")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4Path.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuLine")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4LineTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuEllipse")) {
								try {
									Image img = ImageIO
											.read(new File(
													"PieMenu/Pie/Pie4EllipseTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuH")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4HTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuP")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4P.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuC")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4CTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuV")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4VTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							canvas.repaint();
						}
					} else if (p.x > dessin.x + 20 && p.y > dessin.y + 20) {
						// Menu Line | C
						for (CanvasItem itm : list) {
							if (itm.getType().equals("PieMenuRectangle")) {
								try {
									Image img = ImageIO
											.read(new File(
													"PieMenu/Pie/Pie4RectangleTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuPath")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4PathTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuLine")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4Line.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuEllipse")) {
								try {
									Image img = ImageIO
											.read(new File(
													"PieMenu/Pie/Pie4EllipseTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuH")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4HTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuP")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4PTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuC")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4C.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuV")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4VTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							canvas.repaint();
						}
					} else {
						// Zone neutre
						for (CanvasItem itm : list) {
							if (itm.getType().equals("PieMenuRectangle")) {
								try {
									Image img = ImageIO
											.read(new File(
													"PieMenu/Pie/Pie4RectangleTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuPath")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4PathTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuLine")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4LineTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuEllipse")) {
								try {
									Image img = ImageIO
											.read(new File(
													"PieMenu/Pie/Pie4EllipseTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuH")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4HTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuP")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4PTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuC")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4CTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							if (itm.getType().equals("PieMenuV")) {
								try {
									Image img = ImageIO.read(new File(
											"PieMenu/Pie/Pie4VTrans.png"));
									itm.background = img;
								} catch (IOException y) {

								}
							}
							canvas.repaint();
						}
					}

				} else {
					if (selection == null)
						return;
					if (mode.equals("Select/Move")) {

						// TODO move the selected object
						selection.move(e.getX() - mousepos.x, e.getY()
								- mousepos.y);
					} else if (!mode.equals("Horizontal")
							&& !mode.equals("Vertical")
							&& !mode.equals("Blink")
							&& !mode.equals("Rotation")) {
						selection.update(e.getPoint());
						// System.out.println(selection.get);
					} else if (mode.equals("Rotation")) {
						AffineTransform at = new AffineTransform();
						Point center = new Point(
								(selection.getMinX() * 2 + selection.getWidth()) / 2,
								(selection.getMinY() * 2 + selection
										.getHeight()) / 2);
						Point tmp = new Point(e.getX(), e.getY());

						// System.out.println("Initialisation	" +
						// pointRotate.getX()
						// + " ; " + pointRotate.getY());
						// System.out.println("Deplacement	" + tmp.getX() +
						// " ; " +
						// tmp.getY());
						at.translate(-selection.getMinX(), -selection.getMinY());
						at.rotate(tmp.y - center.y);
						at.translate(selection.getMinX(), selection.getMinY());
					}
					if (selection == null)
						return;
					if (mode.equals("Select/Move")) {

						// TODO move the selected object
						selection.move(e.getX() - mousepos.x, e.getY()
								- mousepos.y);
					} else if (!mode.equals("Horizontal")
							&& !mode.equals("Vertical")
							&& !mode.equals("Blink")
							&& !mode.equals("Rotation")) {
						selection.update(e.getPoint());
						// System.out.println(selection.get);
					}
					mousepos = e.getPoint();
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseMoved(e);
				infosMousePositionLabel.setText(e.getX() + "," + e.getY());
			}
		});
		this.addKeyListener(this);

		// pane.addKeyListener(keyboardListener);
		pack();
		updateTitle();
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(170, 0);
		anim.start();
	}

	// Initialise le Menu de notre Jframe, on ajoute également les listeners de
	// notre menu.
	public void initMenu() {
		menuPanel = new JPanel();
		menuPanel.setLayout(new BorderLayout());

		menu = new JMenuBar();
		fileMenu = new JMenu("File");
		editMenu = new JMenu("Edit");

		menu.add(fileMenu);
		menu.add(editMenu);
		menuPanel.add(menu, BorderLayout.WEST);

		JMenuItem newItem = new JMenuItem("New");
		fileMenu.add(newItem);
		JMenuItem toolbarItem = new JMenuItem("Toolbar");
		fileMenu.add(toolbarItem);
		JMenuItem openItem = new JMenuItem("Open ...");
		fileMenu.add(openItem);
		fileMenu.addSeparator();
		JMenuItem saveItem = new JMenuItem("Save");
		fileMenu.add(saveItem);
		JMenuItem saveAsItem = new JMenuItem("Save As ...");
		fileMenu.add(saveAsItem);
		fileMenu.addSeparator();
		JMenuItem exitItem = new JMenuItem("Exit");
		fileMenu.add(exitItem);

		JMenuItem pasteItem = new JMenuItem("Paste");
		editMenu.add(pasteItem);
		menuPanel.setBackground(Color.lightGray);
		menu.setBackground(Color.lightGray);
		fileMenu.setBackground(Color.lightGray);
		editMenu.setBackground(Color.lightGray);

		undoItem = new JMenuItem("Undo");
		undoItem.setBackground(Color.lightGray);
		undoItem.setEnabled(false);
		menu.add(undoItem);

		/************ Listeners ***********/

		openItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					open();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		undoItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (canvas.items.isEmpty() && selection == null) {
					undoItem.setEnabled(false);
					frame.repaint();
				} else {
					try {
						undo();
						repaintUndo();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

		saveItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					save();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		saveAsItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					saveAs();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				toolbar.dispose();
			}
		});

		pasteItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CanvasItem clone = selection.duplicate();
				clone.move(10, 10);
				select(clone);
			}
		});

		toolbarItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				toolbar = new ToolBar();
			}
		});
	}

	public void initIconsMenuPanel() {
		menuIconPanel = new JPanel();
		menuIconPanel.setLayout(new BorderLayout());
		JPanel iconPanel = new JPanel();
		iconPanel.setLayout(new GridLayout(1, 7, 10, 20));
		iconPanel.add(newIcon);
		iconPanel.add(openIcon);
		iconPanel.add(saveIcon);
		iconPanel.add(toolboxIcon);
		iconPanel.add(undoIcon);
		iconPanel.add(redoIcon);
		iconPanel.add(closeIcon);

		menuIconPanel.add(iconPanel, BorderLayout.WEST);

		this.add(menuIconPanel);
	}

	public void initListenersIconsMenu() {

		newIcon.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.removeAll();
			}
		});

		openIcon.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					open();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		saveIcon.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					save();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		toolboxIcon.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				toolbar = new ToolBar();
			}
		});

		undoIcon.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					undo();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		redoIcon.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		closeIcon.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(EXIT_ON_CLOSE);
			}
		});

	}

	// Permet de mettre à jour le titre quand on change le mode.
	public ActionListener modeListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			mode = e.getActionCommand();
			updateTitle();
		}

	};

	// Update the Title
	public void updateTitle() {
		setTitle(title + " - " + mode);
	}

	// Deselect an Item
	public static void deselect(CanvasItem item) {
		if (selection != null) {
			selection.deselect();
		}
		for (JButton op : operations) {
			op.setEnabled(false);
		}
	}

	// Select an Item
	public static void select(CanvasItem item) {
		if (selection != null)
			selection.deselect();

		selection = item;
		if (selection != null) {
			selection.select();
			for (JButton op : operations)
				op.setEnabled(true);
		} else {
			for (JButton op : operations)
				op.setEnabled(false);
		}
	}

	public CanvasItem getSelection() {
		return selection;

	}

	public PersistentCanvas getCanvas() {
		return canvas;
	}

	/************************************* DROPTARGETLISTENER *****************************************/
	// Ecoute notre fenêtre afin de savoir si on Drop une image. On a choisi de
	// faire un drop que d'une seule image à la fois.
	// Credit : StackOverflow
	@Override
	public void drop(DropTargetDropEvent event) {

		// Accept copy drops
		event.acceptDrop(DnDConstants.ACTION_COPY);

		// Get the transfer which can provide the dropped item data
		Transferable transferable = event.getTransferable();

		// Get the data formats of the dropped item
		DataFlavor[] flavors = transferable.getTransferDataFlavors();

		// Loop through the flavors
		for (DataFlavor flavor : flavors) {

			try {

				// If the drop items are files
				if (flavor.isFlavorJavaFileListType()) {
					String imagePath = "";
					// Get all of the dropped files
					List<File> files = (List<File>) transferable
							.getTransferData(flavor);

					// Loop them through
					for (File file : files) {

						// Print out the file path
						System.out.println("File path is '" + file.getPath()
								+ "'.");
						this.file = file;
						image = ImageIO.read(file);
						paintComponents((Graphics2D) getGraphics());
						imagePath = file.getPath();
					}
					ImageItem item = new ImageItem(canvas, Color.black, null,
							event.getLocation(), image, imagePath, speed);
					canvas.items.add(item);
					repaint();
				}

			} catch (Exception e) {
				e.printStackTrace();

			}
		}

		// Inform that the drop is complete
		event.dropComplete(true);

	}

	@Override
	public void dragEnter(DropTargetDragEvent event) {
	}

	@Override
	public void dragExit(DropTargetEvent event) {
	}

	@Override
	public void dragOver(DropTargetDragEvent event) {
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent event) {
	}

	@Override
	public void paintComponents(Graphics g) {
		super.paintComponents(g);
	}

	/********************************** SAUVEGARDE *****************************/
	// Permet de sauvegarder nos données.
	public void save() throws IOException {
		System.out.println("sauvegarde debut");
		String data = "";
		for (CanvasItem item : canvas.items) {
			if (item.getType() == "Rectangle") {
				RectangleItem newItem = (RectangleItem) item;
				data += "1";
				data += " ";
				data += newItem.getP1X();
				data += " ";
				data += newItem.getP1Y();
				data += " ";
				data += newItem.getP2X();
				data += " ";
				data += newItem.getP2Y();
				data += " ";
				data += newItem.getColorInterieur();
				data += " ";
				data += newItem.getColorExterieur();
			} else if (item.getType() == "Ellipse") {
				CercleItem newItem = (CercleItem) item;
				data += "2";
				data += " ";
				data += newItem.getX();
				data += " ";
				data += newItem.getY();
				data += " ";
				data += newItem.getGrandRayon();
				data += " ";
				data += newItem.getPetitRayon();
				data += " ";
				data += newItem.getColorInterieur();
				data += " ";
				data += newItem.getColorExterieur();
			} else if (item.getType() == "Line") {
				LineItem newItem = (LineItem) item;
				data += "3";
				data += " ";
				data += newItem.getP1X();
				data += " ";
				data += newItem.getP1Y();
				data += " ";
				data += newItem.getP2X();
				data += " ";
				data += newItem.getP2Y();
				data += " ";
				data += newItem.getColorInterieur();
				data += " ";
				data += newItem.getColorExterieur();
			} else if (item.getType() == "Path") {
				PathItem newItem = (PathItem) item;
				data += "4";
				data += " ";
				for (Point point : newItem.getListPoint()) {
					data += (int) point.getX();
					data += " ";
					data += (int) point.getY();
					data += " ";
				}
				data += newItem.getColorInterieur();
				data += " ";
				data += newItem.getColorExterieur();
			} else if (item.getType() == "Image") {
				ImageItem newItem = (ImageItem) item;
				data += "5";
				data += " ";
				data += newItem.getP1X();
				data += " ";
				data += newItem.getP1Y();
				data += " ";
				data += newItem.getPath();
			}
			data += "\t";
			deselect(selection);
			selection = null;

			menuPanel.setVisible(false);
			menu.setVisible(false);

			BufferedImage image = new BufferedImage(this.getSize().width,
					this.getSize().height, BufferedImage.TYPE_INT_ARGB);
			Graphics g = image.createGraphics();
			this.paint(g);
			g.dispose();
			try {
				ImageIO.write(image, "png", new File("test.png"));
			} catch (Exception e) {
			}
		}
		// System.out.println(data);
		if (fileChoosen != null) {
			BufferedWriter saveBuff;
			saveBuff = new BufferedWriter(new FileWriter(fileChoosen));
			saveBuff.write(data);
			saveBuff.close();
		} else {
			saveAs();
		}

		menuPanel.setVisible(true);
		System.out.println("sauvegarde fin");
	}

	// Permet de sauvegarder nos actions afin de pouvoir faire un Undo()
	public void saveUndo() throws IOException {
		System.out.println("SaveUndo debut");
		String data = "";
		for (CanvasItem item : canvas.items) {
			if (item.getType() == "Rectangle") {
				RectangleItem newItem = (RectangleItem) item;
				data += "1";
				data += " ";
				data += newItem.getP1X();
				data += " ";
				data += newItem.getP1Y();
				data += " ";
				data += newItem.getP2X();
				data += " ";
				data += newItem.getP2Y();
				data += " ";
				data += newItem.getColorInterieur();
				data += " ";
				data += newItem.getColorExterieur();
			} else if (item.getType() == "Ellipse") {
				CercleItem newItem = (CercleItem) item;
				data += "2";
				data += " ";
				data += newItem.getX();
				data += " ";
				data += newItem.getY();
				data += " ";
				data += newItem.getGrandRayon();
				data += " ";
				data += newItem.getPetitRayon();
				data += " ";
				data += newItem.getColorInterieur();
				data += " ";
				data += newItem.getColorExterieur();
			} else if (item.getType() == "Line") {
				LineItem newItem = (LineItem) item;
				data += "3";
				data += " ";
				data += newItem.getP1X();
				data += " ";
				data += newItem.getP1Y();
				data += " ";
				data += newItem.getP2X();
				data += " ";
				data += newItem.getP2Y();
				data += " ";
				data += newItem.getColorInterieur();
				data += " ";
				data += newItem.getColorExterieur();
			} else if (item.getType() == "Path") {
				PathItem newItem = (PathItem) item;
				data += "4";
				data += " ";
				for (Point point : newItem.getListPoint()) {
					data += (int) point.getX();
					data += " ";
					data += (int) point.getY();
					data += " ";
				}
				data += newItem.getColorInterieur();
				data += " ";
				data += newItem.getColorExterieur();
			} else if (item.getType() == "Image") {
				ImageItem newItem = (ImageItem) item;
				data += "5";
				data += " ";
				data += newItem.getP1X();
				data += " ";
				data += newItem.getP1Y();
				data += " ";
				data += newItem.getPath();
			}
			data += "\t";
		}
		BufferedWriter saveBuff;
		saveBuff = new BufferedWriter(new FileWriter("tmp.txt"));
		saveBuff.write(data);
		saveBuff.close();
	}

	// Permet de sauvegarder en demandant à l'utilisateur où il souhaite
	// sauvegarder.
	public void saveAs() throws IOException {

		JFileChooser fileChooser = new JFileChooser(".");
		int retrieval = fileChooser.showSaveDialog(null);
		fileChoosen = fileChooser.getSelectedFile();
		if (retrieval == JFileChooser.APPROVE_OPTION) {
			try {
				FileWriter fileWriter = new FileWriter(fileChoosen);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Beginning of Save As...");
		String data = "";
		for (CanvasItem item : canvas.items) {
			if (item.getType() == "Rectangle") {
				RectangleItem newItem = (RectangleItem) item;
				data += "1";
				data += " ";
				data += newItem.getP1X();
				data += " ";
				data += newItem.getP1Y();
				data += " ";
				data += newItem.getP2X();
				data += " ";
				data += newItem.getP2Y();
				data += " ";
				data += newItem.getColorInterieur();
				data += " ";
				data += newItem.getColorExterieur();
			} else if (item.getType() == "Ellipse") {
				CercleItem newItem = (CercleItem) item;
				data += "2";
				data += " ";
				data += newItem.getX();
				data += " ";
				data += newItem.getY();
				data += " ";
				data += newItem.getGrandRayon();
				data += " ";
				data += newItem.getPetitRayon();
				data += " ";
				data += newItem.getColorInterieur();
				data += " ";
				data += newItem.getColorExterieur();
			} else if (item.getType() == "Line") {
				LineItem newItem = (LineItem) item;
				data += "3";
				data += " ";
				data += newItem.getP1X();
				data += " ";
				data += newItem.getP1Y();
				data += " ";
				data += newItem.getP2X();
				data += " ";
				data += newItem.getP2Y();
				data += " ";
				data += newItem.getColorInterieur();
				data += " ";
				data += newItem.getColorExterieur();
			} else if (item.getType() == "Path") {
				PathItem newItem = (PathItem) item;
				data += "4";
				data += " ";
				for (Point point : newItem.getListPoint()) {
					data += (int) point.getX();
					data += " ";
					data += (int) point.getY();
					data += " ";
				}
				data += newItem.getColorInterieur();
				data += " ";
				data += newItem.getColorExterieur();
			} else if (item.getType() == "Image") {
				ImageItem newItem = (ImageItem) item;

				data += "5";
				data += " ";
				data += newItem.getP1X();
				data += " ";
				data += newItem.getP1Y();
				data += " ";
				data += newItem.getPath();
			}

			data += "\t";
		}
		System.out.println(data);
		BufferedWriter saveBuff;
		saveBuff = new BufferedWriter(new FileWriter(fileChoosen));
		saveBuff.write(data);
		saveBuff.close();
		// fileWriter.close();
		System.out.println("End of Save As...");
	}

	// Permet d'annuler la dernière action réalisée par l'utilisateur.

	public void undo() throws IOException {
		BufferedReader readFile = new BufferedReader(new FileReader("tmp.txt"));
		String line = readFile.readLine();
		System.out.println("OpenUndo debut");
		canvas.removeAll();
		while (line != null) {
			String[] itemList = line.split("\t");
			line = readFile.readLine();
			for (String item : itemList) {
				String[] paramList = item.split(" ");
				if (Integer.parseInt(paramList[0]) == 1) {
					RectangleItem canvasItem = new RectangleItem(
							canvas,
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 5]),
									Integer.parseInt(paramList[paramList.length - 1 - 4]),
									Integer.parseInt(paramList[paramList.length - 1 - 3])),
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 2]),
									Integer.parseInt(paramList[paramList.length - 1 - 1]),
									Integer.parseInt(paramList[paramList.length - 1])),
							new Point(Integer.parseInt(paramList[1]), Integer
									.parseInt(paramList[2])), 5);
					canvasItem.update(new Point(Integer.parseInt(paramList[3]),
							Integer.parseInt(paramList[4])));
					canvas.addItem(canvasItem);
				} else if (Integer.parseInt(paramList[0]) == 2) {
					CercleItem canvasItem = new CercleItem(
							canvas,
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 5]),
									Integer.parseInt(paramList[paramList.length - 1 - 4]),
									Integer.parseInt(paramList[paramList.length - 1 - 3])),
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 2]),
									Integer.parseInt(paramList[paramList.length - 1 - 1]),
									Integer.parseInt(paramList[paramList.length - 1])),
							new Point(Integer.parseInt(paramList[1]), Integer
									.parseInt(paramList[2])), 5);
					canvasItem.update(new Point(Integer.parseInt(paramList[4]),
							Integer.parseInt(paramList[3])));
					canvas.addItem(canvasItem);
				} else if (Integer.parseInt(paramList[0]) == 3) {
					LineItem canvasItem = new LineItem(
							canvas,
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 5]),
									Integer.parseInt(paramList[paramList.length - 1 - 4]),
									Integer.parseInt(paramList[paramList.length - 1 - 3])),
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 2]),
									Integer.parseInt(paramList[paramList.length - 1 - 1]),
									Integer.parseInt(paramList[paramList.length - 1])),
							new Point(Integer.parseInt(paramList[1]), Integer
									.parseInt(paramList[2])), 5);
					canvasItem.update(new Point(Integer.parseInt(paramList[3]),
							Integer.parseInt(paramList[4])));
					canvas.addItem(canvasItem);
				} else if (Integer.parseInt(paramList[0]) == 4) {
					PathItem canvasItem = new PathItem(
							canvas,
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 5]),
									Integer.parseInt(paramList[paramList.length - 1 - 4]),
									Integer.parseInt(paramList[paramList.length - 1 - 3])),
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 2]),
									Integer.parseInt(paramList[paramList.length - 1 - 1]),
									Integer.parseInt(paramList[paramList.length - 1])),
							new Point(Integer.parseInt(paramList[1]), Integer
									.parseInt(paramList[2])), 5);
					for (int i = 5; i < paramList.length - 1 - 5; i += 2) {
						canvasItem.update(new Point(Integer
								.parseInt(paramList[i]), Integer
								.parseInt(paramList[i + 1])));
					}
					canvas.addItem(canvasItem);
				}
			}
		}
		deselect(selection);
		selection = null;
		System.out.println("OpenUndo fin");
		readFile.close();
	}

	public void open() throws IOException {
		System.out.println("open debut");
		canvas.removeAll();
		JFileChooser fileChooser = new JFileChooser(".");
		fileChooser.showOpenDialog(null);
		File fichier = fileChooser.getSelectedFile();
		String fileName = fichier.getName();
		BufferedReader readFile = new BufferedReader(new FileReader(fileName));
		String line = readFile.readLine();
		fileChoosen = fichier;

		while (line != null) {
			String[] itemList = line.split("\t");
			line = readFile.readLine();
			for (String item : itemList) {
				String[] paramList = item.split(" ");
				if (Integer.parseInt(paramList[0]) == 1) {
					RectangleItem canvasItem = new RectangleItem(
							canvas,
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 5]),
									Integer.parseInt(paramList[paramList.length - 1 - 4]),
									Integer.parseInt(paramList[paramList.length - 1 - 3])),
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 2]),
									Integer.parseInt(paramList[paramList.length - 1 - 1]),
									Integer.parseInt(paramList[paramList.length - 1])),
							new Point(Integer.parseInt(paramList[1]), Integer
									.parseInt(paramList[2])), 5);
					canvasItem.update(new Point(Integer.parseInt(paramList[3]),
							Integer.parseInt(paramList[4])));
					canvas.addItem(canvasItem);
				} else if (Integer.parseInt(paramList[0]) == 2) {
					CercleItem canvasItem = new CercleItem(
							canvas,
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 5]),
									Integer.parseInt(paramList[paramList.length - 1 - 4]),
									Integer.parseInt(paramList[paramList.length - 1 - 3])),
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 2]),
									Integer.parseInt(paramList[paramList.length - 1 - 1]),
									Integer.parseInt(paramList[paramList.length - 1])),
							new Point(Integer.parseInt(paramList[1]), Integer
									.parseInt(paramList[2])), 5);
					canvasItem.update(new Point(Integer.parseInt(paramList[4]),
							Integer.parseInt(paramList[3])));
					canvas.addItem(canvasItem);
				} else if (Integer.parseInt(paramList[0]) == 3) {
					LineItem canvasItem = new LineItem(
							canvas,
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 5]),
									Integer.parseInt(paramList[paramList.length - 1 - 4]),
									Integer.parseInt(paramList[paramList.length - 1 - 3])),
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 2]),
									Integer.parseInt(paramList[paramList.length - 1 - 1]),
									Integer.parseInt(paramList[paramList.length - 1])),
							new Point(Integer.parseInt(paramList[1]), Integer
									.parseInt(paramList[2])), 5);
					canvasItem.update(new Point(Integer.parseInt(paramList[3]),
							Integer.parseInt(paramList[4])));
					canvas.addItem(canvasItem);
				} else if (Integer.parseInt(paramList[0]) == 4) {
					PathItem canvasItem = new PathItem(
							canvas,
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 5]),
									Integer.parseInt(paramList[paramList.length - 1 - 4]),
									Integer.parseInt(paramList[paramList.length - 1 - 3])),
							new Color(
									Integer.parseInt(paramList[paramList.length - 1 - 2]),
									Integer.parseInt(paramList[paramList.length - 1 - 1]),
									Integer.parseInt(paramList[paramList.length - 1])),
							new Point(Integer.parseInt(paramList[1]), Integer
									.parseInt(paramList[2])), 5);
					for (int i = 5; i < paramList.length - 1 - 5; i += 2) {
						canvasItem.update(new Point(Integer
								.parseInt(paramList[i]), Integer
								.parseInt(paramList[i + 1])));
					}
					canvas.addItem(canvasItem);
				} else if (Integer.parseInt(paramList[0]) == 5) {
					// String data = "";
					// for (int i = 3; i < paramList.length; i++) {
					// data += paramList[i];
					// }
					// byte[] bytes = dataByte.getBytes("UTF-8");
					Image img = ImageIO.read(new File(paramList[3]));

					ImageItem canvasItem = new ImageItem(canvas, Color.black,
							Color.black, new Point(
									Integer.parseInt(paramList[1]),
									Integer.parseInt(paramList[2])), img,
							paramList[3], 5);

					// // canvasItem.setByte(bytes);
					canvas.addItem(canvasItem);
				}
			}
		}
		readFile.close();
		System.out.println("open fin");
	}

	/************************************* KEYLISTENER *****************************/
	@Override
	public void keyTyped(KeyEvent e) {
	}

	// Afin de savoir si la touche ctrl a été pressée, on utilise la méthode
	// event.isControlDown();
	// Permet d'écouter le clavier afin de savoir si un raccourci clavier a
	// été
	// pressée.
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DELETE) {
			canvas.removeItem(selection);
			deselect(selection);
			if (!canvas.items.isEmpty()) {
				undoItem.setEnabled(true);
			} else {
				if (selection != null)
					undoItem.setEnabled(false);
			}
		}

		if ((e.getKeyCode() == KeyEvent.VK_S)
				&& ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
			try {
				save();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if ((e.getKeyCode() == KeyEvent.VK_O)
				&& ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
			try {
				open();
			} catch (IOException e1) {
				e1.printStackTrace();
				if (!canvas.items.isEmpty()) {
					undoItem.setEnabled(true);
				} else {
					undoItem.setEnabled(false);
				}
			}
		}
		if ((e.getKeyCode() == KeyEvent.VK_V)
				&& ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
			CanvasItem clone = selection.duplicate();
			clone.move(10, 10);
			select(clone);
		}

		if (e.isShiftDown() && e.isControlDown()
				&& e.getKeyCode() == KeyEvent.VK_S) {
			try {
				saveAs();

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) {
			try {
				undo();
				repaintUndo();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	public static void setNeige() {
		if (neige) {
			System.out.println("Neige On");
			for (int i = 0; i < heightWindow; i += (heightWindow / 30)) {
				Point tmp = new Point((int) (Math.random() * widthWindow),
						(int) (Math.random() * heightWindow));
				Neige itm = new Neige(canvas, tmp,
						(int) (Math.random() * 100) % 4 + 1);
				itm.verticAnimate = true;
				itm.neige = true;
				canvas.addItem(itm);
			}
		} else {
			System.out.println("Neige Off");
			ArrayList<CanvasItem> tmp = new ArrayList<CanvasItem>();
			for (CanvasItem itm : canvas.getItems()) {
				if (itm.getType().equals("Neige")) {
					tmp.add(itm);
				}
			}
			for (CanvasItem itm : tmp) {
				canvas.removeItem(itm);
			}
		}
	}

	public static void setVent() {
		if (vent) {
			System.out.println("Vent on");
			for (int i = 0; i < widthWindow; i += (widthWindow / 30)) {
				Point tmp = new Point((int) (Math.random() * widthWindow),
						(int) (Math.random() * heightWindow));
				Vent itm = new Vent(canvas, tmp,
						(int) (Math.random() * 100) % 4 + 1);
				itm.horizAnimate = true;
				canvas.addItem(itm);
				itm.vent = true;
			}
		} else {
			System.out.println("Vent off");
			ArrayList<CanvasItem> tmp = new ArrayList<CanvasItem>();
			for (CanvasItem itm : canvas.getItems()) {
				if (itm.getType().equals("Vent")) {
					tmp.add(itm);
				}
			}
			for (CanvasItem itm : tmp) {
				canvas.removeItem(itm);
			}
		}
	}

	/******** Pie Dessin *********/
	public void setPieDessin(Point p, ArrayList<CanvasItem> l) {
		pieMenuDessin = false;
		CanvasItem pieRect = null;
		CanvasItem pieEllipse = null;
		CanvasItem piePath = null;
		CanvasItem pieLine = null;
		try {
			pieRect = new PieMenuRectangle(canvas, new Color(1f, 0f, 0f, .0f),
					new Color(1f, 0f, 0f, .0f), p, 0);
			pieEllipse = new PieMenuEllipse(canvas, new Color(1f, 0f, 0f, .0f),
					new Color(1f, 0f, 0f, .0f), p, 0);
			piePath = new PieMenuPath(canvas, new Color(1f, 0f, 0f, .0f),
					new Color(1f, 0f, 0f, .0f), p, 0);
			pieLine = new PieMenuLine(canvas, new Color(1f, 0f, 0f, .0f),
					new Color(1f, 0f, 0f, .0f), p, 0);
		} catch (IOException error) {

		}
		pieRect.move(0, -pieRect.getHeight());
		pieEllipse.move(-pieEllipse.getWidth(), -pieEllipse.getHeight());
		piePath.move(-piePath.getWidth(), 0);

		canvas.addItem(pieRect);
		canvas.addItem(pieEllipse);
		canvas.addItem(pieLine);
		canvas.addItem(piePath);

		l.add(pieRect);
		l.add(pieEllipse);
		l.add(pieLine);
		l.add(piePath);
	}

	public void removePieDessin() {
		// Efface le menu Dessin
		ArrayList<CanvasItem> list = new ArrayList<CanvasItem>();
		for (int i = 0; i < canvas.getItems().size(); i++) {
			if (canvas.getItems().get(i).getType().equals("PieMenuRectangle")
					|| canvas.getItems().get(i).getType()
							.equals("PieMenuEllipse")
					|| canvas.getItems().get(i).getType().equals("PieMenuPath")
					|| canvas.getItems().get(i).getType().equals("PieMenuLine")) {
				list.add(canvas.getItems().get(i));
			}
		}
		for (int j = 0; j < list.size(); j++) {
			canvas.removeItem(list.get(j));
		}

		pieMenuDessin = true;
	}

	/******** Pie Animation *********/
	public void setPieAnimation(Point p, ArrayList<CanvasItem> l) {
		pieMenuAnimation = false;
		CanvasItem pieH = null;
		CanvasItem pieV = null;
		CanvasItem pieP = null;
		CanvasItem pieC = null;
		try {
			pieH = new PieMenuH(canvas, new Color(1f, 0f, 0f, .0f), new Color(
					1f, 0f, 0f, .0f), p, 0);
			pieV = new PieMenuV(canvas, new Color(1f, 0f, 0f, .0f), new Color(
					1f, 0f, 0f, .0f), p, 0);
			pieP = new PieMenuP(canvas, new Color(1f, 0f, 0f, .0f), new Color(
					1f, 0f, 0f, .0f), p, 0);
			pieC = new PieMenuC(canvas, new Color(1f, 0f, 0f, .0f), new Color(
					1f, 0f, 0f, .0f), p, 0);
		} catch (IOException error) {

		}
		pieH.move(0, -pieH.getHeight());
		pieV.move(-pieV.getWidth(), -pieV.getHeight());
		pieP.move(-pieP.getWidth(), 0);

		canvas.addItem(pieH);
		canvas.addItem(pieV);
		canvas.addItem(pieP);
		canvas.addItem(pieC);

		l.add(pieH);
		l.add(pieV);
		l.add(pieP);
		l.add(pieC);

	}

	public void removePieAnimation() {
		// Efface le menu Animation
		ArrayList<CanvasItem> list = new ArrayList<CanvasItem>();
		for (int i = 0; i < canvas.getItems().size(); i++) {
			if (canvas.getItems().get(i).getType().equals("PieMenuH")
					|| canvas.getItems().get(i).getType().equals("PieMenuV")
					|| canvas.getItems().get(i).getType().equals("PieMenuP")
					|| canvas.getItems().get(i).getType().equals("PieMenuC")) {
				list.add(canvas.getItems().get(i));
			}
		}
		for (int j = 0; j < list.size(); j++) {
			canvas.removeItem(list.get(j));
		}
		pieMenuAnimation = true;
	}

}
