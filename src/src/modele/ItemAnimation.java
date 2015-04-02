package modele;

/**
 * 
 * @author J Garcia Modifications	Thibault Soret & Stephen Batifol (03-03-2015)
 *
 */
public abstract class ItemAnimation {

	//Caracteristiques
	protected CanvasItem item;

	//Constructeur
	public ItemAnimation(CanvasItem item) {
		this.item = item;
	}

	public abstract boolean processHorizontal();
	
	public abstract boolean processVertical();
	
	public abstract boolean processResize();

	public abstract void resume(int x, int y);

}
