package br.com.cwi.apiseguranca.service;

import br.com.cwi.apiseguranca.domain.Usuario;
import br.com.cwi.apiseguranca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BuscarUsuarioService {

    private static final String MENSAGEM_PADRAO_NOT_FOUD ="Usuario não encontrado";

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario porId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, MENSAGEM_PADRAO_NOT_FOUD));
    }
}
