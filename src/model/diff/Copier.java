package model.diff;

import java.util.List;

import model.diff.block.Block;
import model.diff.block.State;

/**
 * Created by Seonghyeon on 5/26/2016.
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
		if(left.get(blockNum).getState() == State.UNCHANGED)
			return;
		
		//우측의 blockNum번 block을 좌측에 추가하고, 좌측의 blockNum + 1번째 block을 삭제.
		Block block = new Block(State.UNCHANGED, right.get(blockNum).getContent());
		left.add(blockNum, block);
		left.remove(blockNum + 1);
		
		//우측에서도 SPACE 또는 CHANGED Block의 상태를 UNCHANGED 상태로 바꿔줘야 하므로 갈아끼운다.
		right.add(blockNum, block);
		right.remove(blockNum + 1);
	}

	@Override
	public void copyToRight(List<Block> left, List<Block> right, int blockNum) {
		copyToLeft(right, left, blockNum);
	}

}
