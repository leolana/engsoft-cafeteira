package engsoft;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import engsoft.stateMachines.*;

public class AppTest extends TestCase {
    class forTest extends Statefull {
	forTest() {
	    this.setState("finished");
	}
    }

    public void testTransition() {
	StateMachine<forTest> s = new StateMachine<forTest>(new forTest());
	Event<forTest> e = s.addEvent();
	Transition<forTest> t = e.addTransition();

	t.from("finished").to("finished");

	t.guard(x -> true);
	assertEquals(true, s.canFire(e));
	assertEquals(true, t.canFire(new forTest()));
	assertEquals(true, e.canFire(new forTest()));

	t.guard(x -> false);
	assertEquals(false, s.canFire(e));
	assertEquals(false, t.canFire(new forTest()));
	assertEquals(false, e.canFire(new forTest()));
	s.consumeEvent(e);
	assertEquals(s.getState(), "finished");

	e.addTransition().from("finished").to("dont-care");
	assertEquals(true, s.canFire(e));
	assertEquals(true, e.canFire(new forTest()));

	s.consumeEvent(e);
	assertEquals(s.getState(), "dont-care");
    }
}
