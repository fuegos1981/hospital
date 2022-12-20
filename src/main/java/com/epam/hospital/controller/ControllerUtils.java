package com.epam.hospital.controller;

import com.epam.hospital.model.Gender;
import com.epam.hospital.model.Person;
import com.epam.hospital.repository.Fields;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ControllerUtils {

    public static void setCookie(HttpServletResponse resp, String name, String value) {

        Cookie c = new Cookie(name, value);
        c.setMaxAge(60 * 60); // время жизни файла cookie
        resp.addCookie(c); // добавление cookie к объекту-ответу

    }

    public static ArrayList<String> addToRequest(HttpServletRequest request) {
        ArrayList<String> messages = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            messages.add("Number cookies : " + cookies.length);
            for (Cookie c : cookies) {
                messages.add(c.getName() + " = " + c.getValue());
            }
        }
        return messages;
    }

    public static Date getDateByString(String string, boolean isTime) throws ParseException {
        if (string==null||string.isEmpty()){
            return null;
        }
        SimpleDateFormat sdf = (isTime)
                ? new SimpleDateFormat("yyyy-MM-dd hh:mm")
                : new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(string.replace("T"," "));
    }

    public static Person getPerson(HttpServletRequest request) throws ParseException {
        return Person.createPerson(request.getParameter(ControllerConstants.LAST_NAME),
                request.getParameter(ControllerConstants.FIRST_NAME),
                ControllerUtils.getDateByString(request.getParameter(Fields.PERSON_BIRTHDAY), false),
                request.getParameter(Fields.PERSON_EMAIL),
                Gender.valueOf((request.getParameter(ControllerConstants.GENDER).toUpperCase())));
    }

    public static void RemoveAttributes(HttpServletRequest request, String... atrs) {
        for (String atr : atrs) {
            request.removeAttribute(atr);
        }
    }

    public static void setAttributes(HttpServletRequest request, String... atrs) {
        for (String atr : atrs) {
            request.setAttribute(atr, request.getParameter(atr));
        }
    }

    public static void setNameFromParameter(HttpServletRequest request) {
        String name = request.getParameter(ControllerConstants.NAME);
        if (name!=null){
            name =name.replace(ControllerConstants.PERC, " ");
            request.setAttribute(ControllerConstants.NAME, name);
        }
    }

    public static void setGender(HttpServletRequest request) {
        String gender = request.getParameter(ControllerConstants.GENDER);
        gender=(gender==null)?ControllerConstants.MALE:gender;
        request.setAttribute(ControllerConstants.GENDER,gender);
    }

    public static int[] setMasForPagination(HttpServletRequest request, int countList, String currentCountName, String countPageName){

        int currentPage;
        if (request.getParameter(currentCountName)==null||request.getParameter(currentCountName).isEmpty()){
            currentPage=1;
        }
        else {
            currentPage = Integer.parseInt(request.getParameter(currentCountName));
        }

        int countPage = (countList<ControllerConstants.MAX_COUNT_ON_PAGE)?1:(int)Math.ceil(1.00*countList/ControllerConstants.MAX_COUNT_ON_PAGE);
        request.setAttribute(countPageName, countPage);
        request.setAttribute(currentCountName,currentPage);
        if (countList<=ControllerConstants.MAX_COUNT_ON_PAGE){
            return null;
        }
        else{
            return new int[]{(currentPage-1)*ControllerConstants.MAX_COUNT_ON_PAGE,Math.min(currentPage*ControllerConstants.MAX_COUNT_ON_PAGE,countList)};
        }
    }
}
