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
	public void testCompare8() {
		PairBlocks pairBlockArrayList;
		pairBlockArrayList = diff.compare("\n#ignore thumbnails created by windows\nThumbs.db\n",
				"\nsyntax: glob\n\nThumbs.db\n");
		String s = pairBlockArrayListToString(pairBlockArrayList);
		
		assertEquals("\n#ignore thumbnails created by windows\n\n\nThumbs.db\n \n\nsyntax: glob\n\nThumbs.db\n", s);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testCompare9() {
		PairBlocks pairBlockArrayList;
		pairBlockArrayList = diff.compare("\n#ignore thumbnails created by windows\nsyntax: glob\n\nThumbs.db\n#Ignore files build by Visual Studio\n*.obj\n*",
				"syntax: glob\n\nThumbs.db\n*.obj\n*");
		String s = pairBlockArrayListToString(pairBlockArrayList);
		
		assertEquals("\n#ignore thumbnails created by windows\nsyntax: glob\n\nThumbs.db\n#Ignore files build by Visual Studio\n*.obj\n* \n\nsyntax: glob\n\nThumbs.db\n\n*.obj\n*", s);
	}
}