package com.joongang.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.joongang.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;

@RestController
@Log4j2
public class UploadController {
	
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str =sdf.format(date);
		return str.replace("-", File.separator);
	}
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			return contentType.startsWith("image");	
		} catch(IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@PostMapping(value="/uploadFileAjax", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AttachFileDTO>> uploadFileAjax(MultipartFile[] uploadFile) {
		List<AttachFileDTO> list = new ArrayList<>();
		String uploadFolder = "C:\\upload";
		
		String uploadFolderPath= getFolder();
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}//폴더생성 
		//MultipartFile 넘어오는 파일들 
		for(MultipartFile multipartFile : uploadFile) {
			AttachFileDTO attachDTO = new AttachFileDTO();
			String uploadFileName = multipartFile.getOriginalFilename();
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
			log.info("only file name:" + uploadFileName);
			attachDTO.setFilename(uploadFileName);//파일 이름 넣기
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			try {
				File saveFile = new File(uploadPath, uploadFileName); //눈으로 보이는 파일 생성
				multipartFile.transferTo(saveFile);
				
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadpath(uploadFolderPath);
				
				if (checkImageType(saveFile)) { // image의 경우 thumbnail 저장
					attachDTO.setImage(true);
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100,100);
					thumbnail.close();
				}
				//add to List
				list.add(attachDTO);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
	}//end for
	return new ResponseEntity<>(list, HttpStatus.OK);
	
	}
	
	@GetMapping(value ="/display", produces = {MediaType.IMAGE_JPEG_VALUE})
	public ResponseEntity<byte[]> getFile(@RequestParam("filename") String filename){
		log.info("filename: " + filename);
		File file= new File ("c:\\upload\\" + filename);
		log.info("file:" + file);
		ResponseEntity<byte[]> result =null;
		try {
			HttpHeaders header = new HttpHeaders();
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);	
		}catch(IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	@DeleteMapping(value="/deleteFile",consumes="application/json")
	public ResponseEntity<String> deletefile(@RequestBody Map<String, String> param){
		String filename= param.get("filename");
		String type= param.get("type");

		log.info("deleteFile: " + filename);
		File file;
		try {
			file = new File("c:\\upload\\" + URLDecoder.decode(filename, "UTF-8"));
			file.delete();
			if(type.equals("image")) {
				String largeFileName = file.getAbsolutePath().replace("s_","");
				log.info("largeFileName: " + largeFileName);
				file = new File(largeFileName);
				file.delete();
			}
		} catch(UnsupportedEncodingException e ) {
			e.printStackTrace();
			return new ResponseEntity<> (HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
	
	@GetMapping("/pdfviewer") 
	public ResponseEntity<Resource> pdfFileDownload(@RequestParam("filename") String filename) {
		try {
			String encodedFileName = URLEncoder.encode(filename,
					StandardCharsets.UTF_8.toString()).replaceAll("\\+","%20");
			Resource resource = new FileSystemResource("c:\\upload\\" + filename);
			File file = resource.getFile();
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION,"inline; filename=\""+ encodedFileName + "\"")
					.header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()))
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF.toString())
					.body(resource);
					
		}catch(FileNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(null);
		}catch(IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	@GetMapping(value ="/downloadFile", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent,
			@RequestParam("filename") String filename) {
		Resource resource = new FileSystemResource("c:\\upload\\" + filename);
		if(resource.exists() == false) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		String resourceName = resource.getFilename();
		log.info("resourceName: " + resourceName);
		log.info("userAgent: "+ userAgent);
		
		//remove UUID
		String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);
		HttpHeaders headers = new HttpHeaders();
		try {
			boolean checkIE = (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1);
			String downloadName = null;
			if(checkIE) {
				downloadName= URLEncoder.encode(resourceOriginalName, "UTF-8").replaceAll("\\+", " ");
			}else {
				if(userAgent.contains("Edge")) {
					downloadName = resourceOriginalName;
				}else{
					downloadName = new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1");
				}	
			}
			headers.add("content-Disposition", "attachment; filename=" + downloadName);	
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);	}
}
