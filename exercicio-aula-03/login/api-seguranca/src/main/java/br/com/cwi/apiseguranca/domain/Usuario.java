package br.com.cwi.apiseguranca.domain;

import br.com.cwi.apiseguranca.domain.enums.Funcao;
import br.com.cwi.apiseguranca.security.domain.Permissao;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    private boolean ativo;

    private String resetPasswordToken;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Permissao> permissoes = new ArrayList<>();

    public void adicionarPermissao(Permissao permissao) {
        this.permissoes.add(permissao);
        permissao.setUsuario(this);
    }


}
