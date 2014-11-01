package engsoft;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HardwareTest {
    private static Hardware oHardware;
    private static Cafeteira aCafeteira;
    private static CafeBB_IHC ihc;
    private static CafeBB_Vaporizador vaporizador;
    private static CafeBB_Aquecedor aquecedor;

    @Before
    public void setUp() throws Exception {
        // Cria os componentes da cafeteira
        oHardware = new Hardware();
        aCafeteira = new Cafeteira();

        CafeBB_ControladorAquecedor controladorAquecedor =
            new CafeBB_ControladorAquecedor(oHardware);
        CafeBB_ControladorVaporizador controladorVaporizador =
            new CafeBB_ControladorVaporizador(oHardware);
        CafeBB_ControladorIHC controladorIHC =
            new CafeBB_ControladorIHC(oHardware);

        aquecedor =  new CafeBB_Aquecedor(controladorAquecedor, aCafeteira);
        vaporizador = new CafeBB_Vaporizador(controladorVaporizador, aCafeteira);
        ihc = new CafeBB_IHC(controladorIHC, aCafeteira);

        // Liga os componentes ao modulo de controle
        aCafeteira.ajustaIHC(ihc);
        aCafeteira.ajustaVaporizador(vaporizador);
        aCafeteira.ajustaAquecedor(aquecedor);
    }

    @Test
    public void test() {
	assertEquals(EstadoHardware.jarraVazia, oHardware.leEstadoAquecedor());
	assertEquals(EstadoHardware.ebulidorVazio, oHardware.leEstadoEbulidor());
	assertEquals(EstadoHardware.interruptorSolto, oHardware.leEstadoInterruptor());
	assertEquals(EstadoHardware.ebulidorDesligado, oHardware.leEstadoElementoEbulidor());
	assertEquals(EstadoHardware.aquecedorDesligado, oHardware.leEstadoElementoAquecedor());
	assertEquals(EstadoHardware.indicadoraDesligada, oHardware.leEstadoLuzIndicadora());
	assertEquals(EstadoHardware.valvulaAberta, oHardware.leEstadoValvulaPressao());

	oHardware.ajustaNivelDeAgua(12);
	assertEquals(12, oHardware.pegaNivelDeAgua());
	oHardware.ajustaNivelDeCafe(0);
	assertEquals(0, oHardware.pegaNivelDeCafe());
	oHardware.iniciar();
	oHardware.pressionaBotao();

	while(oHardware.pegaNivelDeAgua() > 0){
	    ihc.verifica();
	    vaporizador.verifica();
	    aquecedor.verifica();
	    assertEquals(EstadoHardware.indicadorCoando, oHardware.leEstadoLuzIndicadora());
	}

	assertEquals(12, oHardware.pegaNivelDeCafe());
	//fail("Implemente aqui o teste para o novo Hardware, se necessario.");
    }

}
