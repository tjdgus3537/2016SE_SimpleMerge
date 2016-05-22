package model;

import java.util.ArrayList;

/**
 * Created by Seonghyeon on 5/23/2016.
 * PairBlocks를 read only로 사용하기 위한 interface
 */

public interface PairBlocksReadOnlyInterface {
	//내가 생각해도 이 interface는 너무 급조된 것이라 문제점이 있음.
	//ArrayList<Block>을 그대로 받아와 버려서 사실상 변조가 가능함...
	/**
	 * left를 get한다
     */	
	public ArrayList<Block> getLeft();
	/**
	 * right를 get한다
     */
	public ArrayList<Block> getRight();
}
