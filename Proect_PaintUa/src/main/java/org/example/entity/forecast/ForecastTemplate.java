package org.example.entity.forecast;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.entity.dataFromDb.Goods;
import org.example.entity.entityEnum.TypeOfForecast;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "forecast_template")
@NoArgsConstructor
@Data
@ToString(exclude = {"setStockTTSet","goodsSet"})
@EqualsAndHashCode(exclude = {"setStockTTSet","goodsSet"})
public class ForecastTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Long id;
    @Column(name = "start_analysis")
    private LocalDateTime startAnalysis;
    @Column(name = "end_analysis")
    private LocalDateTime endAnalysis;
    @Column(name = "order_for_day")
    private int orderForDay;
    @Column(name = "name")
    @Size(max = 200)
    private String name;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TypeOfForecast type;
    @Column(name = "koef_to_real_sale ")
    private double koefToRealSale;
    @Column(name = "supplier ")
    @Size(max = 50)
    private String supplier;
    @Column(name = "id_main_stock")
    @Min(1)
    private long idMainStock;
    @OneToMany(
            mappedBy = "forecastTemplate",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<SetStockTT> setStockTTSet=new HashSet<>();
    public void addSetStockTT(SetStockTT set){
        setStockTTSet.add(set);
        set.setForecastTemplate(this);
    }
    public void remoteSetStockTT(SetStockTT set){
        setStockTTSet.remove(set);
        set.setForecastTemplate(null);
    }
    @OneToMany(
            mappedBy = "forecastTemplate",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Goods> goodsSet=new HashSet<>();
    public void addGoods(Goods goods){
        goodsSet.add(goods);
        goods.setForecastTemplate(this);
    }

    public void remoteGoods(Goods goods){
        goodsSet.remove(goods);
        goods.setForecastTemplate(null);
    }

    public boolean inForecastDate(LocalDateTime dateTime){
        return dateTime.isAfter(this.startAnalysis.minusDays(1)) && dateTime.isBefore(this.endAnalysis.plusDays(1));
    }
}
