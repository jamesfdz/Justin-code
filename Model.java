package justin;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class Model {
	public int lastCellNumber;
	public int lastRowNumber;
	
	public int getLastCellNumber() {
		return lastCellNumber;
	}
	
	public void setLastCellNumber(Row headerRow) {
		int lastCellIndex = headerRow.getLastCellNum();
		
		if(lastCellIndex > 0) {
			
			for(; lastCellIndex >= 0; lastCellIndex--) {
				Cell lastcell = headerRow.getCell(lastCellIndex);
				if(lastcell != null) {
//					model.setLastCellNumber(lastCellIndex);
					this.lastCellNumber = lastCellIndex;
					break;
				}
			}
		}
	}
	
	public int getLastRowNumber() {
		return lastRowNumber;
	}
	
	public void setLastRowNumber(XSSFSheet currentSheet) {
		int lastRowIndex = currentSheet.getLastRowNum();
		
		if(lastRowIndex > 0) {
			
			for(; lastRowIndex >= 0; lastRowIndex--) {
				Row row = currentSheet.getRow(lastRowIndex);
				if(row != null) {
					Cell cell = row.getCell(0);
					if(cell != null) {
//						model.setLastRowNumber(cell.getRowIndex());
						this.lastRowNumber = lastRowIndex;
						break;
					}
				}
				
			}
		}
	}
}
