package br.com.cwi.apiseguranca.service;

import br.com.cwi.apiseguranca.controller.request.AtualizarMeuPerfilRequest;
import br.com.cwi.apiseguranca.controller.response.UsuarioResponse;
import br.com.cwi.apiseguranca.domain.Usuario;
import br.com.cwi.apiseguranca.mapper.UsuarioMapper;
import br.com.cwi.apiseguranca.repository.UsuarioRepository;
import br.com.cwi.apiseguranca.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AtualizarMeuPerfilService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Transactional
    public UsuarioResponse atualizar(AtualizarMeuPerfilRequest request) {
        Usuario usuario = usuarioAutenticadoService.get();
        usuario.setNome(request.getNome());
        usuario.setTelefone(request.getTelefone());
        usuario.setFoto(request.getFoto());
        usuario.setAtualizadoEm(LocalDateTime.now());

        usuarioRepository.save(usuario);

        return UsuarioMapper.toResponse(usuario);
    }
}
