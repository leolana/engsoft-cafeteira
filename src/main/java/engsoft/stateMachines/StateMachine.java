package engsoft.stateMachines;

import java.util.HashSet;

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
	Event<T> event = getEvent(name);
	if(event != null) return event.canFire(object);

	return false;
    }

    public void consumeEvent(String name) {
	if(canFire(name)) fire(name);
    }

    void fire(String name) {
	Event<T> event = getEvent(name);
	if(event != null) event.fire(object);
    }

    Event<T> getEvent(String name) {
	for(Event<T> event : events) {
	    if(event.getName() == name) return event;
	}
	return null;
    }
}
