package com.agoda.upload.web.web.service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class CacheProperties {
	
	private Properties properties;

	public void reloadCache(String componentName, Integer graphiteReportingInterval) {

        //SystemPropertiesCache spc = CacheManager.getInstance().getCache(SystemPropertiesCache.class);

        String serverName = null;
        try {
            serverName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            serverName = "catalog";
        }
        String env = null;
        InputStream inputStream;
        InputStream camsInputStream;
        String graphiteUrl;
        Integer graphitePort;
        try {
            String propFileName = "server.properties";
//            String propFile = "file.properties";
            Properties temp = new Properties();

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
//            camsInputStream = getClass().getClassLoader().getResourceAsStream(propFile);
            if (inputStream != null) {
            	temp.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            synchronized (properties) {
				properties=temp;
			}

            System.out.println(properties);
    }
        catch(Exception ex){
        	System.out.println("Error while uploading");
        }
	}
	
	
	public Properties getCache(){
		return properties;
	}
}
