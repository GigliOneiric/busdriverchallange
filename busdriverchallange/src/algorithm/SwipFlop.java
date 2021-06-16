package algorithm;

import java.util.List;
import model.Solution;
import ui.Printer;

/**
 * @author Amir Razagh Khah
 * @author Tobias Stelter
 * 
 *         This class implements the swip flop - algorithm.
 */

public class SwipFlop {

	Solution solutionObj = new Solution();

	public Solution swipFlop(int num, int In, boolean additionalRestrictions) {

		this.solutionObj = RandomWalk.radomEncodedWalk(additionalRestrictions);

		if (In == 1) {
			this.solutionObj = swip(num, additionalRestrictions, this.solutionObj);
		} else if (In == 2) {
			this.solutionObj = flop(num, this.solutionObj);
		}

		return this.solutionObj;
	}

	public static Solution swip(int num, boolean additionalRestrictions, Solution solutionObj) {

		Printer.printGeneticAlgorithmPointsPerGen(0, solutionObj.getPoints());
		List<List<Integer>> encodedSolution = solutionObj.getEncodedMatrix();

		for (int i = 1; i <= num; i++) {

			int number = RandomWalk.getRandomInt(0, encodedSolution.size() - 1);
			List<Integer> day = RandomWalk.randomDriverCombinationForDay(number, additionalRestrictions);
			encodedSolution.set(number, day);
			solutionObj = new Solution(encodedSolution);
			Printer.printGeneticAlgorithmPointsPerGen(i, solutionObj.getPoints());

		}
		return solutionObj;

	}

	public Solution flop(int num, Solution solutionObj) {

		Printer.printGeneticAlgorithmPointsPerGen(0, solutionObj.getPoints());
		List<List<Integer>> encodedSolution = solutionObj.getEncodedMatrix();

		for (int i = 1; i <= num; i++) {

			int number = RandomWalk.getRandomInt(0, encodedSolution.size() - 1);
			List<Integer> day = flopDay(encodedSolution, number);
			encodedSolution.set(number, day);

			solutionObj = new Solution(encodedSolution);

			Printer.printGeneticAlgorithmPointsPerGen(i, solutionObj.getPoints());

		}
		return solutionObj;
	}

	public static List<Integer> flopDay(List<List<Integer>> encodedSolution, int number) {
		List<Integer> day = encodedSolution.get(number);
		int dayshift = day.get(0);
		int nightshift = day.get(1);
		day.set(0, nightshift);
		day.set(1, dayshift);

		return day;
	}
}
