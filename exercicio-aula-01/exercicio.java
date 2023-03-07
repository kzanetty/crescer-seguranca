package br.com.cwi.shop.repository;

import br.com.cwi.shop.dtos.UsuarioDto;
import br.com.cwi.shop.entities.Usuario;
import br.com.cwi.shop.helpers.StringHelper;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.postgresql.core.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

/*
Foram ensinadas 4 formas de mitigar a insegurança de SQL Injection
    - Mitigação com Escape
    - Mitigação com Parametrização
    - Mitigação com Parametrização Hibernate
    - Mitigação com JpaRepository (ORM)
*/

@Component
public class UsuarioRepository {

    @Autowired
    private EntityManager entityManager;
    

    // -------------------------- Apenas os exemplos de como mitigar ameaças de SQL Injection -------------------------------

   
    //Com escape - Podemos sanatizar utilizando um método que ira substituir todas aspas simples por aspas duplas.
    public List<Usuario> buscarComEscape(String filtro) {
        filtro = sanatizarFiltro(filtro);
        var sqlString = "select * from usuario u where nome like '%" + filtro + "%'";
        var query = entityManager.createNativeQuery(sqlString, Usuario.class);
        return query.getResultList();
    }
    private String sanatizarFiltro(String filtro) {
        return filtro.replaceAll("'", "''");
    }
  

    // Forma desenvolvida pelo proprio postgres onde ele ira substituir todas aspas simples por aspas duplas.
    public List<Usuario> buscarFormaNativaPostgres(String filtro) {
        filtro = sanatizarFiltroPostgres(filtro);
        var sqlString = "select * from usuario u where nome like '%" + filtro + "%'";
        var query = entityManager.createNativeQuery(sqlString, Usuario.class);
        return query.getResultList();
    }
    private String sanatizarFiltroPostgres(String filtro) {
        try {
            StringBuilder sb = new StringBuilder();
            Utils.escapeLiteral(sb, filtro, true);
            return sb.toString();
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    //Usando prepareStatement - parametrizamos as consultas feitas no banco de dados utilizando prepareStatement - Podemos utilizar dessa forma, mas não é usual e é muito robusta
    public Usuario loginStatement(UsuarioDto usuarioDto) {
        try {
            EntityManagerFactoryInfo info = (EntityManagerFactoryInfo) entityManager.getEntityManagerFactory();
            Connection connection = info.getDataSource().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("select * from usuario u where u.email = ? and u.senha = ?");

            preparedStatement.setString(1, usuarioDto.getEmail());
            preparedStatement.setString(2, StringHelper.md5(usuarioDto.getSenha()));

            var rs = preparedStatement.executeQuery();

            Usuario usuario = null;
            if(rs.next()){
                usuario = new Usuario();
                usuario.setId(rs.getLong("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setSobrenome(rs.getString("sobrenome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setFoto(rs.getString("foto"));
                usuario.setCriadoEm(rs.getDate("criado_em"));
            }
            return usuario;

        } catch(SQLException ex) {
            throw  new RuntimeException(ex);
        }
    }
  


    //Vamos utilizar o JPQL e parametrizar da forma adequada - O prorio hibernate utiliza o preparedStatement por baixo por panos nesse metodo.
    public Usuario loginJPQL(UsuarioDto usuario) {
        var jpqlquery = "select u from Usuario u where u.email = :email and u.senha = :senha";
        var list = entityManager.createQuery(jpqlquery, Usuario.class)
                .setParameter("email", usuario.getEmail())
                .setParameter("senha", StringHelper.md5(usuario.getSenha()))
                .getResultList();
        if (list.isEmpty())
            return null;
        else
            return list.get(0);
    }




    // Criamos uma interface que extends JpaRepository e criamos os métodos que queremos nela.
    public interface UsuarioJpaRepository extends JpaRepository<Usuario, Long> {
        Usuario findByEmailAndSenha(String email, String senha);
    }
    //Em uma classe de Service(?) nós fazemos uma injeção de dependencia dessa interface UsuarioJpaRepository e chamamos o método lá
    // O JpaRepository já tem por padrão medidas contra esse tipo de insegurança de SQl Injection.
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UsuarioDto usuarioDto, HttpServletResponse response) {
            var usuario = usuarioJpaRepository.findByEmailAndSenha(usuarioDto.getEmail(), StringHelper.md5(usuarioDto.getSenha()));
            
            if (usuario != null) {
                var usuarioLogadoDto = new UsuarioLogadoDto(usuario);
                try {
                    String jsonData = StringHelper.toJson(usuarioLogadoDto);
                    String cookieValue = StringHelper.toBase64(jsonData);
                    CookieHelper.AddCookie(response, Constantes.AUTH_COOKIE_NAME, cookieValue, 60 * 60);
                    return new ResponseEntity(usuarioLogadoDto, HttpStatus.OK);
                } catch (Exception ex) {
                    System.out.println(ex);
                    return new ResponseEntity("Erro Desconhecido", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            return new ResponseEntity("Não Autorizado", HttpStatus.UNAUTHORIZED);
    }
}