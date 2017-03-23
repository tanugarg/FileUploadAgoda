package com.agoda.upload.service;

import java.util.concurrent.Callable;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class ReplicationThreads implements Callable<Object> {

    private MultipartFile file;

    private String serverIp;

    private Integer rowId;

    @Autowired
    CacheProperties cache;

    ReplicationThreads(MultipartFile file, String serverIP, Integer rowId) {
        this.file = file;
        this.serverIp = serverIP;
        this.rowId = rowId;
    }

    public Integer call() throws Exception {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        StringBuilder uploadPath = new StringBuilder("http://");
        uploadPath.append(serverIp);
        uploadPath.append("/admin/fileUpload");
        HttpPost uploadFile = new HttpPost(uploadPath.toString());
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("field1", "yes", ContentType.TEXT_PLAIN);

		/*String filePath = cache.getFilePath();
        String completePath= filePath.concat(file.getOriginalFilename());
		
		
		File f = new File(file.getInputStream());   // "[/path/to/upload]"
*/
        try {
            builder.addBinaryBody(
                    "file",
                    file.getInputStream(),
                    ContentType.APPLICATION_OCTET_STREAM,
                    file.getName()
            );

            HttpEntity multipart = builder.build();
            uploadFile.setEntity(multipart);
            CloseableHttpResponse response = httpClient.execute(uploadFile);
            HttpEntity responseEntity = response.getEntity();
        } catch (Exception ex) {
            System.out.println("Exception occurred " + ex);
            return null;
        }
        return rowId;

    }
}
