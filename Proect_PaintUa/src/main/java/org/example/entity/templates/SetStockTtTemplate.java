package org.example.entity.templates;

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
@Table(name = "set_stock_TT_template")
@NoArgsConstructor
@Data
@ToString(exclude = {"template", "stockTipSaleTemplateHashSet"})
@EqualsAndHashCode(exclude = {"template", "stockTipSaleTemplateHashSet"})
public class SetStockTtTemplate {
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
    private Template template;
    @OneToMany(
            mappedBy = "setStockTtTemplate",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<StockTipSaleTemplate> stockTipSaleTemplateHashSet =new HashSet<>();

    public void addStockTipSaleTemplete(StockTipSaleTemplate tip){
        stockTipSaleTemplateHashSet.add(tip);
        tip.setSetStockTtTemplate(this);
    }

    public void remoteStockTipSaleTemplete(StockTipSaleTemplate tip){
        stockTipSaleTemplateHashSet.remove(tip);
        tip.setSetStockTtTemplate(null);
    }

    public SetStockTtTemplate(Long id, long idTT, String nameTT, long idStock, String nameStock,
                              StockRole role, boolean isMin, boolean isMax, boolean isFasovka) {
        this.id = id;
        this.idTT = idTT;
        this.nameTT = nameTT;
        this.idStock = idStock;
        this.nameStock = nameStock;
        this.role = role;
        this.isMin = isMin;
        this.isMax = isMax;
        this.isFasovka = isFasovka;
    }
}
