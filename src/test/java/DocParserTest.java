import model.fileIO.DOCParser;
import model.fileIO.DocumentExtensionBasedContentParser;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Created by Donghwan on 2016-11-26.
 */
public class DocParserTest {

    @Test
    public void parseFileTest() throws Exception {
        File testfile = new File(getClass().getResource("docparseTest.docx").getPath());
        DocumentExtensionBasedContentParser docParser = new DOCParser();
        assertEquals("hello\n", docParser.parseContent(testfile));
    }
}
