package br.gabriel.infnet.gabrielvictorapi.Application.Services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.gabriel.infnet.gabrielvictorapi.Shared.Anotations.LogMask;

import java.net.URLConnection;
import java.util.Map;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendMultipartEmail(String to, String subject, @LogMask String htmlBody,
            Map<String, InputStreamSource> attachments,
            Map<String, InputStreamSource> inlineContent) throws MessagingException {
        logger.info("Iniciando envio de email multipart para {}", to);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        if (attachments != null && !attachments.isEmpty()) {
            for (Map.Entry<String, InputStreamSource> entry : attachments.entrySet()) {
                helper.addAttachment(entry.getKey(), entry.getValue());
                logger.info("Anexo {} adicionado ao email.", entry.getKey());
            }
        }

        if (inlineContent != null && !inlineContent.isEmpty()) {
            for (Map.Entry<String, InputStreamSource> entry : inlineContent.entrySet()) {
                String contentId = entry.getKey();
                InputStreamSource source = entry.getValue();

                String contentType = URLConnection.guessContentTypeFromName(contentId);
                helper.addInline(contentId, source, contentType);

                logger.info("Conteúdo inline com CID '{}' (tipo: {}) adicionado ao email.", contentId, contentType);
            }
        }
        javaMailSender.send(message);
        logger.info("Email multipart enviado com sucesso para {}", to);
    }
}