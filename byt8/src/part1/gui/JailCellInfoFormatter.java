package part1.gui;

import part1.Cell;

public class JailCellInfoFormatter implements CellInfoFormatter {

    public static final String JAIL_CELL_LABEL = "<html><b>Jail</b></html>";

    public String format(Cell cell) {
		return JAIL_CELL_LABEL;
	}

}
