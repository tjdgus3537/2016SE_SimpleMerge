package model;

/**
 * Created by Seonghyeon on 5/15/2016.
 * LCS를 사용하기 위한 interface
 * input : 2개의 String
 * output : 2개의 String을 이용하여 LCS 알고리즘을 적용한 결과
 */

public interface LCSInterface {
	String getLCS(String firstString, String secondString); 
}
