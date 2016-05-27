package model.fileIO;

import model.fileIO.file.ObservableComparisonFile;
import model.fileIO.file.ObservableComparisonFileInterface;

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
	private final Charset charset;
	private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

	public ComparisonFileReader(){
		charset = DEFAULT_CHARSET;
	}

	public ComparisonFileReader(Charset charset){
		this.charset = charset;
	}

	// TODO 나중에 이름 수정해야 함
	public ObservableComparisonFileInterface readComparisonFile(File source) throws IOException{
		return new ObservableComparisonFile(source, readFile(source));
	}

    private String readFile(File source) throws IOException {
    	try (BufferedReader reader = Files.newBufferedReader(source.toPath(), charset)) {
			return reader.lines().collect(Collector.of(
					()->new StringJoiner("\n"),
					StringJoiner::add,
					StringJoiner::merge,
					StringJoiner::toString));
		}
    }
}
