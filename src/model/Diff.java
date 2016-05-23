package model;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seonghyeon on 5/15/2016.
 * compare 기능과 copyToLeft, copyToRight 기능을 제공하는 Class.
 */

// TODO Diff가 static이어야 하는지 아닌지 확인해야 함
public class Diff implements DiffInterface{
	private LCSInterface lcs;
	
	public Diff() {
		lcs = new LCS();
	}

	public Diff(LCSInterface lcs) { this.lcs = lcs; }

	@Override
	public void compare(ComparisonFile left, ComparisonFile right) {
		if(left == null || right == null) return;
		
		PairBlockList pairBlockList = createCompareResult(left.getContentToString(), right.getContentToString());

		ObservableList<BlockInterface> leftCompareResult = left.getContent();
		ObservableList<BlockInterface> rightCompareResult = right.getContent();

		leftCompareResult.clear();
		rightCompareResult.clear();

		leftCompareResult.addAll(pairBlockList.getLeft());
		rightCompareResult.addAll(pairBlockList.getRight());
	}
	
	private String getLCS(String left, String right) {
		return lcs.getLCS(left, right);
	}
	
	private PairBlockList createCompareResult(String left, String right) {
		PairBlockList pairBlocks;
		String lcs = getLCS(left, right);

		// TODO ArrayList와 Array로 짠 구조를 개선할 수 있는지 알아봐야 함
		//string과 lcs를 비교해서 원래의 string에서 각 char가 변화 여부에 대해 알아본다.
		State[] charStatesOfLeft = getStateArray(left, lcs);
		State[] charStatesOfRight = getStateArray(right, lcs);
		
		//위에서 얻은 각 char의 변화 여부를 이용하여, line 단위의 변화 여부에 대해 알아본다.
		ArrayList<State> lineStatesOfLeft = transformCharStateToLineState(left, charStatesOfLeft);
		ArrayList<State> lineStatesOfRight = transformCharStateToLineState(right, charStatesOfRight);
		
		//각 line이 어떤 String으로 구성되어 있는지 알아본다.
		ArrayList<String> lineStringsOfLeft = parseString(left);
		ArrayList<String> lineStringsOfRight = parseString(right);
		
		
		//위에서 얻은 line 단위의 변화 여부를 이용하여 block으로 묶는다.
		ArrayList<Block> blocksOfLeft = getBlockArrayList(lineStatesOfLeft, lineStringsOfLeft);
		ArrayList<Block> blocksOfRight = getBlockArrayList(lineStatesOfRight, lineStringsOfRight);
		
		//block들 사이에 SPACE block을 채워 넣어서 line을 일치하게 만든다.
		pairBlocks = putSpaceBlocks(blocksOfLeft, blocksOfRight);
		
		return pairBlocks;
	}

