package engsoft.stateMachines;

import java.util.HashSet;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.Arrays;

public class Transition<T extends Statefull> {
    private Event<T> event;
    private HashSet<String> froms = new HashSet<String>();
    private String to;
    private Predicate<T> guard = x -> true;
    private HashSet<Consumer<T>> callbacks = new HashSet<Consumer<T>>();

    Transition(Event<T> event) {
	this.event = event;
    }

    public boolean canFire(T object) {
	return froms.stream()
	    .filter(x -> x.equals(object.getState()) && guard.test(object))
	    .findAny()
	    .isPresent();
    }

    public boolean fire(T object) {
	if(canFire(object)) {
	    object.setState(to);
	    callbacks.stream().forEach(x -> x.accept(object));

	    return true;
	} else {
	    return false;
	}
    }

    public Transition<T> from(String... fromState) {
	Arrays.stream(fromState).forEach(x -> froms.add(x));
	return this;
    }

    public Transition<T> to(String toState) {
	this.to = toState;
	return this;
    }

    public Transition<T> guard(Predicate<T> clause) {
	this.guard = clause;
    	return this;
    }

    public Transition<T> onTransition(Consumer<T>... lambdas) {
	Arrays.stream(lambdas).forEach(x -> callbacks.add(x));
	return this;
    }

    public Event<T> done() {
	return event;
    }
}
