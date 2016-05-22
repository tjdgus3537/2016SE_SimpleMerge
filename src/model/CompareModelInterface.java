package model;

import java.util.ArrayList;

/**
 * Created by Seonghyeon on 5/23/2016.
 * compare mode에서 사용되는 data와 관련된 model에 대한 interface
 */

public interface CompareModelInterface {
	public void setLeft(ArrayList<BlockReadInterface> blocks);
	/**
	 * right의 content를 set한다
     */		
	public void setRight(ArrayList<BlockReadInterface> blocks);
	/**
	 * left를 readOnly로 얻는다.
	 * @return readOnly 상태의 left entity
     */	
	public BlockReadInterface getLeft();
	/**
	 * right를 readOnly로 얻는다.
	 * @return readOnly 상태의 right entity
     */		
	public BlockReadInterface getRight();
}
