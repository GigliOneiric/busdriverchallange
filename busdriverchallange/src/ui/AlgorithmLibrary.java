package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import algorithm.Bruteforce;
import algorithm.GeneticAlgorithm;
import algorithm.ParticleSwarmOptimization;
import algorithm.RandomWalk;
import algorithm.SimulatedAnnealing;
import algorithm.TabuSearch;
import model.Decoder;
import model.Default;
import model.Solution;

/**
 * @author Tobias Stelter This class implement the algorithms.
 */

public class AlgorithmLibrary {

	private int[][] matrix;
	private Solution solutionObj = new Solution();

	InputStreamReader isr = new InputStreamReader(System.in);
	BufferedReader br = new BufferedReader(isr);

	/**
	 * Selects the algorithm
	 * 
	 * @return matrix
	 */

	public void selectAlgorithm() {

		int algorithmNumber = 0;

		Printer.printAlgorithmListConsole();

		algorithmNumber = readInt();

		Printer.printEmptyRow();

		if (algorithmNumber == 1) {
			
			Decoder.encodeMatrix(Decoder.radomString(Decoder.decodeMatrix()));
			//selectJustZeros();

		} else if (algorithmNumber == 2) {

			selectDefault();

		} else if (algorithmNumber == 3) {

			selectBruteforce();

		} else if (algorithmNumber == 4) {

			selectRandomWalk();

		} else if (algorithmNumber == 5) {

			selectGeneticAlgorithm();

		} else if (algorithmNumber == 6) {

			selectParticleSwarmOptimization();

		} else if (algorithmNumber == 7) {

			selectTabuSearch();

		} else if (algorithmNumber == 8) {

			selectSimulatedAnnealing();

		} else {
			Printer.printNoAlgorithmSelected();

		}

	}

	public void selectJustZeros() {
		this.matrix = Default.matrixZeros;
		this.solutionObj = new Solution(this.matrix);

		Printer.printPointsConsole(this.solutionObj.getPoints());
		Printer.printValidSolutionConsole(this.solutionObj.getValidSolution());
		Printer.printMatrixConsole(this.solutionObj.getMatrix());

	}

	public void selectDefault() {
		this.matrix = Default.matrix;
		this.solutionObj = new Solution(this.matrix);

		Printer.printPointsConsole(this.solutionObj.getPoints());
		Printer.printValidSolutionConsole(this.solutionObj.getValidSolution());
		Printer.printMatrixConsole(this.solutionObj.getMatrix());

	}

	public void selectBruteforce() {
		Bruteforce b = new Bruteforce();
		this.solutionObj = b.brutforce();

		Printer.printPointsConsole(this.solutionObj.getPoints());
		Printer.printValidSolutionConsole(this.solutionObj.getValidSolution());
		Printer.printMatrixConsole(this.solutionObj.getMatrix());

	}

	public void selectRandomWalk() {
		this.solutionObj = RandomWalk.randomWalk();

		Printer.printPointsConsole(this.solutionObj.getPoints());
		Printer.printValidSolutionConsole(this.solutionObj.getValidSolution());
		Printer.printMatrixConsole(this.solutionObj.getMatrix());

	}

