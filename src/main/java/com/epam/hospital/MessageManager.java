package com.epam.hospital;

import java.util.Locale;
import java.util.ResourceBundle;

public enum MessageManager {
    EN(ResourceBundle.getBundle("pagecontent", new Locale("en", "US"))),
    UA(ResourceBundle.getBundle("pagecontent", new Locale("uk", "UA")));
    private ResourceBundle bundle;

    MessageManager(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public String getString(String key) {
        return bundle.getString(key);
    }
}
