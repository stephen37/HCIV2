package modele;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ImageItem extends CanvasItem {

	Point firstpoint;
	Point lastPoint;
	String path;

	public ImageItem(PersistentCanvas c, Color o, Color f, Point p, Image img,
			String imagePath, int v) {
		super(c, o, f, v);
		shape = new Rectangle(p.x, p.y, img.getWidth(null), img.getHeight(null));
		System.out.println("Hauteur " + img.getHeight(null));
		System.out.println("Largeur " + img.getWidth(null));
		firstpoint = p;
		background = img;
		path = imagePath;

	}

	public ImageItem(ImageItem other) {
		super(other.canvas, other.outline, other.fill, other.vitesse);
		shape = new Rectangle((Rectangle) other.shape);
		isSelected = false;
		firstpoint = other.firstpoint;
		background = other.background;
		path = other.path;
	}

	public CanvasItem duplicate() {
		return canvas.addItem(new ImageItem(this));
	}

	public void update(Point p) {
		canvas.repaint();
		lastPoint = p;
	}

	public void move(int dx, int dy) {
		((Rectangle) shape).x += dx;
		((Rectangle) shape).y += dy;
		canvas.repaint();
	}

	public String getType() {
		return "Image";
	}

	public int getP1X() {
		return (int) ((Rectangle) shape).getMinX();
	}

	public int getP1Y() {
		return (int) ((Rectangle) shape).getMinY();
	}

	public int getP2X() {
		return (int) ((Rectangle) shape).getMaxX();
	}

	public int getP2Y() {
		return (int) ((Rectangle) shape).getMaxY();
	}

	public String getPath() {
		return path;
	}

	@Override
	public ArrayList<Integer> getPoints() {
		// TODO Auto-generated method stub
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(firstpoint.x);
		list.add(firstpoint.y);
		return list;
	}

	@Override
	public int getMinX() {
		// TODO Auto-generated method stub
		return (int) shape.getBounds().getMinX();
	}

	@Override
	public int getMinY() {
		// TODO Auto-generated method stub
		return (int) shape.getBounds().getMinY();
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return (int) shape.getBounds().getWidth();
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return (int) shape.getBounds().getHeight();
	}

	// // Renvoie un string des bytes de l'image
	// public String getImageByte() throws IOException, Base64DecodingException
	// {
	// ByteArrayOutputStream res = new ByteArrayOutputStream(1000);
	// BufferedImage img = ImageIO.read(new File("testSave.jpg"));
	// ImageIO.write(img, imageFormat, res);
	// res.flush();
	//
	// String base64String = Base64.encode(res.toByteArray());
	// res.close();
	//
	// byte[] bytearray = Base64.decode(base64String);
	// BufferedImage img2 = ImageIO.read(new ByteArrayInputStream(bytearray));
	//
	// background =img2;
	//
	// return base64String;
	// }

	public byte[] getByte() throws FileNotFoundException {
		File file = new File(path);

		FileInputStream fis = new FileInputStream(file);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		try {
			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				// Writes to this byte array output stream
				bos.write(buf, 0, readNum);
				// System.out.println("read " + readNum + " bytes,");
			}
		} catch (IOException ex) {
		}
		byte[] bytes = bos.toByteArray();

		return bytes;
	}

	public void setByte(byte[] bytes) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		Iterator<?> readers = ImageIO.getImageReadersByFormatName("jpg");

		// ImageIO is a class containing static methods for locating
		// ImageReaders
		// and ImageWriters, and performing simple encoding and decoding.

		ImageReader reader = (ImageReader) readers.next();
		Object source = bis;
		ImageInputStream iis = ImageIO.createImageInputStream(source);
		reader.setInput(iis, true);
		ImageReadParam param = reader.getDefaultReadParam();

		Image image = reader.read(0, param);
		// got an image file

		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
				image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		// bufferedImage is the RenderedImage to be written

		Graphics2D g2 = bufferedImage.createGraphics();
		g2.drawImage(image, null, null);

		background = image;

		File imageFile = new File("newTestSava.jpg");
	}

	// public void setImageByte(String base64String) throws IOException,
	// Base64DecodingException {
	// byte[] bytearray = Base64.decode(base64String);
	// BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytearray));
	// background = img;
	// }

	public void rotate(int angle) {

	}

	@Override
	public void setY(int y) {
		// TODO Auto-generated method stub
		
	}

}
