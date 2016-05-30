package model.diff;

import java.util.List;

import model.diff.block.Block;
import model.diff.block.CompState;

/**
 * Created by Seonghyeon on 5/26/2016.
 *
 * copyToLeft, copyToRight를 제공해주는 interface를 implement한 Class
 */

public class Copier implements CopierInterface{
	@Override
	public void copyToLeft(List<Block> left, List<Block> right, int blockNum) {
		//둘 중 하나라도 compare 결과를 갖고 있지 않을 때
		if(left == null || right == null)
			return;
		
		//잘못된 blockNum이 입력되었을 때
		if(blockNum < 0 || blockNum >= left.size())
			return;
		
		//unchanged 상황에서는 copyToLeft가 실행이 되든 안 되든 결과는 동일하므로 아무 작업을 하지 않는다.
		if(left.get(blockNum).getState() == CompState.UNCHANGED)
			return;
		
		if(right.get(blockNum).getState() != CompState.SPACE) {
			//우측의 blockNum번 block을 좌측에 추가하고, 좌측의 blockNum + 1번째 block을 삭제.
			String content = right.get(blockNum).getContent();
			Block leftNewBlock = new Block(CompState.UNCHANGED, content);
			left.add(blockNum, leftNewBlock);
			left.remove(blockNum + 1);
			
			//우측에서도 SPACE 또는 CHANGED Block의 상태를 UNCHANGED 상태로 바꿔줘야 하므로 갈아끼운다.
			Block rightNewBlock = new Block(CompState.UNCHANGED, content);
			right.add(blockNum, rightNewBlock);
			right.remove(blockNum + 1);
		}
		else {
			left.remove(blockNum);
			right.remove(blockNum);
		}
		updateBlocks(left);
		updateBlocks(right);
	}

	@Override
	public void copyToRight(List<Block> left, List<Block> right, int blockNum) {
		copyToLeft(right, left, blockNum);
	}

	private void updateBlocks(List<Block> blocks) {
		//size() - 1 까지인 이유는 i와 i+1 번째를 비교하기 때문
		for(int i = 0 ; i < blocks.size() - 1; i++) {
			if(blocks.get(i).getState() == CompState.UNCHANGED && blocks.get(i + 1).getState() == CompState.UNCHANGED) {
				Block block = new Block(CompState.UNCHANGED, blocks.get(i).getContent() + blocks.get(i + 1).getContent());
				blocks.remove(i + 1);
				blocks.remove(i);
				blocks.add(i, block);
			}
		}
	}
}
