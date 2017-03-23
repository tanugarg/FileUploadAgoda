package com.agoda.upload.entities;


import com.agoda.upload.service.FileUploadStatus;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "failed_replication_entry")
public class FailedReplicationEntry {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name =  "user_email")
    private String userEmail;

    //The IP which is getting the file upload request from the external client (the user)
    @Column(name = "source_ip")
    private String source_ip;

    @Column(name = "end_point_ip")
    private String endPointIp;

    @Column(name = "file_path")
    private String filePath;

    @Enumerated
    @Column(name = "file_upload_status")
    private FileUploadStatus fileUploadStatus;

    public FailedReplicationEntry() {
    }

    public FailedReplicationEntry(String endPointIp, String filePath) {
        this.endPointIp = endPointIp;
        this.filePath = filePath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getSource_ip() {
        return source_ip;
    }

    public void setSource_ip(String source_ip) {
        this.source_ip = source_ip;
    }

    public String getEndPointIp() {
        return endPointIp;
    }

    public void setEndPointIp(String endPointIp) {
        this.endPointIp = endPointIp;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public FileUploadStatus getFileUploadStatus() {
        return fileUploadStatus;
    }

    public void setFileUploadStatus(FileUploadStatus fileUploadStatus) {
        this.fileUploadStatus = fileUploadStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FailedReplicationEntry that = (FailedReplicationEntry) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (userEmail != null ? !userEmail.equals(that.userEmail) : that.userEmail != null) return false;
        if (source_ip != null ? !source_ip.equals(that.source_ip) : that.source_ip != null) return false;
        if (endPointIp != null ? !endPointIp.equals(that.endPointIp) : that.endPointIp != null) return false;
        if (filePath != null ? !filePath.equals(that.filePath) : that.filePath != null) return false;
        return fileUploadStatus == that.fileUploadStatus;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userEmail != null ? userEmail.hashCode() : 0);
        result = 31 * result + (source_ip != null ? source_ip.hashCode() : 0);
        result = 31 * result + (endPointIp != null ? endPointIp.hashCode() : 0);
        result = 31 * result + (filePath != null ? filePath.hashCode() : 0);
        result = 31 * result + (fileUploadStatus != null ? fileUploadStatus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FailedReplicationEntry{" +
                "id=" + id +
                ", userEmail='" + userEmail + '\'' +
                ", source_ip='" + source_ip + '\'' +
                ", endPointIp='" + endPointIp + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileUploadStatus=" + fileUploadStatus +
                '}';
    }
}
