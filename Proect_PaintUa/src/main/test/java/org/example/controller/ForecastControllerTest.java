package org.example.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.user.UserDaoImpl;
import org.example.dto.dataFromDb.out.GoodsDtoOut;
import org.example.dto.forecast.out.ForecastTemplateDtoOut;
import org.example.entity.data.from.db.Goods;
import org.example.entity.forecast.Forecast;
import org.example.entity.forecast.ForecastTemplate;
import org.example.mapper.data.from.db.out.DataFromDbMapper;
import org.example.mapper.forecast.out.ForecastOutMapper;
import org.example.mapper.template.out.TemplatesOutMapper;
import org.example.repository.DataLoader;
import org.example.repository.UserRepository;
import org.example.repository.templates.DataLoaderTemplates;
import org.example.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(ForecastController.class)
@AutoConfigureMockMvc(addFilters = false)
class ForecastControllerTest {
    @MockBean
    DataLoader dataLoader;
    @MockBean
    DataLoaderTemplates dataLoaderTemplates;
    @MockBean
    UserDaoImpl userDao;
    @MockBean
    UserRepository userRepository;
    @MockBean
    UserSecurityService userSecurityService;
    @MockBean
    TemplateService templateService;
    @MockBean
    FromExternalDatabaseService fromExternalDatabaseService;
    @MockBean
    MakeForecastOnSupplierService makeForecastOnSupplierService;
    @SpyBean
    TemplatesOutMapper templatesOutMapper;
    @SpyBean
    ForecastOutMapper forecastOutMapper;
    @InjectMocks
    ForecastController forecastController;
    DataTest dT = new DataTest();
    SetDataForTest setDataForTest = new SetDataForTest();

    @Autowired
    ObjectMapper objectMapper;


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataFromDbMapper dataFromDbMapper;

    @Test
    void runForecast() throws Exception {
        ForecastTemplate fT = new ForecastTemplate();
        setDataForTest.setForecast(fT, dT);
        ForecastTemplateDtoOut fTDtoOut=forecastOutMapper.toForecastTemplateDtoOut(fT);
        Mockito.reset(makeForecastOnSupplierService);
        Mockito.when(makeForecastOnSupplierService.run(1L,"Kreul")).thenReturn(fT);
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/forecast/1/supplier/Kreul"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        ForecastTemplateDtoOut templateDtoOut = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });
        assertEquals(fTDtoOut, templateDtoOut);

    }

    @Test
    void getAllForecastHeader() throws Exception {
        ForecastTemplate fT = new ForecastTemplate();
        setDataForTest.setForecast(fT, dT);
        List<ForecastTemplate> fTList= List.of(fT);
        List<ForecastTemplateDtoOut> fTDtoOutList=List.of(forecastOutMapper.toForecastTemplateDtoOut(fT));
        Mockito.reset(makeForecastOnSupplierService);
        Mockito.when(makeForecastOnSupplierService.getAllForecast()).thenReturn(fTList);
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/forecast"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        List<ForecastTemplateDtoOut> templateDtoOutList = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });
        assertEquals(fTDtoOutList, templateDtoOutList);
    }

    @Test
    void getForecastHeader() throws Exception {
        ForecastTemplate fT = new ForecastTemplate();
        setDataForTest.setForecast(fT, dT);
        ForecastTemplateDtoOut fTDtoOut=forecastOutMapper.toForecastTemplateDtoOut(fT);
        Mockito.reset(makeForecastOnSupplierService);
        Mockito.when(makeForecastOnSupplierService.getForecastById(1L)).thenReturn(fT);
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/forecast/1"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        ForecastTemplateDtoOut templateDtoOut = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });
        assertEquals(fTDtoOut, templateDtoOut);
    }

    @Test
    void getForecastGoods() throws Exception {
        Set<Goods> goodsSet = new HashSet<>();
        dT.kreul1Goods.setForecast(new Forecast());
        goodsSet.add(dT.kreul1Goods);
        dT.getKreul2Goods().setForecast(new Forecast());
        goodsSet.add(dT.kreul2Goods);
        dT.getKreul3Goods().setForecast(new Forecast());
        goodsSet.add(dT.kreul3Goods);
        List<GoodsDtoOut> goodsDtoOuts = goodsSet.stream().
                map(g -> dataFromDbMapper.toGoodsDtoOut(g)).toList();
        Mockito.reset(makeForecastOnSupplierService);
        Mockito.when(makeForecastOnSupplierService.getGoodsListByForecastId(1L)).thenReturn(goodsSet);
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/forecast/forecast_goods/1"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        List<GoodsDtoOut> goodsDtoOutsResult = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });
        assertEquals(goodsDtoOuts, goodsDtoOutsResult);
    }

    @Test
    void delForecastGoods() {
    }

    @Test
    void delAllForecass() {
    }
}