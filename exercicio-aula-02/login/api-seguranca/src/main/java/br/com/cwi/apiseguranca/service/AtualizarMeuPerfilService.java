package br.com.cwi.apiseguranca.service;

import br.com.cwi.apiseguranca.controller.request.AtualizarMeuPerfilRequest;
import br.com.cwi.apiseguranca.controller.response.UsuarioResponse;
import br.com.cwi.apiseguranca.domain.Usuario;
import br.com.cwi.apiseguranca.mapper.UsuarioMapper;
import br.com.cwi.apiseguranca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AtualizarMeuPerfilService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BuscarUsuarioService buscarUsuarioService;

    public UsuarioResponse atualizar(AtualizarMeuPerfilRequest request) {
        Usuario usuario = buscarUsuarioService.porId(request.getId());
        usuario.setNome(request.getNome());
        usuario.setTelefone(request.getTelefone());
        usuario.setFoto(request.getFoto());
        usuario.setAtualizadoEm(LocalDateTime.now());

        usuarioRepository.save(usuario);

        return UsuarioMapper.toResponse(usuario);
    }
}
