package model.diff;

import model.diff.block.Block;
import model.diff.block.PairBlocks;
import model.diff.block.CompState;

import java.util.ArrayList;

/**
 * Created by Seonghyeon on 5/15/2016.
 * compare 기능을 제공하는 Class.
 */

public class Diff implements DiffInterface{
	private enum Direction {
		NONE, UP, LEFT, UP_AND_LEFT;
	}
	
	public Diff() {
	}
	
	public PairBlocks compare(String left, String right) {
		//만약 둘 중 하나라도 null이면 null을 return
		if(left == null || right == null)
			return null;

		return makePairBlocks(left, right);
	}
	
	private PairBlocks makePairBlocks(String left, String right) {		
		PairBlocks pairBlocks;

		//각 line이 어떤 String으로 구성되어 있는지 개행 단위로 파싱한다.
		ArrayList<String> lineStringsOfLeft = parseString(left);
		ArrayList<String> lineStringsOfRight = parseString(right);
		
		//line by line으로 비교하여 동일한 line들을 얻어낸다.
		ArrayList<String> lcs = makeLCS(lineStringsOfLeft, lineStringsOfRight);
		
		//line 단위의 변화 여부에 대해 알아본다.
		ArrayList<CompState> lineStatesOfLeft = getLineState(lineStringsOfLeft, lcs);
		ArrayList<CompState> lineStatesOfRight = getLineState(lineStringsOfRight, lcs);

		//line 사이에 SPACE 채워넣는 것 만들기.
		putSpaceLine(lineStatesOfLeft, lineStatesOfRight, lineStringsOfLeft, lineStringsOfRight);
		
		//위에서 얻은 line 단위의 변화 여부를 이용하여 block으로 묶는다.
		ArrayList<Block> blocksOfLetf = getBlockArrayList(lineStatesOfLeft, lineStringsOfLeft);
		ArrayList<Block> blocksOfRight = getBlockArrayList(lineStatesOfRight, lineStringsOfRight);
		
		//한 쪽이라도 block이 생성이 안 되었으면 null을 return
		if(blocksOfLetf == null || blocksOfRight == null)
			return null;
		
		//pairBlocks를 만들기.
		pairBlocks = new PairBlocks();
		pairBlocks.setLeft(blocksOfLetf);
		pairBlocks.setRight(blocksOfRight);
		
		return pairBlocks;
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
	
	private ArrayList<CompState> getLineState(ArrayList<String> s, ArrayList<String> lcs) {
		//한 줄이 어디까지 인지를 확인하고, 그 줄이 변했는지를 판단한다.
		ArrayList<CompState> lineStates = new ArrayList<CompState>();
		int lcsIndex = 0;
		
		for (int i = 0; i < s.size(); i++) {
			//한 줄이 어디까지인지 확인한다.
			if(lcsIndex < lcs.size()) {
				if(s.get(i).equals(lcs.get(lcsIndex))) {
					lineStates.add(CompState.UNCHANGED);
					lcsIndex++;
					continue;
				}
			}
			lineStates.add(CompState.CHANGED);
		}
		
		return lineStates;
	}
		
	private void putSpaceLine(ArrayList<CompState> lineStatesOfLeft, ArrayList<CompState> lineStatesOfRight,
							  ArrayList<String> lineStringsOfLeft, ArrayList<String> lineStringsOfRight) {
		//각 줄을 비교하여 SPACE line을 적절히 채워 넣어준다.
		int leftIndex = 0, rightIndex = 0;
		
		//한 쪽이 ""일 때에 대한 예외처리 부분
		//""일 때를 처리. 한 쪽이 ""이면 다른 한 쪽이 ""이 아닌 이상, 전체가 changed인 1 block이 됨
		if(lineStatesOfLeft.size() == 0 && lineStatesOfRight.size() != 0) {
			for(int i = 0; i < lineStatesOfRight.size(); i++) {
				lineStatesOfLeft.add(i, CompState.SPACE);
				lineStringsOfLeft.add(i, "\n");
			}
			return;
		}
		
		if(lineStatesOfLeft.size() != 0 && lineStatesOfRight.size() == 0) {
			for(int i = 0 ; i < lineStatesOfLeft.size(); i++) {
				lineStatesOfRight.add(i, CompState.SPACE);
				lineStringsOfRight.add(i, "\n");
			}
			return;
		}
				
		//양 쪽 모두 index < size 여야지 BOF error가 나지 않는다.
		while (leftIndex < lineStatesOfLeft.size() && rightIndex < lineStatesOfRight.size()) {
			//양 쪽 모두 UNCHANGED이면 양쪽에 모두 넣어준다.
			if (lineStatesOfLeft.get(leftIndex) == CompState.UNCHANGED && lineStatesOfRight.get(rightIndex) == CompState.UNCHANGED) {
				leftIndex++;
				rightIndex++;
			}
			//왼쪽이 UNCHANGED가 나올 때까지(즉 지금 CHANGED인 동안), 여기에 대응하는 오른쪽에 SPACE line을 채워 넣어주고,
			//현재의 왼쪽 block을 왼쪽에 넣는다.
			while (leftIndex < lineStatesOfLeft.size() && lineStatesOfLeft.get(leftIndex) == CompState.CHANGED) {
				lineStatesOfRight.add(rightIndex, CompState.SPACE);
				lineStringsOfRight.add(rightIndex, "\n");
				leftIndex++;
				rightIndex++;
			}
			//오른쪽이 UNCHANGED가 나올 때까지(즉 지금 CHANGED인 동안), 여기에 대응하는 왼쪽에 SPACE block을 채워 넣어주고,
			//현재의 오른쪽 block을 오른쪽에 넣는다.
			while (rightIndex < lineStatesOfRight.size() && lineStatesOfRight.get(rightIndex) == CompState.CHANGED) {
				lineStatesOfLeft.add(leftIndex, CompState.SPACE);
				lineStringsOfLeft.add(leftIndex, "\n");
				leftIndex++;
				rightIndex++;
			}
		}
		
	}
	
	private ArrayList<Block> getBlockArrayList(
			ArrayList<CompState> lineStates, ArrayList<String> lineStrings) {
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
	
	private ArrayList<String> makeLCS(ArrayList<String> left, ArrayList<String> right) {
		//다음의 두 이차원 배열에서 각 길이에 +1 씩 있는 이유는 0 ~ StringLength 까지 사용하기 때문이다.(StringLenth -1 이 아니다)
		//table[i][j]는 a[i]와 b[j] 사이의 LCS의 길이를 의미한다.
		int[][] table = new int[left.size() + 1][right.size() + 1];
		//restore[i][j]는 backtracking을 위한 것으로, 어느 방향에서 이어져 왔는지를 의미한다.
		Direction[][] restore = new Direction[left.size() + 1][right.size() + 1];
		
		//initialization.
		initialize(table, restore, left.size(), right.size());
		
		//implementation.
		mainLoop(table, restore, left, right);
		
		return backtrack(table, restore, left, right);
	}
	
	private void initialize(int[][] table, Direction[][] restore, int leftLength, int rightLength) {
		//initialize table[x, 0] and table[0, y]
		//(0 <= x <= length of first string, 0 <= y <= length of second string).
		//"<"가 아니라 "<="를 사용하는 것이 중요하다.
		for (int i = 0; i <= leftLength; i++) {
			table[i][0] = 0;
			restore[i][0] = Direction.NONE;
		}

		for (int i = 0; i <= rightLength; i++) {
			table[0][i] = 0;
			restore[0][i] = Direction.NONE;
		}
	}

	private void mainLoop(int[][] table, Direction[][] restore, ArrayList<String> left, ArrayList<String> right) {
		//main loop for implementing LCS algorithm.
		//"<"가 아니라 "<="를 사용하는 것이 중요하다.
		for (int i = 1; i <= left.size(); i++) {
			for (int j = 1; j <= right.size(); j++) {
				//string is starting from 0, so need to use i - 1, j - 1
				if (left.get(i - 1).equals(right.get(j - 1))) {
					table[i][j] = table[i - 1][j - 1] + 1;
					restore[i][j] = Direction.UP_AND_LEFT;
				}
				else {
					if (table[i - 1][j] > table[i][j - 1]) {
						table[i][j] = table[i - 1][j];
						restore[i][j] = Direction.UP;
					}
					else if (table[i - 1][j] < table[i][j - 1]) {
						table[i][j] = table[i][j - 1];
						restore[i][j] = Direction.LEFT;
					}
					else {
						//LCS가 복수개일 때라도 그냥 하나만 구하기 때문에 짧은 string에서는 문제 발생 가능
						//만약 같은 길이라면 위는 무시하고 왼쪽을 따라감
						table[i][j] = table[i][j - 1];
						restore[i][j] = Direction.LEFT;
					}
				}
			}
		}
	}
	
	private ArrayList<String> backtrack(int[][] table, Direction[][] restore, ArrayList<String> left, ArrayList<String> right) {
		//restore과 table를 이용해서 backtracking 하여 LCS를 얻어낸다.
		int i = left.size(), j = right.size();
		ArrayList<String> lcs = new ArrayList<String>();

		while (restore[i][j] != Direction.NONE) {
			//left.get(i) == right.get(j)
			if (restore[i][j] == Direction.UP_AND_LEFT) {
				//string is starting from 0, so need to use i - 1, j - 1
				lcs.add(0, left.get(i - 1));
				i--;
				j--;
			}
			else {
				if (restore[i][j] == Direction.UP) {
					i--;
				}
				else if (restore[i][j] == Direction.LEFT) {
					j--;
				}
			}
		}

		return lcs;
	}
}