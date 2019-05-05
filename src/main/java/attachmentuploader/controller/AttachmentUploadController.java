package attachmentuploader.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.sobject.Attachment;

import attachmentuploader.model.AttachmentRequest;
import attachmentuploader.model.FileWrapper;
import attachmentuploader.service.ExcelFileReader;
import attachmentuploader.service.FileUploadService;
import attachmentuploader.service.SalesforceService;


@RestController
public class AttachmentUploadController {
	@Autowired
	private SalesforceService salesforceService;
	
	@Autowired
    private FileUploadService fileUploadService;
	
	@Autowired
	private ExcelFileReader excelFileReader;
	
	@RequestMapping(value="/uploadAttachment", method=RequestMethod.POST)
    public String uploadSalesforceAttachment(@RequestBody List<AttachmentRequest> attachmentReqeusts) {
		try{
			EnterpriseConnection connection = salesforceService.createConnectionToSalesforceOrg();
			if(connection != null){
				List<Attachment> listAttachments = salesforceService.fetchAttachments(connection, attachmentReqeusts);
				//Attachment attach = listAttachments.get(0);
				for(Attachment attach : listAttachments){
					MultipartFile result = new MockMultipartFile(
												attach.getName(),
												attach.getName(), 
												attach.getContentType(), 
												attach.getBody());
					fileUploadService.uploadFile(result, attach.getParentId());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return "Error from uploadAttachment! " + e.getMessage();
		}
		return "Greetings from uploadAttachment!";
    }
	
	@RequestMapping(value="/testService", method=RequestMethod.GET)
	public String testService() {
		try{
			return "Successful call";
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return "Congrats";
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	@Consumes(MediaType.MULTIPART_FORM_DATA)  
	public FileWrapper uploadExcelFile(@RequestParam("file") MultipartFile file) {
		try{
			if (!file.isEmpty()) {
				FileWrapper wrapperObj = excelFileReader.parseExcelFile(file);
                return wrapperObj;
	        } else {
	        }
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return null;
	}
}
