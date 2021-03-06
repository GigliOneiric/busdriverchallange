package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Config;
import model.Restrictions;
import model.Solution;
import ui.Printer;

/**
 * @author Tobias Stelter This class implements the binary genetic algorithm.
 */

public class GeneticAlgorithmBinary {

	/**
	 * Finds a solution using the binary genetic algorithm
	 * 
	 * @return matrix
	 */

	public Solution geneticAlgorithm(int generationSize, int populationSize, int selection, int crossovermethod,
			int crossoverPoint, int crossoverPointTWO, double mutationRate, int tournamentSize, int childrenNumber,
			int replacementMethod, int numberOfReplacements, int swapsPerRow, int mutationMethod) {

		Solution solutionObj = new Solution();

		int oldPoints = Integer.MIN_VALUE;

		List<Solution> initialPopulation = new ArrayList<Solution>();
		List<Solution> generation = new ArrayList<Solution>();

		int[][] maleParent = null;
		int[][] femaleParent = null;

		int[][] maleChild = null;
		int[][] femaleChild = null;

		// Generates the population of Size N * 100
		for (int i = 0; i < populationSize; i++) {
			solutionObj = RandomWalk.randomBinarydWalk();
			initialPopulation.add(solutionObj);
		}

		int startPoints = Solution.findBestSolution(initialPopulation).getPoints();
		Printer.printGeneticAlgorithmPointsPerGen(0, startPoints);

		for (int g = 1; g <= generationSize; g++) {

			generation = new ArrayList<Solution>();

			for (int k = 0; k < childrenNumber; k++) {

				if (crossovermethod == 1 && crossoverPoint == 0) {
					int max = (Config.totalDays * Config.shiftsPerDay) - 1;
					int min = 1;

					crossoverPoint = RandomWalk.getRandomInt(min, max);
				}

				// Select parents
				List<int[][]> parents = selectParents(initialPopulation, selection, tournamentSize);
				maleParent = parents.get(0);
				femaleParent = parents.get(1);

				// Crossover
				List<int[][]> children = generateChild(maleParent, femaleParent, crossoverPoint, crossoverPointTWO,
						crossovermethod);
				maleChild = children.get(0);
				femaleChild = children.get(1);

				// Mutation
				maleChild = mutation(maleChild, mutationRate, mutationMethod, swapsPerRow);
				femaleChild = mutation(femaleChild, mutationRate, mutationMethod, swapsPerRow);

				// Add the children to the population
				generation = replacement(initialPopulation, generation, maleChild, femaleChild, 1,
						numberOfReplacements);

			}

			if (replacementMethod == 2) {
				// Add the children to the population
				generation = replacement(initialPopulation, generation, maleChild, femaleChild, replacementMethod,
						numberOfReplacements);
			}

			int points = Solution.findBestSolution(generation).getPoints();

			if (points > oldPoints) {
				Printer.printGeneticAlgorithmPointsPerGen(g, points);
				oldPoints = points;
			}

			initialPopulation = generation;
		}

		return Solution.findBestSolution(initialPopulation);

	}

	/**
	 * Selects the parents for the genetic algorithm
	 * 
	 * @return matrix
	 */

	private List<int[][]> selectParents(List<Solution> population, int selection, int tournamentSize) {
		List<int[][]> parents = new ArrayList<int[][]>();

		// Randomly selection
		if (selection == 1) {
			parents = randomSelection(population);
		}

		// Randomly select two parents via tournament selection.
		if (selection == 2) {
			parents = tournamentSelection(population, tournamentSize);
		}

		return parents;
	}

	private List<int[][]> randomSelection(List<Solution> population) {

		List<int[][]> parents = new ArrayList<int[][]>();

		int[][] maleParent;
		int[][] femaleParent;

		int indexONE = RandomWalk.getRandomInt(0, population.size() - 1);
		int indexTWO;

		maleParent = population.get(indexONE).getMatrix();
		parents.add(maleParent);

		do {
			indexTWO = RandomWalk.getRandomInt(0, population.size() - 1);
			femaleParent = population.get(indexTWO).getMatrix();

		} while (indexTWO == indexONE);

		parents.add(femaleParent);

		return parents;

	}

	private List<int[][]> tournamentSelection(List<Solution> population, int tournamentSize) {

		List<int[][]> parents = new ArrayList<int[][]>();

		int champion = -1;
		int winner = -1;
		int enemy = -1;
		boolean contains = false;

		for (int i = 0; i < 2; i++) {

			// Creates a list of all current players
			List<Integer> players = new ArrayList<Integer>();
			players.add(champion);

			// Searching for an player who hasnt already won
			do {
				contains = false;
				winner = RandomWalk.getRandomInt(0, population.size() - 1);
				contains = players.contains(winner);
			} while (contains == true);

			players.add(winner);

			int[][] matrix = population.get(winner).getMatrix();
			parents.add(matrix);

			for (int j = 0; j < tournamentSize; j++) {

				do {
					contains = false;
					enemy = RandomWalk.getRandomInt(0, population.size() - 1);
					contains = players.contains(enemy);
				} while (contains == true);

				players.add(enemy);

				if (population.get(enemy).getPoints() > population.get(winner).getPoints()) {
					matrix = population.get(enemy).getMatrix();
					parents.set(i, matrix);

					winner = enemy;
				}
			}
			champion = winner;
		}
		return parents;
	}

