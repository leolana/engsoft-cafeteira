package engsoft.stateMachines;

import java.util.HashSet;
import java.util.Optional;

public class StateMachine<T extends Statefull> extends Statefull {
    private T object;
    private HashSet<Event<T>> events = new HashSet<Event<T>>();

    public StateMachine(T object) {
	this.object = object;
    }

    public String getState() {
	return object.getState();
    }

    public void setState(String newState) {
	object.setState(newState);
    }

    public Event<T> addEvent(String name) {
	Event<T> event = new Event<T>(this, name);
	events.add(event);

	return event;
    }

    public boolean canFire(String name) {
	return getFireableEvent(name)
	    .isPresent();
    }

    public boolean consumeEvent(String name) {
	return fire(name);
    }

    public boolean fire(String name) {
	return getFireableEvent(name)
	    .map(x -> x.fire(object))
	    .isPresent();
    }

    private Optional<Event<T>> getFireableEvent(String name) {
	return events
	    .stream()
	    .filter(x -> x.getName().equals(name))
	    .filter(x -> x.canFire(object))
	    .findAny();
    }
}
