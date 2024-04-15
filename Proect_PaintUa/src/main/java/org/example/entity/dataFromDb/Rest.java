package org.example.entity.dataFromDb;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "rest")
@NoArgsConstructor
@Data
@ToString(exclude = {"good"})
@EqualsAndHashCode(exclude = {"good"})
public class Rest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Long id;
    @Column(name = "id_stock")
    private long idStock;
    @Column(name = "REZ_KOLCH")
    private double rezKolich;
    @Column(name = "KON_KOLCH")
    private double konKolich;
    @ManyToOne
    Goods good;

    public Rest(Long id, long idStock, double rezKolich, double konKolich) {
        this.id = id;
        this.idStock = idStock;
        this.rezKolich = rezKolich;
        this.konKolich = konKolich;
    }
}
