import java.util.LinkedList;
public class Game {
	public char player, AI; //x vs o color
	public int boardWidth;
	public State currentState;
	public State childState;
	public State temp;
	public int tempMinimax = 0;
	public int minEval;
	public int maxEval;
	public Point bestPoint = new Point(-1,-1);
	public LinkedList<Point> childStates = new LinkedList<Point>();
	
	char[] letters = {'a','b','c','d','e','f','g','h'};
	
	
	public Game(char player, char AI, int boardWidth) {
		this.player = player;
		this.AI = AI;
		this.boardWidth = boardWidth;
		currentState = initialState(boardWidth, boardWidth);
		
	}
	
	public void updateBoardPlayer(int row, int col, char color) {
		currentState.board[row][col] = color;
		currentState = currentState.flipPieces(row,col,color); //looks for any sandwich in direction and flips them
		System.out.println("Current State After Your Move: \n");
		currentState.printState();
	}
	public void updateBoardAI() {
		currentState = minimaxDecision(currentState);
		System.out.println("AI played " );
		System.out.println("Current State After AI Move: \n");
		currentState.printState();
	}
	
//	public char pointChar(Point p) {
//		int c = -1;
//		char[] letters = {'a','b','c','d','e','f','g','h'};
//		for(char l: letters) {
//			c++;
//			if(col == l) {
//				return c;
//			}
//		}
//		return -1;
//	}
	
	public void updateBoardAIAB() {
		currentState = EightDecision(currentState);
		System.out.println("AI played " );
		System.out.println("Current State After AI Move: \n");
		currentState.printState();
	}

	public State EightDecision(State position) {
		position.minimax = Integer.MIN_VALUE;
		temp = copyBoard(position);
		childStates = getChildStates(temp, AI);

		for(Point child: childStates) {
			//child.printPoint();
			temp = copyBoard(position);

			tempMinimax = EightMiniMax(temp.flipPieces(child.x, child.y, AI), 3, Integer.MIN_VALUE, Integer.MAX_VALUE, player);

			if(tempMinimax > position.minimax) {
				currentState.printState();
				bestPoint.x = child.x; bestPoint.y = child.y;
				position.minimax = tempMinimax;
				maxEval = tempMinimax;
			}
		}
		position.minMaxedChild = currentState.flipPieces(bestPoint.x, bestPoint.y, AI);
		return position.minMaxedChild;
	}


	public int EightMiniMax(State position, int depth, int alpha, int beta, char turn) {
		if(depth == 0 || getTerminal(position, turn)) {
			return position.minimax;
		}
		
		if(turn == AI) {
			int tempMax = Integer.MIN_VALUE;
			temp = copyBoard(position);
			childStates = getChildStates(temp, turn);
			
			for(Point c : childStates) {
				State temp2 = copyBoard(position);
				temp2.flipPieces(c.x, c.y, turn);
				int eval = EightMiniMax(temp2, depth - 1, alpha, beta, player);
				maxEval = Math.max(eval, tempMax);
				alpha = Math.max(alpha, eval);
				if(beta <= alpha) {
					break;
				}
			}
			return maxEval;

		} else {
			int tempMin = Integer.MAX_VALUE;
			temp = copyBoard(position);
			childStates = getChildStates(temp, turn);

			for(Point c : childStates) {
				State temp2 = copyBoard(position);
				temp2.flipPieces(c.x, c.y, turn);
				int eval = EightMiniMax(temp2, depth - 1, alpha, beta, AI);
				maxEval = Math.min(eval, tempMin);
				alpha = Math.min(beta, eval);
				if(beta <= alpha) {
					break;
				}
			}
			return maxEval;
		}
	}
	
	
	//functions as maxValue but is the first step and returns a state instead of the minimax utility
	public State minimaxDecision(State s) {
		s.minimax = -10000;
		temp = copyBoard(s);
		
		childStates = getChildStates(temp, AI);
		
		for(Point child: childStates) {
			//child.printPoint();
			
			temp = copyBoard(s);
			
			tempMinimax = minimax(temp.flipPieces(child.x, child.y, AI), player);
			
			if(tempMinimax > s.minimax) {
				//currentState.printState();
				bestPoint.x = child.x; bestPoint.y = child.y;
				s.minimax = tempMinimax;
				maxEval = tempMinimax;
			}
		}
		s.minMaxedChild = currentState.flipPieces(bestPoint.x, bestPoint.y, AI);
		return s.minMaxedChild;
	}
	
