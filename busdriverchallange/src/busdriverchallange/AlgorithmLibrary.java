package busdriverchallange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Tobias Stelter This class implement the algorithms.
 */

/**
 * @author Tobias
 *
 */
public class AlgorithmLibrary {

	private int[][] matrix;
	private Solution solutionObj = new Solution();

	InputStreamReader isr = new InputStreamReader(System.in);
	BufferedReader br = new BufferedReader(isr);

	public void selectAlgorithm() {

		int algorithmNumber = 0;

		Printer.printAlgorithmListConsole();

		algorithmNumber = readInt();

		Printer.printEmptyRow();

		if (algorithmNumber == 1) {

			this.matrix = Default.matrixZeros;

			Solution solutionObj = new Solution(this.matrix);

			Printer.printPointsConsole(solutionObj.getPoints());
			Printer.printValidSolutionConsole(solutionObj.getValidSolution());
			Printer.printMatrixConsole(solutionObj.getMatrix());

		} else if (algorithmNumber == 2) {

			this.matrix = Default.matrix;
			
			Solution solutionObj = new Solution(this.matrix);

			Printer.printPointsConsole(solutionObj.getPoints());
			Printer.printValidSolutionConsole(solutionObj.getValidSolution());
			Printer.printMatrixConsole(solutionObj.getMatrix());

		} else if (algorithmNumber == 3) {

			int[][] initialMatrix = new int[Config.drivers * Config.routes][Config.totalDays * Config.shiftsPerDay];
			brutforce(initialMatrix, 0, 0, 0);

			Printer.printPointsConsole(this.solutionObj.getPoints());
			Printer.printValidSolutionConsole(this.solutionObj.getValidSolution());
			Printer.printMatrixConsole(this.solutionObj.getMatrix());

		} else if (algorithmNumber == 4) {

			randomWalk();

			Printer.printPointsConsole(this.solutionObj.getPoints());
			Printer.printValidSolutionConsole(this.solutionObj.getValidSolution());
			Printer.printMatrixConsole(this.solutionObj.getMatrix());

		} else if (algorithmNumber == 5) {

			int generationSize = 0;
			int populationSize = 0;
			int selection = 0;
			int crossovermethod = 0;
			int crossoverPoint = 0;
			int crossoverPointTWO = 0;
			int mutationRate = 0;
			int tournamentSize = 0;

			Printer.printGeneticAlgorithmGenerationSizeConsole();
			generationSize = readInt();

			Printer.printGeneticAlgorithmPopulationSizeConsole();
			populationSize = readInt();

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

			Printer.printGeneticAlgorithmMutationRateConsole();
			mutationRate = readInt();

			geneticAlgorithm(generationSize, populationSize, selection, crossovermethod, crossoverPoint,
					crossoverPointTWO, mutationRate, tournamentSize);

			Printer.printPointsConsole(solutionObj.getPoints());
			Printer.printValidSolutionConsole(solutionObj.getValidSolution());
			Printer.printMatrixConsole(solutionObj.getMatrix());

		} else {
			Printer.printNoAlgorithmSelected();

		}

	}

	private int brutforce(int[][] matrix, int currentRow, int currentColumn, int acc) {

		Solution s = new Solution(matrix);

		if (currentColumn == (Config.totalDays * Config.shiftsPerDay)) {

			if (s.getPoints() > this.solutionObj.getPoints() && s.getValidSolution() == true) {
				this.solutionObj = s;
			}

			return acc + 1;
		}
		if (currentRow == (Config.drivers * Config.routes)) {
			return brutforce(matrix, 0, currentColumn + 1, acc);
		}

		matrix[currentRow][currentColumn] = 1;
		acc += brutforce(matrix, currentRow + 1, currentColumn, 0);
		matrix[currentRow][currentColumn] = 0;

		return brutforce(matrix, currentRow + 1, currentColumn, acc);
	}

	private void randomWalk() {
		int[][] matrix = new int[Config.drivers * Config.routes][Config.totalDays * Config.shiftsPerDay];

		int col_len = matrix.length;
		int row_len = matrix[0].length;

		int max = 1;
		int min = 0;

		for (int i = 0; i < row_len; i++) {

			for (int j = 0; j < col_len; j++) {

				if (Restrictions.license[j][i] == 1 && Restrictions.holliday[j][i] == 0) {

					matrix[j][i] = getRandomInt(min, max);
				}
			}
		}

		this.solutionObj = new Solution(matrix);

	}

	private void geneticAlgorithm(int generationSize, int populationSize, int selection, int crossovermethod,
			int crossoverPoint, int crossoverPointTWO, int mutationRate, int tournamentSize) {

		List<Solution> population = new ArrayList<Solution>();

		int[][] maleParent;
		int[][] femaleParent;

		int[][] maleChild;
		int[][] femaleChild;

		// Generates the population of Size N
		for (int i = 0; i < populationSize; i++) {
			randomWalk();
			population.add(this.solutionObj);
		}

		if (crossovermethod == 1) {

			if (crossoverPoint == 0) {
				int max = (Config.totalDays * Config.shiftsPerDay) - 1;
				int min = 1;

				crossoverPoint = getRandomInt(min, max);
			}
		}

		for (int g = 0; g < generationSize; g++) {

			// Select parents
			List<int[][]> parents = selectParents(population, selection, tournamentSize);
			maleParent = parents.get(0);
			femaleParent = parents.get(1);

			// Crossover
			maleChild = generateMaleChild(maleParent, femaleParent, crossoverPoint, crossoverPointTWO, crossovermethod);
			femaleChild = generateFemaleChild(maleParent, femaleParent, crossoverPoint, crossoverPointTWO,
					crossovermethod);

			// Mutation
			maleChild = mutation(maleChild, crossoverPoint, crossoverPointTWO, crossovermethod, mutationRate);
			femaleChild = mutation(maleChild, crossoverPoint, crossoverPointTWO, crossovermethod, mutationRate);

			// Add the children to the population # incest allowed :D
			population.add(new Solution(maleChild));
			population.add(new Solution(femaleChild));

		}

		// Find best Child/Parent
		findBestParent(population);

	}

