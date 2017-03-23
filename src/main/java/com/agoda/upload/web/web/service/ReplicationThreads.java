package com.agoda.upload.web.web.service;

import java.io.File;
import java.io.FileInputStream;
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

public class ReplicationThreads implements Callable<Object>{

	private MultipartFile file;
	private String serverIp;
	@Autowired
	CacheProperties cache;
	
	ReplicationThreads(MultipartFile file,String serverIP){
		this.file=file;
		this.serverIp=serverIP;
	}
	public Boolean call() throws Exception {
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		StringBuilder uploadPath= new StringBuilder("http://");
		uploadPath.append(serverIp);
		uploadPath.append("/admin/fileUpload");
	    HttpPost uploadFile = new HttpPost(uploadPath.toString());
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addTextBody("field1", "yes", ContentType.TEXT_PLAIN);

		/*String filePath = cache.getFilePath();
	    String completePath= filePath.concat(file.getOriginalFilename());  
		
		
		File f = new File(file.getInputStream());   // "[/path/to/upload]"
*/		
		try{
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
		}catch(Exception ex){
			System.out.println("Exception" +  ex);
			return false;
		}
		// TODO Call Rest api's to upload
		return true;
	}
	
	//alternative code 
	
	/*HttpClient httpclient = new DefaultHttpClient();
	HttpPost httppost = new HttpPost(url);

	FileBody bin = new FileBody(new File(fileName));
	StringBody comment = new StringBody("Filename: " + fileName);

	MultipartEntity reqEntity = new MultipartEntity();
	reqEntity.addPart("bin", bin);
	reqEntity.addPart("comment", comment);
	httppost.setEntity(reqEntity);

	HttpResponse response = httpclient.execute(httppost);
	HttpEntity resEntity = response.getEntity();*/

}
