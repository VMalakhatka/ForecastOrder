package org.example.service;

import org.example.exception.NotFindByIDException;
import org.example.repository.templates.TemplateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TemplateServiceTest {
    @Mock
    private TemplateRepository templateRepository;
    @InjectMocks
    TemplateService templateService;

    @Test
    void getAllTemlates() {
    }
    @Test
    void getTemplateById() {
        Mockito.reset(templateRepository);
        Mockito.when(templateRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFindByIDException.class,()->templateService.getTemplateById(1L),
                "there is no such template.");
    }
}