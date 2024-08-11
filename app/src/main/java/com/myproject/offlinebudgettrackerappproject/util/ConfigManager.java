package com.myproject.offlinebudgettrackerappproject.util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private Properties properties;

    public ConfigManager(Context context) {
        properties = new Properties();
        try {
            // Load the properties file from the assets directory
            InputStream inputStream = context.getAssets().open("config.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getAdminEmail(Context context) {
        return properties.getProperty("admin_email", "");
    }
}
