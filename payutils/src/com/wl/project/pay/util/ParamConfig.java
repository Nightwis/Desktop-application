package com.wl.project.pay.util;

import java.io.*;
import java.util.Properties;

public class ParamConfig {
    private static Properties prop = new Properties();


    static {
        try {
            prop.load(new BufferedInputStream(new FileInputStream(System.getProperty("user.dir") + "\\application.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getValue(String key){
        return prop.getProperty(key);
    }

    public void setValue(String key,String val){
        BufferedOutputStream bos = null;
        try {
            prop.setProperty(key,val);
            bos = new BufferedOutputStream(
                    new FileOutputStream(System.getProperty("user.dir") + "\\application.properties"));
            //System.out.println(System.getProperty("user.dir") + "\\application.properties");
            prop.store(bos, "Update Address");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != bos)
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }



}
