package org.example.controller;

import org.example.dao.user.UserDaoImpl;
import org.example.repository.DataLoader;
import org.example.repository.UserRepository;
import org.example.repository.templates.DataLoaderTemplates;
import org.example.service.FromExternalDatabaseService;
import org.example.service.MakeForecastOnSupplierService;
import org.example.service.TemplateService;
import org.example.service.UserSecurityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(AuthControllerTest.class)
class AuthControllerTest {
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

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getLoginPage() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/auth/login"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}