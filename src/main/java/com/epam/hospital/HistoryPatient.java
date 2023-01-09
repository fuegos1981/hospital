package com.epam.hospital;

import com.epam.hospital.dto.AppointmentDto;
import com.epam.hospital.model.Appointment;
import com.epam.hospital.model.Patient;
import com.epam.hospital.exceptions.DBException;
import com.epam.hospital.service.impl.AppointmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class HistoryPatient {
    private static final Logger logger = LogManager.getLogger();

    public static void getHistoryPatient(Patient patient, String path) throws DBException, SQLException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        try {
            PDDocumentInformation pdi = document.getDocumentInformation();

            pdi.setTitle("Hospital");
            pdi.setCreator("Patient's history");
            Calendar date = Calendar.getInstance();
            pdi.setCreationDate(date);
            pdi.setModificationDate(date);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 30);
            contentStream.setLeading(50.5f);
            contentStream.newLineAtOffset(200, 750);
            contentStream.showText("History");
            contentStream.newLine();
            contentStream.endText();
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 700);
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 16);
            contentStream.setLeading(20.5f);
            contentStream.showText("Name: "+ patient.toString());
            contentStream.newLine();
            contentStream.showText("birthday: "+patient.getBirthday());
            contentStream.newLine();
            contentStream.showText("email: "+ patient.getEmail());
            contentStream.newLine();
            contentStream.showText("gender: "+patient.getGender().toString().toLowerCase(Locale.ROOT));
            contentStream.newLine();
            contentStream.endText();
            getTable(contentStream,page, patient);
            contentStream.close();

            document.save(path);
            document.close();
        }
        catch (IOException e){
            logger.error(e.getMessage());
        }
    }
    private static void getTable(PDPageContentStream contentStream, PDPage page, Patient patient) throws IOException, DBException, SQLException {
        AppointmentService appointmentService = AppointmentService.getAppointmentService();
        Map<String,Object> selection = new HashMap<>();
        selection.put("patient_id",patient.getId());
        List<AppointmentDto> list =appointmentService.getAll(selection, null, null);
        int pageHeight = (int)page.getTrimBox().getHeight()-200; //get height of the page

        contentStream.setStrokingColor(Color.DARK_GRAY);
        contentStream.setLineWidth(1);

        int initX = 50;
        int initY = pageHeight-50;
        int cellHeight = 20;
        int cellWidth = 120;

        int colCount = 4;
        int rowCount = list.size();

        contentStream.addRect(initX,initY,cellWidth,-cellHeight);
        writeTextInTable(contentStream,initX,initY,cellHeight,"Date");
        initX+=cellWidth;
        contentStream.addRect(initX,initY,cellWidth,-cellHeight);
        writeTextInTable(contentStream,initX,initY,cellHeight,"Doctor");
        initX+=cellWidth;
        contentStream.addRect(initX,initY,cellWidth,-cellHeight);
        writeTextInTable(contentStream,initX,initY,cellHeight,"Diagnosis");
        initX+=cellWidth;
        contentStream.addRect(initX,initY,cellWidth+30,-cellHeight);
        writeTextInTable(contentStream,initX,initY,cellHeight,"Description");
        initX = 50;
        initY -=cellHeight;
        for(int i = 1; i<=rowCount;i++){
            for(int j = 1; j<=colCount;j++){
                if(j == 4){
                    contentStream.addRect(initX,initY,cellWidth+30,-cellHeight);
                    writeApp(contentStream, list, initX, initY, cellHeight, i, j);
                    initX+=cellWidth+30;
                }else{
                    contentStream.addRect(initX,initY,cellWidth,-cellHeight);
                    writeApp(contentStream, list, initX, initY, cellHeight, i, j);
                    initX+=cellWidth;
                }
            }
            initX = 50;
            initY -=cellHeight;
        }

        contentStream.stroke();
    }

    private static void writeApp(PDPageContentStream contentStream, List<AppointmentDto> list, int initX, int initY, int cellHeight, int i, int j) throws IOException {
        if (j ==1)
            writeTextInTable(contentStream, initX, initY, cellHeight, list.get(i -1).getDateCreate().toString());
        if (j ==2)
            writeTextInTable(contentStream, initX, initY, cellHeight,
                    list.get(i -1).getDoctorName());
        if (j ==3)
            writeTextInTable(contentStream, initX, initY, cellHeight, list.get(i -1).getDiagnosisName().toString());
        if (j ==4)
            writeTextInTable(contentStream, initX, initY, cellHeight, list.get(i -1).getDescription());
    }

    private static void writeTextInTable(PDPageContentStream contentStream, int initX, int initY, int cellHeight, String string) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(initX+10,initY-cellHeight+10);
        contentStream.setFont(PDType1Font.COURIER,10);
        contentStream.showText(string);
        contentStream.endText();
    }

}