	private PairBlockList putSpaceBlocks(ArrayList<Block> left, ArrayList<Block> right) {
		PairBlockList pairBlocks = new PairBlockList();
		List<BlockInterface> newLeft = pairBlocks.getLeft();
		List<BlockInterface> newRight = pairBlocks.getRight();
		Block block;
		int leftIndex = 0, rightIndex = 0;
		String s;
		
		//한 쪽이 ""일 때에 대한 예외처리 부분
		//""일 때를 처리. 한 쪽이 ""이면 다른 한 쪽이 ""이 아닌 이상, 전체가 changed인 1 block이 됨
		// TODO 왜 right.size가 1이여야 하는지 모르겠네;;;
		if(left.isEmpty() && right.size() == 1) {
			s = makeNLineFeed(right.get(0).getNumberOfLines());
			block = new Block(State.SPACE, s);
			newLeft.add(block);
			newRight.add(right.get(0));
			
			return pairBlocks;
		}
		
		if(left.size() == 1 && right.isEmpty()) {
			s = makeNLineFeed(left.get(0).getNumberOfLines());
			block = new Block(State.SPACE, s);
			newLeft.add(left.get(0));
			newRight.add(block);
			
			return pairBlocks;
		}
		
		//양 쪽 모두 index < size 여야지 BOF error가 나지 않는다.
		while (leftIndex < left.size() && rightIndex < right.size()) {
			//양 쪽 모두 UNCHANGED이면 양쪽에 모두 넣어준다.
			if (left.get(leftIndex).getState() == State.UNCHANGED && right.get(rightIndex).getState() == State.UNCHANGED) {
				newLeft.add(left.get(leftIndex++));
				newRight.add(right.get(rightIndex++));
			}
			//왼쪽이 UNCHANGED가 나올 때까지(즉 지금 CHANGED인 동안), 여기에 대응하는 오른쪽에 SPACE block을 채워 넣어주고,
			//현재의 왼쪽 block을 왼쪽에 넣는다.
			while (leftIndex < left.size() && left.get(leftIndex).getState() == State.CHANGED) {
				s = makeNLineFeed(left.get(leftIndex).getNumberOfLines());
				block = new Block(State.SPACE, s);
				newLeft.add(left.get(leftIndex++));
				newRight.add(block);
			}
			//오른쪽이 UNCHANGED가 나올 때까지(즉 지금 CHANGED인 동안), 여기에 대응하는 왼쪽에 SPACE block을 채워 넣어주고,
			//현재의 오른쪽 block을 오른쪽에 넣는다.
			while (rightIndex < right.size() && right.get(rightIndex).getState() == State.CHANGED) {
				s = makeNLineFeed(right.get(rightIndex).getNumberOfLines());
				block = new Block(State.SPACE, s);
				newLeft.add(block);
				newRight.add(right.get(rightIndex++));
			}
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
				block = new Block(lineStates.get(i), s);
				blocks.add(block);
				lineCheckIndex = i + 1;
			}
		}

		//위에서 line을 block화 하는 작업에서 마지막 줄이 수행되지 않았다면, 그 줄은 1줄로 이루어진 block일 것이다.
		if (lineCheckIndex < lineStates.size()) {
			//길이는 끝나는 지점 - 시작 지점 + 1
			//3번째 parameter에서 size - 1의 원소를 사용하는 이유는 ArrayList가 0에서 시작하기 때문이다.
			s = concatString(lineStrings, lineCheckIndex, (lineStates.size() - 1));
			block = new Block(lineStates.get(lineStates.size() - 1), s);
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
		StringBuilder stringBuilder = new StringBuilder();
		
		for(int i = 0 ; i < count; i++)
			stringBuilder.append("\n");
		
		return stringBuilder.toString();
	}

	// TODO 원래 코드는 blockNum이 OutOfBoundaryException 발생하는지 미리 체크했는데 살릴지 말지 확인하기
	@Override
	public void copyToLeft(ComparisonFile left, ComparisonFile right, int blockNum) {
		ObservableList<BlockInterface> leftCompareResult = left.getContent();
		ObservableList<BlockInterface> rightCompareResult = right.getContent();

		//unchanged 상황에서는 copyToLeft가 실행되어서는 안 된다.
		//TODO:: 추후 가능하면 exception으로 바꾸기.
		//TODO 이걸 exception으로 해야 할지 아니면 부드럽게 처리할지 결정해야 함.
		if(rightCompareResult.get(blockNum).getState() == State.UNCHANGED)
			return;
		
		//우측의 blockNum번 block을 좌측에 추가하고, 좌측의 blockNum + 1번째 block을 삭제.
		// TODO 스테이트가 Unchanged로 바뀌어야 함
		Block newBlock = new Block(State.UNCHANGED, rightCompareResult.get(blockNum).getContent());
		rightCompareResult.remove(blockNum);
		leftCompareResult.remove(blockNum);
		rightCompareResult.add(newBlock);
		leftCompareResult.add(newBlock);
	}

	@Override
	public void copyToRight(ComparisonFile left, ComparisonFile right, int blockNum) {
		copyToLeft(right, left, blockNum);
	}
}
