package com.agoda.upload.service;

import com.agoda.upload.entities.FailedReplicationEntry;
import com.agoda.upload.repository.IFailedReplicationRepository;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

@Component
public class FailedReplicationProcessor extends Thread {

    private final Long JOB_RUN_INTERVAL = 1000 * 60 * 15l; //15 mins


    ThreadPoolExecutor failedReplicationPool;

    IFailedReplicationRepository replicationRepository;

    String serverIp;

    @PostConstruct
    public void init() {
        BlockingQueue<Runnable> failedReplicationQueue = new LinkedBlockingQueue<Runnable>();
        failedReplicationPool = new ThreadPoolExecutor(0, 10, 60, TimeUnit.MINUTES, failedReplicationQueue);
    }

    public FailedReplicationProcessor(IFailedReplicationRepository replicationRepository, String serverIp) {
        this.replicationRepository = replicationRepository;
        this.serverIp = serverIp;
    }

    /**
     * This thread of job will keep on running on all the servers, to serve all the failed file uploads, requested to it by user.
     * 1. It will first featch all the failed request and then process them
     * 2. Then wait for some time say 15mins and repeat the same thing again.
     */

    public void run() {

        while (true) {

            try {
                //Get all the failed file upload request, for which the server running this job was the initiator
                List<FailedReplicationEntry> entriesBySourceIp = replicationRepository.getAllFailedEntriesBySourceIp(serverIp);

                Map<FailedReplicationEntry, Future<Object>> threadMap = new HashMap<FailedReplicationEntry, Future<Object>>();

                Map<String, MultipartFile> multipartFileMap = new HashMap<String, MultipartFile>();

                for (FailedReplicationEntry entry : entriesBySourceIp) {
                    //Steps followed in this
                    //1. Get the file from location
                    //2. Convert the file for multipart file
                    //3. Create the HTTP request to call on the server
                    //4. Call the service (/admin/fileUpload/uploadFile) on the target server where the file has to be uploaded.
                    //5. If Service is completed successfully, update the DB entry to update the status as SUCCESS.
                    //6. If Service is FAILED, don't update anything. status will remain as FAILED, and will again be retried in the next job run


                    MultipartFile multipartFile = multipartFileMap.get(entry.getFilePath());
                    if (multipartFile == null) {
                        //1. Get the file from location
                        File file = new File(entry.getFilePath());
                        DiskFileItem fileItem = new DiskFileItem("file", "text/plain", false, file.getName(), (int) file.length(), file.getParentFile());
                        //2. Convert the file for multipart filev
                        try {
                            fileItem.getOutputStream();
                            multipartFile = new CommonsMultipartFile(fileItem);
                            multipartFileMap.put(entry.getFilePath(), multipartFile);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if (multipartFile != null) {
                        Future<Object> submit = failedReplicationPool.submit(new ReplicationThreads(multipartFile, serverIp, entry.getId()));
                        threadMap.put(new FailedReplicationEntry(entry.getEndPointIp(), entry.getFilePath()), submit);
                    }
                }
                for (Map.Entry<FailedReplicationEntry, Future<Object>> entry : threadMap.entrySet()) {
                    try {
                        Integer rowId = (Integer) entry.getValue().get();
                        FailedReplicationEntry replicationEntry = replicationRepository.getById(rowId);
                        replicationEntry.setFileUploadStatus(FileUploadStatus.SUCCESS);
                        if (rowId == null) {
                            //That means it is failed. So update the table entry to FAILED
                            replicationEntry.setFileUploadStatus(FileUploadStatus.FAILURE);
                        }
                        replicationRepository.saveEntity(replicationEntry);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(JOB_RUN_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                try {
                    Thread.sleep(JOB_RUN_INTERVAL);
                } catch (InterruptedException ee) {
                    ee.printStackTrace();
                }
            }
        }
    }
}
