package model.diff;

import model.diff.block.PairBlocks;

/**
 * Created by Seonghyeon on 5/21/2016.
 * compare 기능을 제공하는 concreteClass.
 */

public interface DiffInterface {
	/**
	 * 두 개의 string을 input으로 받아서 비교 결과를 PairBlocks 형태로 return.
	 * @param left 비교할 문자열
	 * @param right 비교할 문자열
     * @return 비교 결과
     */
	PairBlocks compare(String left, String right);
}
