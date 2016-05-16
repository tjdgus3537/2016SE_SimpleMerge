import controller.fileIO.ComparisonFileReader;
import controller.fileIO.ComparisonFileWriter;

import java.io.File;
import static org.junit.Assert.assertEquals;

/**
 * Created by Donghwan on 5/12/2016.
 *
 */
public class FileReadTest{
    String testFileResult;
    ComparisonFileReader fileReader;
    ComparisonFileWriter fileWriter;

    @org.junit.Before
    public void setUp() throws Exception {
        testFileResult = "hello\n" +
                "world\n" +
                "and\n" +
                "java";
        fileReader=new ComparisonFileReader();
    }

    @org.junit.Test
    public void testReadFile() throws Exception {
        assertEquals(testFileResult, fileReader.readFile(new File("test/readTestFile.txt")).toString());
    }

    @org.junit.Test
    public void testWriteFile() throws Exception {
        fileWriter.writeFile(new File("test/writeTestFile.txt"), testFileResult);
        assertEquals(testFileResult, fileReader.readFile(new File("test/writeTestFile.txt")));
    }

    @org.junit.After
    public void tearDown() throws Exception{

    }
}
