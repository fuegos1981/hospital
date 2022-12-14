package com.epam.hospital.controller;

import com.epam.hospital.model.Gender;
import com.epam.hospital.model.Person;

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
        Cookie[ ] cookies = request.getCookies();
        if (cookies != null) {
            messages.add("Number cookies : " + cookies.length);
            for (int i = 0; i < cookies.length; i++) {
                Cookie c = cookies[i];
                messages.add(c.getName() + " = " + c.getValue());
            }
        }
        return messages;
    }
    public static Date getDateByString(String string) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(string);
    }
    public static Person getPerson(HttpServletRequest request) throws ParseException {
        return Person.createPerson(request.getParameter("lastName"),
                request.getParameter("firstName"),
                ControllerUtils.getDateByString(request.getParameter("birthday")),
                request.getParameter("email"),
                Gender.MALE);
    }

    public static void RemoveAttributes(HttpServletRequest request, String...atrs){
        for (String atr:atrs) {
           request.removeAttribute(atr);
        }
    }

    public static void setAttributes(HttpServletRequest request, String...atrs){
        for (String atr:atrs) {
            request.setAttribute(atr,request.getParameter(atr));
        }
    }
}
