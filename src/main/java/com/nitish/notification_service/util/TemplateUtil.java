package com.nitish.notification_service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nitish.notification_service.enums.NotificationChannel;
import com.nitish.notification_service.exception.custom_exception.TemplateValidationException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class TemplateUtil {

    private static final List<String> FORBIDDEN_PATTERNS = List.of("${", "#{", "th:", "<script", "</script");
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{\\{(\\w+)}}");
    private static final ObjectMapper mapper = new ObjectMapper();

    private final int smsLength;
    private final int pushLength;

    public TemplateUtil
            (
                    @Value("${app.notification.channel-type.sms.content-length}") int smsLength,
                    @Value("${app.notification.channel-type.push.content-length}") int pushLength
            )
    {
        this.smsLength = smsLength;
        this.pushLength = pushLength;
    }

    public void validateTemplateSyntax(String content) {

        if (content == null || content.isBlank()) throw new TemplateValidationException("Content cannot be empty");

        if (content.length() > 10_000) throw new TemplateValidationException("Content too long");

        for (String forbidden : FORBIDDEN_PATTERNS) {
            if (content.contains(forbidden)) {
                throw new TemplateValidationException("Invalid template syntax detected");
            }
        }
    }

    public void validateContentLength(int contentLength, NotificationChannel channel){
        if (channel == NotificationChannel.SMS && contentLength > smsLength) throw new TemplateValidationException("SMS content exceeds required length");
        if (channel == NotificationChannel.PUSH && contentLength > pushLength) throw  new TemplateValidationException("Push notification too long");
    }


    public Set<String> extractPlaceholders(String content) {
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(content);
        return matcher.results()
                .map(m -> m.group(1))
                .collect(Collectors.toSet());
    }


    public String sanitizeHtml(String html){
        Safelist safelist = Safelist.relaxed()
                .addTags("h1", "h2", "h3", "table", "tr", "td", "th")
                .addAttributes(":all", "style")
                .addProtocols("img", "src", "cid", "http", "https");

        return Jsoup.clean(html, safelist);
    }

    public boolean isHtml(String content){
        Document document = Jsoup.parse(content);
        return document.body().children().isEmpty();
    }

    public String toJson(Set<String> placeholders) {
        try {
            return mapper.writeValueAsString(placeholders);
        } catch (Exception e) {
            throw new TemplateValidationException("Failed to serialize placeholders");
        }
    }

    public String[] toStringArray(String jsonArray) throws JsonProcessingException {
        return mapper.readValue(jsonArray, String[].class);
    }

    public String convertToThymeleafSyntax(String template) {

        return template.replaceAll(
                "\\{\\{(\\w+)}}",
                "[[\\${$1}]]"
        );
    }


}
