package br.com.cwi.apiseguranca.security.domain;


import br.com.cwi.apiseguranca.domain.Usuario;
import br.com.cwi.apiseguranca.security.domain.enums.Funcao;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Getter @Setter @EqualsAndHashCode(of = "id") @ToString(of = "id")
public class Permissao {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(STRING)
    private Funcao funcao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
