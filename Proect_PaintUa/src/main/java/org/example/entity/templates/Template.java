package org.example.entity.templates;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "template")
@NoArgsConstructor
@Data
@ToString(exclude = {"setStockTtTemplates"})
@EqualsAndHashCode(exclude = {"setStockTtTemplates"})
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Long id;
    @Column(name = "start_analysis")
    private LocalDateTime startAnalysis;
    @Column(name = "end_analysis")
    private LocalDateTime endAnalysis;
    @Column(name = "order_for_day")
    private int orderForDay;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;
    @Column(name = "koef_to_real_sale ")
    private double koefToRealSale;
    @Column(name = "id_main_stock")
    @Min(1)
    private long idMainStock;
    @OneToMany(mappedBy = "template",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<SetStockTtTemplate> setStockTtTemplates=new HashSet<>();
    public void addSetStockTemplates(SetStockTtTemplate set){
        setStockTtTemplates.add(set);
        set.setTemplate(this);
    }
    public void removeSetStockTemplates(SetStockTtTemplate set){
        setStockTtTemplates.remove(set);
        set.setTemplate(null);
    }


}
