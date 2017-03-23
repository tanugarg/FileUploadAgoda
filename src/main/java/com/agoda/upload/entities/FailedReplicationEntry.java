package com.agoda.upload.entities;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "failed_replication_entry")
public class FailedReplicationEntry {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "end_point_ip")
    private String endPointIp;

    @Column(name = "file_path")
    private String filePath;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FailedReplicationEntry that = (FailedReplicationEntry) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (endPointIp != null ? !endPointIp.equals(that.endPointIp) : that.endPointIp != null) return false;
        return filePath != null ? filePath.equals(that.filePath) : that.filePath == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (endPointIp != null ? endPointIp.hashCode() : 0);
        result = 31 * result + (filePath != null ? filePath.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FailedReplicationEntry{" +
                "id=" + id +
                ", endPointIp='" + endPointIp + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
