package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import algorithm.RandomWalk;
import ui.Printer;

/**
 * @author Felix Harms,
 * @author Tobias Stelter
 * 
 *         This class implement the decoding.
 * 
 */

public class Decoder {

	public static List<List<Integer>> decodeMatrix() {

		List<List<Integer>> driverCombination = new ArrayList<>();
		List<Integer> routeDay = new ArrayList<>();

		int matrix[][] = Restrictions.license;
		int col_len = matrix.length;
		int row_len = matrix[0].length;

		for (int k = 0; k < Config.routes; k++) {

			for (int i = 0; i < row_len; i = i + Config.shiftsPerDay) {

				routeDay = new ArrayList<>();

				for (int j = 0 + k; j < col_len; j = j + Config.routes) {

					if (Restrictions.license[j][i] == 1 && Restrictions.holliday[j][i] == 0) {

						int driver = j / 3 + 1;
						System.out.print(driver + " ");

						routeDay.add(driver);
					}
				}
				System.out.println(" ");
				driverCombination.add(routeDay);
			}

		}
		return driverCombination;
	}

	public static List<List<Integer>> radomString(List<List<Integer>> driverCombinatiooon) {

		List<Integer> day = new ArrayList<>();
		List<List<Integer>> days = new ArrayList<>();

		for (int i = 0; i < driverCombinatiooon.size(); i++) {

			day = new ArrayList<>();

			int driverDAY = RandomWalk.getRandomInt(0, driverCombinatiooon.get(i).size() - 1);
			int driverNIGHT = -1;

			do {

				driverNIGHT = RandomWalk.getRandomInt(0, driverCombinatiooon.get(i).size() - 1);

			} while (driverNIGHT == driverDAY);

			day.add(driverCombinatiooon.get(i).get(driverDAY));
			day.add(driverCombinatiooon.get(i).get(driverNIGHT));
			days.add(day);
		}

		System.out.print(days + " DDDDDDDDDDDDDD" + days.size());
		System.out.println("");
		return days;

	}

	public static int[][] encodeMatrix(List<List<Integer>> bla) {

		int[][] matrix = new int[Config.drivers * Config.routes][Config.totalDays * Config.shiftsPerDay];

		System.out.print("[");

		int c = 0;

		int vv = 0;

		for (int y = 0; y < bla.size(); y++) {

			for (int i = 0; i < 2; i++) {

				System.out.print("[" + bla.get(y).get(i) + ", ");

				if (c == 28 || c == 58) {
					vv++;
					c = 0;

				}

				int col = bla.get(y).get(i) * 3 - 3 + vv;
				matrix[col][c] = 1;

				c++;

			}
		}

		Printer.printMatrixConsole(matrix);

		return matrix;

	}

}