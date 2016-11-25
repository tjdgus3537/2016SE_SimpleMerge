package model.fileIO;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.*;

/**
 * Created by parkk on 2016-11-25.
 */
public class DOCParser implements DocumentExtensionBasedContentParser{

    @Override
    public String parseContent(File source) throws IOException{
        Parser parser = new OOXMLParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext pcontext = new ParseContext();
        try
                (InputStream is = new FileInputStream(source))
        {
            parser.parse(is, handler, metadata, pcontext);
            return handler.toString();
        }catch(SAXException | TikaException te){
            te.printStackTrace();
        }
        return "";
    }
}
