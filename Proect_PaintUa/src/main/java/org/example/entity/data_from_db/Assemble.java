package org.example.entity.data_from_db;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "assembl")
@NoArgsConstructor
@Data
public class Assemble {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Long id;
    @Column(name = "quantity")
    private double quantity;

    public Assemble(Long id, double quantity, Goods parentGood, Goods childGood) {
        this.id = id;
        this.quantity = quantity;
        this.parentGood = parentGood;
        this.childGood = childGood;
    }

    @ManyToOne
    private Goods parentGood;
    @OneToOne
    @MapsId
    private Goods childGood;
}
