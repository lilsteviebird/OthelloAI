public class State {
	public char[][] board;
	public int boardWidth;
	public int minimax;
	public State minMaxedChild;
	public boolean terminalAI, terminalPlayer; //should have terminalAI and terminalPlayer
	
	public State(char[][] board, int minimax) {
		this.board = board;
		//this.parent = parent;
		this.minimax = minimax;
		minMaxedChild = null;
		terminalAI = false;
		terminalPlayer = false;
		boardWidth = board.length;
	}
	
	public State flipPieces(int row, int col, char given) {
		State returnMe = new State(board, minimax);
		//editing returnMe.board
		if(checkRight(row, col, given)) {
			Point end = flipRight(row, col, given);
			for(int i = col; i <= end.getY(); i++) {
				returnMe.board[row][i] = given;
			}
		}
		if(checkLeft(row, col, given)) {
			Point end = flipLeft(row, col, given);
			for(int i = col; i >= end.getY(); i--) {
				returnMe.board[row][i] = given;
			}
			
		}
		if(checkUp(row, col, given)) {
			Point end = flipUp(row, col, given);
			for(int i = row; i >= end.getX(); i--) {
				returnMe.board[i][col] = given;
			}
		}
		if(checkDown(row, col, given)) {
			Point end = flipDown(row, col, given);
			for(int i = row; i <= end.getX(); i++) {
				returnMe.board[i][col] = given;
			}
		}
		if(checkDiagTR(row, col, given)) {
			Point end = flipDiagTR(row, col, given);
			int tempRow = row;
			int tempCol = col;
			while(tempRow != end.getX() && tempCol != end.getY()) {
				returnMe.board[tempRow][tempCol] = given;
				tempRow--;
				tempCol++;
			}
		}
		if(checkDiagTL(row, col, given)) {
			Point end = flipDiagTL(row, col, given);
			int tempRow = row;
			int tempCol = col;
			while(tempRow != end.getX() && tempCol != end.getY()) {
				returnMe.board[tempRow][tempCol] = given;
				tempRow--;
				tempCol--;
			}
		}
		if(checkDiagBR(row, col, given)) {
			Point end = flipDiagBR(row, col, given);
			int tempRow = row;
			int tempCol = col;
			while(tempRow != end.getX() && tempCol != end.getY()) {
				returnMe.board[tempRow][tempCol] = given;
				tempRow++;
				tempCol++;
			}
		}
		if(checkDiagBL(row, col, given)) {
			Point end = flipDiagBL(row, col, given);
			int tempRow = row;
			int tempCol = col;
			while(tempRow != end.getX() && tempCol != end.getY()) {
				returnMe.board[tempRow][tempCol] = given;
				tempRow++;
				tempCol--;
			}
		}
		
		return returnMe;
	}

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
				if(board[r][c] == 'x') {
					System.out.print("x ");
				} else if(board[r][c] == 'o') {
					System.out.print("o ");
				} else {
					System.out.print("  ");
				}
			}
			System.out.println(r+1);
		}
		
		System.out.print("  "); //2 spaces
		for(int len = 0; len < boardWidth; len++) {
			System.out.print(letters[len]+ " ");
		}
		System.out.println("\n");
		
	}
	
	//flipPieces returns a State
	
	public char opponentCharacter(char given) {
		if(given == 'x') {
			return 'o';
		}
		return 'x';
	}

	public boolean checkValidity(int row, int col, char given) {
		return (checkRight(row, col, given) || checkLeft(row, col, given) 
				|| checkUp(row, col, given) || checkDown(row, col, given) 
				|| checkDiagTR(row, col, given) || checkDiagTL(row, col, given)
				|| checkDiagBR(row, col, given) || checkDiagBL(row, col, given));
	}	

	private boolean checkRight(int row, int col, char given) {
		char opp = opponentCharacter(given);
		boolean seenOpp = false;
		int here = col;
		for(int i = col + 1; i < boardWidth; i++) {
			if(board[row][i] == 'e') {
				break;
			}
			if(board[row][i] == opp) {
				seenOpp = true;
			}
			if(board[row][i] == given) {
				here = i;
				break;
			}
		}
		if(here != col && seenOpp && board[row][here] == given) {
			//System.out.println("Right: VALID MOVE AT: (" + row + "," + here + ")");
			return true;
		}
		return false;
	}
	
	private Point flipRight(int row, int col, char given) {
		Point toHere = new Point(0, 0);
		char opp = opponentCharacter(given);
		boolean seenOpp = false;
		int here = col;
		for(int i = col + 1; i < boardWidth; i++) {
			if(board[row][i] == 'e') {
				break;
			}
			if(board[row][i] == opp) {
				seenOpp = true;
			}
			if(board[row][i] == given) {
				here = i;
				break;
			}
		}
		if(here != col && seenOpp && board[row][here] == given) {
			//System.out.println("Right: VALID MOVE AT: (" + row + "," + here + ")");
			toHere.setX(row);
			toHere.setY(here);
			return toHere;
		}
		return toHere;
	}

	private boolean checkLeft(int row, int col, char given) {
		char opp = opponentCharacter(given);
		boolean seenOpp = false;
		int here = col;
		for(int i = col - 1; i >= 0; i--) {
			if(board[row][i] == 'e') {
				break;
			}
			if(board[row][i] == opp) {
				seenOpp = true;
			}
			if(board[row][i] == given) {
				here = i;
				break;
			}
		}
		if(here != col && seenOpp && board[row][here] == given) {
			//System.out.println("Left: VALID MOVE AT: (" + row + "," + here + ")");
			return true;
		}
		return false;
	}
	
	private Point flipLeft(int row, int col, char given) {
		char opp = opponentCharacter(given);
		Point toHere = new Point(0, 0);
		boolean seenOpp = false;
		int here = col;
		for(int i = col - 1; i >= 0; i--) {
			if(board[row][i] == 'e') {
				break;
			}
			if(board[row][i] == opp) {
				seenOpp = true;
			}
			if(board[row][i] == given) {
				here = i;
				break;
			}
		}
		if(here != col && seenOpp && board[row][here] == given) {
			//System.out.println("Left: VALID MOVE AT: (" + row + "," + here + ")");
			toHere.setX(row);
			toHere.setY(here);
			return toHere;
		}
		return toHere;
	}

	private boolean checkUp(int row, int col, char given) {
		char opp = opponentCharacter(given);
		boolean seenOpp = false;
		int here = row;
		for(int i = row - 1; i >= 0; i--) {
			if(board[i][col] == 'e') {
				break;
			}
			if(board[i][col] == opp) {
				seenOpp = true;
			}
			if(board[i][col] == given) {
				here = i;
				break;
			}
		}
		if(here != row && seenOpp && board[here][col] == given) {
			//System.out.println("UP: VALID MOVE AT: (" + here + "," + col + ")");
			return true;
		}
		return false;
	}
	
	private Point flipUp(int row, int col, char given) {
		char opp = opponentCharacter(given);
		Point toHere = new Point(0, 0);
		boolean seenOpp = false;
		int here = row;
		for(int i = row - 1; i >= 0; i--) {
			if(board[i][col] == 'e') {
				break;
			}
			if(board[i][col] == opp) {
				seenOpp = true;
			}
			if(board[i][col] == given) {
				here = i;
				break;
			}
		}
		if(here != row && seenOpp && board[here][col] == given) {
			//System.out.println("UP: FLIPPED MOVE AT: (" + here + "," + col + ")");
			toHere.setX(here);
			toHere.setY(col);
			return toHere;
		}
		return toHere;
	}

	private boolean checkDown(int row, int col, char given) {
		char opp = opponentCharacter(given);
		boolean seenOpp = false;
		int here = row;
		for(int i = row + 1; i < boardWidth; i++) {
			if(board[i][col] == 'e') {
				break;
			}
			if(board[i][col] == opp) {
				seenOpp = true;
			}
			if(board[i][col] == given) {
				here = i;
				break;
			}
		}
		if(here != row && seenOpp && board[here][col] == given) {
			//System.out.println("Down: VALID MOVE AT: (" + here + "," + col + ")");
			return true;
		}
		return false;
	}
	
	private Point flipDown(int row, int col, char given) {
		char opp = opponentCharacter(given);
		Point toHere = new Point(0, 0);
		boolean seenOpp = false;
		int here = row;
		for(int i = row + 1; i < boardWidth; i++) {
			if(board[i][col] == 'e') {
				break;
			}
			if(board[i][col] == opp) {
				seenOpp = true;
			}
			if(board[i][col] == given) {
				here = i;
				break;
			}
		}
		if(here != row && seenOpp && board[here][col] == given) {
			//System.out.println("Down: VALID MOVE AT: (" + here + "," + col + ")");
			toHere.setX(here);
			toHere.setY(col);
			return toHere;
		}
		return toHere;
	}

	private boolean checkDiagTR(int row, int col, char given) {
		char opp = opponentCharacter(given);
		boolean seenOpp = false;
		int tempRow = row - 1;
		int tempCol = col + 1;

		while(tempRow >= 0 && tempCol < boardWidth) {
			if(board[tempRow][tempCol] == 'e') {
				break;
			}
			if(board[tempRow][tempCol] == opp) {
				seenOpp = true;
			}
			if(board[tempRow][tempCol] == given) {
				break;
			}
			tempRow--;
			tempCol++;
		}
		if(tempRow==-1 || tempCol==boardWidth) {
			return false;
		}
		if(tempRow != row && tempCol != col && seenOpp && board[tempRow][tempCol] == given) {
			//System.out.println("Down: VALID MOVE AT: (" + tempRow + "," + tempCol + ")");
			return true;
		}
		return false;
	}
	
	private Point flipDiagTR(int row, int col, char given) {
		char opp = opponentCharacter(given);
		Point toHere = new Point(0 ,0);
		boolean seenOpp = false;
		int tempRow = row - 1;
		int tempCol = col + 1;

		while(tempRow >= 0 && tempCol < boardWidth) {
			if(board[tempRow][tempCol] == 'e') {
				break;
			}
			if(board[tempRow][tempCol] == opp) {
				seenOpp = true;
			}
			if(board[tempRow][tempCol] == given) {
				break;
			}
			tempRow--;
			tempCol++;
		}
		if(tempRow != row && tempCol != col && seenOpp && board[tempRow][tempCol] == given) {
			//System.out.println("Down: VALID MOVE AT: (" + tempRow + "," + tempCol + ")");
			toHere.setX(tempRow);
			toHere.setY(tempCol);
			return toHere;
		}
		return toHere;
	}

	private boolean checkDiagTL(int row, int col, char given) {
		char opp = opponentCharacter(given);
		boolean seenOpp = false;
		int tempRow = row - 1;
		int tempCol = col - 1;

		while(tempRow >= 0 && tempCol >= 0) {
			if(board[tempRow][tempCol] == 'e') {
				break;
			}
			if(board[tempRow][tempCol] == opp) {
				seenOpp = true;
			}
			if(board[tempRow][tempCol] == given) {
				break;
			}
			tempRow--;
			tempCol--;
		}
		if(tempRow==-1 || tempCol==-1) {
			return false;
		}
		if(tempRow != row && tempCol != col && seenOpp && board[tempRow][tempCol] == given) {
			//System.out.println("Down: VALID MOVE AT: (" + tempRow + "," + tempCol + ")");
			return true;
		}
		return false;
	}
	
	private Point flipDiagTL(int row, int col, char given) {
		char opp = opponentCharacter(given);
		Point toHere = new Point(0 ,0);
		boolean seenOpp = false;
		int tempRow = row - 1;
		int tempCol = col - 1;

		while(tempRow >= 0 && tempCol >= 0) {
			if(board[tempRow][tempCol] == 'e') {
				break;
			}
			if(board[tempRow][tempCol] == opp) {
				seenOpp = true;
			}
			if(board[tempRow][tempCol] == given) {
				break;
			}
			tempRow--;
			tempCol--;
		}
		if(tempRow != row && tempCol != col && seenOpp && board[tempRow][tempCol] == given) {
			//System.out.println("Down: VALID MOVE AT: (" + tempRow + "," + tempCol + ")");
			toHere.setX(tempRow);
			toHere.setY(tempCol);
			return toHere;
		}
		return toHere;
	}
	
	private boolean checkDiagBR(int row, int col, char given) {
		char opp = opponentCharacter(given);
		boolean seenOpp = false;
		int tempRow = row + 1;
		int tempCol = col + 1;

		while(tempRow < boardWidth && tempCol < boardWidth) {
			if(board[tempRow][tempCol] == 'e') {
				break;
			}
			if(board[tempRow][tempCol] == opp) {
				seenOpp = true;
			}
			if(board[tempRow][tempCol] == given) {
				break;
			}
			tempRow++;
			tempCol++;
		}
		if(tempRow==boardWidth || tempCol==boardWidth) {
			return false;
		}
		if(tempRow != row && tempCol != col && seenOpp && board[tempRow][tempCol] == given) {
			//System.out.println("Down: VALID MOVE AT: (" + tempRow + "," + tempCol + ")");
			return true;
		}
		return false;
	}
	
	private Point flipDiagBR(int row, int col, char given) {
		char opp = opponentCharacter(given);
		Point toHere = new Point(0, 0);
		boolean seenOpp = false;
		int tempRow = row + 1;
		int tempCol = col + 1;

		while(tempRow < boardWidth && tempCol < boardWidth) {
			if(board[tempRow][tempCol] == 'e') {
				break;
			}
			if(board[tempRow][tempCol] == opp) {
				seenOpp = true;
			}
			if(board[tempRow][tempCol] == given) {
				break;
			}
			tempRow++;
			tempCol++;
		}
		if(tempRow != row && tempCol != col && seenOpp && board[tempRow][tempCol] == given) {
			//System.out.println("Down: VALID MOVE AT: (" + tempRow + "," + tempCol + ")");
			toHere.setX(tempRow);
			toHere.setY(tempCol);
			return toHere;
		}
		return toHere;
	}

	private boolean checkDiagBL(int row, int col, char given) {
		char opp = opponentCharacter(given);
		boolean seenOpp = false;
		int tempRow = row + 1;
		int tempCol = col - 1;
		
		while(tempRow < boardWidth && tempCol >= 0) {
			if(board[tempRow][tempCol] == 'e') {
				break;
			}
			if(board[tempRow][tempCol] == opp) {
				seenOpp = true;
			}
			if(board[tempRow][tempCol] == given) {
				break;
			}
			tempRow++;
			tempCol--;
		}
		if(tempRow==boardWidth || tempCol==-1) {
			return false;
		}
		if(tempRow != row && tempCol != col && seenOpp && board[tempRow][tempCol] == given) {
			//System.out.println("Down: VALID MOVE AT: (" + tempRow + "," + tempCol + ")");
			return true;
		}
		return false;
	}
	
	private Point flipDiagBL(int row, int col, char given) {
		char opp = opponentCharacter(given);
		Point toHere = new Point(0, 0);
		boolean seenOpp = false;
		int tempRow = row + 1;
		int tempCol = col - 1;
		
		while(tempRow < boardWidth && tempCol >= 0) {
			if(board[tempRow][tempCol] == 'e') {
				break;
			}
			if(board[tempRow][tempCol] == opp) {
				seenOpp = true;
			}
			if(board[tempRow][tempCol] == given) {
				break;
			}
			tempRow++;
			tempCol--;
		}
		if(tempRow != row && tempCol != col && seenOpp && board[tempRow][tempCol] == given) {
			//System.out.println("Down: VALID MOVE AT: (" + tempRow + "," + tempCol + ")");
			toHere.setX(tempRow);
			toHere.setY(tempCol);
			return toHere;
		}
		return toHere;
	}
}
