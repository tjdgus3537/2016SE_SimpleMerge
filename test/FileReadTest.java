import controller.fileIO.ComparisonFileReader;
import java.io.File;
import static org.junit.Assert.assertEquals;

/**
 * Created by Donghwan on 5/12/2016.
 *
 */
public class FileReadTest{
    String testFileResult;
    ComparisonFileReader fileReader=new ComparisonFileReader();

    @org.junit.Before
    public void setUp() throws Exception {
        testFileResult = "hello\n" +
                "world\n" +
                "and\n" +
                "java";
        // TODO FileReader
    }

    @org.junit.Test
    public void testReadFile() throws Exception {
        // TODO
        assertEquals(testFileResult, fileReader.readFile(new File("test"+"/readTestFile.txt")).toString());
    }
}
