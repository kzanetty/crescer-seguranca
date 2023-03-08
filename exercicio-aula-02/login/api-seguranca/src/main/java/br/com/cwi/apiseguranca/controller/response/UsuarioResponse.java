package br.com.cwi.apiseguranca.controller.response;

import br.com.cwi.apiseguranca.domain.enums.Funcao;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String foto;
    private Funcao funcao;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    private boolean ativo;
}
