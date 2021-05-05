package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import model.Config;
import model.Solution;

/**
 * @author Tobias Stelter This class implement the genetic algorithm.
 */

public class GeneticAlgorithm {

	private Solution solutionObj = new Solution();

	/**
	 * Finds a solution using the genetic algorithm
	 * 
	 * @return matrix
	 */

	public Solution geneticAlgorithm(int generationSize, int populationSize, int selection, int crossovermethod,
			int crossoverPoint, int crossoverPointTWO, double mutationRate, int tournamentSize, int children,
			int replacementMethod, int numberOfReplacements) {

		List<Solution> initialPopulation = new ArrayList<Solution>();
		List<Solution> generation = new ArrayList<Solution>();

		int[][] maleParent = null;
		int[][] femaleParent = null;

		int[][] maleChild = null;
		int[][] femaleChild = null;

		if (replacementMethod == 1 || replacementMethod == 1) {
			children = (int) children / 2;
		}

		// Generates the population of Size N
		for (int i = 0; i < populationSize; i++) {
			this.solutionObj = RandomWalk.randomWalk();
			initialPopulation.add(this.solutionObj);
		}

		for (int g = 0; g < generationSize; g++) {

			generation = new ArrayList<Solution>();

			for (int k = 0; k < children; k++) {

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
				maleChild = generateChild(maleParent, femaleParent, crossoverPoint, crossoverPointTWO, crossovermethod);
				femaleChild = generateChild(femaleParent, maleParent, crossoverPoint, crossoverPointTWO,
						crossovermethod);

				// Mutation
				maleChild = mutation(maleChild, crossoverPoint, crossoverPointTWO, crossovermethod, mutationRate);
				femaleChild = mutation(femaleChild, crossoverPoint, crossoverPointTWO, crossovermethod, mutationRate);

				// Add the children to the population
				generation = replacement(initialPopulation, generation, maleChild, femaleChild, 1,
						numberOfReplacements);

			}

			if (replacementMethod == 2) {
				// Add the children to the population
				generation = replacement(initialPopulation, generation, maleChild, femaleChild, replacementMethod,
						numberOfReplacements);
			}

			initialPopulation = generation;
			System.out.println("Generation: " + g + " | Punkte: " + findBestParent(initialPopulation).getPoints());
		}

		// Find best Child/Parent
		findBestParent(initialPopulation);

		return this.solutionObj;

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

		Random rand = new Random(System.currentTimeMillis());

		for (int i = 0; i < 2; i++) {

			// Creates a list of all current players
			List<Integer> players = new ArrayList<Integer>();
			players.add(champion);

			// Searching for an player who hasnt already won
			do {
				contains = false;
				winner = rand.nextInt(population.size());
				contains = players.contains(winner);
			} while (contains == true);

			players.add(winner);

			int[][] matrix = population.get(winner).getMatrix();
			parents.add(matrix);

			for (int j = 0; j < tournamentSize; j++) {

				do {
					contains = false;
					enemy = rand.nextInt(population.size());
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

	private int[][] generateChild(int[][] fristParent, int[][] secondParent, int crossoverPoint, int crossoverPointTWO,
			int crossovermethod) {
		int[][] child = fristParent;

		int col_len = fristParent.length;
		int row_len = fristParent[0].length;

		if (crossovermethod == 1) {

			for (int i = 0; i < row_len; i++) {

				for (int j = crossoverPoint; j < col_len; j++) {

					child[j][i] = secondParent[j][i];
				}
			}
		}
		if (crossovermethod == 2) {
			for (int i = 0; i < row_len; i++) {

				for (int j = crossoverPoint; j < crossoverPointTWO; j++) {

					child[j][i] = secondParent[j][i];
				}
			}
		}

		return child;
	}

	private int[][] mutation(int[][] matrix, int crossoverPoint, int crossoverPointTWO, int crossovermethod,
			double mutationRate) {

		int col_len = matrix.length;
		int row_len = matrix[0].length;

		if (crossovermethod == 1) {

			for (int i = 0; i < row_len; i++) {

				for (int j = crossoverPoint; j < col_len; j++) {

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
		if (crossovermethod == 2) {

			for (int i = 0; i < row_len; i++) {

				for (int j = crossoverPoint; j < crossoverPointTWO; j++) {

					int min = 0;
					int max = 100;

					int mutation = RandomWalk.getRandomInt(min, max);

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

		Collections.reverse(initialPopulation);
		Collections.sort(generation);

		for (int i = 0; i < numberOfReplacements; i++) {
			Solution solutionObj = generation.get(i);
			initialPopulation.set(i, solutionObj);
		}

		return initialPopulation;

	}

	/**
	 * Finds the parent with the highest points
	 * 
	 * @return matrix
	 */

	private Solution findBestParent(List<Solution> population) {

		Solution solutionObj = new Solution();

		for (int i = 0; i < population.size(); i++) {

			solutionObj = population.get(i);

			if (solutionObj.getPoints() > this.solutionObj.getPoints()) {
				this.solutionObj = solutionObj;
			}

		}
		return this.solutionObj;
	}

}
