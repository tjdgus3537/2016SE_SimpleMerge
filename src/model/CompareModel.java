package model;

import java.util.ArrayList;

/**
 * Created by Seonghyeon on 5/22/2016.
 * compare mode에서 사용되는 data와 관련된 model
 */

public class CompareModel implements CompareModelInterface{
	private static CompareModel compareModel = new CompareModel();
	private ArrayList<? extends BlockReadInterface> left;
	private ArrayList<? extends BlockReadInterface> right;
	
	private CompareModel() {
	}

	public static CompareModel getInstance() {
		return compareModel;
	}
	
	public void setLeft(ArrayList<BlockReadInterface> blocks) {
		left = blocks;
	}

	public void setRight(ArrayList<BlockReadInterface> blocks) {
		right = blocks;
	}

	public ArrayList<? extends BlockReadInterface> getLeft() {
		return left;
	}

	public ArrayList<? extends BlockReadInterface> getRight() {
		return right;
	}
	
	
}
