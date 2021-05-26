package model;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ui.Printer;

/**
 * @author Tobias Stelter This class represents the solution-object
 */

public class Solution implements Comparable<Solution> {

	private int matrix[][];
	List<List<Integer>> encodedMatrix;
	private Integer points = -10000;
	private boolean validSolution = true;

	int poolSize = 2;
	ExecutorService executor = Executors.newFixedThreadPool(poolSize);

	public Solution() {
		this.matrix = Default.matrixZeros;
	}

	public Solution(int matrix[][]) {
		this.matrix = matrix;
		buildSolution();

	}

	public Solution(List<List<Integer>> encodedMatrix) {
		this.encodedMatrix = encodedMatrix;
		this.matrix = Decoder.encodeMatrix(this.encodedMatrix);
		buildSolution();

	}

	private void buildSolution() {

		Points p = new Points(this.matrix);
		Runnable pointsThread = p;

		ValidSoultion v = new ValidSoultion(this.matrix);
		Runnable validSoultionThread = v;

		executor.execute(validSoultionThread);
		executor.execute(pointsThread);

		executor.shutdown();

		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			Printer.printError();
		}

		this.validSolution = v.getValidSoultion();
		this.points = p.getPoints();
	}

	/**
	 * Finds the solution with the highest points
	 * 
	 * @return matrix
	 */

	public static Solution findBestSolution(List<Solution> solutions) {

		Collections.sort(solutions, Collections.reverseOrder());

		Solution solutionObj = new Solution();
		solutionObj = solutions.get(0);

		return solutionObj;
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
