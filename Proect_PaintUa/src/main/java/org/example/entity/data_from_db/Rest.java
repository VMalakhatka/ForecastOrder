package org.example.entity.data_from_db;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rest")
@NoArgsConstructor
@Data
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
