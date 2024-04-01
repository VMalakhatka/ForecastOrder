package org.example.entity.data_from_db;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.entity_enum.TypDocmPr;

import java.time.LocalDateTime;

@Entity
@Table(name = "goods_move")
@NoArgsConstructor
@Data
public class GoodsMove {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Long id;
    @Column(name = "id_stock")
    private long idStock;
    @Column(name = "UNICUM_NUM")
    private double UnicumNum;
    @Column(name = "N_DOCUM")
    private long nDocum;
    @Column(name = "NUMDCM_DOP")
    @Size(max = 10)
    private String  numdcmDop;
    @Column(name = "ORG_PREDM")
    @Size(max = 8)
    private String orgPredm;
    @Column(name = "DATA")
    private LocalDateTime data;
    @Column(name = "quantity")
    private double quantity;
    @Column(name = "rest")
    private double rest;
    @Column(name = "TYPDOCM_PR")
    private TypDocmPr typDocmPr;
    @Column(name = "VID_DOC")
    @Size(max = 20)
    private String vidDoc;
    @ManyToOne
    private Goods good;

    public GoodsMove(Long id, long idStock, double unicumNum, long nDocum, String numdcmDop, String orgPredm, LocalDateTime data, double quantity, double rest, TypDocmPr typDocmPr, String vidDoc) {
        this.id = id;
        this.idStock = idStock;
        UnicumNum = unicumNum;
        this.nDocum = nDocum;
        this.numdcmDop = numdcmDop;
        this.orgPredm = orgPredm;
        this.data = data;
        this.quantity = quantity;
        this.rest = rest;
        this.typDocmPr = typDocmPr;
        this.vidDoc = vidDoc;
    }
}
