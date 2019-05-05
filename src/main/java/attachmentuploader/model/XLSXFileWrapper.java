package attachmentuploader.model;

import java.util.List;

public class XLSXFileWrapper {
	private String sheetName;
	private List<Row> rows;
	
	public List<Row> getRows() {
		return rows;
	}

	public void setRows(List<Row> rows) {
		this.rows = rows;
	}

	private List<String> rowCommaSeparatedValues;
	private List<String> headerValues;
	
	
	public List<String> getHeaderValues() {
		return headerValues;
	}

	public void setHeaderValues(List<String> headerValues) {
		this.headerValues = headerValues;
	}

	public List<String> getRowCommaSeparatedValues() {
		return rowCommaSeparatedValues;
	}

	public void setRowCommaSeparatedValues(List<String> rowCommaSeparatedValues) {
		this.rowCommaSeparatedValues = rowCommaSeparatedValues;
	}
	
	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	
	public class Row{
		private List<RowData> rowData;
		
		public List<RowData> getRowData() {
			return rowData;
		}

		public void setRowData(List<RowData> rowData) {
			this.rowData = rowData;
		}

	}
	
	public class RowData{
		private String key;
		private String value;
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
	}
}
