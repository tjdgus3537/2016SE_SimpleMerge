import model.fileIO.parser.DOCParser;
import model.fileIO.parser.DocumentExtensionBasedContentParser;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Created by Donghwan on 2016-11-26.
 */
public class DocParserTest {

    @Test
    public void parsedocFileTest() throws Exception {
        File testfile = new File(getClass().getResource("docparseTest2.doc").getPath());
        DocumentExtensionBasedContentParser docParser = new DOCParser();
        assertEquals("hello\n", docParser.parseContent(testfile));
    }
}
