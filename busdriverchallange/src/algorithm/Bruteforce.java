package algorithm;

import model.Config;
import model.Solution;

public class Bruteforce {

	private Solution solutionObj = new Solution();

	public Solution brutforce() {

		int[][] initialMatrix = new int[Config.drivers * Config.routes][Config.totalDays * Config.shiftsPerDay];
		brutforceHelper(initialMatrix, 0, 0, 0);

		return this.solutionObj;
	}

	/**
	 * Generates all permutations of the solution
	 * @return matrix
	 */

	private int brutforceHelper(int[][] matrix, int currentRow, int currentColumn, int acc) {

		Solution s = new Solution(matrix);

		if (currentColumn == (Config.totalDays * Config.shiftsPerDay)) {

			if (s.getPoints() > this.solutionObj.getPoints() && s.getValidSolution() == true) {
				this.solutionObj = s;
			}

			return acc + 1;
		}
		if (currentRow == (Config.drivers * Config.routes)) {
			return brutforceHelper(matrix, 0, currentColumn + 1, acc);
		}

		matrix[currentRow][currentColumn] = 1;
		acc += brutforceHelper(matrix, currentRow + 1, currentColumn, 0);
		matrix[currentRow][currentColumn] = 0;

		return brutforceHelper(matrix, currentRow + 1, currentColumn, acc);
	}
}
