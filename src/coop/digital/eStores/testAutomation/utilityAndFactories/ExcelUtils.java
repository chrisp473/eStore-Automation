package coop.digital.eStores.testAutomation.utilityAndFactories;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import coop.digital.eStores.testAutomation.helpers.TestHelper;

public class ExcelUtils {
	private Sheet ExcelWSheet;
	private Workbook ExcelWBook;
	private Cell WSCell;
	private Row Row;

	private String filePath;
	private String sheetName;

	public ExcelUtils(String Path, String WorkSheetName) throws Exception {
		filePath = Path;
		sheetName = WorkSheetName;
		openWorkbook();
		openWorkSheet();
	}

	public ExcelUtils(String Path) throws Exception {
		filePath = Path;
		openWorkbook();
	}

	/*
	 * Constructor for creating a new workbook (no Excel file)
	 */
	public ExcelUtils(String Path, boolean xlsx) throws Exception {
		filePath = Path;
		if (xlsx) {
			ExcelWBook = new XSSFWorkbook();
		} else {
			ExcelWBook = new HSSFWorkbook();
		}
	}

	public void openWorkbook() throws Exception {
		try {
			// ExcelWBook = WorkbookFactory.create(new File(filePath));
			ExcelWBook = WorkbookFactory.create(new FileInputStream(filePath));
		} catch (AssertionError | Exception e) {
			TestHelper.handleExceptionNoRetry(String.format(
					"An error occurred loading excel workbook from %s: ",
					filePath)
					+ e.getMessage());
		}
	}

	public void openWorkSheet() throws Exception {
		try {
			ExcelWSheet = ExcelWBook.getSheet(sheetName);
		} catch (AssertionError | Exception e) {
			TestHelper.handleExceptionNoRetry(String.format(
					"An error occurred loading excel worksheet %s from %s: ",
					sheetName, filePath)
					+ e.getMessage());
		}
	}

	public void setWorksheet(String workSheetName) throws Exception {
		try {
			sheetName = workSheetName;
			ExcelWSheet = ExcelWBook.getSheet(sheetName);
		} catch (AssertionError | Exception e) {
			TestHelper.handleExceptionNoRetry(String.format(
					"An error occurred loading excel worksheet %s from %s: ",
					sheetName, filePath)
					+ e.getMessage());
		}
	}

	public void createWorksheet(String workSheetName) throws Exception {
		try {
			ExcelWBook.createSheet(workSheetName);
		} catch (AssertionError | Exception e) {
			TestHelper.handleExceptionNoRetry(String.format(
					"An error occurred loading excel worksheet %s from %s: ",
					sheetName, filePath)
					+ e.getMessage());
		}
	}

	private String getCellContents(Cell CurrentCell) {

		FormulaEvaluator evaluator = ExcelWBook.getCreationHelper()
				.createFormulaEvaluator();

		if (CurrentCell == null) {
			return "";
		} else {
			DataFormatter df = new DataFormatter();
			CellValue cellValue = evaluator.evaluate(CurrentCell);
			if (cellValue == null) {
				return "";
			} else {
				// switch (CurrentCell.getCellType()) {
				cellValue.formatAsString();
				switch (cellValue.getCellType()) {
				case Cell.CELL_TYPE_BLANK:
					return "";
				case Cell.CELL_TYPE_STRING:
//					return CurrentCell.getStringCellValue();
				case Cell.CELL_TYPE_NUMERIC:
					// convert contents to string and return the string
//					if (DateUtil.isCellDateFormatted(CurrentCell)) {
//						return (CurrentCell.getDateCellValue().toString());
//					}
//					return String.valueOf(CurrentCell.getNumericCellValue());
				case Cell.CELL_TYPE_FORMULA:
					// evaluator.evaluateFormulaCell(CurrentCell);
				case Cell.CELL_TYPE_BOOLEAN:
//					return String.valueOf(CurrentCell.getBooleanCellValue());
					return df.formatCellValue(CurrentCell, evaluator);
				case Cell.CELL_TYPE_ERROR:
					return "ERROR IN DATA"; //used in getCellData

				default:
					return "";
				}
			}
		}
	}

