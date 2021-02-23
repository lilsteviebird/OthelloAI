import java.util.ArrayList;
public class Game {
	public String player, AI; //x vs o color
	public int boardWidth;
	public State currentState, childState;
	public int tempMinimax;
	
	public Game(String player, String AI, int boardWidth) {
		this.player = player;
		this.AI = AI;
		this.boardWidth = boardWidth;
		currentState = initialState(boardWidth, boardWidth);
	}
	
	public void updateBoardPlayer(int row, int col, String color) {
		currentState.board[row][col].setColor(color);
		//currentState = flipPieces(row,col,color); //looks for any sandwich in direction and flips them
		currentState.printState();
	}
	public void updateBoardAI() {
		//childState = findBestChild(currentState);
		//if(childState != null) {
		//	currentState = childState;
		//else currentState = minimaxDecision(currentState);
		currentState.printState();
	}
	//findBestChild(currentState
	//a function that will be given a state and return its minMaxedChild for next move

	public State minimaxDecision(State s) {
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
				if(s.checkValidity(r,c,AI)) {
					childState = currentState;
					childState.board[r][c].setColor(AI);
					//childState = flipPieces(row,col,color);
					s.childStates.add(childState);
				}
			}
		}
		return s.childStates;
	}
	public ArrayList<State> getChildStatesMax(State s){
		
		for(int r = 0; r < boardWidth; r++) {
			for(int c = 0; c < boardWidth; c++) {
				if(s.checkValidity(r,c,player)) {
					childState = currentState;
					childState.board[r][c].setColor(player);
					//childState = flipPieces(row,col,color);
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
				if(s.board[r][c].getColor().compareTo("x")==0) {
					xs++;
				}
				if(s.board[r][c].getColor().compareTo("o")==0) {
					os++;
				}
			}
		}
		if(player.compareTo("x") == 0) { //player=x,AI=o
			return os-xs; //minimax value
		} else { //player=o,AI=x
			return xs-os; //minimax value
		}
	}
	
	public State initialState(int numRows, int numCols){

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

		State init =  new State(board, 0);
		init.printState();
		return init;
	}
}
