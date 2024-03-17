package mx.edu.utex.APREHO.controllers.RatesControllers.Dto;


import lombok.Data;
import mx.edu.utex.APREHO.model.rates.Rates;

import java.time.LocalDate;

@Data
public class DtoRates {

    private Long ratesId;
    private String season;
    private Double price;
    private LocalDate startDate;
    private LocalDate endDate;

    public Rates toEntity() {
     return  new Rates(season,price,startDate,endDate);
    }
}
