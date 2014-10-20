package engsoft.stateMachines;

import java.util.HashSet;

public class Transition<T extends Statefull> {
    public interface GuardClause<T> {
	Boolean run(T object);
    }

    private Event<T> event;
    private HashSet<String> froms = new HashSet<String>();
    private String to;
    private HashSet<GuardClause> guards = new HashSet<GuardClause>();

    Transition(Event<T> event) {
	this.event = event;
    }

    public boolean canFire(T object) {
	if(!froms.contains(object.getState())) return false;

	for(GuardClause guard : guards) {
	    if(!guard.run(object)) return false;
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

    public Transition<T> guard(GuardClause<T> clause) {
	guards.add(clause);
    	return this;
    }

    public Event<T> done() {
	return event;
    }
}
