package org.example.entity.forecast;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.entity.entity_enum.StockRole;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "set_stock_TT")
@NoArgsConstructor
@Data
@ToString(exclude = {"forecastTemplate","stockTipSaleSet"})
@EqualsAndHashCode(exclude = {"forecastTemplate","stockTipSaleSet"})
public class SetStockTT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Long id;
    @Column(name = "id_TT")
    private long idTT;
    @Column(name = "name_TT")
    @Size(min = 1, max = 40)
    private String nameTT;
    @Column(name = "id_Stock")
    @Min(1)
    private long idStock;
    @Column(name = "name_stock")
    @Size(max = 200)
    private String nameStock;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private StockRole role;
    @Column(name = "is_min")
    private boolean isMin;
    @Column(name = "is_max")
    private boolean isMax;
    @Column(name = "is_fasovka")
    private boolean isFasovka;

    @ManyToOne
    private ForecastTemplate forecastTemplate;

    @OneToMany(
            mappedBy = "setStockTT",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<StockTipSale> stockTipSaleSet=new HashSet<>();

    public void addStockTipSale(StockTipSale tip){
        stockTipSaleSet.add(tip);
        tip.setSetStockTT(this);
    }

    public void remoteStockTipSale(StockTipSale tip){
        stockTipSaleSet.remove(tip);
        tip.setSetStockTT(null);
    }
}
