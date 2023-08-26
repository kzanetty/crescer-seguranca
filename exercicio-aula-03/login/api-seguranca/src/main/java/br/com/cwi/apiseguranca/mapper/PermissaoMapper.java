package br.com.cwi.apiseguranca.mapper;

import br.com.cwi.apiseguranca.controller.response.PermissaoResponse;
import br.com.cwi.apiseguranca.security.domain.Permissao;

public class PermissaoMapper {

    public static PermissaoResponse toResponse(Permissao permissao) {
        PermissaoResponse permissaoResponse = new PermissaoResponse();
        permissaoResponse.setNome(permissao.getFuncao().getRole());
        permissaoResponse.setUsuario(permissao.getUsuario().getNome());

        return permissaoResponse;
    }
}
