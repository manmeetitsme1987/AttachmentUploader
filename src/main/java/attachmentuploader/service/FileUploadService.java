package attachmentuploader.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.BasicAWSCredentials;
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
	
	@Value("${cloud.aws.credentials.accessKeyBreak1}")
	private String accessKeyBreak1;
	
	@Value("${cloud.aws.credentials.accessKeyBreak2}")
	private String accessKeyBreak2;
	
	@Value("${cloud.aws.credentials.secretKeyBreak1}")
	private String secretKeyBreak1;
	
	@Value("${cloud.aws.credentials.secretKeyBreak2}")
	private String secretKeyBreak2;
	
	public FileName uploadFile(MultipartFile fileUploadReq) throws IOException{
		PutObjectResult putObjectResult = null;
		String fileName = null;
		fileName = fileUploadReq.getOriginalFilename();
		putObjectResult = upload(fileUploadReq.getInputStream(), fileUploadReq.getOriginalFilename());
		FileName fileNameObj = new FileName();
		if(putObjectResult != null){
			fileNameObj.setFileName(fileName);
		}
		fileNameObj.setFileName(fileName);
		return fileNameObj;

	}
	
	
	private PutObjectResult upload(InputStream inputStream, String uploadKey) {
		String accessKey = accessKeyBreak1+accessKeyBreak2;
		String secretKey = secretKeyBreak1+secretKeyBreak2;
		System.out.println("===key==" + accessKey + "====secretKey===" + secretKey);
		amazonS3Client = new AmazonS3Client(new BasicAWSCredentials(accessKey,secretKey));
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, uploadKey, inputStream, new ObjectMetadata());
		putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
		System.out.println("===putObjectRequest.getBucketName()==" + putObjectRequest.getBucketName());
		System.out.println("===putObjectRequest.getKey==" + putObjectRequest.getKey());
		System.out.println("===putObjectRequest.toString==" + putObjectRequest.toString());
		PutObjectResult putObjectResult = amazonS3Client.putObject(putObjectRequest);
		IOUtils.closeQuietly(inputStream);
		return putObjectResult;
	}
	

}
