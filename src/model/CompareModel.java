package model;

import java.util.ArrayList;

import sun.security.jca.GetInstance;

/**
 * Created by Seonghyeon on 5/22/2016.
 * compare mode에서 사용되는 data와 관련된 model
 */

public class CompareModel implements CompareModelInterface{
	private static CompareModel compareModel = new CompareModel();
	private ArrayList<BlockReadInterface> left;
	private ArrayList<BlockReadInterface> right;
	
	private CompareModel() {
	}

	public static CompareModel getInstance() {
		return compareModel;
	}
	
	public void setLeft(ArrayList<BlockReadInterface> blocks) {
		left = blocks;
	}

	@Override
	public void setRight(ArrayList<BlockReadInterface> blocks) {
		right = blocks;
	}

	@Override
	public ArrayList<BlockReadInterface> getLeft() {
		return left;
	}

	@Override
	public ArrayList<BlockReadInterface> getRight() {
		return right;
	}
	
	
}
