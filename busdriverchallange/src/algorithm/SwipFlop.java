package algorithm;

import java.util.ArrayList;
import java.util.List;

import model.Solution;

/**
 * @author Amir Razagh Khah
 * @author Tobias Stelter
 * 
 *         This class implements the swip flop - algorithm.
 */

public class SwipFlop {

	public Solution swipFlop(int num, int In) {

		Solution solutionObj  = new Solution() ;
		if (In == 1) 
			{
			solutionObj = swip(num);
			}
		else if (In == 2)
		{
		solutionObj = flop(num);
		}

		
		return solutionObj;
		}
	

	public static Solution swip(int num) {
		
		Solution solutionObj = RandomWalk.radomEncodedWalk();
		System.out.println("points:" + solutionObj.getPoints());
		List<List<Integer>> encodedSolution = solutionObj.getEncodedMatrix();

		
		for (int i = 0; i < num; i++) {

			int number = RandomWalk.getRandomInt(0, 41);
			List<Integer> day = RandomWalk.randomDriverCombinationForDay(number);
			encodedSolution.set(number, day);
			solutionObj = new Solution(encodedSolution);
			System.out.println("points:" + solutionObj.getPoints());

		}
		return solutionObj;

	}
	
	public static Solution flop(int num) {
		
		Solution solutionObj = RandomWalk.radomEncodedWalk();
		System.out.println("points:" + solutionObj.getPoints());
		List<List<Integer>> encodedSolution = solutionObj.getEncodedMatrix();

		System.out.println(encodedSolution);

		for (int i = 0; i < num; i++) {

			int number = RandomWalk.getRandomInt(0, 41);
			List<Integer> day= encodedSolution.get(number);
			int dayshift = day.get(0);
			int nightshift = day.get(1);
			day.set(0, nightshift);
			day.set(1, dayshift);
			encodedSolution.set(number, day);
			
			solutionObj = new Solution(encodedSolution);
			
			System.out.println("points:" + solutionObj.getPoints());

		}
		return solutionObj;
	}
}
