package CricketGamingProjectWithSpringApplication;

import java.io.IOException;
import java.util.Scanner;


public class CricketGamingProjectWithSpringApplication {

	public static void main(String[] args) {
		welcomeScreen();
	}

	private static void welcomeScreen() {
		System.out.println("***** Welcome to Amritesh's Gaming Arena *****");
		System.out.println(" 1. Enter");
		System.out.println(" 2. Exit");
		System.out.println(" Choose your option --> ");
		Scanner scanner = new Scanner(System.in);
		int res = scanner.nextInt();

		switch (res){
			case 1:
				try{
					Runtime.getRuntime().exec("clear");
				}
				catch (IOException ex){
					ex.printStackTrace();
				}
				chooseGameTypeScreen();
				break;
			case 2:
				System.exit(0);
			default:
				System.out.println("Choose valid option");
				welcomeScreen();
				break;
		}

	}

	private static void chooseGameTypeScreen() {
		System.out.println("***** Choose type of game you want to play *****");
		System.out.println(" 1. Outdoor Games");
		System.out.println(" 2. Indoor Games");
		System.out.println(" Choose your option --> ");
		Scanner scanner = new Scanner(System.in);
		int res = scanner.nextInt();

		switch (res){
			case 1:
				try{
					Runtime.getRuntime().exec("clear");
				}
				catch (IOException ex){
					ex.printStackTrace();
				}
				chooseOutdoorGamesScreen();
				break;
			case 2:
				try{
					Runtime.getRuntime().exec("clear");
				}
				catch (IOException ex){
					ex.printStackTrace();
				}
				chooseOutdoorGamesScreen();
				break;
			default:
				System.out.println("Choose valid option");
				chooseGameTypeScreen();
				break;
		}
	}

	private static void chooseOutdoorGamesScreen() {
		System.out.println("***** Choose game you want to play *****");
		System.out.println(" 1. Cricket");
		System.out.println(" 2. Back");
		System.out.println(" Choose your option --> ");
		Scanner scanner = new Scanner(System.in);
		int res = scanner.nextInt();

		switch (res){
			case 1:
				CricketGame cricketGame = new CricketGame();
				playGameScreen(cricketGame);
				break;
			case 2:
				try{
					Runtime.getRuntime().exec("clear");
				}
				catch (IOException ex){
					ex.printStackTrace();
				}
				chooseGameTypeScreen();
				break;
			default:
				System.out.println("Choose valid option");
				chooseOutdoorGamesScreen();
				break;
		}
	}

	private static void playGameScreen(Games game) {
		game.initializeGame();
	}


}
