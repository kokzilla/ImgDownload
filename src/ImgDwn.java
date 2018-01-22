
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.text.Element;
import javax.swing.text.ElementIterator;
import javax.swing.text.StyleConstants;
import javax.imageio.ImageIO;
import javax.swing.text.AttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ImgDwn {

	// -- taoboa's image source class
	private static java.lang.Object data_src = "data-src";
	private static java.lang.Object data_src_tmall = "src";

	class PsItem{
		String id;
		String url;
		float price;		
	}
	
	public static void main(String[] args) throws Exception {

		// -- read source file to arrays
		
		String path = args[0];
		File file = new File(path);
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		int tab1Index;
		String id;
		String url;
		
		while ((line = br.readLine()) != null) {
			tab1Index = line.indexOf("\t");
			id = line.substring(0, tab1Index).trim();
			url = line.substring(tab1Index).trim();
			
			findImages(id, url);
		}
		
		System.out.println("Program Close.");
	}

	private static void findImages(String imgID, String webUrl) throws Exception {

		URL url = new URL(webUrl);
		URLConnection connection = url.openConnection();
		InputStream is = connection.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		HTMLEditorKit htmlKit = new HTMLEditorKit();
		HTMLDocument htmlDoc = (HTMLDocument) htmlKit.createDefaultDocument();
		HTMLEditorKit.Parser parser = new ParserDelegator();
		HTMLEditorKit.ParserCallback callback = htmlDoc.getReader(0);
		parser.parse(br, callback, true);

		ElementIterator iterator = new ElementIterator(htmlDoc);
		Element element;
		int running = 0;
		int found = 0;

		while ((element = iterator.next()) != null) {
			

			AttributeSet attributes = element.getAttributes();
			Object name = attributes.getAttribute(StyleConstants.NameAttribute);
			System.out.println(name);

			if (name instanceof HTML.Tag && (name == HTML.Tag.IMG)) {

				String imgSrc = (String) attributes.getAttribute(HTML.Attribute.SRC);
//				String imgSrc = (String) attributes.getAttribute(data_src);

				if (imgSrc != null) {
					running++;
					Object key;

					imgSrc = trimString(imgSrc);

					 System.out.println(imgSrc);

					try {
						downloadImage(imgID, imgSrc, running);

					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					} finally {
						found++;
					}
				}
			}
		}

		System.out.println("ID : " + imgID + " images found : " + found);
	}

	private static String trimString(String imgSrc) {

		String[] imgExt = new String[4];

		imgExt[0] = ".jpg";
		imgExt[1] = ".jpeg";
		imgExt[2] = ".png";
		imgExt[3] = ".bmp";

		int intIndex = 0;

		// -- default extension and it's index
		int minIndex = imgSrc.lastIndexOf(".");
		String extension = imgSrc.substring(minIndex, imgSrc.length());

		// find actual image extension (first occurrence)
		for (int i = 0; i < 4; i++) {
			// -- find possible image extension and it's index in URL
			int temp = imgSrc.indexOf(imgExt[i]);
			if (temp != -1 && temp < minIndex) {
				minIndex = temp;
				extension = imgSrc.substring(minIndex, minIndex + imgExt[i].length());
			}
		}

		imgSrc = imgSrc.substring(0, minIndex + extension.length());

		return "https:" + imgSrc;
	}

	private static void downloadImage(String imgID, String imgSrc, int running) throws IOException {
		BufferedImage image = null;
		try {

			String imgPath = null;
			imgPath = "D:/TNJ/" + imgID + "_" + running + ".jpg";
			URL imageUrl = new URL(imgSrc);

			InputStream inputStream = null;
			OutputStream outputStream = null;

			inputStream = imageUrl.openStream();
			outputStream = new FileOutputStream(imgPath);

			byte[] buffer = new byte[2048];
			int length;

			while ((length = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, length);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
