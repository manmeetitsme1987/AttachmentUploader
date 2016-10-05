package attachmentuploader.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import attachmentuploader.model.FileName;

@Service
public class FileUploadService {
	
	private AmazonS3Client amazonS3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	
	public FileName uploadFile(MultipartFile fileUploadReq){
		PutObjectResult putObjectResult = null;
		String fileName = null;
		try {
			fileName = fileUploadReq.getOriginalFilename();
			putObjectResult = upload(fileUploadReq.getInputStream(), fileUploadReq.getOriginalFilename());
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileName fileNameObj = new FileName();
		if(putObjectResult != null){
			fileNameObj.setFileName(fileName);
		}
		
		fileNameObj.setFileName(fileName);
		return fileNameObj;

	}
	
	
	private PutObjectResult upload(InputStream inputStream, String uploadKey) {
		amazonS3Client = new AmazonS3Client();
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, uploadKey, inputStream, new ObjectMetadata());
		putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
		PutObjectResult putObjectResult = amazonS3Client.putObject(putObjectRequest);
		IOUtils.closeQuietly(inputStream);
		return putObjectResult;
	}
	

}
