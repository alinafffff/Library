package ru.fedorova.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.fedorova.library.dto.BookDTO;
import ru.fedorova.library.service.ExcelReportService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelReportServiceImpl implements ExcelReportService {
    private static final Logger logger = LoggerFactory.getLogger(ExcelReportServiceImpl.class);
    @Override
    public ByteArrayInputStream generateExcelReport(List<BookDTO> books) {
        logger.info("Generate excel report");
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Books");

            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Name", "Author", "ISBN", "Release Year"};

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Font font = workbook.createFont();
            font.setColor(IndexedColors.WHITE.getIndex());
            headerCellStyle.setFont(font);

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowIdx = 1;
            for (BookDTO book : books) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(book.getId());
                row.createCell(1).setCellValue(book.getName());
                row.createCell(2).setCellValue(book.getAuthor());
                row.createCell(3).setCellValue(book.getIsbn());
                row.createCell(4).setCellValue(book.getReleaseYear().toString());
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("Failed to generate Excel report", e);
        }
    }

}
