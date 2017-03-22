package com.agoda.upload.web.web.service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

public class CacheProperties {
	
	private HashSet<String> serverList;
	
	private String filePath;
	
	private String currentIp;
	
	@PostConstruct
	public void reloadCache() {

        InputStream inputStream;
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
            		serverList = new HashSet<String>(Arrays.asList(seedNodes.split(Pattern.quote("$"))));
            		for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
       	        	 NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
       	             // Iterate all IP addresses assigned to each card...
       	             for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
       	                 InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
       	                 if(serverList.contains(inetAddr.getHostAddress())){
       	                	currentIp = inetAddr.getHostAddress();
       	                	serverList.remove(inetAddr.getHostAddress());
       	                 }
       	             }
       	        	
       	        }
            		
            	}

            System.out.println("FilePath: "+filePath);
            System.out.println("ServerList"+serverList);
            System.out.println("CurrentIp:"+currentIp);
    }
        catch(Exception ex){
        	System.out.println("Error while uploading");
        	ex.printStackTrace();
        }
	}
	
	public Set<String> getServerList(){
		return serverList;
	}
	
	public String getFilePath(){
		return filePath;
	}
	
	public String getCurrentIP(){
		return currentIp;
	}
	
}
