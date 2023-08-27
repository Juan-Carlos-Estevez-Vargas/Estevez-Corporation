package dev.juan.estevez.reports;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dev.juan.estevez.utils.Constants;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public class GeneratePDFReport {

	public static void generatePDFReport(List<String[]> data, String[] headers, String title, String outputPath) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(outputPath));
        document.open();

		addHeader(document);
        addTitle(document, title);
        addDataTable(document, data, headers);

        document.close();
    }

	/**
	 * Adds a header to the given document.
	 *
	 * @param document the document to add the header to
	 * @throws DocumentException if there is an error with the document
	 * @throws IOException       if there is an error with the image file
	 */
	private static void addHeader(Document document) throws DocumentException, IOException {
		com.itextpdf.text.Image header = com.itextpdf.text.Image.getInstance(Constants.PDF_BANNER_IMG);
		header.scaleToFit(650, 1000);
		header.setAlignment(Element.ALIGN_CENTER);
		document.add(header);
	}

	/**
	 * Adds a title to the given document.
	 *
	 * @param document  the document to add the title to
	 * @param titleText the text of the title
	 * @throws DocumentException if there is an error adding the title to the
	 *                           document
	 */
	private static void addTitle(Document document, String titleText) throws DocumentException {
		Paragraph titleParagraph = new Paragraph(titleText,	FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.DARK_GRAY));
		titleParagraph.setAlignment(Element.ALIGN_CENTER);
		document.add(titleParagraph);
		document.add(Chunk.NEWLINE);
	}

	/**
	 * Adds a data table to the document.
	 *
	 * @param  document  the document to add the table to
	 * @param  data      the data to populate the table with
	 * @param  headers   the headers of the table columns
	 * @throws DocumentException if there is an error adding the table to the document
	 */
	private static void addDataTable(Document document, List<String[]> data, String[] headers) throws DocumentException {
        PdfPTable table = new PdfPTable(headers.length);
        table.setWidthPercentage(100);

        for (String header : headers) {
            table.addCell(header);
        }

        for (String[] rowData : data) {
            for (String value : rowData) {
                table.addCell(value);
            }
        }

        document.add(table);
    }
	
}
