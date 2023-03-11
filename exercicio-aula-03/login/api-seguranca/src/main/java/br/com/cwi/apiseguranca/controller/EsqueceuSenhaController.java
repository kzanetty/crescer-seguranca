package br.com.cwi.apiseguranca.controller;

import br.com.cwi.apiseguranca.controller.request.AtualizarMinhaSenhaRequest;
import br.com.cwi.apiseguranca.controller.request.EsqueceuSuaSenhaRequest;
import br.com.cwi.apiseguranca.service.UpdateResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reset")
public class EsqueceuSenhaController {

    @Autowired
    private UpdateResetPasswordService updateResetPasswordService;

    @PostMapping
    public void processForgetPassword(@RequestBody EsqueceuSuaSenhaRequest request) {
        updateResetPasswordService.processForgetPassword(request);
    }

    @GetMapping
    public boolean validateToken(@Param(value = "token") String token) {
        return updateResetPasswordService.validateToken(token);
    }

    @PostMapping("/update")
    public void updatePassword(@RequestBody AtualizarMinhaSenhaRequest request) {
        String token = request.getToken();
        String newPassword = request.getSenha();
        updateResetPasswordService.updatePassword(newPassword, token);
    }

}
