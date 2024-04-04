package org.example.service;

import org.example.dao.*;
import org.example.dto.data_from_db.in.GoodsDtoIn;
import org.example.dto.data_from_db.out.GeMoveByGoodsListAndStockListAndDataStartEndDtoOut;
import org.example.dto.data_from_db.out.GetDataByGoodsListAndStockListDtoOut;
import org.example.entity.data_from_db.Assemble;
import org.example.entity.data_from_db.Goods;
import org.example.entity.forecast.ForecastTemplate;
import org.example.exception.DataNotValidException;
import org.example.exception.NotEnoughDataException;
import org.example.exception.RabbitNotAnswerException;
import org.example.mapper.data_from_db.in.GoodsInMapper;
import org.example.mapper.data_from_db.in.MoveInMapper;
import org.example.mapper.data_from_db.in.RestMapper;
import org.example.mapper.data_from_db.in.StockParamMapper;
import org.example.repository.data_from_db.AssembleSequenceRepository;
import org.example.repository.data_from_db.GoodsRepository;
import org.example.repository.forecast.ForecastTemplateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.ConnectException;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;



@ExtendWith(MockitoExtension.class)
class FromExternalDatabaseServiceTest {
    SetDataForTest setDataForTest =new SetDataForTest();
    @Mock
    GoodsInDao goodsInDao;
    @Mock
    GoodsInMapper goodsInMapper;
    @Spy
    MoveInMapper moveInMapper;
    @Spy
    RestMapper restMapper;
    @Spy
    StockParamMapper stockParamMapper;
    @Mock
    GoodsRepository goodsRepository;
    @Mock
    ForecastTemplateRepository forecastTemplateRepository;
    @Mock
    AssembleSequenceRepository assembleSequenceRepository;
    @Mock
    MoveInDao moveInDao;
    @Mock
    RestDao restDao;
    @Mock
    StockParamDao stockParamDao;
    @Mock
    AssembleDao assembleDao;
    @InjectMocks
    FromExternalDatabaseService fromExternalDatabaseService;
    DataTest dT=new DataTest();
    ForecastTemplate fT;

    @BeforeEach
    void setUP(){
        fT = new ForecastTemplate();
        setDataForTest.setForecast(fT,dT);
    }

    private void setFT(){

    }


    @Test
    public void testSaveListOfGoods() throws NotEnoughDataException, DataNotValidException, ConnectException, RabbitNotAnswerException {
        Mockito.reset(goodsInDao);
        Mockito.when(goodsInDao.getGoodsBySupplierAndStockId(
                any(String.class), any(String.class), any(Long.class))).thenAnswer(invocation -> {
            return (List<GoodsDtoIn>) null;
        });

        assertThrows(NotEnoughDataException.class, ()->fromExternalDatabaseService.saveListOfGoods(fT), "Not goods for forecast");

        Mockito.reset(goodsInDao);
        Mockito.when(goodsInDao.getGoodsBySupplierAndStockId(
                any(String.class), any(String.class), any(Long.class))).thenAnswer(invocation -> {
                    GoodsDtoIn dto=dT.goodsDtoIn;
            return List.of(dto);
        });
        Mockito.reset(goodsInMapper);
        Mockito.when(goodsInMapper.toGoodsEntity(dT.goodsDtoIn)).thenReturn(dT.getKreul1Goods());
        Mockito.reset(forecastTemplateRepository);
        Mockito.when(forecastTemplateRepository.save(any(ForecastTemplate.class))).thenAnswer(invocation -> {
            return invocation.getArgument(0);
        });
        ForecastTemplate forecastTemplate=fromExternalDatabaseService.saveListOfGoods(fT);
        assertEquals(forecastTemplate.getGoodsSet().stream().findFirst().orElse(null), dT.getKreul1Goods());
        fT.addGoods(dT.getKreul1Goods());
        assertThrows(DataNotValidException.class, ()->fromExternalDatabaseService.saveListOfGoods(fT),
                "The product set in this forecast is not empty You cannot load new data into this forecast. ");

    }



