package com.pragma.carrito.adapters.driven.feigns.clients;

import com.pragma.carrito.adapters.driven.feigns.FeignClientConfig;
import com.pragma.carrito.domain.util.AddReportRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "report-service", url = "${report.service.url}", configuration = FeignClientConfig.class)
public interface ReportFeignClient {

    @PostMapping("/report/create")
    void createReport(@RequestBody AddReportRequest addReportRequest);
}
