package com.ssbrasilprev.error;

public class ClienteNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 8583712418088319955L;

    public ClienteNotFoundException(Long id) {
        super("Cliente id náo existe : " + id);
    }

}
