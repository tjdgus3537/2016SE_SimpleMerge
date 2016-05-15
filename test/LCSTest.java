import static org.junit.Assert.*;
import model.LCS;
import model.LCSInterface;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Seonghyeon on 5/15/2016.
 * LCS에 관련한 테스트
 */

public class LCSTest {
	private LCSInterface lcs;

	@Before
	public void setUp() throws Exception {
		lcs = new LCS();
	}

	//정상적인 형태의 임의의 case
	@Test
	public void testGetLCS1() {
		String lcs_output = lcs.getLCS("abcdefg", "cdeabfg");
		
		assertEquals("cdefg", lcs_output);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testGetLCS2() {
		String lcs_output = lcs.getLCS("bcdb", "abcbdab");
		
		assertEquals("bcdb", lcs_output);
	}

	//둘 중 하나가 비어 있는 string인 경우
	@Test
	public void testGetLCS3() {
		String lcs_output = lcs.getLCS("", "abcbdab");
		
		assertEquals("", lcs_output);
	}
	
	//둘 다 비어있는 string인 경우
	@Test
	public void testGetLCS4() {
		String lcs_output = lcs.getLCS("", "");
		
		assertEquals("", lcs_output);
	}
	
	//둘 중 하나가 null인 경우
	@Test
	public void testGetLCS5() {
		String lcs_output = lcs.getLCS(null, "abcbdab");
		
		assertEquals(null, lcs_output);
	}
	
	//둘 다 null인 경우
	@Test
	public void testGetLCS6() {
		String lcs_output = lcs.getLCS(null, null);
		
		assertEquals(null, lcs_output);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testGetLCS7() {
		String lcs_output = lcs.getLCS("abcbdab", "bdcaba");
		
		assertEquals("bdab", lcs_output);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testGetLCS8() {
		String lcs_output = lcs.getLCS("ACAYKP", "CAPCAK");
		
		assertEquals("ACAK", lcs_output);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testGetLCS9() {
		String lcs_output = lcs.getLCS("a\nb\nc\n", "\n\na\nb\n");
		
		assertEquals("a\nb\n", lcs_output);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testGetLCS10() {
		String lcs_output = lcs.getLCS("abcdfghjqz", "abcdefgijkrxyz");
		
		assertEquals("abcdfgjz", lcs_output);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testGetLCS11() {
		String lcs_output = lcs.getLCS("1234", "1224533324");
		
		assertEquals("1234", lcs_output);
	}
	
	//정상적인 형태의 임의의 case
	@Test
	public void testGetLCS12() {
		String lcs_output = lcs.getLCS("thisisatest", "testing123testing");
		
		assertEquals("tsitest", lcs_output);
	}
}
