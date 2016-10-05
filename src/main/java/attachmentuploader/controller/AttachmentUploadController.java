package attachmentuploader.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.sobject.Attachment;

import attachmentuploader.model.AttachmentRequest;
import attachmentuploader.service.FileUploadService;
import attachmentuploader.service.SalesforceService;


@RestController
public class AttachmentUploadController {
	@Autowired
	private SalesforceService salesforceService;
	
	@Autowired
    private FileUploadService fileUploadService;
	
	@RequestMapping(value="/uploadAttachment", method=RequestMethod.POST)
    public String uploadSalesforceAttachment(@RequestBody List<AttachmentRequest> attachmentReqeusts) {
		try{
			EnterpriseConnection connection = salesforceService.createConnectionToSalesforceOrg();
			System.out.println(connection + "==== connection");
			if(connection != null){
				List<Attachment> listAttachments = salesforceService.fetchAttachments(connection);
				System.out.println(listAttachments.size() + "==== listAttachments.size()");
				System.out.println(listAttachments + "==== ");
				Attachment attach = listAttachments.get(0);
				MultipartFile result = new MockMultipartFile(
											attach.getName(),
											attach.getName(), 
											attach.getContentType(), 
											attach.getBody());
				fileUploadService.uploadFile(result);
			}
		}catch(Exception e){
			e.printStackTrace();
			return "Error from uploadAttachment! " + e.getMessage();
		}
		return "Greetings from uploadAttachment!";
    }
}
