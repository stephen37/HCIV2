package modele;

import vue.GraphicalEditor;
import vue.ToolBar;

public class Main {

	public static void main(String[] args) {

		ToolBar tool = new ToolBar();
		tool.setVisible(false);
//		new GraphicalEditor("Editor ", 1400, 800, tool);
		new GraphicalEditor("Editor 2.0", 1400, 900, tool);
		tool.setVisible(true);
	}

}
