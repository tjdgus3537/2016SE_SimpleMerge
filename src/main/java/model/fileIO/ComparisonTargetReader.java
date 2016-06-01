package model.fileIO;

import model.fileIO.file.ComparisonTargetInterface;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.StringJoiner;
import java.util.stream.Collector;
import org.mozilla.universalchardet.UniversalDetector;

/**
 * Created by Donghwan on 5/12/2016.
 * 프로그램에서 비교할 파일을 읽어오는 객체
 */
public class ComparisonTargetReader {
	public void readComparisonFile(File source, ComparisonTargetInterface destination) throws IOException{
		destination.setSource(source);
		Charset charset = detectEncoding(source);
		destination.setEncoding(charset);
		destination.setContent(readFile(source, charset));
	}

	private Charset detectEncoding(File source) throws IOException{
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
				System.out.println("Detected encoding = " + encoding);
				return Charset.forName(encoding);
			} else {
				System.out.println("No encoding detected.");
				String osName = System.getProperty("os.name");
				if(osName.startsWith("Windows")) return Charset.forName("x-windows-949");
				else return StandardCharsets.UTF_8;
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
