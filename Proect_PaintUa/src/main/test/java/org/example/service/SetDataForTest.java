package org.example.service;

import org.example.entity.entityEnum.TypeOfForecast;
import org.example.entity.forecast.Forecast;
import org.example.entity.forecast.ForecastTemplate;
import org.example.entity.templates.Template;

public class SetDataForTest {
    public void setGoods(ForecastTemplate fT,DataTest dT){
        dT.getKreul1Goods().setForecast(new Forecast());
        fT.addGoods(dT.getKreul1Goods());
        dT.getKreul2Goods().setForecast(new Forecast());
        fT.addGoods(dT.getKreul2Goods());
        dT.getKreulKinder1Goods().setForecast(new Forecast());
        fT.addGoods(dT.getKreulKinder1Goods());
        dT.getKreulKinder2Goods().setForecast(new Forecast());
        fT.addGoods(dT.getKreulKinder2Goods());
        dT.getKreulKinder1ForKinder1Goods().setForecast(new Forecast());
        fT.addGoods(dT.getKreulKinder1ForKinder1Goods());
        dT.getKreul3Goods().setForecast(new Forecast());
        fT.addGoods(dT.getKreul3Goods());
    }

    public void SetStockListWithoutTips(ForecastTemplate fT,DataTest dT){
        fT.addSetStockTT(dT.getSetStockTT1());
        fT.addSetStockTT(dT.getSetStockTT19());
    }

    public void SetStockListWithTips(ForecastTemplate fT,DataTest dT){
        dT.getSetStockTT1().addStockTipSale(dT.getKievRtTip11());
        dT.getSetStockTT1().addStockTipSale(dT.getKievRtTip22());
        dT.getSetStockTT1().addStockTipSale(dT.getKievRtTip33());


        dT.getSetStockTT19().addStockTipSale(dT.getKievRasprTip22());
        dT.getSetStockTT19().addStockTipSale(dT.getKievRasprTip33());


        fT.addSetStockTT(dT.getSetStockTT1());
        fT.addSetStockTT(dT.getSetStockTT19());
    }

    public void setForecast(ForecastTemplate fT, DataTest dT) {
        fT.setId(1L);
        fT.setOrderForDay(30);
        fT.setName(dT.getNameTT());
        fT.setType(TypeOfForecast.valueOf(dT.getType()));
        fT.setKoefToRealSale(dT.getKoef());
        fT.setIdMainStock(dT.getKievId());
        fT.setSupplier("Kreul");
        fT.setStartAnalysis(dT.getStart());
        fT.setEndAnalysis(dT.getEnd());
        fT.setIdMainStock(dT.getKievId());
    }

    public void setTemplate(Template template, DataTest dT) {
        template.setId(1L);
        template.setOrderForDay(30);
        template.setName(dT.getNameTT());
        template.setType(dT.getType());
        template.setKoefToRealSale(dT.getKoef());
        template.setIdMainStock(dT.getKievId());
    }

    public void setStockAndTipForTaplates(Template template, DataTest dT) {
        dT.getSetStockTtTemplate1().addStockTipSaleTemplete(dT.getKievRtTip1());
        dT.getSetStockTtTemplate1().addStockTipSaleTemplete(dT.getKievRtTip2());
        dT.getSetStockTtTemplate1().addStockTipSaleTemplete(dT.getKievRtTip3());


        dT.getSetStockTtTemplate19().addStockTipSaleTemplete(dT.getKievRasprTip2());
        dT.getSetStockTtTemplate19().addStockTipSaleTemplete(dT.getKievRasprTip3());


        template.addSetStockTemplates(dT.getSetStockTtTemplate1());
        template.addSetStockTemplates(dT.getSetStockTtTemplate19());
    }

    public void setChildForForecast(ForecastTemplate fT, DataTest dT) {
        dT.getKreul2Goods().addAssembleParentSet(dT.getAssemble1());
        dT.getKreul2Goods().addAssembleParentSet(dT.getAssemble2());
        dT.getKreulKinder1Goods().setAssembleChild(dT.getAssemble1());
        dT.getKreulKinder2Goods().setAssembleChild(dT.getAssemble2());
        dT.getKreulKinder1Goods().addAssembleParentSet(dT.getAssemble1For1());
        dT.getKreulKinder1ForKinder1Goods().setAssembleChild(dT.getAssemble1For1());
    }

    public void setMoveForForecast(ForecastTemplate fT, DataTest dT) {
        dT.getKreul1Goods().addGoodsMove(dT.getMove1());
        dT.getKreul1Goods().addGoodsMove(dT.getMove2());
        dT.getKreul1Goods().addGoodsMove(dT.getMove3());

        dT.getKreul2Goods().addGoodsMove(dT.getMove2_0());
        dT.getKreul2Goods().addGoodsMove(dT.getMove2_1());
        dT.getKreul2Goods().addGoodsMove(dT.getMove2_2());
        dT.getKreul2Goods().addGoodsMove(dT.getMove2_3());
        dT.getKreul2Goods().addGoodsMove(dT.getMove2_4());
        dT.getKreul2Goods().addGoodsMove(dT.getMove2_5());

        dT.getKreul3Goods().addGoodsMove(dT.getMove1_19());
        dT.getKreul3Goods().addGoodsMove(dT.getMove2_19());
        dT.getKreul3Goods().addGoodsMove(dT.getMove3_19());
    }

    public void setRestForForecast(ForecastTemplate fT, DataTest dT) {
        dT.getKreul1Goods().addRest(dT.getRestKreul1Kiev());
        dT.getKreul1Goods().addRest(dT.getRestKreul1KievRaspr());
        dT.getKreul2Goods().addRest(dT.getRestKreul2Kiev());
        dT.getKreul3Goods().addRest(dT.getRestKreul3Kiev());
        dT.getKreul3Goods().addRest(dT.getRestKreul3KievRaspr());
    }

    public void setStockParamForForecast(ForecastTemplate fT, DataTest dT) {
        dT.getKreul1Goods().addStockParam(dT.getStockParamKreul1Stock1());
        dT.getKreul2Goods().addStockParam(dT.getStockParamKreul2Stock1());
        dT.getKreulKinder1Goods().addStockParam(dT.getStockParamKreulKinder1Stock1());
        dT.getKreulKinder2Goods().addStockParam(dT.getStockParamKreulKinder2Stock1());
        dT.getKreulKinder1ForKinder1Goods().addStockParam(dT.getStockParamKreulKinder1Stock1());
        dT.getKreul3Goods().addStockParam(dT.getStockParamKreul3Stock1());
    }
}
