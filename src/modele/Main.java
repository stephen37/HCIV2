package modele;

import vue.GraphicalEditor;
import vue.ToolBar;

public class Main {

	public static void main(String[] args) {

//		ToolBar tool = new ToolBar();
		ToolBar tool = new ToolBar();
		new GraphicalEditor("Editor ", 1400, 800, tool);
	}

}
