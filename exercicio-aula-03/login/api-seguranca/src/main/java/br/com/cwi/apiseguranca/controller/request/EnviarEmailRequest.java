package br.com.cwi.apiseguranca.controller.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EnviarEmailRequest {

    @NotBlank
    private String titulo;
    @NotBlank
    private String mensagem;
}
