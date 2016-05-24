package controller.fileIO;

import model.ComparisonFile;

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

    private static final Charset CHARSET = StandardCharsets.UTF_8;

    public static void writeComparisonFile(ComparisonFile target) throws IOException{
        writeFile(target.getSource(), target.getContent().toString());
    }

    private static void writeFile(File target, String content) throws IOException{
        try(BufferedWriter writer = Files.newBufferedWriter(target.toPath(),
                CHARSET, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)){
            writer.write(content);
        }
    }
}
