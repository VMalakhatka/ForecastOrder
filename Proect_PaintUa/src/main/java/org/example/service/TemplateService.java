package org.example.service;

import org.example.dto.template.out.TemplateOutDTO;
import org.example.entity.templates.Template;
import org.example.exeption.NotFindByID;
import org.example.mapper.template.out.TemplatesOutMapper;
import org.example.repository.templates.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TemplateService {
    TemplateRepository templateRepository;
    TemplatesOutMapper templatesOutMapper;

    @Autowired
    public TemplateService(TemplateRepository templateRepository, TemplatesOutMapper templatesOutMapper) {
        this.templateRepository = templateRepository;
        this.templatesOutMapper = templatesOutMapper;
    }

    public List<TemplateOutDTO> getAllTemlates() {
        return templateRepository.findAll().stream().map(t-> templatesOutMapper.toTemplateOutDTO(t)).
                collect(Collectors.toList());
    }

    public TemplateOutDTO getTemplateById(long id) throws NotFindByID {
        Optional<Template> templateOptional=templateRepository.findById(id);
        Template template=templateOptional.orElseThrow(()->
                new NotFindByID("there is no such template."));
        return templatesOutMapper.toTemplateOutDTO(template);
    }
}
