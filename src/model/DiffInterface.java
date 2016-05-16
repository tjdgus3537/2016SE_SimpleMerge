package model;

import java.util.ArrayList;

/**
 * Created by Seonghyeon on 5/15/2016.
 * Diff에 대한 interface
 * 
 * 다음과 같은 기능을 제공한다.
 * compare - 두 개의 string을 input으로 받아서 비교 결과를 PairBlockArrayList 형태로 return.
 * 
 * blockCopyToLeft - blockNumber에 해당하는 block을 right --> left 방향으로 
 * copy한 결과를 string으로 return.
 * 
 * blockCopyToRight - blockNumber에 해당하는 block을 left --> right 방향으로 
 * copy한 결과를 string으로 return.
 * 
 * ApplySpaceBlockToEnter - string과 block의 ArrayList인 blocks가 주어지면, blocks에
 * 있는 space block 부분들을 enter(개행)으로 변환하여 원래 string의 적합한 위치에 적용하여 그 string을
 * return --> 일단 이 기능은 다시 의논해 봐야 할 듯.
 */

public interface DiffInterface {
	public PairBlockArrayList compare(String left, String right);
	
	public String blockCopyToLeft(String left, String right, int blockNumber, 
			PairBlockArrayList pairBlockArrayList);
	
	public String blockCopyToRight(String left, String right, int blockNumber, 
			PairBlockArrayList pairBlockArrayList);

	public String ApplySpaceBlockToEnter(String s, ArrayList<Block> blocks);
}
