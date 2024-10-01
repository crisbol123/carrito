package com.pragma.carrito.adapters.driven.feigns.adapter;

import com.pragma.carrito.adapters.driven.feigns.clients.ReportFeignClient;
import com.pragma.carrito.domain.util.AddReportRequest;
import com.pragma.carrito.domain.spi.ReportFeignClientPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReportFeignClientAdapter implements ReportFeignClientPort {
private final ReportFeignClient reportFeignClient;

    @Override
    public void generateReport(AddReportRequest addReportRequest) {
        reportFeignClient.createReport(addReportRequest);
    }
}
