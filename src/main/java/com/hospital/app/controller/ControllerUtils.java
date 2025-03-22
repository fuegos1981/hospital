package com.hospital.app.controller;

import com.hospital.app.HistoryPatient;
import com.hospital.app.MessageManager;
import com.hospital.app.model.Patient;
import com.hospital.app.exceptions.DBException;
import com.hospital.app.repository.Fields;
import com.hospital.app.service.impl.PatientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * The class contains static methods to make the controller work
 *
 * @author Sinkevich Olena
 *
 */
public class ControllerUtils {

    /**
     * <p>This method determines if a patient history should be loaded and performs it if necessary.
     * </p>
     * @param string  contains a string representation of a date.
     * @param isTime informs whether the specified string contains time.
     * @return date obtained from string representation
     *
     */
    public static Date getDateByString(String string, boolean isTime) throws ParseException {
        if (string==null||string.isEmpty()){
            return null;
        }
        SimpleDateFormat sdf = (isTime)
                ? new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.UK)
                : new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(string.replace("T"," "));
    }

    /**
     * <p>This method adds all attributes from request whose names are listed in the second argument</p>
     * @param request is as an argument to the servlet's service methods (doGet, doPost...).
     * @param args are list of parameter names to be added in request.
     *
     */
    public static void setAttributes(HttpServletRequest request, String... args) {
        for (String atr : args) {
            request.setAttribute(atr, request.getParameter(atr));
        }
    }

    /**
     * <p>This method gets id from request and converts to an integer.
     * </p>
     * @param request is as an argument to the servlet's service methods (doGet, doPost...).
     * @param idName is the name of the parameter to be converted to an integer.
     * @return  an integer or null if the parameter name was not found in the request
     *
     */
    public static Integer parseID(HttpServletRequest request, String idName) {
        String idStr= request.getParameter(idName);
        if (idStr==null|| idStr.isEmpty())
            return null;
        else
        return Integer.valueOf(idStr);
    }

    /**
     * <p>This method sets the attributes in the query required to display pagination for the list.
     * </p>
     * @param request is as an argument to the servlet's service methods (doGet, doPost...).
     * @param countList is a list size.
     * @param currentCountName is the name of the parameter with current count page.
     * @param countPageName is the name of the parameter with count pages.*
     * @return  returns an array of two elements from which and to which element to display the values of the list,
     * returns null if it is necessary to display all elements of the list
     *
     */
    public static int[] setMasForPagination(HttpServletRequest request, int countList, String currentCountName, String countPageName){

        Integer currentPage = parseID(request, currentCountName);
        currentPage = (currentPage==null)?1:currentPage;
        int countPage =(int)Math.ceil(1.00*countList/ControllerConstants.MAX_COUNT_ON_PAGE);
        request.setAttribute(countPageName, countPage);
        request.setAttribute(currentCountName,currentPage);
        request.setAttribute("maxCountOnPage",ControllerConstants.MAX_COUNT_ON_PAGE);
        if (countList<=ControllerConstants.MAX_COUNT_ON_PAGE){
            return null;
        }
        else{
            return new int[]{(currentPage-1)*ControllerConstants.MAX_COUNT_ON_PAGE,ControllerConstants.MAX_COUNT_ON_PAGE};
        }
    }

    public  static String setPathReturn(HttpServletRequest request){
        StringBuilder path = new StringBuilder();
        String patient_id = request.getParameter(ControllerConstants.PATIENT_ID);
        String doctor_id = request.getParameter(ControllerConstants.DOCTOR_ID);
        if (request.getParameter("path_return")==null){
            String from =request.getParameter("from");
            if (from.equals("patient_info"))
                path.append("/hospital/readPatient?id=").append(patient_id).append("&command=patient_info");
            else if (from.equals("admin"))
                path.append("/hospital/admin?command=admin");
            else {
                path.append("/hospital/medic?command=medic");
                if (patient_id!=null)
                    path.append("&patient_id=").append(patient_id);
                if (doctor_id!=null)
                    path.append("&doctor_id=").append(doctor_id);
            }
            request.setAttribute("path_return",path.toString());
        }
        else
            ControllerUtils.setAttributes(request,"path_return");
        return  path.toString();
    }

    /**
     * <p>This method downloads the patient's history to the client's computer.
     * </p>
     * @param req {@link HttpServletRequest} is as an argument to the servlet's service methods (doGet, doPost...).
     * @param resp {@link HttpServletResponse} is as an argument to the servlet's service methods (doGet, doPost...).
     *
     */
    static void downloadHistory(HttpServletRequest req, HttpServletResponse resp, MessageManager currentMessageLocale) throws DBException, SQLException {

        Patient patient = PatientService.getPatientService().readById(ControllerUtils.parseID(req, Fields.PATIENT_ID));
        HistoryPatient.getHistoryPatient(patient, req.getServletContext().getRealPath("WEB-INF/pdf/info.pdf"), currentMessageLocale);
        final int ARBITRARY_SIZE = 1048;
        resp.setContentType("application/pdf");
        resp.setHeader("Content-disposition", "attachment; filename=info.pdf");
        try(InputStream in = req.getServletContext().getResourceAsStream("/WEB-INF/pdf/info.pdf");
            OutputStream out = resp.getOutputStream()) {
            byte[] buffer = new byte[ARBITRARY_SIZE];
            int numBytesRead;
            while ((numBytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, numBytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
