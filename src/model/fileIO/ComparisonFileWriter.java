package model.fileIO;

import model.ObservableComparisonFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

/**
 * Created by Donghwan on 5/16/2016.
 * 프로그램에서 비교할 파일을 저장하는 파일
 */
public class ComparisonFileWriter {
    private final Charset charset;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public ComparisonFileWriter(){
        charset = DEFAULT_CHARSET;
    }

    public ComparisonFileWriter(Charset charset){
        this.charset = charset;
    }

    public void writeComparisonFile(ObservableComparisonFile target) throws IOException{
        writeFile(target.getSourceProperty(), target.getContentToString());
    }

    private void writeFile(File target, String content) throws IOException{
        try(BufferedWriter writer = Files.newBufferedWriter(target.toPath(),
                charset, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)){
            writer.write(content);
        }
    }
}
