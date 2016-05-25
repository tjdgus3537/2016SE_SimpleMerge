package model;

import java.util.ArrayList;

/**
 * Created by Seonghyeon on 5/15/2016.
 * compare 기능과 copyToLeft, copyToRight 기능을 제공하는 Class.
 */

public class Diff implements DiffInterface{
	private LCSInterface lcs;
	
	public Diff() {
		lcs = new LCS();
	}
	
	public PairBlocks compare(String left, String right) {
		if(left == null || right == null)
			return null;

		return makePairBlocks(left, right);
	}
	
	public ArrayList<Block> copyToLeft(String left, String right, int blockNum) {
		PairBlocks pairBlocks = compare(left, right);
		//compare의 결과가 null일 때
		if(pairBlocks == null)
			return null;
		
		//TODO::이부분 나중에 삭제
		//int blockNum = findBlockNum(pairBlockArrayList.getRight(), lineNum);
		
		//잘못된 blockNum이 입력되었을 때
		if(blockNum < 0 || blockNum >= pairBlocks.getRight().size())
			return null;
		
		pairBlocks = copyToLeft(pairBlocks, blockNum);
		
		//copyToLeft를 수행할 수 없을 때 null을 return
		if(pairBlocks == null)
			return null;
		
		return pairBlocks.getLeft();
	}
	
	public ArrayList<Block> copyToRight(String left, String right, int blockNum) {
		PairBlocks pairBlocks = compare(left, right);
		//compare의 결과가 null일 때
		if(pairBlocks == null)
			return null;
		
		//TODO::이부분 나중에 삭제
		//int blockNum = findBlockNum(pairBlockArrayList.getLeft(), lineNum);
		
		//잘못된 blockNum이 입력되었을 때
		if(blockNum < 0 || blockNum >= pairBlocks.getLeft().size())
			return null;
		
		pairBlocks = copyToRight(pairBlocks, blockNum);
		
		//copyToRight를 수행할 수 없을 때 null을 return
		if(pairBlocks == null)
			return null;
		
		return pairBlocks.getRight();
	}
	
	private String getLCS(String left, String right) {
		return lcs.getLCS(left, right);
	}
	
	private PairBlocks makePairBlocks(String left, String right) {
		PairBlocks pairBlocks;
		String lcs = getLCS(left, right);
		State[] charStatesOfLeft;
		State[] charStatesOfRight;
		ArrayList<State> lineStatesOfLeft;
		ArrayList<State> lineStatesOfRight;
		ArrayList<String> lineStringsOfLeft;
		ArrayList<String> lineStringsOfRight;
		ArrayList<Block> blocksOfLetf;
		ArrayList<Block> blocksOfRight;
		
		//string과 lcs를 비교해서 원래의 string에서 각 char가 변화 여부에 대해 알아본다.
		charStatesOfLeft = getStateArray(left, lcs);
		charStatesOfRight = getStateArray(right, lcs);
		
		//위에서 얻은 각 char의 변화 여부를 이용하여, line 단위의 변화 여부에 대해 알아본다.
		lineStatesOfLeft = transformCharStateToLineState(left, charStatesOfLeft);
		lineStatesOfRight = transformCharStateToLineState(right, charStatesOfRight);
		
		//각 line이 어떤 String으로 구성되어 있는지 알아본다.
		lineStringsOfLeft = parseString(left);
		lineStringsOfRight = parseString(right);
		
		//TODO:line 사이에 SPACE 채워넣는 것 만들기.
		putSpaceLine(lineStatesOfLeft, lineStatesOfRight, lineStringsOfLeft, lineStringsOfRight);
		
		//위에서 얻은 line 단위의 변화 여부를 이용하여 block으로 묶는다.
		blocksOfLetf = getBlockArrayList(lineStatesOfLeft, lineStringsOfLeft);
		blocksOfRight = getBlockArrayList(lineStatesOfRight, lineStringsOfRight);
		
		//TODO:pairBlocks 만들기
		if(blocksOfLetf != null && blocksOfRight != null) {
			pairBlocks = new PairBlocks();
			pairBlocks.setLeft(blocksOfLetf);
			pairBlocks.setRight(blocksOfRight);
			return pairBlocks;
		}
		//TODO:여기 지우기
		//block들 사이에 SPACE block을 채워 넣어서 line을 일치하게 만든다.
//		pairBlocks = putSpaceBlocks(blocksOfLetf, blocksOfRight);
		
		//TODO: 이것도 지우기
//		pairBlocks = adjustLineNum(pairBlocks);
		
		return null;
	}

