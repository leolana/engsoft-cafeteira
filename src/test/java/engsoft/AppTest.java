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

	t.guard(x -> false);
	assertEquals(false, s.canFire("blarg"));
	assertEquals(false, t.canFire(testObject));
	assertEquals(false, e.canFire(testObject));

	t.guard(x -> true);
	assertEquals(true, s.canFire("blarg"));
	assertEquals(true, t.canFire(testObject));
	assertEquals(true, e.canFire(testObject));

	e.addTransition().from("finished").to("dont-care");
	s.consumeEvent("blarg");
	assertEquals("dont-care", s.getState());

	e.addTransition().from("dont-care").to("stuff").onTransition(x -> x.setState("finished"));
	s.consumeEvent("blarg");

	assertEquals("finished", s.getState());
    }

    @Test
    public void testFullConstruction() {
	fs.setState("finished");
	fs.consumeEvent("toggle");
	assertEquals("start", fs.getState());

	fs.consumeEvent("toggle");
	assertEquals("finished", fs.getState());

	fs.consumeEvent("junk");
	assertEquals("finished", fs.getState());
    }
}
