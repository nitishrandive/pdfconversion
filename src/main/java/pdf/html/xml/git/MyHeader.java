package pdf.html.xml.git;


import java.io.File;

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
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfResources;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

class MyHeader  {
	
public static void main(String[] args) throws IOException {
	
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
				imageDataFooter = ImageDataFactory.create("SI_template_fr_CA_Footer.jpg");
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