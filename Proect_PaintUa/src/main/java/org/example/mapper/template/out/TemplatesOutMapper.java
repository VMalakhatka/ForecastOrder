package org.example.mapper.template.out;

import org.example.dto.template.out.SetStockTtTemplateOutDTO;
import org.example.dto.template.out.StockTipSaleTemplateOutDTO;
import org.example.dto.template.out.TemplateOutDTO;
import org.example.entity.templates.SetStockTtTemplate;
import org.example.entity.templates.StockTipSaleTemplate;
import org.example.entity.templates.Template;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
@Component
public class TemplatesOutMapper {
    public TemplateOutDTO toTemplateOutDTO(Template t) {
        return new TemplateOutDTO(
                t.getId(),t.getStartAnalysis(),t.getEndAnalysis(),t.getOrderForDay(),t.getName(),
                t.getType(),t.getKoefToRealSale(),
                t.getSetStockTtTemplates().stream().map(this::toSetStockTtTemplateOutDTO).collect(Collectors.toList())
        );
    }
    public SetStockTtTemplateOutDTO toSetStockTtTemplateOutDTO(SetStockTtTemplate set){
        return new SetStockTtTemplateOutDTO(
                set.getId(), set.getIdTT(), set.getNameTT(), set.getIdStock(), set.getNameStock(), set.getRole(),
                set.isMin(), set.isMax(), set.isFasovka(),
                set.getStockTipSaleTemplateHashSet().stream().
                        map(this::toStockTipSaleTemplateOutDTO).collect(Collectors.toList())
        );
    }
    public StockTipSaleTemplateOutDTO toStockTipSaleTemplateOutDTO(StockTipSaleTemplate tip){
        return new StockTipSaleTemplateOutDTO(
                tip.getId(), tip.getOrgPredm(), tip.getTypdocmPr(), tip.getVidDoc(), tip.isEqual());
    }
}
