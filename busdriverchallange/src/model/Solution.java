package model;

import java.util.List;

/**
 * @author Tobias Stelter This class represents the solution-object
 */

public class Solution implements Comparable<Solution> {

	private int matrix[][];
	List<List<Integer>> encodedMatrix;
	private Integer points = -10000;
	private boolean validSolution = true;

	private Points p = new Points();
	private ValidSoultion v = new ValidSoultion();

	public Solution() {
	}

	public Solution(int matrix[][]) {
		this.matrix = matrix;
		this.points = p.calculatePoints(this.matrix);
		this.validSolution = v.checkValidSoultion(this.matrix);

	}
	
	public Solution(List<List<Integer>> encodedMatrix) {
		this.encodedMatrix = encodedMatrix;
		this.matrix =  Decoder.encodeMatrix(this.encodedMatrix);
		this.points = p.calculatePoints(this.matrix);
		this.validSolution = v.checkValidSoultion(this.matrix);

	}

	public int[][] getMatrix() {
		return matrix;
	}
	
	public List<List<Integer>> getEncodedMatrix() {
		return encodedMatrix;
	}

	public Integer getPoints() {
		return points;
	}

	public boolean getValidSolution() {
		return validSolution;
	}

	@Override
	public int compareTo(Solution solutionObj) {
		return this.getPoints().compareTo(solutionObj.getPoints());
	}

}
