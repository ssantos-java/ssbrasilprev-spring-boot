package com.ssbrasilprev.error;

import java.util.Set;

public class ClienteUnSupportedFieldPatchException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -1740472923390184947L;

    public ClienteUnSupportedFieldPatchException(Set<String> keys) {
        super("Campo " + keys.toString() + " atualização não é permitida.");
    }

}
