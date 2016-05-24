import static org.junit.Assert.*;

import java.util.ArrayList;

import model.Block;
import model.Diff;
import model.DiffInterface;
import model.PairBlocks;

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
	private String pairBlockArrayListToString(PairBlocks pairBlockArrayList) {
		String s = "";
		
		for(int i = 0 ; i < pairBlockArrayList.getLeft().size(); i++)
			s += blockToString(pairBlockArrayList.getLeft().get(i));
		
		s += " ";
		
		for(int i = 0 ; i < pairBlockArrayList.getRight().size(); i++)
			s += blockToString(pairBlockArrayList.getRight().get(i));
		
		return s;
	}
	
	//helper method
	private String blockArrayListToString(ArrayList<Block> blockArrayList) {
		String s = "";
		
		for(int i = 0 ; i < blockArrayList.size(); i++)
			s += blockToString(blockArrayList.get(i));
		
		return s;
	}
	
	@Before
	public void setUp() throws Exception {
		diff = new Diff();
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testCompare1() {
		PairBlocks pairBlockArrayList;
		pairBlockArrayList = diff.compare("a\nb\nc\n", "\n\na\nb\n");
		String s = pairBlockArrayListToString(pairBlockArrayList);
		
		assertEquals("\n\na\nb\nc\n \n\na\nb\n\n", s);
	}
	
	//String 중 왼쪽이 비어있을 때
	@Test
	public void testCompare2() {
		PairBlocks pairBlockArrayList;
		pairBlockArrayList = diff.compare("", "\n\na\nb\n");
		String s = pairBlockArrayListToString(pairBlockArrayList);

		assertEquals("\n\n\n\n \n\na\nb\n", s);
	}

	//String 중 오른쪽이 비어있을 때
	@Test
	public void testCompare3() {
		PairBlocks pairBlockArrayList;
		pairBlockArrayList = diff.compare("\n\na\nb\n", "");
		String s = pairBlockArrayListToString(pairBlockArrayList);

		assertEquals("\n\na\nb\n \n\n\n\n", s);
	}
	
	//String 두 개가 모두 공스트링인 경우
	@Test
	public void testCompare4() {
		PairBlocks pairBlockArrayList;
		pairBlockArrayList = diff.compare("", "");
		String s = pairBlockArrayListToString(pairBlockArrayList);

		assertEquals(" ", s);
	}
	
	//String 중 한 개가 null인 경우
	@Test
	public void testCompare5() {
		PairBlocks pairBlockArrayList;
		pairBlockArrayList = diff.compare(null, "");

		assertEquals(null, pairBlockArrayList);
	}
	
	//String 두 개 모두 null인 경우
	@Test
	public void testCompare6() {
		PairBlocks pairBlockArrayList;
		pairBlockArrayList = diff.compare(null, null);

		assertEquals(null, pairBlockArrayList);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testCompare7() {
		PairBlocks pairBlockArrayList;
		pairBlockArrayList = diff.compare("d\naabc\n", "aabc\nx\ny\n");
		String s = pairBlockArrayListToString(pairBlockArrayList);
		
		assertEquals("d\naabc\n\n\n \naabc\nx\ny\n", s);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testCopyToLeft1() {
		ArrayList<Block> blockArrayList;
		blockArrayList = diff.copyToLeft("d\naabc\n", "aabc\nx\ny\n", 0);
		String s = blockArrayListToString(blockArrayList);
		
		assertEquals("\naabc\n\n\n", s);
	}
	
	//unchanged Block을 copy 시도
	@Test
	public void testCopyToLeft2() {
		ArrayList<Block> blockArrayList;
		blockArrayList = diff.copyToLeft("d\naabc\n", "aabc\nx\ny\n", 1);
		
		assertEquals(null, blockArrayList);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testCopyToLeft3() {
		ArrayList<Block> blockArrayList;
		blockArrayList = diff.copyToLeft("d\naabc\n", "aabc\nx\ny\n", 2);
		String s = blockArrayListToString(blockArrayList);
		
		assertEquals("d\naabc\nx\ny\n", s);
	}
	
	//원래 string 보다 큰 blockNum을 입력으로 받았을 때
	@Test
	public void testCopyToLeft4() {
		ArrayList<Block> blockArrayList;
		blockArrayList = diff.copyToLeft("d\naabc\n", "aabc\nx\ny\n", 10);
		
		assertEquals(null, blockArrayList);
	}
	
	//blockNum으로 음수를 입력 받았을 때
	@Test
	public void testCopyToLeft5() {
		ArrayList<Block> blockArrayList;
		blockArrayList = diff.copyToLeft("d\naabc\n", "aabc\nx\ny\n", -1);
		
		assertEquals(null, blockArrayList);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testCopyToLeft6() {
		ArrayList<Block> blockArrayList;
		blockArrayList = diff.copyToLeft("", "aabc\nx\ny\n", 0);
		String s = blockArrayListToString(blockArrayList);
		
		assertEquals("aabc\nx\ny\n", s);
	}
	
	//두 String이 모두 공스트링("")일 때 --> blockNum이 그 어떤 String에도 소속할 수 없음
	@Test
	public void testCopyToLeft7() {
		ArrayList<Block> blockArrayList;
		blockArrayList = diff.copyToLeft("", "", 0);
		
		assertEquals(null, blockArrayList);
	}
	
	//한 String이 null일 때
	@Test
	public void testCopyToLeft8() {
		ArrayList<Block> blockArrayList;
		blockArrayList = diff.copyToLeft(null, "asdsad", 0);
		
		assertEquals(null, blockArrayList);
	}
	
	//두 String 모두 null일 때
	@Test
	public void testCopyToLeft9() {
		ArrayList<Block> blockArrayList;
		blockArrayList = diff.copyToLeft(null, null, 0);
		
		assertEquals(null, blockArrayList);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testCopyToRight1() {
		ArrayList<Block> blockArrayList;
		blockArrayList = diff.copyToRight("d\naabc\n", "aabc\nx\ny\n", 0);
		String s = blockArrayListToString(blockArrayList);
		
		assertEquals("d\naabc\nx\ny\n", s);
	}
	
	//unchanged Block을 copy 시도
	@Test
	public void testCopyToRight2() {
		ArrayList<Block> blockArrayList;
		blockArrayList = diff.copyToRight("d\naabc\n", "aabc\nx\ny\n", 1);
		
		assertEquals(null, blockArrayList);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testCopyToRight3() {
		ArrayList<Block> blockArrayList;
		blockArrayList = diff.copyToRight("d\naabc\n", "aabc\nx\ny\n", 2);
		String s = blockArrayListToString(blockArrayList);
		
		assertEquals("\naabc\n\n\n", s);
	}
	
	//원래 string 보다 큰 blockNum을 입력으로 받았을 때
	@Test
	public void testCopyToRight4() {
		ArrayList<Block> blockArrayList;
		blockArrayList = diff.copyToRight("d\naabc\n", "aabc\nx\ny\n", 10);
		
		assertEquals(null, blockArrayList);
	}
	
	//blockNum으로 음수를 입력 받았을 때
	@Test
	public void testCopyToRight5() {
		ArrayList<Block> blockArrayList;
		blockArrayList = diff.copyToRight("d\naabc\n", "aabc\nx\ny\n", -1);
		
		assertEquals(null, blockArrayList);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testCopyToRight6() {
		ArrayList<Block> blockArrayList;
		blockArrayList = diff.copyToRight("", "aabc\nx\ny\n", 0);
		String s = blockArrayListToString(blockArrayList);
		
		assertEquals("\n\n\n", s);
	}
	
	//두 String이 모두 공스트링("")일 때 --> blockNum이 그 어떤 String에도 소속할 수 없음
	@Test
	public void testCopyToRight7() {
		ArrayList<Block> blockArrayList;
		blockArrayList = diff.copyToRight("", "", 0);
		
		assertEquals(null, blockArrayList);
	}
	
	//한 String이 null일 때
	@Test
	public void testCopyToRight8() {
		ArrayList<Block> blockArrayList;
		blockArrayList = diff.copyToRight(null, "asdsad", 0);
		
		assertEquals(null, blockArrayList);
	}
	
	//두 String 모두 null일 때
	@Test
	public void testCopyToRight9() {
		ArrayList<Block> blockArrayList;
		blockArrayList = diff.copyToRight(null, null, 0);
		
		assertEquals(null, blockArrayList);
	}

	/*
	//정상적인 형태의 임의의 case
	@Test
	public void testCopyToRight10() {
		ArrayList<Block> blockArrayList;
		blockArrayList = diff.copyToRight("syntax: glob\n\nThumbs.db\n*.obj\n*.exe\n*.pdb\n*.user\n*.aps\n*.pch\n*.vspscc\n*_i.c\n*_p.c\n*.ncb\n*.suo\n*.tlb\n*.tlh\n*.bak\n*.cache\n*.ilk\n*.log\n*.dll\n*.lib\n*.sbr\n",
				"\n#ignore thumbnails created by windows\nThumbs.db\n#Ignore files build by Visual Studio\n*.obj\n*.exe\n*.pdb\n*.user\n*.aps\n*.pch\n*.vspscc\n*_i.c\n*_p.c\n*.ncb\n*.suo\n*.tlb\n*.tlh\n*.bak\n*.cache\n*.ilk\n*.log\n*.dll\n*.lib\n*.sbr\n", 0);
		String s = blockArrayListToString(blockArrayList);

		assertEquals("syntax: glob\n\n\n#ignore thumbnails created by windows\nThumbs.db\n#Ignore files build by Visual Studio\n", s);
	}
	*/
}