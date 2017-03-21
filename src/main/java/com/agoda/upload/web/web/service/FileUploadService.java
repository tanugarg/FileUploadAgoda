package com.agoda.upload.web.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	
	@Autowired
	CacheProperties cache;
	
	ThreadPoolExecutor threadPool;
	
	@PostConstruct
	public void init(){
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
        threadPool = new ThreadPoolExecutor(0,10,60,TimeUnit.MINUTES,workQueue);
	}
	
	public boolean uploadFile(MultipartFile file,boolean doReplicate){
		
		String filePath = cache.getFilePath();
		try {
			saveMultiPartFile(file, filePath);
			updateFileStatusInMysqlBatch(cache.getServerList(),filePath,FileUploadStatus.INPROGRESS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		if(doReplicate){
			replicateOnServers(file,filePath);
		}
		return true;
	}
	
	public void saveMultiPartFile(MultipartFile file,String filePath) throws Exception{
		
	}
	
	public void replicateOnServers(MultipartFile file,String filePath){
		Map<String ,Future<Object>> threadMap = new HashMap<String ,Future<Object>>();
		for(String serverIp:cache.getServerList()){
			Future<Object> submit = threadPool.submit(new ReplicationThreads(file, serverIp));
			threadMap.put(serverIp, submit);
		}
		for(Entry<String, Future<Object>> entry:threadMap.entrySet()){
			try {
				Boolean b = (Boolean)entry.getValue().get();
				if(b != Boolean.TRUE){
					updateFileStatusInMysql(entry.getKey(),filePath,FileUploadStatus.FAILURE);
				}
				else{
					updateFileStatusInMysql(entry.getKey(),filePath,FileUploadStatus.SUCCESS);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public void updateFileStatusInMysqlBatch(List<String> serverIpList,String filePath,FileUploadStatus status){
		
	}
	
	public void updateFileStatusInMysql(String serverIp,String filePath,FileUploadStatus status){
		
	}
}
