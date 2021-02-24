import java.util.ArrayList;
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
	//public ArrayList<Point> childStates = new ArrayList<Point>();
	public LinkedList<Point> childStates = new LinkedList<Point>();
	
	
	public Game(char player, char AI, int boardWidth) {
		this.player = player;
		this.AI = AI;
		this.boardWidth = boardWidth;
		currentState = initialState(boardWidth, boardWidth);
		
	}
	
	//Need to look hard at how to recursive back. Maybe adjust terminalAI and terminalPlayer
	//You're catching infinite loops at the bottom because you can't get back out.
	//Look at examples of recursion to get a good base case.
	//Maybe combine minValue and maxValue into one and make getChildStates(State, char) generic between player and AI
	
	
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
		temp = copyBoard(s);
		
		childStates = getChildStates(temp, AI);
		
		for(Point child: childStates) {
//			System.out.println("Possible Move by AI: \n");
			child.printPoint();
			
			System.out.println("max:"+childStates.size());
			
			temp = copyBoard(s);
			//calculate state that results from playing point child
			tempMinimax = minimax(temp.flipPieces(child.x, child.y, AI), player);
			
//			System.out.println(child.minimax+" \n");
			
			if(tempMinimax > s.minimax) {
				currentState.printState();
				s.minMaxedChild = currentState.flipPieces(child.x, child.y, AI);
				s.minimax = tempMinimax;
				maxEval = tempMinimax;
			}
			
		}
		return s.minMaxedChild;
	}
	
	public int minimax(State s, char turn) {
		temp = copyBoard(s);
		temp.printState();
		
		//if game over in state s (make a method to check if game over)
		if(getTerminal(temp, turn)) {
			temp.minimax = utility(temp);
			System.out.println("Utility: "+utility(temp));
			return utility(temp);
		}
		//s.minimax = utility(s);
		//return s.minimax
		if(turn == player) {
			minEval = 100000;
			childStates = getChildStates(temp,player);
			for(Point child: childStates) {
				System.out.println("min:"+childStates.size());
				System.out.println("Pushing into max recursion: "+child.x+" "+child.y);
				
				temp = copyBoard(s);
				tempMinimax = minimax(temp.flipPieces(child.x, child.y, player), AI);
				System.out.println("tempMinimaxMin: "+tempMinimax);
				
				if(tempMinimax < minEval) {
					//s.minMaxedChild = s.flipPieces(child.x, child.y, player);
					s.minimax = tempMinimax;
					minEval = tempMinimax;
					
				}
				System.out.println("min:"+childStates.size());
				System.out.println("do you exist ha "+ child.x+" "+child.y);
				//s.flipPieces(child.x, child.y, player), AI);
			}
			System.out.println("why here min");
			return minEval;
			
		} else if (turn == AI) {
			maxEval = -100000;
			childStates = getChildStates(temp,AI);
			for(Point child: childStates) {
				System.out.println("max:"+childStates.size());
				System.out.println("Pushing into min recursion: "+child.x+" "+child.y);
				
				temp = copyBoard(s);
				tempMinimax = minimax(temp.flipPieces(child.x, child.y, AI), player);
				System.out.println("tempMinimaxMax: "+tempMinimax);
				
				if(tempMinimax > maxEval) {
					//s.minMaxedChild = s.flipPieces(child.x, child.y, AI);
					s.minimax = tempMinimax;
					maxEval = tempMinimax;
					
				}
				System.out.println("max:"+childStates.size());
				System.out.println("do you exist ha "+ child.x+" "+child.y);
			}
			System.out.println("why here max");
			return maxEval;
			
		}
		System.out.println("Did you get here");
		
		return 0;
	}
	
	//Obtains all the possible player moves from a state s.
	public LinkedList<Point> getChildStates(State s, char turn){
		LinkedList<Point> childStatesLocal = new LinkedList<Point>();
		int failCounter = 0;
		for(int r = 0; r < boardWidth; r++) {
			for(int c = 0; c < boardWidth; c++) {
				//childState = copyBoard(s);
				if(s.board[r][c] == 'e') {
					if(s.checkValidity(r,c,turn)) {
						//childState.board[r][c] = player;
						//childState = childState.flipPieces(r,c,player);
						System.out.println("Oh fuck:"+r+" "+c);
						childStatesLocal.add(new Point(r,c));
					} else {
						failCounter++;
						//System.out.println("cvP: "+failCounter);
					}
				} else {
					failCounter++;
					//System.out.println("EmP: "+failCounter);
				}

			}
		}
		//			if(failCounter==boardWidth*boardWidth) {
		//				s.terminalPlayer = true;
		//			}
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
			System.out.println("failed");
			return true;
		}
		System.out.println("EmP: "+failCounter);
		return false;
	}
	
	
	
	
	
	//finds and returns the minimum (lowest) utility from the best maxValue child generated from getAIChildStates(s)
	public int minValue (State s) {
		//if terminalAI && terminalPlayer are true. return s.minimax = utility(s) and return s.minimax
		if(s.terminalAI && s.terminalPlayer) {
			System.out.println("No More moves:");
			s.minimax = utility(s);
			return s.minimax;
		}
		
		s.minimax = 10000;
		
		childStates.addAll(0, getPlayerChildStates(s));
		
//		if(s.terminalPlayer) {
//			maxValue(s);
//		}
		
		for(Point child: childStates) {
//			System.out.println("Possible Move by Player: \n");
			child.printPoint();
			
			System.out.println("min:"+childStates.size());
			
			tempMinimax = maxValue(s.flipPieces(child.x, child.y, player));
			
//			System.out.println(child.minimax+"\n");
			
			if(tempMinimax < s.minimax) {
				s.minMaxedChild = s.flipPieces(child.x, child.y, AI);
				s.minimax = tempMinimax;
			}
			
		}
		return s.minimax;
	}
	
	
	//finds and returns the maximum (highest) utility from the best minValue child generated from getPlayerChildStates(s)
	public int maxValue (State s) {
		//if terminalAI && terminalPlayer are true. return s.minimax = utility(s) and return s.minimax
		if(s.terminalAI && s.terminalPlayer) {
			System.out.println("No More moves:");
			s.minimax = utility(s);
			return s.minimax;
		}
		
		s.minimax = -10000;

		childStates.addAll(0, getAIChildStates(s));
		
//		if(s.terminalAI) {
//			minValue(s);
//		}
		
		
		for(Point child: childStates) {
//			System.out.println("Possible Move by AI: \n");
//			child.printState();
			
			System.out.println("max:"+childStates.size());
			
			//calculate state that results from playing point child
			tempMinimax = minValue(s.flipPieces(child.x, child.y, AI));
			
//			System.out.println(child.minimax+" \n");
			
			if(tempMinimax > s.minimax) {
				s.minMaxedChild = s.flipPieces(child.x, child.y, AI);
				s.minimax = tempMinimax;
			}
			
		}
		
		
		return s.minimax;
	}
	


	
	//Obtains all the possible player moves from a state s.
	public LinkedList<Point> getPlayerChildStates(State s){
		LinkedList<Point> childStatesLocal = new LinkedList<Point>();
		int failCounter = 0;
		for(int r = 0; r < boardWidth; r++) {
			for(int c = 0; c < boardWidth; c++) {
				//childState = copyBoard(s);
				if(s.board[r][c] == 'e') {
					if(s.checkValidity(r,c,player)) {
						//childState.board[r][c] = player;
						//childState = childState.flipPieces(r,c,player);
						System.out.println("Oh fuck:"+r+" "+c);
						childStatesLocal.add(new Point(r,c));
					} else {
						failCounter++;
						//System.out.println("cvP: "+failCounter);
					}
				} else {
					failCounter++;
					//System.out.println("EmP: "+failCounter);
				}
				
			}
		}
		if(failCounter==boardWidth*boardWidth) {
			s.terminalPlayer = true;
		}
		return childStatesLocal;
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
	public LinkedList<Point> getAIChildStates(State s) {
		LinkedList<Point> childStatesLocal = new LinkedList<Point>();
		int failCounter = 0;
		//count how many squares faulted either if statement and if that equals boardWidth^2, set terminalAI true
		for(int r = 0; r < boardWidth; r++) {
			for(int c = 0; c < boardWidth; c++) {
				//System.out.println("Testing "+c+":"+r+" with "+AI); //childState = copyBoard(s);
				if(s.board[r][c] == 'e') {
					if(s.checkValidity(r,c,AI)) {
					
						//childState.board[r][c] = AI;
						//childState = childState.flipPieces(r,c,AI);
						System.out.println("Oh fuck:"+r+" "+c);
						childStatesLocal.add(new Point(r,c));
					} else {
						failCounter++;
						//System.out.println("cvAI: "+failCounter);
					}
				} else {
					failCounter++;
					//System.out.println("EmAI: "+failCounter);
				}
			}
		}
		if(failCounter==boardWidth*boardWidth) {
			s.terminalAI = true;
		}
		return childStatesLocal;
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
//	
//	public State minimaxDecision(State s) {
//		
//		s.minimax = -10000;
//		s.childStates = getAIChildStates(s);
//		for(State child: s.childStates) {
//			
////			System.out.println("Possible Move by AI: \n");
////			child.printState();
//			
//			child.minimax = minValue(child);
//			
////			System.out.println(child.minimax+" \n");
//			
//			if(child.minimax > s.minimax) {
//				s.minMaxedChild = child;
//				s.minimax = child.minimax;
//			}
//			
//		}
//		return s.minMaxedChild;
//	}
//	
//	//finds and returns the minimum (lowest) utility from the best maxValue child generated from getAIChildStates(s)
//	public int minValue (State s) {
//		//if terminalAI && terminalPlayer are true. return s.minimax = utility(s) and return s.minimax
//		if(s.terminalAI && s.terminalPlayer) {
//			//System.out.println("No More moves:"+(counter++));
//			return s.minimax = utility(s);
//		}
//		
//		s.minimax = 10000;
//		
//		//if getPlayerChildStates(s) is null, terminalPlayer is true. call maxValue(s).
//		if(getPlayerChildStates(s).isEmpty()) {
//			s.terminalPlayer = true;
//			maxValue(s);
//		}
//		s.childStates = getPlayerChildStates(s);
//		
//		for(State child: s.childStates) {
//			
////			System.out.println("Possible Move by Player: \n");
////			child.printState();
//			
//			child.minimax = maxValue(child);
//			
////			child.minimax = utility(child);
//			
////			System.out.println(child.minimax+"\n");
//			
//			if(child.minimax < s.minimax) {
//				s.minMaxedChild = child;
//				s.minimax = child.minimax;
//			}
//			
//		}
//		return s.minimax;
//	}
//	
//	
//	//finds and returns the maximum (highest) utility from the best minValue child generated from getPlayerChildStates(s)
//	public int maxValue (State s) {
//		//if terminalAI && terminalPlayer are true. return s.minimax = utility(s) and return s.minimax
//		if(s.terminalAI && s.terminalPlayer) {
//			//System.out.println("No More moves:"+(counter++));
//			return s.minimax = utility(s);
//		}
//		
//		s.minimax = -10000;
//		
//		//if getAIChildStates(s) is null, terminalAI is true. call minValue(s).
//		if(getAIChildStates(s).isEmpty()) {
//			s.terminalAI = true;
//			minValue(s);
//		}
//		
//		s.childStates = getAIChildStates(s);
//		for(State child: s.childStates) {
//			
////			System.out.println("Possible Move by AI: \n");
////			child.printState();
//			
//			child.minimax = minValue(child);
//			
////			child.minimax = utility(child);
//			
////			System.out.println(child.minimax+"\n");
//			
//			
//			if(child.minimax > s.minimax) {
//				s.minMaxedChild = child;
//				s.minimax = child.minimax;
//			}
//			
//		}
//		
//		
//		return s.minimax;
//	}
}
