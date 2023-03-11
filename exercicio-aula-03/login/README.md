# Login

Esse é uma aplicação onde os usuarios podem se cadastrar e entrar em seu perfil. Podem fazer alterações nas suas informações básicas e alterar a senha.

## Para rodar o projeto

```Dockerfile
docker compose up
```

## Ferramentas

- java 11
- Spring boot
- Postgres
- React
- Docker

# Observação importante

- As propriedades de

```Dockerfile
 spring.mail.username
```

```Dockerfile
 spring.mail.password
```

no application.yml, em api-segurança, foram removidas. Você devem informar um username e password valido para os endpoints de enviar mensagem funcionar.
