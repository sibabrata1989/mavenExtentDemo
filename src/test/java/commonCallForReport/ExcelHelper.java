package commonCallForReport;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;


import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHelper
{
	private ArrayList<String> listHeader;// 1D Array
	private ArrayList<ArrayList<String>> listData;// 2D Array

	public ExcelHelper()
	{
		listHeader = new ArrayList<String>();
		listData = new ArrayList<ArrayList<String>>();
	}

	public void SetListHeader(String fileName, int headerIndex)// File Name,
																// header Index
																// = 0
	{
		try
		{
			FileInputStream fis = new FileInputStream(fileName);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheet("Sheet1");
			XSSFRow headerrow = sheet.getRow(headerIndex);

			for (int i = 0; i < headerrow.getLastCellNum(); i++)
			{
				XSSFCell cell = headerrow.getCell(i);
				if (cell == null)
				{
					listHeader.add("");
					// System.out.println("");
				} else
				{
					listHeader.add(String.valueOf(cell));
					// System.out.println(String.valueOf(cell));
				}

			}
			System.out.println(listHeader);
			wb.close();
			fis.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void SetListData(String fileName, String tcName)// File name, TC Name
	{

		try
		{
			FileInputStream fis = new FileInputStream(fileName);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheet("Sheet1");

			for (int i = 0; i <= sheet.getLastRowNum(); i++)
			{
				XSSFRow dataRow = sheet.getRow(i);
				String xlTCname = String.valueOf(dataRow.getCell(0));

				if (xlTCname.equalsIgnoreCase(tcName))
				{
					ArrayList<String> tempData = new ArrayList<String>();
					for (int j = 0; j < dataRow.getLastCellNum(); j++)
					{
						XSSFCell cell = dataRow.getCell(j);
						if (cell == null)
						{
							tempData.add("");
						} else
						{
							tempData.add(String.valueOf(cell));
						}

					}
					listData.add(tempData);
				}
			}
			wb.close();
			fis.close();
			System.out.println(listData);

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public String GetValue(int rowIndex, String colName)
	{
		String value = "no value";
		try
		{
			int colindex = listHeader.indexOf(colName);// get the coloum index
														// based on the colum
														// name.
			value = listData.get(rowIndex).get(colindex);// stores the value
															// based on row row
															// and colindex
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return value;
	}

	public void ClearDataList()
	{
		listData.clear();
	}

	public void SetExcelValue(String fileName)// File name, TC Name
	{

		try
		{
			String tcName = this.GetValue(0, "TestCaseName");
			FileInputStream fis = new FileInputStream(fileName);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheet("Sheet1");

			for (int i = 0; i <= sheet.getLastRowNum(); i++)
			{
				XSSFRow dataRow = sheet.getRow(i);
				String xlTCname = String.valueOf(dataRow.getCell(0));

				if (xlTCname.equalsIgnoreCase(tcName))
				{

					for (int j = 0; j < dataRow.getLastCellNum(); j++)
					{
						XSSFCell cell = dataRow.getCell(j);
						if (cell == null)
						{
							cell = dataRow.createCell(j);
							cell.setCellValue(listData.get(0).get(j));
						} else
						{
							cell.setCellValue(listData.get(0).get(j));
						}

					}
					break;
				}
			}
			//wb.close();
			fis.close();
			FileOutputStream fos = new FileOutputStream(fileName);
			wb.write(fos);
			wb.close();

			System.out.println(listData);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String SetValue(int rowIndex, String colName, String value)
	{

		try
		{
			int colindex = listHeader.indexOf(colName);// get the coloum index
														// based on the colum
														// name.
			// value = listData.get(rowIndex).get(colindex);// stores the value
			// based on row row
			listData.get(rowIndex).set(colindex, value); // and colindex
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return value;
	}

}
