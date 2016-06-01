
import static org.junit.Assert.*;

import java.io.File;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import model.editorModel.EditorModel;
import model.editorModel.EditorModelInterface;
import model.fileIO.file.ComparisonTargetInterface;

/**
 * Created by Seonghyeon on 6/2/2016.
 * EditorModel에 관련한 테스트
 */

public class EditorModelTest {
	private ComparisonTargetInterface comparisonTargetMock;
	EditorModelInterface editorModel;

	@Before
	public void setUp() {
		comparisonTargetMock = EasyMock.createMock(ComparisonTargetInterface.class);
	}
	
	//comparisonTarget 안에 내용물이 들어 있어서 getSource()를 하면 null이 아닌 값이 return 되는 경우
	@Test
	public void testIsFileLoaded1() {
		EasyMock.expect(comparisonTargetMock.getSource()).andReturn(new File(""));
		
		EasyMock.replay(comparisonTargetMock);
		
		editorModel = new EditorModel(comparisonTargetMock);
		
		assertTrue(editorModel.isFileLoaded());
	}

	//comparisonTarget 안에 내용물이 없어서 getSource()를 하면 null이 return 되는 경우
	@Test
	public void testIsFileLoaded2() {
		editorModel = new EditorModel(comparisonTargetMock);
		
		assertFalse(editorModel.isFileLoaded());		
	}
}
