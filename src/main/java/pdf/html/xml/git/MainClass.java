package pdf.html.xml.git;

import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.itextpdf.forms.PdfPageFormCopier;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Image;

//import pdf.html.Documenter;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String fileName, subject = "", bulletin = "", issueDate = "", footer = "";
		List<String> bodyList = new ArrayList<String>();
		
		//C:\Ramkrishna\projects\xml-html-parser-poc\TestReadFile.xml
//		C:\Users\Admin\Downloads\html-pdf-xml-master\TestReadFile.xml
		System.out.println("tEST");
		/*Scanner s = new Scanner(System.in);
	
		fileName = s.nextLine();
		*/
		fileName= "C:\\Users\\Admin\\Downloads\\html-pdf-xml-master\\TestReadFile.xml";
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			Document document = builder.parse(new File(fileName));
			
			//List<Documenter> docList = new ArrayList<Documenter>();
			
//			Documenter docu;
			//NodeList nodeList = document.getDocumentElement().getChildNodes();
			
			document.getDocumentElement().normalize();
			
			System.out.println("Root element :" + document.getDocumentElement().getNodeName());
			
			NodeList nlist = document.getElementsByTagName("Subject");			
			System.out.println(nlist.getLength());			
			Node nNode = nlist.item(0);			
			System.out.println("\nSubject Element :" + nNode.getNodeName());			
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {				
				Element ele = (Element) nNode;				
				System.out.println("\nCurrent Element Content: " + ele.getTextContent());
				subject = ele.getTextContent();
			}
			
			nlist = document.getElementsByTagName("Bulletin");			
			System.out.println(nlist.getLength());			
			nNode = nlist.item(0);			
			System.out.println("Bulletin Element :" + nNode.getNodeName());			
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {				
				Element ele = (Element) nNode;				
				System.out.println("\nBulletin Element Content: " + ele.getTextContent());
				bulletin = ele.getTextContent();
			}
			
			nlist = document.getElementsByTagName("LastIssued");			
			System.out.println(nlist.getLength());			
			nNode = nlist.item(0);
			System.out.println("LastIssued Element :" + nNode.getNodeName());			
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {				
				Element ele = (Element) nNode;				
				System.out.println("\nLastIssued Element Content: " + ele.getTextContent());
				issueDate = ele.getTextContent();
			}
			
			nlist = document.getElementsByTagName("footer");			
			System.out.println(nlist.getLength());			
			nNode = nlist.item(0);			
			System.out.println("footer Element :" + nNode.getNodeName());			
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {				
				Element ele = (Element) nNode;				
				System.out.println("\nfooter Element Content: " + ele.getTextContent());
				footer = ele.getTextContent();
			}
			
			nlist = document.getElementsByTagName("body");
			
			for(int i =0; i< nlist.getLength(); i++) {
				
				Node tnode = nlist.item(i);
				
				System.out.println("Current node element: " + tnode.getNodeName());
				
				if(tnode.getNodeType() == Node.ELEMENT_NODE) {
					
					 Element ele = (Element) tnode;
					 
					 System.out.println("Current node id: " + ele.getAttribute("id"));
					 System.out.println("Current node text: " + ele.getTextContent());
					 bodyList.add(ele.getTextContent());
				}
			}
			/*
			for(int i=0;i<nodeList.getLength(); i++) {
				
				Node node = nodeList.item(i);
				
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					
					Element elem = (Element) node;
					
					subject = elem.getElementsByTagName("Subject").item(0).getChildNodes().item(0).getNodeValue();
				}
				
			} */
			
			//System.out.println(nodeList.getLength());\
			
			
			
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//C:\Users\Admin\Downloads\html-pdf-xml-master\TestReadFile.xml
		/*fileName = "C:\\Ramkrishna\\projects\\xml-html-parser-poc\\MainPage.html";
		String secFileName = "C:\\Ramkrishna\\projects\\xml-html-parser-poc\\ContentPage.html"; 
		*/
		//fileName = "C:\\Users\\Admin\\Downloads\\html-pdf-xml-master\\MainPage.html";
		 fileName = "C:\\Users\\Admin\\Downloads\\iText_Sample_Template\\SI_template_fr_CA_Header.html";
		String secFileName = "C:\\Users\\Admin\\Downloads\\iText_Sample_Template\\SI_template_fr_CA_Footer.html"; 
		
		try {
			org.jsoup.nodes.Document doc = Jsoup.parse(new File(fileName), "UTF-8");
			
			org.jsoup.nodes.Element elem = doc.getElementsByTag("th").get(0);
			
			System.out.println("The first th value: " + elem.text());
			
			elem.text("Subject: " + "This is Test subject");
			
			System.out.println("After modification: " + elem.text());
			
			//Lets modify this and print
			doc.getElementsByTag("th").get(0).text("Subject: " + subject);
			/*doc.getElementsByTag("th").get(1).text("Bulletin: " + bulletin);
			doc.getElementsByTag("th").get(2).text("Last Issued: " + issueDate);
		/*	doc.getElementsByTag("article").get(0).html("<h3>" + bodyList.get(0).toString() + "</h3>");
			*/
			//System.out.println("The outer html:");
			//System.out.println(doc.outerHtml());
			int width = 1000, height = 450;
			BufferedImage image = GraphicsEnvironment.getLocalGraphicsEnvironment()
			        .getDefaultScreenDevice().getDefaultConfiguration()
			        .createCompatibleImage(width, height);

			    Graphics graphics = image.createGraphics();
			    String html =doc.outerHtml().toString();
			    JEditorPane jep = new JEditorPane("text/html",html);
			    jep.setSize(width, height);
			    jep.print(graphics);

			    ImageIO.write(image, "png", new File("SI_template_fr_CA_Header.jpg"));	
			//HtmlConverter.convertToDocument(doc.outerHtml(), pdfDocument, converterProperties)
			HtmlConverter.convertToPdf(doc.outerHtml(), new FileOutputStream("1.pdf"));
			
			
			
org.jsoup.nodes.Document doc2 = Jsoup.parse(new File(secFileName), "UTF-8");
			
			//org.jsoup.nodes.Element elem2 = doc2.getElementsByTag("th").get(0);
			
			//System.out.println("The first th value: " + elem2.text());
			
			//elem2.text("Subject: " + "This is Test subject");
			
		//	System.out.println("After modification: " + elem.text());
			
			int width1 = 800, height1 = 200;
			BufferedImage image1 = GraphicsEnvironment.getLocalGraphicsEnvironment()
			        .getDefaultScreenDevice().getDefaultConfiguration()
			        .createCompatibleImage(width1, height1);

			    Graphics graphics1 = image1.createGraphics();
			    String html1 =doc2.outerHtml().toString();
			    JEditorPane jep1 = new JEditorPane("text/html",html1);
			    jep1.setSize(width1, height1);
			    jep1.print(graphics1);

			    ImageIO.write(image1, "png", new File("SI_template_fr_CA_Footer.png"));
			
			
			
			PdfDocument pdfDocop = new PdfDocument(new PdfWriter("outputfile.pdf"));
			 
			
			 com.itextpdf.layout.Document docs = new com.itextpdf.layout.Document(pdfDocop);
			 
			    VariableHeaderEventHandler handler = new VariableHeaderEventHandler();
			    
			    Rectangle headerBox = new Rectangle(36, 54, 559, 788);
			PdfDocument pdfDoc = new PdfDocument(new PdfReader("merge.pdf"));
			PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
			pdfDocop.addFont(font);
		    int numberOfPdfObjects = pdfDoc.getNumberOfPdfObjects();
		    	try{
		 		 List<PdfPage> obj = pdfDoc.copyPagesTo(1,pdfDoc.getNumberOfPages(), pdfDocop);
				
		        pdfDocop.addEventHandler(PdfDocumentEvent.END_PAGE, (IEventHandler) handler);
		        
		 	    handler.setHeader(String.format("This is header Text dkjfahk adjflkdnf kandf kjdnf kjndfkkjdan kdnf kj ndkfn dkjfndanfkjsanfkdsanfkndsakj ndnfkjndsaf kndsa kndsk nfkdsanf kndak nflkdsa nfkda kjnd kneakfnsakndask fnds nfkjsadn fkdsan kdsfnkdsanf ndsakjdsnf nf kndfkj ndsf ndaf kdsa nfkdsa nfl dsnflkndsa l dnafk nldsa jnfdaln"));
		             
		    	}catch(Exception e){
		    		e.printStackTrace();
		    	}
		 
		    docs.close();
			
			
			
			
			
			
			
			
		/*	doc = Jsoup.parse(new File(secFileName), "UTF-8");
			doc.getElementsByTag("th").get(0).text("Subject: " + subject);
			doc.getElementsByTag("th").get(1).text("Bulletin: " + bulletin);
			doc.getElementsByTag("article").get(0).html("<h3>" + bodyList.get(1).toString() + "</h3>");			
			
			
			HtmlConverter.convertToPdf(doc.outerHtml(), new FileOutputStream("2.pdf"));*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*File mergeFile = new File("merge.pdf");
		
		try {
			PdfReader[] readArray = {
					new PdfReader("1.pdf"),
					new PdfReader("2.pdf")				
			};
			
			mergeForms("merge.pdf", readArray);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		

	}
	
	public static void mergeForms(String dest, PdfReader[] readers) throws FileNotFoundException {
		
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
		
		pdfDoc.initializeOutlines();
		
		PdfPageFormCopier copier = new PdfPageFormCopier();
		
		for(PdfReader reader: readers) {
			
			PdfDocument readerDoc = new PdfDocument(reader);
			readerDoc.copyPagesTo(1,readerDoc.getNumberOfPages(), pdfDoc, copier);
			
			readerDoc.close();
		}
		
		pdfDoc.close();
		
	}

	
	private static  class VariableHeaderEventHandler implements IEventHandler {
	    protected String header;

	    public void setHeader(String header) {
	        this.header = header;
	    }


	    
	    @Override
	    public void handleEvent(Event currentEvent) {
	        PdfDocumentEvent documentEvent = (PdfDocumentEvent) currentEvent;
	        PdfPage page = documentEvent.getPage();
	        ImageData imageData = null;
			try {
				imageData = ImageDataFactory.create("SI_template_fr_CA_Header.jpg");
			} catch (MalformedURLException e) {
			
				e.printStackTrace();
			}
	        Image pdfImg = new Image(imageData);
	        
	        new Canvas(page, page.getPageSize()).add(pdfImg);//.setRelativePosition(36, 788, 599, 36).close();
		    
	        PdfDocument pdfDoc = documentEvent.getDocument();

            PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
            ImageData imageDataFooter = null;
			try {
				imageDataFooter = ImageDataFactory.create("SI_template_fr_CA_Footer.png");
			} catch (MalformedURLException e) {

				e.printStackTrace();
			}
		    Image pdfFooterImg = new Image(imageDataFooter);
            new Canvas(canvas, pdfDoc, new Rectangle(36, 20, page.getPageSize().getWidth() - 72, 50))
                    .add(pdfFooterImg)
                    .close();
        }
	        
		
	}	
	
}
