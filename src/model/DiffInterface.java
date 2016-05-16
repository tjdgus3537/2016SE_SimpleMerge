package model;

import java.util.ArrayList;

/**
 * Created by Seonghyeon on 5/15/2016.
 * Diff에 대한 interface
 */

public interface DiffInterface {
	/**
	 * 두 개의 string을 input으로 받아서 비교 결과를 PairBlockArrayList 형태로 return.
	 * @param left 비교할 문자열
	 * @param right 비교할 문자열
     * @return 비교 결과
     */
	public PairBlockArrayList compare(String left, String right);

	/**
	 * blockNumber에 해당하는 block을 right --> left 방향으로 copy한 결과를 string으로 return.
	 * @param left 복사본
	 * @param right 원본
	 * @param blockNumber 복사할 블록 번호
	 * @param pairBlockArrayList 비교 결과를 저장한 리스트
     * @return
     */
	public String blockCopyToLeft(String left, String right, int blockNumber, 
			PairBlockArrayList pairBlockArrayList);
	// TODO copyBlockToLeft로 하는 게 나을 듯

	/**
	 * blockNumber에 해당하는 block을 left --> right 방향으로 copy한 결과를 string으로 return.
	 * @param left 원본
	 * @param right 복사본
	 * @param blockNumber 복사할 블록 번호
	 * @param pairBlockArrayList 비교 결과를 저장한 리스트
     * @return
     */
	public String blockCopyToRight(String left, String right, int blockNumber, 
			PairBlockArrayList pairBlockArrayList);

	/**
	 * string과 block의 ArrayList인 blocks가 주어지면, blocks에 있는 space block 부분들을
	 * enter(개행)으로 변환하여 원래 string의 적합한 위치에 적용하여 그 string을 return
	 * --> 일단 이 기능은 다시 의논해 봐야 할 듯.
	 * @param s
	 * @param blocks
     * @return
     */
	public String ApplySpaceBlockToEnter(String s, ArrayList<Block> blocks);
	// TODO 뭘 하고싶은 지는 알겠음. 만나서 생각해보죠...
}
