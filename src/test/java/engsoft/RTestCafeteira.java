package engsoft;

import static org.junit.Assert.*;

import org.junit.Test;

import java.lang.reflect.Field;

public class RTestCafeteira {
	
	private static Hardware h;
	private static Cafeteira c;
	EstadoAquecedor ea;
	private MetaClass meta;

	@Test
	public void test() {
		meta = new MetaClass();
		
		h = new Hardware(false);
		
		c = new Cafeteira(h);
		ea = new EstadoAquecedor();
		
		meta.getClassComponents(h.getClass(), "ALL");
		meta.getClassComponents(c.getClass(), "ALL");
		meta.getClassComponents(ea.getClass(), "ALL");
		
		Class<?> classCafeteira = c.getClass();
		Class<?>[] classCafeteiraDC = classCafeteira.getDeclaredClasses();
				for (Class<?> innerClassesOfCafeteira : classCafeteiraDC) {
					if (innerClassesOfCafeteira.getCanonicalName() == "inf319.InnerEstadoAquecedor") {
						Field[]  f = innerClassesOfCafeteira.getFields();
						/*
						 * Warning: code incomplete.
					     * Should now get all fields and use them in the tests.
						 * Much trouble for nothing.
						 */
					}
		}
		
		Class<?> classHardware = h.getClass();
		assertEquals("class inf319.Hardware", classHardware.toString());
		assertEquals("class inf319.Cafeteira", classCafeteira.toString());
		assertEquals("inf319.Hardware", classHardware.toString().substring(6));

	}

}
