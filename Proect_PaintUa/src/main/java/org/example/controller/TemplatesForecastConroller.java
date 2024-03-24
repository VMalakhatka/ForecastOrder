package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.example.dto.template.out.TemplateOutDTO;
import org.example.exeption.NotFindByID;
import org.example.service.MakeForecastOnSupplierService;
import org.example.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Tag(name = "Шаблоны прогноза",description = "Контроллер выводит и редактирует шаблоны для прогноза")
@Validated
@RequestMapping(path = "/templates")
@RestController
public class TemplatesForecastConroller {

    //TODO Realize editing of templates, based on data received from an external database

    TemplateService templateService;
    @Autowired
    public TemplatesForecastConroller(TemplateService templateService) {
        this.templateService = templateService;
    }

    @Operation(
            summary = "All existing templates",
            description = "The following templates are given for client selection"
    )
    @GetMapping()
    public List<TemplateOutDTO> getTemplates(){
        return templateService.getAllTemlates();
    }

    @Operation(
            summary = "Template by ID",
            description = "It searches among existing templates by ID, if it does not find it, " +
                    "it throws an exception "
    )
    @GetMapping("{id}")
    public TemplateOutDTO getTemplateById(@PathVariable("id") @Min(0)
                                              @Parameter(description = "the template ID is specified in the path")
                                              long id){
        try {
            return templateService.getTemplateById(id);
        } catch (NotFindByID e) {
            throw new RuntimeException(e);
        }
    }
}
