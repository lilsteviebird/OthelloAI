
public class Space {
	private char col;
	private int row;
	private String color; //black, white, or empty
	private String tile;
	
	public Space(char col, int row, String color) {
		this.col = col;
		this.row = row;
		this.color = color;
	}

	public char getCol() { return col; }
	public void setCol(char col) { this.col = col; }
	public int getRow() { return row; }
	public void setRow(int row) { this.row = row; }
	public String getColor() { return color; }
	public void setColor(String color) { this.color = color; }
	
	public String makeTile() {
		tile = col + String.valueOf(row);
		return tile;
	}
	
	public void printSpace() {
		System.out.println(makeTile()+" : "+getColor());
	}
	public void printInBoard() {
		System.out.print(makeTile()+"\t");
	}
}
