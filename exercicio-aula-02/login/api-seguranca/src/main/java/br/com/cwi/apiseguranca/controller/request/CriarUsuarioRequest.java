package br.com.cwi.apiseguranca.controller.request;

import br.com.cwi.apiseguranca.domain.enums.Funcao;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CriarUsuarioRequest {

    @NotBlank
    private String nome;
    @NotBlank
    private String email;
    @NotBlank
    private String telefone;
    @NotBlank
    private String senha;
    @NotBlank
    private String foto;
    @NotNull
    private Funcao funcao;
}
