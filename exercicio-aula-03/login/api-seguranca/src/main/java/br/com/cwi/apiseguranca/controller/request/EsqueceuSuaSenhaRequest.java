package br.com.cwi.apiseguranca.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EsqueceuSuaSenhaRequest {

    private String email;
    private String siteUrl;
}
