package br.com.cwi.apiseguranca.domain;

import br.com.cwi.apiseguranca.domain.enums.Funcao;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;
    private String senha;
    private String foto;
    @Enumerated(EnumType.STRING)
    private Funcao funcao;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    private boolean ativo;

    private String resetPasswordToken;

}
