package org.example.entity.forecast;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Forecast {

    @Column(name = "not_on_stock")
    private double notOnStock;
    @Column(name = "notsale")
    private double notSaleAndSale;
    @Column(name = "sale")
    private double sale;
    @Column(name = "rest_tt")
    private double restTT;
    @Column(name = "need_to_move")
    private double needToMove;
    @Column(name = "opt_forecast")
    private double optForecast;
    @Column(name = "order_without_pack")
    private double orderWithoutPack;
    @Column(name = "order_TT")
    private double orderTT;

    @PostConstruct
    public void ForecastPost() {
        this.notOnStock = 0.0;
        this.notSaleAndSale = 0.0;
        this.sale = 0.0;
        this.restTT = 0.0;
        this.needToMove = 0.0;
        this.optForecast = 0.0;
        this.orderWithoutPack = 0.0;
        this.orderTT = 0.0;
    }
}
