

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import model.diff.Copier;
import model.diff.CopierInterface;
import model.diff.Diff;
import model.diff.DiffInterface;
import model.diff.block.Block;
import model.diff.block.PairBlocks;

public class CopierTest {
	private CopierInterface copier;
	
	//helper method
	private String blockToString(Block block) {
		String s = "";
		
		s += block.getContent();

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
		copier = new Copier();
	}
	
	@Test
	public void testCopyToLeft() {
		fail("Not yet implemented");
	}

	@Test
	public void testCopyToRight() {
		fail("Not yet implemented");
	}

}
