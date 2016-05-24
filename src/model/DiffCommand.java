package model;

import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by Seonghyeon on 5/22/2016.
 * Diff를 수행하는 곳과 Diff 호출하는 곳을 분리해주는 Class
 * Controller에게 호출 당하면 Model에서 data를 가져와서 기능을 수행하고 결과를 Model에 반영한다.
 */

public class DiffCommand implements DiffCommandInterface{
	private DiffInterface diff;
	
	public DiffCommand() {
		diff = new Diff();
	}

	@Override
	public void compare(ComparisonFile left, ComparisonFile right) {
		PairBlocks pairBlocks;
		//diff의 compare를 호출하여 결과를 얻는다.
		pairBlocks = diff.compare(left.getContentToString(), right.getContentToString());
		//새로운 결과로 compareModel을 update.
		ObservableList<BlockReadInterface> leftContent = left.getContent();
		ObservableList<BlockReadInterface> rightContent = right.getContent();
		leftContent.setAll(pairBlocks.getLeft());
		rightContent.setAll(pairBlocks.getRight());
	}

	@Override
	public void copyToLeft(ComparisonFile left, ComparisonFile right, int blockNum) {
		//diff의 copyToLeft를 호출하여 얻은 결과를 compareModel의 left에 update.
		left.getContent().setAll((diff.copyToLeft(left.getContentToString(), right.getContentToString(), blockNum)));
	}

	@Override
	public void copyToRight(ComparisonFile left, ComparisonFile right, int blockNum) {
		//diff의 copyToRight를 호출하여 얻은 결과를 compareModel의 right에 update.
		right.getContent().setAll(diff.copyToRight(left.getContentToString(), right.getContentToString(), blockNum));
	}
	
	private String changeBlocksToContent(ArrayList<? extends BlockReadInterface> blocks) {
		String s = "";
		
		for(int i = 0 ; i < blocks.size(); i++) {
			if(blocks.get(i).getState() == State.SPACE)
				continue;
			
			s += blocks.get(i).getContent();
		}
		
		return s;
	}
}