	public void selectGeneticAlgorithm() {

		int generationSize = 0;
		int populationSize = 0;
		int selection = 0;
		int crossovermethod = 0;
		int crossoverPoint = 0;
		int crossoverPointTWO = 0;
		double mutationRate = 0;
		int tournamentSize = 0;
		int children = 0;
		int replacement = 0;
		int numberOfReplacements = 0;
		int swapsPerRow = 0;
		int mutationMethod = 0;

		Printer.printGeneticAlgorithmPopulationSizeConsole();
		populationSize = readInt();

		Printer.printGeneticAlgorithmGenerationSizeConsole();
		generationSize = readInt();

		Printer.printGeneticAlgorithmChildrenConsole();
		children = readInt();

		Printer.printGeneticAlgorithmParentsConsole();
		selection = readInt();
		if (selection == 2) {
			Printer.printGeneticAlgorithmTournamentSize();
			tournamentSize = readInt();
		}

		Printer.printGeneticAlgorithmCrossoverMethodConsole();
		crossovermethod = readInt();

		if (crossovermethod == 1) {
			Printer.printGeneticAlgorithmSinglePointCrossoverConsole();
			crossoverPoint = readInt();
		} 
		if (crossovermethod == 2) {
			Printer.printGeneticAlgorithmTwoPointCrossoverFirstPointConsole();
			crossoverPoint = readInt();
			Printer.printGeneticAlgorithmTwoPointCrossoverSecondPointConsole();
			crossoverPointTWO = readInt();
		}

		Printer.printGeneticAlgorithmMutationConsole();
		mutationMethod = readInt();

		if (mutationMethod == 1 || mutationMethod == 3) {
			Printer.printGeneticAlgorithmMutationRateConsole();
			mutationRate = readDouble();
		}
		if (mutationMethod == 2) {
			Printer.printGeneticAlgorithmMutationSwapConsole();
			swapsPerRow = readInt();
		}

		Printer.printGeneticAlgorithmReplacementConsole();
		replacement = readInt();

		if (replacement == 2) {
			Printer.printGeneticAlgorithmReplacementElitesConsole();
			numberOfReplacements = readInt();
		}

		Printer.printEmptyRow();

		GeneticAlgorithm g = new GeneticAlgorithm();
		this.solutionObj = g.geneticAlgorithm(generationSize, populationSize, selection, crossovermethod,
				crossoverPoint, crossoverPointTWO, mutationRate, tournamentSize, children, replacement,
				numberOfReplacements, swapsPerRow, mutationMethod);

		Printer.printEmptyRow();
		Printer.printPointsConsole(this.solutionObj.getPoints());
		Printer.printValidSolutionConsole(this.solutionObj.getValidSolution());
		Printer.printMatrixConsole(this.solutionObj.getMatrix());

	}

	private void selectParticleSwarmOptimization() {

		ParticleSwarmOptimization p = new ParticleSwarmOptimization();
		this.solutionObj = p.particleSwarmOptimization();

		Printer.printPointsConsole(this.solutionObj.getPoints());
		Printer.printValidSolutionConsole(this.solutionObj.getValidSolution());
		Printer.printMatrixConsole(this.solutionObj.getMatrix());

	}

	private void selectTabuSearch() {

		TabuSearch t = new TabuSearch();
		this.solutionObj = t.tabuSearch();

		Printer.printPointsConsole(this.solutionObj.getPoints());
		Printer.printValidSolutionConsole(this.solutionObj.getValidSolution());
		Printer.printMatrixConsole(this.solutionObj.getMatrix());
	}

	private void selectSimulatedAnnealing() {

		SimulatedAnnealing s = new SimulatedAnnealing();
		this.solutionObj = s.simulatedAnnealing();

		Printer.printPointsConsole(this.solutionObj.getPoints());
		Printer.printValidSolutionConsole(this.solutionObj.getValidSolution());
		Printer.printMatrixConsole(this.solutionObj.getMatrix());
	}

	/**
	 * Reads a single int
	 * 
	 * @return number
	 */

	public int readInt() {
		String input;
		int number = 0;

		try {
			input = br.readLine();
			number = Integer.parseInt(input);
		} catch (IOException e) {
			// To do
		} catch (NumberFormatException e) {
			// To do
		}
		return number;
	}

	/**
	 * Reads a single double
	 * 
	 * @return number
	 */

	public double readDouble() {
		String input;
		double number = 0;

		try {
			input = br.readLine().replaceAll(",", ".");
			number = Double.parseDouble(input);
		} catch (IOException e) {
			// To do
		} catch (NumberFormatException e) {
			// To do
		}
		return number;
	}

}
