package br.com.cwi.apiseguranca.controller.response;

import br.com.cwi.apiseguranca.security.domain.Permissao;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String foto;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    private boolean ativo;

    private List<PermissaoResponse> permissoes;
}
