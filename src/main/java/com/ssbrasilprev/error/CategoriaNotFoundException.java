package com.ssbrasilprev.error;

public class CategoriaNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 2948944840412558983L;

    public CategoriaNotFoundException(Long id) {
        super("Categoria [id] não existe : " + id);
    }

}
