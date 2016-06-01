import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.diff.Copier;
import model.diff.CopierInterface;
import model.diff.block.Block;
import model.diff.block.CompState;
import model.diff.block.PairBlocks;

/**
 * Created by Seonghyeon on 6/1/2016.
 * Copier에 관련한 테스트
 */

public class CopierTest {
	private CopierInterface copier;
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
		copier = new Copier();
		blocks = new PairBlocks(new ArrayList<Block>(), new ArrayList<Block>());
	}

		
	//정상적인 형태의 임의의 case
	@Test
	public void testCopyToLeft1() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		
		copier.copyToLeft(blocks.getLeft(), blocks.getRight(), 0);
		String s = pairBlocksToString(blocks);
		
		assertEquals("aabc\n\n\n aabc\nx\ny\n", s);
	}
	
	//unchanged Block을 copy 시도
	@Test
	public void testCopyToLeft2() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		
		String s_before = pairBlocksToString(blocks);
		copier.copyToLeft(blocks.getLeft(), blocks.getRight(), 1);
		String s_after = pairBlocksToString(blocks);
		
		assertEquals(s_before, s_after);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testCopyToLeft3() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		
		copier.copyToLeft(blocks.getLeft(), blocks.getRight(), 2);
		String s = pairBlocksToString(blocks);
		
		assertEquals("d\naabc\nx\ny\n \naabc\nx\ny\n", s);
	}
	
	//원래 string 보다 큰 blockNum을 입력으로 받았을 때
	@Test
	public void testCopyToLeft4() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		
		String s_before = pairBlocksToString(blocks);
		copier.copyToLeft(blocks.getLeft(), blocks.getRight(), 10);
		String s_after = pairBlocksToString(blocks);
		
		assertEquals(s_before, s_after);
	}
	
	//blockNum으로 음수를 입력 받았을 때
	@Test
	public void testCopyToLeft5() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		
		String s_before = pairBlocksToString(blocks);
		copier.copyToLeft(blocks.getLeft(), blocks.getRight(), -1);
		String s_after = pairBlocksToString(blocks);
		
		assertEquals(s_before, s_after);
	}

	//정상적인 형태의 임의의 case
	@Test
	public void testCopyToLeft6() {
		blocks.getLeft().add(makeSpaceBlock("\n\n\n"));
		
		blocks.getRight().add(makeChangedBlock("aabc\nx\ny\n"));
		
		copier.copyToLeft(blocks.getLeft(), blocks.getRight(), 0);
		String s = pairBlocksToString(blocks);
		
		assertEquals("aabc\nx\ny\n aabc\nx\ny\n", s);
	}
	
	//두 String이 모두 공스트링("")일 때
	@Test
	public void testCopyToLeft7() {		
		String s_before = pairBlocksToString(blocks);
		copier.copyToLeft(blocks.getLeft(), blocks.getRight(), 0);
		String s_after = pairBlocksToString(blocks);
		
		assertEquals(s_before, s_after);
	}

	//정상적인 형태의 임의의 case
	@Test
	public void testCopyToRight1() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		
		copier.copyToRight(blocks.getLeft(), blocks.getRight(), 0);
		String s = pairBlocksToString(blocks);
		
		assertEquals("d\naabc\n\n\n d\naabc\nx\ny\n", s);
	}
	
	//unchanged Block을 copy 시도
	@Test
	public void testCopyToRight2() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		
		String s_before = pairBlocksToString(blocks);
		copier.copyToRight(blocks.getLeft(), blocks.getRight(), 1);
		String s_after = pairBlocksToString(blocks);
		
		assertEquals(s_before, s_after);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testCopyToRight3() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		
		copier.copyToRight(blocks.getLeft(), blocks.getRight(), 2);
		String s = pairBlocksToString(blocks);
		
		assertEquals("d\naabc\n \naabc\n", s);
	}
	
	//원래 string 보다 큰 blockNum을 입력으로 받았을 때
	@Test
	public void testCopyToRight4() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));
		
		String s_before = pairBlocksToString(blocks);
		copier.copyToRight(blocks.getLeft(), blocks.getRight(), 10);
		String s_after = pairBlocksToString(blocks);
		
		assertEquals(s_before, s_after);
	}
	
	//blockNum으로 음수를 입력 받았을 때
	@Test
	public void testCopyToRight5() {
		blocks.getLeft().add(makeChangedBlock("d\n"));
		blocks.getLeft().add(makeUnchangedBlock("aabc\n"));
		blocks.getLeft().add(makeSpaceBlock("\n\n"));
		
		blocks.getRight().add(makeSpaceBlock("\n"));
		blocks.getRight().add(makeUnchangedBlock("aabc\n"));
		blocks.getRight().add(makeChangedBlock("x\ny\n"));

		String s_before = pairBlocksToString(blocks);
		copier.copyToRight(blocks.getLeft(), blocks.getRight(), -1);
		String s_after = pairBlocksToString(blocks);
		
		assertEquals(s_before, s_after);
	}

	//정상적인 형태의 임의의 case
	@Test
	public void testCopyToRight6() {
		blocks.getLeft().add(makeSpaceBlock("\n\n\n"));
		
		blocks.getRight().add(makeChangedBlock("aabc\nx\ny\n"));

		copier.copyToRight(blocks.getLeft(), blocks.getRight(), 0);
		String s = pairBlocksToString(blocks);
		
		assertEquals(" ", s);
	}
	
	//두 String이 모두 공스트링("")일 때
	@Test
	public void testCopyToRight7() {
		String s_before = pairBlocksToString(blocks);
		copier.copyToRight(blocks.getLeft(), blocks.getRight(), 0);
		String s_after = pairBlocksToString(blocks);
		
		assertEquals(s_before, s_after);
	}
}