	private List<int[][]> generateChild(int[][] fristParent, int[][] secondParent, int crossoverPoint,
			int crossoverPointTWO, int crossovermethod) {

		List<int[][]> children = new ArrayList<int[][]>();

		if (crossovermethod == 1) {
			children = generateChildSinglePoint(fristParent, secondParent, crossoverPoint);
		}
		if (crossovermethod == 2) {
			children = generateChildTWOPoint(fristParent, secondParent, crossoverPoint, crossoverPointTWO);
		}
		if (crossovermethod == 3) {
			children = generateChildUniform(fristParent, secondParent);
		}
		if (crossovermethod == 4) {
			children = generateChildBlock(fristParent, secondParent);
		}

		return children;
	}

	private List<int[][]> generateChildBlock(int[][] fristParent, int[][] secondParent) {

		List<int[][]> children = new ArrayList<int[][]>();

		int[][] maleChild = fristParent;
		int[][] femaleChild = secondParent;

		int col = RandomWalk.getRandomInt(1, Config.drivers);
		int row = RandomWalk.getRandomInt(1, Config.totalDays);

		if (col > 1) {
			col = (col * Config.routes) - Config.routes; // 2 * 3 = 6 - 3 = 3 2 * 11 = 33 - 3 = 30 - 1 = 29
		}
		col = col - 1;

		row = (row * Config.shiftsPerDay) - Config.shiftsPerDay; // 1 * 2 = 2 - 2 = 0 // 2 * 2 = 4 - 2 = 2

		for (int i = col; i < col + Config.routes; i++) {

			for (int j = row; j < row + Config.shiftsPerDay; j++) {

				maleChild[i][j] = secondParent[i][j];
				femaleChild[i][j] = fristParent[i][j];
			}
		}

		children.add(maleChild);
		children.add(femaleChild);

		return children;
	}

	private List<int[][]> generateChildSinglePoint(int[][] fristParent, int[][] secondParent, int crossoverPoint) {

		List<int[][]> children = new ArrayList<int[][]>();

		int[][] maleChild = fristParent;
		int[][] femaleChild = secondParent;

		int col_len = fristParent.length;
		int row_len = fristParent[0].length;

		if (crossoverPoint == 0) {
			int max = (Config.totalDays * Config.shiftsPerDay) - 1;
			int min = 1;

			crossoverPoint = RandomWalk.getRandomInt(min, max);
		}

		for (int i = 0; i < col_len; i++) {

			for (int j = crossoverPoint; j < row_len; j++) {

				maleChild[i][j] = secondParent[i][j];
				femaleChild[i][j] = fristParent[i][j];
			}
		}

		children.add(maleChild);
		children.add(femaleChild);

		return children;

	}

	private List<int[][]> generateChildTWOPoint(int[][] fristParent, int[][] secondParent, int crossoverPoint,
			int crossoverPointTWO) {

		List<int[][]> children = new ArrayList<int[][]>();

		int[][] maleChild = fristParent;
		int[][] femaleChild = secondParent;

		int col_len = fristParent.length;

		for (int i = 0; i < col_len; i++) {

			for (int j = crossoverPoint; j < crossoverPointTWO; j++) {

				maleChild[i][j] = secondParent[i][j];
				femaleChild[i][j] = fristParent[i][j];

			}
		}

		children.add(maleChild);
		children.add(femaleChild);

		return children;

	}

	private List<int[][]> generateChildUniform(int[][] fristParent, int[][] secondParent) {

		List<int[][]> children = new ArrayList<int[][]>();

		int[][] maleChild = fristParent;
		int[][] femaleChild = secondParent;

		int col_len = fristParent.length;
		int row_len = fristParent[0].length;

		for (int j = 0; j < col_len; j++) {

			for (int i = 0; i < row_len; i++) {

				int coin = RandomWalk.getRandomInt(0, 1);

				if (coin == 1) {

					maleChild[j][i] = secondParent[j][i];
					femaleChild[j][i] = fristParent[j][i];

				}
			}
		}

		children.add(maleChild);
		children.add(femaleChild);

		return children;
	}

	/**
	 * Mutates the children for the genetic algorithm
	 * https://www.tutorialspoint.com/genetic_algorithms/genetic_algorithms_mutation.htm
	 * 
	 * @return matrix
	 */

