/*
Definitive Guide to Swing for Java 2, Second Edition
By John Zukowski     
ISBN: 1-893115-78-X
Publisher: APress
*/

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.ElementIterator;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

public class ElementIteratorExample {

  public static void main(String args[]) throws Exception {

    if (args.length != 1) {
      System.err.println("Usage: java ElementIteratorExample input-URL");
    }

    // Load HTML file synchronously
    URL url = new URL(args[0]);
    URLConnection connection = url.openConnection();
    InputStream is = connection.getInputStream();
    InputStreamReader isr = new InputStreamReader(is);
    BufferedReader br = new BufferedReader(isr);

    HTMLEditorKit htmlKit = new HTMLEditorKit();
    HTMLDocument htmlDoc = (HTMLDocument) htmlKit.createDefaultDocument();
    HTMLEditorKit.Parser parser = new ParserDelegator();
    HTMLEditorKit.ParserCallback callback = htmlDoc.getReader(0);
    parser.parse(br, callback, true);

    // Parse
    ElementIterator iterator = new ElementIterator(htmlDoc);
    Element element;
    while ((element = iterator.next()) != null) {
      AttributeSet attributes = element.getAttributes();
      Object name = attributes.getAttribute(StyleConstants.NameAttribute);
      if (name instanceof HTML.Tag && (name == HTML.Tag.IMG)) {
    	 String imgSrc = (String) attributes.getAttribute(HTML.Attribute.SRC);     
    	 if (imgSrc != null) {
    		 System.out.println(imgSrc);
    	 }
    	  // Build up content text as it may be within multiple elements
       /*
    	 StringBuffer text = new StringBuffer();
        int count = element.getElementCount();
        for (int i = 0; i < count; i++) {
          Element child = element.getElement(i);
          AttributeSet childAttributes = child.getAttributes();
          if (childAttributes
              .getAttribute(StyleConstants.NameAttribute) == HTML.Tag.CONTENT) {
            int startOffset = child.getStartOffset();
            int endOffset = child.getEndOffset();
            int length = endOffset - startOffset;
            text.append(htmlDoc.getText(startOffset, length));
          }
        }
        
        System.out.println(name + ": " + text.toString());
        */
      }
    }
    System.exit(0);
  }
}