	private PairBlocks putSpaceBlocks(ArrayList<Block> left, ArrayList<Block> right) {
		PairBlocks pairBlocks = new PairBlocks();
		Block block;
		int leftIndex = 0, rightIndex = 0;
		String s;
		
		//한 쪽이 ""일 때에 대한 예외처리 부분
		//""일 때를 처리. 한 쪽이 ""이면 다른 한 쪽이 ""이 아닌 이상, 전체가 changed인 1 block이 됨
		if(left.size() == 0 && right.size() == 1) {
			s = makeNLineFeed(right.get(0).getNumberOfLine());
			block = new Block(-1, right.get(0).getNumberOfLine(), State.SPACE, s);
			pairBlocks.addLeft(block);
			pairBlocks.addRight(right.get(0));
			
			return pairBlocks;
		}
		
		if(left.size() == 1 && right.size() == 0) {
			s = makeNLineFeed(left.get(0).getNumberOfLine());
			block = new Block(-1, left.get(0).getNumberOfLine(), State.SPACE, s);
			pairBlocks.addLeft(left.get(0));
			pairBlocks.addRight(block);
			
			return pairBlocks;
		}
		
		//양 쪽 모두 index < size 여야지 BOF error가 나지 않는다.
		while (leftIndex < left.size() && rightIndex < right.size()) {
			//양 쪽 모두 UNCHANGED이면 양쪽에 모두 넣어준다.
			if (left.get(leftIndex).getState() == State.UNCHANGED && right.get(rightIndex).getState() == State.UNCHANGED) {
				pairBlocks.addLeft(left.get(leftIndex++));
				pairBlocks.addRight(right.get(rightIndex++));
			}
			//왼쪽이 UNCHANGED가 나올 때까지(즉 지금 CHANGED인 동안), 여기에 대응하는 오른쪽에 SPACE block을 채워 넣어주고,
			//현재의 왼쪽 block을 왼쪽에 넣는다.
			while (leftIndex < left.size() && left.get(leftIndex).getState() == State.CHANGED) {
				s = makeNLineFeed(left.get(leftIndex).getNumberOfLine());
				block = new Block(-1, left.get(leftIndex).getNumberOfLine(), State.SPACE, s);
				pairBlocks.addLeft(left.get(leftIndex++));
				pairBlocks.addRight(block);
			}
			//오른쪽이 UNCHANGED가 나올 때까지(즉 지금 CHANGED인 동안), 여기에 대응하는 왼쪽에 SPACE block을 채워 넣어주고,
			//현재의 오른쪽 block을 오른쪽에 넣는다.
			while (rightIndex < right.size() && right.get(rightIndex).getState() == State.CHANGED) {
				s = makeNLineFeed(right.get(rightIndex).getNumberOfLine());
				block = new Block(-1, right.get(rightIndex).getNumberOfLine(), State.SPACE, s);
				pairBlocks.addLeft(block);
				pairBlocks.addRight(right.get(rightIndex++));
			}
		}
		if (leftIndex < left.size() && rightIndex < right.size() &&
				left.get(leftIndex).getState() == State.UNCHANGED && right.get(rightIndex).getState() == State.UNCHANGED) {
			pairBlocks.addLeft(left.get(leftIndex++));
			pairBlocks.addRight(right.get(rightIndex++));
		}
		
		return pairBlocks;
	}
	
