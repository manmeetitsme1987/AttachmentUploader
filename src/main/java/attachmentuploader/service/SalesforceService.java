package attachmentuploader.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sforce.soap.enterprise.Connector;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.sobject.Attachment;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

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
	
	public  List<Attachment> fetchAttachments(EnterpriseConnection partnercon){
		List<Attachment> listAttachments = new ArrayList<Attachment>();
		try{
			QueryResult describeGlobalResult = 
					partnercon.query("select Id, Name, Body, ParentId, BodyLength, ContentType, Description from Attachment where id = '00PM0000004iQ9UMAU'");
			System.out.println(describeGlobalResult.getRecords().length);
	        boolean done = false;
        	
	        while(!done){
	        	for (int k = 0; k < describeGlobalResult.getRecords().length; k++){
        		
	        	    Attachment a = (Attachment)describeGlobalResult.getRecords()[k];
	        	    listAttachments.add(a);
	        	    /*
	        	     * FILE WRITING  CODE
	        	    File path = new File("//Users//tmichels//Dropbox//SFDCAttachments");
	        	    String mySubFolder = a.getId();
	        	    File newDir = new File(path + File.separator + mySubFolder);
	        	    System.out.println(newDir);
	        	    boolean success = newDir.mkdirs();
	        	    FileOutputStream fos = new FileOutputStream(newDir + File.separator+ a.getName());
	        	    fos.write(a.getBody());
	        	    fos.close();
	        	    */
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
