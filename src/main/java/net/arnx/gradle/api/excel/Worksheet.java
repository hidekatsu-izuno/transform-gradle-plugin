package net.arnx.gradle.api.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;

public class Worksheet {
    private Workbook workbook;
    private Sheet sheet;

    public Worksheet(Workbook workbook, Sheet sheet) {
        this.workbook = workbook;
        this.sheet = sheet;
    }

    public int getFirstRowIndex() {
        return sheet.getFirstRowNum() + 1;
    }

    public int getLastRowIndex() {
        return sheet.getLastRowNum() + 1;
    }

    public Cell getCell(String ref) {
        CellReference cr = new CellReference(ref);
        if (cr.getSheetName() != null) {
            throw new IllegalArgumentException("SheetName cannot set.");
        }
        return getCell(cr.getRow() + 1, cr.getCol() + 1);
    }

    public Cell getCell(int row, int col) {
        Row r = sheet.getRow(row - 1);
        if (r == null) {
            return Cell.BLANK;
        }

        org.apache.poi.ss.usermodel.Cell c = r.getCell(col - 1);
        if (c == null) {
            return Cell.BLANK;
        }

        return new Cell(workbook, c);
    }
}