package model;

import java.util.ArrayList;

/**
 * Created by Seonghyeon on 5/23/2016.
 * compare mode에서 사용되는 data와 관련된 model에 대한 interface
 */

public interface CompareModelInterface {
	/**
	 * left를 set한다
     */	
	public void setLeft(ArrayList<BlockReadInterface> blocks);
	/**
	 * right를 set한다
     */		
	public void setRight(ArrayList<BlockReadInterface> blocks);
	/**
	 * left를 얻는다.
	 * @return left
     */	
	public ArrayList<BlockReadInterface> getLeft();
	/**
	 * right를 얻는다.
	 * @return right
     */		
	public ArrayList<BlockReadInterface> getRight();
}
