package com.agoda.upload.service;

import com.agoda.upload.entities.FailedReplicationEntry;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by aakhila on 22/3/17.
 */
@Component
public class FailedReplicationProcessor extends Thread{
    private Map<String,Set<String>> failedReplications;

    ThreadPoolExecutor failedReplicationPool;

    static Map<String,Set<String>>failedReplicationMap = new ConcurrentHashMap<String, Set<String>>();

    @PostConstruct
    public void init(){
        BlockingQueue<Runnable> failedReplicationQueue = new LinkedBlockingQueue<Runnable>();
        failedReplicationPool = new ThreadPoolExecutor(0,10,60,TimeUnit.MINUTES,failedReplicationQueue);
    }

      public FailedReplicationProcessor(Map<String,Set<String>> failedReplications){
        this.failedReplications = failedReplications;
    }
    public void run(){
        while(true){
            Map<FailedReplicationEntry,Future<Object>> threadMap = new HashMap<FailedReplicationEntry,Future<Object>>();
            for(Map.Entry e : failedReplications.entrySet()){
                File file = new File(e.getKey().toString());
                DiskFileItem fileItem = new DiskFileItem("file", "text/plain", false, file.getName(), (int) file.length() , file.getParentFile());
                MultipartFile multipartFile = null;
                try {
                    fileItem.getOutputStream();
                    multipartFile = new CommonsMultipartFile(fileItem);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                if(multipartFile!=null){
                    for(String serverIp: (Set<String>)e.getValue()){
                        Future<Object> submit = failedReplicationPool.submit(new ReplicationThreads(multipartFile, serverIp));
                        threadMap.put(new FailedReplicationEntry(serverIp,e.getKey().toString()), submit);
                    }
                }
            }
            for(Map.Entry<FailedReplicationEntry,Future<Object>> entry : threadMap.entrySet()){
                try {
                    Boolean b = (Boolean)entry.getValue().get();
                    if(b != Boolean.TRUE){
                        addFailedReplications(entry.getKey().getFilePath(),entry.getKey().getEndPointIp());
                    }else{
                        removeFailedReplications(entry.getKey().getFilePath(),entry.getKey().getEndPointIp());
                    }

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public  void addFailedReplications(String savedPath, String ip){
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

    public  void removeFailedReplications(String savedPath, String ip) {
        synchronized (failedReplicationMap) {
            Set<String> ips = failedReplicationMap.get(savedPath);
            if (ips != null) {
                ips.remove(ip);
            }
            if (ips == null || ips.isEmpty()) {
                failedReplicationMap.remove(savedPath);
            }
        }
    }
}
