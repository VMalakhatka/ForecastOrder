package org.example.entity.data.from.db;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "assembl")
@NoArgsConstructor
@Data
@ToString(exclude = {"parentGood","childGood"})
@EqualsAndHashCode(exclude = {"parentGood","childGood"})
public class Assemble {
    @Id
 //   @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assemble_sequence_generator")
    @SequenceGenerator(name = "assemble_sequence_generator", sequenceName = "assemble_sequence", allocationSize = 1)

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
