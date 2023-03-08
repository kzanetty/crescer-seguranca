package br.com.cwi.apiseguranca.security.config;


import br.com.cwi.apiseguranca.domain.Usuario;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UsuarioSecurity implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;

    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    public UsuarioSecurity(Usuario usuario) {

        this.id = usuario.getId();
        this.username = usuario.getEmail();
        this.password = usuario.getSenha();

        this.accountNonExpired = usuario.isAtivo();
        this.accountNonLocked = usuario.isAtivo();
        this.credentialsNonExpired = usuario.isAtivo();
        this.enabled = usuario.isAtivo();

        this.authorities = new ArrayList();
    }
}