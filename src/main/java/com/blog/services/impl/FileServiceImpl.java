package com.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.services.IFileService;

@Service
public class FileServiceImpl implements IFileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		//file name eg abc.png
		String name = file.getOriginalFilename();
		
		//genrate a random dynamic name for storing the image
		String randomID = UUID.randomUUID().toString(); 
		//concat this randomname with file extension eg .png
		String newFileName = randomID.concat(name.substring(name.lastIndexOf(".")));
		
		//get a fullpath our old path for eg /image concat further it with filename.png
		String filePath = path + File.separator + newFileName;
		
		//create folder (we mentioned foldername in application.properties path) if not created 
		File f = new File(path);
		if(!f.exists())
			f.mkdir();
		
		//file copy
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		return newFileName;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath = path + File.separator + fileName;
		InputStream ipStream = new FileInputStream(fullPath);
		return ipStream;
	}

}
