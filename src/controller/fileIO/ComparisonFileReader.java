package controller.fileIO;

import model.ComparisonFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.StringJoiner;
import java.util.stream.Collector;

/**
 * Created by Donghwan on 5/12/2016.
 * 프로그램에서 비교할 파일을 읽어오는 객체
 */
public class ComparisonFileReader {

	private static final Charset CHARSET = StandardCharsets.UTF_8;

	public static ComparisonFile readComparisonFile(File source) throws IOException{
		return new ComparisonFile(source, readFile(source));
	}

    private static String readFile(File source) throws IOException {
    	try (BufferedReader reader = Files.newBufferedReader(source.toPath(), CHARSET)) {
			return reader.lines().collect(Collector.of(
					()->new StringJoiner("\n"),
					StringJoiner::add,
					StringJoiner::merge,
					StringJoiner::toString));
		}
    }
}
