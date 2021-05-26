package model;

/**
 * @author Tobias Stelter This class calculates the total points.
 */

public class Points implements Runnable {
	private int[][] matrix;
	private int points = 0;

	public Points(int[][] matrix) {
		this.matrix = matrix;
	}

	public int getPoints() {
		return this.points;
	}

	/**
	 * Calculates the points for the unassigned shift per route
	 * 
	 * @return points
	 */

	private int calculatePointsUnassignedShift() {
		int points = 0;
		int noShift = 0;

		int col_len = this.matrix.length;
		int row_len = this.matrix[0].length;

		for (int k = 0; k < Config.routes; k++) {

			for (int i = 0; i < row_len; i++) {

				for (int j = 0 + k; j < col_len; j = j + Config.routes) {

					if (Restrictions.license[j][i] == 1 && Restrictions.holliday[j][i] == 0) {
						noShift = noShift + this.matrix[j][i];
					}
				}

				if (noShift == 0) {
					points = points + Config.pointsUnassignedShift;
				} else {
					noShift = 0;
				}

			}
		}
		return points;
	}

	/**
	 * Calculates the points for the preferred shift
	 * 
	 * @return points
	 */

	private int calculatePointsPreferredShift() {
		int points = 0;

		int col_len = this.matrix.length;
		int row_len = this.matrix[0].length;

		for (int i = 0; i < row_len; i++) {

			for (int j = 0; j < col_len; j++) {

				if (this.matrix[j][i] == 1 && Restrictions.preferredShift[j][i] == 1
						&& Restrictions.license[j][i] == 1) {
					points = points + Config.pointsPreferredShift;
				}
			}

		}

		return points;
	}

	/**
	 * Calculates the points for the preferred holliday
	 * 
	 * @return points
	 */

	private int calculatePointsPrefferedHoliday() {
		int points = 0;

		int col_len = this.matrix.length;
		int row_len = this.matrix[0].length;

		boolean hollidayCheck = true;

		// ChecK for all drivers
		for (int l = 0; l < col_len; l = l + Config.routes) {

			// Check for single driver
			for (int f = 0; f < row_len; f = f + Config.shiftsPerDay) {

				// Check holliday for one day
				for (int i = 0 + f; i < Config.shiftsPerDay + f; i++) {

					for (int j = 0 + l; j < Config.routes + l; j++) {

						if ((this.matrix[j][i] == 1 && Restrictions.prefferedHoliday[j][i] == 1
								&& Restrictions.license[j][i] == 1) || Restrictions.prefferedHoliday[j][i] == 0) {
							hollidayCheck = false;

						}
					}
				}

				if (hollidayCheck == true) {
					points = points + Config.pointsPrefferedHoliday;
				}

				hollidayCheck = true;
			}
		}

		return points;

	}

	/**
	 * Calculates the points for night-shifts followed by day-shifts
	 * 
	 * @return points
	 */

	private int calculatePointsNightshiftFollowedByDayshift() {
		int points = 0;

		int col_len = this.matrix.length;
		int row_len = this.matrix[0].length;

		int shiftCounter = 0;

		// ChecK for all drivers
		for (int l = 0; l < col_len; l = l + Config.routes) {

			// Check for single driver
			for (int f = 1; f < row_len - 1; f = f + Config.shiftsPerDay) {

				for (int i = 0 + f; i < Config.shiftsPerDay + f; i++) {

					for (int j = 0 + l; j < Config.routes + l; j++) {

						if (this.matrix[j][i] == 1 && Restrictions.license[j][i] == 1) {
							shiftCounter++;

						}
					}
				}

				if (shiftCounter > 1) {
					points = points + Config.pointsNightshiftFollowedByDayshift;
				}

				shiftCounter = 0;
			}
		}

		return points;

	}

	/**
	 * Calculates the points for more than three night-shifts
	 * 
	 * @return points
	 */

	private int calculatePointsMoreThanThreeNightShifts() {
		int points = 0;

		int col_len = this.matrix.length;
		int row_len = this.matrix[0].length;

		int nightShiftCounter = 0;
		boolean nightShiftCheck = false;

		for (int l = 0; l < col_len; l = l + Config.routes) {

			for (int i = 1; i < row_len; i = i + Config.shiftsPerDay) {

				for (int j = 0 + l; j < Config.routes + l; j++) {

					if (this.matrix[j][i] == 1 && Restrictions.license[j][i] == 1) {
						nightShiftCheck = true;
					}

				}

				if (nightShiftCheck == true) {
					nightShiftCounter++;
				}

				if (nightShiftCheck == false) {
					nightShiftCounter = 0;
				}

				if (nightShiftCounter > Config.nightShifstAreBad) {
					points = points + Config.pointsMoreThanThreeNightShifts;
				}
				nightShiftCheck = false;
			}
			nightShiftCounter = 0;

		}
		return points;

	}

