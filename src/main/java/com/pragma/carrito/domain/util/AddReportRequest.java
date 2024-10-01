package com.pragma.carrito.domain.util;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddReportRequest {
    private String email;
    private double totalCost;
    private LocalDate purchaseDate;
    private List<AddArticleReportRequest> articles;

}
