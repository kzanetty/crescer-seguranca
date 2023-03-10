package br.com.cwi.apiseguranca.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailResponse {

    private String remetente;
    private String emailFrom;
    private String emailTo;
    private String titulo;
    private String mensagem;
}
