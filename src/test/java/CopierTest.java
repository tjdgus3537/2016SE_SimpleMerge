import static org.junit.Assert.*;

import org.junit.*;

import model.diff.Copier;
import model.diff.CopierInterface;
import model.diff.Diff;
import model.diff.DiffInterface;
import model.diff.block.Block;
import model.diff.block.PairBlocks;

public class CopierTest {
	private CopierInterface copier;
	private DiffInterface diff;
	PairBlocks blocks;
	
	//helper method
	private String blockToString(Block block) {
		String s = "";
		
		s += block.getContent();

		return s;
	}

	//helper method
	private String pairBlockArrayListToString(PairBlocks pairBlockArrayList) {
		String s = "";
		
		for(int i = 0 ; i < pairBlockArrayList.getLeft().size(); i++)
			s += blockToString(pairBlockArrayList.getLeft().get(i));
		
		s += " ";
		
		for(int i = 0 ; i < pairBlockArrayList.getRight().size(); i++)
			s += blockToString(pairBlockArrayList.getRight().get(i));
		
		return s;
	}
	
	@Before
	public void setUp() throws Exception {
		copier = new Copier();
		diff = new Diff();
	}

		
	//정상적인 형태의 임의의 case
	@Test
	public void testCopyToLeft1() {
		blocks = diff.compare("d\naabc\n", "aabc\nx\ny\n");
		copier.copyToLeft(blocks.getLeft(), blocks.getRight(), 0);
		String s = pairBlockArrayListToString(blocks);
		
		assertEquals("aabc\n\n\n aabc\nx\ny\n", s);
	}
	
	//unchanged Block을 copy 시도
	@Test
	public void testCopyToLeft2() {
		blocks = diff.compare("d\naabc\n", "aabc\nx\ny\n");
		String s_before = pairBlockArrayListToString(blocks);
		copier.copyToLeft(blocks.getLeft(), blocks.getRight(), 1);
		String s_after = pairBlockArrayListToString(blocks);
		
		assertEquals(s_before, s_after);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testCopyToLeft3() {
		blocks = diff.compare("d\naabc\n", "aabc\nx\ny\n");
		copier.copyToLeft(blocks.getLeft(), blocks.getRight(), 2);
		String s = pairBlockArrayListToString(blocks);
		
		assertEquals("d\naabc\nx\ny\n \naabc\nx\ny\n", s);
	}
	
	//원래 string 보다 큰 blockNum을 입력으로 받았을 때
	@Test
	public void testCopyToLeft4() {
		blocks = diff.compare("d\naabc\n", "aabc\nx\ny\n");
		String s_before = pairBlockArrayListToString(blocks);
		copier.copyToLeft(blocks.getLeft(), blocks.getRight(), 10);
		String s_after = pairBlockArrayListToString(blocks);
		
		assertEquals(s_before, s_after);
	}
	
	//blockNum으로 음수를 입력 받았을 때
	@Test
	public void testCopyToLeft5() {
		blocks = diff.compare("d\naabc\n", "aabc\nx\ny\n");
		String s_before = pairBlockArrayListToString(blocks);
		copier.copyToLeft(blocks.getLeft(), blocks.getRight(), -1);
		String s_after = pairBlockArrayListToString(blocks);
		
		assertEquals(s_before, s_after);
	}

	//정상적인 형태의 임의의 case
	@Test
	public void testCopyToLeft6() {
		blocks = diff.compare("", "aabc\nx\ny\n");
		copier.copyToLeft(blocks.getLeft(), blocks.getRight(), 0);
		String s = pairBlockArrayListToString(blocks);
		
		assertEquals("aabc\nx\ny\n aabc\nx\ny\n", s);
	}
	
	//두 String이 모두 공스트링("")일 때 --> blockNum이 그 어떤 String에도 소속할 수 없음
	@Test
	public void testCopyToLeft7() {
		blocks = diff.compare("", "");
		String s_before = pairBlockArrayListToString(blocks);
		copier.copyToLeft(blocks.getLeft(), blocks.getRight(), 0);
		String s_after = pairBlockArrayListToString(blocks);
		
		assertEquals(s_before, s_after);
	}

	//정상적인 형태의 임의의 case
	@Test
	public void testCopyToRight1() {
		blocks = diff.compare("d\naabc\n", "aabc\nx\ny\n");
		copier.copyToRight(blocks.getLeft(), blocks.getRight(), 0);
		String s = pairBlockArrayListToString(blocks);
		
		assertEquals("d\naabc\n\n\n d\naabc\nx\ny\n", s);
	}
	
	//unchanged Block을 copy 시도
	@Test
	public void testCopyToRight2() {
		blocks = diff.compare("d\naabc\n", "aabc\nx\ny\n");
		String s_before = pairBlockArrayListToString(blocks);
		copier.copyToRight(blocks.getLeft(), blocks.getRight(), 1);
		String s_after = pairBlockArrayListToString(blocks);
		
		assertEquals(s_before, s_after);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testCopyToRight3() {
		blocks = diff.compare("d\naabc\n", "aabc\nx\ny\n");
		copier.copyToRight(blocks.getLeft(), blocks.getRight(), 2);
		String s = pairBlockArrayListToString(blocks);
		
		assertEquals("d\naabc\n \naabc\n", s);
	}
	
	//원래 string 보다 큰 blockNum을 입력으로 받았을 때
	@Test
	public void testCopyToRight4() {
		blocks = diff.compare("d\naabc\n", "aabc\nx\ny\n");
		String s_before = pairBlockArrayListToString(blocks);
		copier.copyToRight(blocks.getLeft(), blocks.getRight(), 10);
		String s_after = pairBlockArrayListToString(blocks);
		
		assertEquals(s_before, s_after);
	}
	
	//blockNum으로 음수를 입력 받았을 때
	@Test
	public void testCopyToRight5() {
		blocks = diff.compare("d\naabc\n", "aabc\nx\ny\n");
		String s_before = pairBlockArrayListToString(blocks);
		copier.copyToRight(blocks.getLeft(), blocks.getRight(), -1);
		String s_after = pairBlockArrayListToString(blocks);
		
		assertEquals(s_before, s_after);
	}

	//정상적인 형태의 임의의 case
	@Test
	public void testCopyToRight6() {
		blocks = diff.compare("", "aabc\nx\ny\n");
		copier.copyToRight(blocks.getLeft(), blocks.getRight(), 0);
		String s = pairBlockArrayListToString(blocks);
		
		assertEquals(" ", s);
	}
	
	//두 String이 모두 공스트링("")일 때 --> blockNum이 그 어떤 String에도 소속할 수 없음
	@Test
	public void testCopyToRight7() {
		blocks = diff.compare("", "");
		String s_before = pairBlockArrayListToString(blocks);
		copier.copyToRight(blocks.getLeft(), blocks.getRight(), 0);
		String s_after = pairBlockArrayListToString(blocks);
		
		assertEquals(s_before, s_after);
	}
}
