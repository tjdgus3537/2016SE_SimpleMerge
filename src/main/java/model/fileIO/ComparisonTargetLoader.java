package model.fileIO;

import model.fileIO.file.ComparisonTargetInterface;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.StringJoiner;
import java.util.stream.Collector;
import org.mozilla.universalchardet.UniversalDetector;

/**
 * Created by Donghwan on 5/12/2016.
 * 프로그램에서 비교할 파일을 읽어오는 객체
 */
public class ComparisonTargetLoader {
	public void load(File source, ComparisonTargetInterface destination) throws IOException{
		Charset charset = detectEncoding(source);
		destination.setContent(readFile(source, charset));
		destination.setSource(source);
		destination.setEncoding(charset);
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

    private String readFile(File source, Charset charset) throws IOException{
    	try (BufferedReader reader = Files.newBufferedReader(source.toPath(), charset)) {
			return reader.lines().collect(Collector.of(
					()->new StringJoiner("\n"),
					StringJoiner::add,
					StringJoiner::merge,
					StringJoiner::toString));
		}
    }
}
