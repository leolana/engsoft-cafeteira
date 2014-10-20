package engsoft;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;

import engsoft.stateMachines.*;

class ForTest extends Statefull {
    ForTest() {
	this.setState("finished");
    }
}

public class AppTest {
    private static StateMachine<ForTest> s;
    private static StateMachine<ForTest> fs;
    private static Event<ForTest> e;
    private static Transition<ForTest> t;
    private static ForTest testObject;

    @BeforeClass
    public static void setup() {
	testObject = new ForTest();
	s = new StateMachine<ForTest>(testObject);
	e = s.addEvent("blarg");
	t = e.addTransition();

	fs = new StateMachine<ForTest>(testObject)
	    .addEvent("toggle")
	    .addTransition().from("finished").to("start").done()
	    .addTransition().from("start").to("finished").done()
	    .done();
    }

    @AfterClass
    public static void teardown() {
	s = null;
	e = null;
	t = null;
    }

    @Test
    public void testTransition() {
	t.from("finished").to("finished");

	t.guard(x -> true);
	assertEquals(true, s.canFire("blarg"));
	assertEquals(true, t.canFire(testObject));
	assertEquals(true, e.canFire(testObject));

	t.guard(x -> false);
	assertEquals(false, s.canFire("blarg"));
	assertEquals(false, t.canFire(testObject));
	assertEquals(false, e.canFire(testObject));
	s.consumeEvent("blarg");
	assertEquals(s.getState(), "finished");

	e.addTransition().from("finished").to("dont-care");
	assertEquals(true, s.canFire("blarg"));
	assertEquals(true, e.canFire(testObject));

	s.consumeEvent("blarg");
	assertEquals(s.getState(), "dont-care");
    }

    @Test
    public void testFullConstruction() {
    }
}
