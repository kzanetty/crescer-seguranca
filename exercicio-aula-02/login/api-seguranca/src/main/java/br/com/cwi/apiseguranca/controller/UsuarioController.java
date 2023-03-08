package br.com.cwi.apiseguranca.controller;

import br.com.cwi.apiseguranca.controller.request.CriarUsuarioRequest;
import br.com.cwi.apiseguranca.controller.response.UsuarioResponse;
import br.com.cwi.apiseguranca.security.service.UsuarioAutenticadoService;
import br.com.cwi.apiseguranca.service.CriarUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private CriarUsuarioService criarUsuarioService;

    @Autowired
    private UsuarioAutenticadoService service;

    @PostMapping
    @ResponseStatus(CREATED)
    public UsuarioResponse criarUsuario(@RequestBody CriarUsuarioRequest usuarioRequest) {
        return criarUsuarioService.criarUsuario(usuarioRequest);
    }

    @PostMapping("/login")
    @ResponseStatus(OK)
    public UsuarioResponse login() {
        return service.getResponse();
    }

    @GetMapping("/listar")
    @ResponseStatus(ACCEPTED)
    public String testarLogar() {
        return "Você está logado";
    }


}
