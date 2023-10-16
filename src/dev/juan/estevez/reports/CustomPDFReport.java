package dev.juan.estevez.reports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.DocumentException;

import dev.juan.estevez.enums.Clients;
import dev.juan.estevez.enums.Equipments;
import dev.juan.estevez.enums.Users;
import dev.juan.estevez.models.Client;
import dev.juan.estevez.models.Equipment;
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

    /**
     * Generates a PDF report for the given list of clients and saves it to the
     * specified output path.
     *
     * @param clients    the list of clients to include in the report
     * @param outputPath the path where the generated PDF report will be saved
     * @throws IOException       if there is an error reading or writing to the file
     * @throws DocumentException if there is an error creating the PDF document
     */
    public static void generateClientsPDFReport(List<Client> clients, String outputPath)
            throws IOException, DocumentException {
        String[] clientHeaders = {
                Clients.ID.getValue(),
                Clients.NAME.getValue(),
                Clients.EMAIL.getValue(),
                Clients.PHONE.getValue(),
                Clients.ADDRESS.getValue()
        };

        List<String[]> clientData = new ArrayList<>();

        for (Client client : clients) {
            String[] clientRow = {
                    String.valueOf(client.getClientID()),
                    client.getClientName(),
                    client.getClientEmail(),
                    client.getClientPhone(),
                    client.getClientAddress()
            };

            clientData.add(clientRow);
        }

        GeneratePDFReport.generatePDFReport(clientData, clientHeaders, Constants.CLIENT_LIST, outputPath);
    }

    /**
     * Generates a PDF report for a list of equipments and saves it to the specified output path.
     *
     * @param equipments    a list of Equipment objects to include in the report
     * @param outputPath    the file path where the generated PDF report should be saved
     * @throws IOException          if an I/O error occurs while generating the report
     * @throws DocumentException    if there is an error in the PDF document generation process
     */
    public static void generateEquipmentsPDFReport(List<Equipment> equipments, String outputPath)
            throws IOException, DocumentException {
        String[] equipmentHeaders = {
                Equipments.CLIENT_ID.getValue(),
                Equipments.MODEL.getValue(),
                Equipments.MARK.getValue(),
                Equipments.TYPE.getValue(),
                Equipments.STATUS.getValue()
        };
        
        List<String[]> equipmentData = new ArrayList<>();

        for (Equipment equipment : equipments) {
            String[] equipmentRow = {
                    String.valueOf(equipment.getClientID()), // TODO: buscar cliente por id y setear el name
                    equipment.getModel(),
                    equipment.getMark(),
                    equipment.getEquipmentType(),
                    equipment.getStatus()
            };

            equipmentData.add(equipmentRow);
        }

        GeneratePDFReport.generatePDFReport(equipmentData, equipmentHeaders, Constants.EQUIPMENT_LIST, outputPath);
    }

}
