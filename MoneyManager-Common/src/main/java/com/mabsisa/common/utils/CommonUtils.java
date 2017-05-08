/**
 * 
 */
package com.mabsisa.common.utils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.mabsisa.common.model.CustomerDetail;

/**
 * @author abhinab
 *
 */
public class CommonUtils {
	
	private static final DateFormat dateFormat = new SimpleDateFormat("MMyyyy");
	
	public static boolean isEmpty(String str) {
		if (str == null || str.isEmpty()) {
			return true;
		}
		return false;
	}

	public static String getCurrentMMYYYY() {
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static String getFutureMMYYYY(int monthCount) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, monthCount);
		return dateFormat.format(cal.getTime());
	}
	
	public static String getLoggedInUserAccess() {
		Collection<? extends GrantedAuthority> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		String role = null;
		for (GrantedAuthority grantedAuthority : roles) {
			role = grantedAuthority.getAuthority();
		}
		return role;
	}
	
	private static String getStringFromExcel(Cell cell) {
		try {
			if (cell.getCellTypeEnum() == CellType.STRING) {
				return cell.getStringCellValue();
			} else {
				return String.valueOf(cell.getNumericCellValue());
			}
		} catch(Exception e) {
			return "";
		}
	}
	
	@SuppressWarnings("resource")
	public static List<CustomerDetail> generateCollectionDetails(File excel) throws IOException, InvalidFormatException {
	    Workbook workbook = new XSSFWorkbook(excel);
        Sheet datatypeSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = datatypeSheet.iterator();
        List<CustomerDetail> customerDetails = new ArrayList<CustomerDetail>();
        CustomerDetail customerDetail;
        while (iterator.hasNext()) {
			Row currentRow = (Row) iterator.next();
			if (currentRow.getRowNum() != 0) {
				Iterator<Cell> cellIterator = currentRow.iterator();
				customerDetail = new CustomerDetail();
	            int count  = 0;
	            while (cellIterator.hasNext()) {
					Cell currentCell = (Cell) cellIterator.next();
					if (count == 0) {
						customerDetail.setRegion(getStringFromExcel(currentCell));
					} else if (count == 1) {
						customerDetail.setBuilding(getStringFromExcel(currentCell));
					} else if (count == 2) {
						customerDetail.setAddress(getStringFromExcel(currentCell));
					} else if (count == 3) {
						customerDetail.setClient(getStringFromExcel(currentCell));
					} else if (count == 4) {
						customerDetail.setName(getStringFromExcel(currentCell));
					} else if(count == 5) {
						customerDetail.setFloor(getStringFromExcel(currentCell));
					} else if(count == 6) {
						try{
							if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
								customerDetail.setFee(BigDecimal.valueOf(currentCell.getNumericCellValue()).setScale(2, BigDecimal.ROUND_HALF_EVEN));
							} else {
								customerDetail.setFee(BigDecimal.valueOf(0).setScale(2, BigDecimal.ROUND_HALF_EVEN));
							}
						} catch (Exception e) {
							customerDetail.setFee(BigDecimal.valueOf(0).setScale(2, BigDecimal.ROUND_HALF_EVEN));
						}
					} else if(count == 7) {
						customerDetail.setMahal(getStringFromExcel(currentCell));
					} else if(count == 8) {
						customerDetail.setTelephone(getStringFromExcel(currentCell));
					} else if(count == 9) {
						customerDetail.setLeftTravel(getStringFromExcel(currentCell));
					} else if(count == 10) {
						customerDetail.setNote(getStringFromExcel(currentCell));
					}
	                count++;
				}
	            customerDetails.add(customerDetail);
			}
		}
		return customerDetails;
	}
	
}
