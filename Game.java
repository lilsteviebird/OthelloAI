import java.util.ArrayList;
public class Game {
	public char player, AI; //x vs o color
	public int boardWidth;
	public State currentState;
	public State childState;
	public int tempMinimax;
//	public ArrayList<State> childStates;
	
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
		//currentState = minimaxDecision(currentState);
		
		if(findBestChild(currentState) != null) {
			System.out.println("best child");
			currentState = findBestChild(currentState);
		} else {
			System.out.println("minimaxed child");
			currentState = minimaxDecision(currentState);
		}
		System.out.println("Current State: \n");
		currentState.printState();
	}
	public State findBestChild(State s) {
		return s.minMaxedChild;
	}

	//functions as maxValue but is the first step and returns a state instead of the minimax utility
	public State minimaxDecision(State s) {
		
		s.minimax = -10000;
		s.childStates = getAIChildStates(s);
		for(State child: s.childStates) {
			
//			System.out.println("Possible Move by AI: \n");
//			child.printState();
			
			child.minimax = minValue(child);
			
//			System.out.println(child.minimax+" \n");
			
			if(child.minimax > s.minimax) {
				s.minMaxedChild = child;
				s.minimax = child.minimax;
			}
			
		}
		return s.minMaxedChild;
	}
	
	//finds and returns the minimum (lowest) utility from the best maxValue child generated from getAIChildStates(s)
	public int minValue (State s) {
		//if terminalAI && terminalPlayer are true. return s.minimax = utility(s) and return s.minimax
		if(s.terminalAI && s.terminalPlayer) {
			//System.out.println("No More moves:"+(counter++));
			return s.minimax = utility(s);
		}
		
		s.minimax = 10000;
		
		//if getPlayerChildStates(s) is null, terminalPlayer is true. call maxValue(s).
		if(getPlayerChildStates(s).isEmpty()) {
			s.terminalPlayer = true;
			maxValue(s);
		}
		s.childStates = getPlayerChildStates(s);
		
		for(State child: s.childStates) {
			
//			System.out.println("Possible Move by Player: \n");
//			child.printState();
			
			child.minimax = maxValue(child);
			
//			child.minimax = utility(child);
			
//			System.out.println(child.minimax+"\n");
			
			if(child.minimax < s.minimax) {
				s.minMaxedChild = child;
				s.minimax = child.minimax;
			}
			
		}
		return s.minimax;
	}
	
	
	//finds and returns the maximum (highest) utility from the best minValue child generated from getPlayerChildStates(s)
	public int maxValue (State s) {
		//if terminalAI && terminalPlayer are true. return s.minimax = utility(s) and return s.minimax
		if(s.terminalAI && s.terminalPlayer) {
			//System.out.println("No More moves:"+(counter++));
			return s.minimax = utility(s);
		}
		
		s.minimax = -10000;
		
		//if getAIChildStates(s) is null, terminalAI is true. call minValue(s).
		if(getAIChildStates(s).isEmpty()) {
			s.terminalAI = true;
			minValue(s);
		}
		
		s.childStates = getAIChildStates(s);
		for(State child: s.childStates) {
			
//			System.out.println("Possible Move by AI: \n");
//			child.printState();
			
			child.minimax = minValue(child);
			
//			child.minimax = utility(child);
			
//			System.out.println(child.minimax+"\n");
			
			
			if(child.minimax > s.minimax) {
				s.minMaxedChild = child;
				s.minimax = child.minimax;
			}
			
		}
		
		
		return s.minimax;
	}
	


	
	//Obtains all the possible player moves from a state s.
	public ArrayList<State> getPlayerChildStates(State s){
		
		for(int r = 0; r < boardWidth; r++) {
			for(int c = 0; c < boardWidth; c++) {
				childState = copyBoard(s);
				if(childState.board[r][c] == 'e') {
					if(childState.checkValidity(r,c,player)) {
						
						childState.board[r][c] = player;
						childState = childState.flipPieces(r,c,player);
						
						s.childStates.add(childState);
					}
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
	
	
	//Obtains all the possible AI moves from a state s.
	public ArrayList<State> getAIChildStates(State s) {
		for(int r = 0; r < boardWidth; r++) {
			for(int c = 0; c < boardWidth; c++) {
				//System.out.println("Testing "+c+":"+r+" with "+AI);
				childState = copyBoard(s);
				if(childState.board[r][c] == 'e') {
					if(childState.checkValidity(r,c,AI)) {
					
						childState.board[r][c] = AI;
						childState = childState.flipPieces(r,c,AI);
					
						s.childStates.add(childState);
					}
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
