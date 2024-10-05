package br.com.mcb.galaxyauto.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {

	String uploadFile(MultipartFile image) throws IOException;

}
