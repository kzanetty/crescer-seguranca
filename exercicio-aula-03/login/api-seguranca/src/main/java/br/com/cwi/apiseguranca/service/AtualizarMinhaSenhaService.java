package br.com.cwi.apiseguranca.service;

import br.com.cwi.apiseguranca.controller.request.AtualizarMinhaSenhaRequest;
import br.com.cwi.apiseguranca.controller.request.EnviarEmailRequest;
import br.com.cwi.apiseguranca.controller.response.UsuarioResponse;
import br.com.cwi.apiseguranca.domain.Usuario;
import br.com.cwi.apiseguranca.mapper.UsuarioMapper;
import br.com.cwi.apiseguranca.repository.UsuarioRepository;
import br.com.cwi.apiseguranca.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class AtualizarMinhaSenhaService {

    private final String MENSAGEM_PADRAO_SENHA_REPETIDA = "A nova senha deve ser diferente";
    private final String MENSAGEM_PADRAO_TITULO = "Sua senha foi alterada.";
    private final String MENSAGEM_PADRAO_MENSAGEM = "Sua senha foi alterada no dia " + LocalDate.now() + ".";

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EnviarEmailService enviarEmailService;

    @Transactional
    public UsuarioResponse atualizar(AtualizarMinhaSenhaRequest request) {
        Usuario usuario = usuarioAutenticadoService.get();
        String encodedNewPassword = getSenhaCriptografada((request.getSenha()));

        if(passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MENSAGEM_PADRAO_SENHA_REPETIDA);
        }

        usuario.setSenha(encodedNewPassword);
        usuarioRepository.save(usuario);

        EnviarEmailRequest enviarEmailRequest = new EnviarEmailRequest();
        enviarEmailRequest.setTitulo(MENSAGEM_PADRAO_TITULO);
        enviarEmailRequest.setMensagem(MENSAGEM_PADRAO_MENSAGEM);

//        enviarEmailService.enviar(enviarEmailRequest);

        return UsuarioMapper.toResponse(usuario);
    }

    private String getSenhaCriptografada(String senhaAberta) {
        return passwordEncoder.encode(senhaAberta);
    }
}
