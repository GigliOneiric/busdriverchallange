package algorithm;

import model.Config;
import model.Restrictions;
import model.Solution;

public class RandomWalk {

	/**
	 * Generates a random solution
	 * @return matrix
	 */

	public static Solution randomWalk() {
		int[][] matrix = new int[Config.drivers * Config.routes][Config.totalDays * Config.shiftsPerDay];

		int col_len = matrix.length;
		int row_len = matrix[0].length;

		int max = 1;
		int min = 0;

		for (int i = 0; i < row_len; i++) {

			for (int j = 0; j < col_len; j++) {

				if (Restrictions.license[j][i] == 1 && Restrictions.holliday[j][i] == 0) {

					matrix[j][i] = getRandomInt(min, max);
				}
			}
		}

		return new Solution(matrix);

	}

	/**
	 * Generates a random int
	 * @return radomInt
	 */

	public static int getRandomInt(int min, int max) {

		int radomInt = (int) (Math.floor(Math.random() * (max - min + 1)) + min);

		return radomInt;

	}

}
