import model.fileIO.parser.DOCXParser;
import model.fileIO.parser.DocumentExtensionBasedContentParser;
import model.fileIO.parser.ODTParser;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Created by Donghwan on 2016-11-26.
 */
public class OdtParserTest {
    @Test
    public void parseOdtFileTest() throws Exception {
        File testfile = new File(getClass().getResource("odtparseTest.odt").getPath());
        DocumentExtensionBasedContentParser docParser = new ODTParser();
        assertEquals("hello\n", docParser.parseContent(testfile));
    }
}
