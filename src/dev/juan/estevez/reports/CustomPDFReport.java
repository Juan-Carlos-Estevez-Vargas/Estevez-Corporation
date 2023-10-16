package dev.juan.estevez.reports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.DocumentException;

import dev.juan.estevez.enums.Users;
import dev.juan.estevez.models.User;
import dev.juan.estevez.utils.Constants;

/**
 * @author Juan Carlos Estevez Vargas
 */
public class CustomPDFReport {

    /**
     * Generates a PDF report for a list of users and saves it to the specified
     * output path.
     *
     * @param users      the list of users
     * @param outputPath the path where the PDF report will be saved
     * @throws IOException       if there is an error in the I/O operation
     * @throws DocumentException if there is an error in the PDF document generation
     */
    public static void generateUserPDFReport(List<User> users, String outputPath)
            throws IOException, DocumentException {
        String[] userHeaders = {
                Users.NAME.getValue(),
                Users.EMAIL.getValue(),
                Users.PHONE.getValue(),
                Users.LEVEL.getValue(),
                Users.STATUS.getValue(),
                Users.REGISTERED_BY.getValue()
        };

        List<String[]> userData = new ArrayList<>();

        for (User user : users) {
            String[] userRow = {
                    user.getUserName(),
                    user.getUserEmail(),
                    user.getUserPhone(),
                    user.getLevelType(),
                    user.getStatus(),
                    user.getRegisterBy()
            };
            userData.add(userRow);
        }

        GeneratePDFReport.generatePDFReport(userData, userHeaders, Constants.USER_LIST, outputPath);
    }

}
