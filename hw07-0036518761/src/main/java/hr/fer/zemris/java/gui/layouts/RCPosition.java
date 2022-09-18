package hr.fer.zemris.java.gui.layouts;

public class RCPosition {
	
	private int row, column;
	
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
	public static RCPosition parse(String text) {
		int row, column;
		String[] params = text.split(",");
		
		if(params.length != 2) {
			throw new IllegalArgumentException("Invalid position input");
		}
		try {
			row = Integer.parseInt(params[0]);
			column = Integer.parseInt(params[1]);
		} catch(NumberFormatException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
		
		return new RCPosition(row, column);
	}
}
