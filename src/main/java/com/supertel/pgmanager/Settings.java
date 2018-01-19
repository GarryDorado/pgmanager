package com.supertel.pgmanager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * File: Settings.java (UTF-8)
 * Date: Jan 18, 2018
 * Time: 10:55:55 AM
 */
public class Settings {

    private String pathBin;
    private String pathData;
    private int port;
    private String userLabel;
    private String userName;
    private String password;
    private String dbName;

    private Settings() {
    }

    public static Settings getInstance() {
        return SettingsHolder.INSTANCE;
    }

    private static class SettingsHolder {

        private static final Settings INSTANCE = new Settings();
    }

    public String getPathBin() {
        return pathBin;
    }

    public void setPathBin(String pathBin) {
        this.pathBin = pathBin;
    }

    public String getPathData() {
        return pathData;
    }

    public void setPathData(String pathData) {
        this.pathData = pathData;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserLabel() {
        return userLabel;
    }

    public void setUserLabel(String userLabel) {
        this.userLabel = userLabel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void save() throws IOException {
        Properties prop = new Properties();

        OutputStream output = null;

        try {
            output = new FileOutputStream("config.properties");
            // set the properties value
            prop.setProperty("pathBin", pathBin);
            prop.setProperty("pathData", pathData);
            prop.setProperty("port", "" + port);
            prop.setProperty("userLabel", userLabel);
            prop.setProperty("userName", userName);
            prop.setProperty("password", password);
            prop.setProperty("dbName", dbName);

            // save properties to project root folder
            prop.store(output, null);
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }

    public void load() throws IOException {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("config.properties");
            // load a properties file
            prop.load(input);

            pathBin = prop.getProperty("pathBin");
            pathData = prop.getProperty("pathData");
            port = Integer.valueOf(prop.getProperty("port"));
            userLabel = prop.getProperty("userLabel");
            userName = prop.getProperty("userName");
            password = prop.getProperty("password");
            dbName = prop.getProperty("dbName");
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }
}
