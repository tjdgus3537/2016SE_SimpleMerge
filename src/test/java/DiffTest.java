import static org.junit.Assert.*;

import model.diff.block.Block;
import model.diff.Diff;
import model.diff.DiffInterface;
import model.diff.block.PairBlocks;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Seonghyeon on 5/18/2016.
 * Diff에 관련한 테스트
 */

public class DiffTest {
	private DiffInterface diff;
	
	//helper method
	private String blockToString(Block block) {
		String s = "";
		
		s += block.getContent();

		return s;
	}
	
	//helper method
	private String pairBlocksToString(PairBlocks blocks) {
		String s = "";
		
		for(int i = 0 ; i < blocks.getLeft().size(); i++)
			s += blockToString(blocks.getLeft().get(i));
		
		s += " ";
		
		for(int i = 0 ; i < blocks.getRight().size(); i++)
			s += blockToString(blocks.getRight().get(i));
		
		return s;
	}
		
	@Before
	public void setUp() throws Exception {
		diff = new Diff();
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testCompareNormalCase1() {
		PairBlocks blocks;
		blocks = diff.compare("a\nb\nc\n", "\n\na\nb\n");
		String s = pairBlocksToString(blocks);
		
		assertEquals("\n\na\nb\nc\n \n\na\nb\n\n", s);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testCompareNormalCase2() {
		PairBlocks blocks;
		blocks = diff.compare("d\naabc\n", "aabc\nx\ny\n");
		String s = pairBlocksToString(blocks);
		
		assertEquals("d\naabc\n\n\n \naabc\nx\ny\n", s);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testCompareNormalCase3() {
		PairBlocks blocks;
		blocks = diff.compare("\n#ignore thumbnails created by windows\nThumbs.db\n",
				"\nsyntax: glob\n\nThumbs.db\n");
		String s = pairBlocksToString(blocks);
		
		assertEquals("\n#ignore thumbnails created by windows\n\n\nThumbs.db\n \n\nsyntax: glob\n\nThumbs.db\n", s);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testCompareNormalCase4() {
		PairBlocks blocks;
		blocks = diff.compare("\n#ignore thumbnails created by windows\nsyntax: glob\n\nThumbs.db\n#Ignore files build by Visual Studio\n*.obj\n*",
				"syntax: glob\n\nThumbs.db\n*.obj\n*");
		String s = pairBlocksToString(blocks);
		
		assertEquals("\n#ignore thumbnails created by windows\nsyntax: glob\n\nThumbs.db\n#Ignore files build by Visual Studio\n*.obj\n* \n\nsyntax: glob\n\nThumbs.db\n\n*.obj\n*", s);
	}
	
	//String 중 왼쪽이 비어있을 때
	@Test
	public void testCompareAbnormalCase1() {
		PairBlocks blocks;
		blocks = diff.compare("", "\n\na\nb\n");
		String s = pairBlocksToString(blocks);

		assertEquals("\n\n\n\n \n\na\nb\n", s);
	}

	//String 중 오른쪽이 비어있을 때
	@Test
	public void testCompareAbnormalCase2() {
		PairBlocks blocks;
		blocks = diff.compare("\n\na\nb\n", "");
		String s = pairBlocksToString(blocks);

		assertEquals("\n\na\nb\n \n\n\n\n", s);
	}
	
	//String 두 개가 모두 공스트링인 경우
	@Test
	public void testCompareAbnormalCase3() {
		PairBlocks blocks;
		blocks = diff.compare("", "");
		String s = pairBlocksToString(blocks);

		assertEquals(" ", s);
	}
	
	//String 중 한 개가 null인 경우
	@Test
	public void testCompareAbnormalCase4() {
		PairBlocks blocks;
		blocks = diff.compare(null, "");

		assertEquals(null, blocks);
	}
	
	//String 두 개 모두 null인 경우
	@Test
	public void testCompareAbnormalCase5() {
		PairBlocks blocks;
		blocks = diff.compare(null, null);

		assertEquals(null, blocks);
	}
}