package com.agoda.upload.service;

import java.util.concurrent.Callable;

import org.springframework.web.multipart.MultipartFile;

public class ReplicationThreads implements Callable<Object>{

	private MultipartFile file;
	private String serverIp;
	private Integer tableRowId;
	
	ReplicationThreads(MultipartFile file,String serverIP, Integer tableRowId){
		this.file=file;
		this.serverIp=serverIP;
	}
	public Integer call() throws Exception {
		// TODO Call Rest api's to upload
		boolean success = true;
		if (success) {
			return tableRowId;
		}
		return null;
	}

}
