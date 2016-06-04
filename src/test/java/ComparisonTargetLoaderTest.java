import model.fileIO.ComparisonTargetLoader;
import model.fileIO.file.ComparisonTargetInterface;
import org.easymock.*;
import org.junit.*;
import org.mozilla.universalchardet.UniversalDetector;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.StringJoiner;
import java.util.stream.Collector;

import static org.junit.Assert.assertEquals;

/**
 * Created by Donghwan on 6/2/2016.
 *
 * ComparisonLoader 테스트 */
public class ComparisonTargetLoaderTest extends EasyMockSupport {
    @Rule
    public EasyMockRule rule = new EasyMockRule(this);
    @Mock
    ComparisonTargetInterface comparisonTargetMock;

    static String expectedContent;

    @TestSubject
    static ComparisonTargetLoader comparisonTargetLoader;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        expectedContent = "hello\n" +
                "world\n" +
                "and\n" +
                "java";
        comparisonTargetLoader = new ComparisonTargetLoader();
    }

    @Before
    public void setUp() throws Exception {
        // make sample test file
        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(new File(getClass().getResource("readTestFile.txt").getPath()).toPath(), StandardCharsets.UTF_8, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)){
            bufferedWriter.write(expectedContent);
        }

        // create mock
        comparisonTargetMock = createMock(ComparisonTargetInterface.class);
    }

    @Test
    public void readFileTest() throws Exception {
        // ComparisonTargetLoader가 해당 파일의 내용을 잘 읽어오는지 확인
        File source = new File(getClass().getResource("readTestFile.txt").getPath());
        Charset charset = StandardCharsets.UTF_8;
        String actual;
        // logic of ComparisonTargetLoader.readFile();
        try (BufferedReader reader = Files.newBufferedReader(source.toPath(), charset)) {
            actual = reader.lines().collect(Collector.of(
                    ()->new StringJoiner("\n"),
                    StringJoiner::add,
                    StringJoiner::merge,
                    StringJoiner::toString));
        }
        assertEquals(expectedContent, actual);
    }

    private Charset detectEncoding(File source) throws IOException{
        // juniversalchardet의 예제 코드
        byte[] buf = new byte[4096];
        try(FileInputStream fis = new java.io.FileInputStream(source)){
            UniversalDetector detector = new UniversalDetector(null);
            int nread;
            while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
                detector.handleData(buf, 0, nread);
            }
            detector.dataEnd();
            String encoding = detector.getDetectedCharset();
            if (encoding != null) {
                return Charset.forName(encoding);
            } else {
                return StandardCharsets.UTF_8;
            }
        }
    }

    @Test(expected = UncheckedIOException.class)
    public void detectEncodingExceptionTest() throws Exception {
        // 만약 읽을 수 없는 문자 인코딩 형식일 때 예외를 반환하는지 확인
        File source = new File(getClass().getResource("11_unicode_broken.txt").getPath());
        Charset actual = detectEncoding(source);
        // logic of ComparisonTargetLoader.readFile();
        try (BufferedReader reader = Files.newBufferedReader(source.toPath(), actual)) {
            reader.lines().collect(Collector.of(
                    () -> new StringJoiner("\n"),
                    StringJoiner::add,
                    StringJoiner::merge,
                    StringJoiner::toString));
        }
    }

    @Test
    public void detectUnicodeTest() throws Exception {
        // 만약 Unicode로 된 파일을 불러올 경우, Unicode를 인식하는지 확인
        File source = new File(getClass().getResource("11_unicode.txt").getPath());
        Charset actual = detectEncoding(source);
        assertEquals(StandardCharsets.UTF_16LE, actual);
    }

    @Test
    public void detectUnicodeBETest() throws Exception {
        // 만약 Unicode Big Endian으로 된 파일을 불러올 경우, Unicode Big Endian을 인식하는지 확인
        File source = new File(getClass().getResource("11_unibig.txt").getPath());
        Charset actual = detectEncoding(source);
        assertEquals(StandardCharsets.UTF_16BE, actual);
    }

    @Test
    public void detectUTF8Test() throws Exception {
        // 만약 UTF-8로 된 파일을 불러올 경우, UTF-8을 인식하는지 확인
        File source = new File(getClass().getResource("11_utf8.txt").getPath());
        Charset actual = detectEncoding(source);
        assertEquals(StandardCharsets.UTF_8, actual);
    }

    @Test
    public void detectEUCKRTest() throws Exception {
        // 만약 EUC-KR로 된 파일을 불러올 경우, EUC-KR을 인식하는지 확인
        File source = new File(getClass().getResource("euc_kr.txt").getPath());
        Charset actual = detectEncoding(source);
        assertEquals(Charset.forName("EUC-KR"), actual);
    }

    @Test
    public void loadTest() throws Exception {
        // comparisonTarget을 인자로 받아서 파일을 제대로 불러오는지 확인
        comparisonTargetMock.setContent(expectedContent);
        comparisonTargetMock.setSource(new File(getClass().getResource("readTestFile.txt").getPath()));
        comparisonTargetMock.setEncoding(StandardCharsets.UTF_8);
        replayAll();
        comparisonTargetLoader.load(new File(getClass().getResource("readTestFile.txt").getPath()), comparisonTargetMock);
        verifyAll();
    }
}
