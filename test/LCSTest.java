import static org.junit.Assert.*;
import model.LCS;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Seonghyeon on 5/15/2016.
 * LCS에 관련한 테스트
 */

public class LCSTest {
	private LCS lcs;

	@Before
	public void setUp() throws Exception {
		LCS lcs = new LCS();
	}

	@Test
	public void testGetLCS1() {
		String lcs_output = lcs.getLCS("abcdefg", "cdeabfg");
		
		assertEquals("cdefg", lcs_output);
	}

}
