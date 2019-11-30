package com.ssbrasilprev.error;

import java.util.Set;

public class PedidoItensUnSupportedFieldPatchException extends RuntimeException {

 
    private static final long serialVersionUID = 7790732046157765975L;

    public PedidoItensUnSupportedFieldPatchException(Set<String> keys) {
        super("Campo " + keys.toString() + " atualização não é permitida.");
    }

}
