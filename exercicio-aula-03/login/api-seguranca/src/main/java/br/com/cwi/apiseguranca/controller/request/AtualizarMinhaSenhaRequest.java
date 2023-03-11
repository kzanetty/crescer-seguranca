package br.com.cwi.apiseguranca.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AtualizarMinhaSenhaRequest {

    @NotBlank
    private String senha;

    private String token;
}
