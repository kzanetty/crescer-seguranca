package br.com.cwi.apiseguranca.service;

import br.com.cwi.apiseguranca.controller.request.CriarUsuarioRequest;
import br.com.cwi.apiseguranca.controller.response.UsuarioResponse;
import br.com.cwi.apiseguranca.domain.Usuario;
import br.com.cwi.apiseguranca.mapper.UsuarioMapper;
import br.com.cwi.apiseguranca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CriarUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioResponse criarUsuario(CriarUsuarioRequest usuarioRequest) {
        Usuario usuario = UsuarioMapper.toEntity(usuarioRequest);
        usuario.setSenha(getSenhaCriptografada(usuarioRequest.getSenha()));
        usuario.setCriadoEm(LocalDateTime.now());
        usuario.setAtivo(true);

        usuarioRepository.save(usuario);

        return UsuarioMapper.toResponse(usuario);
    }

    private String getSenhaCriptografada(String senhaAberta) {
        return passwordEncoder.encode(senhaAberta);
    }
}
