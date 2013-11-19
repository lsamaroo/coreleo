/*
 * XMLDocument.java
 *
 * Created on July 28, 2002, 5:42 PM
 */

package com.coreleo.util.xml;

import org.w3c.dom.Document;

/**
 * 
 * This interface is used by classes that wish to generate and write XML content
 * to a file. 

 * 
 * @see <code>XMLReaderWriter.writeXMLToFile</code>
 * 
 * @author Leon Samaroo
 * 
 */
public interface XMLDocument {
	public void generateXMLDocument(Document document);
}
