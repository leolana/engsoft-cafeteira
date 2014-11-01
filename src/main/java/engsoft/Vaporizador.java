package engsoft;

/**
 * Classe abstrata que apresenta a interface de um vaporizador de
 * uma cafeteira.
 */
public abstract class Vaporizador {

    private ControladorVaporizador controlador;
    private ClienteVaporizador     cliente;

    public Vaporizador(ControladorVaporizador oControlador,
		       ClienteVaporizador oCliente) {
	controlador = oControlador;
        cliente = oCliente;
    }

    // Estímulos externos
    public abstract boolean checaPronto();
    public abstract void jarra();
    public abstract void semJarra();
    public abstract void fazerCafe();

    // Interface para as subclasses
    protected ControladorVaporizador getController() {
        return controlador;
    }

    protected ClienteVaporizador getClient() {
        return cliente;
    }
}