    @Test
    public void testSaveListOfChildForForecast() throws NotEnoughDataException, DataNotValidException {
        assertThrows(NotEnoughDataException.class, ()->fromExternalDatabaseService.saveListOfChildForForecast(fT),
                "Not goods to compile for requesting child from external database ");
        Mockito.reset(assembleDao);
        Mockito.when(assembleDao.getAssembleByGoodsList(any(GetDataByGoodsListAndStockListDtoOut.class),Mockito.any()))
                .thenReturn(dT.getAssembleDtoInList());
        setDataForTest.setGoods(fT,dT);
        ForecastTemplate fTest=fromExternalDatabaseService.saveListOfChildForForecast(fT);
        Goods good1=fTest.getGoodsSet().stream().filter(g->g.getCodArtic().equals("KR-001")).findFirst().orElse(null);
        assert good1 != null;
        assertTrue(good1.getAssemblePerentSet().isEmpty());
        Goods good2=fTest.getGoodsSet().stream().filter(g->g.getCodArtic().equals("KR-002")).findFirst().orElse(null);
        assert good2 != null;
        assertEquals(2, good2.getAssemblePerentSet().size());
        Assemble assChild1=good2.getAssemblePerentSet().stream().filter(a->a.getChildGood().getCodArtic().equals("KR-002R")).findFirst().orElse(null);
        assert assChild1 != null;
        assertEquals(10, assChild1.getQuantity());
        Assemble assChild2=good2.getAssemblePerentSet().stream().filter(a->a.getChildGood().getCodArtic().equals("KR-002RR")).findFirst().orElse(null);
        assert assChild2 != null;
        assertEquals(5, assChild2.getQuantity());
        Goods childChild1= Objects.requireNonNull(assChild1.getChildGood().getAssemblePerentSet().stream().findFirst().orElse(null)).getChildGood();
        assert childChild1 != null;
        assertEquals("KR-002R_R", childChild1.getCodArtic());
        assertEquals(5,childChild1.getAssembleChild().getQuantity());

    }

    @Test
    public void testSaveListOfChildForForecastWrongListOfChild() {
        // wrong links child1 two times as inherit
        Mockito.reset(assembleDao);
        Mockito.when(assembleDao.getAssembleByGoodsList(any(GetDataByGoodsListAndStockListDtoOut.class),Mockito.any()))
                .thenReturn(dT.getAssembleDtoInListWrong());
        setDataForTest.setGoods(fT,dT);
        assertThrows(DataNotValidException.class, ()->fromExternalDatabaseService.saveListOfChildForForecast(fT),
                "A product can only be inherited once, otherwise you don't know which parent it belongs to.");
    }

    @Test
    public void testSaveListOfMoveForForecastEx() {
        assertThrows(NotEnoughDataException.class, ()->fromExternalDatabaseService.saveListOfMoveForForecast(fT),
                "Not goods to compile for requesting move from external database ");
        setDataForTest.setGoods(fT,dT);
        assertThrows(NotEnoughDataException.class,()->fromExternalDatabaseService.saveListOfMoveForForecast(fT),
                "Not specified stock to compile for requesting move from external database " );
        setDataForTest.SetStockListWithoutTips(fT,dT);
        assertThrows(NotEnoughDataException.class,()->fromExternalDatabaseService.saveListOfMoveForForecast(fT),
                "Not specified stock to compile for requesting move from external database " );
    }

