import controller.fileIO.ComparisonFileReader;
import controller.fileIO.ComparisonFileWriter;
import model.ComparisonFile;
import org.easymock.EasyMockSupport;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Donghwan on 5/12/2016.
 * 파일IO에 대한 테스트
 */

public class FileIOTest extends EasyMockSupport{
    String testFileResult;
    String testFileResult2;
    ComparisonFileReader fileReader;
    ComparisonFileWriter fileWriter;

    @org.junit.Before
    public void setUp() throws Exception {
        testFileResult = "hello\n" +
                "world\n" +
                "and\n" +
                "java";
        testFileResult2 = "I hate you JB";
        fileReader = new ComparisonFileReader();
        fileWriter = new ComparisonFileWriter();

        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(new File("test/writeTestFile.txt").toPath(), StandardCharsets.UTF_8, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)){
            bufferedWriter.write(testFileResult);
            bufferedWriter.flush();
        }
    }

    @org.junit.Test
    public void testReadFile() throws Exception {
        assertEquals(testFileResult, fileReader.readFile(new File("test/readTestFile.txt")).toString());
    }

    @org.junit.Test
    public void testWriteFile() throws Exception {
        fileWriter.writeFile(new File("test/writeTestFile.txt"), testFileResult); // StandardOption.CREATE가 적용되는지 확인
        assertEquals(testFileResult, fileReader.readFile(new File("test/writeTestFile.txt")).toString());

        fileWriter.writeFile(new File("test/writeTestFile.txt"), testFileResult2); // StandardOpenOption.TRUNCATE_EXISTING가 적용되는지 확인
        assertEquals(testFileResult2, fileReader.readFile(new File("test/writeTestFile.txt")).toString());
    }

    @org.junit.Test
    public void testLoadComparisonFile() throws Exception {
        ComparisonFile comparisonFile = fileReader.readComparisonFile(new File("test/readTestFile.txt"));
        assertEquals(testFileResult, comparisonFile.getContent().toString());
        assertTrue(new File("test/readTestFile.txt").equals(comparisonFile.getSource()));
    }

    @org.junit.Test
    public void testSaveComparisonFile() throws Exception {
        ComparisonFile comparisonFile = new ComparisonFile(new File("test/writeTestFile.txt"), new StringBuffer(testFileResult));
        fileWriter.writeComparisonFile(comparisonFile);
        ComparisonFile savedFile = fileReader.readComparisonFile(new File("test/writeTestFile.txt"));
        assertEquals(testFileResult, savedFile.getContent().toString());
        assertTrue(new File("test/writeTestFile.txt").equals(savedFile.getSource()));
    }

    @org.junit.After
    public void tearDown() throws Exception{

    }
}
