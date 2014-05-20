package edu.yonsei.text_process;

import java.io.InputStream;
import java.net.URL;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.LinkContentHandler;
import org.apache.tika.sax.TeeContentHandler;
import org.apache.tika.sax.ToHTMLContentHandler;
import org.xml.sax.ContentHandler;

public class SimpleHTMLParser {

    public static void main (String args[]) throws Exception {
        URL url = new URL("http://nar.oxfordjournals.org/content/38/21/7587.1");
        InputStream input = url.openStream();
        LinkContentHandler linkHandler = new LinkContentHandler();
        ContentHandler textHandler = new BodyContentHandler();
        ToHTMLContentHandler toHTMLHandler = new ToHTMLContentHandler();
        TeeContentHandler teeHandler = new TeeContentHandler(linkHandler, textHandler, toHTMLHandler);
        Metadata metadata = new Metadata();
        ParseContext parseContext = new ParseContext();
        HtmlParser parser = new HtmlParser();
        parser.parse(input, teeHandler, metadata, parseContext);
        System.out.println("title:\n" + metadata.get("title"));
        System.out.println("links:\n" + linkHandler.getLinks());
        System.out.println("text:\n" + textHandler.toString());
        System.out.println("html:\n" + toHTMLHandler.toString());
    }
}