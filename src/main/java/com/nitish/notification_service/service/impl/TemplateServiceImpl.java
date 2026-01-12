package com.nitish.notification_service.service.impl;

import com.nitish.notification_service.dto.request.CreateTemplateRequest;
import com.nitish.notification_service.dto.response.CreateTemplateResponse;
import com.nitish.notification_service.entity.Client;
import com.nitish.notification_service.entity.NotificationTemplate;
import com.nitish.notification_service.entity.User;
import com.nitish.notification_service.enums.ContentType;
import com.nitish.notification_service.enums.NotificationChannel;
import com.nitish.notification_service.exception.custom_exception.DuplicateFieldException;
import com.nitish.notification_service.exception.custom_exception.EntityNotFoundException;
import com.nitish.notification_service.exception.custom_exception.TemplateValidationException;
import com.nitish.notification_service.repository.ClientRepository;
import com.nitish.notification_service.repository.TemplateRepository;
import com.nitish.notification_service.repository.UserRepository;
import com.nitish.notification_service.service.TemplateService;
import com.nitish.notification_service.util.mapper.TemplateMapper;
import com.nitish.notification_service.util.TemplateUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final TemplateMapper templateMapper;
    private final TemplateUtil templateUtil;

    public TemplateServiceImpl(TemplateRepository templateRepository, UserRepository userRepository, ClientRepository clientRepository, TemplateMapper templateMapper, TemplateUtil templateUtil) {
        this.templateRepository = templateRepository;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.templateMapper = templateMapper;
        this.templateUtil = templateUtil;
    }

    @Override
    public CreateTemplateResponse createTemplate(UUID userId, CreateTemplateRequest request){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("user", userId));

        Client client = clientRepository.findById(request.clientId())
                .orElseThrow(() -> new EntityNotFoundException("client", request.clientId()));

        String content = request.content();

        templateUtil.validateTemplateSyntax(content);
        templateUtil.validateContentLength(content.length(), request.channel());

        if (request.channel() != NotificationChannel.EMAIL && request.contentType() == ContentType.HTML){
            throw new TemplateValidationException("HTML content is only allowed for EMAIL");
        }

        if (request.contentType() == ContentType.HTML){
            if (templateUtil.isHtml(content)){
                throw new TemplateValidationException("Declared HTML but content is not HTML");
            }

            content = templateUtil.sanitizeHtml(content);
        }

        Set<String> placeHolders = templateUtil.extractPlaceholders(request.content());

        NotificationTemplate template = templateMapper.toTemplate(request);

        template.setContent(content);
        template.setPlaceholders(templateUtil.toJson(placeHolders));
        template.setCreatedBy(user);
        template.setClient(client);


        try {
            template = templateRepository.save(template);
        }catch (DataIntegrityViolationException e){
            if (e.getMessage().contains("uk_client_template_name")){
                throw new DuplicateFieldException("template already exists");
            }
            throw e;
        }
        return templateMapper.toCreateResponse(template);
    }
}
