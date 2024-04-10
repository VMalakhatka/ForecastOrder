package org.example.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.user.UserDaoImpl;
import org.example.dto.template.out.TemplateOutDTO;
import org.example.entity.templates.Template;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(TemplatesForecastConroller.class)
@AutoConfigureMockMvc(addFilters = false)
class TemplatesForecastConrollerTest {
    @MockBean
    DataLoader dataLoader;
    @MockBean
    DataLoaderTemplates dataLoaderTemplates;
    @MockBean
    UserDaoImpl userDao;
    @MockBean
    ForecastController forecastController;
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
    @InjectMocks
    TemplatesForecastConroller templatesForecastConroller;
    DataTest dT=new DataTest();
    SetDataForTest setDataForTest=new SetDataForTest();
    ObjectMapper objectMapper = new ObjectMapper();

@Autowired
private MockMvc mockMvc;

    @Test
    void getTemplates() throws Exception {
        Template template=new Template();
        setDataForTest.setTemplate(template,dT);
        Mockito.reset(templateService);
     //   Mockito.when(templateService.getAllTemlates()).thenReturn(List.of(templatesOutMapper.toTemplateOutDTO(template)));
        List<TemplateOutDTO> templateOutDTOList=new ArrayList<>();
        templateOutDTOList.add(templatesOutMapper.toTemplateOutDTO(template));
        Mockito.when(templateService.getAllTemlates()).thenReturn(templateOutDTOList);
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/templates"))
                 .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        List<TemplateOutDTO> templateOutDTOListResponce = objectMapper.readValue(jsonResponse, new TypeReference<List<TemplateOutDTO>>() {});
        assertEquals(templateOutDTOList,templateOutDTOListResponce);
    }

    @Test
    void getTemplateById() throws Exception {
        Template template=new Template();
        setDataForTest.setTemplate(template,dT);
        Mockito.reset(templateService);

        TemplateOutDTO templateOutDTO=templatesOutMapper.toTemplateOutDTO(template);

        Mockito.when(templateService.getTemplateById(1L)).thenReturn(templateOutDTO);
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/templates/1"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
       TemplateOutDTO templateOutDTOResponce = objectMapper.readValue(jsonResponse, new TypeReference<TemplateOutDTO>() {});
        assertEquals(templateOutDTO,templateOutDTOResponce);
    }
}