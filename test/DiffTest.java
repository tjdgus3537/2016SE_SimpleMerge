

import static org.junit.Assert.*;
import model.Block;
import model.Diff;
import model.PairBlockArrayList;

import org.junit.Before;
import org.junit.Test;

public class DiffTest {
	private Diff diff;
	
	//helper method
	private String blockToString(Block block) {
		String s = "";
		
		s += block.getStartNumber();
		s += block.getNumberOfLine();
		s += block.getState();
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

}
