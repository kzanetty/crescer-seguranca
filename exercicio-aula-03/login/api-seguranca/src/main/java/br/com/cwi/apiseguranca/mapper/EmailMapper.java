package br.com.cwi.apiseguranca.mapper;

import br.com.cwi.apiseguranca.controller.request.EnviarEmailRequest;
import br.com.cwi.apiseguranca.controller.response.EmailResponse;
import br.com.cwi.apiseguranca.domain.Email;

public class EmailMapper {

    public static Email toEntity(EnviarEmailRequest request) {
        return Email.builder()
                .titulo(request.getTitulo())
                .mensagem(request.getMensagem())
                .build();
    }

    public static EmailResponse toResponse(Email email) {
        return EmailResponse.builder()
                .remetente(email.getRemetente())
                .emailFrom(email.getEmailFrom())
                .emailTo(email.getEmailTo())
                .titulo(email.getTitulo())
                .mensagem(email.getMensagem())
                .build();
    }
}