    @Test
    public void testSaveListOfMoveForForecast() throws NotEnoughDataException {
        setDataForTest.setGoods(fT,dT);
        setDataForTest.SetStockListWithTips(fT,dT);
        Mockito.reset(moveInDao);
        Mockito.when(moveInDao.getMoveByGoodsListAndStockList(
                any(GeMoveByGoodsListAndStockListAndDataStartEndDtoOut.class))).thenReturn(null);
        fromExternalDatabaseService.saveListOfMoveForForecast(fT);
        assertTrue(dT.getKreul1Goods().getGoodsMoveSet().isEmpty());
        Mockito.reset(moveInDao);
        Mockito.when(moveInDao.getMoveByGoodsListAndStockList(
                any(GeMoveByGoodsListAndStockListAndDataStartEndDtoOut.class))).thenReturn(dT.getMoveDtoInList());


        fromExternalDatabaseService.saveListOfMoveForForecast(fT);

        assertEquals(3,dT.getKreul1Goods().getGoodsMoveSet().size());
        assertEquals(3,dT.getKreul3Goods().getGoodsMoveSet().size());
        assertNotNull(dT.getKreul3Goods().getGoodsMoveSet().stream().filter(m->m.getUnicumNum()==333).findFirst().orElse(null));
        assertNotNull(dT.getKreul1Goods().getGoodsMoveSet().stream().filter(m->m.getUnicumNum()==33).findFirst().orElse(null));
    }

    @Test
    public void testSaveListOfRestForForecastEx() {
        assertThrows(NotEnoughDataException.class, ()->fromExternalDatabaseService.saveListOfMoveForForecast(fT),
                "Not goods to compile for requesting rest from external database ");
        setDataForTest.setGoods(fT,dT);
        assertThrows(NotEnoughDataException.class,()->fromExternalDatabaseService.saveListOfMoveForForecast(fT),
                "Not specified stock to compile for requesting rest from external database " );
        setDataForTest.SetStockListWithoutTips(fT,dT);
        assertThrows(NotEnoughDataException.class,()->fromExternalDatabaseService.saveListOfMoveForForecast(fT),
                "Not specified stock to compile for requesting rest from external database " );
    }

    @Test
    public void testSaveListOfRestForForecast() throws NotEnoughDataException {
        setDataForTest.setGoods(fT,dT);
        setDataForTest.SetStockListWithTips(fT,dT);
        Mockito.reset(restDao);
        Mockito.when(restDao.getRestByGoodsAndStockList(any(GetDataByGoodsListAndStockListDtoOut.class))).
                thenReturn(null);
        fromExternalDatabaseService.saveListOfRestForForecast(fT);
        assertTrue(dT.getKreul1Goods().getRestSet().isEmpty());
        Mockito.reset(restDao);
        Mockito.when(restDao.getRestByGoodsAndStockList(any(GetDataByGoodsListAndStockListDtoOut.class))).
                thenReturn(dT.getRestDtoInList());
        fromExternalDatabaseService.saveListOfRestForForecast(fT);
        assertEquals(2,dT.getKreul1Goods().getRestSet().size());
        assertEquals(5, Objects.requireNonNull(dT.getKreul2Goods().getRestSet().stream().findFirst().orElse(null)).getRezKolich());
    }

    @Test
    public void testSaveStockParamEx() {
        assertThrows(NotEnoughDataException.class, ()->fromExternalDatabaseService.saveListOfMoveForForecast(fT),
                "Not goods to compile for requesting stockParam from external database ");
        setDataForTest.setGoods(fT,dT);
        assertThrows(NotEnoughDataException.class,()->fromExternalDatabaseService.saveListOfMoveForForecast(fT),
                "Not specified stock to compile for requesting stockParam from external database " );
        setDataForTest.SetStockListWithoutTips(fT,dT);
        assertThrows(NotEnoughDataException.class,()->fromExternalDatabaseService.saveListOfMoveForForecast(fT),
                "Not specified stock to compile for requesting stockParam from external database " );
    }

    @Test
    public void testSaveStockParam() throws NotEnoughDataException {
        setDataForTest.setGoods(fT,dT);
        setDataForTest.SetStockListWithTips(fT,dT);
        Mockito.reset(stockParamDao);
        Mockito.when(stockParamDao.getStockParamByGoodsAndStockList(any(GetDataByGoodsListAndStockListDtoOut.class)))
                .thenReturn(dT.getStockParamDtoInList());
        fromExternalDatabaseService.saveStockParam(fT);
        assertEquals(2,dT.getKreul1Goods().getStockParams().size());
        assertEquals(32, Objects.requireNonNull(dT.getKreulKinder1ForKinder1Goods().getStockParams().stream().findFirst().orElse(null)).getMaxTvrZap());
    }
}