package com.siac070.SIACProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.SendFailedException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void enviarCorreo(String correoDestinatario, String asunto, String mensaje, String plantillaNombre) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(correoDestinatario);
            helper.setSubject(asunto);

            if (plantillaNombre != null && !plantillaNombre.isEmpty()) {
                Context context = new Context();
                context.setVariable("mensaje", mensaje);
                String htmlContent = templateEngine.process(plantillaNombre, context);
                helper.setText(htmlContent, true);
            } else {
                helper.setText(mensaje, false);
            }
            javaMailSender.send(mimeMessage);
        } catch (SendFailedException e) {
            throw new RuntimeException("Error al enviar el correo");
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo");
        }
    }

}
