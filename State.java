
public class State {
	public Space[][] board;
	private int boardWidth;
	private int minimax;
	public State parent;
	//public State leftChild;
	//public State rightSibling;
	private boolean terminal;
	
	public State(Space[][] board, State parent, int minimax, boolean terminal) {
		this.board = board;
		this.parent = parent;
		this.minimax = minimax;
		this.terminal = terminal;
		boardWidth = board.length;
	}

	public boolean isTerminal() { return terminal; }
	public void setTerminal(boolean terminal) { this.terminal = terminal; }
	public int getMinimax() { return minimax; }
	public void setMinimax(int minimax) { this.minimax = minimax; }

	public void printState() {
		//dark/black = x , light/white = o
		char[] letters = {'a','b','c','d','e','f','g','h'};
		System.out.print("  "); //2 spaces
		for(int len = 0; len < boardWidth; len++) {
			System.out.print(letters[len]+ " ");
		}
		System.out.println();
		
		for(int r = 0; r < boardWidth; r++) {
			System.out.print(r+1 + " ");
			for(int c = 0; c < boardWidth; c++) {
				if(board[r][c].getColor().compareTo("x") == 0) {
					System.out.print("x ");
				} else if(board[r][c].getColor().compareTo("o") == 0) {
					System.out.print("o ");
				} else if(board[r][c].getColor().compareTo("empty") == 0) {
					System.out.print("  ");
				}
			}
			System.out.println(r+1);
		}
		
		System.out.print("  "); //2 spaces
		for(int len = 0; len < boardWidth; len++) {
			System.out.print(letters[len]+ " ");
		}
		System.out.println();
		
	}
}