	private List<int[][]> selectParents(List<Solution> population, int selection, int tournamentSize) {
		List<int[][]> parents = new ArrayList<int[][]>();

		int[][] maleParent;
		int[][] femaleParent;

		if (selection == 1) {
			maleParent = population.get(getRandomInt(0, population.size() - 1)).getMatrix();
			parents.add(maleParent);

			do {
				femaleParent = population.get(getRandomInt(0, population.size() - 1)).getMatrix();

			} while (maleParent == femaleParent);

			parents.add(femaleParent);
		}
		if (selection == 2) {

			Random rand = new Random(System.currentTimeMillis());

			// Randomly select two parents via tournament selection.
			for (int i = 0; i < 2; i++) {

				int champion = -1;

				int winner = rand.nextInt(population.size());
				int[][] matrix = population.get(winner).getMatrix();
				parents.add(matrix);

				for (int j = 0; j < tournamentSize; j++) {

					int enemy;

					do {
						enemy = rand.nextInt(population.size());
					} while (winner == enemy || champion == enemy);

					if (population.get(enemy).getPoints() > population.get(winner).getPoints()) {
						matrix = population.get(enemy).getMatrix();
						parents.set(i, matrix);

						winner = enemy;
					}

				}
				champion = winner;
			}
		}

		return parents;
	}

	private int[][] generateMaleChild(int[][] maleParent, int[][] femaleParent, int crossoverPoint,
			int crossoverPointTWO, int crossovermethod) {
		int[][] maleChild = maleParent;

		int col_len = maleParent.length;
		int row_len = maleParent[0].length;

		if (crossovermethod == 1) {

			for (int i = 0; i < row_len; i++) {

				for (int j = crossoverPoint; j < col_len; j++) {

					maleChild[j][i] = femaleParent[j][i];
				}
			}
		}
		if (crossovermethod == 2) {
			for (int i = 0; i < row_len; i++) {

				for (int j = crossoverPoint; j < crossoverPointTWO; j++) {

					maleChild[j][i] = femaleParent[j][i];
				}
			}
		}

		return maleChild;
	}

	private int[][] generateFemaleChild(int[][] maleParent, int[][] femaleParent, int crossoverPoint,
			int crossoverPointTWO, int crossovermethod) {
		int[][] femaleChild = femaleParent;

		int col_len = femaleParent.length;
		int row_len = femaleParent[0].length;

		if (crossovermethod == 1) {

			for (int i = 0; i < row_len; i++) {

				for (int j = crossoverPoint; j < col_len; j++) {

					femaleChild[j][i] = maleParent[j][i];
				}
			}
		}
		if (crossovermethod == 2) {

			for (int i = 0; i < row_len; i++) {
				for (int j = crossoverPoint; j < crossoverPointTWO; j++) {

					femaleChild[j][i] = maleParent[j][i];
				}
			}

		}

		return femaleChild;
	}

	private int[][] mutation(int[][] matrix, int crossoverPoint, int crossoverPointTWO, int crossovermethod,
			int mutationRate) {

		int col_len = matrix.length;
		int row_len = matrix[0].length;

		if (crossovermethod == 1) {

			for (int i = 0; i < row_len; i++) {

				for (int j = crossoverPoint; j < col_len; j++) {

					int min = 0;
					int max = 100;

					int mutation = getRandomInt(min, max);

					if (mutation <= mutationRate) {

						int bit = matrix[j][i];

						if (bit == 0) {
							matrix[j][i] = 1;
						} else {
							matrix[j][i] = 0;
						}

					}

				}
			}
		}
		if (crossovermethod == 2) {

			for (int i = 0; i < row_len; i++) {

				for (int j = crossoverPoint; j < crossoverPointTWO; j++) {

					int min = 0;
					int max = 100;

					int mutation = getRandomInt(min, max);

					if (mutation <= mutationRate) {

						int bit = matrix[j][i];

						if (bit == 0) {
							matrix[j][i] = 1;
						} else {
							matrix[j][i] = 0;
						}

					}

				}
			}
		}

		return matrix;

	}

	public int[][] findBestParent(List<Solution> population) {

		Solution solutionObj = new Solution();

		for (int i = 0; i < population.size(); i++) {

			solutionObj = population.get(i);

			if (solutionObj.getPoints() > this.solutionObj.getPoints()) {
				this.solutionObj = solutionObj;
			}

		}

		return matrix;
	}

	public int getRandomInt(int min, int max) {

		int radomInt = (int) (Math.floor(Math.random() * (max - min + 1)) + min);

		return radomInt;

	}

	/**
	 * Reads a single Integer
	 * @return integer
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

}
