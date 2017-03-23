package com.agoda.upload.service;

import java.io.File;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.*;

import javax.annotation.PostConstruct;

import com.agoda.upload.entities.FailedReplicationEntry;
import com.agoda.upload.repository.IFailedReplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

    @Autowired
    CacheProperties cache;

    ThreadPoolExecutor threadPool;

    @Autowired
    IFailedReplicationRepository failedReplicationRepository;


    @PostConstruct
    public void init() {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
        threadPool = new ThreadPoolExecutor(0, 10, 60, TimeUnit.MINUTES, workQueue);
        //Get the own ip
        String ownIp = getOwnIp();
        FailedReplicationProcessor failedReplicationProcessor = new FailedReplicationProcessor(failedReplicationRepository, ownIp);
        Thread thread = new Thread(failedReplicationProcessor);
        thread.start();
    }

    public boolean uploadFile(MultipartFile file, boolean doReplicate) {

        String filePath = cache.getFilePath();
        try {
            String savedPath = saveMultiPartFile(file, filePath);
            System.out.println("File saved at location " + savedPath);
            if (doReplicate) {
                replicateOnServers(file, savedPath, cache.getServerList());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String saveMultiPartFile(MultipartFile file, String filePath) throws Exception {
        String completePath = filePath.concat(file.getOriginalFilename());
        File convFile = new File(completePath);
        file.transferTo(convFile);
        return completePath;
    }

    public void replicateOnServers(MultipartFile file, String savedPath, Set<String> serverList) {
        String userEmail = getUserEmail();
        Map<String, Future<Object>> threadMap = new HashMap<String, Future<Object>>();
        for (String serverIp : serverList) {
            // to save entry in table
            FailedReplicationEntry replicationEntry = new FailedReplicationEntry();
            replicationEntry.setUserEmail(userEmail);
            replicationEntry.setFileUploadStatus(FileUploadStatus.INPROGRESS);
            replicationEntry.setSource_ip(getOwnIp());
            replicationEntry.setEndPointIp(serverIp);
            replicationEntry.setFilePath(savedPath);
            Integer tableRowId = failedReplicationRepository.saveEntity(replicationEntry);
            Future<Object> submit = threadPool.submit(new ReplicationThreads(file, serverIp, tableRowId));
            threadMap.put(serverIp, submit);
        }


        for (Entry<String, Future<Object>> entry : threadMap.entrySet()) {
            try {
                Integer rowId = (Integer) entry.getValue().get();
                FailedReplicationEntry replicationEntry = failedReplicationRepository.getById(rowId);
                replicationEntry.setFileUploadStatus(FileUploadStatus.SUCCESS);
                if (rowId == null) {
                    //That means it is failed. So update the table entry to FAILED
//					addFailedReplications(entry.getKey(), savedPath);
                    replicationEntry.setFileUploadStatus(FileUploadStatus.FAILURE);
                }
                failedReplicationRepository.saveEntity(replicationEntry);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private String getUserEmail() {
        // logic to get user email for loggedIn User in server;
        return new String("ABC");
    }

    private String getOwnIp() {
        // to get the own Ip
        return null;
    }
}
