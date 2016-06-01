

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import model.fileIO.file.ComparisonTargetInterface;

public class EditorModelTest {
	private ComparisonTargetInterface comprisonTarget;

	@Before
	public void setUp() {
		comprisonTarget = EasyMock.createMock(ComparisonTargetInterface.class);
	}
	
	@Test
	public void testIsFileLoaded() {
		fail("Not yet implemented");
	}

}
