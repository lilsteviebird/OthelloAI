import java.util.Scanner;
public class main {
	static char player, AI;
	static String input;
	static int row,col;
	static boolean playerTurn;
	static Scanner scanner = new Scanner(System.in); 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		  
        // String input 
		player = 'e';
		AI = 'e';
		
		System.out.println("Choose your Game:");
		System.out.println("1. Small 4x4 Reversi");
		System.out.println("2. Standard 8x8 Reversi");
		Game g1 = null;
		int gameType = scanner.nextInt();
		System.out.println("Dark is always first to move.");
		System.out.println("Do you want to play DARK (x) or LIGHT (o)?");
		char turn = scanner.next().charAt(0);
		
		if(turn == 'x') {
			player = 'x';
			AI = 'o';
			playerTurn = true;
		} else if(turn == 'o') {
			player = 'o';
			player = 'x';
			playerTurn = false;
		}
		
		if(gameType == 1) {
			g1 = createGame(player, AI, 4);
		} else if(gameType == 2) {
			g1 = createGame(player, AI, 8);
		}
		runGame(g1);
		
	}
	
	public static Game createGame(char player, char AI, int boardWidth) {
		return new Game(player, AI, boardWidth);
	}
	
	public static void runGame(Game game) {
		while(true) {
			if(playerTurn) {
				System.out.println("What would you like your next move to be: ");
				input = scanner.next();
				col = letterToNumber(input.charAt(0));
				row = Character.getNumericValue(input.charAt(1))-1;
				
				playerMove(game, row, col);
				playerTurn = false;
				System.out.println("Now it's the AI's Turn to Move!");
				System.out.println("");
			} else {
				AIMove(game);
				playerTurn = true;
				System.out.println("Now it's Your Turn to Move!");
			}
		}
	}
	
	public static void playerMove(Game g, int moveRow, int moveCol) {
		if(g.currentState.checkValidity(moveRow, moveCol, player)) {
			g.updateBoardPlayer(moveRow, moveCol, player);
		} else {
			System.out.println("That was a bad input, try again.");
			g.currentState.printState();
			runGame(g);
		}
	}
	public static void AIMove(Game g) {
		if(g.boardWidth == 4) {
			g.updateBoardAI();
		} else if(g.boardWidth == 8) {
			g.updateBoardAIAB();
		}
	}
	
	
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
