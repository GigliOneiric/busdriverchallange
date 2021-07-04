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
import model.Solution;

public class Printer {

	public static void printAlgorithmListConsole() {
		System.out.println("Wählen Sie einen Algorithmus aus.");
		System.out.println("[1] Nullen");
		System.out.println("[2] Kein Algorithmus");
		System.out.println("[3] Kein Algorithmus (Datei)");
		System.out.println("[4] Brutforce");
		System.out.println("[5] Random Walk (Binär)");
		System.out.println("[6] Random Walk (Decoded)");
		System.out.println("[7] Swip Flop");
		System.out.println("[8] Genetischer Algorithmus (Binär)");
		System.out.println("[9] Genetischer Algorithmus (Decoded)");
		System.out.println("[10] Partikelschwarmoptimierung");
		System.out.println("[11] Tabu-Suche (Tabu search)");
		System.out.println("[12] Simulated Annealing");
		System.out.println("[13] GASA");
	}

	public static void printSwipFlopSelect() {
		System.out.println("Bitte wählen Sie die Option aus:");
		System.out.println("[1]  Swip");
		System.out.println("[2]  Flop");
	}
	
	public static void printSimulatedAnnealingOption() {
		System.out.println("Wie sollen Nachbarn erzeugt werden?");
		System.out.println("[1]  Flop (Alle Spalten sequentiell)");
		System.out.println("[2]  Flop (Zufällige Anzahl von Spalten mit zufällig gewählten Spalten)");
		System.out.println("[3]  Swip");
		System.out.println("[4]  Fahrertausch");
	}

	public static void printSwipFlopNumber() {
		System.out.println("Bitte geben Sie ein wie oft die Option wiederholt werden soll:");
	}

	public static void printGeneticAlgorithmGenerationSizeConsole() {
		System.out.println("Geben Sie die Anzahl der Generationen ein.");
	}
	
	public static void printIteration() {
		System.out.println("Geben Sie die Anzahl der Iterationen ein.");
	}
	
	public static void printSimulatedAnnealingAcceptance() {
		System.out.println("Geben Sie das Akzeptanzkriterium ein.");
		System.out.println("[1]  Metropolis (Dynamic)");
		System.out.println("[2]  Metropolis (Fixed)");
		
	}
	
	public static void printSimulatedTemp() {
		System.out.println("Geben Sie die Temperatur ein.");		
	}
	
	public static void printSimulatedCoolingRate() {
		System.out.println("Geben Sie die Kühlrate ein.");		
	}

	public static void printGeneticAlgorithmPopulationSizeConsole() {
		System.out.println("Geben Sie die Populationsgröße ein.");
	}

	public static void printReaderRules(String filename) {
		System.out.println("Bitte nennen Sie Ihre Datei "+filename);
		System.out.println("Welches Verzeichnis wollen Sie benutzen?");
		System.out.println("[1] C:\\Users\\[Benutzername]\\Desktop");
		System.out.println("[2] Anderes");
		System.out.println("");
	}

	public static void printReaderPath() {
		System.out.println("Geben Sie das Verzeichnis ein");
	}
	
	public static void printRestrictions() {
		System.out.println("Wollen Sie zusätzliche Restrictions einlesen?");
		System.out.println("[1] Nein");
		System.out.println("[2] Ja");
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
		System.out.println("[1] Swip");
		System.out.println("[2] Flop");
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
	
	public static void changeToSA() {
		printEmptyRow();
		System.out.println("Wechsel zum Simulated Annealing Algorithmus");
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
	
	public static void printSimulatedAnnealingPoints(int iteration, int points) {
		System.out.println("Iteration: " + iteration + " | Punkte: " + points);
	}
	
	public static void printResult(Solution solutionObj) {
		printEmptyRow();
		printPointsConsole(solutionObj.getPoints());
	    printValidSolutionConsole(solutionObj.getValidSolution());
		printMatrixConsole(solutionObj.getMatrix());
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
