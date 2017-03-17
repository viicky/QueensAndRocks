import java.awt.Dimension;
import java.awt.DisplayMode;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

import gameElements.Board;
import gameElements.Game;
import gameElements.Player;
import graphics.GameUI;


public class Main {

	private Main() {
	}
	
	private static void display(Board b){
		GameUI gui = new GameUI(b);
		gui.launch();
	}
	
	private static void testSolo(Board board){
		
		
		board.placeQueen(0, 0);
		
		System.out.println(board.toStringAccess());
	}
	
	
	private static void testComputerAux(Board board){
		{
			Date d = new Date();
			long t = d.getTime();
			String res = "";
			try{
				res = board.solutionSteps(board);
				//System.out.println("solution :\n" + res);
				System.out.println("il y a une solution");
			}
			catch(NoSuchElementException e){
				System.out.println("Pas de solution");
				System.out.println(res);
			}
			d = new Date();
			System.out.println("Temps mis pour " + board.getSize()+ " : " + (d.getTime() - t));
		}
		{
			Date d = new Date();
			long t = d.getTime();
			String res = "";
			try{
				res = board.solutionSteps2(board);
				//System.out.println("solution :\n" + res);
				System.out.println("il y a une solution");
			}
			catch(NoSuchElementException e){
				System.out.println("Pas de solution");
				System.out.println(res);
			}
			d = new Date();
			System.out.println("Temps mis pour " + board.getSize()+ " (2) : " + (d.getTime() - t));
		}
		{
			Date d = new Date();
			long t = d.getTime();
			String res = "";
			try{
				res = board.solutionStepsArray(board.BoardToArray(board));
				//System.out.println("solution :\n" + res);
				System.out.println("il y a une solution");
			}
			catch(NoSuchElementException e){
				System.out.println("Pas de solution");
				System.out.println(res);
			}
			d = new Date();
			System.out.println("Temps mis pour " + board.getSize()+ " (3) : " + (d.getTime() - t));
		}
		
	}
	
	
	private static void testComputer(Board board){
		for(int i=3; i <= 4; i++){
			//System.gc();
			board = new Board(board.getGame(), i);
			testComputerAux(board);
		}
	}
	
	
	private static void testBoardArray(Board board){
		board.placeQueen(0, 0);
		board.placeQueen(1,  2);
		System.out.println("board : \n"+board.toString() + "\n");
		
		int[] tmp = board.BoardToArray(board);
		System.out.println("array :\n");
		for(int i=0; i < tmp.length; i++){
			System.out.println(tmp[i] + "\n");
		}
			
			
		System.out.println("conversion double :\n"+board.arrayToBoard(board.BoardToArray(board)));
	}
	
	private static void testClone(Board b){
		Game g = b.getGame();
		
		//assert(b.placeQueen2(0, 0, g.getPlayer0()));
		//assert(!b.placeQueen2(3, 3, g.getPlayer1()));
		//assert(b.placeQueen2(1,  2,  g.getPlayer1()));
		
		//assert(b.placeRock2(0, 0, g.getPlayer0()));
		//assert(b.placeRock2(1, 1, g.getPlayer1()));
		//assert(b.placeRock2(2,  2,  g.getPlayer0()));
		//assert(b.placeRock2(3,  3,  g.getPlayer1()));

		System.out.println(b.isAccessible2(1,0, new Player(1)));
		System.out.println(b.toStringAccess());
		System.out.println(b.toStringAccess2(g.getPlayer0()));
		System.out.println(b.toStringAccess2(g.getPlayer1()));
	}

	public static void test2Player(Board b){
		Player p1 = new Player(0);
		Player p2 = new Player(1);
		Player pActu = p1;
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		int lig, col;
		char action;
		
		while(true){
			b.toStringAccess2(pActu);
			System.out.println("Saisir ligne colonne action (q/r) ou -1 pour quitter");
			lig = sc.nextInt();
			if(lig == -1) {break;}
			col  = sc.nextInt();
			action = (char) sc.nextShort();
			
			if(action == 'q'){
				b.placeQueen2(col, lig, pActu);
			}else{
				b.placeRock2(col, lig, pActu);
			}
			
			pActu = pActu.getNumber() == 0 ? p2 : p1;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("�a tourne");
		Game g = new Game();
		g.setColorMode("wb");
		Board board = new Board(g, 4);
		
		//test2Player(board);
		
		//testSolo(board);
		//testComputer(board);
		//testBoardArray(board);
		//testClone(board);
		
		display(board);
		System.out.println("�a tourne");
	}

}
