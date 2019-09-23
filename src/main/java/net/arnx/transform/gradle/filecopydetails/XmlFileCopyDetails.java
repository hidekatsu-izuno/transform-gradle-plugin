package net.arnx.transform.gradle.filecopydetails;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.gradle.api.UncheckedIOException;
import org.gradle.api.file.FileCopyDetails;
import org.xml.sax.SAXException;

import groovy.util.Node;
import groovy.util.XmlParser;

public class XmlFileCopyDetails extends AbstractFileCopyDetails {

    public XmlFileCopyDetails(FileCopyDetails parent) {
        super(parent);
    }

    public Node getDocument() throws SAXException {
        try {
            return new XmlParser().parse(getFile());
        } catch (ParserConfigurationException e) {
            throw new IllegalStateException(e);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
}