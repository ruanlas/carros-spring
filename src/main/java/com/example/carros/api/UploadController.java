package com.example.carros.api;

import java.io.IOException;
import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.carros.upload.UploadInput;
import com.example.carros.upload.UploadService;

@RestController
@RequestMapping("/api/v1")
public class UploadController {
	
	@Autowired
	private UploadService uploadService;

	@PostMapping("/upload")
	public ResponseEntity fileUpload(@RequestBody UploadInput uploadInput) {
		URI uriString = uploadService.uploadFile(uploadInput);
		return ResponseEntity.created(uriString).build();
	}
	
	@GetMapping("/show/{fileName}")
	public ResponseEntity<Resource> getFileUpload(@PathVariable String fileName, HttpServletRequest request) {
		Resource resource = uploadService.getFileAsResource(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
//            logger.info("Não foi possível determinar o tipo de arquivo.");
        	System.out.println("Não foi possível determinar o tipo de arquivo.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
	}
}
