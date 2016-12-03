import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.diff.CommandClient;
import model.diff.block.Block;
import model.diff.block.CompState;
import model.diff.block.PairBlocks;

/**
 * Created by Seonghyeon on 6/1/2016.
 * CommandClient에 관련한 테스트
 */

public class CommandClientTest {
	private CommandClient commandClient;
	PairBlocks blocks;
	
	//helper method
	private String blockToString(Block block) {
		String s = "";
		
		s += block.getContent();

		return s;
	}

	//helper method
	private String pairBlocksToString(PairBlocks pairBlocks) {
		String s = "";
		
		for(int i = 0 ; i < pairBlocks.getLeft().size(); i++)
			s += blockToString(pairBlocks.getLeft().get(i));
		
		s += " ";
		
		for(int i = 0 ; i < pairBlocks.getRight().size(); i++)
			s += blockToString(pairBlocks.getRight().get(i));
		
		return s;
	}
	
	//helper method
	private Block makeChangedBlock(String content) {
		return new Block(CompState.CHANGED, content);
	}
	
	private Block makeUnchangedBlock(String content) {
		return new Block(CompState.UNCHANGED, content);
	}
	
	private Block makeSpaceBlock(String content) {
		return new Block(CompState.SPACE, content);
	}
	
	@Before
	public void setUp() throws Exception {
		commandClient = new CommandClient();
		blocks = new PairBlocks(new ArrayList<Block>(), new ArrayList<Block>());
	}

		
	//정상적인 형태의 임의의 case
	@Test
	public void testCopyToLeftNormalCase1() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		
		commandClient.copyToLeft(blocks.getLeft(), blocks.getRight(), 0);
		String s = pairBlocksToString(blocks);
		
		assertEquals("aabc\n\n\n aabc\nx\ny\n", s);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testCopyToLeftNormalCase2() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		
		commandClient.copyToLeft(blocks.getLeft(), blocks.getRight(), 2);
		String s = pairBlocksToString(blocks);
		
		assertEquals("d\naabc\nx\ny\n \naabc\nx\ny\n", s);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testCopyToLeftNormalCase3() {
		blocks.getLeft().add(makeSpaceBlock("\n\n\n"));
		
		blocks.getRight().add(makeChangedBlock("aabc\nx\ny\n"));
		
		commandClient.copyToLeft(blocks.getLeft(), blocks.getRight(), 0);
		String s = pairBlocksToString(blocks);
		
		assertEquals("aabc\nx\ny\n aabc\nx\ny\n", s);
	}
	
	//unchanged Block을 copy 시도
	@Test
	public void testCopyToLeftAbnormalCase1() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		
		String s_before = pairBlocksToString(blocks);
		commandClient.copyToLeft(blocks.getLeft(), blocks.getRight(), 1);
		String s_after = pairBlocksToString(blocks);
		
