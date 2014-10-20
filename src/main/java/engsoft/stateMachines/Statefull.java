package engsoft.stateMachines;

public abstract class Statefull {
    protected String state;

    public String getState() {
	return state;
    }
    public void setState(String newState) {
	this.state = newState;
    }
}
