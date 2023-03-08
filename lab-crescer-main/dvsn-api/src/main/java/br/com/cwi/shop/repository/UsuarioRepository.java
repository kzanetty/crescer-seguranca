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

@Component
public class UsuarioRepository {

    @Autowired
    private EntityManager entityManager;

    public Usuario buscarPorId(long id) {
        var query = entityManager.createQuery("select u from Usuario u where u.id = :id", Usuario.class);
        query.setParameter("id", id);
        var list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    public Usuario buscarPorEmail(String email) {
        var query = entityManager.createQuery("select u from Usuario u where u.email = :email", Usuario.class);
        query.setParameter("email", email);
        var list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    @Transactional
    public void adicionar(UsuarioDto u) {
        String hashSenha = StringHelper.md5(u.getSenha());
        var sqlString = "INSERT INTO usuario (nome, sobrenome, email, senha, foto, funcao, criado_em) VALUES ('%s', '%s', '%s', '%s', '%s', %s, '%s')";
        sqlString = String.format(sqlString, u.getNome(), u.getSobrenome(), u.getEmail(), hashSenha, "", 2, new Date());
        entityManager.createNativeQuery(sqlString).executeUpdate();
    }

    @Transactional
    public void atualizar(UsuarioDto u) {
        if(StringHelper.isNullOrEmpty(u.getFoto())) {
            var sqlString = "UPDATE usuario SET nome = :nome, sobrenome = :sobrenome where id = :id";
            entityManager.createNativeQuery(sqlString)
                    .setParameter("nome", u.getNome())
                    .setParameter("sobrenome", u.getSobrenome())
                    .setParameter("id", u.getId())
                    .executeUpdate();
        } else {
            var sqlString = "UPDATE usuario SET nome = :nome, sobrenome = :sobrenome, foto = :foto where id = :id";
            entityManager.createNativeQuery(sqlString)
                    .setParameter("nome", u.getNome())
                    .setParameter("sobrenome", u.getSobrenome())
                    .setParameter("foto", u.getFoto())
                    .setParameter("id", u.getId())
                    .executeUpdate();
        }
    }

    // -------------------------- Exemplos de como mitigar ameaças de SQL Injection -------------------------------

    public List<Usuario> buscar(String filtro) {
        var sqlString = "select * from usuario u where nome like '%" + filtro + "%'";
        var query = entityManager.createNativeQuery(sqlString, Usuario.class);
        return query.getResultList();
    }
    /*
    // Forma 1 de mitigação - buscar - com escape - Podemos sanatizar utilizando um método que ira substituir todas aspas simples por aspas duplas.
    public List<Usuario> buscar(String filtro) {
        filtro = sanatizarFiltro(filtro);
        var sqlString = "select * from usuario u where nome like '%" + filtro + "%'";
        var query = entityManager.createNativeQuery(sqlString, Usuario.class);
        return query.getResultList();
    }
    private String sanatizarFiltro(String filtro) {
        return filtro.replaceAll("'", "''");
    }
    */
    /*
    // Forma 2 de mitigação - buscar - Forma desenvolvida pelo proprio postgres
    public List<Usuario> buscar(String filtro) {
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
     */



    /*
    //forma padrão falha não recomendada - login - Alteramos esse metodo - Esse método deixa brechas para SQL Injection. Não podemos utiliza-lo
    public Usuario login(UsuarioDto usuario) {
        var jpqlquery = String.format("select u from Usuario u where u.email = '%s' and u.senha = '%s'", usuario.getEmail(), StringHelper.md5(usuario.getSenha()));
        var list = entityManager.createQuery(jpqlquery, Usuario.class).getResultList();
        if(list.isEmpty())
            return null;
        else
            return list.get(0);
    }
     */
    /*
    //Forma 1 de mitigação - login - usando prepareStatement - parametrizamos as consultas feitas no banco de dados utilizando prepareStatement - Podemos utilizar dessa forma, mas não é usual e é muito robusta
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
     */
    /*
    //Forma 2 de mitigação - login - vamos utilizar o JPQL e parametrizar da forma adequada - O prorio hibernate utiliza o preparedStatement por baixo por panos nesse metodo.
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
     */
}