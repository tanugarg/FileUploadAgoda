package com.agoda.upload.web.web.service;

import java.io.File;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.*;

import javax.annotation.PostConstruct;

import com.agoda.upload.web.web.entities.FailedReplicationEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	
	@Autowired
	CacheProperties cache;
	
	ThreadPoolExecutor threadPool;

	static Map<String,Set<String>>failedReplicationMap = new ConcurrentHashMap<String, Set<String>>();

	@PostConstruct
	public void init(){
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
        threadPool = new ThreadPoolExecutor(0,10,60,TimeUnit.MINUTES,workQueue);
		FailedReplicationProcessor failedReplicationProcessor = new FailedReplicationProcessor(failedReplicationMap);
		Thread thread = new Thread(failedReplicationProcessor);
		thread.start();
	}
	
	public boolean uploadFile(MultipartFile file,boolean doReplicate){
		
		String filePath = cache.getFilePath();
		try {
			String savedPath= saveMultiPartFile(file, filePath);
			System.out.println("File saved at location " + savedPath);
			if(doReplicate){
				replicateOnServers(file,savedPath,cache.getServerList());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		//TODO In separate thread. This call will block this thread

		return true;
	}
	
	public String saveMultiPartFile(MultipartFile file,String filePath) throws Exception{
		String completePath= filePath.concat(file.getOriginalFilename());  
		File convFile = new File(completePath);
		file.transferTo(convFile);
		return completePath;
	}
	
	public void replicateOnServers(MultipartFile file,String savedPath,Set<String> serverList){
		Map<String ,Future<Object>> threadMap = new HashMap<String ,Future<Object>>();
		for(String serverIp:serverList){
			Future<Object> submit = threadPool.submit(new ReplicationThreads(file, serverIp));
			threadMap.put(serverIp, submit);
		}
		for(Entry<String, Future<Object>> entry:threadMap.entrySet()){
			try {
				Boolean b = (Boolean)entry.getValue().get();
				if(b != Boolean.TRUE){
					addFailedReplications(entry.getKey(),savedPath);
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
	
	
//	public void updateFileStatusInMysqlBatch(String currentIP,Set<String> serverIpList,String filePath,FileUploadStatus status){
//
//	}
//
//	public void updateFileStatusInMysql(String currentIP,String serverIp,String filePath,FileUploadStatus status){
//
//	}

	public  void addFailedReplications(String savedPath, String ip) {
		synchronized (failedReplicationMap) {
			if (failedReplicationMap.get(savedPath) != null) {
				Set<String> failedIps = failedReplicationMap.get(savedPath);
				failedIps.add(ip);
				failedReplicationMap.put(savedPath, failedIps);
			} else {
				Set<String> failedIps = new HashSet<String>();
				failedIps.add(ip);
				failedReplicationMap.put(savedPath, failedIps);
			}
		}

	}

}
