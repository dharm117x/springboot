package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

@Service
public class FileService {
	private Path foundFile;
	
	public Resource getFileAsResource(String fileCode) throws IOException {
        Path dirPath = Paths.get("Files-Upload");
         
        Files.list(dirPath).forEach(file -> {
            if (file.getFileName().toString().startsWith(fileCode)) {
                foundFile = file;
                return;
            }
        });
 
        if (foundFile != null) {
            return new UrlResource(foundFile.toUri());
        }
         
        return null;
    }

	public String saveFile(String fileName, MultipartFile multipartFile) throws IOException {
		Path dirPath = Paths.get("Files-Upload/"+fileName);
		InputStream inputStream = multipartFile.getInputStream();
		Files.copy(inputStream, dirPath, StandardCopyOption.REPLACE_EXISTING);
		return fileName;
	}
}
