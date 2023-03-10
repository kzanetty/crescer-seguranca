package br.com.cwi.apiseguranca.domain;

import br.com.cwi.apiseguranca.domain.enums.StatusEmail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String remetente;
    private String emailFrom;
    private String emailTo;
    private String titulo;
    private String mensagem;
    private LocalDateTime enviadoEm;
    @Enumerated(EnumType.STRING)
    private StatusEmail statusEmail;


}