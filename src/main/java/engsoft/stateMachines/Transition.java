package engsoft.stateMachines;

import java.util.HashSet;
import java.util.function.Predicate;

public class Transition<T extends Statefull> {
    private Event<T> event;
    private HashSet<String> froms = new HashSet<String>();
    private String to;
    private HashSet<Predicate<T>> guards = new HashSet<Predicate<T>>();

    Transition(Event<T> event) {
	this.event = event;
    }

    public boolean canFire(T object) {
	if(!froms.contains(object.getState())) return false;

	for(Predicate<T> guard : guards) {
	    if(!guard.test(object)) return false;
	}
	return true;
    }

    public void fire(T object) {
	object.setState(to);
    }

    public Transition<T> from(String fromState) {
	froms.add(fromState);
	return this;
    }

    public Transition<T> to(String toState) {
	this.to = toState;
	return this;
    }

    public Transition<T> guard(Predicate<T> clause) {
	guards.add(clause);
    	return this;
    }

    public Event<T> done() {
	return event;
    }
}
