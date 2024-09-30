package com.codewithvishank.blog.service.impl;

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

import com.codewithvishank.blog.service.FileService;
@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
//		Get file Name
		String name=file.getOriginalFilename();
		String randomUUId=UUID.randomUUID().toString();
		String file1=randomUUId.concat(name.substring(name.lastIndexOf(".")));
		
		
		//Get full path		
		
		String filePath=path+File.separator+file1;
		
//		Create folder if not exists
		
		File f=new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
//		file copy
		Files.copy(file.getInputStream(), Paths.get(filePath));
		return file1;
	}

	@Override
	public InputStream getResources(String path, String fileName) throws FileNotFoundException {
		// TODO Auto-generated method stub
		String fullPath = path + File.separator + fileName;
		InputStream is = new FileInputStream(fullPath);
		// db logic to return inpustream
		return is;
	}

}
