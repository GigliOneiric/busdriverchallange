package ui;

/**
 * @author Tobias Stelter
 * @author Amir Razagh Khah,
 * @author Felix Harms
 * 
 * This class prints outputs.
 */

import java.util.Arrays;

import model.Config;

public class Printer {

	public static void printAlgorithmListConsole() {
		System.out.println("Wählen Sie einen Algorithmus aus.");
		System.out.println("[1] Nullen");
		System.out.println("[2] Kein Algorithmus");
		System.out.println("[3] Brutforce");
		System.out.println("[4] Random Walk");
		System.out.println("[5] Swip Flip");
		System.out.println("[6] Genetischer Algorithmus (Binär)");
		System.out.println("[7] Genetischer Algorithmus (Decoded)");
		System.out.println("[8] Partikelschwarmoptimierung");
		System.out.println("[9] Tabu-Suche (Tabu search)");
		System.out.println("[10] Simulated Annealing");
	}

	public static void printGeneticAlgorithmGenerationSizeConsole() {
		System.out.println("Geben Sie die Anzahl der Generationen ein.");
	}

	public static void printGeneticAlgorithmPopulationSizeConsole() {
		System.out.println("Geben Sie die Populationsgröße ein.");
	}

	public static void printGeneticAlgorithmChildrenConsole() {
		System.out.println("Geben Sie die Anzahl der Kinder ein.");
	}

	public static void printGeneticAlgorithmReplacementConsole() {
		System.out.println("Geben Sie das Ersetzungsverfahren an");
		System.out.println("[1] General Replacement");
		System.out.println("[2] Principle of the Elites");
	}

	public static void printGeneticAlgorithmReplacementElitesConsole() {
		System.out.println(
				"Wie viele Individuen mit der schlechtesten Fitness aus der Elterngeneration sollen mit den Kindern ersetzt werden?");
	}

	public static void printGeneticAlgorithmMutationRateConsole() {
		System.out.println("Geben Sie Wahrscheinlichkeit für eine Mutation ein.");
	}

	public static void printGeneticAlgorithmCrossoverMethodBinaryConsole() {
		System.out.println("Geben Sie Crossover-Methode ein.");
		System.out.println("[1] Single-Point");
		System.out.println("[2] Two-Point");
		System.out.println("[3] Uniform");
		System.out.println("[4] Driver Day Block");
	}

	public static void printGeneticAlgorithmCrossoverMethodDecodedConsole() {
		System.out.println("Geben Sie Crossover-Methode ein.");
		System.out.println("[1] Single-Point");
		System.out.println("[2] Two-Point");
		System.out.println("[3] Uniform");
	}

	public static void printGeneticAlgorithmSinglePointCrossoverConsole() {
		System.out.println("Geben Sie die Spalte für die Rekobination ein (0 = zufällig)");
	}

	public static void printGeneticAlgorithmTwoPointCrossoverFirstPointConsole() {
		System.out.println("Geben Sie die erste Spalte für die Rekobination ein");
	}

	public static void printGeneticAlgorithmTwoPointCrossoverSecondPointConsole() {
		System.out.println("Geben Sie die zweite Spalte für die Rekobination ein");
	}

	public static void printGeneticAlgorithmParentsConsole() {
		System.out.println("Wählen Sie die Option für die Ermittlung der Eltern aus");
		System.out.println("[1] Zufällig");
		System.out.println("[2] Tournament");
	}

	public static void printGeneticAlgorithmMutationBinaryConsole() {
		System.out.println("Wählen Sie die Option für Mutation aus");
		System.out.println("[1] Bit Flip Mutation");
		System.out.println("[2] Swap Mutation");
		System.out.println("[3] Cheating Blocks Mutation");
	}

	public static void printGeneticAlgorithmMutationEncodedConsole() {
		System.out.println("Wählen Sie die Option für Mutation aus");
		System.out.println("[1] Standard Mutation");
	}

	public static void printGeneticAlgorithmMutationSwapConsole() {
		System.out.println("Geben Sie die Anzahl der Swaps pro Zeile ein");
	}

	public static void printGeneticAlgorithmTournamentSize() {
		System.out.println("Geben Sie die Tunier-Größe ein.");
	}

	public static void printNoAlgorithmSelected() {
		System.out.println("Es wurde kein Algorithmus ausgewählt.");
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
		System.out.println("Gültig: " + validSolution);
	}

	public static void printGeneticAlgorithmPointsPerGen(int generation, int points) {
		System.out.println("Generation: " + generation + " | Punkte: " + points);
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
