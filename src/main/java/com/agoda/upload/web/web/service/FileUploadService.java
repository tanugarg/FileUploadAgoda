package com.agoda.upload.web.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	
	@Autowired
	CacheProperties cache;
	
	public void uploadFile(MultipartFile file,boolean doReplicate){
		
		String filePath = cache.getCache().getProperty(Constants.filePath)+file.getName();
		saveMultiPartFile(file, filePath);
		if(doReplicate){
			
		}
	}
	
	public void saveMultiPartFile(MultipartFile file,String filePath){
		
	}
	
	public void replicateOnServers(MultipartFile file){
		
	}
}
