import java.awt.Dimension;
import java.awt.DisplayMode;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

import gameElements.Board;
import gameElements.Eval;
import gameElements.Eval0;
import gameElements.Eval1;
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
		int lig, col;
		char action;
		
		while(true){
			System.out.println(b.toStringAccess2(pActu));
			System.out.println("score p1 : " + b.getScore(p1));
			
			System.out.println("Saisir ligne colonne action (q/r) ou -1 pour quitter");
			lig = sc.nextInt();
			if(lig == -1) {
				sc.close();
				break;
			}
			col  = sc.nextInt();
			action = sc.next().charAt(0);
			
			System.out.println(action);
			
			if(action == 'q'){
				b.placeQueen2(col, lig, pActu);
			}else{
				b.placeRock2(col, lig, pActu);
			}
			
			pActu = pActu.getNumber() == 0 ? p2 : p1;
		}
	}
	
	
	public static void testPlayerVsComputer(Board b){
		Player p1 = new Player(0);
		Player p2 = new Player(1);
		Player pActu = p1;
		Scanner sc = new Scanner(System.in);
		int lig, col;
		char action;
		boolean possible;
		
		while(true){
			System.out.println(b.toStringAccess2(pActu));
			
			if(pActu.getNumber() == 0){ //c'est au joueur
				System.out.println("Saisir ligne colonne action (q/r) ou -1 pour quitter");
				lig = sc.nextInt();
				if(lig == -1) {
					sc.close();
					break;
				}
				col  = sc.nextInt();
				action = sc.next().charAt(0);
				
				if(action == 'q'){
					possible = b.placeQueen2(col, lig, pActu);
				}else{
					possible = b.placeRock2(col, lig, pActu);
				}
				if(!possible){
					System.out.println("Action impossible!");
				}
			}
			else{ //c'est � l'ordi
				b = b.minimax(b, pActu, 2, new Eval0());
			}
			
			pActu = pActu.getNumber() == 0 ? p2 : p1;
		}
	}
	
	
	public static void testComputerVsComputer(Board b){
		Player pActu = b.getGame().getPlayer0();
		while(!b.isFinal(pActu)){
			b = b.minimax(b, pActu, 4, new Eval0());
			if(b == null){
				System.out.println("fini");
				break;
			}
			
			pActu = b.getGame().otherPlayer(pActu);
			
			System.out.println(b.toStringAccess2(pActu));
		}
		
	}
	
	
	
	public static void testScore(Board b){
		Player p0 = b.getGame().getPlayer0();
		Player p1 = b.getGame().getPlayer1();
		
		//placer un rock en 0, 0
		assert(b.getScore(p0) == 0);
		assert(b.placeRock2(0, 0, p0));
		assert(b.getScore(p0) == 2);
		
		//placer une reine en 2, 2
		assert(b.getScore(p1) == 0);
		assert(b.placeQueen2(2, 2, p1));
		assert(b.getScore(p1) == 5);
		
		//placer un rock en 1, 1
		assert(b.getScore(p0) == 2);
		assert(b.placeRock2(1,  1, p0));
		assert(b.getScore(p0) == 4);
		
		//placer une reine en 1, 0
		assert(b.getScore(p1) == 5);
		assert(b.placeQueen2(1,  0, p1));
		assert(b.getScore(p1) == 10);
		
		//placer un rock en 0, 1
		assert(b.getScore(p0) == 4);
		assert(b.placeRock2(0,  1, p0));
		assert(b.getScore(p0) == 6);
		//System.out.println(b.toStringAccess2(p1));
		
		//assert(false);
	}
	
	public static void testTempsEval0(Board board){
		Date d = new Date();
		long t = d.getTime();
		testComputerVsComputer(board);
		d = new Date();
		System.out.println("Temps mis pour " + board.getSize()+ " : " + (d.getTime() - t));
	}
	
	public static void testPlusieursParties(){
		Random rand = new Random();
		int nbParties = 4;
		int taille = rand.nextInt(4)+3;
		int profondeur = rand.nextInt(3)+1;
		while(nbParties != 0){
			Board b = new Board(new Game(), taille); 
			Player pActu = b.getGame().getPlayer0();
		while(!b.isFinal(pActu)){
			b = b.minimax(b, pActu, profondeur, new Eval1());
			if(b == null){
				System.out.println("fini");
				break;
			}
			
			pActu = b.getGame().otherPlayer(pActu);
			
			System.out.println(b.toStringAccess2(pActu));
		}
		nbParties--;
		taille = rand.nextInt(4)+3;
		profondeur = rand.nextInt(4)+1;
		}
	}
	
	public static void testComputeurPlusieursProfondeurs(Board b){
		Player pActu = b.getGame().getPlayer0();
		while(!b.isFinal(pActu)){
			if(pActu.getNumber() == 1)
				b = b.minimax(b, pActu, 2, new Eval0());
			else
				b = b.minimax(b, pActu, 5, new Eval0());
			if(b == null){
				System.out.println("fini");
				break;
			}
			
			pActu = b.getGame().otherPlayer(pActu);
			
			System.out.println(b.toStringAccess2(pActu));
		}	
	}
	
	public static void testComputerVsComputerEval1(Board b){
		Player pActu = b.getGame().getPlayer0();
		while(!b.isFinal(pActu)){
			b = b.minimax(b, pActu, 2, new Eval1());
			if(b == null){
				System.out.println("fini");
				break;
			}
			
			pActu = b.getGame().otherPlayer(pActu);
			
			System.out.println(b.toStringAccess2(pActu));
		}	
	}
	
	public static void testTempsEval1(Board board){
		Date d = new Date();
		long t = d.getTime();
		testComputerVsComputerEval1(board);
		d = new Date();
		System.out.println("Temps mis pour " + board.getSize()+ " : " + (d.getTime() - t));
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Game g = new Game();
		g.setColorMode("wb");
		Board board = new Board(g, 4);
		
		//testScore(board);
		
		//test2Player(board);
		
		//testSolo(board);
		//testComputer(board);
		//testBoardArray(board);
		//testClone(board);
		
		//testPlayerVsComputer(board);
		//testComputerVsComputer(board);
		//testComputerVsComputerEval1(board);
		//testTempsEval0(board);
		//testPlusieursParties();
		//testComputeurPlusieursProfondeurs(board);
		//board.minimax(board, g.getPlayer0(), 3, new Eval0());
		//testTempsEval1(board);
		
		display(board);
	}

}
