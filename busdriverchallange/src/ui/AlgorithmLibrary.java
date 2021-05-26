package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import algorithm.Bruteforce;
import algorithm.GeneticAlgorithmBinary;
import algorithm.GeneticAlgorithmEncoded;
import algorithm.ParticleSwarmOptimization;
import algorithm.RandomWalk;
import algorithm.SimulatedAnnealing;
import algorithm.SwipFlop;
import algorithm.TabuSearch;
import model.Config;
import model.Default;
import model.Solution;

/**
 * @author Tobias Stelter
 * @author Amir Razagh Khah,
 * @author Felix Harms
 * 
 *         This class implements the algorithms.
 * 
 */

public class AlgorithmLibrary {

	private int[][] matrix;
	private Solution solutionObj = new Solution();

	InputStreamReader isr = new InputStreamReader(System.in);
	BufferedReader br = new BufferedReader(isr);

	int generationSize = 0;
	int populationSize = 0;
	int selection = 0;
	int crossovermethod = 0;
	int crossoverPoint = 0;
	int crossoverPointTWO = 0;
	double mutationRate = 0;
	int tournamentSize = 0;
	int childrenNumber = 0;
	int replacementMethod = 0;
	int numberOfReplacements = 0;
	int swapsPerRow = 0;
	int mutationMethod = 0;
	String path = "";
	int pathOption = 0;
	int moreRestrictions = 0;

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

