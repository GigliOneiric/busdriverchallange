package algorithm;

import java.util.ArrayList;
import java.util.List;

import model.Config;
import model.Decoder;
import model.Restrictions;
import model.Solution;
import ui.Reader;

/**
 * @author Felix Harms,
 * @author Tobias Stelter
 * 
 *         This class implements the random walk.
 */

public class RandomWalk {
	
	private Decoder decoder;

	public RandomWalk(Reader fileReader, int moreRestrictions) {
		this.decoder = new Decoder(fileReader, moreRestrictions);
	}

	/**
	 * Generates a binary random solution
	 * 
	 * @return matrix
	 */

	public static Solution randomDecodedWalk() {
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
	 * Generates a encoded random solution
	 * 
	 * @return matrix
	 */

	public Solution radomEncodedWalk() {

		List<List<Integer>> driverCombination = decoder.extractPossibleDrivers();

		List<List<Integer>> encodedSolution = new ArrayList<>();
		List<Integer> day = new ArrayList<>();

		for (int i = 0; i < driverCombination.size(); i++) {

			day = randomDriverCombinationForDay(i);
			encodedSolution.add(day);
		}

		return new Solution(encodedSolution);

	}

	/**
	 * Gets a random driver combination for one day
	 * 
	 * @return day
	 */

	public List<Integer> randomDriverCombinationForDay(int i) {

		List<List<Integer>> driverCombination = decoder.extractPossibleDrivers();
		List<Integer> day = new ArrayList<>();

		int driverDAY = RandomWalk.getRandomInt(0, driverCombination.get(i).size() - 1);
		int driverNIGHT = -1;

		do {

			driverNIGHT = RandomWalk.getRandomInt(0, driverCombination.get(i).size() - 1);

		} while (driverNIGHT == driverDAY);

		day.add(driverCombination.get(i).get(driverDAY));
		day.add(driverCombination.get(i).get(driverNIGHT));

		return day;
	}

	/**
	 * Generates a random int
	 * 
	 * @return radomInt
	 */

	public static int getRandomInt(int min, int max) {

		int radomInt = (int) (Math.floor(Math.random() * (max - min + 1)) + min);

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