	private State[] getStateArray(String s, String lcs) {
		//앞에서 부터 비교해나가면서 s가 가리키는 부분과 lcs가 가리키는 부분을 비교하여 판단한다.
		State[] states = new State[s.length()];
		int lcs_index = 0;
		
		for (int i = 0; i < s.length(); i++) {
			//만약 lcs가 이미 다 확인되었으면, 그 이후를 참조하면 BOF가 발생하므로 다 변한 것으로 처리한다.
			if (lcs_index == lcs.length()) {
				states[i] = State.CHANGED;
				continue;
			}
			//만약 현재 읽은 글자가 lcs가 가리키고 있는 글자와 동일하면 변하지 않은 것이고 그렇지 않으면 변한 것이다.
			if (s.charAt(i) == lcs.charAt(lcs_index)) {
				states[i] = State.UNCHANGED;
				lcs_index++;
			}
			else {
				states[i] = State.CHANGED;
			}
		}

		return states;
	}
	
	private ArrayList<State> transformCharStateToLineState(String s, State[] charStates) {
		//한 줄이 어디까지 인지를 확인하고, 그 줄이 변했는지를 판단한다.
		ArrayList<State> lineStates = new ArrayList<State>();
		State stateChecker;
		int lineCheckIndex = 0;
		
		for (int i = 0; i < s.length(); i++) {
			//한 줄이 어디까지인지 확인한다.
			if (s.charAt(i) == '\n') {
				stateChecker = State.UNCHANGED;
				
				//만약 줄이 시작 되면 이전 줄의 바로 다음 부분(lineCheckIndex가 나타내는 부분)부터 현재까지의 상태를 확인한다.
				//lineCheckIndex ~ 현재가 바로 1줄
				for (; lineCheckIndex <= i; lineCheckIndex++) {
					if (charStates[lineCheckIndex] == State.CHANGED)
						stateChecker = State.CHANGED;
				}
				
				lineStates.add(stateChecker);
			}
		}
		
		//마지막이 개행으로 끝나지 않을 수도 있으므로, 마지막 줄을 별도로 처리해준다.
		if (lineCheckIndex != s.length()) {
			stateChecker = State.UNCHANGED;

			for (; lineCheckIndex < s.length(); lineCheckIndex++) {
				if (charStates[lineCheckIndex] == State.CHANGED)
					stateChecker = State.CHANGED;
			}
			
			lineStates.add(stateChecker);
		}
		
		return lineStates;
	}
	
	private ArrayList<String> parseString(String s) {
		//한 줄씩 찾아서 String 단위로 묶어준다.
		ArrayList<String> lineStrings = new ArrayList<String>();
		int lineCheckIndex = 0;
		
		for (int i = 0; i < s.length(); i++) {
			//한 줄이 어디까지인지 확인한다.
			if (s.charAt(i) == '\n') {
				//만약 줄이 시작 되면 이전 줄의 바로 다음 부분(lineCheckIndex가 나타내는 부분)부터 현재까지를 하나의 String으로 만든다.
				//lineCheckIndex ~ 현재가 바로 1줄
				//substring method는 startIndex ~ endIndex - 1 까지임. 따라서 i + 1 사용.
				lineStrings.add(s.substring(lineCheckIndex, i + 1));
				//현재 줄의 바로 다음, 즉 다음 줄의 시작을 나타내도록 index를 조정한다.
				lineCheckIndex = i + 1;
			}
		}
		
		//마지막이 개행으로 끝나지 않을 수도 있으므로, 마지막 줄을 별도로 처리해준다.
		if (lineCheckIndex != s.length()) {
			lineStrings.add(s.substring(lineCheckIndex));
			lineCheckIndex = s.length();
		}
		
		return lineStrings;
	}
	
