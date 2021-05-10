package algorithm;

import model.Config;
import model.Restrictions;
import model.Solution;

/**
 * @author Tobias Stelter This class implement the random walk.
 */

public class RandomWalk {

	/**
	 * Generates a random solution
	 * 
	 * @return matrix
	 */

	public static Solution randomWalk() {
		int[][] matrix = new int[Config.drivers * Config.routes][Config.totalDays * Config.shiftsPerDay];

		int col_len = matrix.length;
		int row_len = matrix[0].length;

		for (int i = 0; i < row_len; i++) {

			for (int j = 0; j < col_len; j++) {

				if (Restrictions.license[j][i] == 1 && Restrictions.holliday[j][i] == 0) {

					matrix[j][i] = getRandomInt(0, 1);
				}
			}
		}

		return new Solution(matrix);

	}

	/**
	 * Generates a random int
	 * 
	 * @return radomInt
	 */

	public static int getRandomInt(int min, int max) {

		int radomInt = (int)  (Math.floor(Math.random() * (max - min + 1)) + min);

		return radomInt;

	}

	/**
	 * Generates a random double
	 * 
	 * @return radomDouble
	 */

	public static double getRandomDouble(int min, int max) {

		double radomDouble = (Math.floor(Math.random() * (max - min + 1)) + min);

		return radomDouble;

	}

}
