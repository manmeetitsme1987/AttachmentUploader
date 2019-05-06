package attachmentuploader.model;

public class AttachmentRequest {
	private String attachmentId;
	private String salesforceUsername;
	private String salesforcePassword;
	private String salesforceSecurityToken;
	private String salesforceAuthEndPoint;
	private String salesforceServiceEndPoint;
	
	
	public String getSalesforceUsername() {
		return salesforceUsername;
	}

	public void setSalesforceUsername(String salesforceUsername) {
		this.salesforceUsername = salesforceUsername;
	}

	public String getSalesforcePassword() {
		return salesforcePassword;
	}

	public void setSalesforcePassword(String salesforcePassword) {
		this.salesforcePassword = salesforcePassword;
	}

	public String getSalesforceSecurityToken() {
		return salesforceSecurityToken;
	}

	public void setSalesforceSecurityToken(String salesforceSecurityToken) {
		this.salesforceSecurityToken = salesforceSecurityToken;
	}

	public String getSalesforceAuthEndPoint() {
		return salesforceAuthEndPoint;
	}

	public void setSalesforceAuthEndPoint(String salesforceAuthEndPoint) {
		this.salesforceAuthEndPoint = salesforceAuthEndPoint;
	}

	public String getSalesforceServiceEndPoint() {
		return salesforceServiceEndPoint;
	}

	public void setSalesforceServiceEndPoint(String salesforceServiceEndPoint) {
		this.salesforceServiceEndPoint = salesforceServiceEndPoint;
	}

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}
}
