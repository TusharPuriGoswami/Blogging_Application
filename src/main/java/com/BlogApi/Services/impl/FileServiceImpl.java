package com.BlogApi.Services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.BlogApi.Services.FileService;

@Service
public class FileServiceImpl implements FileService {
	
	
	@Override
	public String uploadImage(String path , MultipartFile file) throws IOException{

	//File Name

	String name = file.getOriginalFilename();

	//Full Path

	String filePath = path+ File.separator+name;

	//create folder if not created
	File f = new File(path);
	if(!f.exists()){

	f.mkdir();

	}

	//File Copy

	Files.copy(file.getInputStream(),Paths.get(filePath));

	return name;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {

		String fullpath=path+File.separator+fileName;
		InputStream is = new FileInputStream(fullpath);
		
		
		return is;
	}
	
	
	

}
