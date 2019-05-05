package attachmentuploader.model;

import java.util.List;

public class FileWrapper {
	private String errorMessage;
	private List<XLSXFileWrapper> lstXlsxWrapperObj;
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public List<XLSXFileWrapper> getLstXlsxWrapperObj() {
		return lstXlsxWrapperObj;
	}
	public void setLstXlsxWrapperObj(List<XLSXFileWrapper> lstXlsxWrapperObj) {
		this.lstXlsxWrapperObj = lstXlsxWrapperObj;
	}
	
	
}
