package model.fileIO.file;

import model.fileIO.DocumentExtensionBasedContentParser;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;

import java.io.*;

/**
 * Created by parkk on 2016-11-25.
 */
public class DOCParser implements DocumentExtensionBasedContentParser{

    @Override
    public String parseContent(String rawContent) {
        String info = "";
        try {
            //detecting the file type
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            FileInputStream inputstream = null;

                inputstream = new FileInputStream(new File(rawContent));

            ParseContext pcontext = new ParseContext();

            info = handler.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return info;
    }
}
