package com.agoda.upload.repository;


import com.agoda.upload.entities.FailedReplicationEntry;

import java.util.List;

public interface IFailedReplicationRepository {

    Integer saveEntity(FailedReplicationEntry failedReplicationEntry);

    FailedReplicationEntry getById(Integer id);

    List<FailedReplicationEntry> getAllFailedEntriesBySourceIp(String sourceId);
}

