package model.diff;

import java.util.List;

import model.diff.block.Block;

/**
 * Created by Seonghyeon on 5/26/2016.
 * copyToLeft, copyToRight를 제공해주는 interface
 */

public interface CopierInterface {
    /**
     * blockNum에 대응하는 오른쪽 block을 왼쪽으로 copy하고, 필요하면 block을 통합함.
     * @param compare의 결과 중 left
     */
	public void copyToLeft(List<Block> left, List<Block> right, int blockNum);
	/**
     * blockNum에 대응하는 왼쪽 block을 오른쪽으로 copy하고, 필요하면 block을 통합함.
     * @param compare의 결과 중 right
     */
	public void copyToRight(List<Block> left, List<Block> right, int blockNum);
}
