package gui;

import java.awt.GridBagConstraints;

public class Utils {
	public static void setGBC(GridBagConstraints gc, int column, int row, int width, int height, int fill) {
		gc.gridx = column;
		gc.gridy = row;
		gc.gridwidth = width;
		gc.gridheight = height;
		gc.fill = fill;
	}
}
