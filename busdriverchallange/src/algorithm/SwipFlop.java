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

	public Solution swipFlop(int num, int In) {

		this.solutionObj = RandomWalk.radomEncodedWalk();

		if (In == 1) {
			this.solutionObj = swip(num);
		} else if (In == 2) {
			this.solutionObj = flop(num);
		}

		return this.solutionObj;
	}

	private Solution swip(int num) {

		Printer.printGeneticAlgorithmPointsPerGen(0, this.solutionObj.getPoints());
		List<List<Integer>> encodedSolution = this.solutionObj.getEncodedMatrix();

		for (int i = 0; i < num; i++) {

			int number = RandomWalk.getRandomInt(0, 41);
			List<Integer> day = RandomWalk.randomDriverCombinationForDay(number);
			encodedSolution.set(number, day);
			this.solutionObj = new Solution(encodedSolution);
			Printer.printGeneticAlgorithmPointsPerGen(i, this.solutionObj.getPoints());

		}
		return this.solutionObj;

	}

	private Solution flop(int num) {

		Printer.printGeneticAlgorithmPointsPerGen(0, this.solutionObj.getPoints());
		List<List<Integer>> encodedSolution = this.solutionObj.getEncodedMatrix();

		System.out.println(encodedSolution);

		for (int i = 0; i < num; i++) {

			int number = RandomWalk.getRandomInt(0, 41);
			List<Integer> day = encodedSolution.get(number);
			int dayshift = day.get(0);
			int nightshift = day.get(1);
			day.set(0, nightshift);
			day.set(1, dayshift);
			encodedSolution.set(number, day);

			this.solutionObj = new Solution(encodedSolution);

			Printer.printGeneticAlgorithmPointsPerGen(i, this.solutionObj.getPoints());

		}
		return this.solutionObj;
	}
}
