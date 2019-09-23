package net.arnx.transform.excel;

import java.lang.ref.WeakReference;
import java.util.AbstractList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.poi.ss.formula.ConditionalFormattingEvaluator;
import org.apache.poi.ss.formula.WorkbookEvaluatorProvider;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;

public class Workbook {
    private org.apache.poi.ss.usermodel.Workbook workbook;
    private Map<Sheet, WeakReference<Worksheet>> worksheets = new WeakHashMap<>();

    private DataFormatter df;
    private FormulaEvaluator fe;
    private ConditionalFormattingEvaluator cfe;

    public Workbook(org.apache.poi.ss.usermodel.Workbook workbook) {
        this.workbook = workbook;
        this.df = new DataFormatter(Locale.getDefault());
        this.fe = workbook.getCreationHelper().createFormulaEvaluator();
        this.cfe = new ConditionalFormattingEvaluator(workbook, (WorkbookEvaluatorProvider)this.fe);
    }

    private Worksheet getWorksheet(Sheet sheet) {
        if (sheet == null) {
            return null;
        }

        return worksheets.computeIfAbsent(sheet, s -> {
            return new WeakReference<Worksheet>(new Worksheet(this, s));
        }).get();
    }

    public Worksheet getWorksheet(String sheetName) {
        return getWorksheet(workbook.getSheet(sheetName));
    }

    public List<Worksheet> getWorksheets() {
        return new AbstractList<Worksheet>() {
            @Override
            public Worksheet get(int index) {
                return getWorksheet(workbook.getSheetAt(index));
            }

            @Override
            public int size() {
                return workbook.getNumberOfSheets();
            }
        };
    }

    Object evaluateCellValue(Cell cell) {
        CellValue cv = fe.evaluate(cell);
        switch (cv.getCellType()) {
        case BOOLEAN:
            return cv.getBooleanValue();
        case NUMERIC:
            return cv.getNumberValue();
        case STRING:
            return cv.getStringValue();
        default:
            return null;
        }
    }

    String formatCellValue(Cell cell) {
        return df.formatCellValue(cell, fe, cfe);
    }
}