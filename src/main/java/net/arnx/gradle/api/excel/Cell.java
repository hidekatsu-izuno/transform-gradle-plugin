package net.arnx.gradle.api.excel;

public class Cell {
    public static final Cell BLANK = new Cell(null, null);

    private Workbook workbook;
    private org.apache.poi.ss.usermodel.Cell cell;

    public Cell(Workbook workbook, org.apache.poi.ss.usermodel.Cell cell) {
        this.workbook = workbook;
        this.cell = cell;
    }

    public Object getValue() {
        if (cell == null) {
            return null;
        }
        return workbook.evaluateCellValue(cell);
    }

    public String getText() {
        if (cell == null) {
            return "";
        }
        return workbook.formatCellValue(cell);
    }
}