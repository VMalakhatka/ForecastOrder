package org.example.mapper.data_from_db.out;

import org.example.dto.data_from_db.out.AssembleDtoOut;
import org.example.dto.data_from_db.out.GoodsDtoOut;
import org.example.dto.data_from_db.out.StockParamDtoOut;
import org.example.entity.data_from_db.Assemble;
import org.example.entity.data_from_db.Goods;
import org.example.entity.data_from_db.StockParam;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class DataFromDbMapper {
    public GoodsDtoOut toGoodsDtoOut(Goods g){
        return new GoodsDtoOut(g.getId(),g.getCodArtic(),g.getNameArtic(),g.getUchetCena(),g.getCenaValt(),g.getCodValt(),
                g.getEdnVUpak(),g.getSupplier(),g.isFrost(),g.getAssembl(),g.getForecast().getNotOnStock(),g.getForecast().getNotSaleAndSale(),
                g.getForecast().getSale(),g.getForecast().getRestTT(),g.getForecast().getNeedToMove(),g.getForecast().getOptForecast(),
                g.getForecast().getOrderWithoutPack(),g.getForecast().getOrderTT()
               // ,g.getForecastTemplate().getId()
                , g.getStockParams().stream().map(this::toStockParamDtoOut).collect(Collectors.toList())
                ,g.getAssemblePerentSet().stream().map(this::toAssembleDtoOut).collect(Collectors.toList())
                ,g.getAssembleChild()==null?null:toAssembleDtoOut(g.getAssembleChild())
                );
    }

    public StockParamDtoOut toStockParamDtoOut(StockParam param){
        return new StockParamDtoOut(param.getId(),param.getIdStock(),param.getMinTvrZap(),
                param.getMaxTvrZap(),param.getTipTov(),param.getTipOrder());
    }

    public AssembleDtoOut toAssembleDtoOut(Assemble assemble){
        return new AssembleDtoOut(assemble.getId(),assemble.getQuantity(),
                assemble.getParentGood().getId(),assemble.getChildGood().getId());
    }

}