		assertEquals(s_before, s_after);
	}
	
	//사이즈 보다 큰 blockNum을 입력으로 받았을 때
	@Test
	public void testCopyToLeftAbnormalCase2() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		
		String s_before = pairBlocksToString(blocks);
		commandClient.copyToLeft(blocks.getLeft(), blocks.getRight(), 10);
		String s_after = pairBlocksToString(blocks);
		
		assertEquals(s_before, s_after);
	}
	
	//blockNum으로 음수를 입력 받았을 때
	@Test
	public void testCopyToLeftAbnormalCase3() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		
		String s_before = pairBlocksToString(blocks);
		commandClient.copyToLeft(blocks.getLeft(), blocks.getRight(), -1);
		String s_after = pairBlocksToString(blocks);
		
		assertEquals(s_before, s_after);
	}
	
	//두 String이 모두 공스트링("")일 때
	@Test
	public void testCopyToLeftAbnormalCase4() {		
		String s_before = pairBlocksToString(blocks);
		commandClient.copyToLeft(blocks.getLeft(), blocks.getRight(), 0);
		String s_after = pairBlocksToString(blocks);
		
		assertEquals(s_before, s_after);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testCopyToRightNormalCase1() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		
		commandClient.copyToRight(blocks.getLeft(), blocks.getRight(), 0);
		String s = pairBlocksToString(blocks);
		
		assertEquals("d\naabc\n\n\n d\naabc\nx\ny\n", s);
	}

	//정상적인 형태의 임의의 case
	@Test
	public void testCopyToRightNormalCase2() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		
		commandClient.copyToRight(blocks.getLeft(), blocks.getRight(), 2);
		String s = pairBlocksToString(blocks);
		
		assertEquals("d\naabc\n \naabc\n", s);
	}

	//정상적인 형태의 임의의 case
	@Test
	public void testCopyToRightNormalCase3() {
		blocks.getLeft().add(makeSpaceBlock("\n\n\n"));
		
		blocks.getRight().add(makeChangedBlock("aabc\nx\ny\n"));

		commandClient.copyToRight(blocks.getLeft(), blocks.getRight(), 0);
		String s = pairBlocksToString(blocks);
		
		assertEquals(" ", s);
	}
	
	//unchanged Block을 copy 시도
	@Test
	public void testCopyToRightAbnormalCase1() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		
		String s_before = pairBlocksToString(blocks);
		commandClient.copyToRight(blocks.getLeft(), blocks.getRight(), 1);
		String s_after = pairBlocksToString(blocks);
		
		assertEquals(s_before, s_after);
	}
	
	//사이즈 보다 큰 blockNum을 입력으로 받았을 때
	@Test
	public void testCopyToRightAbnormalCase2() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		
		String s_before = pairBlocksToString(blocks);
		commandClient.copyToRight(blocks.getLeft(), blocks.getRight(), 10);
		String s_after = pairBlocksToString(blocks);
		
		assertEquals(s_before, s_after);
	}
	
	//blockNum으로 음수를 입력 받았을 때
	@Test
	public void testCopyToRightAbnormalCase3() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));

		String s_before = pairBlocksToString(blocks);
		commandClient.copyToRight(blocks.getLeft(), blocks.getRight(), -1);
		String s_after = pairBlocksToString(blocks);
		
		assertEquals(s_before, s_after);
	}
	
	//두 String이 모두 공스트링("")일 때
	@Test
	public void testCopyToRightAbnormalCase4() {
		String s_before = pairBlocksToString(blocks);
		commandClient.copyToRight(blocks.getLeft(), blocks.getRight(), 0);
		String s_after = pairBlocksToString(blocks);
		
		assertEquals(s_before, s_after);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testUndoCase1() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		
		commandClient.copyToLeft(blocks.getLeft(), blocks.getRight(), 0);
		String s = pairBlocksToString(blocks);
		
		assertEquals("aabc\n\n\n aabc\nx\ny\n", s);
		commandClient.undo();
		s = pairBlocksToString(blocks);
		assertEquals("d\naabc\n\n\n \naabc\nx\ny\n", s);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testUndoCase2() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		
		commandClient.copyToLeft(blocks.getLeft(), blocks.getRight(), 2);
		String s = pairBlocksToString(blocks);
		
		assertEquals("d\naabc\nx\ny\n \naabc\nx\ny\n", s);
		commandClient.undo();
		s = pairBlocksToString(blocks);
		assertEquals("d\naabc\n\n\n \naabc\nx\ny\n", s);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testUndoCase3() {
		blocks.getLeft().add(makeSpaceBlock("\n\n\n"));
		
		blocks.getRight().add(makeChangedBlock("aabc\nx\ny\n"));
		
		commandClient.copyToLeft(blocks.getLeft(), blocks.getRight(), 0);
		String s = pairBlocksToString(blocks);
		
		assertEquals("aabc\nx\ny\n aabc\nx\ny\n", s);
		commandClient.undo();
		s = pairBlocksToString(blocks);
		assertEquals("\n\n\n aabc\nx\ny\n", s);
	}
	
	//unchanged Block을 copy 시도
	@Test
	public void testUndoCase4() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		
		String s_before = pairBlocksToString(blocks);
		commandClient.copyToLeft(blocks.getLeft(), blocks.getRight(), 1);
		String s_after = pairBlocksToString(blocks);
		
		assertEquals(s_before, s_after);
		
		commandClient.undo();
		assertEquals(s_before, s_after);
	}
	
	//사이즈 보다 큰 blockNum을 입력으로 받았을 때
	@Test
	public void testUndoCase5() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		
		String s_before = pairBlocksToString(blocks);
		commandClient.copyToLeft(blocks.getLeft(), blocks.getRight(), 10);
		String s_after = pairBlocksToString(blocks);
		
		assertEquals(s_before, s_after);
		commandClient.undo();
		assertEquals(s_before, s_after);
	}
	
	//blockNum으로 음수를 입력 받았을 때
	@Test
	public void testUndoCase6() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		
		String s_before = pairBlocksToString(blocks);
		commandClient.copyToLeft(blocks.getLeft(), blocks.getRight(), -1);
		String s_after = pairBlocksToString(blocks);
		
		assertEquals(s_before, s_after);
		commandClient.undo();
		assertEquals(s_before, s_after);
	}
	
	//두 String이 모두 공스트링("")일 때
	@Test
	public void testUndoCase7() {		
		String s_before = pairBlocksToString(blocks);
		commandClient.copyToLeft(blocks.getLeft(), blocks.getRight(), 0);
		String s_after = pairBlocksToString(blocks);
		
		assertEquals(s_before, s_after);
		commandClient.undo();
		assertEquals(s_before, s_after);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testUndoCase8() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		
		commandClient.copyToRight(blocks.getLeft(), blocks.getRight(), 0);
		String s = pairBlocksToString(blocks);
		
		assertEquals("d\naabc\n\n\n d\naabc\nx\ny\n", s);
		commandClient.undo();
		s = pairBlocksToString(blocks);
		assertEquals("d\naabc\n\n\n \naabc\nx\ny\n", s);
	}

	//정상적인 형태의 임의의 case
	@Test
	public void testUndoCase9() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		
		commandClient.copyToRight(blocks.getLeft(), blocks.getRight(), 2);
		String s = pairBlocksToString(blocks);
		
		assertEquals("d\naabc\n \naabc\n", s);
		commandClient.undo();
		s = pairBlocksToString(blocks);
		assertEquals("d\naabc\n\n\n \naabc\nx\ny\n", s);
	}

	//정상적인 형태의 임의의 case
	@Test
	public void testUndoCase10() {
		blocks.getLeft().add(makeSpaceBlock("\n\n\n"));
		
		blocks.getRight().add(makeChangedBlock("aabc\nx\ny\n"));

		commandClient.copyToRight(blocks.getLeft(), blocks.getRight(), 0);
		String s = pairBlocksToString(blocks);
		
		assertEquals(" ", s);
		commandClient.undo();
		s = pairBlocksToString(blocks);
		assertEquals("\n\n\n aabc\nx\ny\n", s);
	}
	
	//USC, CSU, USU, CSC
	
	//정상적인 형태의 임의의 case
	//USC
	@Test
	public void testUndoCase11() {
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		
		commandClient.copyToLeft(blocks.getLeft(), blocks.getRight(), 1);
		commandClient.undo();
		String s = pairBlocksToString(blocks);
		assertEquals("aabc\nd\n\n\n aabc\n\nx\ny\n", s);
	}
	
	//정상적인 형태의 임의의 case
	//CSU
	@Test
	public void testUndoCase12() {
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		
		commandClient.copyToLeft(blocks.getLeft(), blocks.getRight(), 1);
		commandClient.undo();
		String s = pairBlocksToString(blocks);
		assertEquals("\n\nd\naabc\n x\ny\n\naabc\n", s);
	}
	
	//정상적인 형태의 임의의 case
	//USU
	@Test
	public void testUndoCase13() {
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		
		commandClient.copyToLeft(blocks.getLeft(), blocks.getRight(), 1);
		commandClient.undo();
		String s = pairBlocksToString(blocks);
		assertEquals("aabc\nd\naabc\n aabc\n\naabc\n", s);
	}
	
	//정상적인 형태의 임의의 case
	//CSC
	@Test
	public void testUndoCase14() {
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		
		commandClient.copyToLeft(blocks.getLeft(), blocks.getRight(), 1);
		commandClient.undo();
		String s = pairBlocksToString(blocks);
		assertEquals("\n\nd\n\n\n x\ny\n\nx\ny\n", s);
	}
	
	// UCU, UCS, SCU, SCS
	
	//정상적인 형태의 임의의 case
	//UCU
	@Test
	public void testUndoCase15() {
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		
		commandClient.copyToLeft(blocks.getLeft(), blocks.getRight(), 1);
		commandClient.undo();
		String s = pairBlocksToString(blocks);
		assertEquals("aabc\n\n\naabc\n aabc\nx\ny\naabc\n", s);
	}
	
	//정상적인 형태의 임의의 case
	//UCS
	@Test
	public void testUndoCase16() {
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		blocks.getLeft().add(makeChangedBlock("d\n"));
		
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		blocks.getRight().add(makeSpaceBlock("\n"));
		
		commandClient.copyToLeft(blocks.getLeft(), blocks.getRight(), 1);
		commandClient.undo();
		String s = pairBlocksToString(blocks);
		assertEquals("aabc\n\n\nd\n aabc\nx\ny\n\n", s);
	}
	
	//정상적인 형태의 임의의 case
	//SCU
	@Test
	public void testUndoCase17() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));

		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		
		commandClient.copyToLeft(blocks.getLeft(), blocks.getRight(), 1);
		commandClient.undo();
		String s = pairBlocksToString(blocks);
		assertEquals("d\n\n\naabc\n \nx\ny\naabc\n", s);
	}
	
	//정상적인 형태의 임의의 case
	//SCS
	@Test
	public void testUndoCase18() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		blocks.getLeft().add(makeChangedBlock("d\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		blocks.getRight().add(makeSpaceBlock("\n"));
		
		commandClient.copyToLeft(blocks.getLeft(), blocks.getRight(), 1);
		commandClient.undo();
		String s = pairBlocksToString(blocks);
		assertEquals("d\n\n\nd\n \nx\ny\n\n", s);
	}
	
	//USC, CSU, USU, CSC
	
	//정상적인 형태의 임의의 case
	//USC
	@Test
	public void testUndoCase19() {
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n"));
		blocks.getLeft().add(makeChangedBlock("x\ny\n"));
		
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("d\n"));
		blocks.getRight().add(makeSpaceBlock("\n\n"));
		
		commandClient.copyToRight(blocks.getLeft(), blocks.getRight(), 1);
		commandClient.undo();
		String s = pairBlocksToString(blocks);
		assertEquals("aabc\n\nx\ny\n aabc\nd\n\n\n", s);
	}
	
	//정상적인 형태의 임의의 case
	//CSU
	@Test
	public void testUndoCase20() {
		blocks.getLeft().add(makeChangedBlock("x\ny\n"));
		blocks.getLeft().add(makeSpaceBlock("\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n\n"));
		blocks.getRight().add(makeChangedBlock("d\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		
		commandClient.copyToRight(blocks.getLeft(), blocks.getRight(), 1);
		commandClient.undo();
		String s = pairBlocksToString(blocks);
		assertEquals("x\ny\n\naabc\n \n\nd\naabc\n", s);
	}
	
	//정상적인 형태의 임의의 case
	//USU
	@Test
	public void testUndoCase21() {
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("d\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		
		commandClient.copyToRight(blocks.getLeft(), blocks.getRight(), 1);
		commandClient.undo();
		String s = pairBlocksToString(blocks);
		assertEquals("aabc\n\naabc\n aabc\nd\naabc\n", s);
	}
	
	//정상적인 형태의 임의의 case
	//CSC
	@Test
	public void testUndoCase22() {
		blocks.getLeft().add(makeChangedBlock("x\ny\n"));
		blocks.getLeft().add(makeSpaceBlock("\n"));
		blocks.getLeft().add(makeChangedBlock("x\ny\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n\n"));
		blocks.getRight().add(makeChangedBlock("d\n"));
		blocks.getRight().add(makeSpaceBlock("\n\n"));
		
		commandClient.copyToRight(blocks.getLeft(), blocks.getRight(), 1);
		commandClient.undo();
		String s = pairBlocksToString(blocks);
		assertEquals("x\ny\n\nx\ny\n \n\nd\n\n\n", s);
	}
	
	// UCU, UCS, SCU, SCS
	
	//정상적인 형태의 임의의 case
	//UCU
	@Test
	public void testUndoCase23() {
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeChangedBlock("x\ny\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeSpaceBlock("\n\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		
		commandClient.copyToRight(blocks.getLeft(), blocks.getRight(), 1);
		commandClient.undo();
		String s = pairBlocksToString(blocks);
		assertEquals("aabc\nx\ny\naabc\n aabc\n\n\naabc\n", s);
	}
	
	//정상적인 형태의 임의의 case
	//UCS
	@Test
	public void testUndoCase24() {
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeChangedBlock("x\ny\n"));
		blocks.getLeft().add(makeSpaceBlock("\n"));
		
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeSpaceBlock("\n\n"));
		blocks.getRight().add(makeChangedBlock("d\n"));
		
		commandClient.copyToRight(blocks.getLeft(), blocks.getRight(), 1);
		commandClient.undo();
		String s = pairBlocksToString(blocks);
		assertEquals("aabc\nx\ny\n\n aabc\n\n\nd\n", s);
	}
	
	//정상적인 형태의 임의의 case
	//SCU
	@Test
	public void testUndoCase25() {
		blocks.getLeft().add(makeSpaceBlock("\n"));
		blocks.getLeft().add(makeChangedBlock("x\ny\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		
		blocks.getRight().add(makeChangedBlock("d\n"));
		blocks.getRight().add(makeSpaceBlock("\n\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		
		commandClient.copyToRight(blocks.getLeft(), blocks.getRight(), 1);
		commandClient.undo();
		String s = pairBlocksToString(blocks);
		assertEquals("\nx\ny\naabc\n d\n\n\naabc\n", s);
	}
	
	//정상적인 형태의 임의의 case
	//SCS
	@Test
	public void testUndoCase26() {		
		blocks.getLeft().add(makeSpaceBlock("\n"));
		blocks.getLeft().add(makeChangedBlock("x\ny\n"));
		blocks.getLeft().add(makeSpaceBlock("\n"));
		
		blocks.getRight().add(makeChangedBlock("d\n"));
		blocks.getRight().add(makeSpaceBlock("\n\n"));
		blocks.getRight().add(makeChangedBlock("d\n"));
		
		commandClient.copyToRight(blocks.getLeft(), blocks.getRight(), 1);
		commandClient.undo();
		String s = pairBlocksToString(blocks);
		assertEquals("\nx\ny\n\n d\n\n\nd\n", s);
	}
	
	//정상적인 형태의 log case 1
	@Test
	public void testLogCase1() {
		//기존 파일 삭제
		File file = new File("log.txt");
		file.delete();
		
		blocks.getLeft().add(makeSpaceBlock("\n"));
		blocks.getLeft().add(makeChangedBlock("x\ny\n"));
		blocks.getLeft().add(makeSpaceBlock("\n"));
		
		blocks.getRight().add(makeChangedBlock("d\n"));
		blocks.getRight().add(makeSpaceBlock("\n\n"));
		blocks.getRight().add(makeChangedBlock("d\n"));
		
		commandClient.copyToRight(blocks.getLeft(), blocks.getRight(), 1);
		commandClient.createLog();

		String s;
		String str = "";
		
		try {
			BufferedReader in = new BufferedReader(new FileReader("log.txt"));
			
	        while ((s = in.readLine()) != null) {
	          str += s;
	        }
	        in.close();
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//readLine 때문에 \n은 먹힘
		assertEquals("copy to Right : 1th block", str);
	}
	
	//정상적인 형태의 log case 2
	@Test
	public void testLogCase2() {
		//기존 파일 삭제
		File file = new File("log.txt");
		file.delete();
		
		blocks.getLeft().add(makeSpaceBlock("\n"));
		blocks.getLeft().add(makeChangedBlock("x\ny\n"));
		blocks.getLeft().add(makeSpaceBlock("\n"));
		
		blocks.getRight().add(makeChangedBlock("d\n"));
		blocks.getRight().add(makeSpaceBlock("\n\n"));
		blocks.getRight().add(makeChangedBlock("d\n"));
		
		commandClient.copyToRight(blocks.getLeft(), blocks.getRight(), 1);
		commandClient.copyToLeft(blocks.getLeft(), blocks.getRight(), 0);
		commandClient.createLog();

		String s;
		String str = "";
		
		try {
			BufferedReader in = new BufferedReader(new FileReader("log.txt"));
			
	        while ((s = in.readLine()) != null) {
	          str += s;
	        }
	        in.close();
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//readLine 때문에 \n은 먹힘
		assertEquals("copy to Right : 1th blockcopy to Left : 0th block", str);
	}
}
