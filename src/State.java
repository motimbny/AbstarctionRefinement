import java.util.List;

public class State {
	
	private String name;
	private List<State> neighbordes_state_list;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<State> getNeighbordes_state_list() {
		return neighbordes_state_list;
	}
	public void setNeighbordes_state_list(List<State> neighbordes_state_list) {
		this.neighbordes_state_list = neighbordes_state_list;
	}

}
