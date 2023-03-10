package br.com.cwi.apiseguranca.service;

import br.com.cwi.apiseguranca.controller.request.EnviarEmailRequest;
import br.com.cwi.apiseguranca.controller.response.EmailResponse;
import br.com.cwi.apiseguranca.domain.Email;
import br.com.cwi.apiseguranca.domain.Usuario;
import br.com.cwi.apiseguranca.domain.enums.StatusEmail;
import br.com.cwi.apiseguranca.mapper.EmailMapper;
import br.com.cwi.apiseguranca.repository.EmailRepository;
import br.com.cwi.apiseguranca.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EnviarEmailService {

    private final String EMAIL_FROM ="****deploy@gmail.com";
    private final String EMAIL_TO ="*****12@gmail.com";

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    public EmailResponse enviar(EnviarEmailRequest request) {
        Usuario usuario = usuarioAutenticadoService.get();

        Email email = EmailMapper.toEntity(request);
        email.setRemetente(usuario.getNome());
        email.setEmailFrom(EMAIL_FROM);
        email.setEmailTo(EMAIL_TO);
        email.setEnviadoEm(LocalDateTime.now());

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(EMAIL_FROM);
            message.setTo(EMAIL_TO);
            message.setSubject(request.getTitulo());
            message.setText(request.getMensagem());

            javaMailSender.send(message);

            email.setStatusEmail(StatusEmail.SUCESSO);
        } catch(MailException me) {
            email.setStatusEmail(StatusEmail.ERRO);
        } finally {
            emailRepository.save(email);
        }

        return EmailMapper.toResponse(email);
    }
}
