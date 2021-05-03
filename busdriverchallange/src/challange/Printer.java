package challange;

/**
 * @author Tobias Stelter
 * This class prints outputs.
 */

import java.util.Arrays;

public class Printer {

	public static void printAlgorithmListConsole() {
		System.out.println("W�hlen Sie einen Algorithmus aus.");
		System.out.println("[1] Nullen");
		System.out.println("[2] Kein Algorithmus");
		System.out.println("[3] Brutforce");
		System.out.println("[4] Random Walk");
		System.out.println("[5] Genetischer Algorithmus");
	}
	
	public static void printGeneticAlgorithmGenerationSizeConsole() {
		System.out.println("Geben Sie die Anzahl der Generationen ein.");
	}
	
	public static void printGeneticAlgorithmPopulationSizeConsole() {
		System.out.println("Geben Sie die Populationsgr��e ein.");
	}
	
	public static void printGeneticAlgorithmMutationRateConsole() {
		System.out.println("Geben Sie Wahrscheinlichkeit f�r eine Mutation ein.");
	}
	
	public static void printGeneticAlgorithmCrossoverMethodConsole() {
		System.out.println("Geben Sie Crossover-Methode ein.");
		System.out.println("[1] Single-Point");
		System.out.println("[2] Two-Point");
	}
	
	public static void printGeneticAlgorithmSinglePointCrossoverConsole() {
		System.out.println("Geben Sie die Spalte f�r die Rekobination ein (0 = zuf�llig)");
	}
	
	public static void printGeneticAlgorithmTwoPointCrossoverFirstPointConsole() {
		System.out.println("Geben Sie die erste Spalte f�r die Rekobination ein");
	}
	
	public static void printGeneticAlgorithmTwoPointCrossoverSecondPointConsole() {
		System.out.println("Geben Sie die zweite Spalte f�r die Rekobination ein");
	}
	
	public static void printGeneticAlgorithmParentsConsole() {
		System.out.println("W�hlen Sie die Option f�r die Ermittlung der Eltern aus");
		System.out.println("[1] Zuf�llig");
		System.out.println("[2] Tournament");
	}
	
	public static void printGeneticAlgorithmTournamentSize() {
		System.out.println("Geben Sie die Tunier-Gr��e ein.");
	}
	
	
	public static void printNoAlgorithmSelected() {
		System.out.println("Es wurde kein Algorithmus ausgew�hlt.");
	}
	
	public static void printEmptyRow() {
		System.out.println("");
	}

	public static void printError() {
		System.out.println("Fehler");
	}
	
	public static void printPointsConsole(int points) {
		System.out.println("Punkte: " + points);
	}

	public static void printValidSolutionConsole(boolean validSolution) {
		System.out.println("G�ltig: " + validSolution);
	}

	public static void printMatrixConsole(int matrix[][]) {
		int counter = 0;

		System.out.println();
		System.out.println("   " + Arrays.toString(Config.shiftNames).replaceAll("[\\.$|,|;|']", ""));

		for (int[] row : matrix) {

			System.out.print(Config.routeNames[counter] + " [ ");

			for (int j : row) {
				System.out.print(" " + j + "  ");
			}
			System.out.println("]");

			counter++;
		}
	}

}
