package model;

/**
 * Created by Seonghyeon on 5/15/2016.
 * 두 string에 대해 LCS algorithm을 적용한 결과를 준다.
 * input : two string
 * output : string들을 이용해서 만든 lcs 결과값
 */

public interface LCSInterface {
	public String getLCS(String firstString, String secondString);
}
