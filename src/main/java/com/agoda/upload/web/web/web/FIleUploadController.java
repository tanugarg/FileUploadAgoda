package com.agoda.upload.web.web.web;

import com.agoda.upload.web.web.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/admin/fileUpload")
public class FIleUploadController {

    @Autowired
    FileUploadService   fileUploadService;


    @RequestMapping("/file/remittance")
    public String saveRemittance(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "action") String action, ModelMap model,
                                 HttpServletRequest request, @RequestParam(value = "param") String param) {
        //TODO service code here

    	boolean isSuccess;
    	if(action.equalsIgnoreCase("Replicate")){
    		isSuccess = fileUploadService.uploadFile(file, true);
    	}
    	else{
    		isSuccess = fileUploadService.uploadFile(file, false);
    	}
    	model.addObject(isSuccess);
    	
        return "admin/fileUpload/fileUpload";
    }
}
