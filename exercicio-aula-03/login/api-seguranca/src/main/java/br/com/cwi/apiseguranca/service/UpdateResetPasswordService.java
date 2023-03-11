package br.com.cwi.apiseguranca.service;

import br.com.cwi.apiseguranca.controller.request.EsqueceuSuaSenhaRequest;
import br.com.cwi.apiseguranca.domain.Usuario;
import br.com.cwi.apiseguranca.exceptions.UsuarioNotFoundException;
import br.com.cwi.apiseguranca.repository.UsuarioRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UpdateResetPasswordService {

    public static String MENSAGEM_PADRAO_ESQUECEU_SUA_SENHA_PARA_EMAIL = "Alterar senha";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AtualizarMinhaSenhaService atualizarMinhaSenhaService;

    @Autowired
    private EnviarEmailService enviarEmailService;

    public void processForgetPassword(EsqueceuSuaSenhaRequest request) {
        String email = request.getEmail();
        String siteUrl = request.getSiteUrl();
        String token = RandomString.make(50);

        String resetPasswordLink = siteUrl + "/reset?token="+ token;

        updateResetPasswordToken(token, email);

        enviarEmailService.enviarPorEsqueceuSuaSenha(email,MENSAGEM_PADRAO_ESQUECEU_SUA_SENHA_PARA_EMAIL, resetPasswordLink);
    }

    public void updateResetPasswordToken(String token, String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNotFoundException("Email inexistente."));

        usuario.setResetPasswordToken(token);
        usuarioRepository.save(usuario);
    }

    public Usuario get(String resetPasswordToken) {
        return usuarioRepository.findByResetPasswordToken(resetPasswordToken);
    }

    private String getSenhaCriptografada(String senhaAberta) {
        return passwordEncoder.encode(senhaAberta);
    }

    public boolean validateToken(String token) {
        return get(token) != null;
    }

    public void updatePassword(String newPassword, String token) {
        Usuario usuario = get(token);

        if(usuario == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token invalido");
        }

        String encodedNewPassword = getSenhaCriptografada(newPassword);

        usuario.setSenha(encodedNewPassword);
        usuario.setResetPasswordToken(null);

        usuarioRepository.save(usuario);
    }
}
