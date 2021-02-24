import java.util.ArrayList;
public class Game {
	public char player, AI; //x vs o color
	public int boardWidth;
	public State currentState;
	public State childState;
	public int tempMinimax;
	
	public Game(char player, char AI, int boardWidth) {
		this.player = player;
		this.AI = AI;
		this.boardWidth = boardWidth;
		currentState = initialState(boardWidth, boardWidth);
		
	}
	
	public void updateBoardPlayer(int row, int col, char color) {
		currentState.board[row][col] = color;
		currentState = currentState.flipPieces(row,col,color); //looks for any sandwich in direction and flips them
		currentState.printState();
	}
	public void updateBoardAI() {
		childState = findBestChild(currentState);
		if(childState != null) {
			currentState = childState;
		} else {
			currentState = minimaxDecision(currentState);
		}
		System.out.println("program go brrr");
		currentState.printState();
	}
	public State findBestChild(State currentState) {
		return currentState.minMaxedChild;
	}
	//a function that will be given a state and return its minMaxedChild for next move

	public State minimaxDecision(State s) {
		System.out.println("CurrentState: ");
		s.printState();
		s.childStates = getChildStatesMax(s);
		for(State child: s.childStates) {
			if(minValue(child).minimax > s.minimax) {
				s.minMaxedChild = child;
				s.minimax = child.minimax;
			}
		}
		return s.minMaxedChild; //maximum value of all valid states
	}
	
	public State minValue (State s) {
		if(s.terminalAI && s.terminalPlayer) { //should check if both booleans are true
			s.minimax = utility(s);
			return s;
		}
		s.minimax = 100000;
		s.childStates = getChildStatesMin(s);
		
		if(s.childStates.isEmpty()) {
			s.terminalAI = true; //not yet. Need to make sure neither participant can make a move
			//should set AI boolean true
			s.childStates = getChildStatesMax(s);
			for(State child: s.childStates) {
				if(minValue(child).minimax > s.minimax) {
					s.minMaxedChild = child;
					s.minimax = child.minimax;
				}
			}
		} else {
			for(State child: s.childStates) {
				if(maxValue(child).minimax < s.minimax) {
					s.minMaxedChild = child;
					s.minimax = child.minimax;
				}
			}
		}
		return s;
	}
	
	public State maxValue (State s) {
		if(s.terminalAI && s.terminalPlayer) { //should check if both booleans are true
			s.minimax = utility(s);
			return s;
		}
		s.minimax = -100000;
		s.childStates = getChildStatesMax(s);
		
		if(s.childStates.isEmpty()) {
			s.childStates = getChildStatesMin(s);
			for(State child: s.childStates) {
				if(maxValue(child).minimax < s.minimax) {
					s.minMaxedChild = child;
					s.minimax = child.minimax;
				}
			}
		} else {
			for(State child: s.childStates) {
				if(minValue(child).minimax > s.minimax) {
					s.minMaxedChild = child;
					s.minimax = child.minimax;
				}
			}
		}
		return s;
	}
	
	public ArrayList<State> getChildStatesMin(State s){
		
		for(int r = 0; r < boardWidth; r++) {
			for(int c = 0; c < boardWidth; c++) {
				if(s.checkValidity(r,c,player)) {
					childState = s;
					//childState = copyState(s);
					childState.board[r][c] = player;
					childState = childState.flipPieces(r,c,player);
					s.childStates.add(childState);
				}
			}
		}
		return s.childStates;
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
	
	public ArrayList<State> getChildStatesMax(State s) {
		//System.out.println("haha brrr ");
		for(int r = 0; r < boardWidth; r++) {
			for(int c = 0; c < boardWidth; c++) {
				System.out.println("Testing "+c+":"+r+" with "+AI);
				if(s.checkValidity(r,c,AI)) {
					System.out.println("CurrentState: ");
					s.printState();
					//these getChildStates methods are applying a move to the next child instead of the state s
					childState = copyBoard(s); //problematic statement is probably childState = s
					//childState = copyState(s);
					
					childState.board[r][c] = AI;
					//this line is first place where we see error. Most likely because childState is an alias of s.
					//to remedy this entire problem, you can probably make a copyState method that takes the board and its other parameters.
					
					childState = childState.flipPieces(r,c,AI);
					
					System.out.println("CurrentState after applications: ");
					s.printState();
					
					s.childStates.add(childState);
				}
			}
		}
		if(s.childStates.isEmpty()) {
			s.terminalPlayer = true; //not yet. Need to make sure neither participant can make a move
			//should set player boolean true
		}
		return s.childStates;
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
