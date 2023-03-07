package br.com.cwi.shop.repository;

import br.com.cwi.shop.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioJpaRepository extends JpaRepository<Usuario, Long> {

    Usuario findByEmailAndSenha(String email, String senha);
}
