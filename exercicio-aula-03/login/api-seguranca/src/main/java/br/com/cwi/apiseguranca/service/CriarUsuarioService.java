package br.com.cwi.apiseguranca.service;

import br.com.cwi.apiseguranca.controller.request.CriarUsuarioRequest;
import br.com.cwi.apiseguranca.controller.response.UsuarioResponse;
import br.com.cwi.apiseguranca.domain.Usuario;
import br.com.cwi.apiseguranca.mapper.UsuarioMapper;
import br.com.cwi.apiseguranca.repository.UsuarioRepository;
import br.com.cwi.apiseguranca.security.domain.Permissao;
import br.com.cwi.apiseguranca.security.domain.enums.Funcao;
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

    @Autowired
    private ValidarSeEmailJaExisteService validarSeEmailJaExisteService;

    public UsuarioResponse criarUsuario(CriarUsuarioRequest usuarioRequest) {

        validarSeEmailJaExisteService.validar(usuarioRequest.getEmail());

        Usuario usuario = UsuarioMapper.toEntity(usuarioRequest);

        usuario.setSenha(getSenhaCriptografada(usuarioRequest.getSenha()));
        usuario.setCriadoEm(LocalDateTime.now());
        usuario.adicionarPermissao(getPermissaoPadrao());
        usuario.setAtivo(true);

        usuarioRepository.save(usuario);

        return UsuarioMapper.toResponse(usuario);
    }

    private String getSenhaCriptografada(String senhaAberta) {
        return passwordEncoder.encode(senhaAberta);
    }


    private Permissao getPermissaoPadrao() {
        Permissao permissao = new Permissao();
        permissao.setFuncao(Funcao.USUARIO);
        return permissao;
    }

}
