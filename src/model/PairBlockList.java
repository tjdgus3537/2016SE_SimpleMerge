package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seonghyeon on 5/15/2016.
 * 2개의 ArrayList<Block>을 묶어둔 단위
 * Diff Class에서 leftString과 rightString이 input으로 주어지고 Compare 했을 때 return되는 data의 type
 */

public class PairBlockList {
	private List<BlockInterface> left;
	private List<BlockInterface> right;

	public PairBlockList(){
		left = new ArrayList<>();
		right = new ArrayList<>();
	}
	public PairBlockList(List<BlockInterface> left, List<BlockInterface> right) {
		this.left = left;
		this.right = right;
	}

	public List<BlockInterface> getLeft() {
		return left;
	}

	public List<BlockInterface> getRight() {
		return right;
	}
}
