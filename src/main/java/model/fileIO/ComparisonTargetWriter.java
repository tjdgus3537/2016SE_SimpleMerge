package model.fileIO;

import model.fileIO.file.ComparisonTargetInterface;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

/**
 * Created by Donghwan on 5/16/2016.
 * 프로그램에서 비교할 파일을 저장하는 파일
 */
public class ComparisonTargetWriter {

    public void write(ComparisonTargetInterface target) throws IOException{
        writeFile(target.getSource(), target.getContent(), target.getEncoding());
    }

    private void writeFile(File target, String content, Charset charset) throws IOException{
        try(BufferedWriter writer = Files.newBufferedWriter(target.toPath(),
                charset, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)){
            writer.write(content);
        }
    }
}
