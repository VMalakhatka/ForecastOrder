package org.example.entity.forecast;

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

}
