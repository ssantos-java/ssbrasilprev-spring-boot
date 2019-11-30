package com.ssbrasilprev.error;

public class PedidoNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -7241749403100223499L;

    public PedidoNotFoundException(Long id) {
        super("Pedido id náo existe : " + id);
    }

}
