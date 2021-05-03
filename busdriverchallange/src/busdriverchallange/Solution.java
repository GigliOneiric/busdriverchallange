package busdriverchallange;

/**
 * @author Tobias Stelter This class represents the solution-object
 */

public class Solution {

	private int matrix[][];
	private int points = -10000;
	private boolean validSolution = true;

	private Points p = new Points();
	private ValidSoultion v = new ValidSoultion();
	
	public Solution() {}

	public Solution(int matrix[][]) {
		this.matrix = matrix;
		this.points = p.calculatePoints(this.matrix);
		this.validSolution = v.checkValidSoultion(this.matrix);

	}

	public int[][] getMatrix() {
		return matrix;
	}

	public int getPoints() {
		return points;
	}

	public boolean getValidSolution() {
		return validSolution;
	}

}
