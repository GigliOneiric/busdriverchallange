package algorithm;

import java.util.List;
import model.Solution;
import ui.Printer;
import ui.Reader;

/**
 * @author Amir Razagh Khah
 * @author Tobias Stelter
 * 
 *         This class implements the swip flop - algorithm.
 */

public class SwipFlop {

	Solution solutionObj = new Solution();
	RandomWalk radomWalk;

	public Solution swipFlop(int num, int In, Reader fileReader, int moreRestrictions) {

		this.radomWalk = new RandomWalk(fileReader, moreRestrictions);
		this.solutionObj = radomWalk.radomEncodedWalk();

		if (In == 1) {
			this.solutionObj = swip(num, fileReader, moreRestrictions);
		} else if (In == 2) {
			this.solutionObj = flop(num);
		}

		return this.solutionObj;
	}

	private Solution swip(int num, Reader fileReader, int moreRestrictions) {

		Printer.printGeneticAlgorithmPointsPerGen(0, this.solutionObj.getPoints());
		List<List<Integer>> encodedSolution = this.solutionObj.getEncodedMatrix();

		for (int i = 1; i <= num; i++) {

			int number = RandomWalk.getRandomInt(0, encodedSolution.size() - 1);
			List<Integer> day = radomWalk.randomDriverCombinationForDay(number);
			encodedSolution.set(number, day);
			this.solutionObj = new Solution(encodedSolution);
			Printer.printGeneticAlgorithmPointsPerGen(i, this.solutionObj.getPoints());

		}
		return this.solutionObj;

	}

	private Solution flop(int num) {

		Printer.printGeneticAlgorithmPointsPerGen(0, this.solutionObj.getPoints());
		List<List<Integer>> encodedSolution = this.solutionObj.getEncodedMatrix();

		for (int i = 1; i <= num; i++) {

			int number = RandomWalk.getRandomInt(0, encodedSolution.size() - 1);
			List<Integer> day = flopDay(encodedSolution, number);
			encodedSolution.set(number, day);

			this.solutionObj = new Solution(encodedSolution);

			Printer.printGeneticAlgorithmPointsPerGen(i, this.solutionObj.getPoints());

		}
		return this.solutionObj;
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
