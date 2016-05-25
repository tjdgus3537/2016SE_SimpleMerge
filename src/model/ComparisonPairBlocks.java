package model;

import java.util.List;

/**
 * Created by Seonghyeon on 5/15/2016.
 * 2개의 List<Block>을 묶어둔 단위
 * Diff Class에서 leftString과 rightString이 input으로 주어지고 Compare 했을 때 return되는 data의 type
 */

public class ComparisonPairBlocks<T extends List<? extends Block>> {
    private T left;
    private T right;

    public ComparisonPairBlocks(T left, T right) {
        setLeft(left);
        setRight(right);
    }

    public void setLeft(T left) {
        this.left = left;
    }

    public void setRight(T right) {
        this.right = right;
    }

    public T getLeft() {
        return left;
    }

    public T getRight() {
        return right;
    }

    public int size() {
        return Integer.min(left.size(), right.size());
    }
}