package attachmentuploader.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import attachmentuploader.model.FileWrapper;
import attachmentuploader.model.XLSXFileWrapper;
import attachmentuploader.model.XLSXFileWrapper.RowData;

@Service
public class ExcelFileReader {
	
	public FileWrapper parseExcelFile(MultipartFile file) {
		String fileError = "";
		FileWrapper topWrapper = new FileWrapper();
		List<XLSXFileWrapper> listXLSXFileWrapper = new ArrayList<XLSXFileWrapper>();
		FileInputStream fis = null;	
		try {
				InputStream in = file.getInputStream();
				File currDir = new File(".");
			    String path = currDir.getAbsolutePath();
			    String fileLocation = path.substring(0, path.length() - 1) + file.getOriginalFilename();
			    FileOutputStream f = new FileOutputStream(fileLocation);
			    int ch = 0;
			    while ((ch = in.read()) != -1) {
			        f.write(ch);
			    }
			    f.flush();
			    f.close();
			    
			    fis = new FileInputStream(fileLocation);
			    System.out.println(fileLocation + "====fileLocation======" + fis );
			    //fis = new FileInputStream("/Users/manmeetmanethiya/Downloads/TestData.xlsx");
				Workbook workbook = new XSSFWorkbook(fis);
                int numberOfSheets = workbook.getNumberOfSheets(); 
                //looping over each workbook sheet
                for (int i = 0; i < numberOfSheets; i++) {
                	XLSXFileWrapper wrapperObj = new XLSXFileWrapper();
                    Sheet sheet = workbook.getSheetAt(i);
                    wrapperObj.setSheetName(sheet.getSheetName());
                    Iterator<Row> rowIterator = sheet.iterator();
                    
                    int rowCount = 0;
                    List<String> headerValues = new ArrayList<String>();
                    List<String> rowCommaSeparatedValues = new ArrayList<String>();
                    List<XLSXFileWrapper.Row> listRows = new ArrayList<XLSXFileWrapper.Row>();
                    //iterating over each row
                    while (rowIterator.hasNext()) {
                    	if(rowCount == 0) {
                        	setHeaderValues(rowIterator, headerValues);
                        	wrapperObj.setHeaderValues(headerValues);
                        }else {
                        	
                        	XLSXFileWrapper.Row rowObj = wrapperObj.new Row();
                        	List<XLSXFileWrapper.RowData> listRowsData = new ArrayList<XLSXFileWrapper.RowData>();
                        	Row row = rowIterator.next();
                            Iterator<Cell> cellIterator = row.cellIterator();
                            int columnCount = 0;
                            String commaSeparateValues = "";
                            while(cellIterator.hasNext()){
                            	if(columnCount >= 0 &&  columnCount < headerValues.size()) {
	                            	Cell celldata=(Cell) cellIterator.next();
	                            	XLSXFileWrapper.RowData rowdata = wrapperObj.new RowData();
	                            	rowdata.setKey(headerValues.get(columnCount));
	                                switch(celldata.getCellType()){
		                                case STRING:
		                                	rowdata.setValue(celldata.getStringCellValue());
		                                	listRowsData.add(rowdata);
		                                	rowObj.setRowData(listRowsData);
		                                	commaSeparateValues += celldata.getStringCellValue() + "::";
		                                    break;
		                                case NUMERIC:
		                                	rowdata.setValue(String.valueOf(celldata.getNumericCellValue()));
		                                	listRowsData.add(rowdata);
		                                	rowObj.setRowData(listRowsData);
		                                	commaSeparateValues += String.valueOf(celldata.getNumericCellValue()) + "::";
		                                    break;
		                                case BOOLEAN:
		                                	rowdata.setValue(String.valueOf(celldata.getBooleanCellValue()));
		                                	listRowsData.add(rowdata);
		                                	rowObj.setRowData(listRowsData);
		                                	commaSeparateValues += String.valueOf(celldata.getBooleanCellValue()) + "::";
		                                    break;
		    							default:
		    								break;
	                                }
	                            }
                            	columnCount++;
                            }
                            listRows.add(rowObj);
                            rowCommaSeparatedValues.add(commaSeparateValues);
                            wrapperObj.setRowCommaSeparatedValues(rowCommaSeparatedValues);
                            wrapperObj.setRows(listRows);
                        }
                        rowCount++;
                    }
                    listXLSXFileWrapper.add(wrapperObj);
                }
                workbook.close();
            }catch(Exception ex){
            	fileError += ex.getMessage() + "===at ===";
            	ex.printStackTrace();
            }
		topWrapper.setErrorMessage(fileError);
		topWrapper.setLstXlsxWrapperObj(listXLSXFileWrapper); 
		return topWrapper;
	}
	
	//method to fetch the headers
	private void setHeaderValues(Iterator<Row> rowIterator, List<String> headerValues) {
		Row row = rowIterator.next();
        Iterator<Cell> cellIterator = row.cellIterator();

        while(cellIterator.hasNext()){
            Cell celldata=(Cell) cellIterator.next();
	            switch(celldata.getCellType()){
	            case STRING:
	            	headerValues.add(celldata.getStringCellValue());
	                break;
	            case NUMERIC:
	                break;
	            case BOOLEAN:
	                break;
				default:
					break;
	            }
        }
	}
}
