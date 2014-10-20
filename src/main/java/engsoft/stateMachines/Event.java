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
	for(Transition transition : transitions) {
	    if(transition.canFire(object)) return true;
	}

	return false;
    }

    public void fire(T object) {
	for(Transition transition : transitions) {
	    if(transition.canFire(object)) {
		transition.fire(object);
		return;
	    }
	}
    }

    public StateMachine<T> done() {
	return stateMachine;
    }
}
