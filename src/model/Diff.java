package model;

import java.util.ArrayList;

public class Diff implements DiffInterface, StateUsable{

	@Override
	public PairBlockArrayList compare(String left, String right) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String blockCopyToLeft(String left, String right, int blockNumber,
			PairBlockArrayList pairBlockArrayList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String blockCopyToRight(String left, String right, int blockNumber,
			PairBlockArrayList pairBlockArrayList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String allCopyToLeft(String left, String right) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String allCopyToRight(String left, String right) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String ApplySpaceBlockToEnter(String s, ArrayList<Block> blocks) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private String getLCS(String left, String right) {
		LCSInterface lcs = new LCS();
		
		return lcs.getLCS(left, right);
	}
	
	private PairBlockArrayList makePairBlockArrayList(String left, String right) {
		String lcs = getLCS(left, right);
		int[] charStateArrayOfLeft;
		int[] charStateArrayOfRight;
		ArrayList<Integer> lineStateArrayListOfLeft;
		ArrayList<Integer> lineStateArrayListOfRight;
		
		charStateArrayOfLeft = getStateArray(left, lcs);
		charStateArrayOfRight = getStateArray(right, lcs);
		
		
		
		return null;
	}
	
	private int[] getStateArray(String s, String lcs) {
		int[] stateArray = new int[s.length()];
		int lcs_index = 0;
		
		for (int i = 0; i < s.length(); i++) {
			if (lcs_index == lcs.length()) {
				stateArray[i] = CHANGED;
				continue;
			}
			if (s.charAt(i) == lcs.charAt(lcs_index)) {
				stateArray[i] = UNCHANGED;
				lcs_index++;
			}
			else {
				stateArray[i] = CHANGED;
			}
		}

		return stateArray;
	}
	
	private ArrayList<Integer> transformCharStateToLineState(String s, int[] charStateArray) {
		ArrayList<Integer> lineStateArrayList = new ArrayList<Integer>();
		int stateChecker;
		int lineCheckIndex = 0;
		
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '\n') {
				stateChecker = UNCHANGED;
				
				for (; lineCheckIndex <= i; lineCheckIndex++) {
					if (charStateArray[lineCheckIndex] == CHANGED)
						stateChecker = CHANGED;
				}
				
				lineStateArrayList.add(stateChecker);
			}
		}
		
		if (lineCheckIndex != s.length()) {
			stateChecker = UNCHANGED;

			for (; lineCheckIndex <= s.length(); lineCheckIndex++) {
				if (charStateArray[lineCheckIndex] == CHANGED)
					stateChecker = CHANGED;
			}
			
			lineStateArrayList.add(stateChecker);
		}
		
		return lineStateArrayList;
	}
}
