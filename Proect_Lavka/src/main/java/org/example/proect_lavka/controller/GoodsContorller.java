package org.example.proect_lavka.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.example.proect_lavka.entity.SclArtc;
import org.example.proect_lavka.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Test get goods from Paint",description = "enter supplier and stock id")
@Validated
@RequestMapping(path = "/goods")
@RestController
public class GoodsContorller {

    GoodsService goodsService;
    @Autowired
    public GoodsContorller(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @GetMapping("{supp}/{id}")
    public List<SclArtc> getTemplateById(@PathVariable("supp") @Parameter(description = "supplier")
                                             @Size(min = 1,max = 8) String supp,
                                            @PathVariable("id") @Min(0)
                                            @Parameter(description = "the stock ID") long id){
        return goodsService.getGoodsBySupplierAndStockId(supp,id);
    }
}
