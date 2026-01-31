package com.nitish.notification_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nitish.notification_service.dto.request.SendNotificationRequest;
import com.nitish.notification_service.entity.*;
import com.nitish.notification_service.enums.*;
import com.nitish.notification_service.exception.custom_exception.EntityNotFoundException;
import com.nitish.notification_service.exception.custom_exception.EntityStatusException;
import com.nitish.notification_service.exception.custom_exception.VariableNotFoundException;
import com.nitish.notification_service.repository.*;
import com.nitish.notification_service.service.NotificationService;
import com.nitish.notification_service.util.EmailValidator;
import com.nitish.notification_service.util.TemplateUtil;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRequestRepository requestRepository;
    private final NotificationMessageRepository messageRepository;
    private final UserRepository userRepository;
    private final OutBoxEventRepository eventRepository;
    private final TemplateRepository templateRepository;
    private final TemplateUtil templateUtil;
    private final ThymeleafTemplateRenderer templateRenderer;
    private final EmailValidator emailValidator;
    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    public NotificationServiceImpl(NotificationRequestRepository requestRepository, NotificationMessageRepository messageRepository, UserRepository userRepository, OutBoxEventRepository eventRepository, TemplateRepository templateRepository, TemplateUtil templateUtil, ThymeleafTemplateRenderer templateRenderer, EmailValidator emailValidator) {
        this.requestRepository = requestRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.templateRepository = templateRepository;
        this.templateUtil = templateUtil;
        this.templateRenderer = templateRenderer;
        this.emailValidator = emailValidator;
    }


    @Transactional
    @Override
    public void sendNotification(UUID userId, SendNotificationRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("user", userId));

        NotificationTemplate template = templateRepository.findById(request.templateId())
                .orElseThrow(() -> new EntityNotFoundException("template", request.templateId()));

        if (template.getStatus() != TemplateStatus.ACTIVE) throw new EntityStatusException("template is inactive");

        Map<String, Object> variables = request.variables();
        String[] recipients = request.recipients();

        validateVariables(variables, template);
        validateNotificationRequest(template, request);

        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setTemplate(template);
        notificationRequest.setRequestedBy(user);
        notificationRequest.setChannel(template.getChannel());
        notificationRequest.setStatus(RequestStatus.PENDING);

        notificationRequest = requestRepository.save(notificationRequest);

        List<NotificationMessage> messageList = messageRepository
                .saveAll(createMessageList(notificationRequest, template, variables, recipients));

        for (NotificationMessage message : messageList){
            OutBoxEvent event = createMessageEvent(message);
            eventRepository.save(event);
        }

        logger.info("notification request created successfully [notification id={}]", notificationRequest.getRequestId());
    }

    private void validateNotificationRequest(NotificationTemplate template, SendNotificationRequest request) {
        if (template.getChannel() == NotificationChannel.EMAIL){
            emailValidator.validate(template,request);
        }
    }

    private void validateVariables(Map<String, Object> variables, NotificationTemplate template) {
        try {
            String[] placeHolders = templateUtil.toStringArray(template.getPlaceholders());
            for (String placeHolder : placeHolders) {
                if (!variables.containsKey(placeHolder)) {
                    throw new VariableNotFoundException("placeholder is missing");
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private List<NotificationMessage> createMessageList(NotificationRequest notificationRequest, NotificationTemplate template, Map<String, Object> variables, String[] recipients) {
        List<NotificationMessage> messageList = new ArrayList<>();

        for (String recipient : recipients) {
            NotificationMessage notificationMessage = new NotificationMessage();
            notificationMessage.setRequest(notificationRequest);
            notificationMessage.setRecipient(recipient);


            notificationMessage.setRenderedSubject(
                    template.getSubject() != null ? templateRenderer.render(template.getSubject(), variables) : null
            );

            notificationMessage.setRenderedContent(
                    templateRenderer.render(template.getContent(), variables)
            );

            notificationMessage.setStatus(MessageStatus.QUEUED);

            messageList.add(notificationMessage);
        }

        return messageList;
    }

    private OutBoxEvent createMessageEvent(NotificationMessage message){
        OutBoxEvent event = new OutBoxEvent();
        event.setAggregateType(AggregateType.NOTIFICATION_MESSAGE);
        event.setAggregateId(message.getMessageId());
        event.setEventType(EventType.MESSAGE_CREATED);
        event.setStatus(EventStatus.NEW);

        event.setPayload("message id: "+message.getMessageId());

        return event;
    }
}
