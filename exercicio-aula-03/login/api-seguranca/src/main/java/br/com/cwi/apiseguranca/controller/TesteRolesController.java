package br.com.cwi.apiseguranca.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.cwi.apiseguranca.security.domain.enums.Funcao.Nomes.ADMIN;
import static br.com.cwi.apiseguranca.security.domain.enums.Funcao.Nomes.USUARIO;

@RestController
@RequestMapping("/testes")
public class TesteRolesController {

    @Secured(USUARIO)
    @GetMapping("/user")
    public String user() {
        return "Você está vendo essa mensagem pq vc tem a role User";
    }

    @Secured(ADMIN)
    @GetMapping("/admin")
    public String admin() {
        return "Você está vendo essa mensagem pq vc tem a role Admin";
    }
}
