package com.sa.leanning.CarProject.Util;

import java.math.BigDecimal;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.http.HttpStatus;

import com.sa.leanning.CarProject.ApiException.ApiException;

public class ExcelUtils {

    public static Long getRequiredLong(Row row, int col, String fieldName) {
        Cell cell = row.getCell(col);
        if (cell == null || cell.getCellType() != CellType.NUMERIC) {
            throw new ApiException(
                HttpStatus.BAD_REQUEST,
                fieldName + " is required and must be numeric"
            );
        }
        return (long) cell.getNumericCellValue();
    }

    public static Integer getRequiredInt(Row row, int col, String fieldName) {
        Cell cell = row.getCell(col);
        if (cell == null || cell.getCellType() != CellType.NUMERIC) {
            throw new ApiException(
                HttpStatus.BAD_REQUEST,
                fieldName + " is required and must be numeric"
            );
        }
        int value = (int) cell.getNumericCellValue();
        if (value <= 0) {
            throw new ApiException(
                HttpStatus.BAD_REQUEST,
                fieldName + " must be greater than zero"
            );
        }
        return value;
    }

    public static BigDecimal getOptionalDecimal(Row row, int col) {
        Cell cell = row.getCell(col);
        if (cell == null) return null;
        if (cell.getCellType() != CellType.NUMERIC) {
            throw new ApiException(
                HttpStatus.BAD_REQUEST,
                "Price must be numeric"
            );
        }
        return BigDecimal.valueOf(cell.getNumericCellValue());
    }
}
