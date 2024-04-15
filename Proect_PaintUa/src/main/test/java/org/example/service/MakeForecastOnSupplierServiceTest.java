package org.example.service;

import org.example.entity.entityEnum.StockRole;
import org.example.entity.entityEnum.TypeOfForecast;
import org.example.entity.forecast.ForecastTemplate;
import org.example.entity.forecast.SetStockTT;
import org.example.entity.templates.Template;
import org.example.exception.DataNotValidException;
import org.example.exception.NotEnoughDataException;
import org.example.exception.NotFindByIDException;
import org.example.exception.RabbitNotAnswerException;
import org.example.repository.forecast.ForecastTemplateRepository;
import org.example.repository.templates.TemplateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.ConnectException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
class MakeForecastOnSupplierServiceTest {
    private final SetDataForTest setDataForTest=new SetDataForTest();
    private Template template = new Template();
    @Mock
    private TemplateRepository templateRepository;
    @Mock
    ForecastTemplateRepository forecastTemplateRepository;
    @Mock
    FromExternalDatabaseService fromExternalDatabaseService;
    ForecastTemplate forecastTemplate;

    DataTest dT=new DataTest();



    @InjectMocks
    private MakeForecastOnSupplierService makeForecastOnSupplierService;

    @BeforeEach
    void setUp(){
        Mockito.reset(templateRepository);
        forecastTemplate=new ForecastTemplate();
        template = new Template();
        setDataForTest.setTemplate(template,dT);

        Mockito.lenient().when(templateRepository.findById(1L)).thenReturn(Optional.of(template));
        Mockito.when(forecastTemplateRepository.save(any(ForecastTemplate.class))).thenAnswer(invocation -> {
            ForecastTemplate fT=invocation.getArgument(0);
            fT.setId(1L);
            return fT;
        });
        setDataForTest.setStockAndTipForTaplates(template,dT);
    }

    @Test
    void delForecastTemplateTest() throws NotFindByIDException {
        Mockito.reset(forecastTemplateRepository);
        Mockito.when(forecastTemplateRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFindByIDException.class,()->makeForecastOnSupplierService.delForecastTemplate(1L),
                "Could not find the forecast by ID in the delForecastTemplate method");
        setDataForTest.setForecast(forecastTemplate,dT);
        Mockito.reset(forecastTemplateRepository);
        Mockito.when(forecastTemplateRepository.findById(1L)).thenReturn(Optional.of(forecastTemplate));

        makeForecastOnSupplierService.delForecastTemplate(1L);
        Mockito.verify(forecastTemplateRepository, times(1)).delete(forecastTemplate);
    }

    @Test
    void getForecastByIdTest() throws NotFindByIDException {
        Mockito.reset(forecastTemplateRepository);
        Mockito.when(forecastTemplateRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFindByIDException.class,()->makeForecastOnSupplierService.getForecastById(1L),
                "forecast not found by ID");
        setDataForTest.setForecast(forecastTemplate,dT);
        Mockito.reset(forecastTemplateRepository);
        Mockito.when(forecastTemplateRepository.findById(1L)).thenReturn(Optional.of(forecastTemplate));

        ForecastTemplate fT=makeForecastOnSupplierService.getForecastById(1L);
        assertEquals(forecastTemplate,fT);
    }

    @Test
    void getGoodsListByForecastIdTest(){
        Mockito.reset(forecastTemplateRepository);
        Mockito.when(forecastTemplateRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFindByIDException.class,()->makeForecastOnSupplierService.getGoodsListByForecastId(1L),
                "forecast not found by ID when you searched for the product ");
    }

