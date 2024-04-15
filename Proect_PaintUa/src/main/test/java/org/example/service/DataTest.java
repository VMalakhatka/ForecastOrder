package org.example.service;

import lombok.Data;
import org.example.dto.dataFromDb.in.*;
import org.example.entity.dataFromDb.*;
import org.example.entity.entityEnum.StockRole;
import org.example.entity.entityEnum.TypDocmPr;
import org.example.entity.forecast.SetStockTT;
import org.example.entity.forecast.StockTipSale;
import org.example.entity.templates.SetStockTtTemplate;
import org.example.entity.templates.StockTipSaleTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DataTest{
    public final String supp="Kreul";
    public final String nameTT="Kiev Olymp";
    public final String type ="SUPPLIER";
    public final Double koef =1.5;
    public final String nameStock1="Kiev";
    public final String nameStock19="Kiev Rasprodaga";
    public final long KievId=1;
    public final long KievRasprId=19;
    public final LocalDateTime end=LocalDateTime.of(2024,3, 20,0,0);
    public final LocalDateTime start=LocalDateTime.of(2024,2, 19,0,0);
    public final SetStockTtTemplate setStockTtTemplate1=new SetStockTtTemplate(1L,1L,nameTT,KievId,
            nameStock1, StockRole.PT,true,true,true);
    public final SetStockTtTemplate setStockTtTemplate19=new SetStockTtTemplate(2L,1L,nameTT,KievRasprId,
            nameStock19, StockRole.Storage,false,true,false);

    public final StockTipSaleTemplate KievRtTip1=new StockTipSaleTemplate(1L,"РОЗНКИЕВ", TypDocmPr.R,"ALL",true);
    public final StockTipSaleTemplate KievRtTip2=new StockTipSaleTemplate(2L,"ALL", TypDocmPr.R,"РАСХОДНИКИ",true);
    public final StockTipSaleTemplate KievRtTip3=new StockTipSaleTemplate(3L,"ALL", TypDocmPr.R,"Мультисборка",true);
    public final StockTipSaleTemplate KievRasprTip2=new StockTipSaleTemplate(5L,"ALL", TypDocmPr.R,"РЕКЛАМА",false);
    public final StockTipSaleTemplate KievRasprTip3=new StockTipSaleTemplate(6L,"ALL", TypDocmPr.R,"Мультисборка",false);

    public final SetStockTT setStockTT1=new SetStockTT(1L,1L,nameTT,KievId,
            nameStock1, StockRole.PT,true,true,true);
    public final SetStockTT setStockTT19=new SetStockTT(2L,1L,nameTT,KievRasprId,
            nameStock19, StockRole.Storage,false,true,false);

    public final StockTipSale KievRtTip11=new StockTipSale(1L,"РОЗНКИЕВ", TypDocmPr.R,"ALL",true);
    public final StockTipSale KievRtTip22=new StockTipSale(2L,"ALL", TypDocmPr.R,"РАСХОДНИКИ",true);
    public final StockTipSale KievRtTip33=new StockTipSale(3L,"ALL", TypDocmPr.R,"Мультисборка",true);
    public final StockTipSale KievRasprTip22=new StockTipSale(5L,"ALL", TypDocmPr.R,"РЕКЛАМА",false);
    public final StockTipSale KievRasprTip33=new StockTipSale(6L,"ALL", TypDocmPr.R,"Мультисборка",false);

    public final Goods kreul1Goods=new Goods(null,"KR-001","Good Kreul for Test first",10.0,1.5,"EURO",
            12,supp,true,0);
    public final Goods kreul2Goods=new Goods(null,"KR-002","Good Kreul for Test second",20.0,3.0,"EURO",
            3,supp,true,1);
    public final Goods kreul3Goods=new Goods(null,"KR-003","Good Kreul for Test for Tip notEqual",10.0,1.5,"EURO",
            12,supp,true,0);

    public final Goods kreulKinder1Goods =new Goods(null,"KR-002R","R Kinder from second Good Kreul for Test ",
            2.0,0.3,"EURO", 1,supp,true,11);
    public final Goods kreulKinder2Goods=new Goods(null,"KR-002RR","RR Kinder from second Good Kreul for Test ",
            4.0,0.6,"EURO", 1,supp,true,3);
    //внук от KR-002 и ребенок от KR-002R
    public final Goods kreulKinder1ForKinder1Goods=new Goods(null,"KR-002R_R","R_R Kinder1 for Kinder1 from second Good Kreul for Test ",
            4.0,0.6,"EURO", 1,supp,true,3);

    public final StockParam stockParamKreulKinder1ForKinder1Stock1=new StockParam(null,KievId,6,32,1,11);
    public final StockParam stockParamKreulKinder1Stock1=new StockParam(null,KievId,5,16,1,11);
    public final StockParam stockParamKreulKinder2Stock1=new StockParam(null,KievId,3,8,1,11);
    public final StockParam stockParamKreul3Stock1=new StockParam(null,KievId,4,12,1,11);
    public final StockParam stockParamKreul2Stock1=new StockParam(null,KievId,2,10,1,11);
    public final StockParam stockParamKreul1Stock1=new StockParam(null,KievId,3,12,1,11);

    public final StockParamDtoIn stockParamKreul1Stock1Dto=new StockParamDtoIn("KR-001",KievId,3,12,1,11);
    public final StockParamDtoIn stockParamKreul1Stock19Dto=new StockParamDtoIn("KR-001",KievRasprId,0,12,1,11);
    public final StockParamDtoIn stockParamKreul2Stock1Dto=new StockParamDtoIn("KR-002",KievId,2,10,1,11);
    public final StockParamDtoIn stockParamKreul3Stock1Dto=new StockParamDtoIn("KR-003",KievId,4,12,1,11);
    public final StockParamDtoIn stockParamKreulKinder2Stock1Dto=new StockParamDtoIn("KR-002RR",KievId,3,8,1,11);
    public final StockParamDtoIn stockParamKreulKinder1Stock1Dto=new StockParamDtoIn("KR-002R",KievId,5,16,1,11);
    public final StockParamDtoIn stockParamKreulKinder1ForKinder1Stock1Dto=new StockParamDtoIn("KR-002R_R",KievId,6,32,1,11);
    List<StockParamDtoIn> stockParamDtoInList=List.of(stockParamKreulKinder1ForKinder1Stock1Dto,stockParamKreulKinder1Stock1Dto,stockParamKreulKinder2Stock1Dto,
            stockParamKreul3Stock1Dto,stockParamKreul2Stock1Dto,stockParamKreul1Stock1Dto,stockParamKreul1Stock19Dto);

    public final Assemble assemble1=new Assemble(null,10.0,kreul2Goods, kreulKinder1Goods);
    public final Assemble assemble2=new Assemble(null,5.0,kreul2Goods, kreulKinder2Goods);
    public final Assemble assemble1For1=new Assemble(null,5.0,kreulKinder1Goods, kreulKinder1ForKinder1Goods);

    public final AssembleDtoIn ass1=new AssembleDtoIn("KR-002","KR-002R",10);
    public final AssembleDtoIn ass2=new AssembleDtoIn("KR-002","KR-002RR",5);
    public final AssembleDtoIn ass3=new AssembleDtoIn("KR-002R","KR-002R_R",5);
    public final AssembleDtoIn assWrong=new AssembleDtoIn("KR-001","KR-002R_R",5);
    public final List<AssembleDtoIn> assembleDtoInList= List.of(ass1,ass2,ass3);
    public final List<AssembleDtoIn> assembleDtoInListWrong= List.of(ass1,ass2,ass3,assWrong);


    public final GoodsMove move1=new GoodsMove(null,KievId,22,2,"dop","РОЗНКИЕВ",
            LocalDateTime.of(2024,3,15,0,0),
            3,0,TypDocmPr.R,"РОЗНИЦА");
    public final GoodsMove move2=new GoodsMove(null,KievId,33,3,"dop","РОСА",
            LocalDateTime.of(2024,3,10,0,0),
            3,0,TypDocmPr.P,"ПРИХОД");
    public final GoodsMove move3=new GoodsMove(null,KievId,44,4,"dop","ОПТОВИК",
            LocalDateTime.of(2024,3,5,0,0),
            3,0,TypDocmPr.R,"РАСХОДНИКИ");

    public final MoveDtoIn moveDtoIn1=new MoveDtoIn("KR-001",22,2,"dop","РОЗНКИЕВ",
            LocalDateTime.of(2024,3,15,0,0),3,"R","РОЗНИЦА",KievId);
    public final MoveDtoIn moveDtoIn2=new MoveDtoIn("KR-001",33,3,"dop","РОСА",
            LocalDateTime.of(2024,3,10,0,0),3,"P","ПРИХОД",KievId);
    public final MoveDtoIn moveDtoIn3=new MoveDtoIn("KR-001",44,4,"/dop","ОПТОВИК",
            LocalDateTime.of(2024,3,5,0,0),3,"R","РАСХОДНИКИ",KievId);


    public final GoodsMove move2_0=new GoodsMove(null,KievId,22,2,"dop","РОЗНКИЕВ",
            LocalDateTime.of(2024,2,13,0,0),
            3,0,TypDocmPr.R,"РОЗНИЦА");
    public final GoodsMove move2_1=new GoodsMove(null,KievId,11,1,"dop","РОСА",
            LocalDateTime.of(2024,2,22,0,0),
            3,0,TypDocmPr.P,"ПРИХОД");
    public final GoodsMove move2_2=new GoodsMove(null,KievId,22,2,"dop","РОЗНКИЕВ",
            LocalDateTime.of(2024,2,28,0,0),
            3,0,TypDocmPr.R,"РОЗНИЦА");
    public final GoodsMove move2_3=new GoodsMove(null,KievId,33,3,"dop","РОСА",
            LocalDateTime.of(2024,3,12,0,0),
            3,0,TypDocmPr.P,"ПРИХОД");
    public final GoodsMove move2_4=new GoodsMove(null,KievId,44,4,"dop","ОПТОВИК",
            LocalDateTime.of(2024,3,19,0,0),
            3,0,TypDocmPr.R,"РАСХОДНИКИ");
    public final GoodsMove move2_5=new GoodsMove(null,KievId,55,5,"dop","РОСА",
            LocalDateTime.of(2024,4,10,0,0),
            5,0,TypDocmPr.P,"ПРИХОД");


    public final MoveDtoIn moveDtoIn2_0=new MoveDtoIn("KR-002",22,2,"dop","РОЗНКИЕВ",
            LocalDateTime.of(2024,2,13,0,0),
            3,"R","РОЗНИЦА",KievId);
    public final MoveDtoIn moveDtoIn2_1=new MoveDtoIn("KR-002",11,1,"dop","РОСА",
            LocalDateTime.of(2024,2,22,0,0),
            3,"P","ПРИХОД",KievId);
    public final MoveDtoIn moveDtoIn2_2=new MoveDtoIn("KR-002",22,2,"dop","РОЗНКИЕВ",
            LocalDateTime.of(2024,2,28,0,0),
            3,"R","РОЗНИЦА",KievId);
    public final MoveDtoIn moveDtoIn2_3=new MoveDtoIn("KR-002",33,3,"dop","РОСА",
            LocalDateTime.of(2024,3,12,0,0),
            3,"P","ПРИХОД",KievId);
    public final MoveDtoIn moveDtoIn2_4=new MoveDtoIn("KR-002",44,4,"/dop","ОПТОВИК",
            LocalDateTime.of(2024,3,19,0,0),
            3,"R","РАСХОДНИКИ",KievId);
    public final MoveDtoIn moveDtoIn2_5=new MoveDtoIn("KR-002",55,5,"dop","РОСА",
            LocalDateTime.of(2024,4,10,0,0),
            5,"P","ПРИХОД",KievId);


    public final GoodsMove move1_19=new GoodsMove(null,KievRasprId,222,2,"dop","РОЗНКИЕВ",
            LocalDateTime.of(2024,3,15,0,0),
            3,0,TypDocmPr.R,"РОЗНИЦА");
    public final GoodsMove move2_19=new GoodsMove(null,KievRasprId,333,3,"dop","РОСА",
            LocalDateTime.of(2024,3,10,0,0),
            3,0,TypDocmPr.R,"РЕКЛАМА");
    public final GoodsMove move3_19=new GoodsMove(null,KievRasprId,444,4,"dop","ОПТОВИК",
            LocalDateTime.of(2024,3,5,0,0),
            3,0,TypDocmPr.R,"РАСХОДНИКИ");

    public final MoveDtoIn moveDtoIn1_19=new MoveDtoIn("KR-003",222,2,"dop","РОЗНКИЕВ",
            LocalDateTime.of(2024,3,15,0,0),3,"R","РОЗНИЦА",KievRasprId);
    public final MoveDtoIn moveDtoIn2_19=new MoveDtoIn("KR-003",333,3,"dop","РОСА",
            LocalDateTime.of(2024,3,10,0,0),3,"R","РЕКЛАМА",KievRasprId);
    public final MoveDtoIn moveDtoIn3_19=new MoveDtoIn("KR-003",444,4,"dop","РОСА",
            LocalDateTime.of(2024,3,5,0,0),3,"R","РАСХОДНИКИ",KievRasprId);

    public List<MoveDtoIn> moveDtoInList=List.of(moveDtoIn1,moveDtoIn2,moveDtoIn3,moveDtoIn2_0,moveDtoIn2_1,moveDtoIn2_2,moveDtoIn2_3,moveDtoIn2_4,moveDtoIn2_5,moveDtoIn1_19,moveDtoIn2_19,moveDtoIn3_19);

    public final Rest restKreul1Kiev =new Rest(null,KievId,1,1);
    public final Rest restKreul1KievRaspr =new Rest(null,KievRasprId,3,1);
    public final Rest restKreul2Kiev =new Rest(null,KievId,5,5);
    public final Rest restKreul3Kiev =new Rest(null,KievId,1,1);
    public final Rest restKreul3KievRaspr =new Rest(null,KievRasprId,3,8);

    public final RestDtoIn restKreul1KievDto=new RestDtoIn("KR-001",KievId,1,1);
    public final RestDtoIn restKreul1KievRasprDto=new RestDtoIn("KR-001",KievRasprId,3,1);
    public final RestDtoIn restKreul2KievrDto=new RestDtoIn("KR-002",KievId,5,5);
    public final RestDtoIn restKreul3KievDto=new RestDtoIn("KR-003",KievId,1,1);
    public final RestDtoIn restKreul3KievRasprDto=new RestDtoIn("KR-003",KievRasprId,3,8);

    List<RestDtoIn> restDtoInList=List.of(restKreul1KievDto,restKreul1KievRasprDto,restKreul2KievrDto,restKreul3KievDto,restKreul3KievRasprDto);

    public final GoodsDtoIn goodsDtoIn=new GoodsDtoIn(" "," ",0.0,0.0," ",0.0," ", true,0);

}

