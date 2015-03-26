package modele;

import vue.Editor;
import vue.ToolBar;

public class Main {

	public static void main(String[] args) {

//		ToolBar tool = new ToolBar();
		ToolBar tool = new ToolBar();
	//	new GraphicalEditor("Editor ", 1400, 800, tool);
		new Editor("Editor 2.0", 1400, 900, tool);
	}

}