	private int[][] mutation(int[][] matrix, double mutationRate, int mutationMethod, int swapsPerRow) {

		if (mutationMethod == 1) {
			matrix = mutationBitFlip(matrix, mutationRate);
		}
		if (mutationMethod == 2) {
			matrix = mutationSwaps(matrix, swapsPerRow);
		}
		if (mutationMethod == 3) {
			matrix = mutationCheatingBlocks(matrix, mutationRate);
		}
		return matrix;

	}

	private int[][] mutationCheatingBlocks(int[][] matrix, double mutationRate) {

		int col_len = matrix.length;
		int row_len = matrix[0].length;

		int shiftCounter = 0;

		// ChecK for all drivers
		for (int l = 0; l < col_len; l = l + Config.routes) {

			// Check for single driver
			for (int f = 0; f < row_len; f = f + Config.shiftsPerDay) {

				// Check shift for one day
				for (int i = 0 + f; i < Config.shiftsPerDay + f; i++) {

					for (int j = 0 + l; j < Config.routes + l; j++) {

						if (matrix[j][i] == 1) {
							shiftCounter++;

						}
					}
				}

				int min = 0;
				int max = 100;

				double mutation = RandomWalk.getRandomInt(min, max);

				if (mutation <= mutationRate) {

					if (shiftCounter > 0) {
						matrix[l + 0][f] = 0;
						matrix[l + 0][f + 1] = 0;
						matrix[l + 1][f] = 0;
						matrix[l + 1][f + 1] = 0;
						matrix[l + 2][f] = 0;
						matrix[l + 2][f + 1] = 0;

						shiftCounter = 0;
					}

					if (shiftCounter == 0) {

						int row;
						int col;

						int counter = 0;

						do {

							int randRow = RandomWalk.getRandomInt(0, 2);
							int randCol = RandomWalk.getRandomInt(0, 1);

							row = l + randRow;
							col = f + randCol;

							counter++;

							if (counter == 50) {
								break;
							}

						} while (Restrictions.license[row][col] == 0 || Restrictions.holliday[row][col] == 1);

						if (Restrictions.license[row][col] == 1 && Restrictions.holliday[row][col] == 0) {

							int coin = RandomWalk.getRandomInt(0, 1);

							if (coin == 1) {
								matrix[row][col] = 1;
							}
						}
					}
				}

				shiftCounter = 0;
			}
		}
		return matrix;

	}

	private int[][] mutationBitFlip(int[][] matrix, double mutationRate) {

		int col_len = matrix.length;
		int row_len = matrix[0].length;

		for (int i = 0; i < row_len; i++) {

			for (int j = 0; j < col_len; j++) {

				if (Restrictions.license[j][i] == 1 && Restrictions.holliday[j][i] == 0) {

					int min = 0;
					int max = 100;

					double mutation = RandomWalk.getRandomDouble(min, max);

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

	private int[][] mutationSwaps(int[][] matrix, int swapsPerRow) {

		int row_len = matrix[0].length;

		int swapPointOne = 0;
		int swapPointTWO = 0;

		for (int j = 0; j < row_len; j++) {

			for (int s = 0; s < swapsPerRow; s++) {

				swapPointOne = RandomWalk.getRandomInt(0, row_len - 1);

				do {
					swapPointTWO = RandomWalk.getRandomInt(0, row_len - 1);
				} while (swapPointOne == swapPointTWO);

				int swapValueONE = matrix[j][swapPointOne];
				int swarValueTWO = matrix[j][swapPointTWO];

				matrix[j][swapPointOne] = swarValueTWO;
				matrix[j][swapPointTWO] = swapValueONE;
			}

		}

		return matrix;
	}

	/**
	 * Chooses the replacement method
	 * https://www.modius-techblog.de/data-science/ersetzungsverfahren-in-kontext-von-genetischen-algorithmen/
	 * 
	 * @return generation
	 */

	public List<Solution> replacement(List<Solution> initialPopulation, List<Solution> generation, int[][] maleChild,
			int[][] femaleChild, int replacementMethod, int numberOfReplacements) {

		// General Replacement
		if (replacementMethod == 1) {

			generation = replacementGeneralReplacement(generation, maleChild, femaleChild);

		}

		// Principle of the Elites
		if (replacementMethod == 2) {

			generation = replacementPrincipleOfTheElites(initialPopulation, generation, numberOfReplacements);

		}

		return generation;
	}

	private List<Solution> replacementGeneralReplacement(List<Solution> generation, int[][] maleChild,
			int[][] femaleChild) {

		generation.add(new Solution(maleChild));
		generation.add(new Solution(femaleChild));

		return generation;

	}

	private List<Solution> replacementPrincipleOfTheElites(List<Solution> initialPopulation, List<Solution> generation,
			int numberOfReplacements) {

		Collections.sort(initialPopulation);
		Collections.sort(generation, Collections.reverseOrder());

		for (int i = 0; i < numberOfReplacements; i++) {

			Solution solutionObj = generation.get(i);

			if (solutionObj.getPoints() > initialPopulation.get(i).getPoints()) {
				initialPopulation.set(i, solutionObj);
			}
		}

		return initialPopulation;

	}

}
