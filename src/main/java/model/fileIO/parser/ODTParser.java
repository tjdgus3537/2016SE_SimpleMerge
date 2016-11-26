package model.fileIO.parser;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.apache.tika.parser.odf.OpenDocumentParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Donghwan on 2016-11-26.
 */
public class ODTParser implements DocumentExtensionBasedContentParser {
    @Override
    public String parseContent(File source) throws IOException {
        Parser parser = new OpenDocumentParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext pcontext = new ParseContext();
        try
                (InputStream is = new FileInputStream(source)) {
            parser.parse(is, handler, metadata, pcontext);
            return handler.toString();
        } catch (SAXException | TikaException te) {
            te.printStackTrace();
        }
        return "";
    }
}