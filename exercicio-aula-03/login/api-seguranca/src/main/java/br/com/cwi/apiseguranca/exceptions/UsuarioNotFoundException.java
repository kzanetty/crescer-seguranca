package br.com.cwi.apiseguranca.exceptions;

public class UsuarioNotFoundException extends RuntimeException{

    public UsuarioNotFoundException(String msg) {
        super(msg);
    }
}
