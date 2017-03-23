package com.agoda.upload.repository.Impl;

import com.agoda.upload.entities.FailedReplicationEntry;
import com.agoda.upload.repository.IFailedReplicationRepository;

import java.util.List;


public class FailedReplicationRepositoryDaoImpl implements IFailedReplicationRepository{


    public Integer saveEntity(FailedReplicationEntry failedReplicationEntry) {
        // save in table and return the id;

        return 1;
    }

    public FailedReplicationEntry getById(Integer id) {
        FailedReplicationEntry entry = new FailedReplicationEntry();
        //Get the raw from db and return
        return entry;
    }

    public List<FailedReplicationEntry> getAllFailedEntriesBySourceIp(String sourceId) {
        //Get all te failed entries with sourceId = sourceId and fileUploadStatus = FAILED, or
        return null;
    }
}