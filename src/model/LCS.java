package model;

/**
 * Created by Seonghyeon on 5/15/2016.
 * LCS interface에 대한 구현
 * Longest Common Subsequence(LCS) algorithm을 java로 구현
 * LCS algorithm을 이해하고 싶으면 https://en.wikipedia.org/wiki/Longest_common_subsequence_problem 을 참고 
 */

public class LCS implements LCSInterface {
	private final int NONE = 0;
	private final int UP = 1;
	private final int LEFT = 2;
	private final int UP_AND_LEFT = 3;
	
	@Override
	public String getLCS(String firstString, String secondString) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private String makeLCS(String firstString, String secondString) {
		//다음의 두 이차원 배열에서 각 길이에 +1 씩 있는 이유는 0 ~ StringLength 까지 사용하기 때문이다.(StringLenth -1 이 아니다)
		//table[i][j]는 a[i]와 b[j] 사이의 LCS의 길이를 의미한다.
		int[][] table = new int[firstString.length() + 1][secondString.length() + 1];
		//restore[i][j]는 backtracking을 위한 것으로, 어느 방향에서 이어져 왔는지를 의미한다.
		int[][] restore = new int[firstString.length() + 1][secondString.length() + 1];
		
		//initialization.
		initialize(table, restore, firstString.length(), secondString.length());
		
		//implementation.

		
		return null;
	}
	
	private void initialize(int[][] table, int[][] restore, int firstStringLength, int secondStringLength) {
		//initialize table[x, 0] and table[0, y]
		//(0 <= x <= length of first string, 0 <= y <= length of second string).
		//"<"가 아니라 "<="를 사용하는 것이 중요하다.
		for (int i = 0; i <= firstStringLength; i++) {
			table[i][0] = 0;
			restore[i][0] = NONE;
		}

		for (int i = 0; i <= secondStringLength; i++) {
			table[0][i] = 0;
			restore[0][i] = NONE;
		}
	}

	private void mainLoop(int[][] table, int[][] restore, String firstString, String secondString) {
		//main loop for implementing LCS algorithm.
		//"<"가 아니라 "<="를 사용하는 것이 중요하다.
		for (int i = 1; i <= firstString.length(); i++) {
			for (int j = 1; j <= secondString.length(); j++) {
				//string is starting from 0, so need to use i - 1, j - 1
				if (firstString.charAt(i - 1) == secondString.charAt(j - 1)) {
					table[i][j] = table[i - 1][j - 1] + 1;
					restore[i][j] = UP_AND_LEFT;
				}
				else {
					if (table[i - 1][j] > table[i][j - 1]) {
						table[i][j] = table[i - 1][j];
						restore[i][j] = UP;
					}
					else if (table[i - 1][j] < table[i][j - 1]) {
						table[i][j] = table[i][j - 1];
						restore[i][j] = LEFT;
					}
				}
			}
		}
	}
}
