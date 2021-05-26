package algorithm;

import java.util.List;

import model.Solution;

public class ParticleSwarmOptimization {

	public Solution particleSwarmOptimization() {

		Solution solutionObj = RandomWalk.radomEncodedWalk();
		List<List<Integer>> myString = solutionObj.getEncodedMatrix();
		List<Integer> myStringZWO = myString.get(0);
		int shiftOne = myStringZWO.get(0);

		System.out.println(myString);
		System.out.println(myStringZWO);
		System.out.println(shiftOne);

		return solutionObj;

	}

}
