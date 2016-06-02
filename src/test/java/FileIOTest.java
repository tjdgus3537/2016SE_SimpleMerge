import model.fileIO.ComparisonTargetLoader;
import model.fileIO.ComparisonTargetWriter;
import model.fileIO.file.ComparisonTarget;
import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

/**
 * Created by Donghwan on 5/12/2016.
 * 파일IO에 대한 테스트
 *
 * @deprecated loader와 writer test로 쪼개짐
 */

public class FileIOTest extends EasyMockSupport{
    String testFileResult;
    String testFileResult2;
    ComparisonTargetLoader fileReader;
    ComparisonTargetWriter fileWriter;

    @Before
    public void setUp() throws Exception {
        testFileResult = "hello\n" +
                "world\n" +
                "and\n" +
                "java";
        testFileResult2 = "I hate you JB";
        fileReader = new ComparisonTargetLoader();
        fileWriter = new ComparisonTargetWriter();

        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(new File("writeTestFile.txt").toPath(), StandardCharsets.UTF_8, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)){
            bufferedWriter.write(testFileResult);
            bufferedWriter.flush();
        }
    }
/*
    @Test
    public void testReadFile() throws Exception {
        assertEquals(testFileResult, fileReader.readFile(new File("test/readTestFile.txt")).toString());
    }

    @Test
    public void testWriteFile() throws Exception {
        fileWriter.write(new ComparisonTarget(new File("test/writeTestFile.txt"), testFileResult)); // StandardOption.CREATE가 적용되는지 확인
        assertEquals(testFileResult, fileReader.readFile(new File("test/writeTestFile.txt")).toString());

        fileWriter.write(new File("test/writeTestFile.txt"), testFileResult2); // StandardOpenOption.TRUNCATE_EXISTING가 적용되는지 확인
        assertEquals(testFileResult2, fileReader.readFile(new File("test/writeTestFile.txt")).toString());
    }

    @Test
    public void testLoadComparisonFile() throws Exception {
        ComparisonTarget comparisonFile = fileReader.load(new File("test/readTestFile.txt"));
        assertEquals(testFileResult, comparisonFile.getContent().toString());
        assertTrue(new File("test/readTestFile.txt").equals(comparisonFile.getSource()));
    }

    @Test
    public void testSaveComparisonFile() throws Exception {
        ComparisonTarget comparisonFile = new ComparisonTarget(new File("test/writeTestFile.txt"), new StringBuffer(testFileResult));
        fileWriter.write(comparisonFile);
        ComparisonTarget savedFile = fileReader.load(new File("test/writeTestFile.txt"));
        assertEquals(testFileResult, savedFile.getContent().toString());
        assertEquals(new File("test/writeTestFile.txt"), savedFile.getSource());
    }

    */
    @After
    public void tearDown() throws Exception{

    }
}
