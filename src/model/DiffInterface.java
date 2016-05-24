package model;

import java.util.ArrayList;

/**
 * Created by Seonghyeon on 5/21/2016.
 * compare 기능과 copyToLeft, copyToRight 기능을 제공하는 Interface.
 */

public interface DiffInterface {
	/**
	 * 두 개의 string을 input으로 받아서 비교 결과를 PairBlockArrayList 형태로 return.
	 * compare 함수를 실행하면 내부적으로 member variable값이 변경된다.
	 * @param left 비교할 문자열
	 * @param right 비교할 문자열
     * @return 비교 결과
     */
	public PairBlocks compare(String left, String right);
	
	/**
	 * 특정 줄에 해당하는 우측의 Block을 좌측으로 copy한 결과를 Block들로 return
	 * @param left 비교할 문자열
	 * @param right 비교할 문자열
	 * @param blockNum copy할 부분의 block 번호
     * @return copy를 적용하고 난 후의 좌측의 ArrayList<Block>
     */	

	public ArrayList<Block> copyToLeft(String left, String right, int blockNum);
	
	/**
	 * 특정 줄에 해당하는 우측의 Block을 좌측으로 copy한 결과를 Block들로 return
	 * @param left 비교할 문자열
	 * @param right 비교할 문자열
	 * @param blockNum copy할 부분의 block 번호
     * @return copy를 적용하고 난 후의 좌측의 ArrayList<Block>
     */	
	public ArrayList<Block> copyToRight(String left, String right, int blockNum);
}
