package model;

/**
 * Created by Seonghyeon on 5/21/2016.
 * LCS를 사용할 때 필요한 interface
 */

public interface LCSInterface {
	/**
 	 * @param left 비교할 문자열
	 * @param right 비교할 문자열
     * @return 두 문자열에 대한 lcs 값
     */
	public String getLCS(String left, String right);
}
