package model.diff;

import java.util.ArrayList;
import java.util.List;

import model.diff.block.Block;
import model.diff.block.CompState;

/**
 * Created by Sunghyeon on 11/25/2016.
 * CopyToRight 기능을 수행하는 command
 */

public class CopyToRightCommand implements Command{
	private List<Block> left, right;
	private int blockNum;
	private ArrayList<Block> beforeCopyLeftBlocks;
	private ArrayList<Block> beforeCopyRightBlocks;
	private boolean willRemoveToUndo;
	private int blockNumforLog;
	
	//합칠 block 번호 전달.
	public CopyToRightCommand(List<Block> left, List<Block> right, int blockNum) {
		this.left = left;
		this.right = right;
		this.blockNum = blockNum;
		beforeCopyLeftBlocks = new ArrayList<Block>();
		beforeCopyRightBlocks = new ArrayList<Block>();
		willRemoveToUndo = true;
		blockNumforLog = blockNum;
	}

	@Override
	public void execute() {
		//둘 중 하나라도 compare 결과를 갖고 있지 않을 때
		if(left == null || right == null)
			return;
		
		//잘못된 blockNum이 입력되었을 때
		if(blockNum < 0 || blockNum >= right.size())
			return;
		
		//unchanged 상황에서는 copyToRight가 실행이 되든 안 되든 결과는 동일하므로 아무 작업을 하지 않는다.
		if(right.get(blockNum).getState() == CompState.UNCHANGED)
			return;
		
		if(left.get(blockNum).getState() != CompState.SPACE) {
			//좌측의 blockNum번 block을 우측에 추가하고, 우측의 blockNum + 1번째 block을 삭제.
			String content = left.get(blockNum).getContent();
			Block rightNewBlock = new Block(CompState.UNCHANGED, content);
			
			right.add(blockNum, rightNewBlock);
			
			//undo를 제공하기 위해 기존의 block을 지우기 전에 저장
			beforeCopyRightBlocks.add(right.get(blockNum + 1));
			
			right.remove(blockNum + 1);
			
			//좌측에서도 SPACE 또는 CHANGED Block의 상태를 UNCHANGED 상태로 바꿔줘야 하므로 갈아끼운다.
			Block leftNewBlock = new Block(CompState.UNCHANGED, content);
			left.add(blockNum, leftNewBlock);
			
			//undo를 제공하기 위해 기존의 block을 지우기 전에 저장
			beforeCopyLeftBlocks.add(left.get(blockNum + 1));
			
			left.remove(blockNum + 1);
			
			int tmp = blockNum;
			updateBlocksChangedCase(left, beforeCopyLeftBlocks);
			blockNum = tmp;
			updateBlocksChangedCase(right, beforeCopyRightBlocks);
		}
		else {
			//undo를 제공하기 위해 기존의 block을 지우기 전에 저장
			beforeCopyLeftBlocks.add(left.get(blockNum));
			beforeCopyRightBlocks.add(right.get(blockNum));

			left.remove(blockNum);
			right.remove(blockNum);
			
			//remove만 하고 update가 안 되는 경우 undo할 때 block을 지우면 안 됨
			willRemoveToUndo = false;
			
			int tmp = blockNum;
			updateBlocksSpaceCase(left, beforeCopyLeftBlocks);
			blockNum = tmp;
			updateBlocksSpaceCase(right, beforeCopyRightBlocks);
		}
	}

	@Override
	public void undo() {
		//변화가 없음
		if(beforeCopyLeftBlocks.size() == 0 || beforeCopyRightBlocks.size() == 0)
			return;
		
		if(willRemoveToUndo) {
			left.remove(blockNum);
			right.remove(blockNum);
		}

		for(int i = beforeCopyLeftBlocks.size() - 1 ; i >= 0 ; i--)
			left.add(blockNum, beforeCopyLeftBlocks.get(i));
		for(int i = beforeCopyRightBlocks.size() - 1 ; i >= 0 ; i--)
			right.add(blockNum, beforeCopyRightBlocks.get(i));
	}

	@Override
	public String getLog() {
		return "copy to Right : " + String.valueOf(blockNumforLog) + "th block\n";
	}
	
	private void updateBlocksChangedCase(List<Block> blocks, List<Block> beforeBlocks) {
		//이후 block과 병합
		if(blockNum < blocks.size() - 1 && blocks.get(blockNum).getState() == blocks.get(blockNum + 1).getState()) {
			Block block = new Block(CompState.UNCHANGED, blocks.get(blockNum).getContent() + blocks.get(blockNum + 1).getContent());
			beforeBlocks.add(blocks.get(blockNum + 1));
			blocks.remove(blockNum + 1);
			blocks.remove(blockNum);
			blocks.add(blockNum, block);
		}
		//이전 block과 병합
		if(blockNum > 0 && blocks.get(blockNum - 1).getState() == blocks.get(blockNum).getState()) {
			Block block = new Block(CompState.UNCHANGED, blocks.get(blockNum - 1).getContent() + blocks.get(blockNum).getContent());
			beforeBlocks.add(0, blocks.get(blockNum - 1));
			blocks.remove(blockNum);
			blocks.remove(blockNum - 1);
			blockNum--;
			blocks.add(blockNum, block);
		}
	}
	
	private void updateBlocksSpaceCase(List<Block> blocks, List<Block> beforeBlocks) {
		if(blockNum > 0 && blockNum <= blocks.size() - 1 && blocks.get(blockNum - 1).getState() == blocks.get(blockNum).getState()) {
			//병합된 경우 지울 block이 발생
			willRemoveToUndo = true;
			
			Block block = new Block(CompState.UNCHANGED, blocks.get(blockNum - 1).getContent() + blocks.get(blockNum).getContent());
			beforeBlocks.add(0, blocks.get(blockNum - 1));
			beforeBlocks.add(blocks.get(blockNum));
			blocks.remove(blockNum);
			blocks.remove(blockNum - 1);
			blockNum--;
			blocks.add(blockNum, block);
		}
	}
}
