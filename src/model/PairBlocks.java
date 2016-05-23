package model;

import java.util.ArrayList;

/**
 * Created by Seonghyeon on 5/15/2016.
 * 2개의 ArrayList<Block>을 묶어둔 단위
 * Diff Class에서 leftString과 rightString이 input으로 주어지고 Compare 했을 때 return되는 data의 type
 */

public class PairBlocks implements PairBlocksReadOnlyInterface{
	private ArrayList<Block> left;
	private ArrayList<Block> right;
	
	public PairBlocks() {
		left = new ArrayList<Block>();
		right = new ArrayList<Block>();
	}
	
	public PairBlocks(ArrayList<Block> left, ArrayList<Block> right) {
		setLeft(left);
		setRight(right);
	}
	
	public void setLeft(ArrayList<Block> left) {
		this.left = left;
	}
	
	public void setRight(ArrayList<Block> right) {
		this.right = right;
	}
	
	public ArrayList<Block> getLeft() {
		return left;
	}
	
	public ArrayList<Block> getRight() {
		return right;
	}
	
	public void addLeft(Block b) {
		left.add(b);
	}
	
	public void addRight(Block b) {
		right.add(b);
	}
	
	public ArrayList<? extends BlockInterface> getLeftReadOnly() {
		return left;
	}
	
	public ArrayList<? extends BlockInterface> getRightReadOnly() {
		return right;
	}
}
