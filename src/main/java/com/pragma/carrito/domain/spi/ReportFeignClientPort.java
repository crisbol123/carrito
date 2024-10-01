package com.pragma.carrito.domain.spi;

import com.pragma.carrito.domain.util.AddReportRequest;

public interface ReportFeignClientPort {
    void generateReport(AddReportRequest addReportRequest);
}
