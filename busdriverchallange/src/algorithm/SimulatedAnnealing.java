package algorithm;

import java.util.List;

import model.Config;
import model.Encoder;
import model.Solution;
import ui.Printer;

public class SimulatedAnnealing {

	int interation = 0;
	int temp = 0;
	double coolingRate = 0;

	Solution solutionObjCurr = new Solution();
	Solution solutionObjInital = new Solution();
	Solution solutionObjBest = new Solution();

	boolean additionalRestrictions;

	public Solution simulatedAnnealing(int In, boolean additionalRestrictions, int evalOption, int interation, int temp,
			double coolingRate) {

		this.solutionObjCurr = RandomWalk.radomEncodedWalk(additionalRestrictions);
		this.solutionObjInital = solutionObjCurr;
		this.solutionObjBest = solutionObjCurr;

		this.interation = interation;
		this.temp = temp;
		this.coolingRate = coolingRate;

		this.additionalRestrictions = additionalRestrictions;

		if (In == 1) {
			this.solutionObjBest = swip(this.solutionObjCurr, evalOption, temp, coolingRate);
		} else if (In == 2) {
			this.solutionObjBest = flop(this.solutionObjCurr, evalOption, temp, coolingRate);
		} else if (In == 3) {
			this.solutionObjBest = changeDrivers(this.solutionObjCurr, evalOption, temp, coolingRate);
		}

		return this.solutionObjBest;

	}

	public Solution simulatedAnnealing(int In, Solution solutionObj, boolean additionalRestrictions, int evalOption,
			int interation, int temp, double coolingRate) {

		this.solutionObjCurr = solutionObj;
		this.solutionObjInital = solutionObj;
		this.solutionObjBest = solutionObj;
		
		this.interation = interation;
		this.temp = temp;
		this.coolingRate = coolingRate;

		if (In == 1) {
			this.solutionObjBest = swip(this.solutionObjCurr, evalOption, temp, coolingRate);
		} else if (In == 2) {
			this.solutionObjBest = flop(this.solutionObjCurr, evalOption, temp, coolingRate);
		} else if (In == 3) {
			this.solutionObjBest = changeDrivers(this.solutionObjCurr, evalOption, temp, coolingRate);
		}

		return this.solutionObjBest;

	}

	private Solution flop(Solution solutionObj, int evalOption, int temp, double coolingRate) {

		Solution solutionObjNeighbour = solutionObj;

		List<List<Integer>> encodedSolution = solutionObj.getEncodedMatrix();

		if (evalOption == 1) {

			for (int y = 0; y < interation; y++) {

				encodedSolution = this.solutionObjCurr.getEncodedMatrix();

				for (int i = 0; i < encodedSolution.size(); i++) {
					List<Integer> day = SwipFlop.flopDay(encodedSolution, i);
					encodedSolution.set(i, day);

					solutionObjNeighbour = new Solution(encodedSolution);

					if (evalCanditat(evalOption, solutionObj, solutionObjNeighbour, y, temp, coolingRate)) {
						solutionObj = solutionObjNeighbour;

						this.solutionObjBest = storeBest(encodedSolution, solutionObjBest, solutionObj, y);
					}

				}
			}
		}

		if (evalOption == 2) {

			int y = 0;

			while (this.temp > 1) {

				encodedSolution = this.solutionObjCurr.getEncodedMatrix();

				for (int i = 0; i < encodedSolution.size(); i++) {
					List<Integer> day = SwipFlop.flopDay(encodedSolution, i);
					encodedSolution.set(i, day);

					solutionObjNeighbour = new Solution(encodedSolution);

					if (evalCanditat(evalOption, solutionObj, solutionObjNeighbour, y, temp, coolingRate)) {
						solutionObj = solutionObjNeighbour;

						this.solutionObjBest = storeBest(encodedSolution, solutionObjBest, solutionObj, y);
					}

				}

				this.temp *= 1 - this.coolingRate;
				y++;
			}
		}

		return this.solutionObjCurr;

	}

	private Solution swip(Solution solutionObj, int evalOption, int temp, double coolingRate) {

		Solution solutionObjNeighbour = this.solutionObjCurr;

		List<List<Integer>> encodedSolution = this.solutionObjCurr.getEncodedMatrix();

		if (evalOption == 1) {

			for (int y = 0; y < interation; y++) {

				for (int i = 0; i < encodedSolution.size(); i++) {

					encodedSolution = this.solutionObjCurr.getEncodedMatrix();

					List<Integer> day = RandomWalk.randomDriverCombinationForDay(i, additionalRestrictions);
					encodedSolution.set(i, day);
					solutionObjNeighbour = new Solution(encodedSolution);

					if (evalCanditat(evalOption, solutionObj, solutionObjNeighbour, y, temp, coolingRate)) {
						this.solutionObjCurr = solutionObjNeighbour;

						this.solutionObjBest = storeBest(encodedSolution, solutionObjBest, solutionObj, y);
					}

				}
			}
		}

		if (evalOption == 2) {

			int y = 0;

			while (this.temp > 1) {

				for (int i = 0; i < encodedSolution.size(); i++) {

					encodedSolution = this.solutionObjCurr.getEncodedMatrix();

					List<Integer> day = RandomWalk.randomDriverCombinationForDay(i, additionalRestrictions);
					encodedSolution.set(i, day);
					solutionObjNeighbour = new Solution(encodedSolution);

					if (evalCanditat(evalOption, solutionObj, solutionObjNeighbour, y, temp, coolingRate)) {
						this.solutionObjCurr = solutionObjNeighbour;

						this.solutionObjBest = storeBest(encodedSolution, solutionObjBest, solutionObj, y);
					}

				}

				this.temp *= 1 - this.coolingRate;
				y++;
			}
		}

		return this.solutionObjCurr;

	}

