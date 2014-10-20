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

    public Event<T> addEvent() {
	Event<T> event = new Event<T>(this);
	events.add(event);

	return event;
    }

    public boolean canFire(Event<T> event) {
	return event.canFire(object);
    }

    public void consumeEvent(Event<T> event) {
	if(canFire(event)) fire(event);
    }

    void fire(Event<T> event) {
	event.fire(object);
    }
}
