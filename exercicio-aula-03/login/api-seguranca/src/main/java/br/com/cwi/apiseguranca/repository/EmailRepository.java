package br.com.cwi.apiseguranca.repository;

import br.com.cwi.apiseguranca.domain.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {
}
