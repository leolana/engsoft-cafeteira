package engsoft;

/*
  interface grafica de uma cafeteira. Esta classe conhece clientes da
  interface e o controlador do hardware. Ela permite que classes externas
  notifiquem o estado do ciclo de confecção do café.
*/

public abstract class IHC {

    private ControladorIHC controlador;
    private ClienteIHC     cliente;

    public IHC(ControladorIHC oControlador, ClienteIHC oCliente) {
	controlador = oControlador;
        cliente = oCliente;
    }

    // Estímulos externos
    public abstract void cafeFeito();
    public abstract void cicloCompleto();

    // Interface para as subclasses
    protected ControladorIHC getController() {
        return controlador;
    }

    protected ClienteIHC getClient() {
        return cliente;
    }
}
