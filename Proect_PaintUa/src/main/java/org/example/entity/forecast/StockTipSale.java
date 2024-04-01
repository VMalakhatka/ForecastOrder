package org.example.entity.forecast;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.entity.entity_enum.TypDocmPr;

@Entity
@Table(name = "stock_tip_sale")
@NoArgsConstructor
@Data
@ToString(exclude = {"setStockTT"})
@EqualsAndHashCode(exclude = {"setStockTT"})
public class StockTipSale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Long id;
    @Column(name = "ORG_PREDM")
    @Size(max = 8)
    private String orgPredm;
    @Column(name = "TYPDOCM_PR")
    @Enumerated(EnumType.STRING)
    private TypDocmPr typdocmPr;
    @Column(name = "VID_DOC")
    @Size(max = 20)
    private String vidDoc;
    @Column(name = "is_equal ")
    private boolean isEqual;
    @ManyToOne
    private SetStockTT setStockTT;
}
