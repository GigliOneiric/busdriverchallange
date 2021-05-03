package busdriverchallange;

/**
 * @author Tobias Stelter This class calculates the validity of the solution.
 */

public class ValidSoultion {
	private int[][] matrix;
	private boolean validSoultion;

	private int[][] license;
	private int[][] holliday;

	public boolean checkValidSoultion(int[][] matrix) {
		this.matrix = matrix;
		this.validSoultion = true;
		this.license = Restrictions.license;
		this.holliday = Restrictions.holliday;

		checkLicense();
		checkHolliday();
		checkShiftsPerDay();
		checkMultipleShifts();

		return this.validSoultion;

	}

	private void checkLicense() {

		int col_len = this.matrix.length;
		int row_len = this.matrix[0].length;

		for (int i = 0; i < row_len; i++) {

			for (int j = 0; j < col_len; j++) {

				if (this.matrix[j][i] == 1 && this.license[j][i] == 0) {
					this.validSoultion = false;
				}
			}

		}
	}

	private void checkHolliday() {

		int col_len = this.matrix.length;
		int row_len = this.matrix[0].length;

		for (int i = 0; i < row_len; i++) {

			for (int j = 0; j < col_len; j++) {

				if (this.matrix[j][i] == 1 && this.holliday[j][i] == 1) {
					this.validSoultion = false;
					
				}
			}

		}
	}

	private void checkMultipleShifts() {
		int shiftCounter = 0;

		int col_len = this.matrix.length;
		int row_len = this.matrix[0].length;

		for (int k = 0; k < Config.routes; k++) {

			for (int i = 0; i < row_len; i++) {

				for (int j = 0 + k; j < col_len; j = j + Config.routes) {
					shiftCounter = shiftCounter + this.matrix[j][i];
				}

				if (shiftCounter > 1) {
					this.validSoultion = false;
					
					
				}
				shiftCounter = 0;

			}
		}
	}

	private int checkShiftsPerDay() {
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

						if (this.matrix[j][i] == 1 && this.license[j][i] == 1) {
							shiftCounter++;

						}
					}
				}

				if (shiftCounter > 1) {
					this.validSoultion = false;
				}

				shiftCounter = 0;
			}
		}
		return points;

	}

}