	public String getCellData(int rowNum, int colNum) throws Exception {
		boolean validData = true;
		try {

			Cell CurrentCell = ExcelWSheet.getRow(rowNum).getCell(colNum);

			String cellData = getCellContents(CurrentCell);

			if (cellData.equals("ERROR IN DATA") || cellData == null) {
				validData = false;
			}
			assertThat(validData, equalTo(true));
			return cellData;

		} catch (AssertionError | Exception e) {
			TestHelper
					.handleExceptionNoRetry(String
							.format("An error occurred retrieving data from cell in row %s, column %s: ",
									rowNum, colNum)
							+ e.getMessage());
			return null;
		}
	}

	public void setCellData(String value, int rowNum, int colNum)
			throws Exception {
		try {
			WSCell = ExcelWSheet.getRow(rowNum).getCell(colNum);

			if (WSCell == null) {
				ExcelWSheet.getRow(rowNum).createCell(colNum)
						.setCellValue(value);
			} else {
				WSCell.setCellValue(value);
			}
		} catch (AssertionError | Exception e) {
			TestHelper
					.handleExceptionNoRetry(String
							.format("An error occurred setting value for cell in row %s, column %s: ",
									rowNum, colNum)
							+ e.getMessage());
		}
	}

	public void setCellData(String value, String parameterName)
			throws Exception {
		int rowNum = 0;
		int colNum = 0;

		try {
			rowNum = Row.getRowNum();
			colNum = getColumnIndex(parameterName);
			setCellData(value, rowNum, colNum);
		} catch (AssertionError | Exception e) {
			TestHelper
					.handleExceptionNoRetry(String
							.format("An error occurred setting value for cell in row %s, column %s: ",
									rowNum, colNum)
							+ e.getMessage());
		}
	}

	public int getRowCount() throws Exception {
		try {
			return ExcelWSheet.getLastRowNum() + 1;
		} catch (AssertionError | Exception e) {
			TestHelper
					.handleExceptionNoRetry(String
							.format("An error occurred retrieving row count for worksheet %s: ",
									sheetName)
							+ e.getMessage());
			return 0;
		}
	}

	public int getColumnCount() throws Exception {
		try {
			// assume that first row contains all parameter names - therefore
			// can do column count on this row
			int firstRowNum = ExcelWSheet.getFirstRowNum();
			return ExcelWSheet.getRow(firstRowNum).getLastCellNum();
		} catch (AssertionError | Exception e) {
			TestHelper
					.handleExceptionNoRetry(String
							.format("An error occurred retrieving column count for worksheet %s: ",
									sheetName)
							+ e.getMessage());
			return 0;
		}
	}

	public void setRow(String columnName, String value) throws Exception {
		boolean rowFound = false;

		try {
			int colIndex = getColumnIndex(columnName);
			int numRows = getRowCount();

			for (int i = 0; i < numRows; i++) {
				String valueFound = getCellData(i, colIndex);

				if (valueFound.equals(value)) {
					rowFound = true;
					Row = ExcelWSheet.getRow(i);
					break;
				}
			}
			assertThat(rowFound, equalTo(true));
		} catch (AssertionError | Exception e) {
			TestHelper
					.handleExceptionNoRetry(String
							.format("An error occurred setting the row for column with header %s and value %s: ",
									columnName, value)
							+ e.getMessage());
		}
	}

	public void setRow(int rowNum) throws Exception {
		if (ExcelWSheet.getRow(rowNum) == null)
			createRow(rowNum);

		try {
			Row = ExcelWSheet.getRow(rowNum);
		} catch (AssertionError | Exception e) {
			TestHelper.handleExceptionNoRetry(String
					.format("An error occurred setting the row ;"
							+ e.getMessage()));
		}
	}

	public void createRow(int rowNum) throws Exception {
		try {
			ExcelWSheet.createRow(rowNum);
		} catch (AssertionError | Exception e) {
			TestHelper.handleExceptionNoRetry(String
					.format("An error occurred creating the row ;"
							+ e.getMessage()));
		}
	}

	public int getColumnIndex(String columnHeader) throws Exception {
		int colIndex = 0;
		boolean colFound = false;

		try {
			int firstRowNum = ExcelWSheet.getFirstRowNum();
			int numColumns = getColumnCount();

			for (int i = 0; i < numColumns; i++) {
				String colHeader = getCellData(firstRowNum, i);

				if (colHeader.equals(columnHeader)) {
					colIndex = i;
					colFound = true;
					break;
				}
			}
			assertThat(colFound, equalTo(true));
		} catch (AssertionError | Exception e) {
			TestHelper.handleExceptionNoRetry(String.format(
					"An error occurred retrieving column with header %s: ",
					columnHeader)
					+ e.getMessage());
		}

		return colIndex;
	}

