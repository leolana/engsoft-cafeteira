package engsoft;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HardwareTest {
    // Cria os componentes da cafeteira
    private static Hardware oHardware;
    private static Cafeteira aCafeteira;
    private static Aquecedor aquecedor;
    private static Vaporizador vaporizador;
    private static IHC ihc;

    @Before
    public void setUp() throws Exception {

	oHardware = new Hardware();
	aCafeteira = new Cafeteira();

	aquecedor = new Aquecedor(oHardware, aCafeteira);
	vaporizador = new Vaporizador(oHardware, aCafeteira);
	ihc = new IHC(oHardware, aCafeteira);

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
	assertEquals(EstadoHardware.valvulaFechada, oHardware.leEstadoValvulaPressao());

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
	//fail("Se necessário, implemente um teste para o Hardware/Cafeteira.");
    }

}
