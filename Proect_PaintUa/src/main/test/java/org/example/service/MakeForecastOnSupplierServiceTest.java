package org.example.service;

import org.example.entity.data_from_db.*;
import org.example.entity.forecast.Forecast;
import org.example.entity.forecast.ForecastTemplate;
import org.example.entity.forecast.SetStockTT;
import org.example.entity.forecast.entity_enum.TypeOfForecast;
import org.example.entity.templates.SetStockTtTemplate;
import org.example.entity.templates.StockTipSaleTemplate;
import org.example.entity.templates.Template;
import org.example.entity.templates.entity_enum.StockRole;
import org.example.entity.templates.entity_enum.TypDocmPr;
import org.example.exeption.DataNotValid;
import org.example.repository.forecast.ForecastTemplateRepository;
import org.example.repository.templates.TemplateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class MakeForecastOnSupplierServiceTest {
    private Template template = new Template();
    @Mock
    private TemplateRepository templateRepository;
    @Mock
    ForecastTemplateRepository forecastTemplateRepository;
    @Mock
    FromExternalDatabaseServise fromExternalDatabaseServise;
    @Mock
    CountingService countingService;
    ForecastTemplate forecastTemplate;

    private final String supp="Kreul";
    private final String nameTT="Kiev Olymp";
    private final String type ="SUPPLIER";
    private final Double koef =1.5;
    private final String nameStock1="Kiev";
    private final String nameStock19="Kiev Rasprodaga";
    private final long KievId=1;
    private final long KievRasprId=19;
    private final SetStockTtTemplate setStockTtTemplate1=new SetStockTtTemplate(1L,1L,nameTT,KievId,
                                                            nameStock1, StockRole.PT,true,true,true);
    private final SetStockTtTemplate setStockTtTemplate19=new SetStockTtTemplate(2L,1L,nameTT,KievRasprId,
                                        nameStock19, StockRole.Storage,false,true,false);

    private final StockTipSaleTemplate KievRtTip1=new StockTipSaleTemplate(1L,"РОЗНКИЕВ", TypDocmPr.R,"ALL",true);
    private final StockTipSaleTemplate KievRtTip2=new StockTipSaleTemplate(2L,"ALL", TypDocmPr.R,"РАСХОДНИКИ",true);
    private final StockTipSaleTemplate KievRtTip3=new StockTipSaleTemplate(3L,"ALL", TypDocmPr.R,"Мультисборка",true);

    private final Goods kreul1Goods=new Goods(1L,"KR-001","Good Kreul for Test first",10.0,1.5,"EURO",
            12,supp,true,0);
    private final StockParam stockParamKreul1Stock1=new StockParam(1L,1L,3,12,1,11);
    private final Goods kreul2Goods=new Goods(2L,"KR-002","Good Kreul for Test second",20.0,3.0,"EURO",
            3,supp,true,1);
    private final StockParam stockParamKreul2Stock1=new StockParam(2L,1L,2,10,1,11);

    private final Goods kreulKinder1Goods =new Goods(3L,"KR-002R","R Kinder from second Good Kreul for Test ",
            2.0,0.3,"EURO", 1,supp,true,11);
    private final StockParam stockParamKreulKinder1Stock1=new StockParam(3L,1L,5,16,1,11);
    private final Goods kreulKinder2Goods=new Goods(4L,"KR-002RR","RR Kinder from second Good Kreul for Test ",
            4.0,0.6,"EURO", 1,supp,true,3);
    private final StockParam stockParamKreulKinder2Stock1=new StockParam(4L,1L,3,8,1,11);
    //внук от KR-002 и ребенок от KR-002R
    private final Goods kreulKinder1ForKinder1Goods=new Goods(5L,"KR-002R_R","R_R Kinder1 for Kinder1 from second Good Kreul for Test ",
            4.0,0.6,"EURO", 1,supp,true,3);
    private final StockParam stockParamKreulKinder1ForKinder1Stock1=new StockParam(5L,1L,6,32,1,11);


    private final Assemble assemble1=new Assemble(1L,10.0,kreul2Goods, kreulKinder1Goods);
    private final Assemble assemble2=new Assemble(2L,5.0,kreul2Goods, kreulKinder2Goods);
    private final Assemble assemble1For1=new Assemble(3L,5.0,kreulKinder1Goods, kreulKinder1ForKinder1Goods);
    private final GoodsMove move1=new GoodsMove(1L,KievId,22,2,"dop","РОЗНКИЕВ",
            LocalDateTime.of(2024,3,15,0,0),
            3,0,TypDocmPr.R,"РОЗНИЦА");
    private final GoodsMove move2=new GoodsMove(2L,KievId,33,3,"dop","РОСА",
            LocalDateTime.of(2024,3,10,0,0),
            3,0,TypDocmPr.P,"ПРИХОД");
    private final GoodsMove move3=new GoodsMove(2L,KievId,33,3,"dop","ОПТОВИК",
            LocalDateTime.of(2024,3,5,0,0),
            3,0,TypDocmPr.R,"РАСХОДНИКИ");
    private final Rest restKreul1Kiev =new Rest(1L,KievId,1,1);
    private final Rest restKreul1KievRaspr =new Rest(2L,KievRasprId,3,1);
    private final Rest restKreul2Kiev =new Rest(3L,KievId,5,5);

    private final Goods kreul3Goods=new Goods(6L,"KR-003","Good Kreul for Test for Tip notEqual",10.0,1.5,"EURO",
            12,supp,true,0);
    private final StockParam stockParamKreul3Stock1=new StockParam(5L,1L,4,12,1,0);
    private final Rest restKreul3Kiev =new Rest(4L,KievId,1,1);
    private final Rest restKreul3KievRaspr =new Rest(5L,KievRasprId,3,8);

    private final GoodsMove move1_19=new GoodsMove(1L,KievRasprId,222,2,"dop","РОЗНКИЕВ",
            LocalDateTime.of(2024,3,15,0,0),
            3,0,TypDocmPr.R,"РОЗНИЦА");
    private final GoodsMove move2_19=new GoodsMove(2L,KievRasprId,333,3,"dop","РОСА",
            LocalDateTime.of(2024,3,10,0,0),
            3,0,TypDocmPr.R,"РЕКЛАМА");
    private final GoodsMove move3_19=new GoodsMove(2L,KievRasprId,444,4,"dop","ОПТОВИК",
            LocalDateTime.of(2024,3,5,0,0),
            3,0,TypDocmPr.R,"РАСХОДНИКИ");

    private final StockTipSaleTemplate KievRasprTip2=new StockTipSaleTemplate(5L,"ALL", TypDocmPr.R,"РЕКЛАМА",false);
    private final StockTipSaleTemplate KievRasprTip3=new StockTipSaleTemplate(6L,"ALL", TypDocmPr.R,"Мультисборка",false);




    @InjectMocks
    private MakeForecastOnSupplierService makeForecastOnSupplierService;

    @BeforeEach
    void setUp(){
        Mockito.reset(templateRepository);
        forecastTemplate=new ForecastTemplate();
        template = new Template();
        template.setId(1L);
        template.setOrderForDay(30);
        template.setName(nameTT);
        template.setType(type);
        template.setKoefToRealSale(koef);
        Mockito.when(templateRepository.findById(1L)).thenReturn(Optional.of(template));
        Mockito.when(forecastTemplateRepository.save(any(ForecastTemplate.class))).thenAnswer(invocation -> {
            ForecastTemplate fT=invocation.getArgument(0);
            fT.setId(1L);
            return fT;
        });

        setStockTtTemplate1.addStockTipSaleTemplete(KievRtTip1);
        setStockTtTemplate1.addStockTipSaleTemplete(KievRtTip2);
        setStockTtTemplate1.addStockTipSaleTemplete(KievRtTip3);

        setStockTtTemplate19.addStockTipSaleTemplete(KievRasprTip2);
        setStockTtTemplate19.addStockTipSaleTemplete(KievRasprTip3);

        template.addSetStockTemplates(setStockTtTemplate1);
        template.addSetStockTemplates(setStockTtTemplate19);
    }

    @Test
    void testCountingData() throws DataNotValid {
        LocalDateTime end=LocalDateTime.of(2024,2, 20,0,0);
        LocalDateTime start=LocalDateTime.of(2024,3, 19,0,0);
        template.setEndAnalysis(end);
        template.setStartAnalysis(start);
        Mockito.reset(fromExternalDatabaseServise);
        Mockito.when(fromExternalDatabaseServise.saveListOfGoodsBySupplier(any(ForecastTemplate.class))).thenAnswer(invocation -> {
            ForecastTemplate fT = invocation.getArgument(0);
            kreul1Goods.setForecast(new Forecast());
            fT.addGoods(kreul1Goods);
            kreul2Goods.setForecast(new Forecast());
            fT.addGoods(kreul2Goods);
            kreulKinder1Goods.setForecast(new Forecast());
            fT.addGoods(kreulKinder1Goods);
            kreulKinder2Goods.setForecast(new Forecast());
            fT.addGoods(kreulKinder2Goods);
            kreulKinder1ForKinder1Goods.setForecast(new Forecast());
            fT.addGoods(kreulKinder1ForKinder1Goods);
            kreul3Goods.setForecast(new Forecast());
            fT.addGoods(kreul3Goods);
            return fT;
        });
        Mockito.when(fromExternalDatabaseServise.saveListOfChildForForecast(any(ForecastTemplate.class))).thenAnswer(invocation -> {
            ForecastTemplate fT = invocation.getArgument(0);
            kreul2Goods.addAssembleParentSet(assemble1);
            kreul2Goods.addAssembleParentSet(assemble2);
            kreulKinder1Goods.setAssembleChild(assemble1);
            kreulKinder2Goods.setAssembleChild(assemble2);
            kreulKinder1Goods.addAssembleParentSet(assemble1For1);
            kreulKinder1ForKinder1Goods.setAssembleChild(assemble1For1);
            return fT;
        });
        Mockito.when(fromExternalDatabaseServise.saveListOfMoveForForecast(any(ForecastTemplate.class))).thenAnswer(invocation -> {
            ForecastTemplate fT = invocation.getArgument(0);
            kreul1Goods.addGoodsMove(move1);
            kreul1Goods.addGoodsMove(move2);
            kreul1Goods.addGoodsMove(move3);
            kreul3Goods.addGoodsMove(move1_19);
            kreul3Goods.addGoodsMove(move2_19);
            kreul3Goods.addGoodsMove(move3_19);
            return fT;
        });
        Mockito.when(fromExternalDatabaseServise.saveListOfRestForForecast(any(ForecastTemplate.class))).thenAnswer(invocation -> {
            ForecastTemplate fT = invocation.getArgument(0);
            kreul1Goods.addRest(restKreul1Kiev);
            kreul1Goods.addRest(restKreul1KievRaspr);
            kreul2Goods.addRest(restKreul2Kiev);
            kreul3Goods.addRest(restKreul3Kiev);
            kreul3Goods.addRest(restKreul3KievRaspr);
            return fT;
        });

        Mockito.when(fromExternalDatabaseServise.saveStockParam(any(ForecastTemplate.class))).thenAnswer(invocation -> {
            ForecastTemplate fT = invocation.getArgument(0);
            kreul1Goods.addStockParam(stockParamKreul1Stock1);
            kreul2Goods.addStockParam(stockParamKreul2Stock1);
            kreulKinder1Goods.addStockParam(stockParamKreulKinder1Stock1);
            kreulKinder2Goods.addStockParam(stockParamKreulKinder2Stock1);
            kreulKinder1ForKinder1Goods.addStockParam(stockParamKreulKinder1ForKinder1Stock1);
            kreul3Goods.addStockParam(stockParamKreul3Stock1);
            return fT;
        });

        forecastTemplate = makeForecastOnSupplierService.run(1L, supp);
        assertEquals("KR-001",kreul1Goods.getCodArtic());
        assertEquals(6,kreul1Goods.getForecast().getSale());
        assertEquals(2,kreul1Goods.getForecast().getRestTT());
        assertEquals(19,kreul1Goods.getForecast().getNotOnStock());
        assertEquals(5,kreul2Goods.getForecast().getRestTT());
        assertTrue((kreul1Goods.getForecast().getNotSaleAndSale()-18.666666666666664)<0.0001);
        assertEquals(1.22,kreul2Goods.getForecast().getOrderWithoutPack());
        assertEquals(7,kreul1Goods.getForecast().getOrderWithoutPack());
        assertEquals(6,kreul3Goods.getForecast().getSale());
    }


    @Test
    void testRunDataSetValid() throws DataNotValid {
        forecastTemplate = makeForecastOnSupplierService.run(1L, supp);
        assertEquals(forecastTemplate.getStartAnalysis().getDayOfMonth(), LocalDateTime.now().getDayOfMonth());
        assertThrows(DataNotValid.class,()->makeForecastOnSupplierService.run(2L, supp),
                "There is no such template");
    }

    @Test
    void testRunForecastTemplateFullCopyVerify() throws DataNotValid {
        LocalDateTime end=LocalDateTime.of(2023,10, 29,0,0);
        LocalDateTime start=LocalDateTime.of(2023,10, 1,0,0);
        template.setEndAnalysis(end);
        template.setStartAnalysis(start);
        forecastTemplate = makeForecastOnSupplierService.run(1L, supp);
        testForecastTemplateFilling(forecastTemplate,start,end);
        testSetStockTTFilling(forecastTemplate);
    }

    void testSetStockTTFilling(ForecastTemplate forecastTemplate){
        for(SetStockTT set: forecastTemplate.getSetStockTTSet()){
            assertEquals(nameTT, set.getNameTT());
            assertEquals(1L, set.getIdTT());
            if(set.getIdStock()==KievId){
                assertEquals(set.getNameStock(),nameStock1);
                assertEquals(set.getRole(),StockRole.PT);
                assertTrue(set.isMax()& set.isMin()& set.isFasovka());
                assertEquals(set.getStockTipSaleSet().size(),3);
            }
            if(set.getIdStock()==KievRasprId){
                assertEquals(set.getNameStock(),nameStock19);
                assertEquals(set.getRole(),StockRole.Storage);
                assertTrue(set.isMax()& !set.isMin()& !set.isFasovka());
                assertFalse(set.getStockTipSaleSet().isEmpty());
            }

        }
    }

    void testForecastTemplateFilling(ForecastTemplate forecastTemplate,LocalDateTime start, LocalDateTime end){
        assertEquals(forecastTemplate.getSupplier(),supp);
        assertEquals(forecastTemplate.getStartAnalysis(),start);
        assertEquals(forecastTemplate.getEndAnalysis(),end);
        assertEquals(forecastTemplate.getOrderForDay(),Math.abs(Duration.between(end,start).toDays()));
        assertEquals(forecastTemplate.getKoefToRealSale(),koef);
        assertEquals(forecastTemplate.getName(),nameTT);
        assertEquals(forecastTemplate.getType(), TypeOfForecast.SUPPLIER);
        assertFalse(forecastTemplate.getSetStockTTSet().isEmpty());
    }

    @Test
    void testRunExceptionTestOk() throws DataNotValid {
        Mockito.reset(templateRepository);
        Mockito.reset(forecastTemplateRepository);
        Mockito.when(templateRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(DataNotValid.class,()->makeForecastOnSupplierService.run(2L, "Kreul"),
                "There is no such template");
    }


}