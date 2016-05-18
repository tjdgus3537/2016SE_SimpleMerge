
import static org.junit.Assert.*;

import model.Block;
import model.Diff;
import model.PairBlockArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Seonghyeon on 5/18/2016.
 * Diff에 관련한 테스트
 */

public class DiffTest {
	private Diff diff;
	
	//helper method
	private String blockToString(Block block) {
		String s = "";
		
		s += block.getContent();

		return s;
	}
	
	//helper method
	private String pairBlockArrayListToString(PairBlockArrayList pairBlockArrayList) {
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
		PairBlockArrayList pairBlockArrayList;
		pairBlockArrayList = diff.compare("a\nb\nc\n", "\n\na\nb\n");
		String s = pairBlockArrayListToString(pairBlockArrayList);
		
		assertEquals("-122\n\n020a\nb\n211c\n 021\n\n220a\nb\n-112\n", s);
	}
	
	//String 중 왼쪽이 비어있을 때
	@Test
	public void testCompare2() {
		PairBlockArrayList pairBlockArrayList;
		pairBlockArrayList = diff.compare("", "\n\na\nb\n");
		String s = pairBlockArrayListToString(pairBlockArrayList);

		assertEquals("-142\n\n\n\n 041\n\na\nb\n", s);
	}

	//String 중 오른쪽이 비어있을 때
	@Test
	public void testCompare3() {
		PairBlockArrayList pairBlockArrayList;
		pairBlockArrayList = diff.compare("\n\na\nb\n", "");
		String s = pairBlockArrayListToString(pairBlockArrayList);

		assertEquals("041\n\na\nb\n -142\n\n\n\n", s);
	}
	
	//String 두 개가 모두 공스트링인 경우
	@Test
	public void testCompare4() {
		PairBlockArrayList pairBlockArrayList;
		pairBlockArrayList = diff.compare("", "");
		String s = pairBlockArrayListToString(pairBlockArrayList);

		assertEquals(" ", s);
	}
	
	//String 중 한 개가 null인 경우
	@Test
	public void testCompare5() {
		PairBlockArrayList pairBlockArrayList;
		pairBlockArrayList = diff.compare(null, "");

		assertEquals(null, pairBlockArrayList);
	}
	
	//String 두 개 모두 null인 경우
	@Test
	public void testCompare6() {
		PairBlockArrayList pairBlockArrayList;
		pairBlockArrayList = diff.compare(null, null);

		assertEquals(null, pairBlockArrayList);
	}
}
