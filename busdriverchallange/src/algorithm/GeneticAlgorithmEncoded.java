package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.Solution;
import ui.Printer;

/**
 * @author Tobias Stelter This class implements the decoded genetic algorithm.
 */

public class GeneticAlgorithmEncoded {

	/**
	 * Finds a solution using the decoded genetic algorithm
	 * 
	 * @return matrix
	 */

	public Solution geneticAlgorithmEncoded(int generationSize, int populationSize, int selection, int crossovermethod,
			int crossoverPoint, int crossoverPointTWO, double mutationRate, int tournamentSize, int childrenNumber,
			int replacementMethod, int numberOfReplacements, int mutationMethod, boolean additionalRestrictions) {

		Solution solutionObj = new Solution();

		List<Solution> initialPopulation = new ArrayList<Solution>();
		List<Solution> generation = new ArrayList<Solution>();

		List<List<Integer>> maleParent = null;
		List<List<Integer>> femaleParent = null;

		List<List<Integer>> maleChild = null;
		List<List<Integer>> femaleChild = null;

		// Generates the population of Size N
		for (int i = 0; i < populationSize; i++) {
			solutionObj = RandomWalk.radomEncodedWalk(additionalRestrictions);
			initialPopulation.add(solutionObj);
		}

		int startPoints = Solution.findBestSolution(initialPopulation).getPoints();
		Printer.printGeneticAlgorithmPointsPerGen(0, startPoints);

		for (int g = 1; g <= generationSize; g++) {

			generation = new ArrayList<Solution>();

			for (int k = 0; k < childrenNumber; k++) {

				// Select parents
				List<List<List<Integer>>> parents = selectParents(initialPopulation, selection, tournamentSize);
				maleParent = parents.get(0);
				femaleParent = parents.get(1);

				// Crossover
				List<List<List<Integer>>> children = generateChild(maleParent, femaleParent, crossoverPoint,
						crossoverPointTWO, crossovermethod);
				maleChild = children.get(0);
				femaleChild = children.get(1);

				// Mutation
				maleChild = mutation(maleChild, mutationRate, mutationMethod, additionalRestrictions);
				femaleChild = mutation(femaleChild, mutationRate, mutationMethod, additionalRestrictions);

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
			Printer.printGeneticAlgorithmPointsPerGen(g, points);

			initialPopulation = generation;
		}

		return Solution.findBestSolution(initialPopulation);

	}

	/**
	 * Selects the parents for the genetic algorithm
	 * 
	 * @return parents
	 */

	private List<List<List<Integer>>> selectParents(List<Solution> population, int selection, int tournamentSize) {
		List<List<List<Integer>>> parents = new ArrayList<>();

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

	private List<List<List<Integer>>> randomSelection(List<Solution> population) {

		List<List<List<Integer>>> parents = new ArrayList<>();

		List<List<Integer>> maleParent;
		List<List<Integer>> femaleParent;

		int indexONE = RandomWalk.getRandomInt(0, population.size() - 1);
		int indexTWO;

		maleParent = population.get(indexONE).getEncodedMatrix();
		parents.add(maleParent);

		do {
			indexTWO = RandomWalk.getRandomInt(0, population.size() - 1);
			femaleParent = population.get(indexTWO).getEncodedMatrix();

		} while (indexTWO == indexONE);

		parents.add(femaleParent);

		return parents;

	}

	private List<List<List<Integer>>> tournamentSelection(List<Solution> population, int tournamentSize) {

		List<List<List<Integer>>> parents = new ArrayList<>();

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

			List<List<Integer>> matrix = population.get(winner).getEncodedMatrix();
			parents.add(matrix);

			for (int j = 0; j < tournamentSize; j++) {

				do {
					contains = false;
					enemy = RandomWalk.getRandomInt(0, population.size() - 1);
					contains = players.contains(enemy);
				} while (contains == true);

				players.add(enemy);

				if (population.get(enemy).getPoints() > population.get(winner).getPoints()) {
					matrix = population.get(enemy).getEncodedMatrix();
					parents.set(i, matrix);

					winner = enemy;
				}
			}
			champion = winner;
		}
		return parents;
	}

	private List<List<List<Integer>>> generateChild(List<List<Integer>> fristParent, List<List<Integer>> secondParent,
			int crossoverPoint, int crossoverPointTWO, int crossovermethod) {

		List<List<List<Integer>>> children = new ArrayList<>();

		if (crossovermethod == 1) {
			children = generateChildSinglePoint(fristParent, secondParent, crossoverPoint);
		}
		if (crossovermethod == 2) {
			children = generateChildTWOPoint(fristParent, secondParent, crossoverPoint, crossoverPointTWO);
		}
		if (crossovermethod == 3) {
			children = generateChildUniform(fristParent, secondParent);
		}

		return children;
	}

	private List<List<List<Integer>>> generateChildSinglePoint(List<List<Integer>> fristParent,
			List<List<Integer>> secondParent, int crossoverPoint) {

		List<List<List<Integer>>> children = new ArrayList<>();

		List<List<Integer>> maleChild = fristParent;
		List<List<Integer>> femaleChild = secondParent;

		int len = fristParent.size();

		if (crossoverPoint == 0) {
			int max = fristParent.size() - 1;
			int min = 1;

			crossoverPoint = RandomWalk.getRandomInt(min, max);
		}

		for (int j = crossoverPoint; j < len; j++) {

			maleChild.set(j, secondParent.get(j));
			femaleChild.set(j, fristParent.get(j));
		}

		children.add(maleChild);
		children.add(femaleChild);

		return children;

	}

	private List<List<List<Integer>>> generateChildTWOPoint(List<List<Integer>> fristParent,
			List<List<Integer>> secondParent, int crossoverPoint, int crossoverPointTWO) {

		List<List<List<Integer>>> children = new ArrayList<>();

		List<List<Integer>> maleChild = fristParent;
		List<List<Integer>> femaleChild = secondParent;

		for (int j = crossoverPoint; j < crossoverPointTWO; j++) {

			maleChild.set(j, secondParent.get(j));
			femaleChild.set(j, fristParent.get(j));
		}

		children.add(maleChild);
		children.add(femaleChild);

		return children;

	}

	private List<List<List<Integer>>> generateChildUniform(List<List<Integer>> fristParent,
			List<List<Integer>> secondParent) {

		List<List<List<Integer>>> children = new ArrayList<>();

		List<List<Integer>> maleChild = fristParent;
		List<List<Integer>> femaleChild = secondParent;

		int len = fristParent.size();

		for (int j = 0; j < len; j++) {

			int coin = RandomWalk.getRandomInt(0, 1);

			if (coin == 1) {

				maleChild.set(j, secondParent.get(j));
				femaleChild.set(j, fristParent.get(j));

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

	private List<List<Integer>> mutation(List<List<Integer>> matrix, double mutationRate, int mutationMethod,
			boolean additionalRestrictions) {

		if (mutationMethod == 1) {
			matrix = mutationSwip(matrix, mutationRate, additionalRestrictions);
		}
		if (mutationMethod == 2) {
			matrix = mutationFlop(matrix, mutationRate);
		}

		return matrix;

	}

	private List<List<Integer>> mutationFlop(List<List<Integer>> matrix, double mutationRate) {
		List<Integer> day = new ArrayList<>();

		int min = 0;
		int max = 100;

		for (int i = 0; i < matrix.size(); i++) {

			double mutation = RandomWalk.getRandomDouble(min, max);

			if (mutation <= mutationRate) {

				day = SwipFlop.flopDay(matrix, i);
				matrix.set(i, day);
			}

		}

		return matrix;
	}

	private List<List<Integer>> mutationSwip(List<List<Integer>> matrix, double mutationRate,
			boolean additionalRestrictions) {

		List<Integer> day = new ArrayList<>();

		int min = 0;
		int max = 100;

		for (int i = 0; i < matrix.size(); i++) {

			double mutation = RandomWalk.getRandomDouble(min, max);

			if (mutation <= mutationRate) {

				day = RandomWalk.randomDriverCombinationForDay(i, additionalRestrictions);
				matrix.set(i, day);
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

	public List<Solution> replacement(List<Solution> initialPopulation, List<Solution> generation,
			List<List<Integer>> maleChild, List<List<Integer>> femaleChild, int replacementMethod,
			int numberOfReplacements) {

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

	private List<Solution> replacementGeneralReplacement(List<Solution> generation, List<List<Integer>> maleChild,
			List<List<Integer>> femaleChild) {

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
