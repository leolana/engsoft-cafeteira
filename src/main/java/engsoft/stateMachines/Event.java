package engsoft.stateMachines;

import java.util.HashSet;

public class Event<T extends Statefull> {
    private HashSet<Transition<T>> transitions = new HashSet<Transition<T>>();
    private StateMachine<T> stateMachine;
    private String name;

    public Event(StateMachine<T> stateMachine, String name) {
	this.stateMachine = stateMachine;
	this.name = name;
    };

    public String getName() {
	return name;
    }

    public Transition<T> addTransition() {
	Transition<T> transition = new Transition<T>(this);
	transitions.add(transition);

	return transition;
    }

    public boolean canFire(T object) {
	return transitions.stream()
	    .anyMatch(x -> x.canFire(object));
    }

    public boolean fire(T object) {
	return transitions.stream()
	    .filter(x -> x.canFire(object))
	    .map(x -> x.fire(object))
	    .findAny()
	    .isPresent();
    }

    public StateMachine<T> done() {
	return stateMachine;
    }
}
