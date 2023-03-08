package br.com.cwi.apiseguranca.controller;

import br.com.cwi.apiseguranca.controller.request.AtualizarMeuPerfilRequest;
import br.com.cwi.apiseguranca.controller.request.CriarUsuarioRequest;
import br.com.cwi.apiseguranca.controller.response.UsuarioResponse;
import br.com.cwi.apiseguranca.service.AtualizarMeuPerfilService;
import br.com.cwi.apiseguranca.service.CriarUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private CriarUsuarioService criarUsuarioService;

    @Autowired
    private AtualizarMeuPerfilService atualizarMeuPerfilService;


    @PostMapping
    @ResponseStatus(CREATED)
    public UsuarioResponse criarUsuario(@Valid @RequestBody CriarUsuarioRequest usuarioRequest) {
        return criarUsuarioService.criarUsuario(usuarioRequest);
    }

    @GetMapping("/listar")
    @ResponseStatus(ACCEPTED)
    public String testarLogar() {
        return "Você está logado";
    }

    @PostMapping("/atualizar")
    public UsuarioResponse atualizarMeuPerfil(@Valid @RequestBody AtualizarMeuPerfilRequest request) {
        return atualizarMeuPerfilService.atualizar(request);
    }

}
