package br.gabriel.infnet.gabrielvictorapi.Application.Handlers.Email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Email.ForbiddenPasswordEmailCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.Services.EmailService;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.CommandHandler;

import jakarta.mail.MessagingException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class ForbiddenPasswordEmailHandler implements CommandHandler<ForbiddenPasswordEmailCommand, Boolean> {

    private static final Logger logger = LoggerFactory.getLogger(ConfirmUserEmailHandler.class);

    @Autowired
    private EmailService emailService;
    @Value("classpath:emails/default_confirm.html")
    private Resource defaultConfirmTemplate;
    
   @Override
    public Boolean handle(ForbiddenPasswordEmailCommand command) {
        String subject = "Reset de senha";
        String bodyHtml = "";
        
        String iconCid = "reset_password_icon.png"; 

        InputStreamSource attachmentIcon = new ClassPathResource("static/" + iconCid);

        Map<String, InputStreamSource> inlineContent = new HashMap<>();
        inlineContent.put(iconCid, attachmentIcon);

        try {
            bodyHtml = new String(defaultConfirmTemplate.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            bodyHtml = bodyHtml.replace("{{TEXT}}", emailText());
            bodyHtml = bodyHtml.replace("{{urlConfirmation}}", "http://localhost:8080/View/reset-password/" + command.getForbiddenPasswordId());
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
        return "Olá bom dia! Identificamos uma tentativa de resetar a senha do seu usuário no nosso sistema, por favor, se foi você continue o processo clicando botão abaixo.";
    }
}
