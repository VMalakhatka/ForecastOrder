package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.example.dto.data_from_db.out.GoodsDtoOut;
import org.example.dto.forecast.out.ForecastTemplateDtoOut;
import org.example.exeption.DataNotValid;
import org.example.exeption.NotFindByID;
import org.example.mapper.data_from_db.out.DataFromDbMapper;
import org.example.mapper.forecast.out.ForecastOutMapper;
import org.example.service.MakeForecastOnSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Running the forecast",description = "run forecast by template ID and supplier, output results")
@Validated
@RequestMapping(path = "/forecast")
@RestController
public class ForecastController {

    MakeForecastOnSupplierService makeForecastOnSupplierService;
    ForecastOutMapper forecastOutMapper;
    DataFromDbMapper dataFromDbMapper;

    @Autowired
    public ForecastController(MakeForecastOnSupplierService makeForecastOnSupplierService,
                              ForecastOutMapper forecastOutMapper,
                              DataFromDbMapper dataFromDbMapper) {
        this.makeForecastOnSupplierService = makeForecastOnSupplierService;
        this.forecastOutMapper = forecastOutMapper;
        this.dataFromDbMapper = dataFromDbMapper;
    }


    @Operation(
            summary = "By template number and supplier - starts generation of a new forecast",
            description = "The following templates are given for client selection"
    )
    @PostMapping("{id}/{supplier}")
    public ForecastTemplateDtoOut runForecast(@PathVariable("id") @Min(0)
                                                  @Parameter(description = "the template ID that should be used to forecast")
                                                  long id,
                                              @PathVariable("supplier") @Size(min = 1,max = 8)
                                              @Parameter(description = "goods of this supplier will participate in the forecast")
                                              String supplier
                                              ){

        try {
            return forecastOutMapper.toForecastTemplateDtoOut(makeForecastOnSupplierService.run(id,supplier));
        } catch (DataNotValid e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(
            summary = "All existing templates",
            description = "The following templates are given for client selection"
    )
    @GetMapping()
    public List<ForecastTemplateDtoOut> getAllForecastHeader(){
        return makeForecastOnSupplierService.getAllForecast().stream().
                map(f->forecastOutMapper.toForecastTemplateDtoOut(f)).collect(Collectors.toList());
    }

    @Operation(
            summary = "Id Forecast Header",
            description = "This is all data copied from the forecast template + supplier , also possibly replaced dates - if they were null"
    )
    @GetMapping("{id}")
    public ForecastTemplateDtoOut getForecastHeader(@PathVariable("id") @Min(0) @Parameter(description = "Forecast Id") long id){
        try {
            return forecastOutMapper.toForecastTemplateDtoOut(makeForecastOnSupplierService.getForecastById(id));
        } catch (NotFindByID e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("forecast_goods/{id}")
    @Operation(
            summary = "List of goods by Forecast Id",
            description = "List of products linked to the forecast header"
    )
    public List<GoodsDtoOut> getForecastGoods(@PathVariable("id") @Min(0) @Parameter(description = "Forecast Id") long id){
        try {
            return makeForecastOnSupplierService.getGoodsListByForecastId(id).stream().
                    map(g->dataFromDbMapper.toGoodsDtoOut(g)).collect(Collectors.toList());
        } catch (NotFindByID e) {
            throw new RuntimeException(e);
        }
    }


}
