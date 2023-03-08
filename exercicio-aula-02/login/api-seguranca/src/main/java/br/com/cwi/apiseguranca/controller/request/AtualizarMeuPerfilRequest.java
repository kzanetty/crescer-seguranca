package br.com.cwi.apiseguranca.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AtualizarMeuPerfilRequest {

    @NotNull
    private Long id;
    @NotBlank
    private String nome;
    @NotBlank
    private String telefone;
    @NotBlank
    private String foto;
}
