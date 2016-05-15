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
		ArrayList<Block> blockArrayListOfLetf;
		ArrayList<Block> blockArrayListOfRight;
		
		//string과 lcs를 비교해서 원래의 string에서 각 char가 변화 여부에 대해 알아본다.
		charStateArrayOfLeft = getStateArray(left, lcs);
		charStateArrayOfRight = getStateArray(right, lcs);
		
		//위에서 얻은 각 char의 변화 여부를 이용하여, line 단위의 변화 여부에 대해 알아본다.
		lineStateArrayListOfLeft = transformCharStateToLineState(left, charStateArrayOfLeft);
		lineStateArrayListOfRight = transformCharStateToLineState(right, charStateArrayOfRight);
		
		
		return null;
	}
	
	private int[] getStateArray(String s, String lcs) {
		//앞에서 부터 비교해나가면서 s가 가리키는 부분과 lcs가 가리키는 부분을 비교하여 판단한다.
		int[] stateArray = new int[s.length()];
		int lcs_index = 0;
		
		for (int i = 0; i < s.length(); i++) {
			//만약 lcs가 이미 다 확인되었으면, 그 이후를 참조하면 BOF가 발생하므로 다 변한 것으로 처리한다.
			if (lcs_index == lcs.length()) {
				stateArray[i] = CHANGED;
				continue;
			}
			//만약 현재 읽은 글자가 lcs가 가리키고 있는 글자와 동일하면 변하지 않은 것이고 그렇지 않으면 변한 것이다.
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
		//한 줄이 어디까지 인지를 확인하고, 그 줄이 변했는지를 판단한다.
		ArrayList<Integer> lineStateArrayList = new ArrayList<Integer>();
		int stateChecker;
		int lineCheckIndex = 0;
		
		for (int i = 0; i < s.length(); i++) {
			//한 줄이 어디까지인지 확인한다.
			if (s.charAt(i) == '\n') {
				stateChecker = UNCHANGED;
				
				//만약 줄이 시작 되면 이전 줄의 바로 다음 부분(lineCheckIndex가 나타내는 부분)부터 현재까지의 상태를 확인한다.
				//lineCheckIndex ~ 현재가 바로 1줄
				for (; lineCheckIndex <= i; lineCheckIndex++) {
					if (charStateArray[lineCheckIndex] == CHANGED)
						stateChecker = CHANGED;
				}
				
				lineStateArrayList.add(stateChecker);
			}
		}
		
		//마지막이 개행으로 끝나지 않을 수도 있으므로, 마지막 줄을 별도로 처리해준다.
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
	
	private ArrayList<Block> getBlockArrayList(ArrayList<Integer> lineStateArrayList) {
		ArrayList<Block> blockArrayList = new ArrayList<Block>();
		Block block;
		int lineCheckIndex = 0;
		
		for (int i = 0; i < lineStateArrayList.size() - 1; i++) {
			if (lineStateArrayList.get(i) != lineStateArrayList.get(i + 1)) {
				//길이는 끝나는 지점 - 시작 지점 + 1
				block = new Block(lineCheckIndex, i - lineCheckIndex + 1, lineStateArrayList.get(i));
				blockArrayList.add(block);
				lineCheckIndex = i + 1;
			}
		}

		//위에서 line을 block화 하는 작업에서 마지막 줄이 수행되지 않았다면, 그 줄은 1줄로 이루어진 block일 것이다.
		if (lineCheckIndex < lineStateArrayList.size()) {
			//길이는 끝나는 지점 - 시작 지점 + 1
			//3번째 parameter에서 size - 1의 원소를 사용하는 이유는 ArrayList가 0에서 시작하기 때문이다.
			block = new Block(lineCheckIndex, (lineStateArrayList.size() - 1) - lineCheckIndex + 1, 
					lineStateArrayList.get(lineStateArrayList.size() - 1));
			lineCheckIndex = (lineStateArrayList.size() - 1) + 1;
			blockArrayList.add(block);
		}
		
		return blockArrayList;
	}
}
