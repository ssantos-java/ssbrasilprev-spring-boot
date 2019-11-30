package com.ssbrasilprev.error;

public class ProdutoNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 8583912418088319955L;

    public ProdutoNotFoundException(Long id) {
        super("Produto id não existe : " + id);
    }

}
