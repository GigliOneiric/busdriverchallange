package algorithm;

import java.util.List;

import model.Config;
import model.Encoder;
import model.Solution;
import ui.Printer;

public class SimulatedAnnealing {

	int interation = 10000;

	Solution solutionObj = new Solution();
	Solution solutionObjInital = new Solution();
	Solution solutionObjBest = new Solution();

	boolean additionalRestrictions;

	public Solution simulatedAnnealing(int In, boolean additionalRestrictions) {

		this.solutionObj = RandomWalk.radomEncodedWalk(additionalRestrictions);
		this.solutionObjInital = solutionObj;
		this.solutionObjBest = solutionObj;

		this.additionalRestrictions = additionalRestrictions;

		if (In == 1) {
			this.solutionObj = swip(this.solutionObj);
		} else if (In == 2) {
			this.solutionObj = flop(this.solutionObj);
		} else if (In == 3) {
			this.solutionObj = changeDrivers(this.solutionObj);
		}

		return this.solutionObjBest;

	}

	public Solution simulatedAnnealing(int In, Solution solutionObj, boolean additionalRestrictions) {

		this.solutionObj = solutionObj;
		this.solutionObjInital = solutionObj;
		this.solutionObjBest = solutionObj;

		if (In == 1) {
			solutionObj = swip(this.solutionObj);
		} else if (In == 2) {
			solutionObj = flop(this.solutionObj);
		} else if (In == 3) {
			solutionObj = changeDrivers(this.solutionObj);
		}

		return this.solutionObjBest;

	}

	private Solution flop(Solution solutionObj) {

		Solution solutionObjFlop = solutionObj;

		List<List<Integer>> encodedSolution = solutionObj.getEncodedMatrix();

		for (int y = 0; y < interation; y++) {

			encodedSolution = this.solutionObj.getEncodedMatrix();

			for (int i = 0; i < encodedSolution.size(); i++) {
				List<Integer> day = SwipFlop.flopDay(encodedSolution, i);
				encodedSolution.set(i, day);

				solutionObjFlop = new Solution(encodedSolution);

				if (evalCanditat(this.solutionObj, solutionObjFlop, y)) {
					solutionObj = solutionObjFlop;

					this.solutionObjBest = storeBest(encodedSolution, solutionObjBest, solutionObj, y);
				}

			}
		}

		return this.solutionObj;

	}

	private Solution swip(Solution solutionObj) {

		Solution solutionObjSwip = this.solutionObj;

		List<List<Integer>> encodedSolution = this.solutionObj.getEncodedMatrix();

		for (int y = 0; y < interation; y++) {

			for (int i = 0; i < encodedSolution.size(); i++) {

				encodedSolution = this.solutionObj.getEncodedMatrix();

				List<Integer> day = RandomWalk.randomDriverCombinationForDay(i, additionalRestrictions);
				encodedSolution.set(i, day);
				solutionObjSwip = new Solution(encodedSolution);

				if (evalCanditat(this.solutionObj, solutionObjSwip, y)) {
					this.solutionObj = solutionObjSwip;

					this.solutionObjBest = storeBest(encodedSolution, solutionObjBest, solutionObj, y);
				}

			}
		}

		return this.solutionObj;

	}

	private Solution changeDrivers(Solution solutionObj) {

		Solution solutionObjCurr = solutionObj;

		List<List<Integer>> encodedSolution = this.solutionObj.getEncodedMatrix();

		for (int y = 0; y < interation; y++) {

			for (int d = 1; d <= Config.drivers; d++) {

				for (int i = 0; i < encodedSolution.size(); i++) {
					encodedSolution = this.solutionObj.getEncodedMatrix();
					List<Integer> day = removeDriver(d, encodedSolution, i, additionalRestrictions);
					encodedSolution.set(i, day);
					solutionObjCurr = new Solution(encodedSolution);

					if (evalCanditat(solutionObj, solutionObjCurr, y)) {
						this.solutionObj = solutionObjCurr;

						this.solutionObjBest = storeBest(encodedSolution, solutionObjBest, solutionObj, y);
					}

				}

			}
		}

		return solutionObjBest;

	}

	private List<Integer> removeDriver(int driver, List<List<Integer>> encodedSolution, int i,
			boolean additionalRestrictions) {

		List<List<Integer>> driverCombination = Encoder.extractPossibleDrivers(additionalRestrictions);

		List<Integer> day = encodedSolution.get(i);

		int dayshift = day.get(0);
		int nightshift = day.get(1);

		if (day.contains(driver) == true) {

			while (dayshift == driver) {
				dayshift = RandomWalk.getRandomInt(0, driverCombination.get(i).size() - 1);
				day.set(0, driverCombination.get(i).get(dayshift));

			}

			while (nightshift == driver) {
				nightshift = RandomWalk.getRandomInt(0, driverCombination.get(i).size() - 1);
				day.set(1, driverCombination.get(i).get(nightshift));
			}
		}
		return day;
	}

	private boolean evalCanditat(Solution solutionObj, Solution solutionObjCurr, int y) {

		boolean store = false;

		double diff = solutionObj.getPoints() - solutionObjCurr.getPoints();
		double p = this.solutionObjInital.getPoints() / (y + 1);
		double metropolis = Math.exp((-diff) / p);

		if (solutionObj.getPoints() < solutionObjCurr.getPoints()) {
			store = true;
		} else if (RandomWalk.getRandomDouble(0, 1) < metropolis) {
			store = true;
		}

		return store;

	}

	private Solution storeBest(List<List<Integer>> encodedSolution, Solution solutionObjBest, Solution solutionObj,
			int y) {
		Solution solutionObjCurr = new Solution(encodedSolution);

		if (evalCanditat(solutionObj, solutionObjCurr, y)) {
			solutionObj = solutionObjCurr;

			if (solutionObj.getPoints() > solutionObjBest.getPoints()) {

				solutionObjBest = solutionObj;

				Printer.printSimulatedAnnealingPoints(y, solutionObjCurr.getPoints());
			}
		}
		return solutionObjBest;

	}

}
