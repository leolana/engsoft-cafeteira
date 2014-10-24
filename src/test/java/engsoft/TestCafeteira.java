package engsoft;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

public class TestCafeteira {
	
	private static Hardware h;
	private static Cafeteira c;
		
	@BeforeClass
	public static void upCafeteira() {
	
		
		/**  Instantiate a hardware and a coffee maker.
		 * Recall that the coffee maker has not a graphical interface
		 * because the hardware has been built with the hasIHC attribute
		 * set to false.
		 */

		h = new Hardware(false);
		c = new Cafeteira(h);
		
		/**
		 * Emulate the pressing of the Liga button.
		 */
		h.pressionaBotao();
		
		assertTrue(c.getEstadoIHC().equals(EstadoIHC.naoFazendo));
		assertTrue(c.getEstadoAquecedor().equals(EstadoAquecedor.naoAquecendo));
		assertTrue(c.getEstadoVaporizador().equals(EstadoVaporizador.naoFazendo));

	}

	@Test
	public void normalFlow() {
	// Place water in jar.	
    h.ajustaNivelDeAgua(10);
    // Initial run of the coffee maker.
    c.executa();
    assertEquals(EstadoIHC.fazendo, c.getEstadoIHC());
    assertEquals(EstadoAquecedor.fazendoJarVazia, c.getEstadoAquecedor());
    assertEquals(EstadoVaporizador.vaporizando, c.getEstadoVaporizador());
    // Next, execute the hardware.
    h.executa();
    h.executa();
    // After two executions of the hardware, the coffee maker should have
    assertEquals(8,h.pegaNivelDeAgua());
    assertEquals(2,h.pegaNivelDeCafe());
    // Invariant: volume(water + coffee) is constant.
    assertEquals(10,h.pegaNivelDeAgua()+h.pegaNivelDeCafe());
    c.executa();
    assertEquals(EstadoIHC.fazendo, c.getEstadoIHC());
    assertEquals(EstadoAquecedor.fazendoAquecendo, c.getEstadoAquecedor());
    assertEquals(EstadoVaporizador.vaporizando, c.getEstadoVaporizador());
    h.executa();
    h.executa();
    assertEquals(6,h.pegaNivelDeAgua());
    assertEquals(4,h.pegaNivelDeCafe());
    assertEquals(10,h.pegaNivelDeAgua()+h.pegaNivelDeCafe());
    assertEquals(EstadoIHC.fazendo, c.getEstadoIHC());
    assertEquals(EstadoAquecedor.fazendoAquecendo, c.getEstadoAquecedor());
    assertEquals(EstadoVaporizador.vaporizando, c.getEstadoVaporizador());
    for (int i = 0; i < 5; i++) h.executa();
    assertEquals(1,h.pegaNivelDeAgua());
    assertEquals(9,h.pegaNivelDeCafe());
    assertEquals(10,h.pegaNivelDeAgua()+h.pegaNivelDeCafe());
    h.executa();
    assertEquals(0,h.pegaNivelDeAgua());
    assertEquals(10,h.pegaNivelDeCafe());
    c.executa();
    assertEquals(EstadoAquecedor.cafeFeito, c.getEstadoAquecedor());
    assertEquals(EstadoVaporizador.naoFazendo, c.getEstadoVaporizador());
    assertEquals(EstadoIHC.cafeFeito, c.getEstadoIHC());
    }
	
	@Test
	public void exceptionalFlow() {
		
	}
	
	@AfterClass
	public static void downCafeteira() {
		h = null;
		c = null;
	}

}
