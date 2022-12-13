package com.epam.hospital.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

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
}
