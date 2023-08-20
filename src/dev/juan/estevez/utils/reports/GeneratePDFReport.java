package dev.juan.estevez.utils.reports;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
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

import dev.juan.estevez.controllers.UserController;
import dev.juan.estevez.models.User;
import dev.juan.estevez.persistence.UserDAO;
import dev.juan.estevez.utils.Constants;
import dev.juan.estevez.utils.StringUtils;
import dev.juan.estevez.utils.enums.Users;

public class GeneratePDFReport {

	/**
	 * Creates a PDF file with the given output path.
	 *
	 * @param outputPath the path where the PDF file will be created
	 * @throws IOException       if there is an error in the input or output stream
	 * @throws DocumentException if there is an error in the PDF document
	 */
	public static void createPDF(String outputPath) throws IOException, DocumentException {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(outputPath));
		document.open();

		addHeader(document);
		addTitle(document, Constants.USER_LIST);
		addUserDataTable(document);

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
	 * Adds a user data table to the specified document.
	 *
	 * @param document the document to add the table to
	 * @throws DocumentException if there is an error adding the table to the
	 *                           document
	 */
	private static void addUserDataTable(Document document) throws DocumentException {
		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100);
		table.setWidths(new float[] { 2, 3, 2, 1.5f, 1.5f, 3 });
		table.addCell(Users.NAME.getValue());
		table.addCell(Users.EMAIL.getValue());
		table.addCell(Users.PHONE.getValue());
		table.addCell(Users.LEVEL.getValue());
		table.addCell(Users.STATUS.getValue());
		table.addCell(Users.REGISTERED_BY.getValue());

		try {
			List<User> users =  new UserController(new UserDAO()).getAllUsers();

			users.forEach(user -> {
				table.addCell(user.getUserName());
				table.addCell(user.getUserEmail());
				table.addCell(user.getUserPhone());
				table.addCell(user.getLevelType());
				table.addCell(user.getStatus());
				table.addCell(user.getRegisterBy());
			});
			
			document.add(table);
		} catch (SQLException ex) {
			StringUtils.handleQueryError(ex, Constants.ERROR_GENERATING_USER_LIST);
		}
	}

}
