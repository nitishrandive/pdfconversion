package pdf.html.xml.git;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
//from  w  w w.  java  2  s . c o m
import javax.imageio.ImageIO;
import javax.swing.JEditorPane;

import org.jsoup.Jsoup;

import com.itextpdf.html2pdf.HtmlConverter;

public class Main {

  public static void main(String[] args) throws Exception {
//    String html = "<h1>Hello, world.</h1>";
    int width = 1000, height = 500;
    String fileName = "C:\\Users\\Admin\\Downloads\\iText_Sample_Template\\SI_template_fr_CA_Header.html";
    org.jsoup.nodes.Document doc = Jsoup.parse(new File(fileName), "UTF-8");
	
	org.jsoup.nodes.Element elem = doc.getElementsByTag("th").get(0);
	
	System.out.println("The first th value: " + elem.text());
	
	elem.text("Subject: " + "This is Test subject");
	
	System.out.println("After modification: " + elem.text());
	
	//Lets modify this and print
	/*doc.getElementsByTag("th").get(0).text("Subject: " + subject);
	doc.getElementsByTag("th").get(1).text("Bulletin: " + bulletin);
	doc.getElementsByTag("th").get(2).text("Last Issued: " + issueDate);
	doc.getElementsByTag("article").get(0).html("<h3>" + bodyList.get(0).toString() + "</h3>");*/
	
	//System.out.println("The outer html:");
	//System.out.println(doc.outerHtml());
	
	//HtmlConverter.convertToDocument(doc.outerHtml(), pdfDocument, converterProperties)
	
    
    
    BufferedImage image = GraphicsEnvironment.getLocalGraphicsEnvironment()
        .getDefaultScreenDevice().getDefaultConfiguration()
        .createCompatibleImage(width, height);

    Graphics graphics = image.createGraphics();
    String html =doc.outerHtml().toString();
    JEditorPane jep = new JEditorPane("text/html",html);
    jep.setSize(width, height);
    jep.print(graphics);

    ImageIO.write(image, "png", new File("Image.jpg"));
  }
}