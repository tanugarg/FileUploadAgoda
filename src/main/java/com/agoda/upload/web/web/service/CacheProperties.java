package com.agoda.upload.web.web.service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

public class CacheProperties {
	
	private List<String> serverList;
	
	private String filePath;
	
	@PostConstruct
	public void reloadCache(String componentName, Integer graphiteReportingInterval) {

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

            	filePath = (String)temp.get(Constants.filePath);
            	String seedNodes= (String)temp.get(Constants.seedNode);
            	if(!seedNodes.isEmpty()){
            		serverList = Arrays.asList(seedNodes.split(Pattern.quote("$")));
            	}
				

            System.out.println("FilePath: "+filePath);
            System.out.println("ServerList"+serverList);
    }
        catch(Exception ex){
        	System.out.println("Error while uploading");
        }
	}
	
	public List<String> getServerList(){
		return serverList;
	}
	
	public String getFilePath(){
		return filePath;
	}
}
