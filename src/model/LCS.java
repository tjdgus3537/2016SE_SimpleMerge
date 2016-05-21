package model;

/**
 * Created by Seonghyeon on 5/15/2016.
 * LCS interface에 대한 구현
 * Longest Common Subsequence(LCS) algorithm을 java로 구현
 * LCS algorithm을 이해하고 싶으면 https://en.wikipedia.org/wiki/Longest_common_subsequence_problem 을 참고 
 * time complexity와 space complexity 모두 O(mn) (단, m은 첫 번째 string의 길이, n은 두 번째 string의 길이)
 */

public class LCS implements LCSInterface{
	enum Direction {
		NONE, UP, LEFT, UP_AND_LEFT;
	}
	
	public String getLCS(String left, String right) {
		//만약 둘 중 하나라도 null이면 null을 return
		if(left == null || right == null)
			return null;
		
		return makeLCS(left, right);
	}
	
	private String makeLCS(String left, String right) {
		//다음의 두 이차원 배열에서 각 길이에 +1 씩 있는 이유는 0 ~ StringLength 까지 사용하기 때문이다.(StringLenth -1 이 아니다)
		//table[i][j]는 a[i]와 b[j] 사이의 LCS의 길이를 의미한다.
		int[][] table = new int[left.length() + 1][right.length() + 1];
		//restore[i][j]는 backtracking을 위한 것으로, 어느 방향에서 이어져 왔는지를 의미한다.
		Direction[][] restore = new Direction[left.length() + 1][right.length() + 1];
		
		//initialization.
		initialize(table, restore, left.length(), right.length());
		
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

	private void mainLoop(int[][] table, Direction[][] restore, String left, String right) {
		//main loop for implementing LCS algorithm.
		//"<"가 아니라 "<="를 사용하는 것이 중요하다.
		for (int i = 1; i <= left.length(); i++) {
			for (int j = 1; j <= right.length(); j++) {
				//string is starting from 0, so need to use i - 1, j - 1
				if (left.charAt(i - 1) == right.charAt(j - 1)) {
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
	
	private String backtrack(int[][] table, Direction[][] restore, String left, String right) {
		//restore과 table를 이용해서 backtracking 하여 LCS를 얻어낸다.
		int i = left.length(), j = right.length();
		int size = table[left.length()][right.length()];
		int index = size - 1;
		char[] lcs_array = new char[size];

		while (restore[i][j] != Direction.NONE) {
			//left.charAt(i) == right.charAt(j)
			if (restore[i][j] == Direction.UP_AND_LEFT) {
				//string is starting from 0, so need to use i - 1, j - 1
				lcs_array[index] = left.charAt(i - 1);
				index--;
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

		return new String(lcs_array);
	}
	
	
	
	
}
