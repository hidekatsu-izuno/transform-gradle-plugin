package net.arnx.transform.gradle.filecopydetails;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.gradle.api.UncheckedIOException;
import org.gradle.api.file.FileCopyDetails;

public class ExcelFileCopyDetails extends AbstractFileCopyDetails {
    private Workbook workbook;

    public ExcelFileCopyDetails(FileCopyDetails parent) {
        super(parent);
    }

    public Workbook getWorkbook() {
        if (workbook == null) {
            try {
                workbook = WorkbookFactory.create(getFile());
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        return workbook;
    }
}