package com.siac070.SIACProject.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.siac070.SIACProject.service.EmailService;

@RestController
@CrossOrigin
public class EmailController {

    @Autowired

    private EmailService emailService;

    @PostMapping("/enviarCorreo")
    public ResponseEntity<Object> sendEmail(@RequestBody Map<String, String> emailData) {
        Map<String, Object> response = new HashMap<>();
        try {
            String correoDestinatario = emailData.get("correoDestinatario");
            String asunto = emailData.get("asunto") != null ? emailData.get("asunto").strip() : "";
            String mensaje = emailData.get("mensaje") != null ? emailData.get("mensaje").strip() : "";
            String plantillaNombre = emailData.get("plantillaNombre");

            boolean validacion = true;
            String mensajeRespuesta = "";

            // Validaciones de los campos
            if (correoDestinatario == null || correoDestinatario.isEmpty()) {
                mensajeRespuesta = "El correo destinatario es requerido";
                validacion = false;
            }

            if (asunto == null || asunto.isEmpty()) {
                mensajeRespuesta = "El asunto es requerido";
                validacion = false;
            }

            if (mensaje == null || mensaje.isEmpty()) {
                mensajeRespuesta = "El mensaje es requerido";
                validacion = false;
            }

            if (!validacion) {
                response.put("message", mensajeRespuesta);
                response.put("success", false);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // Llamar al servicio para enviar el correo
            emailService.enviarCorreo(correoDestinatario, asunto, mensaje, plantillaNombre);

            // Respuesta de éxito
            response.put("message", "Correo enviado correctamente");
            response.put("success", true);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (RuntimeException e) {
            response.put("message", "Error al enviar el correo");
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            // Manejar cualquier otro error
            response.put("message", "Error inesperado al enviar el correo");
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
