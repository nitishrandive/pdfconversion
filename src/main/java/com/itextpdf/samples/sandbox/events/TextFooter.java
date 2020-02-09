/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2020 iText Group NV
    Authors: iText Software.
 
    For more information, please contact iText Software at this address:
    sales@itextpdf.com
 */
/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/27780756/adding-footer-with-itext-doesnt-work
 */
package com.itextpdf.samples.sandbox.events;
 
import com.itextpdf.forms.PdfPageFormCopier;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.property.TextAlignment;
 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jsoup.Jsoup;
 
public class TextFooter {
    public static final String DEST = "1.pdf";
    		//"./target/sandbox/events/text_footer.pdf";
 
    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
       // file.getParentFile().mkdirs();
 
        new TextFooter().manipulatePdf(DEST);
    }
 
    protected void manipulatePdf(String dest) throws Exception {
    	
    	
    String	fileName = "C:\\Users\\Admin\\Downloads\\html-pdf-xml-master\\MainPage.html";
		String secFileName = "C:\\Users\\Admin\\Downloads\\html-pdf-xml-master\\ContentPage.html"; 
		
		try {
			org.jsoup.nodes.Document doc = Jsoup.parse(new File(fileName), "UTF-8");
			
			org.jsoup.nodes.Element elem = doc.getElementsByTag("th").get(0);
			
			System.out.println("The first th value: " + elem.text());
			
			elem.text("Subject: " + "This is Test subject");
			
			System.out.println("After modification: " + elem.text());
			
			//Lets modify this and print
			doc.getElementsByTag("th").get(0).text("Subject: " + "subject");
			doc.getElementsByTag("th").get(1).text("Bulletin: " + "bulletin");
			doc.getElementsByTag("th").get(2).text("Last Issued: " + "issueDate");
			//doc.getElementsByTag("article").get(0).html("<h3>" + bodyList.get(0).toString() + "</h3>");
			
			//System.out.println("The outer html:");
			//System.out.println(doc.outerHtml());
			 PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
		       
		 
		        
		        ConverterProperties props = new ConverterProperties();
		      //  FontProvider dfp = new DefaultFontProvider(true, false, false);
		        //dfp.addFont("/path/to/MyFont.ttf");
		        //props.setFontProvider(dfp);
			HtmlConverter.convertToDocument(doc.outerHtml(), pdfDoc, props);
			 Document doc2 = new Document(pdfDoc);
		        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new TextFooterEventHandler(doc2));
		 
		        for (int i = 0; i < 3; i++) {
		            doc2.add(new Paragraph("Test " + (i + 1)));
		            if (i != 2) {
		                doc2.add(new AreaBreak());
		            }
		        }
		        doc2.close();
			//HtmlConverter.convertToPdf(doc.outerHtml(), new FileOutputStream("1.pdf"));
			
			doc = Jsoup.parse(new File(secFileName), "UTF-8");
			doc.getElementsByTag("th").get(0).text("Subject: " + "subject");
			doc.getElementsByTag("th").get(1).text("Bulletin: " + "bulletin");
			//doc.getElementsByTag("article").get(0).html("<h3>" + bodyList.get(1).toString() + "</h3>");			
			
			
			HtmlConverter.convertToPdf(doc.outerHtml(), new FileOutputStream("2.pdf"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
File mergeFile = new File("merge.pdf");
		
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
    /*	
    	
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc);
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new TextFooterEventHandler(doc));
 
        for (int i = 0; i < 3; i++) {
            doc.add(new Paragraph("Test " + (i + 1)));
            if (i != 2) {
                doc.add(new AreaBreak());
            }
        }
 
        doc.close();*/
    }
 
 
    private static class TextFooterEventHandler implements IEventHandler {
        protected Document doc;
 
        public TextFooterEventHandler(Document doc) {
            this.doc = doc;
        }
 
        @Override
        public void handleEvent(Event currentEvent) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) currentEvent;
            Rectangle pageSize = docEvent.getPage().getPageSize();
            PdfFont font = null;
            try {
                font = PdfFontFactory.createFont(StandardFonts.HELVETICA_OBLIQUE);
            } catch (IOException e) {
 
                // Such an exception isn't expected to occur,
                // because helvetica is one of standard fonts
                System.err.println(e.getMessage());
            }
 
            float coordX = ((pageSize.getLeft() + doc.getLeftMargin())
                    + (pageSize.getRight() - doc.getRightMargin())) / 2;
            float headerY = pageSize.getTop() - doc.getTopMargin() + 10;
            float footerY = doc.getBottomMargin();
            Canvas canvas = new Canvas(docEvent.getPage(), pageSize);
            canvas
 
                    // If the exception has been thrown, the font variable is not initialized.
                    // Therefore null will be set and iText will use the default font - Helvetica
                    .setFont(font)
                    .setFontSize(5)
                    .showTextAligned("this is a header", coordX, headerY, TextAlignment.CENTER)
                    .showTextAligned("this is a footer", coordX, footerY, TextAlignment.CENTER)
                    .close();
        }
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
    
    
}
