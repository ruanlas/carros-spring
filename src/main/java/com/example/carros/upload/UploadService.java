package com.example.carros.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Base64.Decoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class UploadService {
	
	private Path fileStorageLocation;
	
	@Value("${upload.dir}")
	private String uploadDirPath;
	
	@Autowired
	private void createFolderIfNotExists() {
		System.out.println("****************** Criação de pasta ********************");
		if (fileStorageLocation == null) {
			fileStorageLocation = Paths.get(uploadDirPath)
					.toAbsolutePath()
					.normalize();
		}
		
		try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Não foi possível criar o diretório onde os arquivos serão armazenados.", ex);
//        	System.out.println("Não foi possível criar o diretório onde os arquivos serão armazenados");
        }
	}
	
	public URI uploadFile(UploadInput uploadInput) {
		try {
//			FileOutputStream fos = new FileOutputStream(uploadDirPath + "/" + uploadInput.getFileName());
			File file = fileStorageLocation.resolve(uploadInput.getFileName()).toFile();
			FileOutputStream fos = new FileOutputStream(file);
			Base64.Decoder decoder = Base64.getDecoder();
			fos.write(decoder.decode(uploadInput.getBase64()));
			fos.close();
	        URI fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
		          .path("/api/v1/show/{fileName}")
//		          .path(uploadInput.getFileName())
//		          .build()
		          .buildAndExpand(uploadInput.getFileName())
		          .toUri();
	        return fileDownloadUri;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public Resource getFileAsResource(String fileName) {
		try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("Arquivo não encontrado " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Arquivo não encontrado " + fileName, ex);
        }
	}
}
