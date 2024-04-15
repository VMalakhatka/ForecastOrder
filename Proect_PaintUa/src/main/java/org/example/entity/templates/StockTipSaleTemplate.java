package org.example.entity.templates;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.entity.entityEnum.TypDocmPr;

@Entity
@Table(name = "stock_tip_sale_templete")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
@ToString(exclude = {"setStockTtTemplate"})
@EqualsAndHashCode(exclude = {"setStockTtTemplate"})

public class StockTipSaleTemplate {
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
    private SetStockTtTemplate setStockTtTemplate;

    public StockTipSaleTemplate(Long id, String orgPredm, TypDocmPr typdocmPr, String vidDoc, boolean isEqual) {
        this.id = id;
        this.orgPredm = orgPredm;
        this.typdocmPr = typdocmPr;
        this.vidDoc = vidDoc;
        this.isEqual = isEqual;
    }
}
