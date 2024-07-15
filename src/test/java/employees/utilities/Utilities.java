package employees.utilities;

import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Utilities {

    XSSFWorkbook xssfWorkbook;
    FileInputStream fileInputStream;
    String path = System.getProperty("user.dir") + "//testData//Employees_API_TestData.xlsx";

    public int getRow(String sheetName) throws Exception {
        fileInputStream = new FileInputStream(path);
        xssfWorkbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = xssfWorkbook.getSheet(sheetName);
        int rowNum = sheet.getLastRowNum();
        return rowNum;
    }

    public int getCell(String sheetName) throws Exception {
        fileInputStream = new FileInputStream(path);
        xssfWorkbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = xssfWorkbook.getSheet(sheetName);
        int lastCellNum = sheet.getRow(0).getLastCellNum();
        return lastCellNum;
    }

    public String getSheetData(String sheetName, int rowNum, int cellNum) throws Exception {
        fileInputStream = new FileInputStream(path);
        xssfWorkbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = xssfWorkbook.getSheet(sheetName);
        XSSFRow row = sheet.getRow(rowNum);
        XSSFCell cell = row.getCell(cellNum);
        DataFormatter dataFormatter = new DataFormatter();
        String data = dataFormatter.formatCellValue(cell);
        return data;
    }
}
