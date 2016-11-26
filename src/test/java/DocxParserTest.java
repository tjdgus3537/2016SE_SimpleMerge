import model.fileIO.parser.DocumentExtensionBasedContentParser;
import model.fileIO.parser.DOCXParser;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Created by Donghwan on 2016-11-26.
 */
public class DocxParserTest {
    @Test
    public void parsedocxFileTest() throws Exception {
        File testfile = new File(getClass().getResource("docparseTest.docx").getPath());
        DocumentExtensionBasedContentParser docParser = new DOCXParser();
        assertEquals("hello\n", docParser.parseContent(testfile));
    }
}
