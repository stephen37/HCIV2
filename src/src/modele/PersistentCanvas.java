package modele;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;

import vue.ToolBar;
import controleur.PositionAnimation;

/**
 * @author Nicolas Roussel (roussel@lri.fr) Modifications apportées par Stephen
 *         Batifol et Thibault Soret
 * 
 */
@SuppressWarnings("serial")
public class PersistentCanvas extends Component {

	public static ArrayList<CanvasItem> items;

	public PersistentCanvas() {
		items = new ArrayList<CanvasItem>();
	}

	public PersistentCanvas(PersistentCanvas other) {
		items = other.getItems();
	}

	public CanvasItem getItemAt(Point p) {
		// TODO pick the 2D item under the Point p
		CanvasItem res = null;
		for (CanvasItem item : items) {
			if (item.contains(p)) {
				res = item;
			}
		}
		return res;
	}

	public GuideItem getNearGuide(Point p) {
		GuideItem result = null;
		for (int i = items.size() - 1; i >= 0; i--) {
			CanvasItem item = items.get(i);

			if (item instanceof GuideItem) {
				if (((GuideItem) item).isAttracted(p)) {
					if (result != null) {
						if (result.distanceToLine(p) > ((GuideItem) item)
								.distanceToLine(p)) {
							result = ((GuideItem) item);
						}
					} else {
						result = ((GuideItem) item);
					}
				}
			}
		}
		return result;
	}

	public CanvasItem addItem(CanvasItem item) {
		if (item == null)
			return null;
		items.add(item);
		repaint();
		return item;
	}

	public void removeItem(CanvasItem item) {
		if (item == null)
			return;
		items.remove(item);
		repaint();
	}

	public void paint(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		for (CanvasItem item : items)
			item.paint(g);

	}

	public void removeAll() {
		items.clear();
		repaint();
	}

	public ArrayList<CanvasItem> getItems() {
		return items;
	}

	public static void processAnimation() {
		if (ToolBar.startCheckBox.isSelected()) {
			for (CanvasItem item : items) {
				if (item.horizAnimate) {
					PositionAnimation anim = new PositionAnimation(item);
					anim.processHorizontal();
				}
				if (item.verticAnimate) {
					PositionAnimation anim = new PositionAnimation(item);
					anim.processVertical();
				}
				if (item.blinkAnimate) {
					PositionAnimation anim = new PositionAnimation(item);
					anim.processBlink();
				}
				if (item.resize) {
					PositionAnimation anim = new PositionAnimation(item);
					anim.processResize();
					item.resize = false;
				}
				if (item.neige) {
					PositionAnimation anim = new PositionAnimation(item);
					anim.processVertical();
				}
				if (item.vent) {
					PositionAnimation anim = new PositionAnimation(item);
					anim.processHorizontal();
				}
			}
		}
	}

	public static void resumeAnimations() {
		for (CanvasItem item : items) {
			PositionAnimation anim = new PositionAnimation(item);
		}
	}
}
