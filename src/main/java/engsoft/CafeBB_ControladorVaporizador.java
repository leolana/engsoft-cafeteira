package engsoft;

/**
 * Esta classe implementa a interface para o hardware do vaporizador da
 * cafeteira CafeBemBrasileiro.
 */
public class CafeBB_ControladorVaporizador implements ControladorVaporizador {
    
    private Hardware cafeteiraCafeBB;

    public CafeBB_ControladorVaporizador(Hardware oHardware) {
        cafeteiraCafeBB = oHardware;
    }

    public void jogaAgua() {
        cafeteiraCafeBB.atuEstadoElementoEbulidor(EstadoHardware.ebulidorLigado);
        cafeteiraCafeBB.atuValvulaPressao(EstadoHardware.valvulaFechada);
    }

    public void naoJogaAgua() {
        cafeteiraCafeBB.atuEstadoElementoEbulidor(
                EstadoHardware.ebulidorDesligado);
        cafeteiraCafeBB.atuValvulaPressao(EstadoHardware.valvulaAberta);
    }

    public boolean temAgua() {
        return cafeteiraCafeBB.leEstadoEbulidor().equals(
                EstadoHardware.ebulidorNaoVazio);
    }
}
