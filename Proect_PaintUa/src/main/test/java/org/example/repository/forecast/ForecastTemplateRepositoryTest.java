package org.example.repository.forecast;

import org.example.entity.data_from_db.Assemble;
import org.example.entity.data_from_db.Goods;
import org.example.entity.data_from_db.GoodsMove;
import org.example.entity.data_from_db.StockParam;
import org.example.entity.forecast.ForecastTemplate;
import org.example.repository.data_from_db.*;
import org.example.service.DataTest;
import org.example.service.SetDataForTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ForecastTemplateRepositoryTest {

    private final SetDataForTest setDataForTest=new SetDataForTest();
    private final DataTest dT=new DataTest();

    private ForecastTemplate fT;

    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    AssembleRepository assembleRepository;
    @Autowired
    ForecastTemplateRepository forecastTemplateRepository;
    @Autowired
    GoodsMoveRepository goodsMoveRepository;
    @Autowired
    RestRepository repository;
    @Autowired
    StockParamRepository stockParamRepository;

    @BeforeEach
    void setUp() {
        fT=new ForecastTemplate();
        setDataForTest.setForecast(fT,dT);
        setDataForTest.setGoods(fT,dT);
        setDataForTest.setChildForForecast(fT,dT);
        setDataForTest.setMoveForForecast(fT,dT);
        setDataForTest.setRestForForecast(fT,dT);
        setDataForTest.setStockParamForForecast(fT,dT);
    }

    @Test
    public void forecastSaveTest(){
        fT.setId(null);
        ForecastTemplate forecastTemplateSaved=forecastTemplateRepository.save(fT);
        assertNotNull(forecastTemplateSaved);
        assertNotNull(forecastTemplateSaved.getId());
        assertTrue(forecastTemplateSaved.getId() > 0);
        assertNotNull(dT.getKreul1Goods().getId());
        List<Goods> goodsList=goodsRepository.findAll();
        System.out.println(goodsList);
        List<GoodsMove> goodsMoveList=goodsMoveRepository.findAll();
        System.out.println(goodsMoveList);
    }


    @AfterEach
    void tearDown() {
        forecastTemplateRepository.deleteAll();
    }
}