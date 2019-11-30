package com.ssbrasilprev.error;

import java.util.Set;

public class CategoriaUnSupportedFieldPatchException extends RuntimeException {

    private static final long serialVersionUID = -7611170600757606060L;

    public CategoriaUnSupportedFieldPatchException(Set<String> keys) {
        super("Campo " + keys.toString() + " atualização não é permitida.");
    }

}