	private Solution changeDrivers(Solution solutionObj, int evalOption, int temp, double coolingRate) {

		Solution solutionObjNeighbour = solutionObj;

		List<List<Integer>> encodedSolution = this.solutionObjCurr.getEncodedMatrix();

		if (evalOption == 1) {

			for (int y = 0; y < interation; y++) {

				for (int d = 1; d <= Config.drivers; d++) {

					for (int i = 0; i < encodedSolution.size(); i++) {
						encodedSolution = this.solutionObjCurr.getEncodedMatrix();
						List<Integer> day = removeDriver(d, encodedSolution, i, additionalRestrictions);
						encodedSolution.set(i, day);
						solutionObjNeighbour = new Solution(encodedSolution);

						if (evalCanditat(evalOption, solutionObj, solutionObjNeighbour, y, temp, coolingRate)) {
							this.solutionObjCurr = solutionObjNeighbour;

							this.solutionObjBest = storeBest(encodedSolution, solutionObjBest, solutionObj, y);
						}

					}

				}
			}
		}

		if (evalOption == 2) {

			int y = 0;

			while (this.temp > 1) {

				for (int d = 1; d <= Config.drivers; d++) {

					for (int i = 0; i < encodedSolution.size(); i++) {
						encodedSolution = this.solutionObjCurr.getEncodedMatrix();
						List<Integer> day = removeDriver(d, encodedSolution, i, additionalRestrictions);
						encodedSolution.set(i, day);
						solutionObjNeighbour = new Solution(encodedSolution);

						if (evalCanditat(evalOption, solutionObj, solutionObjNeighbour, y, temp, coolingRate)) {
							this.solutionObjCurr = solutionObjNeighbour;

							this.solutionObjBest = storeBest(encodedSolution, solutionObjBest, solutionObj, y);
						}

					}

				}

				this.temp *= 1 - this.coolingRate;
				y++;
			}
		}

		return this.solutionObjCurr;

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

	private boolean evalCanditat(int evalOption, Solution solutionObjCurr, Solution solutionObjNeighbour,
			int itersation, int temp, double coolingRate) {

		boolean store = false;

		// https://machinelearningmastery.com/simulated-annealing-from-scratch-in-python/
		if (evalOption == 1) {
			store = evalCanditatMetropolis(solutionObjCurr, solutionObjNeighbour, itersation);
			
		// https://www.theprojectspot.com/tutorial-post/simulated-annealing-algorithm-for-beginners/6	
		} else if (evalOption == 2) {
			store = evalCanditatTemp(solutionObjCurr, solutionObjNeighbour, temp, coolingRate);
		}

		return store;
	}

	private boolean evalCanditatMetropolis(Solution solutionObjCurr, Solution solutionObjNeighbour, int itersation) {

		boolean store = false;

		double diff = solutionObjCurr.getPoints() - solutionObjNeighbour.getPoints();
		double p = this.solutionObjInital.getPoints() / (itersation + 1);
		double metropolis = Math.exp((-diff) / p);

		if (solutionObjCurr.getPoints() < solutionObjNeighbour.getPoints()) {
			store = true;
		} else if (RandomWalk.getRandomDouble(0, 1) < metropolis) {
			store = true;
		}

		return store;

	}

	private boolean evalCanditatTemp(Solution solutionObjCurr, Solution solutionObjNeighbour, int temp,
			double coolingRate) {

		double acceptanceProbability = 0;
		boolean store = false;

		if (solutionObjNeighbour.getPoints() > solutionObjCurr.getPoints()) {
			acceptanceProbability = 1.0;
		} else {
			acceptanceProbability = Math.exp((solutionObjCurr.getPoints() - solutionObjNeighbour.getPoints()) / temp);
		}

		if (acceptanceProbability > Math.random()) {
			store = true;
		}

		return store;

	}

	private Solution storeBest(List<List<Integer>> encodedSolution, Solution solutionObjBest, Solution solutionObjCurr,
			int y) {
		Solution solutionObjNeighbour = new Solution(encodedSolution);

		if (evalCanditatMetropolis(solutionObjCurr, solutionObjNeighbour, y)) {
			solutionObjCurr = solutionObjNeighbour;
		}

		if (solutionObjCurr.getPoints() > solutionObjBest.getPoints()) {

			solutionObjBest = solutionObjCurr;

			Printer.printSimulatedAnnealingPoints(y, solutionObjNeighbour.getPoints());
		}

		return solutionObjBest;

	}

}