	/**
	 * Calculates the points for the long break
	 * 
	 * @return points
	 */

	private int calculatePointsLongBreak() {
		int points = 0;

		int col_len = this.matrix.length;
		int row_len = this.matrix[0].length;

		int longBreakCounter = 0;
		boolean longBreakCheck = true;

		// ChecK for all drivers
		for (int l = 0; l < col_len; l = l + Config.routes) {

			longBreakCounter = 0;

			// Check for single driver
			for (int f = 0; f < row_len; f = f + Config.shiftsPerDay) {

				for (int i = 0 + f; i < Config.shiftsPerDay + f; i++) {

					for (int j = 0 + l; j < Config.routes + l; j++) {

						if (this.matrix[j][i] == 1 && Restrictions.license[j][i] == 1) {
							longBreakCheck = false;
							break;
						}
					}

					if (longBreakCheck == false) {
						longBreakCounter = 0;
						break;
					}

					if (longBreakCheck == true) {
						longBreakCounter++;
					}
				}

				if (longBreakCounter == Config.longBreak) {
					points = points + Config.pointsLongBreak;
				}

				longBreakCheck = true;
			}

		}
		return points;

	}

	/**
	 * Calculates the points for unbalanced night-shifts
	 * 
	 * @return points
	 */

	private int calculatePointsUnbalancedNightShifts() {
		int points = (-1 * Config.pointsUnbalancedNightShifts)
				* ((Config.drivers * Config.equalNightShifts) - (Config.totalDays * Config.routes));

		int col_len = this.matrix.length;
		int row_len = this.matrix[0].length;

		int nightShiftCounter = 0;
		boolean nightShiftCheck = false;

		for (int l = 0; l < col_len; l = l + Config.routes) {

			for (int i = 1; i < row_len; i = i + Config.shiftsPerDay) {

				for (int j = 0 + l; j < Config.routes + l; j++) {

					if (this.matrix[j][i] == 1 && Restrictions.license[j][i] == 1) {
						nightShiftCheck = true;
					}

				}
				if (nightShiftCheck == true) {
					nightShiftCounter++;
				}
				nightShiftCheck = false;

			}

			if (nightShiftCounter >= Config.equalNightShifts) {
				points = points + (Config.pointsUnbalancedNightShifts * (nightShiftCounter - Config.equalNightShifts));
			}

			if (nightShiftCounter < Config.equalNightShifts) {
				if (nightShiftCounter == 0) {
					points = points + Config.pointsUnbalancedNightShifts * Config.equalNightShifts;
				} else {
					points = points
							+ (Config.pointsUnbalancedNightShifts * (Config.equalNightShifts - nightShiftCounter));
				}
			}
			nightShiftCounter = 0;
		}
		return points;

	}

	/**
	 * Calculates the points for a driver have more than one shift per day
	 * 
	 * @return points
	 */

	private int calculatePointsShiftsPerDay() {
		int points = 0;

		int col_len = this.matrix.length;
		int row_len = this.matrix[0].length;

		int shiftCounter = 0;

		// ChecK for all drivers
		for (int l = 0; l < col_len; l = l + Config.routes) {

			// Check for single driver
			for (int f = 0; f < row_len; f = f + Config.shiftsPerDay) {

				// Check shift for one day
				for (int i = 0 + f; i < Config.shiftsPerDay + f; i++) {

					for (int j = 0 + l; j < Config.routes + l; j++) {

						if (this.matrix[j][i] == 1 && Restrictions.license[j][i] == 1) {
							shiftCounter++;

						}
					}
				}

				if (shiftCounter > 1) {
					points = points + Config.pointsInvalidSolution;
				}

				shiftCounter = 0;
			}
		}
		return points;
	}

	@Override
	public void run() {

		int pointsSecondaryCondition = calculatePointsUnassignedShift() + calculatePointsPreferredShift()
				+ calculatePointsPrefferedHoliday() + calculatePointsNightshiftFollowedByDayshift()
				+ calculatePointsMoreThanThreeNightShifts() + calculatePointsLongBreak()
				+ calculatePointsUnbalancedNightShifts();

		int pointsInvalidSolution = calculatePointsShiftsPerDay();

		this.points = pointsSecondaryCondition + pointsInvalidSolution;
	}

}
