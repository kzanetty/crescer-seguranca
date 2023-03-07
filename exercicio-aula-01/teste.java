    //  ----------------- ****** Opcões de mitigação para o método buscar ****** -----------------

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








    //  ----------------- ****** Opcões de mitigação para o método login ****** -----------------

    //forma padrão falha não recomendada - login - Alteramos esse metodo - Esse método deixa brechas para SQL Injection. Não podemos utiliza-lo
    public Usuario login(UsuarioDto usuario) {
        var jpqlquery = String.format("select u from Usuario u where u.email = '%s' and u.senha = '%s'", usuario.getEmail(), StringHelper.md5(usuario.getSenha()));
        var list = entityManager.createQuery(jpqlquery, Usuario.class).getResultList();
        if(list.isEmpty())
            return null;
        else
            return list.get(0);
    }

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
   





    //  ----------------- ****** Opcões de mitigação utilizando uma interface que implementar o JpaRepository ****** -----------------

    // Criamos uma interface que extends JpaRepository e criamos os métodos que queremos nela.
    public interface UsuarioJpaRepository extends JpaRepository<Usuario, Long> {
        Usuario findByEmailAndSenha(String email, String senha);
    }

    //Em uma classe de Service nós fazer uma injeção de dependencia dessa interface UsuarioJpaRepository e chamamos método lá
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