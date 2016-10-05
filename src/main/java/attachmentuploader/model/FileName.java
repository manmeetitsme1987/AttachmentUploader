package attachmentuploader.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileName {
	@JsonProperty(value="file_name")
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
