package attachmentuploader.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.sforce.soap.enterprise.Connector;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.sobject.Attachment;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

import attachmentuploader.model.AttachmentRequest;

@Service
public class SalesforceService {
	
	public  EnterpriseConnection createConnectionToSalesforceOrg() throws NumberFormatException, IOException, URISyntaxException{
		//Create a new connectionconfig to your Salesforce Org
	    ConnectorConfig sfconfig = new ConnectorConfig();
	    //Use your salesforce username = email
	    sfconfig.setUsername("manmeetitsme1987@gmail.com.svmx");
	    //Use your saleforce password with your security token look like: passwordjeIzBAQKkR6FBW8bw5HbVkkkk
	    sfconfig.setPassword("qwerqwer3G6ucTm0GkLYsCi08XxTUNJHR");
	    sfconfig.setAuthEndpoint("https://test.salesforce.com/services/Soap/c/34.0");
	    sfconfig.setServiceEndpoint("https://test.salesforce.com/services/Soap/c/34.0");
	    
	    EnterpriseConnection partnercon = null;
	    try {
	    	 
	        // create a salesforce connection object with the credentials supplied in your connectionconfig
	        partnercon = Connector.newConnection(sfconfig);
	    }catch (ConnectionException e) {
	    	// TODO Auto-generated catch block
	    	e.printStackTrace();
	    }
	    return partnercon;
	}
	
	public  List<Attachment> fetchAttachments(EnterpriseConnection partnercon, List<AttachmentRequest> attachmentReqeusts){
		List<Attachment> listAttachments = new ArrayList<Attachment>();
		try{
			String query = "select Id, Name, Body, ParentId, BodyLength, ContentType, "+
							" Description from Attachment ";
			
			String attachmentIds = "";
			for(AttachmentRequest reqObj : attachmentReqeusts){
	            if(attachmentIds.equals("")){
	            	attachmentIds = "'" + reqObj.getAttachmentId() + "'";
	            }else{
	            	attachmentIds += ",'" + reqObj.getAttachmentId() + "'";
	            }
	        }
			query += " where id in (" + attachmentIds + ")";
			QueryResult describeGlobalResult = partnercon.query(query);
			System.out.println("Records length : =" + describeGlobalResult.getRecords().length);
	        boolean done = false;
        	
	        while(!done){
	        	for (int k = 0; k < describeGlobalResult.getRecords().length; k++){
	        	    Attachment a = (Attachment)describeGlobalResult.getRecords()[k];
	        	    listAttachments.add(a);
	        	}
        	
	        	if (describeGlobalResult.isDone()) {
	               done = true;
	            } else {
	           	 describeGlobalResult = partnercon.queryMore(describeGlobalResult.getQueryLocator());
	            }
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
		return listAttachments;
	}

}
