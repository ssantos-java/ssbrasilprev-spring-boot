package com.ssbrasilprev.error;

import java.util.Set;

public class PedidoUnSupportedFieldPatchException extends RuntimeException {

    private static final long serialVersionUID = -8647095890755205021L;

    public PedidoUnSupportedFieldPatchException(Set<String> keys) {
        super("Campo " + keys.toString() + " atualização não é permitida.");
    }

}
