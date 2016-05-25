package model.diff.block;

import javafx.collections.ObservableList;
import model.diff.block.Block;

/**
 * Created by Seonghyeon on 5/15/2016.
 * 2개의 List<Block>을 묶어둔 단위
 * Diff Class에서 leftString과 rightString이 input으로 주어지고 Compare 했을 때 return되는 data의 type
 */

public class ComparisonPairBlocks {
    private ObservableList<Block> left;
    private ObservableList<Block> right;

    public ComparisonPairBlocks(ObservableList<Block> left, ObservableList<Block> right) {
        setLeft(left);
        setRight(right);
    }

    public void setLeft(ObservableList<Block> left) {
        this.left = left;
    }

    public void setRight(ObservableList<Block> right) {
        this.right = right;
    }

    public ObservableList<Block> getLeft() {
        return left;
    }

    public ObservableList<Block> getRight() {
        return right;
    }

    public int size() {
        return Integer.min(left.size(), right.size());
    }
}