	public int minimax(State s, char turn) {
		temp = copyBoard(s);
		//temp.printState();
		
		//if game over in state s (make a method to check if game over)
		if(getTerminal(temp, turn)) {
			temp.minimax = utility(temp);
			return utility(temp);
		}
		
		if(turn == player) {
			minEval = 100000;
			childStates = getChildStates(temp,player);
			for(Point child: childStates) {
				//System.out.println("Pushing into max recursion: "+child.x+" "+child.y);
				
				temp = copyBoard(s);
				tempMinimax = minimax(temp.flipPieces(child.x, child.y, player), AI);
				
				if(tempMinimax < minEval) {
					//s.minMaxedChild = s.flipPieces(child.x, child.y, player);
					s.minimax = tempMinimax;
					minEval = tempMinimax;
					
				}
			}
			return minEval;
			
		} else if (turn == AI) {
			maxEval = -100000;
			childStates = getChildStates(temp,AI);
			for(Point child: childStates) {
				//System.out.println("Pushing into min recursion: "+child.x+" "+child.y);
				
				temp = copyBoard(s);
				tempMinimax = minimax(temp.flipPieces(child.x, child.y, AI), player);
				
				if(tempMinimax > maxEval) {
					//s.minMaxedChild = s.flipPieces(child.x, child.y, AI);
					s.minimax = tempMinimax;
					maxEval = tempMinimax;
					
				}
			}
			return maxEval;
			
		}
		
		return 0;
	}
	
	//Obtains all the possible player moves from a state s.
	public LinkedList<Point> getChildStates(State s, char turn){
		LinkedList<Point> childStatesLocal = new LinkedList<Point>();
		int failCounter = 0;
		for(int r = 0; r < boardWidth; r++) {
			for(int c = 0; c < boardWidth; c++) {
				if(s.board[r][c] == 'e') {
					if(s.checkValidity(r,c,turn)) {
						childStatesLocal.add(new Point(r,c));
					} else {
						failCounter++;
					}
				} else {
					failCounter++;
				}

			}
		}
		return childStatesLocal;
	}

	public boolean getTerminal(State s, char turn){
		int failCounter = 0;
		for(int r = 0; r < boardWidth; r++) {
			for(int c = 0; c < boardWidth; c++) {
				if(s.board[r][c] == 'e') {
					if(!s.checkValidity(r,c,turn)) {
						failCounter++;
					} 
				} else {
					failCounter++;
				}

			}
		}
		if(failCounter==boardWidth*boardWidth) {
			//System.out.println("failed");
			return true;
		}
		//System.out.println("EmP: "+failCounter);
		return false;
	}
	
	public State copyBoard(State s) {
		State temp = new State(new char[boardWidth][boardWidth], -100000);
		for(int r = 0; r < boardWidth; r++) {
			for(int c = 0; c < boardWidth; c++) {
				temp.board[r][c] = s.board[r][c];
			}
		}
		
		return temp;
	}
	
	public int utility(State s) {
		int xs = 0, os = 0;
		for(int r = 0; r < s.boardWidth; r++) {
			for(int c = 0; c < s.boardWidth; c++) {
				if(s.board[r][c] == 'x') {
					xs++;
				}
				if(s.board[r][c] == 'o') {
					os++;
				}
			}
		}
		if(player == 'x') { //player=x,AI=o
			return os-xs; //minimax value
		} else { //player=o,AI=x
			return xs-os; //minimax value
		}
	}
	
	public State initialState(int numRows, int numCols){

		char[][] board = new char[numRows][numCols];
		char[] letters = {'a','b','c','d','e','f','g','h'};

		for(int r = 0; r < numRows; r++) {
			for(int c = 0; c < numCols; c++) {
				if((r == board.length/2 && c == board.length/2) || 
						(r == board.length/2 - 1 && c == board.length/2 - 1)) {

					board[r][c] = 'o';

				} else if((r == board.length/2 - 1 && c == board.length/2) || 
						(r == board.length/2 && c == board.length/2 - 1)) {

					board[r][c] = 'x';

				} else {
					board[r][c] = 'e';
				}
			}
		}

		State init =  new State(board, 0);
		init.printState();
		return init;
	}
}



