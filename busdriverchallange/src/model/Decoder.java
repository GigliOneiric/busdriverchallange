package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Felix Harms,
 * @author Tobias Stelter
 * 
 *         This class implements the decoding.
 * 
 */

public class Decoder {

	/**
	 * Extracts all possible drivers per day Route 1: List entry 0 - 13 Route 2:
	 * List entry 14 - 27 Route 3: List entry 28 - 32
	 * 
	 * @return matrix
	 */

	public static List<List<Integer>> extractPossibleDrivers(String path, int pathOption, int moreRestrictions) {

		List<List<Integer>> possibleDrivers = new ArrayList<>();
		List<Integer> routeDay = new ArrayList<>();

		int col_len = Default.matrixZeros.length;
		int row_len = Default.matrixZeros.length;

		int additionalHardRestrictions[][] = Default.matrixZeros;
		
		if (moreRestrictions == 2) {
			additionalHardRestrictions = Restrictions.additionalHardRestrictions(path, pathOption);
		}

		for (int k = 0; k < Config.routes; k++) {

			for (int i = 0; i < row_len; i = i + Config.shiftsPerDay) {

				routeDay = new ArrayList<>();

				for (int j = 0 + k; j < col_len; j = j + Config.routes) {

					if (Restrictions.license[j][i] == 1 && Restrictions.holliday[j][i] == 0 && additionalHardRestrictions[j][i] == 0) {

						int driver = j / Config.routes + 1;

						routeDay.add(driver);
					}
				}
				possibleDrivers.add(routeDay);
			}

		}
		return possibleDrivers;
	}

	/**
	 * Encodes an encoded solution to a matrix
	 * 
	 * @return matrix
	 */

	public static int[][] encodeMatrix(List<List<Integer>> encodedSolution) {

		int[][] matrix = new int[Config.drivers * Config.routes][Config.totalDays * Config.shiftsPerDay];

		int shiftCounter = 0;
		int routeCounter = 0;

		for (int y = 0; y < encodedSolution.size(); y++) {

			for (int i = 0; i < Config.shiftsPerDay; i++) {

				if (shiftCounter == (Config.totalDays * Config.shiftsPerDay)
						|| shiftCounter == (Config.totalDays * Config.shiftsPerDay * 2)) {
					shiftCounter = 0;
					routeCounter++;

				}

				int col = encodedSolution.get(y).get(i) * Config.routes - Config.routes + routeCounter;
				matrix[col][shiftCounter] = 1;

				shiftCounter++;

			}
		}

		return matrix;

	}

}