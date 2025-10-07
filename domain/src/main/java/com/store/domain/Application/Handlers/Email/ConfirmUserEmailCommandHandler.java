package com.store.domain.Application.Handlers.Email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.store.domain.Application.Commands.Email.ConfirmUserEmailCommand;
import com.store.domain.Application.Services.EmailService;
import com.store.domain.Shared.MediatorPattern.CommandHandler;

import jakarta.mail.MessagingException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class ConfirmUserEmailCommandHandler implements CommandHandler<ConfirmUserEmailCommand, Boolean> {

    private static final Logger logger = LoggerFactory.getLogger(ConfirmUserEmailCommandHandler.class);

    @Autowired
    private EmailService emailService;
    @Value("classpath:emails/default_confirm.html")
    private Resource defaultConfirmTemplate;
    
   @Override
    public Boolean handle(ConfirmUserEmailCommand command) {
        String subject = "Confirmação de usuário";
        String bodyHtml = "";
        
        String iconCid = "confirm_user_icon.png"; 

        InputStreamSource attachmentIcon = new ClassPathResource("static/" + iconCid);

        Map<String, InputStreamSource> inlineContent = new HashMap<>();
        inlineContent.put(iconCid, attachmentIcon);

        try {
            bodyHtml = new String(defaultConfirmTemplate.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            bodyHtml = bodyHtml.replace("{{TEXT}}", emailText());
            bodyHtml = bodyHtml.replace("{{urlConfirmation}}", "http://localhost:8080/User/ConfirmUser/" + command.getConfirmId());
            bodyHtml = bodyHtml.replace("{{text_botton}}", "Confirmar");
            
            bodyHtml = bodyHtml.replace("{{ICON}}", iconCid);

        } catch (IOException e) {
            logger.error("Erro ao ler template de email", e);
            return false;
        }

        try {
            emailService.sendMultipartEmail(command.getEmail(), subject, bodyHtml, null, inlineContent);
        } catch (MessagingException e) {
            logger.error("Falha ao enviar email multipart para {}: {}", command.getEmail(), e.getMessage());
            return false;
        }
        return true;
    }

    private String emailText(){
        return "Olá bom dia! Identificamos uma tentativa de cadastro no nosso sistema usando o seu endereço de e-mail. Se você não foi o responsável por essa tentativa, por favor, ignore este e-mail. Por outro lado, se foi você, por favor, confirme sua inscrição clicando no botão abaixo.";
    }
}
