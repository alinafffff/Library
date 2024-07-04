package ru.fedorova.library.service;

import ru.fedorova.library.dto.BookDTO;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface ExcelReportService {
    ByteArrayInputStream generateExcelReport(List<BookDTO> books);
}
