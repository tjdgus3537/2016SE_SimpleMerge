package model;

import java.util.ArrayList;

/**
 * Created by Seonghyeon on 5/15/2016.
 * compare 기능과 copyToLeft, copyToRight 기능을 제공하는 Class.
 */

public class Diff implements StateUsable{
	private LCS lcs;
	
	/**
	 * 생성자
     */
	public Diff() {
		lcs = new LCS();
	}
	
	/**
	 * 두 개의 string을 input으로 받아서 비교 결과를 PairBlockArrayList 형태로 return.
	 * compare 함수를 실행하면 내부적으로 member variable값이 변경된다.
	 * @param left 비교할 문자열
	 * @param right 비교할 문자열
     * @return 비교 결과
     */
	public PairBlockArrayList compare(String left, String right) {
		if(left == null || right == null)
			return null;
		
		return makePairBlockArrayList(left, right);
	}
	
	/**
	 * 특정 줄에 해당하는 우측의 Block을 좌측으로 copy한 결과를 Block들로 return
	 * @param left 비교할 문자열
	 * @param right 비교할 문자열
	 * @param lineNum copy할 부분의 줄 번호
     * @return copy를 적용하고 난 후의 좌측의 ArrayList<Block>
     */	

	public ArrayList<Block> copyToLeft(String left, String right, int lineNum) {
		PairBlockArrayList pairBlockArrayList = compare(left, right);
		int blockNum = findBlockNum(pairBlockArrayList.getRight(), lineNum);
		
		//잘못된 blockNum이 입력되었을 때
		if(blockNum == -1)
			return null;
		
		pairBlockArrayList = copyToLeft(pairBlockArrayList, blockNum);
		
		return pairBlockArrayList.getLeft();
	}
	
	/**
	 * 특정 줄에 해당하는 우측의 Block을 좌측으로 copy한 결과를 Block들로 return
	 * @param left 비교할 문자열
	 * @param right 비교할 문자열
	 * @param lineNum copy할 부분의 줄 번호
     * @return copy를 적용하고 난 후의 좌측의 ArrayList<Block>
     */	
	//@TODO::내부 구현하기
	public ArrayList<Block> copyToRight(String left, String right, int lineNum) {
		PairBlockArrayList pairBlockArrayList = compare(left, right);
		int blockNum = findBlockNum(pairBlockArrayList.getLeft(), lineNum);
		
		//잘못된 blockNum이 입력되었을 때
		if(blockNum == -1)
			return null;
		
		pairBlockArrayList = copyToRight(pairBlockArrayList, blockNum);
		
		return pairBlockArrayList.getRight();
	}
	
	private String getLCS(String left, String right) {
		return lcs.getLCS(left, right);
	}
	
	private PairBlockArrayList makePairBlockArrayList(String left, String right) {
		PairBlockArrayList pairBlockArrayList;
		String lcs = getLCS(left, right);
		int[] charStateArrayOfLeft;
		int[] charStateArrayOfRight;
		ArrayList<Integer> lineStateArrayListOfLeft;
		ArrayList<Integer> lineStateArrayListOfRight;
		ArrayList<String> lineStringArrayListOfLeft;
		ArrayList<String> lineStringArrayListOfRight;
		ArrayList<Block> blockArrayListOfLetf;
		ArrayList<Block> blockArrayListOfRight;
		
		//string과 lcs를 비교해서 원래의 string에서 각 char가 변화 여부에 대해 알아본다.
		charStateArrayOfLeft = getStateArray(left, lcs);
		charStateArrayOfRight = getStateArray(right, lcs);
		
		//위에서 얻은 각 char의 변화 여부를 이용하여, line 단위의 변화 여부에 대해 알아본다.
		lineStateArrayListOfLeft = transformCharStateToLineState(left, charStateArrayOfLeft);
		lineStateArrayListOfRight = transformCharStateToLineState(right, charStateArrayOfRight);
		
		//각 line이 어떤 String으로 구성되어 있는지 알아본다.
		lineStringArrayListOfLeft = parseString(left);
		lineStringArrayListOfRight = parseString(right);
		
		
		//위에서 얻은 line 단위의 변화 여부를 이용하여 block으로 묶는다.
		blockArrayListOfLetf = getBlockArrayList(lineStateArrayListOfLeft, lineStringArrayListOfLeft);
		blockArrayListOfRight = getBlockArrayList(lineStateArrayListOfRight, lineStringArrayListOfRight);
		
		//block들 사이에 SPACE block을 채워 넣어서 line을 일치하게 만든다.
		pairBlockArrayList = putSpaceBlocks(blockArrayListOfLetf, blockArrayListOfRight);
		
		return null;
	}

