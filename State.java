
import java.util.ArrayList;
public class State {
	public Space[][] board;
	public int boardWidth;
	public int minimax;
	//public State parent;
	public State minMaxedChild;
	public boolean terminalAI, terminalPlayer; //should have terminalAI and terminalPlayer
	public ArrayList<State> childStates;
	
	public State(Space[][] board, int minimax) {
		this.board = board;
		//this.parent = parent;
		this.minimax = minimax;
		minMaxedChild = null;
		terminalAI = false;
		terminalPlayer = false;
		boardWidth = board.length;
		childStates = new ArrayList<State>();
	}

//	public boolean isTerminal() { return terminal; }
//	public void setTerminal(boolean terminal) { this.terminal = terminal; }
//	public int getMinimax() { return minimax; }
//	public void setMinimax(int minimax) { this.minimax = minimax; }

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
	
	//flipPieces returns a State
	
	public String opponentCharacter(String given) {
		if(given.equals("X")) {
			return "O";
		}
		return "X";
	}

	public boolean checkValidity(int row, int col, String given) {
		return (checkRight(row, col, given) || checkLeft(row, col, given) 
				|| checkUp(row, col, given) || checkDown(row, col, given) 
				|| checkDiagTR(row, col, given) || checkDiagTL(row, col, given)
				|| checkDiagBR(row, col, given) || checkDiagBL(row, col, given));
	}	

	private boolean checkRight(int row, int col, String given) {
		String opp = opponentCharacter(given);
		boolean seenOpp = false;
		int here = col;
		for(int i = col + 1; i < boardWidth; i++) {
			if(board[row][i].equals(opp)) {
				seenOpp = true;
			}
			if(board[row][i].equals(given)) {
				here = i;
				break;
			}
		}
		if(here != col && seenOpp && board[row][here].equals(given)) {
			System.out.println("Right: VALID MOVE AT: (" + row + "," + here + ")");
			return true;
		}
		return false;
	}

	private boolean checkLeft(int row, int col, String given) {
		String opp = opponentCharacter(given);
		boolean seenOpp = false;
		int here = col;
		for(int i = col - 1; i >= 0; i--) {
			if(board[row][i].equals(opp)) {
				seenOpp = true;
			}
			if(board[row][i].equals(given)) {
				here = i;
				break;
			}
		}
		if(here != col && seenOpp && board[row][here].equals(given)) {
			System.out.println("Left: VALID MOVE AT: (" + row + "," + here + ")");
			return true;
		}
		return false;
	}

	private boolean checkUp(int row, int col, String given) {
		String opp = opponentCharacter(given);
		boolean seenOpp = false;
		int here = row;
		for(int i = row - 1; i >= 0; i--) {
			if(board[i][col].equals(opp)) {
				seenOpp = true;
			}
			if(board[i][col].equals(given)) {
				here = i;
				break;
			}
		}
		if(here != row && seenOpp && board[here][col].equals(given)) {
			System.out.println("UP: VALID MOVE AT: (" + here + "," + col + ")");
			return true;
		}
		return false;
	}

	private boolean checkDown(int row, int col, String given) {
		String opp = opponentCharacter(given);
		boolean seenOpp = false;
		int here = row;
		for(int i = row + 1; i < boardWidth; i++) {
			if(board[i][col].equals(opp)) {
				seenOpp = true;
			}
			if(board[i][col].equals(given)) {
				here = i;
				break;
			}
		}
		if(here != row && seenOpp && board[here][col].equals(given)) {
			System.out.println("Down: VALID MOVE AT: (" + here + "," + col + ")");
			return true;
		}
		return false;
	}

	private boolean checkDiagTR(int row, int col, String given) {
		String opp = opponentCharacter(given);
		boolean seenOpp = false;
		int tempRow = row - 1;
		int tempCol = col + 1;

		while(tempRow >= 0 && tempCol < boardWidth) {
			if(board[tempRow][tempCol].equals(opp)) {
				seenOpp = true;
			}
			if(board[tempRow][tempCol].equals(given)) {
				break;
			}
			tempRow--;
			tempCol++;
		}
		if(tempRow != row && tempCol != col && seenOpp && board[tempRow][tempCol].equals(given)) {
			System.out.println("Down: VALID MOVE AT: (" + tempRow + "," + tempCol + ")");
			return true;
		}
		return false;
	}

	private boolean checkDiagTL(int row, int col, String given) {
		String opp = opponentCharacter(given);
		boolean seenOpp = false;
		int tempRow = row - 1;
		int tempCol = col - 1;

		while(tempRow >= 0 && tempCol >= 0) {
			if(board[tempRow][tempCol].equals(opp)) {
				seenOpp = true;
			}
			if(board[tempRow][tempCol].equals(given)) {
				break;
			}
			tempRow--;
			tempCol--;
		}
		if(tempRow != row && tempCol != col && seenOpp && board[tempRow][tempCol].equals(given)) {
			System.out.println("Down: VALID MOVE AT: (" + tempRow + "," + tempCol + ")");
			return true;
		}
		return false;
	}
	
	private boolean checkDiagBR(int row, int col, String given) {
		String opp = opponentCharacter(given);
		boolean seenOpp = false;
		int tempRow = row + 1;
		int tempCol = col + 1;

		while(tempRow < boardWidth && tempCol < boardWidth) {
			if(board[tempRow][tempCol].equals(opp)) {
				seenOpp = true;
			}
			if(board[tempRow][tempCol].equals(given)) {
				break;
			}
			tempRow++;
			tempCol++;
		}
		if(tempRow != row && tempCol != col && seenOpp && board[tempRow][tempCol].equals(given)) {
			System.out.println("Down: VALID MOVE AT: (" + tempRow + "," + tempCol + ")");
			return true;
		}
		return false;
	}

	private boolean checkDiagBL(int row, int col, String given) {
		String opp = opponentCharacter(given);
		boolean seenOpp = false;
		int tempRow = row + 1;
		int tempCol = col - 1;
		
		while(tempRow < boardWidth && tempCol >= 0) {
			if(board[tempRow][tempCol].equals(opp)) {
				seenOpp = true;
			}
			if(board[tempRow][tempCol].equals(given)) {
				break;
			}
			tempRow++;
			tempCol--;
		}
		if(tempRow != row && tempCol != col && seenOpp && board[tempRow][tempCol].equals(given)) {
			System.out.println("Down: VALID MOVE AT: (" + tempRow + "," + tempCol + ")");
			return true;
		}
		return false;
	}
}
