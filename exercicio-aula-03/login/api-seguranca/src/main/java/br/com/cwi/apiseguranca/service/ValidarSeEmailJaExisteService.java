package br.com.cwi.apiseguranca.service;

import br.com.cwi.apiseguranca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ValidarSeEmailJaExisteService {

    private static final String MENSAGEM_PADRAO_EMAIL_JA_EXISTE ="Já existe um usuario com esse email";

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void validar(String email) {
        if(usuarioRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MENSAGEM_PADRAO_EMAIL_JA_EXISTE);
        }
    }
}
