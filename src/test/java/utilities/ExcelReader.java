package utilities;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.List;
import java.util.Map;

import java.io.FileInputStream;
import java.util.*;

public class ExcelReader {
    String filePath;

    public ExcelReader(String filePath) {
        this.filePath = filePath;
    }

    // This method will read all rows from the given sheet
    public List<Map<String, String>> readAllRows(String sheetName) {
        List<Map<String, String>> allRowsData = new ArrayList<>();  // List to store all row data maps

        try {
            FileInputStream fis = new FileInputStream(filePath);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet(sheetName);

            // Get the header row (first row) to use as keys for the map
            Row header = sheet.getRow(0);
            DataFormatter formatter = new DataFormatter();

            // Loop through all rows (starting from the second row since the first is the header)
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex); // Get the row

                // Create a map to store data for each row
                Map<String, String> rowData = new HashMap<>();

                // Loop through all columns (cells) in the row
                for (int cellIndex = 0; cellIndex < header.getLastCellNum(); cellIndex++) {
                    String key = header.getCell(cellIndex).getStringCellValue();  // Header value as key
                    String value = formatter.formatCellValue(row.getCell(cellIndex));  // Cell value as value
                    rowData.put(key, value); // Add the data to the map
                }

                // Add this row data map to the list of all rows
                allRowsData.add(rowData);
            }

            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return allRowsData;  // Return the list of all row data
    }
}


