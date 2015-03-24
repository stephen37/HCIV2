package modele;

import vue.GraphicalEditor;
import vue.ToolBar2;

public class Main {

	public static void main(String[] args) {

//		ToolBar tool = new ToolBar();
		ToolBar2 tool = new ToolBar2();
		new GraphicalEditor("Editor ", 1400, 800, tool);
	}

}
