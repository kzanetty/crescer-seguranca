package br.com.cwi.apiseguranca.mapper;

import br.com.cwi.apiseguranca.controller.request.CriarUsuarioRequest;
import br.com.cwi.apiseguranca.controller.response.UsuarioResponse;
import br.com.cwi.apiseguranca.domain.Usuario;

public class UsuarioMapper {

    public static Usuario toEntity(CriarUsuarioRequest usuarioRequest) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioRequest.getNome());
        usuario.setEmail(usuarioRequest.getEmail());
        usuario.setTelefone(usuarioRequest.getTelefone());
        usuario.setFoto(usuarioRequest.getFoto());
        usuario.setFuncao(usuarioRequest.getFuncao());
        return usuario;
    }

    public static UsuarioResponse toResponse(Usuario usuario) {
        return UsuarioResponse.builder()

                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .telefone(usuario.getTelefone())
                .foto(usuario.getFoto())
                .funcao(usuario.getFuncao())
                .criadoEm(usuario.getCriadoEm())
                .atualizadoEm(usuario.getAtualizadoEm())
                .ativo(usuario.isAtivo())
                .build();
    }

}
