import java.util.Scanner;
public class main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		State initial = initialState(4,4);
		//Game g1 = new Game("x","o",4);
		
	}
	public static State initialState(int numRows, int numCols){

		Space[][] board = new Space[numRows][numCols];
		char[] letters = {'a','b','c','d','e','f','g','h'};

		for(int r = 0; r < numRows; r++) {
			for(int c = 0; c < numCols; c++) {
				if((r == board.length/2 && c == board.length/2) || 
						(r == board.length/2 - 1 && c == board.length/2 - 1)) {

					board[r][c] = new Space(letters[c],r+1,"o");

				} else if((r == board.length/2 - 1 && c == board.length/2) || 
						(r == board.length/2 && c == board.length/2 - 1)) {

					board[r][c] = new Space(letters[c],r+1,"x");

				} else {
					board[r][c] = new Space(letters[c],r+1,"empty");
				}
			}
		}

		State init =  new State(board, null, -100000, false);
		init.printState();
		return init;
	}
}
