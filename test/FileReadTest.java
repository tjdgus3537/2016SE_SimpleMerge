import controller.fileIO.ComparisonFileReader;
import java.io.File;
import static org.junit.Assert.assertEquals;

/**
 * Created by Donghwan on 5/12/2016.
 * 파일 읽기 기능과 관련된 테스트
 */
public class FileReadTest{
    String testFileResult;
    ComparisonFileReader fileReader;

    @org.junit.Before
    public void setUp() throws Exception {
        testFileResult = "hello\n" +
                "world\n" +
                "and\n" +
                "java";
        // TODO FileReader를 추가
    }

    @org.junit.Test
    public void testReadFile() throws Exception {
        // TODO
        assertEquals(testFileResult, fileReader.readFile(new File("readTestFile.txt")).toString());
    }
}
