package br.com.cwi.apiseguranca.service;

import br.com.cwi.apiseguranca.controller.request.EnviarEmailRequest;
import br.com.cwi.apiseguranca.controller.response.EmailResponse;
import br.com.cwi.apiseguranca.domain.Email;
import br.com.cwi.apiseguranca.domain.Usuario;
import br.com.cwi.apiseguranca.domain.enums.StatusEmail;
import br.com.cwi.apiseguranca.mapper.EmailMapper;
import br.com.cwi.apiseguranca.repository.EmailRepository;
import br.com.cwi.apiseguranca.repository.UsuarioRepository;
import br.com.cwi.apiseguranca.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class EnviarEmailService {

    private final String EMAIL_FROM ="***********loy@gmail.com";
    private final String EMAIL_TO ="************2@gmail.com";

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

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

    public EmailResponse enviarPorEsqueceuSuaSenha(String emailTo, String titulo, String conteudo) {
        Usuario usuario = usuarioRepository.findByEmail(emailTo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario não encontrado por esse email."));


        Email email = new Email();
        email.setTitulo(titulo);
        email.setMensagem(conteudo);
        email.setRemetente(usuario.getNome());
        email.setEmailFrom(EMAIL_FROM);
        email.setEmailTo(emailTo);
        email.setEnviadoEm(LocalDateTime.now());

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(EMAIL_FROM);
            message.setTo(emailTo);
            message.setSubject(titulo);
            message.setText(conteudo);

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
