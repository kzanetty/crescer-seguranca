package br.com.cwi.apiseguranca.controller.request;

import br.com.cwi.apiseguranca.domain.enums.Funcao;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CriarUsuarioRequest {

    private String nome;
    private String email;
    private String telefone;
    private String senha;
    private String foto;
    private Funcao funcao;
}
