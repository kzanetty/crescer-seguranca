package br.com.cwi.shop.controllers;

import br.com.cwi.shop.dtos.UsuarioDto;
import br.com.cwi.shop.dtos.UsuarioLogadoDto;
import br.com.cwi.shop.helpers.Constantes;
import br.com.cwi.shop.helpers.CookieHelper;
import br.com.cwi.shop.helpers.StringHelper;
import br.com.cwi.shop.repository.UsuarioJpaRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController extends BaseController {

    @Autowired
    private UsuarioJpaRepository usuarioJpaRepository;

    @PostMapping("criarConta")
    public ResponseEntity criarConta(@RequestBody UsuarioDto usuarioDto) {
        try {

            if (!StringHelper.isEmail(usuarioDto.getEmail()))
                return badRequest("Email inválido.");

            if (StringHelper.isNullOrEmpty(usuarioDto.getNome())) {
                return badRequest("Nome inválido.");
            }

            if (StringHelper.isNullOrEmpty(usuarioDto.getSobrenome())) {
                return badRequest("Sobrenome inválido.");
            }

            if (usuarioRepository.buscarPorEmail(usuarioDto.getEmail()) != null) {
                return badRequest("Email já cadastrado.");
            }

            try {
                usuarioRepository.adicionar(usuarioDto);
            } catch (Exception ex) {
                return new ResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return ResponseEntity.ok().build();
        }catch(Exception ex) {
            return internalServerError(ex);
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UsuarioDto usuarioDto, HttpServletResponse response) {

        try {
            // Esse try-catch não é necessario quando usamos usuarioJpaRepository, mas para as outras opções sim.

            // login(usuarioDto)[muitas fahas]
            // loginStatement(usuarioDto)[Muito robusto]
            // loginJPQL(usuarioDto)[Melhor, mas ainda é melhor utilizar uma interface que extends JpaRepository]
            // findByEmailAndSenha(usuarioDto.getEmail(), StringHelper.md5(usuarioDto.getSenha())) - criamos um método "mágico", é o mais recomendado e seguro, para nossa situação atual.
            var usuario = usuarioJpaRepository.findByEmailAndSenha(usuarioDto.getEmail(), StringHelper.md5(usuarioDto.getSenha()));

            if (usuario != null) {

                var usuarioLogadoDto = new UsuarioLogadoDto(usuario);

                try {
                    String jsonData = StringHelper.toJson(usuarioLogadoDto);
                    String cookieValue = StringHelper.toBase64(jsonData);
                    CookieHelper.AddCookie(response, Constantes.AUTH_COOKIE_NAME, cookieValue, 60 * 60);
                    return new ResponseEntity(usuarioLogadoDto, HttpStatus.OK);
                } catch (Exception ex) {
                    System.out.println(ex);
                    return new ResponseEntity("Erro Desconhecido", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            return new ResponseEntity("Não Autorizado", HttpStatus.UNAUTHORIZED);
        }catch (Exception ex) {
            return internalServerError(ex);
        }
    }

    @GetMapping("logout")
    public ResponseEntity logout(HttpServletResponse response) {
        try {
            CookieHelper.AddCookie(response, Constantes.AUTH_COOKIE_NAME, "", 0);
            return new ResponseEntity(HttpStatus.OK);
        } catch(Exception ex) {
            return internalServerError(ex);
        }
    }
}
