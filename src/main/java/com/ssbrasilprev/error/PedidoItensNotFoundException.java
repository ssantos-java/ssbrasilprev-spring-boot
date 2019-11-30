package com.ssbrasilprev.error;

public class PedidoItensNotFoundException extends RuntimeException {


    private static final long serialVersionUID = -3894130523008667306L;

    public PedidoItensNotFoundException(Long id) {
        super("PedidoItens id não existe : " + id);
    }

}
