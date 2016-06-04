import model.fileIO.ComparisonTargetWriter;
import model.fileIO.file.ComparisonTargetInterface;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.StringJoiner;
import java.util.stream.Collector;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

/**
 * Created by Donghwan on 6/2/2016.
 *
 * ComparisonWriter 테스트
 */
public class ComparisonWriterTest extends EasyMockSupport {
    @Rule
    public EasyMockRule rule = new EasyMockRule(this);
    @Mock
    ComparisonTargetInterface comparisonTargetMock;

    static String expectedContent;

    @TestSubject
    static ComparisonTargetWriter comparisonTargetWriter;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        expectedContent = "hello\n" +
                "world\n" +
                "and\n" +
                "java";
        comparisonTargetWriter = new ComparisonTargetWriter();
    }

    @Before
    public void setUp() throws Exception {
        // make sample test file
        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(new File(getClass().getResource("writeTestFile.txt").getPath()).toPath(), StandardCharsets.UTF_8, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)){
            bufferedWriter.write(expectedContent);
        }

        // create mock
        comparisonTargetMock = createMock(ComparisonTargetInterface.class);
    }

    @Test
    public void writeFileTest() throws Exception {
        // 파일 쓰기를 제대로 하는지 확인
        // logic of ComparisonTargetWriter.writeFile();
        File destination = new File(getClass().getResource("writeTestFile.txt").getPath());
        Charset charset = StandardCharsets.UTF_8;
        try(BufferedWriter writer = Files.newBufferedWriter(destination.toPath(),
                charset, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)){
            writer.write(expectedContent);
        }

        // read sample file
        String actual;
        try (BufferedReader reader = Files.newBufferedReader(destination.toPath(), charset)) {
            actual = reader.lines().collect(Collector.of(
                    ()->new StringJoiner("\n"),
                    StringJoiner::add,
                    StringJoiner::merge,
                    StringJoiner::toString));
        }
        assertEquals(expectedContent, actual);
    }

    @Test
    public void writeTest() throws Exception {
        // comparisonTarget을 인자로 받아서 파일을 제대로 저장하는지 확인
        expect(comparisonTargetMock.getSource()).andReturn(new File(getClass().getResource("writeTestFile.txt").getPath()));
        expect(comparisonTargetMock.getContent()).andReturn(expectedContent);
        expect(comparisonTargetMock.getEncoding()).andReturn(StandardCharsets.UTF_8);
        replayAll();
        comparisonTargetWriter.write(comparisonTargetMock);
        verifyAll();
    }
}
