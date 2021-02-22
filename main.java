import java.util.Scanner;
public class main {
	static String player, AI;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in); 
		  
        // String input 
        char r, col;
        int row;
		player = "x";
		AI = "o";
		//State initial = initialState(4,4);
		boolean playerTurn = true;
		Game g1 = new Game(player, AI, 4);
		
		while(true) {
			//have a if statement that determines if no more moves are possible
			if(playerTurn) {
				System.out.println("What would you like your next move to be: ");
				r = scanner.next().charAt(0);
				row = letterToNumber(r);
				col = scanner.next().charAt(1);
				playerMove(g1, row, col);
				playerTurn = false;
			} else {
				AIMove(g1);
			}
		}
		
		//for loop until game is complete
		// alternate between player turn and AI turn
		
	}
	public static void playerMove(Game g, int moveRow, int moveCol) {
		if(g.currentState.checkValidity(moveRow, moveCol, player)) {
			g.updateBoardPlayer(moveRow, moveCol, player);
		}
	}
	public static void AIMove(Game g) {
		g.updateBoardAI();
	}
	
	//PlayerMove(State currentBoard, String nextMove)
	// checks validity of move
	// returns new board state
	//AIMove(State currentBoard)
	// obtains new board state from calling minimax
	// returns new board state
	
	
	public static int letterToNumber(char row) {
		int r = -1;
		char[] letters = {'a','b','c','d','e','f','g','h'};
		for(char l: letters) {
			r++;
			if(row == l) {
				return r;
			}
		}
		return -1;
	}
}
