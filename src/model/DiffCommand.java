package model;

import java.util.ArrayList;

/**
 * Created by Seonghyeon on 5/22/2016.
 * Diff를 수행하는 곳과 Diff 호출하는 곳을 분리해주는 Class
 * Controller에게 호출 당하면 Model에서 data를 가져와서 기능을 수행하고 결과를 Model에 반영한다.
 */

public class DiffCommand implements DiffCommandInterface{
	private TextModelInterface textModel;
	private CompareModel compareModel;
	private DiffInterface diff;
	
	public DiffCommand() {
		textModel = TextModel.getInstance();
		compareModel = CompareModel.getInstance();
		diff = new Diff();
	}

	public void compare() {
		PairBlocksReadOnlyInterface pairBlocks;
		
		//diff의 compare를 호출하여 결과를 얻는다.
		pairBlocks = diff.compare(textModel.getLeftReadOnly().getContent(), textModel.getRightReadOnly().getContent());
		//새로운 결과로 compareModel을 update.
		compareModel.setLeft(pairBlocks.getLeftReadOnly());
		compareModel.setRight(pairBlocks.getRightReadOnly());
	}

	public void copyToLeft(int blockNum) {
		//diff의 copyToLeft를 호출하여 얻은 결과를 compareModel의 left에 update.
		compareModel.setLeft(diff.copyToLeft(textModel.getLeftReadOnly().getContent(), 
				textModel.getRightReadOnly().getContent(), blockNum));
		//compareModel의 left에 update된 값을 바탕으로 textModel의 left를 update.
		textModel.setLeftContent(changeBlocksToContent(compareModel.getLeft()));
	}

	public void copyToRight(int blockNum) {
		//diff의 copyToRight를 호출하여 얻은 결과를 compareModel의 right에 update.
		compareModel.setRight(diff.copyToRight(textModel.getLeftReadOnly().getContent(), 
				textModel.getRightReadOnly().getContent(), blockNum));
		//compareModel의 right에 update된 값을 바탕으로 textModel의 right를 update.
		textModel.setRightContent(changeBlocksToContent(compareModel.getRight()));
	}
	
	private String changeBlocksToContent(ArrayList<? extends BlockReadInterface> blocks) {
		String s = "";
		
		for(int i = 0 ; i < blocks.size(); i++) {
			if(blocks.get(i).isSpace())
				continue;
			
			s += blocks.get(i).getContent();
		}
		
		return s;
	}
}
