package model;

import java.util.ArrayList;

/**
 * Created by Seonghyeon on 5/23/2016.
 * PairBlocks를 read only로 사용하기 위한 interface
 */

public interface PairBlocksReadOnlyInterface {
	/**
	 * left를 read only로 get한다
     */	
	public ArrayList<? extends BlockReadInterface> getLeftReadOnly();
	/**
	 * right를 read only로 get한다
     */
	public ArrayList<? extends BlockReadInterface> getRightOnly();
}
