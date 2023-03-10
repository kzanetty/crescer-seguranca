package br.com.cwi.apiseguranca.controller;


import br.com.cwi.apiseguranca.controller.request.EnviarEmailRequest;
import br.com.cwi.apiseguranca.controller.response.EmailResponse;
import br.com.cwi.apiseguranca.service.EnviarEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EnviarEmailService enviarEmailService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public EmailResponse enviarEmail(@Valid @RequestBody EnviarEmailRequest request) {
        return enviarEmailService.enviar(request);
    }
}
