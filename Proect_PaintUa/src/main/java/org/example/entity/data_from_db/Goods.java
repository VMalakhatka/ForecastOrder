package org.example.entity.data_from_db;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.entity.forecast.Forecast;
import org.example.entity.forecast.ForecastTemplate;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "goods")
@NoArgsConstructor
@Data
@ToString(exclude = {"forecast","forecastTemplate","goodsMoveSet",
        "restSet","assemblePerentSet","assembleChild","stockParams"})
@EqualsAndHashCode(exclude = {"forecast","forecastTemplate","goodsMoveSet",
        "restSet","assemblePerentSet","assembleChild","stockParams"})
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Long id;
    @Column(name = "COD_ARTIC")
    @Size(max = 20)
    private String codArtic;
    @Column(name = "NAME_ARTIC")
    @Size(max = 200)
    private String nameArtic;
    @Column(name = "UCHET_CENA")
    private double uchetCena;
    @Column(name = "CENA_VALT")
    private double cenaValt;
    @Column(name = "COD_VALT")
    @Size(max = 4)
    private String codValt;
    @Column(name = "EDN_V_UPAK")
    private double ednVUpak;
    @Column(name = "DOP2_ARTIC")
    @Size(max = 50)
    private String supplier;// supplier
    @Column(name = "FROST")
    private boolean frost;
    @Column(name = "ASSEMBL")
    private int assembl;

    public Goods(Long id, String codArtic, String nameArtic, double uchetCena, double cenaValt,
                 String codValt, double ednVUpak, String supplier,
                 boolean frost, int assembl) {
        this.id = id;
        this.codArtic = codArtic;
        this.nameArtic = nameArtic;
        this.uchetCena = uchetCena;
        this.cenaValt = cenaValt;
        this.codValt = codValt;
        this.ednVUpak = ednVUpak;
        this.supplier = supplier;
        this.frost = frost;
        this.assembl = assembl;
    }

    @Embedded
    private Forecast forecast;

    @ManyToOne
    private ForecastTemplate forecastTemplate;

    @OneToMany(
            mappedBy = "good",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<StockParam> stockParams=new HashSet<>();;

    public void addStockParam(StockParam stockParam){
        stockParams.add(stockParam);
        stockParam.setGood(this);
    }

    public void remoteStockParam(StockParam stockParam){
        stockParams.remove(stockParam);
        stockParam.setGood(null);
    }

    @OneToMany(
            mappedBy = "good",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<GoodsMove> goodsMoveSet=new HashSet<>();

    public void addGoodsMove(GoodsMove goodsMove){
        goodsMoveSet.add(goodsMove);
        goodsMove.setGood(this);
    }
    public void remoteGoodsMove(GoodsMove goodsMove){
        goodsMoveSet.remove(goodsMove);
        goodsMove.setGood(null);
    }

    @OneToMany(
            mappedBy = "good",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Rest> restSet = new HashSet<>();
    public void addRest(Rest rest){
        restSet.add(rest);
        rest.setGood(this);
    }
    public void remote(Rest rest){
        restSet.remove(rest);
        rest.setGood(null);
    }

    @OneToMany(
            mappedBy = "parentGood",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )

    private Set<Assemble> assemblePerentSet = new HashSet<>();

    public void addAssembleParentSet(Assemble assemble){
        assemblePerentSet.add(assemble);
        assemble.setParentGood(this);
    }
    public void remoteAssembleParentSet(Assemble assemble){
        assemblePerentSet.remove(assemble);
        assemble.setParentGood(null);
    }

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "childGood")
    private Assemble assembleChild;
}