	private PairBlockArrayList putSpaceBlocks(ArrayList<Block> left, ArrayList<Block> right) {
		PairBlockArrayList pairBlockArrayList = new PairBlockArrayList();
		Block block;
		int leftIndex = 0, rightIndex = 0;
		String s;
		
		//한 쪽이 ""일 때에 대한 예외처리 부분
		//""일 때를 처리. 한 쪽이 ""이면 다른 한 쪽이 ""이 아닌 이상, 전체가 changed인 1 block이 됨
		if(left.size() == 0 && right.size() == 1) {
			s = makeNLineFeed(right.get(0).getNumberOfLine());
			block = new Block(-1, right.get(0).getNumberOfLine(), SPACE, s);
			pairBlockArrayList.addLeft(block);
			pairBlockArrayList.addRight(right.get(0));
			
			return pairBlockArrayList;
		}
		
		if(left.size() == 1 && right.size() == 0) {
			s = makeNLineFeed(left.get(0).getNumberOfLine());
			block = new Block(-1, left.get(0).getNumberOfLine(), SPACE, s);
			pairBlockArrayList.addLeft(left.get(0));
			pairBlockArrayList.addRight(block);
			
			return pairBlockArrayList;
		}
		
		//양 쪽 모두 index < size 여야지 BOF error가 나지 않는다.
		while (leftIndex < left.size() && rightIndex < right.size()) {
			//양 쪽 모두 UNCHANGED이면 양쪽에 모두 넣어준다.
			if (left.get(leftIndex).getState() == UNCHANGED && right.get(rightIndex).getState() == UNCHANGED) {
				pairBlockArrayList.addLeft(left.get(leftIndex++));
				pairBlockArrayList.addRight(right.get(rightIndex++));
			}
			//왼쪽이 UNCHANGED가 나올 때까지(즉 지금 CHANGED인 동안), 여기에 대응하는 오른쪽에 SPACE block을 채워 넣어주고,
			//현재의 왼쪽 block을 왼쪽에 넣는다.
			while (leftIndex < left.size() && left.get(leftIndex).getState() == CHANGED) {
				s = makeNLineFeed(left.get(leftIndex).getNumberOfLine());
				block = new Block(-1, left.get(leftIndex).getNumberOfLine(), SPACE, s);
				pairBlockArrayList.addLeft(left.get(leftIndex++));
				pairBlockArrayList.addRight(block);
			}
			//오른쪽이 UNCHANGED가 나올 때까지(즉 지금 CHANGED인 동안), 여기에 대응하는 왼쪽에 SPACE block을 채워 넣어주고,
			//현재의 오른쪽 block을 오른쪽에 넣는다.
			while (rightIndex < right.size() && right.get(rightIndex).getState() == CHANGED) {
				s = makeNLineFeed(right.get(rightIndex).getNumberOfLine());
				block = new Block(-1, right.get(rightIndex).getNumberOfLine(), SPACE, s);
				pairBlockArrayList.addLeft(block);
				pairBlockArrayList.addRight(right.get(rightIndex++));
			}
		}
		
		return pairBlockArrayList;
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
	
	private ArrayList<String> parseString(String s) {
		//한 줄씩 찾아서 String 단위로 묶어준다.
		ArrayList<String> lineStringArrayList = new ArrayList<String>();
		int lineCheckIndex = 0;
		
		for (int i = 0; i < s.length(); i++) {
			//한 줄이 어디까지인지 확인한다.
			if (s.charAt(i) == '\n') {
				//만약 줄이 시작 되면 이전 줄의 바로 다음 부분(lineCheckIndex가 나타내는 부분)부터 현재까지를 하나의 String으로 만든다.
				//lineCheckIndex ~ 현재가 바로 1줄
				//substring method는 startIndex ~ endIndex - 1 까지임. 따라서 i + 1 사용.
				lineStringArrayList.add(s.substring(lineCheckIndex, i + 1));
				//현재 줄의 바로 다음, 즉 다음 줄의 시작을 나타내도록 index를 조정한다.
				lineCheckIndex = i + 1;
			}
		}
		
		//마지막이 개행으로 끝나지 않을 수도 있으므로, 마지막 줄을 별도로 처리해준다.
		if (lineCheckIndex != s.length()) {
			lineStringArrayList.add(s.substring(lineCheckIndex));
			lineCheckIndex = s.length();
		}
		
		return lineStringArrayList;
	}
	
	private ArrayList<Block> getBlockArrayList(
			ArrayList<Integer> lineStateArrayList, ArrayList<String> lineStringArrayList) {
		ArrayList<Block> blockArrayList = new ArrayList<Block>();
		Block block;
		String s;
		int lineCheckIndex = 0;
		
		for (int i = 0; i < lineStateArrayList.size() - 1; i++) {
			if (lineStateArrayList.get(i) != lineStateArrayList.get(i + 1)) {
				//길이는 끝나는 지점 - 시작 지점 + 1
				s = concatString(lineStringArrayList, lineCheckIndex, i);
				block = new Block(lineCheckIndex, i - lineCheckIndex + 1, lineStateArrayList.get(i), s);
				blockArrayList.add(block);
				lineCheckIndex = i + 1;
			}
		}

		//위에서 line을 block화 하는 작업에서 마지막 줄이 수행되지 않았다면, 그 줄은 1줄로 이루어진 block일 것이다.
		if (lineCheckIndex < lineStateArrayList.size()) {
			//길이는 끝나는 지점 - 시작 지점 + 1
			//3번째 parameter에서 size - 1의 원소를 사용하는 이유는 ArrayList가 0에서 시작하기 때문이다.
			s = concatString(lineStringArrayList, lineCheckIndex, (lineStateArrayList.size() - 1));
			block = new Block(lineCheckIndex, (lineStateArrayList.size() - 1) - lineCheckIndex + 1, 
					lineStateArrayList.get(lineStateArrayList.size() - 1), s);
			lineCheckIndex = (lineStateArrayList.size() - 1) + 1;
			blockArrayList.add(block);
		}
		
		return blockArrayList;
	}
	
	private String concatString(ArrayList<String> lineStringArrayList, int start, int end) {
		//start ~ end 까지의 String들을 concatenate 해서 return
		String s = "";
		
		for(int i = start; i <= end; i++)
			s += lineStringArrayList.get(i);
		
		return s;
	}

	private String makeNLineFeed(int count) {
		//count의 개수만큼의 개행을 String으로 return
		String s ="";
		
		for(int i = 0 ; i < count; i++)
			s += "\n";
		
		return s;
	}
	
	private int findBlockNum(ArrayList<Block> blockArrayList, int lineNum) {
		int blockNum;
		
		//lineNum이 block의 시작 줄 ~ (시작 줄 + 줄의 개수 - 1)에 있으면 그 line은 해당 block에 속하는 것
		for(blockNum = 0 ; blockNum < blockArrayList.size(); blockNum++) {
			if(blockArrayList.get(blockNum).getStartNumber() <= lineNum &&
					lineNum <= blockArrayList.get(blockNum).getStartNumber() 
					+ blockArrayList.get(blockNum).getNumberOfLine() - 1) {
				break;
			}
		}
		
		//잘못된 lineNum이 입력 되었을 때.
		if(blockNum == blockArrayList.size())
			return -1;
		
		return blockNum;
	}
	
	//@TODO::사실 return해 줄 필요 없음 - 리팩토링 때 개선
	private PairBlockArrayList copyToLeft(PairBlockArrayList pairBlockArrayList, int blockNum) {
		pairBlockArrayList.getLeft().add(blockNum, pairBlockArrayList.getRight().get(blockNum));
		pairBlockArrayList.getLeft().remove(blockNum + 1);
		
		return pairBlockArrayList;
	}
	
	//@TODO::사실 return해 줄 필요 없음 - 리팩토링 때 개선
	private PairBlockArrayList copyToRight(PairBlockArrayList pairBlockArrayList, int blockNum) {
		pairBlockArrayList.getRight().add(blockNum, pairBlockArrayList.getLeft().get(blockNum));
		pairBlockArrayList.getRight().remove(blockNum + 1);
		
		return pairBlockArrayList;
	}
	
	//@TODO::사실 return해 줄 필요 없음 - 리팩토링 때 개선
	private PairBlockArrayList adjustLineNum(PairBlockArrayList pairBlockArrayList) {
		int line = 0;
		
		//left와 right의 block 수는 동일한 것이 input으로 들어옴.
		for(int i = 0 ; i < pairBlockArrayList.getLeft().size(); i++) {
			pairBlockArrayList.getLeft().get(i).setStartNumber(line);
			pairBlockArrayList.getRight().get(i).setStartNumber(line);
			line += pairBlockArrayList.getLeft().get(i).getNumberOfLine();
		}
		
		return pairBlockArrayList;
	}
}
