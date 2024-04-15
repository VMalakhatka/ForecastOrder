package org.example.entity.dataFromDb;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "stock_param")
@NoArgsConstructor
@Data
@ToString(exclude = {"good"})
@EqualsAndHashCode(exclude = {"good"})
public class StockParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Long id;
    @Column(name = "id_stock")
    private long idStock;
    @Column(name = "MIN_TVRZAP")
    private double minTvrZap;
    @Column(name = "MAX_TVRZAP")
    private double maxTvrZap;
    @Column(name = "TIP_TOV")
    private int tipTov;
    @Column(name = "TIP_ORDER")
    private int tipOrder;

    public StockParam(Long id, long idStock, double minTvrZap, double maxTvrZap, int tipTov, int tipOrder) {
        this.id = id;
        this.idStock = idStock;
        this.minTvrZap = minTvrZap;
        this.maxTvrZap = maxTvrZap;
        this.tipTov = tipTov;
        this.tipOrder = tipOrder;
    }
    @ManyToOne
    private Goods good;
}
