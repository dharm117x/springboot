package com.example.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.data.FileUploadResponse;
import com.example.service.FileService;

@RestController
@CrossOrigin(allowedHeaders = {"responseType"})
public class FileUploadDownloadController {

	@Autowired
	FileService fileService;

	@PostMapping("/uplaodFile")
	public ResponseEntity<?> fileUpload(@RequestParam("file") MultipartFile multipartFile) throws IOException {

		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		long size = multipartFile.getSize();

		String filecode = fileService.saveFile(fileName, multipartFile);

		FileUploadResponse response = new FileUploadResponse();
		response.setFileName(fileName);
		response.setSize(size);
		response.setDownloadUri("/downloadFile/" + filecode);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/downloadFile/{fileCode}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode) throws IOException {
        Resource resource = null;

        try {
            resource = fileService.getFileAsResource(fileCode);
        } catch (IOException e) {
            return ResponseEntity.noContent().build();
        }
         
        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.BAD_REQUEST);
        }
         
        byte[] byteArray = Files.readAllBytes(Paths.get(resource.getURI()));
        
        ByteArrayResource res= new ByteArrayResource(byteArray);
        String filename = resource.getFilename();
        String contentType = filename.contains("pdf")?"application/pdf":"application/octet-stream";
        String headerValue = "attachment; filename=\"" + filename + "\"";
         
        HttpHeaders hrs = new HttpHeaders();
        hrs.set(HttpHeaders.CONTENT_TYPE, contentType);
        hrs.set(HttpHeaders.CONTENT_DISPOSITION, headerValue);
        
        ResponseEntity<ByteArrayResource> data = ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType)).contentLength(res.contentLength())
                .headers(hrs)
                .body(res);    
         
         return data;
    }
}
