import java.util.Scanner;
public class main {
	static char player, AI;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in); 
		  
        // String input 
        String input = null;
        int row, col;
		player = 'x';
		AI = 'o';
		boolean playerTurn = true;
		
		
//		System.out.println("Choose your Game:");
//		System.out.println("1. Small 4x4 Reversi");
//		System.out.println("2. Standard 8x8 Reversi");
//		Game g1 = null;
//		int gameType = scanner.nextInt();
//		if(gameType == 1) {
//			g1 = new Game(player, AI, 4);
//		} else if(gameType == 2) {
//			g1 = new Game(player, AI, 8);
//		}
		Game g1 = new Game(player, AI, 4);
		
		while(true) {
			//have a if statement that determines if no more moves are possible
			//if currentState terminalAI and terminalPlayer are both true
			if(g1.currentState.terminalAI && g1.currentState.terminalPlayer) {
				break;
			}
			if(playerTurn) {
				System.out.println("What would you like your next move to be: ");
				input = scanner.next();
				col = letterToNumber(input.charAt(0));
				row = Character.getNumericValue(input.charAt(1))-1;
				
				System.out.println(input.charAt(0)+" "+row);
				playerMove(g1, row, col);
				playerTurn = false;
				System.out.println("AI's Turn");
				System.out.println("");
				System.out.println("");
			} else {
				AIMove(g1);
				playerTurn = true;
			}
		}
		
		//for loop until game is complete
		// alternate between player turn and AI turn
		
	}
	public static void playerMove(Game g, int moveRow, int moveCol) {
		System.out.println("validity check");
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
	
	
	public static int letterToNumber(char col) {
		int c = -1;
		char[] letters = {'a','b','c','d','e','f','g','h'};
		for(char l: letters) {
			c++;
			if(col == l) {
				return c;
			}
		}
		return -1;
	}
}
