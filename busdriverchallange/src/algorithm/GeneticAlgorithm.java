package algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Config;
import model.Solution;

public class GeneticAlgorithm {

	private Solution solutionObj = new Solution();

	/**
	 * Finds a solution using the genetic algorithm
	 * 
	 * @return matrix
	 */

	public Solution geneticAlgorithm(int generationSize, int populationSize, int selection, int crossovermethod,
			int crossoverPoint, int crossoverPointTWO, int mutationRate, int tournamentSize) {

		List<Solution> population = new ArrayList<Solution>();

		int[][] maleParent;
		int[][] femaleParent;

		int[][] maleChild;
		int[][] femaleChild;

		// Generates the population of Size N
		for (int i = 0; i < populationSize; i++) {
			this.solutionObj = RandomWalk.randomWalk();
			population.add(this.solutionObj);
		}

		if (crossovermethod == 1) {

			if (crossoverPoint == 0) {
				int max = (Config.totalDays * Config.shiftsPerDay) - 1;
				int min = 1;

				crossoverPoint = RandomWalk.getRandomInt(min, max);
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

		return solutionObj;

	}

	/**
	 * Selects the parents for the genetic algorithm
	 * 
	 * @return matrix
	 */

	private List<int[][]> selectParents(List<Solution> population, int selection, int tournamentSize) {
		List<int[][]> parents = new ArrayList<int[][]>();

		int[][] maleParent;
		int[][] femaleParent;

		// Randomly selection
		if (selection == 1) {
			maleParent = population.get(RandomWalk.getRandomInt(0, population.size() - 1)).getMatrix();
			parents.add(maleParent);

			do {
				femaleParent = population.get(RandomWalk.getRandomInt(0, population.size() - 1)).getMatrix();

			} while (maleParent == femaleParent);

			parents.add(femaleParent);
		}

		// Randomly select two parents via tournament selection.
		if (selection == 2) {

			Random rand = new Random(System.currentTimeMillis());

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
	 * Finds the parent with the highest points
	 * 
	 * @return matrix
	 */

	public Solution findBestParent(List<Solution> population) {

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
