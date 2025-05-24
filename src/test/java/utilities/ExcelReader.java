package utilities;


	

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;	import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
	
//
//		

		public class ExcelReader {
			
		    private static final String FILE_PATH = ConfigReader.getProperty("filePath");

		    public static Map<String, String> getDataByScenario(String sheetName, String scenarioType) {
		        Map<String, String> dataMap = new HashMap<>();
		        IOUtils.setByteArrayMaxOverride(200_000_000);

		        try (FileInputStream fis = new FileInputStream(FILE_PATH);
		             Workbook workbook = new XSSFWorkbook(fis)) {

		            Sheet sheet = workbook.getSheet(sheetName);
		            if (sheet == null) throw new RuntimeException("Sheet not found: " + sheetName);

		            Row headerRow = sheet.getRow(0);
		            int scenarioCol = -1;
		            int colCount = headerRow.getLastCellNum();

		            // Find column index of "Scenario"
		            for (int i = 0; i < colCount; i++) {
		                if (headerRow.getCell(i).getStringCellValue().trim().equalsIgnoreCase("scenarioType")) {
		                    scenarioCol = i;
		                    break;
		                }
		            }

		            if (scenarioCol == -1) throw new RuntimeException("'Scenario' column not found in sheet: " + sheetName);

		            // Find the matching scenario row
		            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
		                Row row = sheet.getRow(i);
		                if (row == null || row.getCell(scenarioCol) == null) continue;

		                String currentScenario = row.getCell(scenarioCol).toString().trim();
		                if (currentScenario.equalsIgnoreCase(scenarioType)) {
		                    for (int j = 0; j < colCount; j++) {
		                        if (j == scenarioCol) continue; // skip scenario column
		                        String key = headerRow.getCell(j).getStringCellValue().trim();
		                        String value = row.getCell(j) != null ? row.getCell(j).toString().trim() : "";
		                        dataMap.put(key, value);
		                    }
		                    break;
		                }
		            }

		        } catch (IOException e) {
		            e.printStackTrace();
		        }

		        return dataMap;
		    }
		   
				
			}
		    
		