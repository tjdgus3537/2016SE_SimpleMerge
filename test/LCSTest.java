package model;

import static org.junit.Assert.*;
import model.LCS;

import org.junit.Test;

/**
 * Created by Seonghyeon on 5/15/2016.
 * LCS에 관련한 테스트
 */

public class LCSTest {

	@Test
	public void testGetLCS1() {
		LCS lcs;
		String lcs_output = lcs.getLCS("abcdefg", "cdeabfg");
		
		assertEquals(true, lcs_output.equals("cdef"));
	}

}