			selectJustZeros();

		} else if (algorithmNumber == 2) {

			selectDefault();

		} else if (algorithmNumber == 3) {

			selectReadFromFile();

		} else if (algorithmNumber == 4) {

			selectBruteforce();

		} else if (algorithmNumber == 5) {

			selectRandomWalkBinär();

		} else if (algorithmNumber == 6) {

			selectRandomWalkEncoded();

		} else if (algorithmNumber == 7) {

			selectSwapFlip();

		} else if (algorithmNumber == 8) {

			selectGeneticAlgorithmBinary();

		} else if (algorithmNumber == 9) {

			selectGeneticAlgorithmDecoded();

		} else if (algorithmNumber == 10) {

			selectParticleSwarmOptimization();

		} else if (algorithmNumber == 11) {

			selectTabuSearch();

		} else if (algorithmNumber == 12) {

			selectSimulatedAnnealing();

		} else {
			Printer.printNoAlgorithmSelected();

		}

	}

	public void selectJustZeros() {
		this.matrix = Default.matrixZeros;
		this.solutionObj = new Solution(this.matrix);

		Printer.printResult(this.solutionObj);

	}

	public void selectDefault() {
		this.matrix = Default.matrix;
		this.solutionObj = new Solution(this.matrix);

		Printer.printResult(this.solutionObj);

	}

	private void selectReadFromFile() {
		Printer.printReaderRules(Config.filenameMatrix);

		String path = "";
		int pathOption = readInt();

		if (pathOption == 2) {
			Printer.printReaderPath();
			path = readString();
		}

		this.matrix = Reader.readFile(path, pathOption, Config.filenameMatrix);
		this.solutionObj = new Solution(this.matrix);

		Printer.printResult(this.solutionObj);

	}

	public void selectBruteforce() {
		Bruteforce b = new Bruteforce();
		this.solutionObj = b.brutforce();

		Printer.printResult(this.solutionObj);

	}

	public void selectRandomWalkBinär() {
		this.solutionObj = RandomWalk.randomDecodedWalk();

		Printer.printResult(this.solutionObj);

	}

	private void selectRandomWalkEncoded() {
		moreRestrictions();

		this.solutionObj = RandomWalk.radomEncodedWalk(path, pathOption, moreRestrictions);

		Printer.printResult(this.solutionObj);

	}

	private void selectSwapFlip() {

		moreRestrictions();

		Printer.printSwipFlopSelect();
		int In = readInt();
		Printer.printEmptyRow();
		Printer.printSwipFlopNumber();
		int num = readInt();
		Printer.printEmptyRow();
		SwipFlop s = new SwipFlop();
		this.solutionObj = s.swipFlop(num, In, path, pathOption, moreRestrictions);

		Printer.printResult(this.solutionObj);

	}

	public void selectGeneticAlgorithmBinary() {

		Printer.printGeneticAlgorithmPopulationSizeConsole();
		populationSize = readInt();

		Printer.printGeneticAlgorithmGenerationSizeConsole();
		generationSize = readInt();

		Printer.printGeneticAlgorithmChildrenConsole();
		childrenNumber = readInt();

		Printer.printGeneticAlgorithmParentsConsole();
		selection = readInt();
		if (selection == 2) {
			Printer.printGeneticAlgorithmTournamentSize();
			tournamentSize = readInt();
		}

		Printer.printGeneticAlgorithmCrossoverMethodBinaryConsole();
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

		Printer.printGeneticAlgorithmMutationBinaryConsole();
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
		replacementMethod = readInt();

		if (replacementMethod == 2) {
			Printer.printGeneticAlgorithmReplacementElitesConsole();
			numberOfReplacements = readInt();
		}

		Printer.printEmptyRow();

		GeneticAlgorithmBinary g = new GeneticAlgorithmBinary();
		this.solutionObj = g.geneticAlgorithm(generationSize, populationSize, selection, crossovermethod,
				crossoverPoint, crossoverPointTWO, mutationRate, tournamentSize, childrenNumber, replacementMethod,
				numberOfReplacements, swapsPerRow, mutationMethod);

		Printer.printResult(this.solutionObj);

	}

	private void selectGeneticAlgorithmDecoded() {

		moreRestrictions();

		Printer.printGeneticAlgorithmPopulationSizeConsole();
		populationSize = readInt();

		Printer.printGeneticAlgorithmGenerationSizeConsole();
		generationSize = readInt();

		Printer.printGeneticAlgorithmChildrenConsole();
		childrenNumber = readInt();

		Printer.printGeneticAlgorithmParentsConsole();
		selection = readInt();
		if (selection == 2) {
			Printer.printGeneticAlgorithmTournamentSize();
			tournamentSize = readInt();
		}

		Printer.printGeneticAlgorithmCrossoverMethodDecodedConsole();
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

		Printer.printGeneticAlgorithmMutationEncodedConsole();
		mutationMethod = readInt();

		if (mutationMethod == 1 || mutationMethod == 2) {
			Printer.printGeneticAlgorithmMutationRateConsole();
			mutationRate = readDouble();
		}

		Printer.printGeneticAlgorithmReplacementConsole();
		replacementMethod = readInt();

		if (replacementMethod == 2) {
			Printer.printGeneticAlgorithmReplacementElitesConsole();
			numberOfReplacements = readInt();
		}

		Printer.printEmptyRow();

		GeneticAlgorithmEncoded g = new GeneticAlgorithmEncoded();
		this.solutionObj = g.geneticAlgorithmEncoded(generationSize, populationSize, selection, crossovermethod,
				crossoverPoint, crossoverPointTWO, mutationRate, tournamentSize, childrenNumber, replacementMethod,
				numberOfReplacements, mutationMethod, path, pathOption, moreRestrictions);

		Printer.printResult(this.solutionObj);

	}

	private void selectParticleSwarmOptimization() {

		ParticleSwarmOptimization p = new ParticleSwarmOptimization();
		this.solutionObj = p.particleSwarmOptimization(path, pathOption, moreRestrictions);

		Printer.printResult(this.solutionObj);

	}

	private void selectTabuSearch() {

		TabuSearch t = new TabuSearch();
		this.solutionObj = t.tabuSearch();

		Printer.printResult(this.solutionObj);
	}

	private void selectSimulatedAnnealing() {

		SimulatedAnnealing s = new SimulatedAnnealing();
		this.solutionObj = s.simulatedAnnealing();

		Printer.printResult(this.solutionObj);
	}

	private void moreRestrictions() {
		Printer.printRestrictions();
		this.moreRestrictions = readInt();

		if (moreRestrictions == 2) {
			Printer.printReaderRules(Config.filenameRestrictions);

			this.pathOption = readInt();

			if (this.pathOption == 2) {
				Printer.printReaderPath();
				this.path = readString();
			}
		}
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

	/**
	 * Reads a String
	 * 
	 * @return input
	 */

	public String readString() {
		String input = "";

		try {
			input = br.readLine();
		} catch (IOException e) {
			// To do
		}
		return input;

	}
}
