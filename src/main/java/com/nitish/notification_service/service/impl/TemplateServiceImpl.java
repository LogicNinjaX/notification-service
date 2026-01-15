package com.nitish.notification_service.service.impl;

import com.nitish.notification_service.dto.request.CreateTemplateRequest;
import com.nitish.notification_service.dto.request.UpdateTemplateRequest;
import com.nitish.notification_service.dto.response.CreateTemplateResponse;
import com.nitish.notification_service.dto.response.PageResponse;
import com.nitish.notification_service.dto.response.TemplateResponse;
import com.nitish.notification_service.dto.response.UpdateTemplateResponse;
import com.nitish.notification_service.entity.Client;
import com.nitish.notification_service.entity.NotificationTemplate;
import com.nitish.notification_service.entity.User;
import com.nitish.notification_service.enums.ContentType;
import com.nitish.notification_service.enums.NotificationChannel;
import com.nitish.notification_service.enums.TemplateStatus;
import com.nitish.notification_service.exception.custom_exception.DuplicateFieldException;
import com.nitish.notification_service.exception.custom_exception.EntityNotFoundException;
import com.nitish.notification_service.exception.custom_exception.TemplateValidationException;
import com.nitish.notification_service.repository.ClientRepository;
import com.nitish.notification_service.repository.TemplateRepository;
import com.nitish.notification_service.repository.UserRepository;
import com.nitish.notification_service.service.TemplateService;
import com.nitish.notification_service.util.mapper.TemplateMapper;
import com.nitish.notification_service.util.TemplateUtil;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final TemplateMapper templateMapper;
    private final TemplateUtil templateUtil;
    private static final Logger logger = LoggerFactory.getLogger(TemplateServiceImpl.class);

    public TemplateServiceImpl(TemplateRepository templateRepository, UserRepository userRepository, ClientRepository clientRepository, TemplateMapper templateMapper, TemplateUtil templateUtil) {
        this.templateRepository = templateRepository;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.templateMapper = templateMapper;
        this.templateUtil = templateUtil;
    }

    @Transactional
    @Override
    public CreateTemplateResponse createTemplate(UUID userId, CreateTemplateRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("user", userId));

        Client client = clientRepository.findById(request.clientId())
                .orElseThrow(() -> new EntityNotFoundException("client", request.clientId()));

        String content = request.content();

        templateUtil.validateTemplateSyntax(content);
        templateUtil.validateContentLength(content.length(), request.channel());

        if (request.channel() != NotificationChannel.EMAIL && request.contentType() == ContentType.HTML) {
            throw new TemplateValidationException("HTML content is only allowed for EMAIL");
        }

        if (request.contentType() == ContentType.HTML) {
            if (templateUtil.isHtml(content)) {
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
        template.setStatus(TemplateStatus.INACTIVE);


        try {
            template = templateRepository.saveAndFlush(template);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("uk_client_template_name")) {
                throw new DuplicateFieldException("template already exists");
            }
            throw e;
        }
        logger.info(
                "template created successfully [template id={}, channel type={}, created by={}, client={}]",
                template.getTemplateId(), template.getChannel(), template.getCreatedBy().getUserId(), template.getClient().getClientId()
        );
        return templateMapper.toCreateResponse(template);
    }

    @Transactional
    @Override
    public UpdateTemplateResponse updateTemplate(UUID templateId, UpdateTemplateRequest request) {
        NotificationTemplate template = templateRepository.findById(templateId)
                .orElseThrow(() -> new EntityNotFoundException("template", templateId));

        if (request.name() != null) template.setName(request.name());
        if (request.channel() != null) template.setChannel(request.channel());
        if (request.subject() != null) template.setSubject(request.subject());

        if (request.content() != null) {
            template.setContent(request.content());

            Set<String> placeHolders = templateUtil.extractPlaceholders(request.content());

            template.setPlaceholders(templateUtil.toJson(placeHolders));
        }

        try{
            template = templateRepository.saveAndFlush(template);
        }catch (DataIntegrityViolationException e){
            if (e.getMessage().contains("uk_client_template_name")){
                throw new DuplicateFieldException("template name already exists try a different name");
            }
            throw e;
        }

        logger.info("template record updated successfully [template id={}]", template.getTemplateId());

        return templateMapper.toUpdateResponse(template);
    }

    @Override
    public PageResponse<TemplateResponse> getTemplateByCreatorId(UUID creatorId, Pageable pageable){
        Page<TemplateResponse> page = templateRepository.getTemplatesByCreatorId(creatorId, pageable)
                .map(templateMapper::toTemplateResponse);
        return PageResponse.from(page);

    }

    @Override
    public void deleteTemplateByUserAndTemplateId(UUID userId, UUID templateId){
        int updatedRows = templateRepository.deleteTemplateById(templateId, userId);
        if (updatedRows > 0) logger.info("template record deleted successfully [template id={}, user id={}]", templateId, userId);
    }
}
