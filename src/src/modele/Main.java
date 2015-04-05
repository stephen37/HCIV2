package modele;

import vue.GraphicalEditor;
import vue.ToolBar;
import vue.Tutorial;

public class Main {

	public static void main(String[] args) {

		ToolBar tool = new ToolBar();
//		tool.setVisible(false);
		new GraphicalEditor("Editor 2.0", 1400, 900, tool);
		tool.setVisible(true);
//		new Tutorial();
	}

}