    @Test
    void testCountingData() throws DataNotValidException, NotEnoughDataException, RabbitNotAnswerException, ConnectException {
        template.setEndAnalysis(dT.getEnd());
        template.setStartAnalysis(dT.getStart());
        Mockito.reset(fromExternalDatabaseService);
        Mockito.when(fromExternalDatabaseService.saveListOfGoods(any(ForecastTemplate.class))).thenAnswer(invocation -> {
            ForecastTemplate fT = invocation.getArgument(0);
            setDataForTest.setGoods(fT,dT);
            return fT;
        });
        Mockito.when(fromExternalDatabaseService.saveListOfChildForForecast(any(ForecastTemplate.class))).thenAnswer(invocation -> {
            ForecastTemplate fT = invocation.getArgument(0);
            setDataForTest.setChildForForecast(fT,dT);
            return fT;
        });
        Mockito.when(fromExternalDatabaseService.saveListOfMoveForForecast(any(ForecastTemplate.class))).thenAnswer(invocation -> {
            ForecastTemplate fT = invocation.getArgument(0);
            setDataForTest.setMoveForForecast(fT,dT);
            return fT;
        });
        Mockito.when(fromExternalDatabaseService.saveListOfRestForForecast(any(ForecastTemplate.class))).thenAnswer(invocation -> {
            ForecastTemplate fT = invocation.getArgument(0);
            setDataForTest.setRestForForecast(fT,dT);
            return fT;
        });

        Mockito.when(fromExternalDatabaseService.saveStockParam(any(ForecastTemplate.class))).thenAnswer(invocation -> {
            ForecastTemplate fT = invocation.getArgument(0);
            setDataForTest.setStockParamForForecast(fT,dT);
            return fT;
        });

        forecastTemplate = makeForecastOnSupplierService.run(1L, dT.getSupp());
        assertEquals("KR-001",dT.getKreul1Goods().getCodArtic());
        assertEquals(6,dT.getKreul1Goods().getForecast().getSale());
        assertEquals(2,dT.getKreul1Goods().getForecast().getRestTT());
        assertEquals(0,dT.getKreul1Goods().getForecast().getNotOnStock());
        assertEquals(5,dT.getKreul2Goods().getForecast().getRestTT());
        assertTrue((dT.getKreul1Goods().getForecast().getNotSaleAndSale()-18.666666666666664)<0.0001);
        assertEquals(9.2,dT.getKreul2Goods().getForecast().getOrderWithoutPack());
        assertEquals(7,dT.getKreul1Goods().getForecast().getOrderWithoutPack());
        assertEquals(6,dT.getKreul3Goods().getForecast().getSale());
    }


    @Test
    void testRunDataSetValid() throws DataNotValidException, NotEnoughDataException, RabbitNotAnswerException, ConnectException {
        forecastTemplate = makeForecastOnSupplierService.run(1L, dT.getSupp());
        assertEquals(forecastTemplate.getEndAnalysis().getDayOfMonth(), LocalDateTime.now().getDayOfMonth());
        assertThrows(DataNotValidException.class,()->makeForecastOnSupplierService.run(2L, dT.getSupp()),
                "There is no such template");
    }

    @Test
    void testRunForecastTemplateFullCopyVerify() throws DataNotValidException, NotEnoughDataException, RabbitNotAnswerException, ConnectException {
        LocalDateTime end=LocalDateTime.of(2023,10, 29,0,0);
        LocalDateTime start=LocalDateTime.of(2023,10, 1,0,0);
        template.setEndAnalysis(end);
        template.setStartAnalysis(start);
        forecastTemplate = makeForecastOnSupplierService.run(1L, dT.getSupp());
        testForecastTemplateFilling(forecastTemplate,start,end);
        testSetStockTTFilling(forecastTemplate);
    }

    void testSetStockTTFilling(ForecastTemplate forecastTemplate){
        for(SetStockTT set: forecastTemplate.getSetStockTTSet()){
            assertEquals(dT.getNameTT(), set.getNameTT());
            assertEquals(1L, set.getIdTT());
            if(set.getIdStock()== dT.getKievId()){
                assertEquals(set.getNameStock(), dT.getNameStock1());
                assertEquals(set.getRole(),StockRole.PT);
                assertTrue(set.isMax()& set.isMin()& set.isFasovka());
                assertEquals(set.getStockTipSaleSet().size(),3);
            }
            if(set.getIdStock()== dT.getKievRasprId()){
                assertEquals(set.getNameStock(), dT.getNameStock19());
                assertEquals(set.getRole(),StockRole.Storage);
                assertTrue(set.isMax()& !set.isMin()& !set.isFasovka());
                assertFalse(set.getStockTipSaleSet().isEmpty());
            }

        }
    }

    void testForecastTemplateFilling(ForecastTemplate forecastTemplate,LocalDateTime start, LocalDateTime end){
        assertEquals(forecastTemplate.getSupplier(),dT.getSupp());
        assertEquals(forecastTemplate.getStartAnalysis(),start);
        assertEquals(forecastTemplate.getEndAnalysis(),end);
        assertEquals(forecastTemplate.getOrderForDay(),Math.abs(Duration.between(end,start).toDays()));
        assertEquals(forecastTemplate.getKoefToRealSale(), dT.getKoef());
        assertEquals(forecastTemplate.getName(), dT.getNameTT());
        assertEquals(forecastTemplate.getType(), TypeOfForecast.SUPPLIER);
        assertFalse(forecastTemplate.getSetStockTTSet().isEmpty());
    }

    @Test
    void testRunExceptionTestOk(){
        Mockito.reset(templateRepository);
        Mockito.reset(forecastTemplateRepository);
        Mockito.when(templateRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(DataNotValidException.class,()->makeForecastOnSupplierService.run(2L, "Kreul"),
                "There is no such template");
    }


}