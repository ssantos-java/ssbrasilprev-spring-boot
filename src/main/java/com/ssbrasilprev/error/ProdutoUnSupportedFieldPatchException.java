package com.ssbrasilprev.error;

import java.util.Set;

public class ProdutoUnSupportedFieldPatchException extends RuntimeException {

  
    private static final long serialVersionUID = -1806400212502951158L;

    public ProdutoUnSupportedFieldPatchException(Set<String> keys) {
        super("Campo " + keys.toString() + " atualização não é permitida.");
    }

}
