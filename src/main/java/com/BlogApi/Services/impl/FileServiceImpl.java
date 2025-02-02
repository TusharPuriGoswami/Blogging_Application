// package com.BlogApi.Services.impl;

// import java.io.File;
// import java.io.FileInputStream;
// import java.io.FileNotFoundException;
// import java.io.IOException;
// import java.io.InputStream;
// import java.nio.file.Files;
// import java.nio.file.Paths;

// import org.springframework.stereotype.Service;
// import org.springframework.web.multipart.MultipartFile;
// import com.BlogApi.Services.FileService;

// @Service
// public class FileServiceImpl implements FileService {
	
	
// 	@Override
// 	public String uploadImage(String path , MultipartFile file) throws IOException{

// 	//File Name

// 	String name = file.getOriginalFilename();

// 	//Full Path

// 	String filePath = path+ File.separator+name;

// 	//create folder if not created
// 	File f = new File(path);
// 	if(!f.exists()){

// 	f.mkdir();

// 	}

// 	//File Copy

// 	Files.copy(file.getInputStream(),Paths.get(filePath));

// 	return name;
// 	}

// 	@Override
// 	public InputStream getResource(String path, String fileName) throws FileNotFoundException {

// 		String fullpath=path+File.separator+fileName;
// 		InputStream is = new FileInputStream(fullpath);
		
		
// 		return is;
// 	}
	
	
	

// }

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
    public String uploadImage(String path, MultipartFile file) throws IOException {

        // File Name
        String name = file.getOriginalFilename();

        // Get absolute project directory
        String projectDir = System.getProperty("user.dir");

        // Ensure the images folder is inside the project directory
        String uploadDir = projectDir + File.separator + "images"; // images folder in root directory

        // Ensure directory exists
        File f = new File(uploadDir);
        if (!f.exists()) {
            f.mkdirs();  // Create directory if it doesn't exist
        }

        // Full path for saving file
        String filePath = uploadDir + File.separator + name;

        // Copy file to directory
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return name;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {

        // Get absolute project directory
        String projectDir = System.getProperty("user.dir");

        // Construct full image path
        String fullPath = projectDir + File.separator + "images" + File.separator + fileName;

        // Return file as InputStream
        return new FileInputStream(fullPath);
    }
}

