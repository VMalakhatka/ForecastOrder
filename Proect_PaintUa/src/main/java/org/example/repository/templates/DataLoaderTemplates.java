package org.example.repository.templates;

import jakarta.annotation.PostConstruct;
import org.example.entity.entityEnum.StockRole;
import org.example.entity.entityEnum.TypDocmPr;
import org.example.entity.templates.SetStockTtTemplate;
import org.example.entity.templates.StockTipSaleTemplate;
import org.example.entity.templates.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
public class DataLoaderTemplates {
    private final TemplateRepository templateRepository;
    @Autowired
    public DataLoaderTemplates(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @PostConstruct
    private void loadData() {
        List<Template> templateList = templateRepository.findAll();
        if (templateList.isEmpty()) {
            Template t= Template.builder().id(1L).orderForDay(30).name("Kiev Olymp").type("SUPPLIER").koefToRealSale(1.5).idMainStock(1).build();
            Template t1= Template.builder().id(2L).orderForDay(30).name("Kiev Olymp").type("DOCUMENT").koefToRealSale(1.5).idMainStock(1).build();
            templateList=List.of(t,t1);
            SetStockTtTemplate set1=SetStockTtTemplate.builder().idTT(1L).nameTT("Kiev Olymp").idStock(1L).nameStock("Kiev").role(StockRole.valueOf("PT"))
                            .isMin(true).isMax(true).isFasovka(true).build();
            SetStockTtTemplate set2=SetStockTtTemplate.builder().idTT(1L).nameTT("Kiev Olymp").idStock(19L).nameStock("Kiev Rasprodaga").role(StockRole.valueOf("Storage"))
                    .isMin(false).isMax(true).isFasovka(false).build();
            SetStockTtTemplate set3=SetStockTtTemplate.builder().idTT(2L).nameTT("Kiev Olymp").idStock(1L).nameStock("Kiev").role(StockRole.valueOf("PT"))
                    .isMin(true).isMax(true).isFasovka(true).build();
            SetStockTtTemplate set4=SetStockTtTemplate.builder().idTT(2L).nameTT("Kiev Olymp").idStock(19L).nameStock("Kiev Rasprodaga").role(StockRole.valueOf("Storage"))
                    .isMin(false).isMax(true).isFasovka(false).build();
            StockTipSaleTemplate tip1=StockTipSaleTemplate.builder().isEqual(true).orgPredm("РОЗНКИЕВ").typdocmPr(TypDocmPr.R).vidDoc("ALL").build();
            StockTipSaleTemplate tip2=StockTipSaleTemplate.builder().isEqual(true).orgPredm("ALL").typdocmPr(TypDocmPr.R).vidDoc("РАСХОДНИКИ").build();
            StockTipSaleTemplate tip3=StockTipSaleTemplate.builder().isEqual(true).orgPredm("ALL").typdocmPr(TypDocmPr.R).vidDoc("Мультисборка").build();
            StockTipSaleTemplate tip4=StockTipSaleTemplate.builder().isEqual(true).orgPredm("РОЗНКИЕВ").typdocmPr(TypDocmPr.R).vidDoc("ALL").build();
            StockTipSaleTemplate tip5=StockTipSaleTemplate.builder().isEqual(true).orgPredm("ALL").typdocmPr(TypDocmPr.R).vidDoc("РАСХОДНИКИ").build();
            StockTipSaleTemplate tip6=StockTipSaleTemplate.builder().isEqual(true).orgPredm("ALL").typdocmPr(TypDocmPr.R).vidDoc("Мультисборка").build();
            set1.setStockTipSaleTemplateHashSet(new HashSet<>());
            set1.addStockTipSaleTemplete(tip1);
            set1.addStockTipSaleTemplete(tip2);
            set1.addStockTipSaleTemplete(tip3);
            set3.setStockTipSaleTemplateHashSet(new HashSet<>());
            set3.addStockTipSaleTemplete(tip4);
            set3.addStockTipSaleTemplete(tip5);
            set3.addStockTipSaleTemplete(tip6);
            t.setSetStockTtTemplates(new HashSet<>());
            t.addSetStockTemplates(set1);
            t.addSetStockTemplates(set2);
            t1.setSetStockTtTemplates(new HashSet<>());
            t1.addSetStockTemplates(set3);
            t1.addSetStockTemplates(set4);
            templateRepository.saveAll(templateList);
        }
    }

}
