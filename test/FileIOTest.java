import controller.fileIO.ComparisonFileReader;
import controller.fileIO.ComparisonFileWriter;

import java.io.File;
import static org.junit.Assert.assertEquals;

/**
 * Created by Donghwan on 5/12/2016.
 * 파일IO에 대한 테스트
 */
public class FileIOTest {
    String testFileResult;
    ComparisonFileReader fileReader;
    ComparisonFileWriter fileWriter;

    @org.junit.Before
    public void setUp() throws Exception {
        testFileResult = "hello\n" +
                "world\n" +
                "and\n" +
                "java";
        fileReader = new ComparisonFileReader();
        fileWriter = new ComparisonFileWriter();
    }

    @org.junit.Test
    public void testReadFile() throws Exception {
        assertEquals(testFileResult, fileReader.readFile(new File("test/readTestFile.txt")).toString());
    }

    @org.junit.Test
    public void testWriteFile() throws Exception {
        fileWriter.writeFile(new File("test/writeTestFile.txt"), testFileResult);
        assertEquals(testFileResult, fileReader.readFile(new File("test/writeTestFile.txt")).toString());
    }

    @org.junit.After
    public void tearDown() throws Exception{

    }
}
