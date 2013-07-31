package utils;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;

import model.FileReport;

import org.jfree.chart.JFreeChart;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class Test {
	
	  private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
		      Font.BOLD);
		  private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
		      Font.NORMAL, BaseColor.RED);
		  private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
		      Font.BOLD);
		  private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
		      Font.BOLD);
	
//	public static void main(String[] args) {
//	    writeChartToPDF(PieChartDemo.generatePieChart(null), 500, 400, "WebContent\\pdf\\piechart.pdf");
//	}
	
	 
	
	 private static void addEmptyLine(Paragraph paragraph, int number) {
		    for (int i = 0; i < number; i++) {
		      paragraph.add(new Paragraph(" "));
		    }
		  }
	public File  writeChartToPDF(JFreeChart chart, int width, int height, String fileName,ArrayList<FileReport> reports,JFreeChart chart1) {
	    PdfWriter writer = null;
	    File file=null;
	    Document document = new Document();
	 
	    try {

			file = File.createTempFile("hello", "test");
			OutputStream outputStream = new FileOutputStream(file);
			//PdfWriter.getInstance(receipt, outputStream);
			
	        writer = PdfWriter.getInstance(document, outputStream);
	        document.open();
	        document.addTitle("My first PDF");
	        document.addSubject("Using iText");
	        document.addKeywords("Java, PDF, iText");
	        document.addAuthor("Lars Vogel");
	        document.addCreator("Lars Vogel");
	        
	        Paragraph preface = new Paragraph();
	        preface.add(new Paragraph("Report generated by: " + System.getProperty("user.name") + ", " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		            smallBold));
	        addEmptyLine(preface, 1);
	        preface.add(new Paragraph("This document describes something which is very important ",
	            redFont));
	        // We add one empty line
	        addEmptyLine(preface, 1);
	        // Lets write a big header
	        preface.add(new Paragraph("File Report", catFont));
	        preface.add(new Paragraph("File Name: "+reports.get(0).getFileName(),
		            smallBold));
	        preface.add(new Paragraph("File Owner: "+reports.get(0).getAccountID(),
		            smallBold));
	        
	        addEmptyLine(preface, 1);
	        // Will create: Report generated by: _name, _date
	       
	        


	        preface.add(new Paragraph("User's who accessed "+reports.get(0).getFileName(),
	            redFont));
	        addEmptyLine(preface, 1);
	        document.add(preface);
	        
	        PdfPTable table = new PdfPTable(6);

	        // t.setBorderColor(BaseColor.GRAY);
	        // t.setPadding(4);
	        // t.setSpacing(4);
	        // t.setBorderWidth(1);
	        

	        PdfPCell c1 = new PdfPCell(new Phrase("User"));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(c1);

	        c1 = new PdfPCell(new Phrase("IPAddress"));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(c1);
	        	
	        c1 = new PdfPCell(new Phrase("File Owner"));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(c1);
	        
	        c1 = new PdfPCell(new Phrase("Status"));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(c1);
	        table.setHeaderRows(1);
	        
	        c1 = new PdfPCell(new Phrase("Access Date"));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(c1);
	        table.setHeaderRows(1);
	        
	        c1 = new PdfPCell(new Phrase("Access Time"));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(c1);
	        table.setHeaderRows(1);
	        
	        for(int i=0;i<reports.size();i++)
	        {
	        	table.addCell(reports.get(i).getUserName());
	        	table.addCell(reports.get(i).getIPAddress());
	        	table.addCell(reports.get(i).getAccountID());
	        	table.addCell(reports.get(i).getStatus());
	        	table.addCell(reports.get(i).getDownloadedDate().toString());
	        	table.addCell(reports.get(i).getDownloadedTime());
	        }
	        	          
	        
	        document.add(table);		

	       
	        
	        PdfContentByte contentByte = writer.getDirectContent();
	        PdfTemplate template = contentByte.createTemplate(width, height);
	        Graphics2D graphics2d = template.createGraphics(width, height,
	                new DefaultFontMapper());
	        Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width,
	                height);
	 
	        chart.draw(graphics2d, rectangle2d);
	         
	        graphics2d.dispose();
	        contentByte.addTemplate(template, 0, 0);
	        
	        
	        PdfContentByte contentByte1 = writer.getDirectContent();
	        PdfTemplate template1 = contentByte1.createTemplate(width, height);
	        Graphics2D graphics2d1 = template1.createGraphics(width, height,
	                new DefaultFontMapper());
	        Rectangle2D rectangle2d1 = new Rectangle2D.Double(0, 0, width,
	                height);
	 
	        chart1.draw(graphics2d1, rectangle2d1);
	         
	        graphics2d1.dispose();
	        contentByte1.addTemplate(template1, 0, 0);
	        
	        
	 
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    document.close();
	    
	    return file;
	}

	
}
