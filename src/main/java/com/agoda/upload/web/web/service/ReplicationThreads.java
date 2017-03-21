package com.agoda.upload.web.web.service;

import java.util.concurrent.Callable;

import org.springframework.web.multipart.MultipartFile;

public class ReplicationThreads implements Callable<Object>{

	private MultipartFile file;
	private String serverIp;
	
	ReplicationThreads(MultipartFile file,String serverIP){
		this.file=file;
		this.serverIp=serverIP;
	}
	public Object call() throws Exception {
		// TODO Call Rest api's to upload
		
		return null;
	}

}