	//TODO:
	private void putSpaceLine(ArrayList<State> lineStatesOfLeft, ArrayList<State> lineStatesOfRight, 
			ArrayList<String> lineStringsOfLeft, ArrayList<String> lineStringsOfRight) {
		int leftIndex = 0, rightIndex = 0;
		
		//한 쪽이 ""일 때에 대한 예외처리 부분
		//""일 때를 처리. 한 쪽이 ""이면 다른 한 쪽이 ""이 아닌 이상, 전체가 changed인 1 block이 됨
		if(lineStatesOfLeft.size() == 0 && lineStatesOfRight.size() != 0) {
			for(int i = 0; i < lineStatesOfRight.size(); i++) {
				lineStatesOfLeft.add(i, State.SPACE);
				lineStringsOfLeft.add(i, "\n");
			}
			return;
		}
		
		if(lineStatesOfLeft.size() != 0 && lineStatesOfRight.size() == 0) {
			for(int i = 0 ; i < lineStatesOfLeft.size(); i++) {
				lineStatesOfRight.add(i, State.SPACE);
				lineStringsOfRight.add(i, "\n");
			}
			return;
		}
		
		
		//양 쪽 모두 index < size 여야지 BOF error가 나지 않는다.
		while (leftIndex < lineStatesOfLeft.size() && rightIndex < lineStatesOfRight.size()) {
			//양 쪽 모두 UNCHANGED이면 양쪽에 모두 넣어준다.
			if (lineStatesOfLeft.get(leftIndex) == State.UNCHANGED && lineStatesOfRight.get(rightIndex) == State.UNCHANGED) {
				leftIndex++;
				rightIndex++;
			}
			//왼쪽이 UNCHANGED가 나올 때까지(즉 지금 CHANGED인 동안), 여기에 대응하는 오른쪽에 SPACE line을 채워 넣어주고,
			//현재의 왼쪽 block을 왼쪽에 넣는다.
			while (leftIndex < lineStatesOfLeft.size() && lineStatesOfLeft.get(leftIndex) == State.CHANGED) {
				lineStatesOfRight.add(rightIndex, State.SPACE);
				lineStringsOfRight.add(rightIndex, "\n");
				leftIndex++;
				rightIndex++;
			}
			//오른쪽이 UNCHANGED가 나올 때까지(즉 지금 CHANGED인 동안), 여기에 대응하는 왼쪽에 SPACE block을 채워 넣어주고,
			//현재의 오른쪽 block을 오른쪽에 넣는다.
			while (rightIndex < lineStatesOfRight.size() && lineStatesOfRight.get(rightIndex) == State.CHANGED) {
				lineStatesOfLeft.add(leftIndex, State.SPACE);
				lineStringsOfLeft.add(leftIndex, "\n");
				leftIndex++;
				rightIndex++;
			}
		}
		
	}
	
	private ArrayList<Block> getBlockArrayList(
			ArrayList<State> lineStates, ArrayList<String> lineStrings) {
		ArrayList<Block> blocks = new ArrayList<Block>();
		Block block;
		String s;
		int lineCheckIndex = 0;
		
		for (int i = 0; i < lineStates.size() - 1; i++) {
			if (lineStates.get(i) != lineStates.get(i + 1)) {
				//길이는 끝나는 지점 - 시작 지점 + 1
				s = concatString(lineStrings, lineCheckIndex, i);
				block = new Block(lineCheckIndex, i - lineCheckIndex + 1, lineStates.get(i), s);
				blocks.add(block);
				lineCheckIndex = i + 1;
			}
		}

		//위에서 line을 block화 하는 작업에서 마지막 줄이 수행되지 않았다면, 그 줄은 1줄로 이루어진 block일 것이다.
		if (lineCheckIndex < lineStates.size()) {
			//길이는 끝나는 지점 - 시작 지점 + 1
			//3번째 parameter에서 size - 1의 원소를 사용하는 이유는 ArrayList가 0에서 시작하기 때문이다.
			s = concatString(lineStrings, lineCheckIndex, (lineStates.size() - 1));
			block = new Block(lineCheckIndex, (lineStates.size() - 1) - lineCheckIndex + 1, 
					lineStates.get(lineStates.size() - 1), s);
			lineCheckIndex = (lineStates.size() - 1) + 1;
			blocks.add(block);
		}
		
		return blocks;
	}
	