	public String getParameter(String parameterName) throws Exception {
//		try {
			return getCellData(Row.getRowNum(), getColumnIndex(parameterName));
//		} catch (AssertionError | Exception e) {
//			TestHelper.handleExceptionNoRetry(String
//					.format("An error occurred retrieving data for %s: ",
//							parameterName)
//					+ e.getMessage());
//			return "";
//		}
	}

	public void CloseExcel() throws IOException {
		try {
			ExcelWBook.close();
		} catch (IOException e) {
			// add steps to handle exception
		}
	}

	public Collection<HashMap<String, String>> loadTestData() throws Exception {
		return loadExcelTestData("");

	}

	public Collection<HashMap<String, String>> loadTestData(String testCaseName)
			throws Exception {
		return loadExcelTestData(testCaseName);
	}

	private Collection<HashMap<String, String>> loadExcelTestData(
			String testCaseName) throws Exception {
		// add try/catch

		Collection<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

		int numRows = getRowCount();
		int numColumns = getColumnCount();

		List<String> dataNames = new ArrayList<String>();

		for (int j = 0; j < numColumns; j++) {
			// assume headers on first row - might want to make dynamic
			dataNames.add(getCellData(0, j));
		}

		// Start at one to skip header row...
		boolean testDataFound = false;
		for (int i = 1; i < numRows; i++) {
			List<String> dataValues = new ArrayList<String>();

			
			//------------ New Code
			if(!getCellData(i, 0).trim().equalsIgnoreCase(testCaseName)){
				continue;
			}
			
			testDataFound = true;
			for (int k = 0; k < numColumns; k++) {
				dataValues.add(getCellData(i, k));
			}
			
			HashMap<String, String> testCase = new HashMap<String, String>();

			// Add data from sheet
			for (int d = 0; d < numColumns; d++) {
				testCase.put(dataNames.get(d), dataValues.get(d));
			}
			
			
			data.add(testCase);
			break;
			//------------ New Code
			
			
			
//			for (int k = 0; k < numColumns; k++) {
////				System.out.println("Col Name " + getCellData(i, k));
//				dataValues.add(getCellData(i, k));
//			}
//			HashMap<String, String> testCase = new HashMap<String, String>();
//
//			// Add data from sheet
//			for (int d = 0; d < numColumns; d++) {
//				testCase.put(dataNames.get(d), dataValues.get(d));
//			}
//
//			// if no test case name specified then load all rows
//			if (testCaseName.trim().equals("")) {
//				data.add(testCase);
//			}
//
//			// assume test case name in first column - again could make dynamic
//			else if (dataValues.get(0).equals(testCaseName)) {
//				// Only add row required for test case
//				data.add(testCase);
//				break;
//			}
		}

		if (!testDataFound) {
			fail("No data found for test script " + testCaseName );			
		}

		return data;
	}

	public void saveWorkBook() throws Exception {
		try {
			FileOutputStream fos = new FileOutputStream(filePath);
			ExcelWBook.write(fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			TestHelper.handleExceptionNoRetry(e.getMessage());
		}
	}

	public void setCellStyle(CellStyle cellstyle) throws Exception {
		WSCell.setCellStyle(cellstyle);
	}

	public void setCell(String parameterName) throws Exception {
		int col = getColumnIndex(parameterName);
		if (Row.getCell(col) == null)
			Row.createCell(col);

		try {
			WSCell = Row.getCell(col);
		} catch (AssertionError | Exception e) {
			TestHelper.handleExceptionNoRetry(String
					.format("An error occurred setting the cell ;"
							+ e.getMessage()));
		}
	}

	public void setCell(int rowNum, int colNum) throws Exception {

		if (ExcelWSheet.getRow(rowNum).getCell(colNum) == null)
			ExcelWSheet.getRow(rowNum).createCell(colNum);

		try {
			WSCell = ExcelWSheet.getRow(rowNum).getCell(colNum);
		} catch (AssertionError | Exception e) {
			TestHelper.handleExceptionNoRetry(String
					.format("An error occurred setting the cell ;"
							+ e.getMessage()));
		}
	}

}
