package controller.fileIO;

import model.ComparisonFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Created by Donghwan on 5/12/2016.
 * @deprecated EditorPaneFXController로 파일IO를 수행합니다.
 * 프로그램에서 비교할 파일을 읽어오는 객체
 */
public class ComparisonFileReader {

	private static final Charset CHARSET = StandardCharsets.UTF_8;

	public ComparisonFile readComparisonFile(File source) throws IOException{
		StringBuffer content = readFile(source);
		return new ComparisonFile(source, content);
	}

	// TODO private로 전환
    public StringBuffer readFile(File source) throws IOException {
		StringBuffer content = null;
    	try (BufferedReader reader = Files.newBufferedReader(source.toPath(), CHARSET)) {
			content = new StringBuffer();

			String line = null;
			while((line = reader.readLine()) != null) {
				content.append(line);
				content.append("\n");
			}
			content.deleteCharAt(content.length()-1);
		}
        return content;
    }
}