	private String concatString(ArrayList<String> lineStrings, int start, int end) {
		//start ~ end 까지의 String들을 concatenate 해서 return
		String s = "";
		
		for(int i = start; i <= end; i++)
			s += lineStrings.get(i);
		
		return s;
	}

	private String makeNLineFeed(int count) {
		//count의 개수만큼의 개행을 String으로 return
		String s ="";
		
		for(int i = 0 ; i < count; i++)
			s += "\n";
		
		return s;
	}
	
	//TODO::이부분 나중에 삭제
	private int findBlockNum(ArrayList<Block> blocks, int lineNum) {
		//lineNum에 속하는 blockNum을 return
		int blockNum;
		
		//lineNum이 block의 시작 줄 ~ (시작 줄 + 줄의 개수 - 1)에 있으면 그 line은 해당 block에 속하는 것
		for(blockNum = 0 ; blockNum < blocks.size(); blockNum++) {
			if(blocks.get(blockNum).getStartNumber() <= lineNum &&
					lineNum <= blocks.get(blockNum).getStartNumber() 
					+ blocks.get(blockNum).getNumberOfLine() - 1) {
				break;
			}
		}
		
		//잘못된 lineNum이 입력 되었을 때.
		if(blockNum == blocks.size())
			return -1;
		
		return blockNum;
	}
	
	//TODO::사실 return해 줄 필요 없음 - 리팩토링 때 개선
	private PairBlocks copyToLeft(PairBlocks pairBlocks, int blockNum) {
		//unchanged 상황에서는 copyToLeft가 실행되어서는 안 된다.
		//TODO:: 추후 가능하면 exception으로 바꾸기.
		if(pairBlocks.getRight().get(blockNum).getState() == State.UNCHANGED)
			return null;

		//우측의 blockNum번 block을 좌측에 추가하고, 좌측의 blockNum + 1번째 block을 삭제.
		pairBlocks.getLeft().add(blockNum, pairBlocks.getRight().get(blockNum));
		pairBlocks.getLeft().remove(blockNum + 1);
		
		return pairBlocks;
	}
	
	//TODO::사실 return해 줄 필요 없음 - 리팩토링 때 개선
	private PairBlocks copyToRight(PairBlocks pairBlocks, int blockNum) {
		//unchanged 상황에서는 copyToLeft가 실행되어서는 안 된다.
		//TODO:: 추후 가능하면 exception으로 바꾸기.
		if(pairBlocks.getLeft().get(blockNum).getState() == State.UNCHANGED)
			return null;
		
		//우측의 blockNum번 block을 우측에 추가하고, 우측의 blockNum + 1번째 block을 삭제.
		pairBlocks.getRight().add(blockNum, pairBlocks.getLeft().get(blockNum));
		pairBlocks.getRight().remove(blockNum + 1);
		
		return pairBlocks;
	}
	
	//TODO::사실 return해 줄 필요 없음 - 리팩토링 때 개선
	private PairBlocks adjustLineNum(PairBlocks pairBlocks) {
		//block들의 startNum을 SPACE를 반영해서 다시 조정함.
		int line = 0;
		
		//left와 right의 block 수는 동일한 것이 input으로 들어옴.
		for(int i = 0 ; i < pairBlocks.getLeft().size(); i++) {
			pairBlocks.getLeft().get(i).setStartNumber(line);
			pairBlocks.getRight().get(i).setStartNumber(line);
			line += pairBlocks.getLeft().get(i).getNumberOfLine();
		}
		
		return pairBlocks;
	}
}